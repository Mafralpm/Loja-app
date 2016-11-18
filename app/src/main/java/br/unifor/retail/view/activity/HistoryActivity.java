package br.unifor.retail.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;

import java.util.ArrayList;

import br.unifor.retail.R;
import br.unifor.retail.adapter.AdapterListViewHistory;
import br.unifor.retail.model.History;
import br.unifor.retail.navegation.drawer.NavegationDrawer;
import br.unifor.retail.singleton.SingletonMyProduct;
import br.unifor.retail.view.activity.common.BaseActivity;
import me.sudar.zxingorient.Barcode;
import me.sudar.zxingorient.ZxingOrient;
import me.sudar.zxingorient.ZxingOrientResult;

import static android.R.attr.format;

@OptionsMenu(R.menu.menu_geral)
@EActivity(R.layout.activity_history)
public class HistoryActivity extends BaseActivity {

  //  @RestService
 //   protected HistoryService historyService;

    protected Intent intent;
    protected String contents;
    protected int idDoQRCOde;
    protected Handler handler;

    private Toolbar toolbar;



    NavegationDrawer navegationDrawer;

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 42;

    protected History history;

    @AfterViews
    public void begin() {
        toolbar = (Toolbar) findViewById(R.id.toolbarHistory);
        toolbar.setTitle("Historico");
        toolbar.setBackground(getResources().getDrawable(R.drawable.canto_superior_da_tela));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ArrayList<SingletonMyProduct> singleton_my_products;
        singleton_my_products = todos_Os_Produtos();

        AdapterListViewHistory adapter_listView_my_product = new AdapterListViewHistory(singleton_my_products, getApplicationContext());

        ListView listView = (ListView) findViewById(R.id.action_history_ListView);
        listView.setAdapter(adapter_listView_my_product);

        navegationDrawer = new NavegationDrawer(toolbar, this);
        navegationDrawer.getProfile();

    }

    @OptionsItem(R.id.menu_carinho)
    public void carrinho() {
        Intent intent = new Intent(getApplicationContext(), CartActivity_.class);
        startActivity(intent);
    }

    @OptionsItem(R.id.menu_qr_code)
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

        }

    }

    public void onBackPressed(){
        Intent intent = new Intent(this, MainActivity_.class);
        startActivity(intent);
    }




    public ArrayList<SingletonMyProduct> todos_Os_Produtos() {
        ArrayList<SingletonMyProduct> singleton_my_products = new ArrayList<>();

        singleton_my_products.add(new SingletonMyProduct(R.drawable.camisa_preta, "Camisa Basica", "Preta", "10/04/2016"));
        singleton_my_products.add(new SingletonMyProduct(R.drawable.camisa2, "Camisa Social", "Salmão", "10/04/2016"));
        singleton_my_products.add(new SingletonMyProduct(R.drawable.camisa1, "Camisa Social", "Verde", "10/04/2016"));

        return singleton_my_products;
    }



}
