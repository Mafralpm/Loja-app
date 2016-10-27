package br.unifor.retail.view.activity;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.SwitchDrawerItem;
import com.mikepenz.materialdrawer.model.ToggleDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.OnCheckedChangeListener;

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
    private Toolbar toolbar;
    private Drawer.Result navigationDrawerLeft;
    private AccountHeader.Result headerNavigationLeft;

    private OnCheckedChangeListener mOnCheckedChangeListener = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(IDrawerItem iDrawerItem, CompoundButton compoundButton, boolean b) {
            Toast.makeText(InfoClientActivity.this, "onCheckedChanged: " + (b ? "true" : "false"), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_client);
        toolbar = (Toolbar) findViewById(R.id.toolbarInfo_Client);
        toolbar.setTitle("Minhas informações");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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


        //NAVIGATION DRAWER
        headerNavigationLeft = new AccountHeader()
                .withActivity(this)
                .withCompactStyle(false)
                .withSavedInstance(savedInstanceState)
                .withThreeSmallProfileImages(true)
                .withHeaderBackground(R.drawable.pandalambendo)
                .addProfiles(
                        new ProfileDrawerItem().withName("Person One").withEmail("person1@gmail.com").withIcon(getResources().getDrawable(R.drawable.pandalambendo)),
                        new ProfileDrawerItem().withName("Person Two").withEmail("person2@gmail.com").withIcon(getResources().getDrawable(R.drawable.animalpicturepandabearucumari)),
                        new ProfileDrawerItem().withName("Person Three").withEmail("person3@gmail.com").withIcon(getResources().getDrawable(R.drawable.felipemassapilotof1))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile iProfile, boolean b) {
                        Toast.makeText(InfoClientActivity.this, "onProfileChanged: " + iProfile.getName(), Toast.LENGTH_SHORT).show();
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
                        if (i == 0) {
                            Intent intent = new Intent(InfoClientActivity.this, CartActivity.class);
                            startActivity(intent);
                        }
                        Toast.makeText(InfoClientActivity.this, "Item: " + i, Toast.LENGTH_SHORT).show();
                    }
                })
                .withOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                        Toast.makeText(InfoClientActivity.this, "onItemLongClick: " + i, Toast.LENGTH_SHORT).show();
                        return false;
                    }
                })
                .build();

        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Carros Esportivos").withIcon(getResources().getDrawable(R.mipmap.ic_launcher)));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Carros de Luxo").withIcon(getResources().getDrawable(R.mipmap.ic_launcher)));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Carros para Colecionadores").withIcon(getResources().getDrawable(R.mipmap.ic_launcher)));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Carros Populares").withIcon(getResources().getDrawable(R.mipmap.ic_launcher)));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_navigation_drawer_drawer, menu);
        return true;
    }

    public void sexoSpinner() {
        spinnerSexo.setOnItemSelectedListener(this);

        List<String> sexos = new ArrayList<String>();
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
