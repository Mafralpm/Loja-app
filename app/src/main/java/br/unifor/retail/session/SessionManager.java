package br.unifor.retail.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import br.unifor.retail.model.RecordLogin;

/**
 * Created by vania on 14/11/16.
 */

public class SessionManager {
    SharedPreferences preferences;

    SharedPreferences.Editor editor;

    Context context;

    private static final int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "MyPref";

    private static final String IS_LOGIN = "IsLoggedIn";

    public SessionManager(Context context) {
        this.preferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        this.editor = preferences.edit();
        this.context = context;
    }

    public void createLoginSession(){
        editor.putBoolean(IS_LOGIN, true);
        editor.commit();
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();
    }

    public void logoutFacebook(){
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn() {
        return preferences.getBoolean(IS_LOGIN, false);
    }

    public void addUser(RecordLogin recordLogin){
        editor.putLong("id", recordLogin.getCliente().getId());
        editor.putString("nome", recordLogin.getCliente().getNome_cliente());
        editor.putString("foto", recordLogin.getCliente().getFoto());
        editor.putString("email", recordLogin.getUser().getEmail());
        editor.commit();
    }

    public RecordLogin pegaUsuario(){
        RecordLogin recordLogin = new RecordLogin();
        recordLogin.getCliente().setId(preferences.getLong("id", 0));
        recordLogin.getCliente().setNome_cliente(preferences.getString("nome", ""));
        recordLogin.getCliente().setFoto(preferences.getString("foto", ""));
        recordLogin.getUser().setEmail(preferences.getString("email", ""));
        Log.d("NOME", recordLogin.getCliente().getNome_cliente());
        Log.d("FOTO", recordLogin.getCliente().getFoto());
        Log.d("EMAIL", recordLogin.getUser().getEmail());
        Log.d("ID", recordLogin.getCliente().getId().toString());

        return  recordLogin;
    }

    public long getIdProduto(){
        return preferences.getLong("idProduto", 0);
    }

    public void setIdProduto(Long idProduto){
        editor.putLong("idProduto", idProduto);
        editor.commit();
    }

    public long getIdCarrinho(){
        return preferences.getLong("idCarrinho", 0);
    }

    public void setIdCarrinho(Long idCarrinho){
        editor.putLong("idCarrinho", idCarrinho);
        editor.commit();
    }

    public Boolean getFinalizado(){
        return preferences.getBoolean("finalizado", false);
    }

    public void setFinalizado(){
        editor.putBoolean("finalizado", true);
        editor.commit();
    }

    public RecordLogin pegaFacebook(){
        RecordLogin recordLogin = new RecordLogin();
        recordLogin.getCliente().setNome_cliente(preferences.getString("nome", ""));
        recordLogin.getCliente().setFoto(preferences.getString("foto", ""));
        recordLogin.getUser().setEmail(preferences.getString("email", ""));
        recordLogin.getUser().setFacebook_token(preferences.getString("token", ""));
        return  recordLogin;
    }

    public void addFacebook(RecordLogin recordLogin){
        editor.putString("nome", recordLogin.getCliente().getNome_cliente());
        editor.putString("foto", recordLogin.getCliente().getFoto());
        editor.putString("email", recordLogin.getUser().getEmail());
        editor.putString("token", recordLogin.getUser().getFacebook_token());
        editor.commit();
    }


}



