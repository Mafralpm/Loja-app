package br.unifor.retail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import br.unifor.retail.R;
import br.unifor.retail.singleton.SingletonProduct;

/**
 * Created by mafra on 09/11/16.
 */

public class AdapterListViewProduct extends BaseAdapter{
    List<SingletonProduct> singletonProductList;
    Context context;
    LayoutInflater layoutInflater;

    public AdapterListViewProduct(List<SingletonProduct> singletonProductList, Context context) {
        this.singletonProductList = singletonProductList;
        this.context = context;
        layoutInflater = layoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return singletonProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return singletonProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SingletonProduct singletonProduct = singletonProductList.get(position);

        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.iten_listview_product, parent, false);
        }

        TextView nomeUsuario = (TextView) convertView.findViewById(R.id.adapter_review_usuario);
        RatingBar ratingBar = (RatingBar) convertView.findViewById(R.id.adapter_review_ratingBar);
        TextView comentario = (TextView) convertView.findViewById(R.id.adapter_review_descricao);

        nomeUsuario.setText(singletonProduct.getNomeUsuario());
        ratingBar.setRating((float) singletonProduct.getNota());
        comentario.setText(singletonProduct.getComentario());

        return convertView;
    }
}
