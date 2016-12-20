package br.unifor.retail.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.UiThread;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.util.Arrays;

import br.unifor.retail.R;
import br.unifor.retail.model.RecordLogin;
import br.unifor.retail.model.User;
import br.unifor.retail.rest.ClientService;
import br.unifor.retail.session.SessionManager;
import br.unifor.retail.view.activity.common.BaseActivity;
import br.unifor.retail.view.activity.common.DialogHelper;

@EActivity(R.layout.activity_login)
public class LoginActivity extends BaseActivity {

    @RestService
    ClientService clientService;

    @ViewById(R.id.email)
    EditText email;

    @ViewById(R.id.password)
    EditText password;

    private LoginButton loginButton;
    private CallbackManager callbackManager;

    private RecordLogin recordLogin = new RecordLogin();
    private User user = new User();

    private SessionManager manager;

    private Handler handler = new Handler();

    protected DialogHelper dialogHelper = new DialogHelper();

    @AfterViews
    protected void begin() {
        manager = new SessionManager(this);

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.loginButton);
        loginButton.setReadPermissions(Arrays.asList("email", "user_friends", "user_birthday"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                goMainScreen();
                finish();
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
        manager.createLoginSession();
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    @Background
    @Click
    @UiThread
    public void email_sign_in_button() {

        if (email.getText().toString().equals("") || password.getText().toString().equals(" ")) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    dialogHelper.showDialog("Campos em branco", "Insira login e senha para entrar");
                }
            });
        } else {
            try {
                recordLogin.getUser().setEmail(email.getText().toString().toLowerCase());
                recordLogin.getUser().setPassword(password.getText().toString());
                user = clientService.login(recordLogin);
                recordLogin.getCliente().setId(user.getId());
                recordLogin.getCliente().setNome_cliente(user.getNome_cliente());
                recordLogin.getCliente().setFoto(user.getFoto());

                manager.addUser(recordLogin);

                goMainScreen();

            } catch (HttpClientErrorException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        dialogHelper.showDialog("Email ou Senha errados", "Verifique se email e senha estão corretos");

                    }
                });
            } catch (ResourceAccessException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        dialogHelper.showDialog("Problemas de internet", "Verifique a sua conexão com a internet");

                    }
                });

            } catch (Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        dialogHelper.showDialog("Algo deu errado", "Ocorreu algum erro no servidor, mas já estamos resolvendo");

                    }
                });
            }
        }
    }

    public void esqueciSenha(View v) {

        LayoutInflater inflate = getLayoutInflater();
        View alertDialogLayout = inflate.inflate(R.layout.custom_dialog_esquecisenha, null);
        final EditText esquecisenha = (EditText) alertDialogLayout.findViewById(R.id.boxText_Dialog_EsqueciSenha);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Esqueci minha senha");
        alertDialogBuilder.setMessage("Digite o endereço de email cadastrado: ");
        // this is set the view from XML inside AlertDialog
        alertDialogBuilder.setView(alertDialogLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = alertDialogBuilder.create();
        dialog.show();
    }

    public void cadastrarUsuario(View v) {
        Intent intent = new Intent(this, RegisterUser_.class);
        startActivity(intent);
    }

}