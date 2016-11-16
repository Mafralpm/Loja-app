package br.unifor.retail.session;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by vania on 14/11/16.
 */

public class SessoinManager {
    SharedPreferences pref;

    SharedPreferences.Editor editor;

    Context _context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "MyPref";

    private static final String IS_LOGIN = "IsLoggedIn";


}
