package br.unifor.retail.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
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
import br.unifor.retail.adapter.AdapterListViewProduct;
import br.unifor.retail.model.History;
import br.unifor.retail.model.Pedido;
import br.unifor.retail.model.PedidoHasProduto;
import br.unifor.retail.model.Product;
import br.unifor.retail.model.RecordLogin;
import br.unifor.retail.model.Review;
import br.unifor.retail.navegation.drawer.NavegationDrawer;
import br.unifor.retail.qr.code.QrCode;
import br.unifor.retail.rest.HistoryService;
import br.unifor.retail.rest.PedidoService;
import br.unifor.retail.rest.ProductService;
import br.unifor.retail.rest.ReviewService;
import br.unifor.retail.session.SessionManager;
import br.unifor.retail.singleton.SingletonProduct;
import br.unifor.retail.view.activity.common.BaseActivity;

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
    @ViewById
    protected Toolbar toolbarProduct;

    protected Intent intent;
    protected String contents;
    protected Long idDoQRCOde;
    protected Handler handler;
    private NavegationDrawer navegationDrawer;
    private RecordLogin recordLogin;

    protected Product product;
    protected Collection<Review> reviews;
    private History history = new History();
    private Pedido pedido = new Pedido();
    private PedidoHasProduto pedidoHasProduto = new PedidoHasProduto();
    private SessionManager manager;

    private ArrayList<SingletonProduct> singletonProductArrayList;

    @Bean
    protected AdapterListViewProduct adapter;

    private QrCode qrCode;

    @AfterViews
    public void begin() {

        manager = new SessionManager(this);
        recordLogin = manager.pegaUsuario();
        qrCode = new QrCode(this, getApplicationContext());

        toolbarProduct.setTitle("Produtos");
        toolbarProduct.setBackground(getResources().getDrawable(R.drawable.canto_superior_da_tela));
        setSupportActionBar(toolbarProduct);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        intent = getIntent();
        contents = intent.getStringExtra("contents");
        handler = new Handler();

        singletonProductArrayList = new ArrayList<>();
//        adapter.getDadosProduct(singletonProductArrayList);


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

        navegationDrawer = new NavegationDrawer(toolbarProduct, this);
        navegationDrawer.getProfile();

    }

    @Background
    public void busca(Long idQrCode) {
        try {
            product = productService.searchProduct(idQrCode);
            reviews = reviewService.searchProductReview(idQrCode);
            montaActivity(product, reviews);

        } catch (ResourceAccessException e) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                        Toast.makeText(getApplicationContext(), "Verifique a sua conexão com a internet", Toast.LENGTH_SHORT).show();
//                    dialogHelper.showDialog("Problemas de internet", "Verifique a sua conexão com a internet");

                }
            });

        } catch (Exception e) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                        Toast.makeText(getApplicationContext(), "Ocorreu algum erro no servidor, mas já estamos resolvendo", Toast.LENGTH_SHORT).show();
//                    dialogHelper.showDialog("Algo deu errado", "Ocorreu algum erro no servidor, mas já estamos resolvendo");

                }
            });
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

            for (Review review : responseReview) {
                Double nota = Double.valueOf(review.getNota());
                singletonProductArrayList.add(new SingletonProduct(nota, review.getReview_descric()));
            }

            adapter.getDadosProduct(singletonProductArrayList);
            produto_list_view.setAdapter(adapter);

        } catch (Exception e) {
            Log.d("Erro no mostra:", e.toString());
        }
    }

    public void enviaProHistorico(Long idQrCode) {

        history.setProduto_id(idQrCode);
    }

    @Click
    public void adcionar_carrinho() {
        criaPedido();
        Intent intent = new Intent(this, PedidoActivity_.class);
        intent.putExtra("id", contents);
        if (contents != null) {
            Log.d("Testezinho", contents);
        }
        startActivity(intent);
    }

    @Background
    public void criaPedido() {
        setaDadosPedido();
        pedido = pedidoService.criaPedido(pedido);
        manager.setIdCarrinho(pedido.getId());
        Log.d("TESTE DE ID", manager.getIdCarrinho() + "");
        criaPedidoHasProduto();

    }

    public void setaDadosPedido() {
        pedido.setCliente_id(manager.pegaUsuario().getCliente().getId());
        Log.d("TESTE DE ID do cliente", manager.pegaUsuario().getCliente().getId().toString());

        pedido.setValor_total(0.00);
        pedido.setFinalizado(false);
    }

    @Background
    public void criaPedidoHasProduto() {
        setaDadosPedidoHasProduto();
        pedidoService.criaPedidoHasProduto(pedidoHasProduto);
    }

    public void setaDadosPedidoHasProduto() {
        pedidoHasProduto.setProduto_id(manager.getIdProduto());
        Log.d("TESTE DE ID PRODUTO", pedidoHasProduto.getProduto_id().toString());

        pedidoHasProduto.setPedido_id(manager.getIdCarrinho());
        Log.d("TESTE PEDIDO", pedidoHasProduto.getPedido_id().toString());

        pedidoHasProduto.setQuantidade(1);
        Log.d("TESTE DE QUANTIDADE", String.valueOf(pedidoHasProduto.getQuantidade()));

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

    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity_.class);
        startActivity(intent);
    }

}
