package br.unifor.retail.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.unifor.retail.R;
import br.unifor.retail.singleton.Singleton_Car;
import br.unifor.retail.singleton.Singleton_My_Product;


public class Adapter_ListView_Car extends BaseAdapter {
    private List<Singleton_Car> singleton_cars;
    LayoutInflater inflater;
    Context context;
    Activity activity;

    public Adapter_ListView_Car(List<Singleton_Car> singleton_cars, Context context, Activity activity) {
        this.singleton_cars = singleton_cars;
        this.context = context;
        this.activity = activity;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        Singleton_Car singleton_car = singleton_cars.get(position);


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

        imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertExcluir = new AlertDialog.Builder(activity);
                alertExcluir.setMessage("Você deseja realmente excluir o item?");
                alertExcluir.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        singleton_cars.remove(position);
                        Adapter_ListView_Car.this.notifyDataSetChanged();
                    }
                });

                alertExcluir.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Toast.makeText(context, "Cancelou", Toast.LENGTH_SHORT).show();
                    }
                });

                alertExcluir.show();
            }
        });

        return convertView;
    }
}
