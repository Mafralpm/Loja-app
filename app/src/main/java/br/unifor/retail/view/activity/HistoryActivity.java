package br.unifor.retail.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import br.unifor.retail.session.SessoinManager;
import br.unifor.retail.singleton.SingletonHistory;
import br.unifor.retail.view.activity.common.BaseActivity;
import me.sudar.zxingorient.Barcode;
import me.sudar.zxingorient.ZxingOrient;
import me.sudar.zxingorient.ZxingOrientResult;

import static android.R.attr.contextClickable;
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
    //    @ViewById
//    protected TextView iten_listview_history_textView_Data;
    @ViewById
    protected ListView action_history_ListView;


    protected Intent intent;
    protected Long cliente_id;
    SessoinManager manager;
    RecordLogin recordLogin;

    private Toolbar toolbar;
    protected Collection<Product> productCollection;

    QrCode qrCode;

    ArrayList<SingletonHistory> singletonHistoryArrayList = new ArrayList<>();

    NavegationDrawer navegationDrawer;

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 42;

    protected History history;

    @AfterViews
    public void begin() {
        toolbar = (Toolbar) findViewById(R.id.toolbarHistory);
        toolbar.setTitle("Histórico");
        toolbar.setBackground(getResources().getDrawable(R.drawable.canto_superior_da_tela));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navegationDrawer = new NavegationDrawer(toolbar, this);
        navegationDrawer.getProfile();

        manager = new SessoinManager(getApplicationContext());
        recordLogin = manager.pegaUsuario();

        cliente_id = recordLogin.getCliente().getId();

        Log.i("Cliente id", cliente_id + "");

        buscaProdutos(cliente_id);

        qrCode = new QrCode(this, getApplicationContext());
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
    public void buscaProdutos(Long cliente_id) {


        try {
            productCollection = historyService.searchProducts(cliente_id);
            montaActivity(productCollection);

        } catch (Exception e) {
            Log.d("Erro na busca", e.toString());
        }
    }

    @UiThread
    public void montaActivity(Collection<Product> productCollection) {

        try {

            for (Product product : productCollection) {

                String uri = "http://bluelab.herokuapp.com" + product.getFoto().toString();

                singletonHistoryArrayList.add(new SingletonHistory(uri, product.getNome(), product.getPreco(), "18/11/2016"));

                Log.i("HHHHHHHHHHHHHH", uri);
            }

            AdapterListViewHistory adapter = new AdapterListViewHistory(singletonHistoryArrayList, getApplicationContext());
            action_history_ListView.setAdapter(adapter);

        } catch (Exception e) {
            Log.d("Erro na mostra:", e.toString());
        }
    }

    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity_.class);
        startActivity(intent);
    }

}
