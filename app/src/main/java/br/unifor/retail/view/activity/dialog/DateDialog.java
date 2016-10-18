package br.unifor.retail.view.activity.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by mafra on 17/10/16.
 */

public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    EditText txtDate;

    public DateDialog (View v){
        txtDate = (EditText) v;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar calendar = Calendar.getInstance();
        int ano = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this,ano, mes, dia);
    }

    public void onDateSet (DatePicker view, int ano, int mes, int dia){
        String date = dia+"/"+(mes+1)+"/"+ano;
        txtDate.setText(date);
    }

}
