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
import br.unifor.retail.model.Pedido;
import br.unifor.retail.model.Product;
import br.unifor.retail.model.RecordLogin;
import br.unifor.retail.model.Review;
import br.unifor.retail.navegation.drawer.NavegationDrawer;
import br.unifor.retail.rest.HistoryService;
import br.unifor.retail.rest.PedidoService;
import br.unifor.retail.rest.ProductService;
import br.unifor.retail.rest.ReviewService;
import br.unifor.retail.session.SessoinManager;
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
    @RestService
    protected PedidoService pedidoService;

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

    protected Product product;
    protected Collection<Review>  responseReview;

    protected Intent intent;
    protected String contents;
    protected Long idDoQRCOde;
    protected Handler handler;
    private Toolbar toolbar;
    NavegationDrawer navegationDrawer;
    private RecordLogin recordLogin;


    History history = new History();

    Pedido pedido =  new Pedido();



    private SessoinManager manager;


    ArrayList<SingletonProduct> singletonProductArrayList =  new ArrayList<>();

    @AfterViews
    public void begin() {

        manager = new SessoinManager(this);
        recordLogin = manager.pegaUsuario();

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
    }

    @Background
    public void busca(Long idQrCode) {

        try {
            product = productService.searchProduct(idQrCode);
            responseReview = reviewService.searchProductReview(idQrCode);
            montaActivity(product, responseReview);

        } catch (Exception e) {
            Log.d("Erro na busca", e.toString());
        }
    }

    @UiThread
    public void montaActivity(Product product, Collection<Review> responseReview) {

        try {
            produto_nome.setText(product.getNome().toString());
            produto_preco.setText(product.getPreco().toString());
            produto_tamanho.setText(product.getTamanho().toString().toUpperCase());
            int color = Color.parseColor(product.getCor());
            produto_cor.setColorFilter(color);
            String uri = "http://bluelab.herokuapp.com" + product.getFoto().toString();

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

    }

    @Click
    public void adcionar_carrinho(){
        criaPedido();
        Intent intent = new Intent(this, CartActivity_.class);
        intent.putExtra("id", contents);
        if(contents != null){
            Log.d("Testezinho", contents);
        }
        startActivity(intent);
    }

    @Background
    public void criaPedido(){
        setaDadosPedido();
        pedido = pedidoService.criaPedido(pedido);
        manager.setIdCarrinho(pedido.getId());
//        Log.d("TESTE DE ID", pedido.getId().toString());
    }

    public void setaDadosPedido(){
        pedido.setCliente_id(manager.pegaUsuario().getCliente().getId());
        pedido.setValor_total(0.00);
        pedido.setFinalizado(false);
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
