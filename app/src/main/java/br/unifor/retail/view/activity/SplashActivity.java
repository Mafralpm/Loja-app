package br.unifor.retail.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.facebook.AccessToken;

import org.androidannotations.annotations.EActivity;

import br.unifor.retail.R;
import br.unifor.retail.view.activity.common.BaseActivity;

@EActivity(R.layout.activity_splash)
public class SplashActivity extends BaseActivity implements Runnable {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handler = new Handler();
        handler.postDelayed(this, 1);
    }

    @Override
    public void run() {
        if (AccessToken.getCurrentAccessToken() == null) {
            startActivity(new Intent(this, LoginActivity_.class));
        }else{
            startActivity(new Intent(this, MainActivity_.class));
        }
        finish();
    }
}
