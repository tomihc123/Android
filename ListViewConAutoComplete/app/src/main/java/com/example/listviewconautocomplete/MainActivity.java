package com.example.listviewconautocomplete;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener, AdapterView.OnItemLongClickListener {

    ListView lista;
    AdapterList adaptadorLista;
    ArrayList<Novela> novelas;
    AutoCompleteTextView buscadorLista;
    /* AutoCompleteTextView*/EditText adapterAutoComplete;
    TextView botonActividadSpinner;
    Button anadir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();


     /*   if(savedInstanceState == null) {

            initList();


        } else {

             novelas = (ArrayList<Novela>) savedInstanceState.getSerializable("novelas");

        } */



        lista = findViewById(R.id.lista);
        adaptadorLista = new AdapterList(this, novelas);
        lista.setAdapter(adaptadorLista);
        lista.setOnItemClickListener(this);
        lista.setOnItemLongClickListener(this);

        buscadorLista = findViewById(R.id.buscar);
       /* adapterAutoComplete = new AdapterAutoComplete(this, novelas);
        buscadorLista.setAdapter(adapterAutoComplete); */
        buscadorLista.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


                filtrarLista(s.toString());


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                filtrarLista(s.toString());


            }

            @Override
            public void afterTextChanged(Editable s) {

                filtrarLista(s.toString());
            }
        });

        botonActividadSpinner = findViewById(R.id.textoCabecera);
        botonActividadSpinner.setOnClickListener(this);

        anadir = findViewById(R.id.botonAnadir);
        anadir.setOnClickListener(this);


    }

    private void initList() {
        novelas = new ArrayList<>();
        novelas.add(new Novela("Reverend Insanity", R.drawable.fang, "LA MEJOR NOVELA DEL MUNDO"));
        novelas.add(new Novela("Reverend Insanity", R.drawable.fang, "LA MEJOR NOVELA DEL MUNDO"));
        novelas.add(new Novela("Reverend Insanity", R.drawable.fang, "LA MEJOR NOVELA DEL MUNDO"));
        novelas.add(new Novela("Reverend Insanity", R.drawable.fang, "LA MEJOR NOVELA DEL MUNDO"));
        novelas.add(new Novela("God of Slaughter", R.drawable.download, "NO ME LA TERMINE"));
        novelas.add(new Novela("God of Slaughter", R.drawable.download, "NO ME LA TERMINE"));
        novelas.add(new Novela("God of Slaughter", R.drawable.download, "NO ME LA TERMINE"));
        novelas.add(new Novela("God of Slaughter", R.drawable.download, "NO ME LA TERMINE"));
        novelas.add(new Novela("God of Slaughter", R.drawable.download, "NO ME LA TERMINE"));
        novelas.add(new Novela("God of Slaughter", R.drawable.download, "NO ME LA TERMINE"));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Novela novela = (Novela) parent.getItemAtPosition(position);
        Intent mostrarDetallesNovelas = new Intent(MainActivity.this, DetallesNovelas.class);
        mostrarDetallesNovelas.putExtra("Novela", novela);
        mostrarDetallesNovelas.putExtra("posicion", position);
        startActivityForResult(mostrarDetallesNovelas, 3);
    }


    private void filtrarLista(String s) {

        ArrayList<Novela> filtro = new ArrayList<>();

        for (Novela filtrado : novelas) {

            if (filtrado.getNombre().toLowerCase().contains(s.toLowerCase())) {

                filtro.add(filtrado);
            }

        }

        AdapterList adaptadorLista = new AdapterList(this, filtro);
        lista.setAdapter(adaptadorLista);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.textoCabecera) {
            Intent iniciarActividad = new Intent(MainActivity.this, SpinnerActivity.class);
            iniciarActividad.putExtra("Lista", novelas);
            startActivityForResult(iniciarActividad, 1);
        } else if (v.getId() == R.id.botonAnadir) {
            Intent anadir = new Intent(MainActivity.this, AnadirNovela.class);
            startActivityForResult(anadir, 2);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {

            if(Activity.RESULT_OK == resultCode) {
                Novela novela = (Novela) data.getSerializableExtra("resultadoSpinner");
                Toast.makeText(this, novela.getNombre(), Toast.LENGTH_LONG).show();
            }

        } else if (requestCode == 2) {

                if(Activity.RESULT_OK == resultCode) {

                    Novela novela = (Novela) data.getSerializableExtra("nuevaNovela");
                    novelas.add(novela);
                    adaptadorLista = new AdapterList(this, novelas);
                    lista.setAdapter(adaptadorLista);

                   /* adapterAutoComplete = new AdapterAutoComplete(this, novelas);
                    buscadorLista.setAdapter(adapterAutoComplete); */

                }

            } else if(requestCode == 3) {

                if(Activity.RESULT_OK == resultCode) {
                    Novela novela = (Novela)data.getSerializableExtra("novelaEditada");
                    int posicion = data.getIntExtra("position", 0);
                    novelas.set(posicion, novela);
                    adaptadorLista = new AdapterList(this, novelas);
                    lista.setAdapter(adaptadorLista);
                }
            }
        }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Novela novela = (Novela) parent.getItemAtPosition(position);
        novelas.remove(novela);

        adaptadorLista = new AdapterList(this, novelas);
        lista.setAdapter(adaptadorLista);

      /*  adapterAutoComplete = new AdapterAutoComplete(this, novelas);
        buscadorLista.setAdapter(adapterAutoComplete); */

        return false;

    }

 /*   @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable("novelas", novelas);
        super.onSaveInstanceState(outState);
    } */


    @Override
    protected void onStop() {
        super.onStop();
        saveData();
    }

    private void saveData() {
        super.onPause();

        SharedPreferences datos = getSharedPreferences("novelas", MODE_PRIVATE);
        SharedPreferences.Editor miEditor = datos.edit();
        Gson gson = new Gson();
        String json = gson.toJson(novelas);
        miEditor.putString("novelas", json);
        miEditor.apply();

    }


    private void loadData() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("novelas", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("novelas", null);
        Type type = new TypeToken<ArrayList<Novela>>() {}.getType();
        novelas = gson.fromJson(json, type);

        if(novelas == null) {
            initList();
        }
    }

}