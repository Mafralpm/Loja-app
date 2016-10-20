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

import java.util.ArrayList;
import java.util.List;

import br.unifor.retail.R;
import br.unifor.retail.singleton.Singleton_My_Product;
import br.unifor.retail.view.activity.LoginActivity;

/**
 * Created by mafra on 19/10/16.
 */

public class Adapter_ListView_My_Product extends BaseAdapter{
    private List<Singleton_My_Product> singleton_my_productLists;
    LayoutInflater inflater;
    Context context;

    public Adapter_ListView_My_Product(List<Singleton_My_Product> singleton_my_productList, Context context) {
        this.singleton_my_productLists = singleton_my_productList;
        this.context = context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        //View view =inflater.inflate(R.layout.iten_listview_my_product, parent, true);

        Singleton_My_Product singleton_my_product = singleton_my_productLists.get(position);

        if(convertView == null){
            convertView = inflater.inflate(R.layout.iten_listview_my_product, parent, false);
        }

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
                Intent intent = new Intent(v.getContext(), LoginActivity.class);


                v.getContext().startActivity(intent);

            }
        });



        return convertView;
    }
}
