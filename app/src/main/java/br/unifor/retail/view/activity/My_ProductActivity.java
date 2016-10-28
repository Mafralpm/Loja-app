package br.unifor.retail.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
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
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;

import br.unifor.retail.R;
import br.unifor.retail.adapter.Adapter_ListView_My_Product;
import br.unifor.retail.singleton.Singleton_My_Product;


public class My_ProductActivity extends AppCompatActivity {

    private Drawer.Result navigationDrawerLeft;
    private AccountHeader.Result headerNavigationLeft;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_prodcuct);
        final int BUTTON_MYPRODCT = 0;
        toolbar = (Toolbar) findViewById(R.id.toolbarMyProduct);
        toolbar.setTitle("Meus Produtos");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<Singleton_My_Product> singleton_my_products = todos_Os_Produtos();

        Adapter_ListView_My_Product adapter = new Adapter_ListView_My_Product(singleton_my_products, getApplicationContext(), BUTTON_MYPRODCT);

        ListView listView;
        listView = (ListView) findViewById(R.id.myproduct);

        listView.setAdapter(adapter);

        createNavigationDrawer(savedInstanceState);

    }

    public ArrayList<Singleton_My_Product> todos_Os_Produtos(){
        ArrayList<Singleton_My_Product> singleton_my_products = new ArrayList<>();

        singleton_my_products.add(new Singleton_My_Product(R.drawable.pandalambendo, "1", "Blusa preta", "10/04/2016"));
        singleton_my_products.add(new Singleton_My_Product(R.drawable.felipemassapilotof1, "2", "Blusa branca", "10/04/2016"));
        singleton_my_products.add(new Singleton_My_Product(R.drawable.bermuda_1, "3", "Blusa verde", "10/04/2016"));
        singleton_my_products.add(new Singleton_My_Product(R.drawable.pandalambendo, "4", "Blusa preta", "10/04/2016"));
        singleton_my_products.add(new Singleton_My_Product(R.drawable.felipemassapilotof1, "5", "Blusa branca", "10/04/2016"));
        singleton_my_products.add(new Singleton_My_Product(R.drawable.bermuda_1, "6", "Blusa verde", "10/04/2016"));
        singleton_my_products.add(new Singleton_My_Product(R.drawable.pandalambendo, "7", "Blusa preta", "10/04/2016"));
        singleton_my_products.add(new Singleton_My_Product(R.drawable.felipemassapilotof1, "8", "Blusa branca", "10/04/2016"));
        singleton_my_products.add(new Singleton_My_Product(R.drawable.bermuda_1, "9", "Blusa verde", "10/04/2016"));
        singleton_my_products.add(new Singleton_My_Product(R.drawable.pandalambendo, "10", "Blusa preta", "10/04/2016"));
        singleton_my_products.add(new Singleton_My_Product(R.drawable.felipemassapilotof1, "11", "Blusa branca", "10/04/2016"));
        singleton_my_products.add(new Singleton_My_Product(R.drawable.bermuda_1, "12", "Blusa verde", "10/04/2016"));
        singleton_my_products.add(new Singleton_My_Product(1));

        return singleton_my_products;
    }

    private void goLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void createNavigationDrawer(Bundle savedInstanceState) {
        //NAVIGATION DRAWER
        headerNavigationLeft = new AccountHeader()
                .withActivity(this)
                .withCompactStyle(false)
                .withSavedInstance(savedInstanceState)
                .withThreeSmallProfileImages(true)
                .withHeaderBackground(R.drawable.pandalambendo)
                .addProfiles(
                        new ProfileDrawerItem().withName("Person One").withEmail("person1@gmail.com").withIcon(getResources().getDrawable(R.drawable.pandalambendo))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile iProfile, boolean b) {
                        Toast.makeText(My_ProductActivity.this, "onProfileChanged: " + iProfile.getName(), Toast.LENGTH_SHORT).show();
                        headerNavigationLeft.setBackgroundRes(R.drawable.camisa3);
                        return false;
                    }
                })
                .build();


        navigationDrawerLeft = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withDisplayBelowToolbar(false)
                .withActionBarDrawerToggleAnimated(true)
                .withDrawerGravity(Gravity.LEFT)
                .withSavedInstance(savedInstanceState)
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
                                Intent intent = new Intent(My_ProductActivity.this, MainActivity_.class);
                                startActivity(intent);
                                break;
                            case 1:
                                Intent intent1 = new Intent(My_ProductActivity.this, CartActivity.class);
                                startActivity(intent1);
                                break;
                            case 2:
                                Intent intent2 = new Intent(My_ProductActivity.this, My_ProductActivity.class);
                                startActivity(intent2);
                                break;
                            case 3:
                                Intent intent3 = new Intent(My_ProductActivity.this, HistoryActivity.class);
                                startActivity(intent3);
                                break;
                            case 4:
                                LoginManager.getInstance().logOut();
                                goLoginScreen();
                                break;

                        }
                        Toast.makeText(My_ProductActivity.this, "Item: " + i, Toast.LENGTH_SHORT).show();
                    }
                })
                .withOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                        Toast.makeText(My_ProductActivity.this, "onItemLongClick: " + i, Toast.LENGTH_SHORT).show();
                        return false;
                    }
                })
                .build();

        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Perfil").withIcon(getResources().getDrawable(R.mipmap.ic_launcher)));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Carrinho").withIcon(getResources().getDrawable(R.mipmap.ic_launcher)));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Meus pedidos").withIcon(getResources().getDrawable(R.mipmap.ic_launcher)));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Historico de itens").withIcon(getResources().getDrawable(R.mipmap.ic_launcher)));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Sair").withIcon(getResources().getDrawable(R.mipmap.ic_launcher)));
    }


}
