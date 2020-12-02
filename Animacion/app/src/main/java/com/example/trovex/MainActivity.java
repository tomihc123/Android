package com.example.trovex;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {

    private Thread hilo;
    private static final long DURACION_ANIMACION = 2800;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hilo = new Thread(){

            @Override
            public void run() {

                try {

                    synchronized(this) {

                        wait(DURACION_ANIMACION);
                    }

                } catch (InterruptedException e){

                    e.printStackTrace();

                } finally {

                    Intent siguiente = new Intent(MainActivity.this, SecondActivity.class);
                    startActivity(siguiente);
                    finish();

                }
            }

        };

        hilo.start();

    }


}