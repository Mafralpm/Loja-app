package br.unifor.retail.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
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
import br.unifor.retail.model.Pedido;
import br.unifor.retail.model.Product;
import br.unifor.retail.model.RecordLogin;
import br.unifor.retail.navegation.drawer.NavegationDrawer;
import br.unifor.retail.rest.PedidoService;
import br.unifor.retail.rest.ProductService;
import br.unifor.retail.session.SessoinManager;
import br.unifor.retail.singleton.SingletonCar;
import br.unifor.retail.view.activity.common.BaseActivity;
import me.sudar.zxingorient.Barcode;
import me.sudar.zxingorient.ZxingOrient;
import me.sudar.zxingorient.ZxingOrientResult;

import static android.R.attr.format;

@OptionsMenu(R.menu.menu_carrinho)
@EActivity(R.layout.activity_cart)
public class PedidoActivity extends BaseActivity {

    NavegationDrawer navegationDrawer;
    protected Intent intent;
    protected String contents;

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 42;

    @ViewById
    protected ListView car_activity_listView;

    @ViewById
    protected Toolbar toolbarCart;

    @RestService
    protected PedidoService pedidoService;

    @RestService
    protected ProductService productService;

    protected Collection<Product> productCollection;

    ArrayList<SingletonCar> singleton_cars = new ArrayList<>();

    private SessoinManager manager;
    private RecordLogin recordLogin;

    private Pedido pedido;

    Handler handler = new Handler();

    @AfterViews
    public void begin() {
        manager = new SessoinManager(this);
        recordLogin = manager.pegaUsuario();

        intent = getIntent();
        contents = intent.getStringExtra("id");

        toolbarCart.setTitle("Carrinho");
        toolbarCart.setBackground(getResources().getDrawable(R.drawable.canto_superior_da_tela));
        setSupportActionBar(toolbarCart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AdapterListViewCar adapter_listView_car = new AdapterListViewCar(singleton_cars, getApplicationContext(), this);

        car_activity_listView.setAdapter(adapter_listView_car);

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        ZxingOrientResult scanResult =
                ZxingOrient.parseActivityResult(requestCode, resultCode, intent);
        try {
            if (scanResult != null) {
                //  String leitura = scanResult.getContents();
                String contents = intent.getStringExtra("SCAN_RESULT");
                Intent intentResult = new Intent(this, ProductActivity_.class);
                intentResult
                        .putExtra("contents", contents)
                        .putExtra("format", format);
                startActivity(intentResult);
            }
        } catch (RuntimeException e) {
            Log.i("TETESRGFG111111", e.toString());
        }

    }

    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity_.class);
        startActivity(intent);
    }

    @Background
    public void buscaPedidoHasProdutos() {
        try {
            productCollection = pedidoService.searchProductReview(manager.getIdCarrinho());
            Log.d("IDCARRRINHo", manager.getIdCarrinho() + "");
            mostraNaTela(productCollection);

        } catch (ResourceAccessException e) {
            handler.post(new Runnable() {
                @Override
                public void run() {
//                        Toast.makeText(getApplicationContext(), "Verifique a sua conexão com a internet", Toast.LENGTH_SHORT).show();
                    dialogHelper.showDialog("Problemas de internet", "Verifique a sua conexão com a internet");

                }
            });

        } catch (Exception e) {
            handler.post(new Runnable() {
                @Override
                public void run() {
//                        Toast.makeText(getApplicationContext(), "Ocorreu algum erro no servidor, mas já estamos resolvendo", Toast.LENGTH_SHORT).show();
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
                String uri = "http://bluelab.herokuapp.com" + product.getFoto().toString();
                singleton_cars.add(new SingletonCar(uri, product.getNome(), product.getPreco().toString()));

                AdapterListViewCar adapterListViewCar = new AdapterListViewCar(singleton_cars, getApplicationContext(), this);
                car_activity_listView.setAdapter(adapterListViewCar);
            }
        } catch (Exception e) {
            Log.i("TETESRGFG222222", e.toString());
        }
    }

    @Background
    public void buscaPedido() {
        pedido = pedidoService.buscaPedido(manager.getIdCarrinho());
        pedido.setFinalizado(true);
        pedidoService.finalizaPedido(manager.getIdCarrinho(), pedido);
    }

    @Click
    public void pedido_envia_pro_caixa() {
        buscaPedido();
    }


}