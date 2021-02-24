package com.example.sharedpreferencesbundle;

import android.content.Context;
import android.content.SharedPreferences;
import android.nfc.tech.Ndef;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */




public class GameFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    RadioButton piedra, papel, tijeras;
    ImageView  imagenOpcionUsuario, imagenOpcionCPU;

    EditText partidasGanadas, partidasPerdias, partidasEmpatadas;

    Button confirmar;
    TextView mostrarGanador;

    private int opcionCpu;
    private int opcionUsuario;

    SharedPreferences sharedPreferences;


    public GameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GameFragment newInstance(String param1, String param2) {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

    sharedPreferences = getActivity().getSharedPreferences("RESULTADOS", Context.MODE_PRIVATE);

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_blank, container, false);
        piedra = v.findViewById(R.id.piedra);
        papel = v.findViewById(R.id.papel);
        tijeras = v.findViewById(R.id.tijeras);
        confirmar = v.findViewById(R.id.botonConfirmar);
        mostrarGanador = v.findViewById(R.id.mostrarGanador);
        imagenOpcionCPU = v.findViewById(R.id.imagenOpcionCPU);
        imagenOpcionUsuario = v.findViewById(R.id.imagenOpcionUsuario);
        partidasGanadas = v.findViewById(R.id.partidasGanadas);
        partidasPerdias = v.findViewById(R.id.partidasPerdidas);
        partidasEmpatadas = v.findViewById(R.id.partidasEmpatadas);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        partidasGanadas.setText(""+sharedPreferences.getInt("Ganados",0));
        partidasPerdias.setText(""+sharedPreferences.getInt("Derrota",0));
        partidasEmpatadas.setText(""+sharedPreferences.getInt("Empate",0));




        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprobarEleccionUsuario();
                opcionCpu();
                mostrarGanador();
                actualizarGanador();
            }
        });

        return v;

    }

    private void comprobarEleccionUsuario() {

        if(piedra.isChecked()) {
            imagenOpcionUsuario.setImageResource(R.drawable.piedra);
            opcionUsuario = 1;
        } else if(papel.isChecked()) {
            imagenOpcionUsuario.setImageResource(R.drawable.papel);
            opcionUsuario = 2;
        } else {
            imagenOpcionUsuario.setImageResource(R.drawable.tijeras);
            opcionUsuario = 3;
        }

    }

    private void opcionCpu() {

        Random rnd = new Random();
        opcionCpu = rnd.nextInt(3) + 1;

        switch (opcionCpu) {

            case 1:
                imagenOpcionCPU.setImageResource(R.drawable.piedra);
                break;
            case 2:
                imagenOpcionCPU.setImageResource(R.drawable.papel);
                break;
            case 3:
                imagenOpcionCPU.setImageResource(R.drawable.tijeras);
                break;
        }

    }

    private String mostrarGanador() {
        String resultado = "";
        switch (opcionUsuario) {
            case 1:
                switch (opcionCpu) {
                    case 1:
                        resultado = "Empate";
                        break;
                    case 2:
                        resultado = "Derrota";
                        break;
                    case 3:
                        resultado = "Ganador";
                        break;
                }
                break;
            case 2:
                switch (opcionCpu) {
                    case 1:
                        resultado = "Ganador";
                        break;
                    case 2:
                        resultado = "Empate";
                        break;
                    case 3:
                        resultado = "Derrota";
                        break;
                }
                break;
            case 3:
                switch (opcionCpu) {
                    case 1:
                        resultado = "Derrota";
                        break;
                    case 2:
                        resultado = "Ganador";
                        break;
                    case 3:
                        resultado = "Empate";
                        break;
                }
            break;
        }
        mostrarGanador.setText(resultado);
        return resultado;
    }

    private void actualizarGanador() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(mostrarGanador().equals("Ganador")) {
            editor.putInt("Ganados", sharedPreferences.getInt("Ganados", 0) + 1).commit();
            partidasGanadas.setText(""+sharedPreferences.getInt("Ganados",0));
        } else if(mostrarGanador().equals(("Derrota"))) {
            editor.putInt("Derrota", sharedPreferences.getInt("Derrota", 0) + 1).commit();
            partidasPerdias.setText(""+sharedPreferences.getInt("Derrota",0));
        } else {
            editor.putInt("Empate", sharedPreferences.getInt("Empate", 0) + 1).commit();
            partidasEmpatadas.setText(""+sharedPreferences.getInt("Empate",0));
        }
    }


}