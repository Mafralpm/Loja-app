package br.unifor.retail.view.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.AccessToken;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.ArrayList;

import br.unifor.retail.R;
import br.unifor.retail.adapter.AdapterListViewMain;
import br.unifor.retail.model.History;
import br.unifor.retail.model.RecordLogin;
import br.unifor.retail.model.User;
import br.unifor.retail.navegation.drawer.NavegationDrawer;
import br.unifor.retail.qr.code.QrCode;
import br.unifor.retail.rest.FacebookService;
import br.unifor.retail.rest.HistoryService;
import br.unifor.retail.session.SessionManager;
import br.unifor.retail.singleton.SingletonMain;
import br.unifor.retail.view.activity.common.BaseActivity;
import me.sudar.zxingorient.ZxingOrient;
import me.sudar.zxingorient.ZxingOrientResult;

import static android.R.attr.format;


@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewById
    protected Toolbar toolbarMain;
    @ViewById
    protected ListView listVire_Main;

    @RestService
    protected HistoryService historyService;
    @RestService
    protected FacebookService facebookService;

    @Bean
    AdapterListViewMain adapter;

    private NavegationDrawer navegationDrawer;
    boolean doubleBackToExitPressedOnce = false;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 42;
    private Handler handler;

    private SessionManager manager;

    private RecordLogin recordLogin;

    private History history = new History();

    private QrCode qrCode;

    private User user = new User();
    ArrayList<SingletonMain> singleton_mains;

    @AfterViews
    public void begin() {
        showProgressDialog("Buscando promoções");

        manager = new SessionManager(this);
        handler = new Handler();
        qrCode = new QrCode(this, getApplicationContext());

        if (AccessToken.getCurrentAccessToken() != null) {
            Log.d("Permissões", AccessToken.getCurrentAccessToken().toString());
            Log.d("Token", AccessToken.getCurrentAccessToken().getToken());
            recordLogin = manager.pegaFacebook();
        }else{
            recordLogin = manager.pegaUsuario();
        }

        toolbarMain.setTitle("Retail");
        toolbarMain.setBackground(getResources().getDrawable(R.drawable.canto_superior_da_tela));
        setSupportActionBar(toolbarMain);

        singleton_mains = new ArrayList<>();
        todos_Os_Produtos();

        navegationDrawer = new NavegationDrawer(toolbarMain, MainActivity.this);
        navegationDrawer.getProfile();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialogHelper.dismissDialog();
                if (AccessToken.getCurrentAccessToken() != null) {
                    buscaFacebook();
                }
            }
        }, 3000);
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
        Intent intent = new Intent(getApplicationContext(), CarrinhoActivity_.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    qrCode.scanQR();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        ZxingOrientResult scanResult =
                ZxingOrient.parseActivityResult(requestCode, resultCode, intent);
        try {
            if (scanResult != null) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                Intent intentResult = new Intent(this, ProductActivity_.class);
                intentResult
                        .putExtra("contents", contents)
                        .putExtra("format", format);

                Long id = Long.valueOf(contents);
                manager.setIdProduto(id);
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
        history.setCliente_id(manager.pegaUsuario().getUser().getUser_id());
        history.setProduto_id(manager.getIdProduto());
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.finishAffinity();
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

    @UiThread
    public void todos_Os_Produtos() {

        singleton_mains.add(new SingletonMain(R.drawable.imagem_blusa_laranja, R.drawable.imagem_blusa_star_wars, "R$ 200,00", "Camisa social Masc.", "R$ 337,99", "Camisa social Feme."));

        adapter.getDadosMain(singleton_mains);
        listVire_Main.setAdapter(adapter);
    }

    @Background
    public void buscaFacebook(){
        recordLogin = manager.pegaFacebook();
        recordLogin.getUser().getEmail();
        recordLogin.getUser().getFacebook_token();
        try {
            user =  facebookService.pegaFacebook(recordLogin);
            recordLogin.getUser().setUser_id(user.getUser_id());
            Log.d("1111111111111", user.getUser_id()+"");
            Log.d("333333333333", recordLogin.getUser().getUser_id()+"");

            recordLogin.getCliente().setId(user.getId());
            Log.d("222222222", user.getId()+"");
            Log.d("4444444444444444", recordLogin.getCliente().getId()+"");

            manager.addUser(recordLogin);
            manager.pegaUsuario();
        }catch (Exception e){
            Log.d("erro do facebook", e.toString());
        }
    }
}