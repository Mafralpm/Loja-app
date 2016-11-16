package br.unifor.retail.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

import java.util.Arrays;

import br.unifor.retail.R;
import br.unifor.retail.rest.LoginService;
import br.unifor.retail.rest.response.UserLogin;

@EActivity(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity {


    @RestService
    LoginService loginService;

    @ViewById(R.id.email)
    EditText email;

    @ViewById(R.id.password)
    EditText password;


    private LoginButton loginButton;
    private CallbackManager callbackManager;

    @AfterViews
    protected void begin() {


        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.loginButton);

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

    @Background
    @Click
    public void email_sign_in_button (){



        loginService.login(new UserLogin(email.getText().toString(),password.getText().toString()));
        Log.i("email", email.getText().toString());
        Log.i("password", password.getText().toString());

        Intent intent = new Intent(this, MainActivity_.class);
        startActivity(intent);
    }

    public void esqueciSenha(View v){
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

    public void cadastrarUsuario (View v){
        Intent intent = new Intent(this, RegisterUser.class);
        startActivity(intent);
    }

}