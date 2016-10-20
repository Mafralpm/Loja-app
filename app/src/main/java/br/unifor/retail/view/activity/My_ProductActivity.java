package br.unifor.retail.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

import br.unifor.retail.R;
import br.unifor.retail.adapter.Adapter_ListView_My_Product;
import br.unifor.retail.singleton.Singleton_My_Product;

/**
 * Created by mafra on 19/10/16.
 */

public class My_ProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_prodcuct);
        final int BUTTON_MYPRODCT = 0;

        ArrayList<Singleton_My_Product> singleton_my_products = todos_Os_Produtos();

        Adapter_ListView_My_Product adapter = new Adapter_ListView_My_Product(singleton_my_products, getApplicationContext(), BUTTON_MYPRODCT);

        ListView listView;
        listView = (ListView) findViewById(R.id.myproduct);

        listView.setAdapter(adapter);



    }

    public ArrayList<Singleton_My_Product> todos_Os_Produtos(){
        ArrayList<Singleton_My_Product> singleton_my_products = new ArrayList<>();

        singleton_my_products.add(new Singleton_My_Product(R.drawable.pandalambendo, "Americanas", "Blusa preta", "10/04/2016"));
        singleton_my_products.add(new Singleton_My_Product(R.drawable.felipemassapilotof1, "Americanas", "Blusa branca", "10/04/2016"));
        singleton_my_products.add(new Singleton_My_Product(R.drawable.pandalambendo, "Americanas", "Blusa verde", "10/04/2016"));

        return singleton_my_products;
    }


}
