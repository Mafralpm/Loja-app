package br.unifor.retail.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.ArrayList;

import br.unifor.retail.R;
import br.unifor.retail.adapter.AdapterListViewHistory;
import br.unifor.retail.navegation.drawer.NavegationDrawer;
import br.unifor.retail.rest.HistoryService;
import br.unifor.retail.rest.ProductService;
import br.unifor.retail.rest.ReviewService;
import br.unifor.retail.rest.response.ResponseHistory;
import br.unifor.retail.rest.response.ResponseProduct;
import br.unifor.retail.rest.response.ResponseReview;
import br.unifor.retail.singleton.SingletonMyProduct;
import br.unifor.retail.view.activity.common.BaseActivity;
import me.sudar.zxingorient.Barcode;
import me.sudar.zxingorient.ZxingOrient;

import static br.unifor.retail.R.id.produto_foto;

@OptionsMenu(R.menu.menu_geral)
@EActivity(R.layout.activity_history)
public class HistoryActivity extends BaseActivity {

    @RestService
    protected HistoryService historyService;

    protected Intent intent;
    protected String contents;
    protected int idDoQRCOde;
    protected Handler handler;

    private Toolbar toolbar;



    NavegationDrawer navegationDrawer;

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 42;

    protected ResponseHistory responseHistory;

    @AfterViews
    public void begin() {
        toolbar = (Toolbar) findViewById(R.id.toolbarHistory);
        toolbar.setTitle("Historico");
        toolbar.setBackground(getResources().getDrawable(R.drawable.canto_superior_da_tela));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        intent = getIntent();
        contents = intent.getStringExtra("contents");
        handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (!contents.isEmpty()) {
                    Log.d("sc", contents);
                    showProgressDialogCancel("Buscando os dados", null);
                    idDoQRCOde = Integer.parseInt(contents);
                    busca(idDoQRCOde);
                }
            }
        });

        ArrayList<SingletonMyProduct> singleton_my_products;
        singleton_my_products = todos_Os_Produtos();

        AdapterListViewHistory adapter_listView_my_product = new AdapterListViewHistory(singleton_my_products, getApplicationContext());

        ListView listView = (ListView) findViewById(R.id.action_history_ListView);
        listView.setAdapter(adapter_listView_my_product);

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

    @Background
    public void busca(int idQrCode) {

        try {
            responseHistory = historyService.searchHistory(idQrCode);
            mostrarActivity(responseHistory);
            //mostrarActivity(responseProduct);
//
        } catch (Exception e) {
            Log.d("Puta que Pariu", e.toString());
        }
    }

    @UiThread
    //public void mostrarActivity(ResponseProduct responseProduct) {
    public void mostrarActivity(ResponseHistory responseHistory) {


        try {

//            produto_nome.setText(responseProduct.getNome().toString());
//            produto_preco.setText(responseProduct.getPreco().toString());
//            produto_tamanho.setText(responseProduct.getTamanho().toString().toUpperCase());
//            int color = Color.parseColor(responseProduct.getCor());
//            produto_cor.setColorFilter(color);
//            String uri = "http://bluelab.herokuapp.com" + responseProduct.getFoto().toString();
//
//            Picasso.with(produto_foto.getContext()).load(uri).into(produto_foto);

            Log.d("Vai da certo", responseHistory.getIdHistory()+" ");


        } catch (Exception e) {
            Log.d("Erro do caralho", e.toString());

        }
    }

    public ArrayList<SingletonMyProduct> todos_Os_Produtos() {
        ArrayList<SingletonMyProduct> singleton_my_products = new ArrayList<>();

        singleton_my_products.add(new SingletonMyProduct(R.drawable.camisa1, "Camisa Social", "Verde", "10/04/2016"));
        singleton_my_products.add(new SingletonMyProduct(R.drawable.camisa2, "Camisa Social", "Salmão", "15/04/2016"));
        singleton_my_products.add(new SingletonMyProduct(R.drawable.camisa1, "Camisa Social", "Verde", "10/04/2016"));
        singleton_my_products.add(new SingletonMyProduct(R.drawable.camisa2, "Camisa Social", "Salmão", "15/04/2016"));
        singleton_my_products.add(new SingletonMyProduct(R.drawable.camisa1, "Camisa Social", "Verde", "10/04/2016"));
        singleton_my_products.add(new SingletonMyProduct(R.drawable.camisa2, "Camisa Social", "Salmão", "15/04/2016"));
        singleton_my_products.add(new SingletonMyProduct(R.drawable.camisa1, "Camisa Social", "Verde", "10/04/2016"));
        singleton_my_products.add(new SingletonMyProduct(R.drawable.camisa2, "Camisa Social", "Salmão", "15/04/2016"));
        singleton_my_products.add(new SingletonMyProduct(R.drawable.camisa1, "Camisa Social", "Verde", "10/04/2016"));
        singleton_my_products.add(new SingletonMyProduct(R.drawable.camisa2, "Camisa Social", "Salmão", "15/04/2016"));
        singleton_my_products.add(new SingletonMyProduct(R.drawable.camisa1, "Camisa Social", "Verde", "10/04/2016"));
        singleton_my_products.add(new SingletonMyProduct(R.drawable.camisa2, "Camisa Social", "Salmão", "15/04/2016"));
        singleton_my_products.add(new SingletonMyProduct(R.drawable.camisa1, "Camisa Social", "Verde", "10/04/2016"));
        singleton_my_products.add(new SingletonMyProduct(R.drawable.camisa2, "Camisa Social", "Salmão", "15/04/2016"));
        singleton_my_products.add(new SingletonMyProduct(R.drawable.camisa1, "Camisa Social", "Verde", "10/04/2016"));
        singleton_my_products.add(new SingletonMyProduct(R.drawable.camisa2, "Camisa Social", "Salmão", "15/04/2016"));

        return singleton_my_products;
    }



}
