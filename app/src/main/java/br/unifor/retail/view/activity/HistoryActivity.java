package br.unifor.retail.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
import br.unifor.retail.model.History;
import br.unifor.retail.model.Product;
import br.unifor.retail.model.RecordLogin;
import br.unifor.retail.navegation.drawer.NavegationDrawer;
import br.unifor.retail.qr.code.QrCode;
import br.unifor.retail.rest.HistoryService;
import br.unifor.retail.session.SessionManager;
import br.unifor.retail.singleton.SingletonHistory;
import br.unifor.retail.view.activity.common.BaseActivity;
import me.sudar.zxingorient.ZxingOrient;
import me.sudar.zxingorient.ZxingOrientResult;

import static android.R.attr.format;

@OptionsMenu(R.menu.menu_geral)
@EActivity(R.layout.activity_history)
public class HistoryActivity extends BaseActivity {

    @RestService
    protected HistoryService historyService;

    @ViewById
    protected ImageView iten_listview_history_imageView_Image;
    @ViewById
    protected TextView iten_listview_history_textView_Nome;
    @ViewById
    protected TextView iten_listview_history_TextView_Preco;
    @ViewById
    protected ListView action_history_ListView;


    protected Intent intent;
    protected Long cliente_id;
    private SessionManager manager;
    private RecordLogin recordLogin;

    private Toolbar toolbar;
    protected Collection<Product> productCollection;

    private QrCode qrCode;

    ArrayList<SingletonHistory> singletonHistoryArrayList = new ArrayList<>();

    private NavegationDrawer navegationDrawer;

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 42;

    private History history = new History();

    private Handler handler = new Handler();

    @AfterViews
    public void begin() {
        showProgressDialog("Buscando produtos visualizados");

        manager = new SessionManager(getApplicationContext());
        recordLogin = manager.pegaUsuario();

        qrCode = new QrCode(this, getApplicationContext());

        toolbar = (Toolbar) findViewById(R.id.toolbarHistory);
        toolbar.setTitle("Histórico");
        toolbar.setBackground(getResources().getDrawable(R.drawable.canto_superior_da_tela));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navegationDrawer = new NavegationDrawer(toolbar, this);
        navegationDrawer.getProfile();

        cliente_id = recordLogin.getCliente().getId();
        buscaProdutos(cliente_id);
    }

    @OptionsItem(R.id.menu_carinho)
    public void carrinho() {
        Intent intent = new Intent(getApplicationContext(), CarrinhoActivity_.class);
        startActivity(intent);
    }

    @OptionsItem(R.id.menu_qr_code)
    public void qrCode() {
        qrCode.scanQR();
    }


    @Background
    public void buscaProdutos(Long cliente_id) {
        try {
            productCollection = historyService.searchProducts(cliente_id);
            montaActivity(productCollection);
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
    public void montaActivity(Collection<Product> productCollection) {
        try {
            for (Product product : productCollection) {
                String uri = "http://bluelab.herokuapp.com" + product.getFoto().toString();
                singletonHistoryArrayList.add(new SingletonHistory(uri, product.getNome(), product.getPreco(), "18/11/2016"));
            }
            AdapterListViewHistory adapter = new AdapterListViewHistory(singletonHistoryArrayList, getApplicationContext());
            action_history_ListView.setAdapter(adapter);
        } catch (Exception e) {
            Log.d("Erro na mostra:", e.toString());
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialogHelper.dismissDialog();
            }
        }, 2000);

    }

    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity_.class);
        startActivity(intent);
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

                Log.d("èo id ?", contents);
                Long id = Long.valueOf(contents);
                manager.setIdProduto(id);
                Log.d("Id do produto", manager.getIdProduto()+"");
                enviaProHistorico();
                startActivity(intentResult);
            }
        } catch (RuntimeException e) {
            Log.d("Deu nessa xibata", e.toString());
        } catch (Exception e) {
            Log.d("DEU ERRO AQUI CARALHO", e.toString());
        }
        }

    @Background
    public void enviaProHistorico(){
        setaDadosHistorico();
        historyService.cria(history);
    }

    public void setaDadosHistorico(){
        history.setCliente_id(manager.pegaUsuario().getCliente().getId());
        Log.d("CLIENTE ID", history.getCliente_id().toString());
        history.setProduto_id(manager.getIdProduto());
        Log.d("PRODUTO ID", history.getProduto_id().toString());
    }
}
