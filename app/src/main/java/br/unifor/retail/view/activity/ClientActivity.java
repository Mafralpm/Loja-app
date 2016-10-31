package br.unifor.retail.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.unifor.retail.R;

public class ClientActivity extends AppCompatActivity {

    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        toolbar = (Toolbar) findViewById(R.id.toolbarClientt);
        toolbar.setTitle("Perfil");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView nameView = (TextView) findViewById(R.id.cliente_nome);

        ImageView imageView = (ImageView) findViewById(R.id.profile_image);
        imageView.setImageResource(R.drawable.pandalambendo);

    }


    public void entrar_Minhas_Compras(View v){
        Intent intent = new Intent(this, My_ProductActivity.class);
        startActivity(intent);
    }

    public void entrar_Historico_Itens(View v){
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    public void entrar_Meu_Perfil(View v){
        Intent intent = new Intent(this, InfoClientActivity.class);
        startActivity(intent);
    }
}
