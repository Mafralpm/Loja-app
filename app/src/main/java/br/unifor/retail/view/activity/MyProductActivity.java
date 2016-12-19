package br.unifor.retail.view.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import org.springframework.web.client.ResourceAccessException;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;
import br.unifor.retail.R;
import br.unifor.retail.adapter.AdapterListViewMyProduct;
import br.unifor.retail.model.PedidoHasProduto;
import br.unifor.retail.model.RecordLogin;
import br.unifor.retail.navegation.drawer.NavegationDrawer;
import br.unifor.retail.qr.code.QrCode;
import br.unifor.retail.session.SessionManager;
import br.unifor.retail.singleton.SingletonMyProduct;
import me.sudar.zxingorient.ZxingOrient;
import me.sudar.zxingorient.ZxingOrientResult;

import java.util.Collection;
import br.unifor.retail.rest.PedidoHasProdutoService;
import java.util.ArrayList;

import static android.R.attr.format;

@OptionsMenu(R.menu.menu_geral)
@EActivity(R.layout.activity_my_prodcuct)
public class MyProductActivity extends AppCompatActivity {

    private Toolbar toolbar;
    NavegationDrawer navegationDrawer;
    SessionManager manager;


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
    RecordLogin recordLogin;

    @Bean
    AdapterListViewMyProduct adapter;

    @AfterViews
    protected void begin() {

        manager = new SessionManager(this);

        toolbar = (Toolbar) findViewById(R.id.toolbarMyProduct);
        toolbar.setTitle("Meus Produtos");
        toolbar.setBackground(getResources().getDrawable(R.drawable.canto_superior_da_tela));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

            adapter.getDadosMyProduct(singletonMyProductArrayList, this);
            action_myproduct_ListView.setAdapter(adapter);

        } catch (Exception e) {
            Log.d("Erro na mostra:", e.toString());
        }
    }

    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity_.class);
        startActivity(intent);
    }
}
