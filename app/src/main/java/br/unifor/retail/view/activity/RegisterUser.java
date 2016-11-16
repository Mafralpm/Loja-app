package br.unifor.retail.view.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.rest.spring.annotations.RestService;

import br.unifor.retail.R;
import br.unifor.retail.model.Usuario;
import br.unifor.retail.rest.ClientService;
import br.unifor.retail.view.activity.common.BaseActivity;

@EActivity(R.layout.activity_register_user)
public class RegisterUser extends BaseActivity{

    @RestService
    ClientService clientService;

//    @ViewById
//    protected EditText register_nome;
//    @ViewById
//    protected EditText register_email;
//    @ViewById
//    protected EditText register_senha;
//    @ViewById
//    protected EditText register_confirmar_senha;


    Button button;
    Usuario usuario = new Usuario();


    private Toolbar toolbar;

    private final int ROLE = 0;

    @AfterViews
    public void begin() {
        toolbar = (Toolbar) findViewById(R.id.toolbarInfo_Client);
        toolbar.setTitle("Cadastro");
        toolbar.setBackground(getResources().getDrawable(R.drawable.canto_superior_da_tela));
        setSupportActionBar(toolbar);

        button = (Button) findViewById(R.id.RegisterUser_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity_.class);
                startActivity(intent);
            }
        });


    }

//    @Click
//    void register_botao_cadastro() {
//        setaDadosCliente();
//    }

//    @Background
//    void setaDadosCliente() {
//        MultiValueMap<String, Object> values = new LinkedMultiValueMap<>();
//
//        try {
//
//            values.add("user[email]", register_email.getText().toString());
//            values.add("user[password]", register_senha.getText().toString());
//            values.add("user[password_confirmation]", register_confirmar_senha.getText().toString());
//            values.add("user[role]", ROLE);
//            values.add("cliente[nome_cliente]", register_nome.getText().toString());
//
//            String a = String.valueOf(values.containsKey("user[email]"));
//
//            Log.i("contem a key do email",a);
//
//            String b = String.valueOf(values.containsValue(ROLE));
//
//            Log.i("contem o role",b);
//
//            Log.d("dsdsds", register_email.getText().toString());
//
//            clientService.cadastraCliente(values);
//        } catch (Exception e) {
//            Log.d("WEFEF", e.toString());
//        }
//
//    }





}
