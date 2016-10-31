package br.unifor.retail.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.unifor.retail.R;
import br.unifor.retail.singleton.Singleton_Car;
import br.unifor.retail.singleton.Singleton_My_Product;

/**
 * Created by mafra on 19/10/16.
 */

public class Adapter_ListView_Car extends BaseAdapter {
    private List<Singleton_Car> singleton_cars;
    LayoutInflater inflater;
    Context context;

    public Adapter_ListView_Car(List<Singleton_Car> singleton_cars, Context context) {
        this.singleton_cars = singleton_cars;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return singleton_cars.size();
    }

    @Override
    public Object getItem(int position) {
        return singleton_cars.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Singleton_Car singleton_car = singleton_cars.get(position);

        Log.d("HUAGDUAGSUODYGSAUYOGDYUGSAUDGUAS", singleton_car.getFlag() + "");

        if (singleton_car.getFlag() != 1) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.iten_listview_car, parent, false);
            }

            TextView nome = (TextView) convertView.findViewById(R.id.activity_car_TextView_Nome);
            TextView decricao = (TextView) convertView.findViewById(R.id.activity_car_TextView_Descricao);
            TextView cor = (TextView) convertView.findViewById(R.id.activity_car_TextView_Cor);
            ImageView imageProduct = (ImageView) convertView.findViewById(R.id.activity_car_ImageView_Image_Product);
            ImageView imageDelete = (ImageView) convertView.findViewById(R.id.activity_car_ImageView_Image_Delete);

            nome.setText(singleton_car.getNome());
            decricao.setText(singleton_car.getDecricao());
            cor.setText(singleton_car.getCor());
            imageProduct.setImageResource(singleton_car.getImageProduct());
            imageDelete.setImageResource(R.mipmap.ic_launcher);
        }
        return convertView;
    }
}
