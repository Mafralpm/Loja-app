package br.unifor.retail.view.activity;

import android.Manifest;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.OnCheckedChangeListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.unifor.retail.R;
import br.unifor.retail.navegation.drawer.NavegationDrawer;
import br.unifor.retail.view.activity.dialog.DateDialog;
import me.sudar.zxingorient.Barcode;
import me.sudar.zxingorient.ZxingOrient;

import static com.facebook.AccessToken.getCurrentAccessToken;


@OptionsMenu(R.menu.menu_info_client)
@EActivity(R.layout.activity_info_client)
public class InfoClientActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText nome;
    EditText email;
    EditText txtDate;
    Spinner spinnerSexo;
    Spinner spinnerTamanhoBlusa;
    Spinner spinnerTamanhoCalça;
    Spinner spinnerTamanhoCalçado;
    private Toolbar toolbar;

    NavegationDrawer navegationDrawer;

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 42;



    @AfterViews
    public void begin() {
        toolbar = (Toolbar) findViewById(R.id.toolbarInfo_Client);
        toolbar.setTitle("Minhas informações");
        toolbar.setBackground(getResources().getDrawable(R.drawable.canto_superior_da_tela));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinnerSexo = (Spinner) findViewById(R.id.sexoSpinner);
        sexoSpinner();

        spinnerTamanhoBlusa = (Spinner) findViewById(R.id.tamanhoBlusaSpinner);
        tamanhoBlusaSpinner();

        spinnerTamanhoCalça = (Spinner) findViewById(R.id.tamanhoCalçaSpinner);
        tamanhoCalçaSpinner();

        spinnerTamanhoCalçado = (Spinner) findViewById(R.id.tamanhoCalçadoSpinner);
        tamanhoCalçadoSpinner();

        navegationDrawer = new NavegationDrawer(toolbar, this);
        navegationDrawer.getProfile();
        navegationDrawer.createNavigationDrawer();
    }

    public void onStart() {
        super.onStart();
        txtDate = (EditText) findViewById(R.id.textDate);
        txtDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DateDialog dialog = new DateDialog(v);
                    android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                    dialog.show(ft, "DatePicker");
                }
            }
        });
    }

    @OptionsItem(R.id.carinho_cliente)
    public void carrinho() {
        Intent intent = new Intent(getApplicationContext(), CartActivity_.class);
        startActivity(intent);
    }

    @OptionsItem(R.id.qr_code_cliete)
    public void qrCode() {
        if (!(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);
        } else {
            scanBarcode();
        }
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

    public void sexoSpinner() {
        spinnerSexo.setOnItemSelectedListener(this);

        List<String> sexos = new ArrayList<>();
        sexos.add("Masculino");
        sexos.add("Femenino");

        ArrayAdapter<String> adapterSexos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sexos);
        adapterSexos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSexo.setAdapter(adapterSexos);
    }

    public void tamanhoBlusaSpinner() {
        spinnerTamanhoBlusa.setOnItemSelectedListener(this);

        List<String> tamanhdoBlusas = new ArrayList<String>();
        tamanhdoBlusas.add(" ");
        tamanhdoBlusas.add("GG");
        tamanhdoBlusas.add("G");
        tamanhdoBlusas.add("M");
        tamanhdoBlusas.add("P");
        tamanhdoBlusas.add("PP");

        ArrayAdapter<String> adapterTamanhoBlusa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tamanhdoBlusas);
        adapterTamanhoBlusa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTamanhoBlusa.setAdapter(adapterTamanhoBlusa);
    }

    public void tamanhoCalçaSpinner() {
        spinnerTamanhoCalça.setOnItemSelectedListener(this);

        List<String> tamanhdoCalças = new ArrayList<String>();
        tamanhdoCalças.add(" ");
        tamanhdoCalças.add("GG");
        tamanhdoCalças.add("G");
        tamanhdoCalças.add("M");
        tamanhdoCalças.add("P");
        tamanhdoCalças.add("PP");

        ArrayAdapter<String> adapterTamanhoCalça = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tamanhdoCalças);
        adapterTamanhoCalça.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTamanhoCalça.setAdapter(adapterTamanhoCalça);
    }

    public void tamanhoCalçadoSpinner() {
        spinnerTamanhoCalçado.setOnItemSelectedListener(this);

        List<String> tamanhdoCalçados = new ArrayList<String>();
        tamanhdoCalçados.add(" ");
        for (int i = 30; i < 49; i += 2) {
            tamanhdoCalçados.add("" + i);
        }


        ArrayAdapter<String> adapterTamanhoCalçado = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tamanhdoCalçados);
        adapterTamanhoCalçado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTamanhoCalçado.setAdapter(adapterTamanhoCalçado);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
