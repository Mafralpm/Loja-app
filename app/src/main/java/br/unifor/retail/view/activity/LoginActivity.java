
package br.unifor.retail.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.Arrays;
import java.util.List;

import br.unifor.retail.R;
import br.unifor.retail.rest.ClientService;
import br.unifor.retail.model.response.ResponseClient;


@EActivity(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity {

    @ViewById
    protected LoginButton loginButton;

    private CallbackManager callbackManager;

    @ViewById
    EditText login_password;
    @ViewById
    AutoCompleteTextView login_email;

    @RestService
    ClientService clientService;

    List<ResponseClient> listaClients;

    @AfterViews
    protected void begin() {

        callbackManager = CallbackManager.Factory.create();

        loginButton.setReadPermissions(Arrays.asList("email", "user_friends", "user_birthday"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                goMainScreen();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), R.string.cancel_login, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), R.string.error_login, Toast.LENGTH_SHORT).show();
                Log.i("Erro", error.toString());
            }
        });
    }

    private void goMainScreen() {
        Intent intent = new Intent(this, MainActivity_.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }
    @Click
    public void logar (View v){
        buscarCliente();

    }

    public void esqueciSenha(View v){
        LayoutInflater inflate = getLayoutInflater();
        View alertDialogLayout = inflate.inflate(R.layout.custom_dialog_esquecisenha, null);
        final EditText esquecisenha = (EditText) alertDialogLayout.findViewById(R.id.boxText_Dialog_EsqueciSenha);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(alertDialogLayout);

        // disallow cancel of AlertDialog on click of back button and outside touch
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(getApplicationContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
//                        System.out.print(ratingbar.getRating());
            }
        });

        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(getApplicationContext(), "Comentario feito com sucesso ", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = alertDialogBuilder.create();
        dialog.show();
    }

    public void cadastrarUsuario (View v) {
        Intent intent = new Intent(this, RegisterUser_.class);
        startActivity(intent);
    }

    @Background
    public void buscarCliente(){
        listaClients = clientService.getClients();

        String login = this.login_email.toString();
        String senha = this.login_password.toString();

        if(login.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Campo nao preenchido", Toast.LENGTH_SHORT).show();
        }else{
            boolean check = false;

//            for(ResponseClient client : listaClients){
//                if(login == client.) {
//                    check = true;
//                    if (senha == client.getPassword()) {
//                        Toast.makeText(this, "olá, " + client.getNomeClient(), Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(this, MainActivity_.class);
//                        startActivity(intent);
//                        break;
//                    } else {
//                        Toast.makeText(this, "Senha inválida !!!", Toast.LENGTH_SHORT).show();
//                        break;
//                    }
//                }
//            }

            if (!check){
                Toast.makeText(this, "Login Invalido !!!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

