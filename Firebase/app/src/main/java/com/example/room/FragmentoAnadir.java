package com.example.room;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.room.Model.User;
import com.example.room.viewmodel.AuthViewModel;
import com.example.room.viewmodel.NovelaViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;


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

    private ImageView imagenSubida, iconoSubida;
    private Uri imageUri;


    HashMap<String, String> novela = new HashMap<>();


    private FirebaseStorage storage;

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
        imagenSubida = view.findViewById(R.id.imagenAnadidaNovela);
        iconoSubida = view.findViewById(R.id.uploadImageNovela);


        storage = FirebaseStorage.getInstance();

        iconoSubida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });




        botonAnadirNovela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nuevoNombre.getText().toString().isEmpty() && !nuevaDescripcion.getText().toString().isEmpty() && !autor.getText().toString().isEmpty() && imagenSubida.getDrawable() != null) {

                    //Primero subimos la imagen y una vez que este subida se añade la novela ya que si no en la pag
                    //principal se veria una novela sin foto, como es asincrono todo mientras se este subiendo la imagen
                    //la novela ya se ha podido subir ademas no podemos capturar el token de la imagen subido para añadirlo con lo demas
                    //puesto que ya esta subido
                    uploadImage();


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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imagenSubida.setImageURI(imageUri);
        }
    }


    private void uploadImage() {

        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setTitle("Subiendo novela...");
        pd.show();

        StorageReference storageReference;


        final String randomKey = java.util.UUID.randomUUID().toString();
        storageReference = storage.getReference().child(randomKey);


        storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();
                Map<String, Object> map = new HashMap<>();
                map.put("image", taskSnapshot.getMetadata().getName());
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            novela.put("nombre", nuevoNombre.getText().toString());
                            novela.put("descripcion", nuevaDescripcion.getText().toString());
                            novela.put("autor", autor.getText().toString());
                            novela.put("enlaceDescarga", nuevoEnlace.getText().toString());
                            novela.put("imagen", uri.toString());

                            novelaViewModel.anadirNovela(novela);

                            novelaViewModel.getIdNovelaSubida().observe(getActivity(), new Observer<String>() {
                                @Override
                                public void onChanged(String s) {
                                    FirebaseFirestore.getInstance().collection("Users").document(authViewModel.getUser().getValue().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if(task.isSuccessful()) {
                                                User user = task.getResult().toObject(User.class);
                                                if(!user.getIdNovelasSubidas().contains(s)) {
                                                    user.getIdNovelasSubidas().add(s);
                                                    FirebaseFirestore.getInstance().collection("Users").document(authViewModel.getUser().getValue().getUid()).update("idNovelasSubidas", user.getIdNovelasSubidas());
                                                }
                                            }
                                        }
                                    });
                                }
                            });

                            novelaViewModel.setVisualizacion(getResources().getString(R.string.VISUALIZACION_LISTA));

                        }
                    });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Cannot upload image", Toast.LENGTH_SHORT).show();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                pd.setMessage("Percentage: "+(int)progressPercent + "%");
            }
        });

    }
}