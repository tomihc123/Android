package com.example.listviewconautocomplete;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class AnadirNovela extends AppCompatActivity {

    EditText nuevoNombreNovela;
    Button nuevoNovelaAnadir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_novela);


        nuevoNombreNovela = findViewById(R.id.nuevoNombreNovela);
        nuevoNovelaAnadir = findViewById(R.id.botonAnadirConfirmar);

        nuevoNovelaAnadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.botonAnadirConfirmar) {
                    Novela novela = new Novela(nuevoNombreNovela.getText().toString(), R.drawable.fang, "LA MEJOR NOVELA AÑADIDA");
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("nuevaNovela", novela);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
            }
        });


    }
}