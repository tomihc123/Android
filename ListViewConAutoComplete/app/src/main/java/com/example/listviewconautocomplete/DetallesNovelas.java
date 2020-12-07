package com.example.listviewconautocomplete;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetallesNovelas extends AppCompatActivity {

    TextView nombre;
    ImageView imagen;
    TextView descripcion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_novelas);

        Novela novela = (Novela) getIntent().getSerializableExtra("Novela");

        nombre = findViewById(R.id.nombreNovela);
        imagen = findViewById(R.id.imagenNovela);
        descripcion = findViewById(R.id.descripcionNovela);

        nombre.setText(novela.getNombre());
        imagen.setImageResource(novela.getImagen());
        descripcion.setText(novela.getDescripcion());


    }
}