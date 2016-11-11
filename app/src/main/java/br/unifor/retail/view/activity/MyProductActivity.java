package br.unifor.retail.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import br.unifor.retail.R;
import br.unifor.retail.adapter.AdapterListViewMyProduct;
import br.unifor.retail.navegation.drawer.NavegationDrawer;
import br.unifor.retail.singleton.SingletonMyProduct;


public class MyProductActivity extends AppCompatActivity {

    private Toolbar toolbar;
    NavegationDrawer navegationDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_prodcuct);
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
