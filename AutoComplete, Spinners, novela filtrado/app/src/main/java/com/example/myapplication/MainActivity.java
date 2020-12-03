package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener     {

    private ArrayList<Novelas> novelasSpinner = new ArrayList<>(), novelas;
    private AdapterSpinner adaptadorSpinner;
    private AdapterAutocomplete adaptadorAutoComplete;
    private Spinner spinner;
    private AutoCompleteTextView autocomplete;
    private Button anadir, eliminar, cambiar, alarma;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anadir = findViewById(R.id.add);
        eliminar = findViewById(R.id.eliminar);
        cambiar = findViewById(R.id.lista);
        alarma = findViewById(R.id.horario);


        anadir.setOnClickListener(this);
        eliminar.setOnClickListener(this);
        cambiar.setOnClickListener(this);
        alarma.setOnClickListener(this);


        initList();

        spinner = findViewById(R.id.Spinner);
        adaptadorSpinner = new AdapterSpinner(this, novelasSpinner);
        spinner.setAdapter(adaptadorSpinner);
        spinner.setOnItemSelectedListener(this);

        autocomplete = findViewById(R.id.autoComplete);
        adaptadorAutoComplete = new AdapterAutocomplete(this, novelas);
        autocomplete.setAdapter(adaptadorAutoComplete);


    }



    private void initList() {

        novelas = new ArrayList<>();
        novelas.add(new Novelas(R.drawable.lo,"Reverend Insanity"));
        novelas.add(new Novelas(R.drawable.el,"God of slaugther"));


    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.add:

                for(int i = 0; i < novelas.size(); i++) {

                    if(autocomplete.getText().toString().matches(novelas.get(i).toString()) && novelasSpinner.size() < 4) {

                            novelasSpinner.add(novelas.get(i));
                            eliminar.setEnabled(true);

                    }

                }

                autocomplete.setText(" ");

                break;

            case R.id.eliminar:

                novelasSpinner.clear();
                eliminar.setEnabled(false);


                break;

            case R.id.lista:

                Intent intento = new Intent(MainActivity.this, Lista.class);
                startActivity(intento);

                break;

            case R.id.horario:

                Intent intentoHorario = new Intent(MainActivity.this, Alarma.class);
                startActivity(intentoHorario);

                break;

        }

        spinner.setAdapter(adaptadorSpinner);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            Novelas novela = (Novelas) parent.getItemAtPosition(position);
            Intent intent = new Intent(MainActivity.this, DetailNovel.class);
            intent.putExtra("Novela", novela);
            startActivity(intent);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}