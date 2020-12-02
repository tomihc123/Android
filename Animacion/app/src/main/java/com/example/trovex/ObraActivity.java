package com.example.trovex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import java.util.ArrayList;
import java.util.List;

public class ObraActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<ListElement_Obras> obras;
    Button botonAnadirObra;
    EditText search;
    ListAdapter listAdapter;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obra);

        if(savedInstanceState == null || !savedInstanceState.containsKey("obras")) {

            initObras();

        } else {

            obras = savedInstanceState.getParcelableArrayList("obras");

        }

        botonAnadirObra = findViewById(R.id.botonAnadir);
        botonAnadirObra.setOnClickListener(this);

        recyclerView = findViewById(R.id.ListRecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listAdapter = new ListAdapter(obras, this);
        recyclerView.setAdapter(listAdapter);


        search = findViewById(R.id.buscador_obra);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!obras.isEmpty()) {
                    filter(s.toString());
                }
            }
        });


    }

    private void filter(String text) {
        ArrayList<ListElement_Obras> filterObra = new ArrayList<>();
        if (!obras.isEmpty()) {
            for (ListElement_Obras filtro : obras) {
                if (filtro.getCalle().toLowerCase().contains(text.toLowerCase())) {
                    filterObra.add(filtro);
                }
            }

            listAdapter.filterList(filterObra);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == botonAnadirObra.getId()) {

                this.anadirObra();
                recyclerView.setAdapter(listAdapter);

        }
    }



    public void anadirObra() {

        obras.add(new ListElement_Obras("Pepecito", "Manolo", "Pagado"));


    }


    public void initObras() {

        obras = new ArrayList<>();
        obras.add(new ListElement_Obras("Nuevo", "Manolo", "Pagado"));
        obras.add(new ListElement_Obras("Manolo", "Manolo", "Pagado"));

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("obras", obras);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        obras = savedInstanceState.getParcelableArrayList("obras");
    }


}