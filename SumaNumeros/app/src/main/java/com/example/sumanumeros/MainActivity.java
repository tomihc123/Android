 package com.example.sumanumeros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.net.URI;

 public class MainActivity extends AppCompatActivity {

    EditText campo1, campo2;
    Button confirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        campo1 = findViewById(R.id.campo1);
        campo2 = findViewById(R.id.campo2);
        confirmar = findViewById(R.id.calcular);

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.calcular)  {

                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.putExtra("campo1", campo1.getText().toString());
                    intent.putExtra("campo2", campo2.getText().toString());

                    intent.setType("text/plain");
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }

                }
            }
        });

    }
}