package com.example.calcular;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int total = Integer.parseInt(getIntent().getStringExtra("campo1")) + Integer.parseInt(getIntent().getStringExtra("campo2"));

        Toast.makeText(this,total+"", Toast.LENGTH_LONG).show();


    }
}