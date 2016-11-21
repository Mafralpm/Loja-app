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

import java.util.List;

import br.unifor.retail.R;
import br.unifor.retail.singleton.SingletonHistory;

/**
 * Created by mafra on 19/10/16.
 */

public class AdapterListViewHistory extends BaseAdapter {
    private List<SingletonHistory> singleton_history;
    LayoutInflater inflater;
    Context context;
    int teste;

    public AdapterListViewHistory(List<SingletonHistory> singleton_history, Context context) {
        this.singleton_history = singleton_history;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

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
        SingletonHistory singletonHistory = singleton_history.get(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.iten_listview_history, parent, false);
        }

        TextView nome = (TextView) convertView.findViewById(R.id.iten_listview_history_textView_Nome);
//        TextView data = (TextView) convertView.findViewById(R.id.iten_listview_history_textView_Data);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.iten_listview_history_imageView_Image);
        TextView preco = (TextView) convertView.findViewById(R.id.iten_listview_history_TextView_Preco);


        nome.setText(singletonHistory.getNome());
//        data.setText(singletonHistory.getData());
//        imageView.setImageResource(Integer.parseInt(String.valueOf(singletonHistory.getImagem())));
        preco.setText(singletonHistory.getPreco()+"");

        Log.i("URLLL", singletonHistory.getImagem()+"" + singletonHistory.getNome());
        Picasso.with(context).load(singletonHistory.getImagem()).into(imageView);


        return convertView;
    }
}
