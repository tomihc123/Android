package com.example.room;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.room.viewmodel.NovelaViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentoDetalle#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentoDetalle extends Fragment {

    //ViewModel
    private NovelaViewModel novelaViewModel;

    //Widgets para editar novela
    private TextView nombreDetalleNovela, descripcionDetalleNovela, autorDetalleNovela;
    private EditText anadirComentario;
    private Button confirmarAnadir;
    private ImageView imagen;
    private RecyclerView listaComentarios;
    private ListComentarioAdapter listComentarioAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentoDetalle() {
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
    public static FragmentoDetalle newInstance(String param1, String param2) {
        FragmentoDetalle fragment = new FragmentoDetalle();
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
        View v =  inflater.inflate(R.layout.details, container, false);

        nombreDetalleNovela = v.findViewById(R.id.nombreDetalleNovela);
        descripcionDetalleNovela = v.findViewById(R.id.descripcionDetalleNovela);
        autorDetalleNovela = v.findViewById(R.id.autorDetalleNovela);
        imagen = v.findViewById(R.id.imagenNovelaDetalles);
        anadirComentario = v.findViewById(R.id.anadirComentario);
        confirmarAnadir = v.findViewById(R.id.botonAnadirComentario);

        nombreDetalleNovela.setText(novelaViewModel.getNovelaEditar().getNombre());
        descripcionDetalleNovela.setText(novelaViewModel.getNovelaEditar().getDescripcion());
        autorDetalleNovela.setText(novelaViewModel.getNovelaEditar().getAutor());
        Glide.with(v).load(novelaViewModel.getNovelaEditar().getImagen()).into(imagen);

        listaComentarios = v.findViewById(R.id.listaComentarios);
        listaComentarios.setLayoutManager(new LinearLayoutManager(getContext()));
        listaComentarios.setHasFixedSize(true);

        /* listComentarioAdapter = new ListComentarioAdapter(new ListComentarioAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Comentario comentario) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view = getLayoutInflater().inflate(R.layout.edit_comment_dialog, null);
                final EditText textoComentarioEditar = view.findViewById(R.id.editarTextoComentario);
                final Button confirmarEditar = view.findViewById(R.id.botonEditarComentarioConfirmar);
                final Button cancelarEditar = view.findViewById(R.id.botonEditarComentarioCancelar);

                textoComentarioEditar.setText(comentario.getTexto());

                builder.setView(view);
                AlertDialog dialog = builder.create();
                dialog.show();

                confirmarEditar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!textoComentarioEditar.getText().toString().isEmpty()) {
                            comentario.setTexto(textoComentarioEditar.getText().toString());
                            novelaViewModel.actualizar(comentario);
                            dialog.dismiss();
                        } else {
                            textoComentarioEditar.setError("No puede estar vacio");
                        }
                    }
                });

                cancelarEditar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        }); */

        listaComentarios.setAdapter(listComentarioAdapter);

       /* novelaViewModel.obtenerComentarios(novelaViewModel.getNovelaParaEditar().getNovela().getId()).observe(getActivity(), new Observer<List<Comentario>>() {
            @Override
            public void onChanged(List<Comentario> comentarios) {
                listComentarioAdapter.setComentarios(comentarios);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                AlertDialog dialogo = new AlertDialog
                        .Builder(getActivity())
                        .setPositiveButton("Sí, eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                novelaViewModel.eliminar(listComentarioAdapter.getItemAtPosition(viewHolder.getAdapterPosition()));
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listComentarioAdapter.setComentarios(novelaViewModel.getNovelaParaEditar().getComentarios());
                            }
                        })
                        .setTitle("Confirmar")
                        .setMessage("¿Deseas eliminar el comentario "+listComentarioAdapter.getItemAtPosition(viewHolder.getAdapterPosition()).getTexto()+" ?") // El mensaje
                        .create();

                dialogo.show();

            }
        }).attachToRecyclerView(listaComentarios);


        confirmarAnadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!anadirComentario.getText().toString().isEmpty()) {
                    Comentario comentario = new Comentario(anadirComentario.getText().toString());
                    comentario.setIdNovela(novelaViewModel.getNovelaParaEditar().getNovela().getId());
                    novelaViewModel.insertar(comentario);
                    anadirComentario.setText("");
                } else {
                    anadirComentario.setError("No puede estar vacio");
                }
            }
        }); */

        return v;

    }


}