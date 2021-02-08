package com.example.room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;


import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private NovelaViewModel novelaViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // novelaViewModel = ViewModelProviders.of(this).get(NovelaViewModel.class);
        novelaViewModel = new ViewModelProvider(this).get(NovelaViewModel.class);


        


    }
}