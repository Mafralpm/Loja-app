package br.unifor.retail.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.unifor.retail.R;
import br.unifor.retail.singleton.SingletonMyProduct;

/**
 * Created by mafra on 19/10/16.
 */

public class AdapterListViewMyProduct extends BaseAdapter {
    private List<SingletonMyProduct> singleton_my_productLists;
    LayoutInflater inflater;
    Context context;
    Activity activity;

    public AdapterListViewMyProduct(List<SingletonMyProduct> singleton_my_productList, Context context, Activity activity) {
        this.singleton_my_productLists = singleton_my_productList;
        this.context = context;
        this.activity = activity;
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
        final SingletonMyProduct singleton_my_product = singleton_my_productLists.get(position);


        if (convertView == null) {
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
                LayoutInflater inflate = inflater;
                View alertDialogLayout = inflate.inflate(R.layout.custom_dialog_product, null);
                final RatingBar ratingbar = (RatingBar) alertDialogLayout.findViewById(R.id.ratingBar_Dialog_Product);
                final EditText boxText = (EditText) alertDialogLayout.findViewById(R.id.boxText_Dialog_Product);


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
                alertDialogBuilder.setTitle("Avaliçao " + singleton_my_product.getLoja());
                // this is set the view from XML inside AlertDialog
                alertDialogBuilder.setView(alertDialogLayout);

                // disallow cancel of AlertDialog on click of back button and outside touch
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("Valor AQUIII", Double.valueOf(ratingbar.getRating()).toString());
                        Log.d("Valor AQUIII", boxText.toString());
                        Toast.makeText(context, "Cancel clicked", Toast.LENGTH_SHORT).show();
//                        System.out.print(ratingbar.getRating());
                    }
                });

                alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("Valor AQUIII", Double.valueOf(ratingbar.getRating()).toString());
                        Log.d("Valor AQUIII", boxText.getText().toString());
                        Toast.makeText(context, "Comentario feito com sucesso ", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = alertDialogBuilder.create();
                dialog.show();
            }
        });


        return convertView;
    }
}
