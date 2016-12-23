package br.unifor.retail.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.List;

import br.unifor.retail.R;
import br.unifor.retail.model.PedidoHasProduto;
import br.unifor.retail.rest.PedidoHasProdutoService;
import br.unifor.retail.rest.PedidoService;
import br.unifor.retail.singleton.SingletonCar;

@EBean
public class AdapterListViewCar extends BaseAdapter {
    private List<SingletonCar> singleton_cars;
    LayoutInflater inflater;
    Activity activity;
    long idRemove = 0;


    PedidoHasProduto pedidoHasProduto = new PedidoHasProduto();

    @RestService
    PedidoHasProdutoService pedidoHasProdutoService;

    @RestService
    PedidoService pedidoService;

    @RootContext
    Context context;

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
        inflater = LayoutInflater.from(context);
        final SingletonCar singleton_car = singleton_cars.get(position);


        if (convertView == null) {
            convertView = inflater.inflate(R.layout.iten_listview_car, parent, false);
        }

        TextView nome = (TextView) convertView.findViewById(R.id.activity_car_TextView_Nome);
        TextView preco = (TextView) convertView.findViewById(R.id.activity_car_TextView_Preco);
        ImageView imageProduct = (ImageView) convertView.findViewById(R.id.activity_car_ImageView_Image_Product);
        Button buttonDelete = (Button) convertView.findViewById(R.id.activity_car_ImageView_Image_Delete);

        nome.setText(singleton_car.getNome());
        preco.setText(singleton_car.getPreco());

        Picasso.with(context).load(singleton_car.getImageProduct()).into(imageProduct);

        Log.d("Posicao", position + "");


        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertExcluir = new AlertDialog.Builder(activity);
                alertExcluir.setMessage("Você deseja realmente excluir o item?");
                Toast.makeText(context, "Apertou " + position, Toast.LENGTH_SHORT).show();
                Log.d("000000000000", singleton_cars.size() + "");

                alertExcluir.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteaProdutosHasPedido(position);
                        deletaProdutoLis();

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

    public void getDadosCar(List<SingletonCar> singleton_cars, Activity activity) {
        this.singleton_cars = singleton_cars;
        this.activity = activity;
    }

    @Background
    public void deleteaProdutosHasPedido(int position) {
        try {
            idRemove = singleton_cars.get(position).getproduto_id();
            pedidoService.deletarPedido(singleton_cars.get(position).getPedido_id(), singleton_cars.get(position).getproduto_id());
        } catch (Exception e) {
            Log.d("Deu erro bem aqui aqui", e.toString());
        }
    }

    public void deletaProdutoLis() {
        for (int i = 0; i < singleton_cars.size(); i++) {
            if (singleton_cars.get(i).getproduto_id() == idRemove) {
                singleton_cars.remove(i);

            }
            notifyDataSetChanged();
        }
    }
}

