package br.unifor.retail.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.List;

import br.unifor.retail.R;
import br.unifor.retail.model.Review;
import br.unifor.retail.rest.ReviewService;
import br.unifor.retail.session.SessionManager;
import br.unifor.retail.singleton.SingletonMyProduct;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by mafra on 19/10/16.
 */

@EBean
public class AdapterListViewMyProduct extends BaseAdapter {

    @RootContext
    Context context;

    @RestService
    protected ReviewService reviewService;

    @ViewById
    protected TextView my_product_textView_nome;
    @ViewById
    protected TextView my_product_textView_preco;
    @ViewById
    protected TextView my_product_textView_quantidade;

    private Review review = new Review();

    private SessionManager manager = new SessionManager(getApplicationContext());

    private List<SingletonMyProduct> singleton_my_productLists;
    LayoutInflater inflater;
    Activity activity;

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
        inflater = LayoutInflater.from(context);
        final SingletonMyProduct singleton_my_product = singleton_my_productLists.get(position);


        if (convertView == null) {
            convertView = inflater.inflate(R.layout.iten_listview_my_product, parent, false);
        }

        TextView nome = (TextView) convertView.findViewById(R.id.my_product_textView_nome);
        TextView preco = (TextView) convertView.findViewById(R.id.my_product_textView_preco);
        TextView quantidade = (TextView) convertView.findViewById(R.id.my_product_textView_quantidade);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.my_product_imageView_Image);
        final Button button = (Button) convertView.findViewById(R.id.my_product_button_avaliar);
        final RatingBar ratingBarListView = (RatingBar) convertView.findViewById(R.id.iten_listview_my_product_RatingBar);

        nome.setText(singleton_my_product.getNome());
        preco.setText(singleton_my_product.getPreco());
        quantidade.setText(singleton_my_product.getQuantidade());

        Picasso.with(context).load(singleton_my_product.getImagem()).into(imageView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflate = inflater;
                View alertDialogLayout = inflate.inflate(R.layout.custom_dialog_product, null);
                final RatingBar ratingbarDialog = (RatingBar) alertDialogLayout.findViewById(R.id.ratingBar_Dialog_Product);
                final EditText boxText = (EditText) alertDialogLayout.findViewById(R.id.boxText_Dialog_Product);
                Drawable drawable = ratingBarListView.getProgressDrawable();
                drawable.setColorFilter(Color.parseColor("#5FD300"), PorterDuff.Mode.SRC_ATOP);


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
                alertDialogBuilder.setTitle("Avaliçao " + singleton_my_product.getNome());
                alertDialogBuilder.setView(alertDialogLayout);

                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        button.setText("Reavaliar");
                        ratingBarListView.setRating(ratingbarDialog.getRating());

                        Double nota = Double.valueOf(ratingbarDialog.getRating());

                        review.setNota(nota);
                        review.setProduto_id(singleton_my_product.getIdProduto());
                        review.setReview_descric(String.valueOf(boxText.getText()));
                        review.setCliente_id(manager.pegaUsuario().getCliente().getId());

                        criandoReview(review);
                    }
                });
                AlertDialog dialog = alertDialogBuilder.create();
                dialog.show();
            }
        });
        return convertView;
    }

    public void getDadosMyProduct(List<SingletonMyProduct> singleton_my_productList, Activity activity){
        this.singleton_my_productLists = singleton_my_productList;
        this.activity = activity;
    }


    @Background
    public void criandoReview(Review review){
        try{
            reviewService.criaReview(review);
            toasts("Review criado!");
        }catch (Exception e){
            Log.d("exception ", e.toString());

        }
    }

    @UiThread
    public void toasts(String string){
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();

    }
}
