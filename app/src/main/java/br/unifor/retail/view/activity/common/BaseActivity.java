package br.unifor.retail.view.activity.common;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

@EActivity
public abstract class BaseActivity extends AppCompatActivity implements DialogHelper.dialogHelperInterface{

    @Bean
    protected DialogHelper dialogHelper;

    protected boolean doubleBackToExitPressedOnce = false;
    protected boolean toastAtivo = false;
    protected View parentSnackView;

    @Override
    protected void onResume() {
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onResume();
    }

    @Override
    public void showProgressDialog(String message) {
        dialogHelper.showProgressDialog(message);
    }

    @Override
    public void dismissDialog() {
        dialogHelper.dismissDialog();
    }

    @Override
    public void changeProgressDialogMessage(String message) {
        dialogHelper.changeProgressDialogMessage(message);
    }

    @Override
    public void showProgressDialogCancel(String message, DialogInterface.OnCancelListener onCancelListener) {
        dialogHelper.showProgressDialogCancel(message, onCancelListener);
    }

}
