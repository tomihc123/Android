package com.example.room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

    private NovelaViewModel novelaViewModel;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Para saber si es una tablet
        final boolean isSmall;

        //ViewModel
        novelaViewModel = new ViewModelProvider(this).get(NovelaViewModel.class);

        //SharedPreferences para gestionar si se ha mostrado el tutorial ya
        sharedPreferences = getSharedPreferences("TUTORIAL", Context.MODE_PRIVATE);

        //Fragments
        final FragmentoLista fragmentoLista = new FragmentoLista();
        final FragmentoAnadir fragmentoAnadir = new FragmentoAnadir();
        final FragmentoDetalle fragmentoDetalle = new FragmentoDetalle();
        final FragmentTutorial fragmentTutorial = new FragmentTutorial();

        //Guardamos una clave valor para ver si ya se vio el tutorial de inicio
        SharedPreferences.Editor  editor = sharedPreferences.edit();

        if(!sharedPreferences.getBoolean("Mostrado", false)) {

            if (findViewById(R.id.contenedorGeneral) != null) {
                isSmall = true;
                getSupportFragmentManager().beginTransaction().replace(R.id.contenedorGeneral, fragmentTutorial).addToBackStack(null).commit();

            } else {
                isSmall = false;
                getSupportFragmentManager().beginTransaction().replace(R.id.frameIzq, fragmentoLista).addToBackStack(null).commit();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameDrch, fragmentTutorial).addToBackStack(null).commit();
            }

            editor.putBoolean("Mostrado", true).commit();


        } else {

            if (findViewById(R.id.contenedorGeneral) != null) {
                isSmall = true;
                getSupportFragmentManager().beginTransaction().replace(R.id.contenedorGeneral, fragmentoLista).addToBackStack(null).commit();

            } else {
                isSmall = false;
                getSupportFragmentManager().beginTransaction().replace(R.id.frameIzq, fragmentoLista).addToBackStack(null).commit();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameDrch, fragmentoAnadir).addToBackStack(null).commit();
            }


        }

        //Observmo los cambis que hay entre fragments
        novelaViewModel.getVisualizacion().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (isSmall) {
                    if (s.equals(getResources().getString(R.string.VISUALIZACION_LISTA))) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorGeneral, fragmentoLista).addToBackStack(null).commit();
                    } else if (s.equals(getResources().getString(R.string.VISUALIZACION_ANADIR))) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorGeneral, fragmentoAnadir).addToBackStack(null).commit();
                    } else  {
                        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorGeneral, fragmentoDetalle).addToBackStack(null).commit();
                    }
                } else {
                    if(s.equals(getResources().getString(R.string.VISUALIZACION_ANADIR))) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameDrch, fragmentoAnadir).addToBackStack(null).commit();
                    } else if(s.equals(getResources().getString(R.string.VISUALIZACION_EDITAR))) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameDrch, fragmentoDetalle).addToBackStack(null).commit();
                    }
                }
            }
        });

    }
}