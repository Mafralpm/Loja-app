package br.unifor.retail.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.unifor.retail.R;
import br.unifor.retail.adapter.AdapterListViewProduct;
import br.unifor.retail.model.Product;
import br.unifor.retail.model.RecordLogin;
import br.unifor.retail.model.Review;
import br.unifor.retail.model.User;
import br.unifor.retail.navegation.drawer.NavegationDrawer;
import br.unifor.retail.qr.code.QrCode;
import br.unifor.retail.rest.InfoClienteService;
import br.unifor.retail.session.SessionManager;
import br.unifor.retail.singleton.SingletonProduct;
import br.unifor.retail.view.activity.common.BaseActivity;
import br.unifor.retail.view.activity.dialog.DateDialog;
import me.sudar.zxingorient.Barcode;
import me.sudar.zxingorient.ZxingOrient;
import me.sudar.zxingorient.ZxingOrientResult;

import static android.R.attr.format;
import static android.R.attr.logo;


@OptionsMenu(R.menu.menu_info_client)
@EActivity(R.layout.activity_info_client)
public class InfoClientActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

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

    List<String> tamanhdoBlusas;

    private Toolbar toolbar;

    NavegationDrawer navegationDrawer;

    QrCode qrCode;

    private RecordLogin recordLogin;
    private SessionManager manager;

    private User user;

    @RestService
    protected InfoClienteService infoClienteService;

    Handler handler;

    Long cliente_id;

    @AfterViews
    public void begin() {
        manager = new SessionManager(getApplicationContext());
        recordLogin = manager.pegaUsuario();
        handler = new Handler();
        toolbar = (Toolbar) findViewById(R.id.toolbarInfo_Client);
        toolbar.setTitle("Minhas informações");
        toolbar.setBackground(getResources().getDrawable(R.drawable.canto_superior_da_tela));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        navegationDrawer = new NavegationDrawer(toolbar, this);
        navegationDrawer.getProfile();

        qrCode = new QrCode(this, getApplicationContext());

        info_cliente_nome.setText(manager.pegaUsuario().getCliente().getNome_cliente());

        info_cliente_email.setText(manager.pegaUsuario().getUser().getEmail());

        cliente_id = recordLogin.getCliente().getId();

        busca(cliente_id);

//        Log.d("usuario e email ", "Email: " + manager.pegaUsuario().getUser().getEmail() + " Nome: " + manager.pegaUsuario().getCliente().getNome_cliente());


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
        Intent intent = new Intent(getApplicationContext(), PedidoActivity_.class);
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

    @Background
    public void busca(Long idCliente) {
        try {
            user = infoClienteService.searchClient(idCliente);
            montaActivity(user);

        } catch (ResourceAccessException e) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Verifique a sua conexão com a internet", Toast.LENGTH_SHORT).show();
//                    dialogHelper.showDialog("Problemas de internet", "Verifique a sua conexão com a internet");

                }
            });

        } catch (Exception e) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Ocorreu algum erro no servidor, mas já estamos resolvendo", Toast.LENGTH_SHORT).show();
