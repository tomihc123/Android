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

        final boolean smallScreen;
        final Fragment fragmentoLista = new FragmentoLista();
        final Fragment fragmentoAnadir = new FragmentoAnadir();

        vm = ViewModelProviders.of(this).get(MainViewModel.class);

            if (findViewById(R.id.frameLista) != null) {

                smallScreen = true;
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLista, fragmentoLista).addToBackStack(null).commit();

            } else {

                smallScreen = false;
                getSupportFragmentManager().beginTransaction().replace(R.id.frameIzq, fragmentoLista).addToBackStack(null).commit();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameDrch, fragmentoAnadir).addToBackStack(null).commit();
            }

            vm.getVisualizacion().observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    if(smallScreen) {
                        if (s.equals(getResources().getString(R.string.VISUALIZACION_ANADIR))) {
                            getSupportFragmentManager().beginTransaction().replace(R.id.frameLista, fragmentoAnadir).addToBackStack(null).commit();
                        } else if (s.equals(getResources().getString(R.string.VISUALIZACION_LISTA))) {
                            getSupportFragmentManager().beginTransaction().replace(R.id.frameLista, fragmentoLista).addToBackStack(null).commit();
                        }

                    } else {

                        if(s.equals(getResources().getString(R.string.VISUALIZACION_LISTA))) {
                            getSupportFragmentManager().beginTransaction().replace(R.id.frameIzq, fragmentoLista).addToBackStack(null).commit();
                        }

                    }

                }
            });

        }
}

