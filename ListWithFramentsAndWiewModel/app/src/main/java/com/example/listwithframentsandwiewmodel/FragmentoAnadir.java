package com.example.listwithframentsandwiewmodel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentoAnadir#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentoAnadir extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private EditText campoNombre;
    private MainViewModel vm;
    private Button confirmar;

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
        super.onCreate(savedInstanceState);

        vm = ViewModelProviders.of(getActivity()).get(MainViewModel.class);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fragmento_anadir, container, false);
        confirmar = v.findViewById(R.id.botonAnadirConfirmar);
        campoNombre = (EditText) v.findViewById(R.id.nuevoNombreNovela);
        confirmar.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.botonAnadirConfirmar:

                Novela novela = new Novela(campoNombre.getText().toString(), R.drawable.fang, "Mi novela favorita");
                vm.addNovela(novela);
                vm.setVisualizacion(getResources().getString(R.string.VISUALIZACION_LISTA));
        }
    }
}