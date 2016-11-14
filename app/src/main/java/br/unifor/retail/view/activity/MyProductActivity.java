package br.unifor.retail.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;

import java.util.ArrayList;

import br.unifor.retail.R;
import br.unifor.retail.adapter.AdapterListViewMyProduct;
import br.unifor.retail.navegation.drawer.NavegationDrawer;
import br.unifor.retail.singleton.SingletonMyProduct;
import me.sudar.zxingorient.Barcode;
import me.sudar.zxingorient.ZxingOrient;

@OptionsMenu(R.menu.menu_geral)
@EActivity(R.layout.activity_my_prodcuct)
public class MyProductActivity extends AppCompatActivity {

    private Toolbar toolbar;
    NavegationDrawer navegationDrawer;

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 42;

    @AfterViews
    protected void begin() {
        toolbar = (Toolbar) findViewById(R.id.toolbarMyProduct);
        toolbar.setTitle("Meus Produtos");
        toolbar.setBackground(getResources().getDrawable(R.drawable.canto_superior_da_tela));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<SingletonMyProduct> singleton_my_products = todos_Os_Produtos();

        AdapterListViewMyProduct adapter = new AdapterListViewMyProduct(singleton_my_products, getApplicationContext(), this);

        ListView listView;
        listView = (ListView) findViewById(R.id.myproduct);

        listView.setAdapter(adapter);

        navegationDrawer = new NavegationDrawer(toolbar, this);
        navegationDrawer.getProfile();
        navegationDrawer.createNavigationDrawer();


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

    public void onBackPressed(){
        Intent intent = new Intent(this, MainActivity_.class);
        startActivity(intent);
    }

    public ArrayList<SingletonMyProduct> todos_Os_Produtos() {
        ArrayList<SingletonMyProduct> singleton_my_products = new ArrayList<>();


        singleton_my_products.add(new SingletonMyProduct(R.drawable.camisa1, "Camisa Social", "Verde", "Tamanho G"));
        singleton_my_products.add(new SingletonMyProduct(R.drawable.camisa2, "Camisa Social", "Salmão", "Tamanho G"));
        singleton_my_products.add(new SingletonMyProduct(R.drawable.camisa1, "Camisa Social", "Verde", "Tamanho G"));
        singleton_my_products.add(new SingletonMyProduct(R.drawable.camisa2, "Camisa Social", "Salmão", "Tamanho G"));
        singleton_my_products.add(new SingletonMyProduct(R.drawable.camisa1, "Camisa Social", "Verde", "Tamanho G"));
        singleton_my_products.add(new SingletonMyProduct(R.drawable.camisa2, "Camisa Social", "Salmão", "Tamanho G"));
        singleton_my_products.add(new SingletonMyProduct(R.drawable.camisa1, "Camisa Social", "Verde", "Tamanho G"));
        singleton_my_products.add(new SingletonMyProduct(R.drawable.camisa2, "Camisa Social", "Salmão", "Tamanho G"));
        singleton_my_products.add(new SingletonMyProduct(R.drawable.camisa1, "Camisa Social", "Verde", "Tamanho G"));
        singleton_my_products.add(new SingletonMyProduct(R.drawable.camisa2, "Camisa Social", "Salmão", "Tamanho G"));
        singleton_my_products.add(new SingletonMyProduct(R.drawable.camisa1, "Camisa Social", "Verde", "Tamanho G"));
        singleton_my_products.add(new SingletonMyProduct(R.drawable.camisa2, "Camisa Social", "Salmão", "Tamanho G"));
        singleton_my_products.add(new SingletonMyProduct(R.drawable.camisa1, "Camisa Social", "Verde", "Tamanho G"));
        singleton_my_products.add(new SingletonMyProduct(R.drawable.camisa2, "Camisa Social", "Salmão", "Tamanho G"));


        return singleton_my_products;
    }

}
