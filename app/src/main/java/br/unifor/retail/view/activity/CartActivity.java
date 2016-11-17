package br.unifor.retail.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;

import java.util.ArrayList;
import java.util.List;

import br.unifor.retail.R;
import br.unifor.retail.adapter.AdapterListViewCar;
import br.unifor.retail.model.Product;
import br.unifor.retail.navegation.drawer.NavegationDrawer;
import br.unifor.retail.singleton.SingletonCar;
import me.sudar.zxingorient.Barcode;
import me.sudar.zxingorient.ZxingOrient;
import me.sudar.zxingorient.ZxingOrientResult;

import static android.R.attr.format;

@OptionsMenu(R.menu.menu_carrinho)
@EActivity(R.layout.activity_cart)
public class CartActivity extends AppCompatActivity {

    private Toolbar toolbar;
    NavegationDrawer navegationDrawer;
    ImageView imageViewDelete;

    Product product = new Product();

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 42;

    protected Intent intent;
    protected String contents;

    ProductActivity productActivity = new ProductActivity();


    @AfterViews
    public void begin() {
        toolbar = (Toolbar) findViewById(R.id.toolbarCart);
        toolbar.setTitle("Carrinho");
        toolbar.setBackground(getResources().getDrawable(R.drawable.canto_superior_da_tela));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<SingletonCar> singleton_cars;
        singleton_cars = todos_os_produtos();

        AdapterListViewCar adapter_listView_car = new AdapterListViewCar(singleton_cars, getApplicationContext(), this);


        ListView listView = (ListView) findViewById(R.id.car_activity_listView);
        listView.setAdapter(adapter_listView_car);


        String contents = String.valueOf(productActivity.getProduct());
        Log.d("sc", contents);


        navegationDrawer = new NavegationDrawer(toolbar, this);
        navegationDrawer.getProfile();
        navegationDrawer.createNavigationDrawer();


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

        }

    }

    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity_.class);
        startActivity(intent);
    }


    public List<SingletonCar> todos_os_produtos() {
        List<SingletonCar> singleton_cars = new ArrayList<>();

        singleton_cars.add(new SingletonCar(R.drawable.camisa_preta, "Camisa Fem.", "Camisa femenina", "preta"));

        return singleton_cars;
    }


}