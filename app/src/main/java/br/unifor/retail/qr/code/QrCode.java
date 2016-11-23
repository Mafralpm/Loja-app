package br.unifor.retail.qr.code;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import br.unifor.retail.model.History;
import br.unifor.retail.rest.HistoryService;
import br.unifor.retail.session.SessoinManager;
import br.unifor.retail.view.activity.ProductActivity_;
import br.unifor.retail.view.activity.common.DialogHelper;
import me.sudar.zxingorient.Barcode;
import me.sudar.zxingorient.ZxingOrient;
import me.sudar.zxingorient.ZxingOrientResult;

import static android.R.attr.format;

/**
 * Created by mafra on 22/11/16.
 */

public class QrCode {
    private Activity activity;
    private Context context;


    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 42;

    public QrCode(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
    }

    public void scanQR() {
        if (!(ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);
        } else {
            scanBarcode();
        }
    }

    public void scanBarcode() {
        ZxingOrient integrator = new ZxingOrient(activity);
        integrator
                .setToolbarColor("#AA000000")
                .showInfoBox(false)
                .setBeep(false)
                .setVibration(true)
                .initiateScan(Barcode.QR_CODE);
    }
}
