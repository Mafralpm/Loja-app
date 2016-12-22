package br.unifor.retail.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;

import br.unifor.retail.R;
import br.unifor.retail.model.RecordLogin;
import br.unifor.retail.model.User;
import br.unifor.retail.navegation.drawer.NavegationDrawer;
import br.unifor.retail.qr.code.QrCode;
import br.unifor.retail.rest.InfoClienteService;
import br.unifor.retail.session.SessionManager;
import br.unifor.retail.view.activity.common.BaseActivity;
import br.unifor.retail.view.activity.dialog.DateDialog;
import me.sudar.zxingorient.ZxingOrient;
import me.sudar.zxingorient.ZxingOrientResult;

import static android.R.attr.format;


@EActivity(R.layout.activity_info_client)
public class InfoClientActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

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
    @RestService
    protected InfoClienteService infoClienteService;

    private RecordLogin recordLogin;

    private List<String> tamanhdoBlusas;

    private Toolbar toolbar;

    private NavegationDrawer navegationDrawer;

    private QrCode qrCode;

    private SessionManager manager;

    private User user;

    private Handler handler;

    private Long idUser;

    @AfterViews
    public void begin() {
        showProgressDialog("Buscando dados");

        manager = new SessionManager(this);
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
        idUser = recordLogin.getUser().getUser_id();

        busca(idUser);
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
        Intent intent = new Intent(getApplicationContext(), CarrinhoActivity_.class);
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
                String contents = intent.getStringExtra("SCAN_RESULT");
                Intent intentResult = new Intent(this, ProductActivity_.class);
                intentResult
                        .putExtra("contents", contents)
                        .putExtra("format", format);
                startActivity(intentResult);
            }
        } catch (RuntimeException e) {
            Log.d("Deu ERRO ", e.toString());
        } catch (Exception e) {
            Log.d("DEU ERRO AQUI", e.toString());
        }
    }

    @Background
    public void busca(Long idUser) {
        try {
            user = infoClienteService.searchClient(idUser);
            montaActivity(user);
        } catch (ResourceAccessException e) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    dialogHelper.showDialog("Problemas de internet", "Verifique a sua conexão com a internet");
                }
            });
        } catch (Exception e) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    dialogHelper.showDialog("Algo deu errado", "Ocorreu algum erro no servidor, mas já estamos resolvendo");
                }
            });
        }
    }

    @UiThread
    public void montaActivity(User user) {

        try {
            info_cliente_nome.setText(user.getNome_cliente());
            info_cliente_email.setText(user.getEmail());

            if (user.getAniversario() == null || user.getSexo() == null ||
                    user.getTamanho_blusa() == null || user.getTamanho_calca() == null || user.getTamanho_calcado() == null) {

                info_cliente_nascimento.setText(" ");
                sexoSpinner(" ");
                blusaSpinner(" ");
                calcaSpinner(" ");
                calcadoSpinner(" ");
            } else {
                info_cliente_nascimento.setText(user.getAniversario().toString());
                sexoSpinner(user.getSexo().toString());
                blusaSpinner(user.getTamanho_blusa().toString());
                calcaSpinner(user.getTamanho_calca().toString());
                calcadoSpinner(user.getTamanho_calcado().toString());
            }

        } catch (ResourceAccessException e) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    dialogHelper.showDialog("Problemas de internet", "Verifique a sua conexão com a internet");
                }
            });

        } catch (Exception e) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    dialogHelper.showDialog("Algo deu errado", "Ocorreu algum erro no servidor, mas já estamos resolvendo");
                }
            });
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialogHelper.dismissDialog();
            }
        }, 1000);
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

        tamanhdoBlusas = new ArrayList<>();
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

        List<String> tamanhdoCalças = new ArrayList<>();
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
    public void alteraraDadosUsuario(View v) {
//        Log.d("Saber Infos","QUERO ESSE: "+ info_cliente_nome.getText().toString() + " "  + info_cliente_sexo_spinner.getSelectedItem().toString() + " " +
//                info_cliente_blusa_spinner.getSelectedItem().toString()+ " " + info_cliente_calca_spinner.getSelectedItem().toString() + " " + info_cliente_calcado_spinner.getSelectedItem().toString());
//
//        if (info_cliente_nome.getText().toString().isEmpty() || info_cliente_email.getText().toString().isEmpty() ||
//                info_cliente_nascimento.getText().toString().isEmpty() || info_cliente_sexo_spinner.getSelectedItem().toString().isEmpty() ||
//                info_cliente_blusa_spinner.getSelectedItem().toString().isEmpty() || info_cliente_calca_spinner.getSelectedItem().toString().isEmpty() || info_cliente_calcado_spinner.getSelectedItem().toString().isEmpty()) {
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    dialogHelper.showDialog("Campos em branco", "Preencha todos os campos antes de salvar");
//
//                }
//            });
//        } else {


            try {
                user.setNome_cliente(info_cliente_nome.getText().toString().trim());
                user.setEmail(info_cliente_email.getText().toString().toLowerCase().trim());
                user.setAniversario(info_cliente_nascimento.getText().toString().trim());
                user.setSexo(info_cliente_sexo_spinner.getSelectedItem().toString());
                user.setTamanho_blusa(info_cliente_blusa_spinner.getSelectedItem().toString());
                user.setTamanho_calca(info_cliente_calca_spinner.getSelectedItem().toString());
                user.setTamanho_calcado(info_cliente_calcado_spinner.getSelectedItem().toString());

                infoClienteService.updatInfoCliente(user, idUser);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        dialogHelper.showDialog("Sucesso", "Suas alterações foram salvas");
                    }
                });

            } catch (ResourceAccessException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        dialogHelper.showDialog("Problemas de internet", "Verifique a sua conexão com a internet");
                    }
                });

            } catch (Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        dialogHelper.showDialog("Algo deu errado", "Ocorreu algum erro no servidor, mas já estamos resolvendo");
                    }
                });

            }
        }
//    }
}
