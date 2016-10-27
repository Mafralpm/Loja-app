package br.unifor.retail.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import br.unifor.retail.R;
import br.unifor.retail.adapter.Adapter_ListView_My_Product;
import br.unifor.retail.singleton.Singleton_My_Product;

public class HistoryActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        toolbar = (Toolbar) findViewById(R.id.toolbarHistory);
        toolbar.setTitle("Historico");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final int BUTTON_HITORY = 1;

        ArrayList<Singleton_My_Product> singleton_my_products;
        singleton_my_products = todos_Os_Produtos();

        Adapter_ListView_My_Product adapter_listView_my_product = new Adapter_ListView_My_Product(singleton_my_products, getApplicationContext(), BUTTON_HITORY);

        ListView listView = (ListView) findViewById(R.id.action_history_ListView);
        listView.setAdapter(adapter_listView_my_product);

    }

    public ArrayList<Singleton_My_Product> todos_Os_Produtos(){
        ArrayList<Singleton_My_Product> singleton_my_products = new ArrayList<>();

        singleton_my_products.add(new Singleton_My_Product(R.drawable.pandalambendo, "Americanas", "Blusa preta", "10/04/2016"));
        singleton_my_products.add(new Singleton_My_Product(R.drawable.felipemassapilotof1, "Americanas", "Blusa branca", "10/04/2016"));
        singleton_my_products.add(new Singleton_My_Product(R.drawable.pandalambendo, "Americanas", "Blusa verde", "10/04/2016"));
        singleton_my_products.add(new Singleton_My_Product(R.drawable.pandalambendo, "Submarino", "Camisa", "30/04/2016"));
        singleton_my_products.add(new Singleton_My_Product(R.drawable.felipemassapilotof1, "Teste", "Bermuda", "05/05/2015"));
        singleton_my_products.add(new Singleton_My_Product(R.drawable.pandalambendo, "Americanas", "Blusa laranja", "10/04/2016"));

        return singleton_my_products;
    }
}