//                    dialogHelper.showDialog("Algo deu errado", "Ocorreu algum erro no servidor, mas já estamos resolvendo");

                }
            });
        }
    }

    @UiThread
    public void montaActivity(User user) {

        try {
//            Log.d("Saber Infos", user.getNome_cliente().toString() + " " + user.getAniversario().toString() + " " + user.getSexo().toString() + " " +
//                    user.getTamanho_blusa().toString() + " " + user.getTamanho_calca().toString() + " " + user.getTamanho_calcado().toString());

            if (user.getSexo() == null || user.getTamanho_blusa() == null || user.getTamanho_calca() == null || user.getTamanho_calcado() == null) {

                sexoSpinner(" ");
                blusaSpinner(" ");
                calcaSpinner(" ");
                calcadoSpinner(" ");
            } else {
                sexoSpinner(user.getSexo().toString());
                blusaSpinner(user.getTamanho_blusa().toString());
                calcaSpinner(user.getTamanho_calca().toString());
                calcadoSpinner(user.getTamanho_calcado().toString());
            }


        } catch (ResourceAccessException e) {
            handler.post(new Runnable() {
                @Override
                public void run() {
//                    Toast.makeText(getApplicationContext(), "Verifique a sua conexão com a internet", Toast.LENGTH_SHORT).show();
                    dialogHelper.showDialog("Problemas de internet", "Verifique a sua conexão com a internet");

                }
            });

        } catch (Exception e) {
            handler.post(new Runnable() {
                @Override
                public void run() {
//                    Toast.makeText(getApplicationContext(), "Ocorreu algum erro no servidor, mas já estamos resolvendo", Toast.LENGTH_SHORT).show();
                    dialogHelper.showDialog("Algo deu errado", "Ocorreu algum erro no servidor, mas já estamos resolvendo");

                }
            });
        }
    }


    public void sexoSpinner(String primeiroItem) {
        info_cliente_sexo_spinner.setOnItemSelectedListener(this);

        List<String> sexos = new ArrayList<>();
        sexos.add(primeiroItem);
        sexos.add("Feminino");
        sexos.add("Masculino");

        ArrayAdapter<String> adapterSexos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, removerItemDoCliente(sexos));
        adapterSexos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        info_cliente_sexo_spinner.setAdapter(adapterSexos);

    }

    public void blusaSpinner(String primeiroItem) {
        info_cliente_blusa_spinner.setOnItemSelectedListener(this);

        tamanhdoBlusas = new ArrayList<String>();
        tamanhdoBlusas.add(primeiroItem);
        tamanhdoBlusas.add("GG");
        tamanhdoBlusas.add("G");
        tamanhdoBlusas.add("M");
        tamanhdoBlusas.add("P");
        tamanhdoBlusas.add("PP");


        ArrayAdapter<String> adapterTamanhoBlusa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, removerItemDoCliente(tamanhdoBlusas));
        adapterTamanhoBlusa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        info_cliente_blusa_spinner.setAdapter(adapterTamanhoBlusa);

    }

    public void calcaSpinner(String primeiroItem) {
        info_cliente_calca_spinner.setOnItemSelectedListener(this);

        List<String> tamanhdoCalças = new ArrayList<String>();
        tamanhdoCalças.add(primeiroItem);
        tamanhdoCalças.add("GG");
        tamanhdoCalças.add("G");
        tamanhdoCalças.add("M");
        tamanhdoCalças.add("P");
        tamanhdoCalças.add("PP");

        ArrayAdapter<String> adapterTamanhoCalça = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, removerItemDoCliente(tamanhdoCalças));
        adapterTamanhoCalça.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        info_cliente_calca_spinner.setAdapter(adapterTamanhoCalça);

    }

    public void calcadoSpinner(String primeiroItem) {
        info_cliente_calcado_spinner.setOnItemSelectedListener(this);

        List<String> tamanhdoCalçados = new ArrayList<String>();
        tamanhdoCalçados.add(primeiroItem);
        for (int i = 30; i < 49; i += 2) {
            tamanhdoCalçados.add("" + i);
        }


        ArrayAdapter<String> adapterTamanhoCalçado = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, removerItemDoCliente(tamanhdoCalçados));
        adapterTamanhoCalçado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        info_cliente_calcado_spinner.setAdapter(adapterTamanhoCalçado);

    }


    public List<String> removerItemDoCliente(List<String> lista) {
        String aux = lista.get(0);

        for (int i = 1; i < lista.size(); i++) {
            if (aux.equals(lista.get(i).toString())) {
                lista.remove(i);
            }
        }

        return lista;
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

    @Background
    public void vaiPraMain(View v) {

        user.setNome_cliente(manager.pegaUsuario().getCliente().getNome_cliente());
        user.setEmail(info_cliente_email.toString().toLowerCase());
        user.setSexo(info_cliente_sexo_spinner.getSelectedItem().toString());
        user.setTamanho_blusa(info_cliente_blusa_spinner.getSelectedItem().toString());
        user.setTamanho_calca(info_cliente_calca_spinner.getSelectedItem().toString());
        user.setTamanho_calcado(info_cliente_calcado_spinner.getSelectedItem().toString());
//        try {
            Log.d("Saber Infos", user.getNome_cliente().toString() + " " + user.getSexo().toString() + " " +
                    user.getTamanho_blusa().toString() + " " + user.getTamanho_calca().toString() + " " + user.getTamanho_calcado().toString());

//        } catch (Exception e) {
//            Log.d("Erro", e.toString());
//        }

        if (user.getNome_cliente().toString().equals(" ") || user.getSexo().toString().equals(" ") || user.getTamanho_blusa().toString().equals(" ")
                || user.getTamanho_calca().toString().equals(" ") || user.getTamanho_calcado().toString().equals(" ")) {
            handler.post(new Runnable() {
                @Override
                public void run() {
//                    Toast.makeText(getApplicationContext(), "Verifique a sua conexão com a internet", Toast.LENGTH_SHORT).show();
                    dialogHelper.showDialog("Campos em branco", "Preencha todos os campos antes de salvar");

                }
            });
//        if(user.getNome_cliente() == null){
//            Log.d("Nome", "Nome: " + user.getNome_cliente());
//        }else if (user.getSexo().toString().equals(" ")){
//            Log.d("Sexo", "Sexo: " + user.getNome_cliente());
//        }else if (user.getTamanho_blusa().toString().equals(" ")){
//            Log.d("Blusa", "Blusa: " + user.getTamanho_blusa());
//        }else if (user.getTamanho_calca() == null){
//            Log.d("Calca", "Calca: " + user.getTamanho_calca());
//        }else if (user.getTamanho_calcado() == null){
//            Log.d("Calcado", "Calcado: " + user.getTamanho_calcado());
//        }else{
//            Log.d("PQP", "PQP: "+ user.getSexo().toString());
//        }
        } else {

            try {
                infoClienteService.updatInfoCliente(user, cliente_id);

            } catch (ResourceAccessException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Verifique a sua conexão com a internet", Toast.LENGTH_SHORT).show();
                        dialogHelper.showDialog("Problemas de internet", "Verifique a sua conexão com a internet");

                    }
                });

            } catch (Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
//                    Toast.makeText(getApplicationContext(), "Ocorreu algum erro no servidor, mas já estamos resolvendo", Toast.LENGTH_SHORT).show();
                        dialogHelper.showDialog("Algo deu errado", "Ocorreu algum erro no servidor, mas já estamos resolvendo");

                    }
                });

            } finally {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
//                    Toast.makeText(getApplicationContext(), "Deu certo", Toast.LENGTH_SHORT).show();
                        dialogHelper.showDialog("Sucesso", "Suas alterações foram salvas");

                    }
                });
            }
        }

    }


}
