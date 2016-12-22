package br.unifor.retail.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.web.client.ResourceAccessException;

import br.unifor.retail.R;
import br.unifor.retail.model.RecordLogin;
import br.unifor.retail.model.User;
import br.unifor.retail.rest.ClientService;
import br.unifor.retail.session.SessionManager;
import br.unifor.retail.view.activity.common.BaseActivity;

@EActivity(R.layout.activity_register_user)
public class RegisterUser extends BaseActivity {

    @RestService
    ClientService clientService;
    @ViewById
    protected EditText register_nome;
    @ViewById
    protected EditText register_email;
    @ViewById
    protected EditText register_senha;
    @ViewById
    protected EditText register_confirmar_senha;
    @ViewById
    protected Button register_cadastrar;
    @ViewById
    protected Toolbar toolbarInfo_Client;

    private RecordLogin recordLogin = new RecordLogin();

    private User user = new User();

    private Handler handler = new Handler();

    private SessionManager manager;

    private static final String ROLE_CLIENT = "client";

    @AfterViews
    public void begin() {
        manager = new SessionManager(getApplicationContext());
        toolbarInfo_Client.setTitle("Cadastro");
        toolbarInfo_Client.setBackground(getResources().getDrawable(R.drawable.canto_superior_da_tela));
        setSupportActionBar(toolbarInfo_Client);
    }

    @Background
    @Click
    void register_cadastrar() {
        try {
            recordLogin.getUser().setEmail(register_email.getText().toString().toLowerCase().trim());
            recordLogin.getUser().setPassword(register_senha.getText().toString().trim());
            recordLogin.getUser().setPassword_confirmation(register_confirmar_senha.getText().toString().trim());
            recordLogin.getUser().setRole(ROLE_CLIENT);
            recordLogin.getCliente().setNome_cliente(register_nome.getText().toString().trim());
            clientService.cadastraCliente(recordLogin);
        } catch (ResourceAccessException e) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    dialogHelper.showDialog("Problemas de internet", "Verifique a sua conexão com a internet");

                }
            });
        } catch (Exception e) {
            Log.d("LOgin", e.toString());
        }
        goMainActivity();
    }

    private void goMainActivity(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        },2000);
        try {
            user = clientService.login(recordLogin);
            recordLogin.getUser().setUser_id(user.getUser_id());
            recordLogin.getCliente().setNome_cliente(user.getNome_cliente());
            recordLogin.getCliente().setFoto(user.getFoto());
            recordLogin.getCliente().setId(user.getId());
            manager.addUser(recordLogin);
        } catch (Exception e) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    dialogHelper.showDialog("Algo deu errado", "Ocorreu algum erro no servidor, mas já estamos resolvendo");
                }
            });
        }
        Intent intent = new Intent(this, MainActivity_.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        manager.createLoginSession();
        startActivity(intent);
    }
}