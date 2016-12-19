package br.unifor.retail.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

import br.unifor.retail.R;
import br.unifor.retail.singleton.SingletonHistory;
import br.unifor.retail.singleton.SingletonMyProduct;

/**
 * Created by mafra on 19/10/16.
 */

@EBean
public class AdapterListViewHistory extends BaseAdapter {
    private List<SingletonHistory> singleton_history;
    LayoutInflater inflater;

    @RootContext
    Context context;

    @Override
    public int getCount() {
        return singleton_history.size();
    }

    @Override
    public Object getItem(int position) {
        return singleton_history.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        inflater = LayoutInflater.from(context);
        SingletonHistory singletonHistory = singleton_history.get(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.iten_listview_history, parent, false);
        }

        TextView nome = (TextView) convertView.findViewById(R.id.iten_listview_history_textView_Nome);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.iten_listview_history_imageView_Image);
        TextView preco = (TextView) convertView.findViewById(R.id.iten_listview_history_TextView_Preco);


        nome.setText(singletonHistory.getNome());
        preco.setText(singletonHistory.getPreco() + "");

        Log.i("URLLL", singletonHistory.getImagem() + "" + singletonHistory.getNome());
        Picasso.with(context).load(singletonHistory.getImagem()).into(imageView);


        return convertView;
    }

    public void getDadosHistory(List<SingletonHistory> historyList){
        this.singleton_history = historyList;
    }
}
