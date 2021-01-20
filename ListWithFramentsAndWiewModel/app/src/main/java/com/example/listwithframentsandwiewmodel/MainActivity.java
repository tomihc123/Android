package com.example.listwithframentsandwiewmodel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private MainViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Fragment fragmentoLista = new FragmentoLista();
        final Fragment fragmentoAnadir = new FragmentoAnadir();

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLista, fragmentoLista).addToBackStack(null).commit();


        vm = ViewModelProviders.of(this).get(MainViewModel.class);

        vm.getVisualizacion().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.equals(getResources().getString(R.string.VISUALIZACION_ANADIR))) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLista, fragmentoAnadir).addToBackStack(null).commit();
                } else if(s.equals(getResources().getString(R.string.VISUALIZACION_LISTA))) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLista, fragmentoLista).addToBackStack(null).commit();

                }

            }
        });


    }
}
