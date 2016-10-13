package br.unifor.retail.view.activity;

import android.content.Intent;
import android.os.Handler;

import org.androidannotations.annotations.EActivity;

import br.unifor.retail.R;
import br.unifor.retail.view.activity.common.BaseActivity;

@EActivity(R.layout.activity_splash)
public class SplashActivity extends BaseActivity implements Runnable {

    protected void onResume() {
        super.onResume();

        Handler handler = new Handler();
        handler.postDelayed(this, 2000);

    }


    @Override
    public void run() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
