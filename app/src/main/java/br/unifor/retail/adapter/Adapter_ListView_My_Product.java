package br.unifor.retail.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.unifor.retail.R;
import br.unifor.retail.singleton.Singleton_My_Product;
import br.unifor.retail.view.activity.LoginActivity;

/**
 * Created by mafra on 19/10/16.
 */

public class Adapter_ListView_My_Product extends BaseAdapter {
    private List<Singleton_My_Product> singleton_my_productLists;
    LayoutInflater inflater;
    Context context;
    int teste;

    public Adapter_ListView_My_Product(List<Singleton_My_Product> singleton_my_productList, Context context, int teste) {
        this.singleton_my_productLists = singleton_my_productList;
        this.context = context;
        this.teste = teste;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return singleton_my_productLists.size();
    }

    @Override
    public Object getItem(int position) {
        return singleton_my_productLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Singleton_My_Product singleton_my_product = singleton_my_productLists.get(position);

        if (teste == 0) {

            if (singleton_my_product.getFlag() != 1) {
                convertView = inflater.inflate(R.layout.iten_listview_my_product, parent, false);

                TextView loja = (TextView) convertView.findViewById(R.id.my_product_textView_Loja);
                TextView produto = (TextView) convertView.findViewById(R.id.my_product_textView_Produto);
                TextView data = (TextView) convertView.findViewById(R.id.my_product_textView_Data);
                ImageView imageView = (ImageView) convertView.findViewById(R.id.my_product_imageView_Image);
                Button button = (Button) convertView.findViewById(R.id.my_product_button_avaliar);

                loja.setText(singleton_my_product.getLoja());
                produto.setText(singleton_my_product.getProduto());
                data.setText(singleton_my_product.getData());
                imageView.setImageResource(singleton_my_product.getImage());

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "Clique no botão: "+ position, Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                convertView = inflater.inflate(R.layout.iten_listview_my_product_final, parent, false);
                TextView subtotal = (TextView) convertView.findViewById(R.id.my_product_textView_subtotal);
//                TextView valor = (TextView) convertView.findViewById(R.id.my_product_textView_valor);

                subtotal.setText("Total: R$ 1000,00");
//                valor.setText("1000000,0");
            }

        } else {

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.iten_listview_history, parent, false);
            }

            TextView loja = (TextView) convertView.findViewById(R.id.my_product_textView_Loja);
            TextView produto = (TextView) convertView.findViewById(R.id.my_product_textView_Produto);
            TextView data = (TextView) convertView.findViewById(R.id.my_product_textView_Data);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.my_product_imageView_Image);
//            Button button = (Button) convertView.findViewById(R.id.my_product_button_avaliar);
//
//            button.setVisibility(View.GONE);
            loja.setText(singleton_my_product.getLoja());
            produto.setText(singleton_my_product.getProduto());
            data.setText(singleton_my_product.getData());
            imageView.setImageResource(singleton_my_product.getImage());
        }


        return convertView;
    }
}
