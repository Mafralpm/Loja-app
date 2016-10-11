package br.unifor.retail.view.activity.common;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;

import br.unifor.retail.R;

@EBean
public class DialogHelper {

    public interface dialogHelperInterface {
        void showProgressDialog(String message);
        void changeProgressDialogMessage(String message);
        void showProgressDialogCancel(String message, DialogInterface.OnCancelListener onCancelListener);
        void dismissDialog();
    }

    protected Dialog dialog;

    @RootContext
    Context context;

    @UiThread
    public void showProgressDialog(String message) {

        try {
            localDismiss();

            ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false);

            progressDialog.setTitle(context.getString(R.string.msg_aguarde));
            progressDialog.setMessage(message);
            progressDialog.setIndeterminate(true);

            dialog = progressDialog;
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @UiThread
    public void showProgressDialogCancel(String message, DialogInterface.OnCancelListener onCancelListener) {

        try {
            localDismiss();

            ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(true);

            progressDialog.setTitle(context.getString(R.string.msg_aguarde));
            progressDialog.setMessage(message);
            progressDialog.setIndeterminate(true);
            progressDialog.setOnCancelListener(onCancelListener);
            dialog = progressDialog;
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @UiThread
    public void showAlertDialog(String title, String message, DialogInterface.OnClickListener onClickListener) {

        try {
            localDismiss();

            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setCancelable(false);

            builder.setTitle(title);
            builder.setMessage(message);
            builder.setNeutralButton("ok", onClickListener);

            dialog = builder.create();
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showDialog(String title, String message) {
        try {
            localDismiss();

            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setMessage(message)
                    .setTitle(title);

            AlertDialog dialog = builder.create();
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @UiThread
    public void changeProgressDialogMessage(String message) {

        try {

            if (dialog != null && dialog instanceof ProgressDialog) {
                ProgressDialog progressDialog = (ProgressDialog) dialog;
                progressDialog.setMessage(message);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void localDismiss() {

        try {

            if (dialog != null) {
                dialog.dismiss();
                dialog = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @UiThread
    public void dismissDialog() {

        try {

            if (dialog != null) {
                dialog.dismiss();
                dialog = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Dialog getDialog() {
        return dialog;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

}
