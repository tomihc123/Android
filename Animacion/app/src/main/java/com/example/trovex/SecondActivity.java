package com.example.trovex;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView b1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        b1 = findViewById(R.id.ImgObra);
        b1.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ImgObra:

                Intent obraSiguiente = new Intent(this, ObraActivity.class);
                startActivity(obraSiguiente);

                break;

            default:

                break;

        }
    }
}