package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class Lista extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ArrayList<Novelas> novelas;
    ListView lista;
    EditText search;
    AdapterList adaptadorLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista);

        initNovelas();

        lista = findViewById(R.id.list);
        adaptadorLista = new AdapterList(this, novelas);
        lista.setOnItemClickListener(this);
        lista.setAdapter(adaptadorLista);


        search = findViewById(R.id.imgNovela);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                filtrar(s.toString());


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                filtrar(s.toString());


            }

            @Override
            public void afterTextChanged(Editable s) {

                filtrar(s.toString());

            }
        });



    }


    private void initNovelas() {

        novelas = new ArrayList<>();
        novelas.add(new Novelas(R.drawable.el, "Reverend Insanity"));
        novelas.add(new Novelas(R.drawable.download, "GOD OF SLAUGTHER"));

    }

    private void filtrar(String s) {

        ArrayList<Novelas> filtro = new ArrayList<>();

        for(Novelas item: novelas) {

            if(item.getNombre().toLowerCase().contains(s.toLowerCase())) {

                filtro.add(item);

            }

        }

        AdapterList filtrado = new AdapterList(this, filtro);
        lista.setAdapter(filtrado);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Novelas novela = (Novelas) parent.getItemAtPosition(position);
        Intent intent = new Intent(Lista.this, DetailNovel.class);
        intent.putExtra("Novela", novela);
        startActivity(intent);

    }
}