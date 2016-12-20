package br.unifor.retail.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

import br.unifor.retail.R;
import br.unifor.retail.singleton.SingletonProduct;

/**
 * Created by mafra on 09/11/16.
 */

@EBean
public class AdapterListViewProduct extends BaseAdapter{
    List<SingletonProduct> singletonProductList;
    LayoutInflater layoutInflater;

    @RootContext
    Context context;

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
        layoutInflater = layoutInflater.from(context);
        SingletonProduct singletonProduct = singletonProductList.get(position);

        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.iten_listview_product, parent, false);
        }

        RatingBar ratingBar = (RatingBar) convertView.findViewById(R.id.adapter_review_ratingBar);
        Drawable drawable = ratingBar.getProgressDrawable();
        drawable.setColorFilter(Color.parseColor("#092641"), PorterDuff.Mode.SRC_ATOP);
        TextView comentario = (TextView) convertView.findViewById(R.id.adapter_review_descricao);

        ratingBar.setRating((float) singletonProduct.getNota());
        comentario.setText(singletonProduct.getComentario());

        return convertView;
    }

    public void getDadosProduct(List<SingletonProduct> singletonProductList){
        this.singletonProductList = singletonProductList;
    }
}
