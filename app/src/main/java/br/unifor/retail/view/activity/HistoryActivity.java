package br.unifor.retail.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

import org.json.JSONObject;

import java.util.ArrayList;

import br.unifor.retail.R;
import br.unifor.retail.adapter.Adapter_ListView_History;
import br.unifor.retail.adapter.Adapter_ListView_My_Product;
import br.unifor.retail.navegation.drawer.NavegationDrawer;
import br.unifor.retail.singleton.Singleton_My_Product;

import static com.facebook.AccessToken.getCurrentAccessToken;

public class HistoryActivity extends AppCompatActivity {

    private Toolbar toolbar;
    NavegationDrawer navegationDrawer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        toolbar = (Toolbar) findViewById(R.id.toolbarHistory);
        toolbar.setTitle("Historico");
        toolbar.setBackground(getResources().getDrawable(R.drawable.canto_superior_da_tela));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<Singleton_My_Product> singleton_my_products;
        singleton_my_products = todos_Os_Produtos();

        Adapter_ListView_History adapter_listView_my_product = new Adapter_ListView_History(singleton_my_products, getApplicationContext());

        ListView listView = (ListView) findViewById(R.id.action_history_ListView);
        listView.setAdapter(adapter_listView_my_product);

        navegationDrawer = new NavegationDrawer(toolbar, this);
        navegationDrawer.getProfile();
        navegationDrawer.createNavigationDrawer();


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_geral, menu);
        return true;
    }

    public ArrayList<Singleton_My_Product> todos_Os_Produtos() {
        ArrayList<Singleton_My_Product> singleton_my_products = new ArrayList<>();

        singleton_my_products.add(new Singleton_My_Product(R.drawable.camisa1, "Camisa Social", "Verde", "10/04/2016"));
        singleton_my_products.add(new Singleton_My_Product(R.drawable.camisa2, "Camisa Social", "Salmão", "15/04/2016"));
        singleton_my_products.add(new Singleton_My_Product(R.drawable.camisa1, "Camisa Social", "Verde", "10/04/2016"));
        singleton_my_products.add(new Singleton_My_Product(R.drawable.camisa2, "Camisa Social", "Salmão", "15/04/2016"));
        singleton_my_products.add(new Singleton_My_Product(R.drawable.camisa1, "Camisa Social", "Verde", "10/04/2016"));
        singleton_my_products.add(new Singleton_My_Product(R.drawable.camisa2, "Camisa Social", "Salmão", "15/04/2016"));
        singleton_my_products.add(new Singleton_My_Product(R.drawable.camisa1, "Camisa Social", "Verde", "10/04/2016"));
        singleton_my_products.add(new Singleton_My_Product(R.drawable.camisa2, "Camisa Social", "Salmão", "15/04/2016"));
        singleton_my_products.add(new Singleton_My_Product(R.drawable.camisa1, "Camisa Social", "Verde", "10/04/2016"));
        singleton_my_products.add(new Singleton_My_Product(R.drawable.camisa2, "Camisa Social", "Salmão", "15/04/2016"));
        singleton_my_products.add(new Singleton_My_Product(R.drawable.camisa1, "Camisa Social", "Verde", "10/04/2016"));
        singleton_my_products.add(new Singleton_My_Product(R.drawable.camisa2, "Camisa Social", "Salmão", "15/04/2016"));
        singleton_my_products.add(new Singleton_My_Product(R.drawable.camisa1, "Camisa Social", "Verde", "10/04/2016"));
        singleton_my_products.add(new Singleton_My_Product(R.drawable.camisa2, "Camisa Social", "Salmão", "15/04/2016"));
        singleton_my_products.add(new Singleton_My_Product(R.drawable.camisa1, "Camisa Social", "Verde", "10/04/2016"));
        singleton_my_products.add(new Singleton_My_Product(R.drawable.camisa2, "Camisa Social", "Salmão", "15/04/2016"));

        return singleton_my_products;
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


}
