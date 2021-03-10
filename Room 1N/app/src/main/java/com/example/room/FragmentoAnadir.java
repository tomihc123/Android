package com.example.room;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentoAnadir#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentoAnadir extends Fragment {

    //ViewModel
    private NovelaViewModel novelaViewModel;

    //Widgets para editar
    private EditText nuevoNombre, nuevaDescripcion, autor, nuevoEnlace;
    private TextView botonAnadirNovela;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentoAnadir() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentoAnadir.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentoAnadir newInstance(String param1, String param2) {
        FragmentoAnadir fragment = new FragmentoAnadir();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        novelaViewModel = new ViewModelProvider(getActivity()).get(NovelaViewModel.class);

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
        View view = inflater.inflate(R.layout.fragmento_anadir, container, false);
        nuevoNombre = view.findViewById(R.id.nuevoNombreNovela);
        nuevaDescripcion = view.findViewById(R.id.nuevoDescripcionNovela);
        autor = view.findViewById(R.id.autor);
        nuevoEnlace = view.findViewById(R.id.enlaceDescarga);
        botonAnadirNovela = view.findViewById(R.id.botonAnadirConfirmar);

        botonAnadirNovela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nuevoNombre.getText().toString().isEmpty() && !nuevaDescripcion.getText().toString().isEmpty() && !autor.getText().toString().isEmpty()) {
                    novelaViewModel.insertar(new Novela(nuevoNombre.getText().toString(), R.drawable.cafe, nuevaDescripcion.getText().toString(), autor.getText().toString(), nuevoEnlace.getText().toString()));
                    novelaViewModel.setVisualizacion(getResources().getString(R.string.VISUALIZACION_LISTA));
                } else {
                    Toast.makeText(getContext(), "Los campos nombres, descripcion y autor deben ser rellenados", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}