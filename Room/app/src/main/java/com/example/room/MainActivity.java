package com.example.room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;


import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private NovelaViewModel novelaViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final boolean isSmall;

        //ViewModel
        novelaViewModel = new ViewModelProvider(this).get(NovelaViewModel.class);

        //Fragments
        final FragmentoLista fragmentoLista = new FragmentoLista();
        final FragmentoAnadir fragmentoAnadir = new FragmentoAnadir();
        final FragmentoEditar fragmentoEditar = new FragmentoEditar();


        if(findViewById(R.id.contenedorGeneral) != null) {
            isSmall = true;
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedorGeneral, fragmentoLista).addToBackStack(null).commit();

        } else {
            //TODO implementar tablet
            isSmall = false;
        }

        novelaViewModel.getVisualizacion().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (isSmall) {
                    if (s.equals(getResources().getString(R.string.VISUALIZACION_LISTA))) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorGeneral, fragmentoLista).addToBackStack(null).commit();
                    } else if (s.equals(getResources().getString(R.string.VISUALIZACION_ANADIR))) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorGeneral, fragmentoAnadir).addToBackStack(null).commit();
                    } else {
                        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorGeneral, fragmentoEditar).addToBackStack(null).commit();
                    }
                }
            }
        });

    }
}