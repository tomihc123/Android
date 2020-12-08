package com.example.listviewconautocomplete;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class DetallesNovelas extends AppCompatActivity {

    EditText nombre;
    ImageView imagen;
    TextView descripcion;
    Button confirmarEditar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_novelas);

        final Novela novela = (Novela) getIntent().getSerializableExtra("Novela");
        final int posicion = getIntent().getIntExtra("posicion", 0);

        nombre = findViewById(R.id.nombreNovela);
        imagen = findViewById(R.id.imagenNovela);
        descripcion = findViewById(R.id.descripcionNovela);
        confirmarEditar = findViewById(R.id.confirmarEditar);

        nombre.setText(novela.getNombre());
        imagen.setImageResource(novela.getImagen());
        descripcion.setText(novela.getDescripcion());

        confirmarEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nombre.getText().toString().equals(novela.getNombre())) {
                    Intent intent = new Intent();
                    Novela novelaEditada = new Novela(nombre.getText().toString(), novela.getImagen(), novela.getDescripcion());
                    intent.putExtra("novelaEditada",novelaEditada);
                    intent.putExtra("position", posicion);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }


            }
        });



    }
}