package br.unifor.retail.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.ArrayList;

import br.unifor.retail.R;
import br.unifor.retail.adapter.AdapterListViewProduct;
import br.unifor.retail.navegation.drawer.NavegationDrawer;
import br.unifor.retail.rest.ProductService;
import br.unifor.retail.rest.ReviewService;
import br.unifor.retail.rest.response.ResponseProduct;
import br.unifor.retail.rest.response.ResponseReview;
import br.unifor.retail.singleton.SingletonProduct;
import br.unifor.retail.view.activity.common.BaseActivity;


@EActivity(R.layout.activity_product)
public class ProductActivity extends BaseActivity {

    @RestService
    protected ProductService productService;
    protected ReviewService reviewService;

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


    protected ResponseProduct responseProduct;
    protected ResponseReview responseReview;

    protected Intent intent;
    protected String contents;
    protected Long idDoQRCOde;
    protected Handler handler;
    private Toolbar toolbar;
    NavegationDrawer navegationDrawer;

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
                    Log.d("sc", contents);
                    idDoQRCOde = Long.parseLong(contents);
                    showProgressDialogCancel("Buscando os dados", null);
                    busca(idDoQRCOde);
                }
            }
        });

        navegationDrawer = new NavegationDrawer(toolbar, this);
        navegationDrawer.getProfile();
        navegationDrawer.createNavigationDrawer();

        ArrayList<SingletonProduct> singletonProductArrayList = todosComentarios();

        AdapterListViewProduct adapter = new AdapterListViewProduct(singletonProductArrayList, getApplicationContext());

        ListView listView;
        listView = (ListView) findViewById(R.id.list_review);

        listView.setAdapter(adapter);



    }

    @Background
    public void busca(Long idQrCode) {

        try {
            responseProduct = productService.searchProduct(idQrCode);
 //           responseReview = reviewService.searchProductReview(idQrCode);
//            mostrarActivity(responseProduct, responseReview);
            mostrarActivity(responseProduct);

        } catch (Exception e) {
            Log.d("Puta que Pariu", e.toString());
        }
    }


    @UiThread
    public void mostrarActivity(ResponseProduct responseProduct) {
    //public void mostrarActivity(ResponseProduct responseProduct, ResponseReview responseReview) {

        try {

            produto_nome.setText(responseProduct.getNome().toString());
            produto_preco.setText(responseProduct.getPreco().toString());
            produto_tamanho.setText(responseProduct.getTamanho().toString().toUpperCase());
            int color = Color.parseColor(responseProduct.getCor());
            produto_cor.setColorFilter(color);
            String uri ="http://bluelab.herokuapp.com"+ responseProduct.getFoto().toString();

            Picasso.with(produto_foto.getContext()).load(uri).into(produto_foto);


        } catch (Exception e) {
            Log.d("Erro do caralho", e.toString());

        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_geral, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        Intent intent = new Intent(getApplicationContext(), CartActivity.class);
        Intent intent2 = new Intent(getApplicationContext(), MainActivity_.class);
        if (menuItem.getItemId() == R.id.menu_carinho) {
            startActivity(intent);
        } else {
            startActivity(intent2);
        }

        return true;
    }

    public ArrayList<SingletonProduct> todosComentarios() {
        ArrayList<SingletonProduct> singletonProductArrayList = new ArrayList<>();

        singletonProductArrayList.add(new SingletonProduct("Usuario 87", 3.5, "jhdfldhfpiuhdspifuhidafiabsfipbpadisbf;dhfk;hadsflkhdskljfhlkdjshflkjdhsflkjhdskljfhdskljhflkdjshflkjdhsflkjhdlkhflkdshflkhdklfhdslkhfdskjhfhdgs,bc,bvzxmnbeuygroewgroyigeifgdlhjgfjhdgfljhgdslhfgldshg"));

        for (int i = 0; i < 15; i++) {
            singletonProductArrayList.add(new SingletonProduct("Usuario " + (i + 1), 5, "jhdfldhfpiuhdspifuhidafiabsfipbpadisbf;dhfk;hadsflkhdskljfhlkdjshflkjdhsflkjhdskljfhdskljhflkdjshflkjdhsflkjhdlkhflkdshflkhdklfhdslkhfdskjhfhdgs,bc,bvzxmnbeuygroewgroyigeifgdlhjgfjhdgfljhgdslhfgldshg"));
        }

        return singletonProductArrayList;

    }

}
