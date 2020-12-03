package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.net.URI;

public class EmailActivity extends AppCompatActivity implements View.OnClickListener {

    Button enviar;
    EditText destinatario, asunto, cuerpo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        enviar = findViewById(R.id.enviar);
        destinatario = findViewById(R.id.destinatario);
        asunto = findViewById(R.id.asunto);
        cuerpo = findViewById(R.id.cuerpo);
        enviar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.enviar:

                Intent enviarEmail = new Intent(Intent.ACTION_SENDTO);
                enviarEmail.setData(Uri.parse("mailto:"));
                enviarEmail.putExtra(Intent.EXTRA_EMAIL, destinatario.getText().toString());
                enviarEmail.putExtra(Intent.EXTRA_SUBJECT,asunto.getText().toString());
                enviarEmail.putExtra(Intent.EXTRA_TEXT,cuerpo.getText().toString());
                startActivity(enviarEmail);
                
        }

    }
}