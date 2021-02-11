package com.example.room;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentoEditar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentoEditar extends Fragment {

    //ViewModel
    private NovelaViewModel novelaViewModel;

    //Widgets para editar novela
    private EditText editarNombre, editarDescripcion;
    private Button botonConfirmarEditar;
    private ImageView imagen;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentoEditar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentoEditar.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentoEditar newInstance(String param1, String param2) {
        FragmentoEditar fragment = new FragmentoEditar();
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
        View v =  inflater.inflate(R.layout.fragment_editar, container, false);
        editarNombre = v.findViewById(R.id.nombreNovelaEditar);
        editarDescripcion = v.findViewById(R.id.descripcionNovelaEditar);
        botonConfirmarEditar = v.findViewById(R.id.confirmarEditar);
        imagen = v.findViewById(R.id.imagenNovela);

        editarNombre.setText(novelaViewModel.getNovelaParaEditar().getNombre());
        editarDescripcion.setText(novelaViewModel.getNovelaParaEditar().getDescripcion());
        imagen.setImageResource(novelaViewModel.getNovelaParaEditar().getImagen());

        botonConfirmarEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editarNombre.getText().toString().isEmpty() && !editarNombre.getText().toString().isEmpty()) {
                    novelaViewModel.getNovelaParaEditar().setNombre(editarNombre.getText().toString());
                    novelaViewModel.getNovelaParaEditar().setDescripcion(editarDescripcion.getText().toString());
                    novelaViewModel.actualizar(novelaViewModel.getNovelaParaEditar());
                    novelaViewModel.setVisualizacion(getResources().getString(R.string.VISUALIZACION_LISTA));
                }
            }
        });

        return v;

    }
}