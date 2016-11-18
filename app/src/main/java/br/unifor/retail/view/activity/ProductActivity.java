package br.unifor.retail.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.ArrayList;
import java.util.Collection;

import br.unifor.retail.R;
import br.unifor.retail.adapter.AdapterListViewProduct;
import br.unifor.retail.model.History;
import br.unifor.retail.model.Review;
import br.unifor.retail.model.response.ResponseProduct;
import br.unifor.retail.navegation.drawer.NavegationDrawer;
import br.unifor.retail.rest.HistoryService;
import br.unifor.retail.rest.ProductService;
import br.unifor.retail.rest.ReviewService;
import br.unifor.retail.singleton.SingletonProduct;
import br.unifor.retail.view.activity.common.BaseActivity;
import me.sudar.zxingorient.Barcode;
import me.sudar.zxingorient.ZxingOrient;

@OptionsMenu(R.menu.menu_geral)
@EActivity(R.layout.activity_product)
public class ProductActivity extends BaseActivity {

    @RestService
    protected ProductService productService;
    @RestService
    protected ReviewService reviewService;
    @RestService
    protected HistoryService historyService;

    @ViewById
    protected TextView produto_nome;
    @ViewById
    protected TextView produto_preco;
    @ViewById
    protected TextView produto_tamanho;
    @ViewById
    protected ImageView produto_foto;
    @ViewById
    protected ImageView produto_cor;
    @ViewById
    protected ListView produto_list_view;
    @ViewById
    protected RatingBar adapter_review_ratingBar;
    @ViewById
    protected TextView adapter_review_descricao;

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 42;

    protected ResponseProduct responseProduct;
    protected Collection<Review>  responseReview;

    protected Intent intent;
    protected String contents;
    protected Long idDoQRCOde;
    protected Handler handler;
    private Toolbar toolbar;
    NavegationDrawer navegationDrawer;
    History history = new History();


    ArrayList<SingletonProduct> singletonProductArrayList =  new ArrayList<>();

    @AfterViews
    public void begin() {

        toolbar = (Toolbar) findViewById(R.id.toolbarProduct);
        toolbar.setTitle("Produtos");
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
                    idDoQRCOde = Long.valueOf(contents);
                    showProgressDialogCancel("Buscando os dados", null);
                    busca(idDoQRCOde);
                    enviaProHistorico(idDoQRCOde);
                }
            }
        });

        navegationDrawer = new NavegationDrawer(toolbar, this);
        navegationDrawer.getProfile();
        navegationDrawer.createNavigationDrawer();



    }

    @Background
    public void busca(Long idQrCode) {

        try {
            responseProduct = productService.searchProduct(idQrCode);
            responseReview = reviewService.searchProductReview(idQrCode);
            montaActivity(responseProduct, responseReview);

        } catch (Exception e) {
            Log.d("Erro na busca", e.toString());
        }
    }


    @UiThread
    public void montaActivity(ResponseProduct responseProduct, Collection<Review> responseReview) {

        try {
            produto_nome.setText(responseProduct.getNome().toString());
            produto_preco.setText(responseProduct.getPreco().toString());
            produto_tamanho.setText(responseProduct.getTamanho().toString().toUpperCase());
            int color = Color.parseColor(responseProduct.getCor());
            produto_cor.setColorFilter(color);
            String uri = "http://bluelab.herokuapp.com" + responseProduct.getFoto().toString();

            Picasso.with(produto_foto.getContext()).load(uri).into(produto_foto);

            for (Review review : responseReview){
                Double nota = Double.valueOf(review.getNota());
                singletonProductArrayList.add(new SingletonProduct(nota, review.getReview_descric()));
            }

            AdapterListViewProduct adapter = new AdapterListViewProduct(singletonProductArrayList, getApplicationContext());
            produto_list_view.setAdapter(adapter);

        } catch (Exception e) {
            Log.d("Erro na mostra:", e.toString());
        }
    }

    public void enviaProHistorico(Long idQrCode){

        history.setProduto_id(idQrCode);
        //history.setCliente_id();
        //historyService.enviar();

    }

    @Click
    public void adcionar_carrinho(){
        Intent intent = new Intent(this, CartActivity_.class);
        intent.putExtra("id", contents);
        if(contents != null){
            Log.d("Testezinho", contents);
        }
        startActivity(intent);


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

}
