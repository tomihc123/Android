package com.example.listviewconautocomplete;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class SpinnerActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinner;
    private AdapterSpinner adapterSpinner;
    private Button confirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

        ArrayList<Novela> novelas = (ArrayList<Novela>)getIntent().getSerializableExtra("Lista");

        spinner = findViewById(R.id.spinner);
        adapterSpinner = new AdapterSpinner(this, novelas);
        spinner.setAdapter(adapterSpinner);

        confirmar = findViewById(R.id.confirmar);
        confirmar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.confirmar) {

            Novela novela = (Novela)spinner.getSelectedItem();
            Intent returnIntent = new Intent();
            returnIntent.putExtra("resultadoSpinner", novela);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();

        }
    }
}