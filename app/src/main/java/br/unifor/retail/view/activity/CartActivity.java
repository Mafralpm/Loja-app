package br.unifor.retail.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.List;

import br.unifor.retail.R;
import br.unifor.retail.adapter.Adapter_ListView_Car;
import br.unifor.retail.singleton.Singleton_Car;


public class CartActivity extends AppCompatActivity {

    private Drawer.Result navigationDrawerLeft;
    private AccountHeader.Result headerNavigationLeft;
    private Toolbar toolbar;

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

        createNavigationDrawer();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_geral, menu);
        return true;
    }

    public List<Singleton_Car> todos_os_produtos(){
        List<Singleton_Car> singleton_cars = new ArrayList<>();

//        //Travando
//        singleton_cars.add(new Singleton_Car(R.drawable.bermuda_1, "Bermuda_1", "Bermuda muito legal", "essa ai"));
//        singleton_cars.add(new Singleton_Car(R.drawable.bermuda_2, "Bermuda_2", "Bermuda muito massa", "essa ai"));
//        singleton_cars.add(new Singleton_Car(R.drawable.camisa3, "Camisa_1", "Camisa social muito legal", "essa ai"));
//        singleton_cars.add(new Singleton_Car(R.drawable.camisa_1, "Camisa_2", "Camisa social muito massa", "essa ai"));
//        singleton_cars.add(new Singleton_Car(R.drawable.camisa_2, "Camisa_3", "Camisa social muito irada", "essa ai"));
//        singleton_cars.add(new Singleton_Car(R.drawable.regata_1, "Camisa_Regata_1", "Camisa regata muito legal", "essa ai"));
//        singleton_cars.add(new Singleton_Car(R.drawable.regata_1, "Camisa_Regata_2", "Camisa regata muito massa", "essa ai"));

//        ok
        singleton_cars.add(new Singleton_Car(R.mipmap.ic_launcher, "Bermuda_1", "Bermuda muito legal", "essa ai"));
        singleton_cars.add(new Singleton_Car(R.mipmap.ic_launcher, "Bermuda_2", "Bermuda muito massa", "essa ai"));
        singleton_cars.add(new Singleton_Car(R.mipmap.ic_launcher, "Camisa_1", "Camisa social muito legal", "essa ai"));
        singleton_cars.add(new Singleton_Car(R.mipmap.ic_launcher, "Camisa_2", "Camisa social muito massa", "essa ai"));
        singleton_cars.add(new Singleton_Car(R.mipmap.ic_launcher, "Camisa_3", "Camisa social muito irada", "essa ai"));
        singleton_cars.add(new Singleton_Car(R.mipmap.ic_launcher, "Camisa_Regata_1", "Camisa regata muito legal", "essa ai"));
        singleton_cars.add(new Singleton_Car(R.mipmap.ic_launcher, "Camisa_Regata_2", "Camisa regata muito massa", "essa ai"));

        return singleton_cars;
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
                .withSelectedItem(-2)
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
                                Intent intent = new Intent(CartActivity.this, InfoClientActivity.class);
                                startActivity(intent);
                                break;
                            case 1:
                                Intent intent1 = new Intent(CartActivity.this, CartActivity.class);
                                startActivity(intent1);
                                break;
                            case 2:
                                Intent intent2 = new Intent(CartActivity.this, My_ProductActivity.class);
                                startActivity(intent2);
                                break;
                            case 3:
                                Intent intent3 = new Intent(CartActivity.this, HistoryActivity.class);
                                startActivity(intent3);
                                break;
                            case 4:
                                LoginManager.getInstance().logOut();
                                goLoginScreen();
                                break;


                        }
//                        Toast.makeText(CartActivity.this, "Item: " + i, Toast.LENGTH_SHORT).show();
                    }
                })
                .withOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                        Toast.makeText(CartActivity.this, "onItemLongClick: " + i, Toast.LENGTH_SHORT).show();
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
