package br.unifor.retail.view.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import br.unifor.retail.R;
import br.unifor.retail.view.activity.dialog.DateDialog;

public class InfoClientActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText nome;
    EditText email;
    EditText txtDate;
    Spinner spinnerSexo;
    Spinner spinnerTamanhoBlusa;
    Spinner spinnerTamanhoCalça;
    Spinner spinnerTamanhoCalçado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_client);

        EditText nome = (EditText) findViewById(R.id.nome);

        EditText email = (EditText) findViewById(R.id.email);

        spinnerSexo = (Spinner) findViewById(R.id.sexoSpinner);
        sexoSpinner();

        spinnerTamanhoBlusa = (Spinner) findViewById(R.id.tamanhoBlusaSpinner);
        tamanhoBlusaSpinner();

        spinnerTamanhoCalça = (Spinner) findViewById(R.id.tamanhoCalçaSpinner);
        tamanhoCalçaSpinner();

        spinnerTamanhoCalçado = (Spinner) findViewById(R.id.tamanhoCalçadoSpinner);
        tamanhoCalçadoSpinner();

    }

    public void onStart(){
        super.onStart();
        txtDate = (EditText) findViewById(R.id.textDate);
        txtDate.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    DateDialog dialog = new DateDialog(v);
                    android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                    dialog.show(ft, "DatePicker");
                }
            }
        });
    }

    public void sexoSpinner (){
        spinnerSexo.setOnItemSelectedListener(this);

        List<String> sexos = new ArrayList<String>();
        sexos.add("Masculino");
        sexos.add("Femenino");

        ArrayAdapter<String> adapterSexos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sexos);
        adapterSexos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSexo.setAdapter(adapterSexos);
    }

    public void tamanhoBlusaSpinner (){
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

    public void tamanhoCalçaSpinner (){
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

    public void tamanhoCalçadoSpinner (){
        spinnerTamanhoCalçado.setOnItemSelectedListener(this);

        List<String> tamanhdoCalçados = new ArrayList<String>();
        tamanhdoCalçados.add(" ");
        for (int i = 30; i < 49; i+=2){
            tamanhdoCalçados.add(""+i);
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
