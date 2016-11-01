package br.unifor.retail.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;

import java.util.ArrayList;
import java.util.List;

import br.unifor.retail.R;
import br.unifor.retail.adapter.Adapter_ListView_Car;
import br.unifor.retail.navegation.drawer.NavegationDrawer;
import br.unifor.retail.singleton.Singleton_Car;

public class CartActivity extends AppCompatActivity {

    private Toolbar toolbar;
    NavegationDrawer navegationDrawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        toolbar = (Toolbar) findViewById(R.id.toolbarCart);
        toolbar.setTitle("Carrinho");
        toolbar.setBackground(getResources().getDrawable(R.drawable.canto_superior_da_tela));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<Singleton_Car> singleton_cars;
        singleton_cars = todos_os_produtos();

        Adapter_ListView_Car adapter_listView_car = new Adapter_ListView_Car(singleton_cars, getApplicationContext());

        ListView listView = (ListView) findViewById(R.id.car_activity_listView);
        listView.setAdapter(adapter_listView_car);

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

    public List<Singleton_Car> todos_os_produtos() {
        List<Singleton_Car> singleton_cars = new ArrayList<>();

        singleton_cars.add(new Singleton_Car(R.drawable.camisa1, "Camisa1", "Camisa social masculina", "verde"));
        singleton_cars.add(new Singleton_Car(R.drawable.camisa2, "Camisa2", "Camisa social femenina", "salmao"));
        singleton_cars.add(new Singleton_Car(R.drawable.camisa1, "Camisa1", "Camisa social masculina", "verde"));
        singleton_cars.add(new Singleton_Car(R.drawable.camisa2, "Camisa2", "Camisa social femenina", "salmao"));
        singleton_cars.add(new Singleton_Car(R.drawable.camisa1, "Camisa1", "Camisa social masculina", "verde"));
        singleton_cars.add(new Singleton_Car(R.drawable.camisa2, "Camisa2", "Camisa social femenina", "salmao"));
        singleton_cars.add(new Singleton_Car(R.drawable.camisa1, "Camisa1", "Camisa social masculina", "verde"));
        singleton_cars.add(new Singleton_Car(R.drawable.camisa2, "Camisa2", "Camisa social femenina", "salmao"));
        singleton_cars.add(new Singleton_Car(R.drawable.camisa1, "Camisa1", "Camisa social masculina", "verde"));
        singleton_cars.add(new Singleton_Car(R.drawable.camisa2, "Camisa2", "Camisa social femenina", "salmao"));
        singleton_cars.add(new Singleton_Car(R.drawable.camisa1, "Camisa1", "Camisa social masculina", "verde"));
        singleton_cars.add(new Singleton_Car(R.drawable.camisa2, "Camisa2", "Camisa social femenina", "salmao"));
        singleton_cars.add(new Singleton_Car(R.drawable.camisa1, "Camisa1", "Camisa social masculina", "verde"));
        singleton_cars.add(new Singleton_Car(R.drawable.camisa2, "Camisa2", "Camisa social femenina", "salmao"));

        return singleton_cars;
    }

}