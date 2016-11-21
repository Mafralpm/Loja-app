package br.unifor.retail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import br.unifor.retail.R;
import br.unifor.retail.singleton.SingletonMain;

/**
 * Created by mafra on 19/10/16.
 */

public class AdapterListViewMain extends BaseAdapter {
    private List<SingletonMain> singleton_mainList;
    LayoutInflater inflater;
    Context context;

    public AdapterListViewMain(List<SingletonMain> singleton_mainList, Context context) {
        this.singleton_mainList = singleton_mainList;
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return singleton_mainList.size();
    }

    @Override
    public Object getItem(int position) {
        return singleton_mainList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        SingletonMain singleton_main = singleton_mainList.get(position);


        if (convertView == null) {
            convertView = inflater.inflate(R.layout.iten_listview_main, parent, false);
        }

        RelativeLayout relativeLayoutEsquerda = (RelativeLayout) convertView.findViewById(R.id.layout_Esquerda_ListView_Main);
        TextView produtoEsquerda = (TextView) convertView.findViewById(R.id.textView_produtoEsquerda_ListView_Main);
        TextView precoEsquerda = (TextView) convertView.findViewById(R.id.textView_precoEsquerda_ListView_Main);
        ImageView imagemEsquerda = (ImageView) convertView.findViewById(R.id.imageView_Esquerda_ListView_Main);

        RelativeLayout relativeLayoutDireita = (RelativeLayout) convertView.findViewById(R.id.layout_Direita_ListView_Main);
        TextView produtoDireita = (TextView) convertView.findViewById(R.id.textView_produtoDireita_ListView_Main);
        TextView precoDireita = (TextView) convertView.findViewById(R.id.textView_precoDireita_ListView_Main);
        ImageView imagemDireita = (ImageView) convertView.findViewById(R.id.imageView_Direita_ListView_Main);


        produtoEsquerda.setText(singleton_main.getProdutoEsquerda());
        precoEsquerda.setText(singleton_main.getPrecoEsquerda());
        imagemEsquerda.setImageResource(singleton_main.getImagemEsquerda());

        produtoDireita.setText(singleton_main.getProdutoDireita());
        precoDireita.setText(singleton_main.getPrecoDireita());
        imagemDireita.setImageResource(singleton_main.getImagemDireita());


        relativeLayoutEsquerda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(context, "Clique na imagem da Esquerda: " + position, Toast.LENGTH_SHORT).show();
            }
        });

        relativeLayoutDireita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    Toast.makeText(context, "Clique na imagem da Direita: " + position, Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
}
