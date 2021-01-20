package com.example.listwithframentsandwiewmodel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentoLista#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentoLista extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private ListAdapter adaptador;
    private ListView lista;

    private Button botonAnadir;
    private MainViewModel vm;

    public FragmentoLista() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentoLista.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentoLista newInstance(String param1, String param2) {
        FragmentoLista fragment = new FragmentoLista();
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

        View v = inflater.inflate(R.layout.fragment_lista, container, false);

        lista = v.findViewById(R.id.lista);

        adaptador = new ListAdapter(getActivity(), vm.getNovelas().getValue());
        lista.setAdapter(adaptador);

        botonAnadir = v.findViewById(R.id.botonAnadir);
        botonAnadir.setOnClickListener(this);
        return v;

    }


    @Override
    public void onClick(View view) {

        switch(view.getId()) {

            case R.id.botonAnadir:

                vm.setVisualizacion(getResources().getString(R.string.VISUALIZACION_ANADIR));

            break;

        }
    }
}