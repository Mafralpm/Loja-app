package br.unifor.retail.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import br.unifor.retail.R;
import br.unifor.retail.view.activity.common.BaseActivity;
import me.sudar.zxingorient.Barcode;
import me.sudar.zxingorient.ZxingOrient;
import me.sudar.zxingorient.ZxingOrientResult;

import static android.R.attr.format;


@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    private Drawer.Result navigationDrawerLeft;
    private AccountHeader.Result headerNavigationLeft;

    private Toolbar toolbar;

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
        setSupportActionBar(toolbar);

//        createNavigationDrawer();
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

    public void logout(View view) {
        LoginManager.getInstance().logOut();
        goLoginScreen();
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

    public void entrar_CarActivity (View view){
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }

    public void entrar_ClientActivity (View view){
        Intent intent = new Intent(this, ClientActivity.class);
        startActivity(intent);
    }


    private void createNavigationDrawer(Bundle savedInstanceState) {
        //NAVIGATION DRAWER
        headerNavigationLeft = new AccountHeader()
                .withActivity(this)
                .withCompactStyle(false)
                .withSavedInstance(savedInstanceState)
                .withThreeSmallProfileImages(true)
                .withHeaderBackground(R.drawable.pandalambendo)
                .addProfiles(
                        new ProfileDrawerItem().withName("Person One").withEmail("person1@gmail.com").withIcon(getResources().getDrawable(R.drawable.pandalambendo))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile iProfile, boolean b) {
                        Toast.makeText(MainActivity.this, "onProfileChanged: " + iProfile.getName(), Toast.LENGTH_SHORT).show();
                        headerNavigationLeft.setBackgroundRes(R.drawable.camisa3);
                        return false;
                    }
                })
                .build();


        navigationDrawerLeft = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withDisplayBelowToolbar(false)
                .withActionBarDrawerToggleAnimated(true)
                .withDrawerGravity(Gravity.LEFT)
                .withSavedInstance(savedInstanceState)
                .withSelectedItem(-2)
                .withActionBarDrawerToggle(true)
                .withAccountHeader(headerNavigationLeft)
                    /*.withOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
                        @Override
                        public boolean onNavigationClickListener(View view) {
                            return false;
                        }
                    })*/
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                        switch (i){
                            case 0:
                                Intent intent = new Intent(MainActivity.this, MainActivity_.class);
                                startActivity(intent);
                                break;
                            case 1:
                                Intent intent1 = new Intent(MainActivity.this, CartActivity.class);
                                startActivity(intent1);
                                break;
                            case 2:
                                Intent intent2 = new Intent(MainActivity.this, My_ProductActivity.class);
                                startActivity(intent2);
                                break;
                            case 3:
                                Intent intent3 = new Intent(MainActivity.this, HistoryActivity.class);
                                startActivity(intent3);
                                break;
                            case 4:
                                LoginManager.getInstance().logOut();
                                goLoginScreen();
                                break;

                        }
                        Toast.makeText(MainActivity.this, "Item: " + i, Toast.LENGTH_SHORT).show();
                    }
                })
                .withOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                        Toast.makeText(MainActivity.this, "onItemLongClick: " + i, Toast.LENGTH_SHORT).show();
                        return false;
                    }
                })
                .build();

        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Perfil").withIcon(getResources().getDrawable(R.mipmap.ic_launcher)));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Carrinho").withIcon(getResources().getDrawable(R.mipmap.ic_launcher)));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Meus pedidos").withIcon(getResources().getDrawable(R.mipmap.ic_launcher)));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Historico de itens").withIcon(getResources().getDrawable(R.mipmap.ic_launcher)));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Sair").withIcon(getResources().getDrawable(R.mipmap.ic_launcher)));
    }

}