package br.unifor.retail.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by vania on 11/11/16.
 */

public class Utils {

    public static boolean isOnline(Context context){

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();

        if(info == null){
            return false;
        } else if (!info.isConnected()){
            return false;
        } else if (!info.isAvailable()){
            return false;
        }
        return true;
    }
}
