package br.unifor.retail.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import br.unifor.retail.R;
import br.unifor.retail.rest.ProductService;
import br.unifor.retail.rest.response.ResponseProduct;

@EActivity(R.layout.activity_product)
public class ProductActivity extends AppCompatActivity {

    @RestService
    protected ProductService productService;

    @ViewById
    protected TextView nome_produto;

    @ViewById
    protected TextView valor_preco_produto;

    @ViewById
    protected TextView valor_tamanho_produto;

    @ViewById
    protected TextView valor_cor_produto;

    protected ResponseProduct responseProduct;
    protected Intent intent;
    protected String contents;
    protected Long idDoQRCOde;
    protected Handler handler;


    @AfterViews
    public void begin() {

        intent = getIntent();
        contents = intent.getStringExtra("contents");
        handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (!contents.isEmpty()) {
                    Log.d("sc", contents);
                    idDoQRCOde = Long.parseLong(contents);
                    busca(idDoQRCOde);
                }
            }
        });
    }

    @Background
    public void busca(Long idQrCode) {

        try {
            responseProduct = productService.searchProduct(idQrCode);
            mostrarActivity(responseProduct);
        } catch (Exception e){
            Log.d("Puta que Pariu", e.toString());
        }


    }

    @UiThread
    public void mostrarActivity(ResponseProduct responseProduct) {
        try{

            nome_produto.setText(responseProduct.getNome().toString());
            valor_cor_produto.setText(responseProduct.getCor().toString());
            valor_preco_produto.setText(responseProduct.getPreco().toString());
            valor_tamanho_produto.setText(responseProduct.getTamanho().toString());

        } catch (Exception e){
            Log.d("Erro do caralho", e.toString());

        }
    }


}
