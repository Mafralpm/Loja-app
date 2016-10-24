package br.unifor.retail.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.unifor.retail.R;
import br.unifor.retail.adapter.Adapter_ListView_Car;
import br.unifor.retail.singleton.Singleton_Car;


public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        List<Singleton_Car> singleton_cars;
        singleton_cars = todos_os_produtos();

        Adapter_ListView_Car adapter_listView_car = new Adapter_ListView_Car(singleton_cars, getApplicationContext());

        ListView listView = (ListView) findViewById(R.id.car_activity_listView);
        listView.setAdapter(adapter_listView_car);
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




}
