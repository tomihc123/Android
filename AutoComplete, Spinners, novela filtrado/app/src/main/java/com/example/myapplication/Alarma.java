
package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Alarma extends AppCompatActivity implements View.OnClickListener {

    private Button confimar;
    private EditText horas;
    private EditText minutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarma);

        confimar = findViewById(R.id.confirmar);
        horas = findViewById(R.id.horas);
        minutos = findViewById(R.id.minutis);

        confimar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (horas.getText() != null && minutos.getText() != null) {

            createAlarm("Texto de pruba", Integer.parseInt(horas.getText().toString()), Integer.parseInt(minutos.getText().toString()));
        }

    }

    private void createAlarm(String message, int hour, int minutes) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_MESSAGE, message)
                .putExtra(AlarmClock.EXTRA_HOUR, hour)
                .putExtra(AlarmClock.EXTRA_MINUTES, minutes);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}