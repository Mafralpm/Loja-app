package br.unifor.retail.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.facebook.AccessToken;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import java.util.ArrayList;

import br.unifor.retail.R;
import br.unifor.retail.adapter.Adapter_ListView_Main;
import br.unifor.retail.navegation.drawer.NavegationDrawer;
import br.unifor.retail.singleton.Singleton_Main;
import br.unifor.retail.view.activity.common.BaseActivity;
import me.sudar.zxingorient.Barcode;
import me.sudar.zxingorient.ZxingOrient;
import me.sudar.zxingorient.ZxingOrientResult;

import static android.R.attr.format;


@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    private Toolbar toolbar;
    NavegationDrawer navegationDrawer;

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 42;
    private Handler handler;


    @AfterViews
    public void begin(){
        handler = new Handler();
        if (AccessToken.getCurrentAccessToken() == null){
            goLoginScreen();
        }else{
            Log.d("Permissões", AccessToken.getCurrentAccessToken().toString());
            Log.d("Token", AccessToken.getCurrentAccessToken().getToken());
        }

        toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        toolbar.setTitle("Retail");
        toolbar.setBackground(getResources().getDrawable(R.drawable.canto_superior_da_tela));
        setSupportActionBar(toolbar);

        ArrayList<Singleton_Main> singleton_mains = todos_Os_Produtos();

        Adapter_ListView_Main adapter = new Adapter_ListView_Main(singleton_mains, getApplicationContext());

        ListView listView;
        listView = (ListView) findViewById(R.id.listVire_Main);

        listView.setAdapter(adapter);

        handler.post(new Runnable() {
            @Override
            public void run() {
                navegationDrawer = new NavegationDrawer(toolbar, MainActivity.this);
                navegationDrawer.getProfile();
                navegationDrawer.createNavigationDrawer();
            }
        });
    }

    @Click
    public void scanQR(){
        if (!(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);
        } else {
            scanBarcode();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        Intent intent = new Intent(getApplicationContext(), CartActivity.class);
        Intent intent2 = new Intent(getApplicationContext(), MainActivity_.class);
        if (menuItem.getItemId() == R.id.menu_carinho) {
            startActivity(intent);
        } else {
            startActivity(intent2);
        }

        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    scanBarcode();
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            dialogHelper.showAlertDialog("Atenção", "Permita o acesso à câmera", null);
                        }
                    });
                }
            }
        }
    }

    private void goLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    @UiThread
    public void scanBarcode() {
        ZxingOrient integrator = new ZxingOrient(this);
        integrator
                .setToolbarColor("#AA000000")
                .showInfoBox(false)
                .setBeep(false)
                .setVibration(true)
                .initiateScan(Barcode.QR_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        ZxingOrientResult scanResult =
                ZxingOrient.parseActivityResult(requestCode, resultCode, intent);
        try {
            if (scanResult != null) {
              //  String leitura = scanResult.getContents();
                String contents = intent.getStringExtra("SCAN_RESULT");
                Intent intentResult = new Intent(this, ProductActivity_.class);
                intentResult
                            .putExtra("contents", contents)
                            .putExtra("format", format);
                startActivity(intentResult);
            }
        } catch (RuntimeException e) {

        }

    }


    public ArrayList<Singleton_Main> todos_Os_Produtos() {
        ArrayList<Singleton_Main> singleton_mains = new ArrayList<>();



        singleton_mains.add(new Singleton_Main(R.drawable.camisa1, R.drawable.camisa2, "R$ 20,00", "Camisa social Masc.", "R$ 1000,00", "Camisa social Feme."));
        singleton_mains.add(new Singleton_Main(R.drawable.camisa1, R.drawable.camisa2, "R$ 20,00", "Camisa social Masc.", "R$ 1000,00", "Camisa social Feme."));
        singleton_mains.add(new Singleton_Main(R.drawable.camisa1, R.drawable.camisa2, "R$ 20,00", "Camisa social Masc.", "R$ 1000,00", "Camisa social Feme."));
        singleton_mains.add(new Singleton_Main(R.drawable.camisa1, R.drawable.camisa2, "R$ 20,00", "Camisa social Masc.", "R$ 1000,00", "Camisa social Feme."));
        singleton_mains.add(new Singleton_Main(R.drawable.camisa1, R.drawable.camisa2, "R$ 20,00", "Camisa social Masc.", "R$ 1000,00", "Camisa social Feme."));
        singleton_mains.add(new Singleton_Main(R.drawable.camisa1, R.drawable.camisa2, "R$ 20,00", "Camisa social Masc.", "R$ 1000,00", "Camisa social Feme."));
        singleton_mains.add(new Singleton_Main(R.drawable.camisa1, R.drawable.camisa2, "R$ 20,00", "Camisa social Masc.", "R$ 1000,00", "Camisa social Feme."));
        singleton_mains.add(new Singleton_Main(R.drawable.camisa1, R.drawable.camisa2, "R$ 20,00", "Camisa social Masc.", "R$ 1000,00", "Camisa social Feme."));
        singleton_mains.add(new Singleton_Main(R.drawable.camisa1, R.drawable.camisa2, "R$ 20,00", "Camisa social Masc.", "R$ 1000,00", "Camisa social Feme."));
        singleton_mains.add(new Singleton_Main(R.drawable.camisa1, R.drawable.camisa2, "R$ 20,00", "Camisa social Masc.", "R$ 1000,00", "Camisa social Feme."));
        singleton_mains.add(new Singleton_Main(R.drawable.camisa1, R.drawable.camisa2, "R$ 20,00", "Camisa social Masc.", "R$ 1000,00", "Camisa social Feme."));
        singleton_mains.add(new Singleton_Main(R.drawable.camisa1, R.drawable.camisa2, "R$ 20,00", "Camisa social Masc.", "R$ 1000,00", "Camisa social Feme."));
        singleton_mains.add(new Singleton_Main(R.drawable.camisa1, R.drawable.camisa2, "R$ 20,00", "Camisa social Masc.", "R$ 1000,00", "Camisa social Feme."));
        singleton_mains.add(new Singleton_Main(R.drawable.camisa1, R.drawable.camisa2, "R$ 20,00", "Camisa social Masc.", "R$ 1000,00", "Camisa social Feme."));
        singleton_mains.add(new Singleton_Main(R.drawable.camisa1, R.drawable.camisa2, "R$ 20,00", "Camisa social Masc.", "R$ 1000,00", "Camisa social Feme."));
        singleton_mains.add(new Singleton_Main(R.drawable.camisa1, R.drawable.camisa2, "R$ 20,00", "Camisa social Masc.", "R$ 1000,00", "Camisa social Feme."));
        singleton_mains.add(new Singleton_Main(R.drawable.camisa1, R.drawable.camisa2, "R$ 20,00", "Camisa social Masc.", "R$ 1000,00", "Camisa social Feme."));
        singleton_mains.add(new Singleton_Main(R.drawable.camisa1, R.drawable.camisa2, "R$ 20,00", "Camisa social Masc.", "R$ 1000,00", "Camisa social Feme."));
        singleton_mains.add(new Singleton_Main(R.drawable.camisa1, R.drawable.camisa2, "R$ 20,00", "Camisa social Masc.", "R$ 1000,00", "Camisa social Feme."));
        singleton_mains.add(new Singleton_Main(R.drawable.camisa1, R.drawable.camisa2, "R$ 20,00", "Camisa social Masc.", "R$ 1000,00", "Camisa social Feme."));
        singleton_mains.add(new Singleton_Main(R.drawable.camisa1, R.drawable.camisa2, "R$ 20,00", "Camisa social Masc.", "R$ 1000,00", "Camisa social Feme."));






        return singleton_mains;
    }


}