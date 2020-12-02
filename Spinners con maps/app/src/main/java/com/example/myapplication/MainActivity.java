package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    ArrayList<Lugar> lugares;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rellenarSpinner();

        spinner = findViewById(R.id.spinner);
        AdaptadorSpinner adaptadorSpinner = new AdaptadorSpinner(this, lugares);
        spinner.setAdapter(adaptadorSpinner);
        spinner.setOnItemSelectedListener(this);


    }

    private void rellenarSpinner() {

        lugares = new ArrayList<>();
        lugares.add(new Lugar(("Sevilla"), R.drawable.escudo));
        lugares.add(new Lugar(("Japon"), R.drawable.japon));


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = null;
        Lugar novela = (Lugar) parent.getItemAtPosition(position);

        switch (novela.getNombre().toLowerCase()) {

            case "sevilla":
                
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:37.38283,-5.97317"));
                startActivity(intent);

            case "japon":

                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:36.204824,138.252924"));
                startActivity(intent);

        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}