package br.unifor.retail.view.activity;

import android.content.Intent;
import android.os.Handler;

import com.facebook.AccessToken;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import br.unifor.retail.R;
import br.unifor.retail.session.SessionManager;
import br.unifor.retail.view.activity.common.BaseActivity;

@EActivity(R.layout.activity_splash)
public class SplashActivity extends BaseActivity implements Runnable {

    private SessionManager manager;

    @AfterViews
    protected void begin() {

        manager = new SessionManager(getApplicationContext());
        Handler handler = new Handler();
        handler.postDelayed(this, 2000);
    }

    @Override
    public void run() {
        if (AccessToken.getCurrentAccessToken() == null && !manager.isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity_.class));
        }else{
            startActivity(new Intent(this, MainActivity_.class));
        }
        finish();
    }
}
