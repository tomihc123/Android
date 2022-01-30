package com.example.room;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.room.Model.User;
import com.example.room.viewmodel.AuthViewModel;
import com.example.room.viewmodel.NovelaViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentoAnadir#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentoAnadir extends Fragment {

    //ViewModel
    private NovelaViewModel novelaViewModel;
    private AuthViewModel authViewModel;

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
        authViewModel = new ViewModelProvider(getActivity()).get(AuthViewModel.class);

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
                    HashMap<String, String> novela = new HashMap<>();
                    novela.put("nombre", nuevoNombre.getText().toString());
                    novela.put("descripcion", nuevaDescripcion.getText().toString());
                    novela.put("autor", autor.getText().toString());
                    novela.put("enlaceDescarga", nuevoEnlace.getText().toString());

                    novelaViewModel.anadirNovela(novela);

                    novelaViewModel.getIdNovelaSubida().observe(getActivity(), new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            FirebaseFirestore.getInstance().collection("Users").document(authViewModel.getUser().getValue().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if(task.isSuccessful()) {
                                        User user = task.getResult().toObject(User.class);
                                        user.getIdNovelasSubidas().add(s);
                                        FirebaseFirestore.getInstance().collection("Users").document(authViewModel.getUser().getValue().getUid()).update("idNovelasSubidas", user.getIdNovelasSubidas());
                                    }
                                }
                            });
                        }
                    });


                    novelaViewModel.setVisualizacion(getResources().getString(R.string.VISUALIZACION_LISTA));

                } else {
                    LinearLayout linearContenedorEdits = view.findViewById(R.id.linearLayout);

                    for(int i = 1; i < linearContenedorEdits.getChildCount() - 1; i++) {

                        if(((com.google.android.material.textfield.TextInputEditText)linearContenedorEdits.getChildAt(i)).getText().toString().isEmpty()) {
                            ((com.google.android.material.textfield.TextInputEditText)linearContenedorEdits.getChildAt(i)).setError("No puede estar vacio");
                        }

                    }
                }
            }
        });
        return view;
    }
}