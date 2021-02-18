package com.example.sharedpreferencesbundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final boolean isSmall;

        final GameFragment gameFragment = new GameFragment();

        if(findViewById(R.id.contenedorGeneral) != null) {

            isSmall = true;
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedorGeneral, gameFragment).addToBackStack(null).commit();

        }


    }
}