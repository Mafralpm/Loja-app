package br.unifor.retail.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.Collection;

import br.unifor.retail.R;
import br.unifor.retail.adapter.AdapterListViewCar;
import br.unifor.retail.model.History;
import br.unifor.retail.model.Pedido;
import br.unifor.retail.model.Product;
import br.unifor.retail.model.RecordLogin;
import br.unifor.retail.navegation.drawer.NavegationDrawer;
import br.unifor.retail.rest.HistoryService;
import br.unifor.retail.rest.PedidoService;
import br.unifor.retail.rest.ProductService;
import br.unifor.retail.session.SessionManager;
import br.unifor.retail.singleton.SingletonCar;
import br.unifor.retail.view.activity.common.BaseActivity;
import me.sudar.zxingorient.Barcode;
import me.sudar.zxingorient.ZxingOrient;
import me.sudar.zxingorient.ZxingOrientResult;

import static android.R.attr.format;
import static br.unifor.retail.statics.StaticsRest.ROOT_URL;

@OptionsMenu(R.menu.menu_carrinho)
@EActivity(R.layout.activity_cart)
public class CarrinhoActivity extends BaseActivity {

    private NavegationDrawer navegationDrawer;
    protected Intent intent;
//    protected String contents;

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 42;

    @RestService
    protected HistoryService historyService;

    @ViewById
    protected ListView car_activity_listView;

    @ViewById
    protected Toolbar toolbarCart;

    @RestService
    protected PedidoService pedidoService;

    @RestService
    protected ProductService productService;

    protected Collection<Product> productCollection;

    private ArrayList<SingletonCar> singleton_cars = new ArrayList<>();

    private SessionManager manager;
    RecordLogin recordLogin;

    private Pedido pedido;

    private History history = new History();

    private Handler handler = new Handler();

    @Bean
    AdapterListViewCar adapter_listView_car;
    @AfterViews
    public void begin() {

        showProgressDialog("Buscando produtos do carrinho");

        manager = new SessionManager(this);
        recordLogin = manager.pegaUsuario();
//
//        intent = getIntent();
//        contents = intent.getStringExtra("contents");

        toolbarCart.setTitle("Carrinho");
        toolbarCart.setBackground(getResources().getDrawable(R.drawable.canto_superior_da_tela));
        setSupportActionBar(toolbarCart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navegationDrawer = new NavegationDrawer(toolbarCart, this);
        navegationDrawer.getProfile();

            buscaPedidoHasProdutos();
    }

    @OptionsItem(R.id.carrinho_qr_code)
    public void qrCode() {
        if (!(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);
        } else {
            scanBarcode();
        }
    }

    @UiThread
    public void scanBarcode() {
        ZxingOrient integrator = new ZxingOrient(this);
        integrator
                .setToolbarColor("#AA000000")
                .showInfoBox(false)
                .setBeep(false)
                .setVibration(true)
                .initiateScan(Barcode.QR_CODE);
    }

    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity_.class);
        startActivity(intent);
    }

    @Background
    public void buscaPedidoHasProdutos() {
        try {
            productCollection = pedidoService.searchPedidoHasProduto(manager.getIdCarrinho());
            Log.d("IDCARRRINHo", manager.getIdCarrinho() + "");
            mostraNaTela(productCollection);

        } catch (ResourceAccessException e) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    dialogHelper.showDialog("Problemas de internet", "Verifique a sua conexão com a internet");
                }
            });

        } catch (Exception e) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    dialogHelper.showDialog("Algo deu errado", "Ocorreu algum erro no servidor, mas já estamos resolvendo");
                }
            });
        }
    }

    @UiThread
    @Background
    public void mostraNaTela(Collection<Product> productCollection) {
        try {
            for (Product product : productCollection) {
                String uri = ROOT_URL + product.getFoto().toString();
                singleton_cars.add(new SingletonCar(uri, product.getNome(), product.getPreco().toString()));

                adapter_listView_car.getDadosCar(singleton_cars, this, pedidoService);
                car_activity_listView.setAdapter(adapter_listView_car   );
            }
        } catch (Exception e) {
            Log.i("TETESRGFG222222", e.toString());
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialogHelper.dismissDialog();
            }
        }, 2000);
    }

    @Background
    public void finalizaPedido() {
        pedido = pedidoService.buscaPedido(manager.getIdCarrinho());
        pedido.setFinalizado(true);
        pedidoService.finalizaPedido(manager.getIdCarrinho(), pedido);
    }

    @Click
    public void pedido_envia_pro_caixa() {
        Log.d("Entrou?", "Entrou aqui?");
        try {
            Log.d("Entrou2222?", "Entrou aqui2222?");
            finalizaPedido();

            Intent itent = new Intent(this, MyProductActivity_.class);
            startActivity(itent);
        }catch (Exception e){
            Log.d("IHNHJJJKJ", e.toString());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        ZxingOrientResult scanResult =
                ZxingOrient.parseActivityResult(requestCode, resultCode, intent);
        try {
            if (scanResult != null) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                Intent intentResult = new Intent(this, ProductActivity_.class);
                intentResult
                        .putExtra("contents", contents)
                        .putExtra("format", format);

                Long id = Long.valueOf(contents);
                manager.setIdProduto(id);
                enviaProHistorico();
                startActivity(intentResult);
            }
        } catch (RuntimeException e) {
            Log.d("Deu erro", e.toString());
        } catch (Exception e) {
            Log.d("DEU ERRO AQUI", e.toString());
        }

    }

    @Background
    public void enviaProHistorico(){
        setaDadosHistorico();
        historyService.cria(history);
    }

    public void setaDadosHistorico(){
        history.setCliente_id(manager.pegaUsuario().getUser().getUser_id());
        history.setProduto_id(manager.getIdProduto());
    }
}