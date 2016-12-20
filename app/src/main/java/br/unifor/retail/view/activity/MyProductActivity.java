package br.unifor.retail.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
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
import br.unifor.retail.adapter.AdapterListViewMyProduct;
import br.unifor.retail.model.History;
import br.unifor.retail.model.PedidoHasProduto;
import br.unifor.retail.model.RecordLogin;
import br.unifor.retail.navegation.drawer.NavegationDrawer;
import br.unifor.retail.qr.code.QrCode;
import br.unifor.retail.rest.HistoryService;
import br.unifor.retail.rest.PedidoHasProdutoService;
import br.unifor.retail.rest.ReviewService;
import br.unifor.retail.session.SessionManager;
import br.unifor.retail.singleton.SingletonMyProduct;
import br.unifor.retail.view.activity.common.BaseActivity;
import me.sudar.zxingorient.ZxingOrient;
import me.sudar.zxingorient.ZxingOrientResult;

import static android.R.attr.format;

@OptionsMenu(R.menu.menu_geral)
@EActivity(R.layout.activity_my_prodcuct)
public class MyProductActivity extends BaseActivity {

    private Toolbar toolbar;
    private NavegationDrawer navegationDrawer;
    private SessionManager manager;
    private QrCode qrCode;
    private Handler handler;

    ArrayList<SingletonMyProduct> singletonMyProductArrayList = new ArrayList<>();

    private Collection<PedidoHasProduto> pedidoHasProdutoCollection;

    @RestService
    PedidoHasProdutoService pedidoHasProdutoService;

    @RestService
    protected HistoryService historyService;

    @RestService
    protected ReviewService reviewService;

    @ViewById
    protected ListView action_myproduct_ListView;
    @ViewById
    protected Button my_product_button_avaliar;

    protected Intent intent;
    protected Long cliente_id;
    private RecordLogin recordLogin;

    private History history = new History();

    AdapterListViewMyProduct adapter;

    @AfterViews
    protected void begin() {

        showProgressDialog("Buscando produtos comprados");

        manager = new SessionManager(this);
        recordLogin = manager.pegaUsuario();
        cliente_id = recordLogin.getCliente().getId();

        qrCode = new QrCode(this, getApplicationContext());
        handler = new Handler();

        toolbar = (Toolbar) findViewById(R.id.toolbarMyProduct);
        toolbar.setTitle("Meus Produtos");
        toolbar.setBackground(getResources().getDrawable(R.drawable.canto_superior_da_tela));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navegationDrawer = new NavegationDrawer(toolbar, this);
        navegationDrawer.getProfile();

        busca(cliente_id);
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
    public void busca(Long cliente_id) {
        try {
            pedidoHasProdutoCollection = pedidoHasProdutoService.searchPedidoHasProduct(cliente_id);
            montaActivity(pedidoHasProdutoCollection);
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
    public void montaActivity(Collection<PedidoHasProduto> responsePedidoHasProduct) {
        try {
            for (PedidoHasProduto pedidoHasProduto : responsePedidoHasProduct) {
                String uri = "http://bluelab.herokuapp.com" + pedidoHasProduto.getFoto().toString();
                String preco = String.valueOf(pedidoHasProduto.getPreco());
                String quantidade = String.valueOf(pedidoHasProduto.getQuantidade());
                Long id = pedidoHasProduto.getProduto_id();

                singletonMyProductArrayList.add(new SingletonMyProduct(uri, pedidoHasProduto.getNome(), preco, quantidade, id));
            }
            adapter = new AdapterListViewMyProduct(singletonMyProductArrayList, getApplicationContext(), this);
            action_myproduct_ListView.setAdapter(adapter);
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
            Log.d("Deu erro de runtime ", e.toString());
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
        history.setCliente_id(manager.pegaUsuario().getCliente().getId());
        history.setProduto_id(manager.getIdProduto());
    }

    @Click
    public void my_product_button_avaliar(){



    }
}
