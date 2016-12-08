package br.unifor.retail.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
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
import br.unifor.retail.adapter.AdapterListViewHistory;
import br.unifor.retail.adapter.AdapterListViewMyProduct;
import br.unifor.retail.adapter.AdapterListViewProduct;
import br.unifor.retail.model.PedidoHasProduto;
import br.unifor.retail.model.Product;
import br.unifor.retail.model.RecordLogin;
import br.unifor.retail.model.Review;
import br.unifor.retail.navegation.drawer.NavegationDrawer;
import br.unifor.retail.qr.code.QrCode;
import br.unifor.retail.rest.PedidoHasProdutoService;
import br.unifor.retail.session.SessoinManager;
import br.unifor.retail.singleton.SingletonHistory;
import br.unifor.retail.singleton.SingletonMyProduct;
import br.unifor.retail.singleton.SingletonProduct;
import me.sudar.zxingorient.Barcode;
import me.sudar.zxingorient.ZxingOrient;
import me.sudar.zxingorient.ZxingOrientResult;

import static android.R.attr.contextClickable;
import static android.R.attr.format;
import static br.unifor.retail.R.id.action_history_ListView;

@OptionsMenu(R.menu.menu_geral)
@EActivity(R.layout.activity_my_prodcuct)
public class MyProductActivity extends AppCompatActivity {

    private Toolbar toolbar;
    NavegationDrawer navegationDrawer;


    QrCode qrCode;

    Handler handler;

    ArrayList<SingletonMyProduct> singletonMyProductArrayList = new ArrayList<>();

    Collection<PedidoHasProduto> pedidoHasProdutoCollection;

    @RestService
    PedidoHasProdutoService pedidoHasProdutoService;

    @ViewById
    protected ListView action_myproduct_ListView;

    protected Intent intent;
    protected Long cliente_id;
    SessoinManager manager;
    RecordLogin recordLogin;

    @AfterViews
    protected void begin() {

        manager = new SessoinManager(this);

        toolbar = (Toolbar) findViewById(R.id.toolbarMyProduct);
        toolbar.setTitle("Meus Produtos");
        toolbar.setBackground(getResources().getDrawable(R.drawable.canto_superior_da_tela));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        ArrayList<SingletonMyProduct> singleton_my_products = todos_Os_Produtos();

//        AdapterListViewMyProduct adapter = new AdapterListViewMyProduct(singleton_my_products, getApplicationContext(), this);
//
//        ListView listView;
//        listView = (ListView) findViewById(R.id.myproduct);
//
//        listView.setAdapter(adapter);

        manager = new SessoinManager(getApplicationContext());
        recordLogin = manager.pegaUsuario();

        cliente_id = recordLogin.getCliente().getId();

        Log.i("Cliente id", cliente_id + "");

        busca(cliente_id);

        navegationDrawer = new NavegationDrawer(toolbar, this);
        navegationDrawer.getProfile();

        qrCode = new QrCode(this, getApplicationContext());

        handler = new Handler();
    }

    @OptionsItem(R.id.menu_carinho)
    public void carrinho() {
        Intent intent = new Intent(getApplicationContext(), PedidoActivity_.class);
        startActivity(intent);
    }

    @OptionsItem(R.id.menu_qr_code)
    public void qrCode() {
        qrCode.scanQR();
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

        }
    }

    @Background
    public void busca(Long cliente_id) {

        try {
            pedidoHasProdutoCollection = pedidoHasProdutoService.searchPedidoHasProduct(cliente_id);
            montaActivity(pedidoHasProdutoCollection);

        } catch (ResourceAccessException e) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Verifique a sua conexão com a internet", Toast.LENGTH_SHORT).show();
//                    dialogHelper.showDialog("Problemas de internet", "Verifique a sua conexão com a internet");

                }
            });

        } catch (Exception e) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Ocorreu algum erro no servidor, mas já estamos resolvendo", Toast.LENGTH_SHORT).show();
//                    dialogHelper.showDialog("Algo deu errado", "Ocorreu algum erro no servidor, mas já estamos resolvendo");

                }
            });
        }
    }

    @UiThread
    public void montaActivity(Collection<PedidoHasProduto> responsePedidoHasProduct) {

        try {

            for (PedidoHasProduto pedidoHasProduto : responsePedidoHasProduct) {

                String uri = "http://bluelab.herokuapp.com" + pedidoHasProduto.getFoto().toString();

                String preco = String.valueOf(pedidoHasProduto.getPreco());
                String quantidade = String.valueOf(pedidoHasProduto.getQuantidade());

                singletonMyProductArrayList.add(new SingletonMyProduct(uri, pedidoHasProduto.getNome(), preco, quantidade));

                Log.i("HHHHHHHHHHHHHH", uri);
            }

            AdapterListViewMyProduct adapter = new AdapterListViewMyProduct(singletonMyProductArrayList, getApplicationContext(), this);
            action_myproduct_ListView.setAdapter(adapter);

        } catch (Exception e) {
            Log.d("Erro na mostra:", e.toString());
        }
    }

    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity_.class);
        startActivity(intent);
    }
//
//    public ArrayList<SingletonMyProduct> todos_Os_Produtos() {
//        ArrayList<SingletonMyProduct> singleton_my_products = new ArrayList<>();
//
//
//        singleton_my_products.add(new SingletonMyProduct(R.drawable.camisa1, "Camisa Social", "Verde", "Tamanho G"));
//        singleton_my_products.add(new SingletonMyProduct(R.drawable.camisa2, "Camisa Social", "Salmão", "Tamanho G"));
//
//
//        return singleton_my_products;
//    }

}
