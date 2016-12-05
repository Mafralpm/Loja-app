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
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.AccessToken;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.ArrayList;

import br.unifor.retail.R;
import br.unifor.retail.adapter.AdapterListViewMain;
import br.unifor.retail.model.History;
import br.unifor.retail.model.RecordLogin;
import br.unifor.retail.navegation.drawer.NavegationDrawer;
import br.unifor.retail.qr.code.QrCode;
import br.unifor.retail.rest.HistoryService;
import br.unifor.retail.session.SessoinManager;
import br.unifor.retail.singleton.SingletonMain;
import br.unifor.retail.view.activity.common.BaseActivity;
import me.sudar.zxingorient.Barcode;
import me.sudar.zxingorient.ZxingOrient;
import me.sudar.zxingorient.ZxingOrientResult;

import static android.R.attr.format;


@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    private Toolbar toolbar;
    NavegationDrawer navegationDrawer;
    boolean doubleBackToExitPressedOnce = false;

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 42;
    private Handler handler;
    private SessoinManager manager;
    private RecordLogin recordLogin;

    @RestService
    HistoryService historyService;

    private History history = new History();

    QrCode qrCode;


    @AfterViews
    public void begin() {

        manager = new SessoinManager(this);

        recordLogin = manager.pegaUsuario();

        handler = new Handler();
        if (AccessToken.getCurrentAccessToken() == null) {
        } else {
            Log.d("Permissões", AccessToken.getCurrentAccessToken().toString());
            Log.d("Token", AccessToken.getCurrentAccessToken().getToken());
        }


        toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        toolbar.setTitle("Retail");
        toolbar.setBackground(getResources().getDrawable(R.drawable.canto_superior_da_tela));
        setSupportActionBar(toolbar);

        ArrayList<SingletonMain> singleton_mains = todos_Os_Produtos();

        AdapterListViewMain adapter = new AdapterListViewMain(singleton_mains, getApplicationContext());

        ListView listView;
        listView = (ListView) findViewById(R.id.listVire_Main);

        listView.setAdapter(adapter);


        navegationDrawer = new NavegationDrawer(toolbar, MainActivity.this);
        navegationDrawer.getProfile();

        if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);
        }

        qrCode = new QrCode(this, getApplicationContext());

    }

    @Click
    public void scanQR() {
        qrCode.scanQR();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @OptionsItem(R.id.menu_carinho)
    public void carrinho() {
        Intent intent = new Intent(getApplicationContext(), PedidoActivity_.class);
        startActivity(intent);
    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_CAMERA: {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    qrCode.scanQR();
//                } else {
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            dialogHelper.showAlertDialog("Atenção", "Permita o acesso à câmera", null);
//                        }
//                    });
//                }
//            }
//        }
//    }

    private void goLoginScreen() {
        Intent intent = new Intent(this, LoginActivity_.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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

                Log.d("èo id ?", contents);
                manager.setIdProduto(Long.valueOf(contents));
                enviaProHistorico();
                startActivity(intentResult);


            }
        } catch (RuntimeException e) {
            Log.d("Deu nessa xibata", e.toString());
        } catch (Exception e) {
            Log.d("DEU ERRO AQUI CARALHO", e.toString());
        }

    }

    @Background
    public void enviaProHistorico(){
        setaDadosHistorico();
        historyService.cria(history);
    }

    public void setaDadosHistorico(){
        history.setCliente_id(manager.pegaUsuario().getCliente().getId());
        Log.d("CLIENTE ID", history.getCliente_id().toString());
        history.setProduto_id(manager.getIdProduto());
        Log.d("PRODUTO ID", history.getProduto_id().toString());
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Pressione novamente para sair", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


    public ArrayList<SingletonMain> todos_Os_Produtos() {
        ArrayList<SingletonMain> singleton_mains = new ArrayList<>();


        singleton_mains.add(new SingletonMain(R.drawable.imagem_blusa_laranja, R.drawable.imagem_blusa_star_wars, "R$ 200,00", "Camisa social Masc.", "R$ 337,99", "Camisa social Feme."));



        return singleton_mains;
    }


}