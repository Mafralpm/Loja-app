package br.unifor.retail.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import br.unifor.retail.R;
import br.unifor.retail.navegation.drawer.NavegationDrawer;
import br.unifor.retail.qr.code.QrCode;
import br.unifor.retail.view.activity.dialog.DateDialog;
import me.sudar.zxingorient.Barcode;
import me.sudar.zxingorient.ZxingOrient;
import me.sudar.zxingorient.ZxingOrientResult;

import static android.R.attr.format;


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

    @ViewById
    protected EditText info_cliente_nome;
    @ViewById
    protected EditText info_cliente_email;
    @ViewById
    protected EditText info_cliente_nascimento;
    @ViewById
    protected Spinner info_cliente_sexo_spinner;
    @ViewById
    protected Spinner info_cliente_blusa_spinner;
    @ViewById
    protected Spinner info_cliente_calca_spinner;
    @ViewById
    protected Spinner info_cliente_calcado_spinner;

    private Toolbar toolbar;

    NavegationDrawer navegationDrawer;

    QrCode qrCode;

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 42;

    @AfterViews
    public void begin() {
        toolbar = (Toolbar) findViewById(R.id.toolbarInfo_Client);
        toolbar.setTitle("Minhas informações");
        toolbar.setBackground(getResources().getDrawable(R.drawable.canto_superior_da_tela));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sexoSpinner();
        blusaSpinner();
        calcaSpinner();
        calcadoSpinner();

        navegationDrawer = new NavegationDrawer(toolbar, this);
        navegationDrawer.getProfile();

        qrCode = new QrCode(this, getApplicationContext());
    }

    public void onStart() {
        super.onStart();
        info_cliente_nascimento.setOnFocusChangeListener(new View.OnFocusChangeListener() {

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
        qrCode.scanQR();
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

    public void sexoSpinner() {
        info_cliente_sexo_spinner.setOnItemSelectedListener(this);

        List<String> sexos = new ArrayList<>();
        sexos.add("");
        sexos.add("Femenino");
        sexos.add("Masculino");

        ArrayAdapter<String> adapterSexos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sexos);
        adapterSexos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        info_cliente_sexo_spinner.setAdapter(adapterSexos);
    }

    public void blusaSpinner() {
        info_cliente_blusa_spinner.setOnItemSelectedListener(this);

        List<String> tamanhdoBlusas = new ArrayList<String>();
        tamanhdoBlusas.add(" ");
        tamanhdoBlusas.add("GG");
        tamanhdoBlusas.add("G");
        tamanhdoBlusas.add("M");
        tamanhdoBlusas.add("P");
        tamanhdoBlusas.add("PP");

        ArrayAdapter<String> adapterTamanhoBlusa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tamanhdoBlusas);
        adapterTamanhoBlusa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        info_cliente_blusa_spinner.setAdapter(adapterTamanhoBlusa);
    }

    public void calcaSpinner() {
        info_cliente_calca_spinner.setOnItemSelectedListener(this);

        List<String> tamanhdoCalças = new ArrayList<String>();
        tamanhdoCalças.add(" ");
        tamanhdoCalças.add("GG");
        tamanhdoCalças.add("G");
        tamanhdoCalças.add("M");
        tamanhdoCalças.add("P");
        tamanhdoCalças.add("PP");

        ArrayAdapter<String> adapterTamanhoCalça = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tamanhdoCalças);
        adapterTamanhoCalça.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        info_cliente_calca_spinner.setAdapter(adapterTamanhoCalça);
    }

    public void calcadoSpinner() {
        info_cliente_calcado_spinner.setOnItemSelectedListener(this);

        List<String> tamanhdoCalçados = new ArrayList<String>();
        tamanhdoCalçados.add(" ");
        for (int i = 30; i < 49; i += 2) {
            tamanhdoCalçados.add("" + i);
        }


        ArrayAdapter<String> adapterTamanhoCalçado = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tamanhdoCalçados);
        adapterTamanhoCalçado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        info_cliente_calcado_spinner.setAdapter(adapterTamanhoCalçado);
    }

    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity_.class);
        startActivity(intent);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void vaiPraMain(View v) {
        Intent intent = new Intent(this, MainActivity_.class);
        startActivity(intent);
    }


}
