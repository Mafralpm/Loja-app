package br.unifor.retail.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;


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
    private Toolbar toolbar;
    private Drawer.Result navigationDrawerLeft;
    private AccountHeader.Result headerNavigationLeft;


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
                    busca(idDoQRCOde);
                }
            }
        });

        createNavigationDrawer();
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
        try{

            nome_produto.setText(responseProduct.getNome().toString());
            valor_cor_produto.setText(responseProduct.getCor().toString());
            valor_preco_produto.setText(responseProduct.getPreco().toString());
            valor_tamanho_produto.setText(responseProduct.getTamanho().toString());

        } catch (Exception e){
            Log.d("Erro do caralho", e.toString());

        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_geral, menu);
        return true;
    }

    private void goLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    private void createNavigationDrawer() {
        //NAVIGATION DRAWER
        headerNavigationLeft = new AccountHeader()
                .withActivity(this)
                .withCompactStyle(false)
//                .withSavedInstance(savedInstanceState)
                .withThreeSmallProfileImages(true)
                .withHeaderBackground(R.drawable.menu)




//                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
//                    @Override
//                    public boolean onProfileChanged(View view, IProfile iProfile, boolean b) {
//                        Toast.makeText(HistoryActivity.this, "onProfileChanged: " + iProfile.getName(), Toast.LENGTH_SHORT).show();
//                        headerNavigationLeft.setBackgroundRes(R.drawable.camisa3);
//                        return false;
//                    }
//                })
                .build();


//        headerNavigationLeft.addProfile(), 0);
        headerNavigationLeft.addProfiles(new ProfileDrawerItem().withName("Person One").withEmail("person1@gmail.com").withIcon(getResources().getDrawable(R.drawable.pandalambendo)));




        navigationDrawerLeft = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withDisplayBelowToolbar(false)
                .withActionBarDrawerToggleAnimated(true)
                .withDrawerGravity(Gravity.LEFT)
//                .withSavedInstance(savedInstanceState)
                .withSelectedItem(-1)
                .withActionBarDrawerToggle(true)
                .withAccountHeader(headerNavigationLeft)
                   /*.withOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
                       @Override
                       public boolean onNavigationClickListener(View view) {
                           return false;
                       }
                   })*/
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                        switch (i){
                            case 0:
                                Intent intent = new Intent(ProductActivity.this, InfoClientActivity.class);
                                startActivity(intent);
                                break;
                            case 1:
                                Intent intent1 = new Intent(ProductActivity.this, CartActivity.class);
                                startActivity(intent1);
                                break;
                            case 2:
                                Intent intent2 = new Intent(ProductActivity.this, My_ProductActivity.class);
                                startActivity(intent2);
                                break;
                            case 3:
                                Intent intent3 = new Intent(ProductActivity.this, HistoryActivity.class);
                                startActivity(intent3);
                                break;
                            case 4:
                                LoginManager.getInstance().logOut();
                                goLoginScreen();
                                break;


                        }
//                        Toast.makeText(ProductActivity.this, "Item: " + i, Toast.LENGTH_SHORT).show();
                    }
                })
                .withOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                        Toast.makeText(ProductActivity.this, "onItemLongClick: " + i, Toast.LENGTH_SHORT).show();
                        return false;
                    }
                })
                .build();


        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Perfil").withIcon(getResources().getDrawable(R.drawable.perfil)));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Carrinho").withIcon(getResources().getDrawable(R.drawable.carrinho)));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Meus pedidos").withIcon(getResources().getDrawable(R.drawable.pedidos)));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Historico de itens").withIcon(getResources().getDrawable(R.drawable.visualizacao)));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Sair").withIcon(getResources().getDrawable(R.drawable.sair)));
    }



}
