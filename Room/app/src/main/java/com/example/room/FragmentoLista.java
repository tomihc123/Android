package com.example.room;

import android.animation.TimeAnimator;
import android.content.ClipData;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.style.AlignmentSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewAnimator;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentoLista#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentoLista extends Fragment  {

    //ViewModel
    private NovelaViewModel novelaViewModel;

    //RecyclerView y adaptador
    private RecyclerView recyclerView;
    private ListAdapter adaptador;

    //Widgets
    private Button botonAnadir;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
        View v = inflater.inflate(R.layout.fragment_lista, container, false);

        adaptador = new ListAdapter(new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Novela novela) {
                novelaViewModel.setNovelaParaEditar(novela);
                novelaViewModel.setVisualizacion(getResources().getString(R.string.VISUALIZACION_EDITAR));
            }
        });

        recyclerView = v.findViewById(R.id.lista);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adaptador);

        novelaViewModel.obtenerNovelas().observe(getActivity(), new Observer<List<Novela>>() {
            @Override
            public void onChanged(List<Novela> novelas) {
               adaptador.setNovelas(novelas);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                novelaViewModel.eliminar(adaptador.getItemAtPosition(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);


        botonAnadir = v.findViewById(R.id.botonAnadir);
        botonAnadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                novelaViewModel.setVisualizacion(getResources().getString(R.string.VISUALIZACION_ANADIR));
            }
        });
        return v;
    }

}