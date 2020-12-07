package com.example.listviewconautocomplete;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    ListView lista;
    AdapterList adaptadorLista;
    ArrayList<Novela> novelas;
    AutoCompleteTextView buscadorLista;
    AdapterAutoComplete adapterAutoComplete;
    TextView botonActividadSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initList();


        lista = findViewById(R.id.lista);
        buscadorLista = findViewById(R.id.buscar);


        adaptadorLista = new AdapterList(this, novelas);
        lista.setAdapter(adaptadorLista);
        lista.setOnItemClickListener(this);

        adapterAutoComplete = new AdapterAutoComplete(this, novelas);
        buscadorLista.setAdapter(adapterAutoComplete);

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
        startActivity(mostrarDetallesNovelas);
    }



    private void filtrarLista(String s) {

        ArrayList<Novela> filtro = new ArrayList<>();

        for(Novela filtrado: novelas) {

            if(filtrado.getNombre().toLowerCase().contains(s.toLowerCase())) {

                filtro.add(filtrado);
            }

        }

        AdapterList adaptadorLista = new AdapterList(this, filtro);
        lista.setAdapter(adaptadorLista);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.textoCabecera) {
            Intent iniciarActividad = new Intent(MainActivity.this, SpinnerActivity.class);
            iniciarActividad.putExtra("Lista", novelas);
            startActivityForResult(iniciarActividad, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Novela novela = (Novela)data.getSerializableExtra("resultadoSpinner");
            if (requestCode == 1) {
                Toast.makeText(this, novela.getNombre(), Toast.LENGTH_LONG).show();
            }
    }
}