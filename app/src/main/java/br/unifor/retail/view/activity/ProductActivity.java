package br.unifor.retail.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;
import org.json.JSONObject;

import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;


import java.util.ArrayList;
import java.util.List;

import br.unifor.retail.R;
import br.unifor.retail.adapter.Adapter_ListView_My_Product;
import br.unifor.retail.adapter.Adapter_ListView_Product;
import br.unifor.retail.navegation.drawer.NavegationDrawer;
import br.unifor.retail.rest.ProductService;
import br.unifor.retail.rest.response.ResponseProduct;
import br.unifor.retail.singleton.Singleton_My_Product;
import br.unifor.retail.singleton.Singleton_Product;
import br.unifor.retail.view.activity.common.BaseActivity;

import static com.facebook.AccessToken.getCurrentAccessToken;

@EActivity(R.layout.activity_product)
public class ProductActivity extends BaseActivity {

    @RestService
    protected ProductService productService;


    protected ResponseProduct responseProduct;
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

        ArrayList<Singleton_Product> singletonProductArrayList = todosComentarios();

        Adapter_ListView_Product adapter = new Adapter_ListView_Product(singletonProductArrayList, getApplicationContext());

        ListView listView;
        listView = (ListView) findViewById(R.id.list_review);

        listView.setAdapter(adapter);


    }

    @Background
    public void busca(Long idQrCode) {

        try {
            responseProduct = productService.searchProduct(idQrCode);
            mostrarActivity(responseProduct);
        } catch (Exception e) {
            Log.d("Puta que Pariu", e.toString());
        }
    }


    @UiThread
    public void mostrarActivity(ResponseProduct responseProduct) {
        try {

//            descrica_ActivityProducty.setText(responseProduct.getNome().toString());
//            valor_cor_produto.setText(responseProduct.getCor().toString());
//            preco_ActivityProduct.setText(responseProduct.getPreco().toString());
//            valor_tamanho_produto.setText(responseProduct.getTamanho().toString());

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

    public ArrayList<Singleton_Product> todosComentarios() {
        ArrayList<Singleton_Product> singletonProductArrayList = new ArrayList<>();

        singletonProductArrayList.add(new Singleton_Product("Usuario 87", 3.5, "jhdfldhfpiuhdspifuhidafiabsfipbpadisbf;dhfk;hadsflkhdskljfhlkdjshflkjdhsflkjhdskljfhdskljhflkdjshflkjdhsflkjhdlkhflkdshflkhdklfhdslkhfdskjhfhdgs,bc,bvzxmnbeuygroewgroyigeifgdlhjgfjhdgfljhgdslhfgldshg"));

        for (int i = 0; i < 15; i++) {
            singletonProductArrayList.add(new Singleton_Product("Usuario " + (i + 1), 5, "jhdfldhfpiuhdspifuhidafiabsfipbpadisbf;dhfk;hadsflkhdskljfhlkdjshflkjdhsflkjhdskljfhdskljhflkdjshflkjdhsflkjhdlkhflkdshflkhdklfhdslkhfdskjhfhdgs,bc,bvzxmnbeuygroewgroyigeifgdlhjgfjhdgfljhgdslhfgldshg"));
        }

        return singletonProductArrayList;

    }

}
