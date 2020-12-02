package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DetailNovel extends AppCompatActivity {

    private Novelas novela;
    private EditText nombre;
    private ImageView imagen;
    private Button confirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_novel);

        novela = (Novelas) getIntent().getSerializableExtra("Novela");

        nombre = findViewById(R.id.texto);
        imagen = findViewById(R.id.img);

        nombre.setText(novela.getNombre());
        imagen.setImageResource(novela.getImagen());

        Novelas novelas = new Novelas(novela.getImagen(), nombre.toString());

    }

}