package br.unifor.retail.session;

import android.content.Context;
import android.content.SharedPreferences;

import br.unifor.retail.model.RecordLogin;

/**
 * Created by vania on 14/11/16.
 */

public class SessoinManager {
    SharedPreferences preferences;

    SharedPreferences.Editor editor;

    Context context;

    private static final int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "MyPref";

    private static final String IS_LOGIN = "IsLoggedIn";

    public SessoinManager(Context context) {
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

    public boolean isLoggedIn() {
        return preferences.getBoolean(IS_LOGIN, false);
    }


    public void addUser(RecordLogin recordLogin){
        editor.putString("id", String.valueOf(recordLogin.getCliente().getId()));
        editor.putString("nome", recordLogin.getCliente().getNome_cliente());
        editor.putString("foto", recordLogin.getCliente().getFoto());
        editor.putString("email", recordLogin.getUser().getEmail());

        editor.commit();

    }
}
