package br.unifor.retail.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.ArrayList;

import br.unifor.retail.R;
import br.unifor.retail.adapter.Adapter_ListView_My_Product;
import br.unifor.retail.singleton.Singleton_My_Product;

/**
 * Created by mafra on 19/10/16.
 */

public class My_ProductActivity extends AppCompatActivity {

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


}
