package com.example.room;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;
import com.example.room.Model.Novela;
import com.example.room.Model.User;
import com.example.room.viewmodel.AuthViewModel;
import com.example.room.viewmodel.NovelaViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;
import static com.google.gson.internal.bind.TypeAdapters.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentProfileSettings#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProfileSettings extends Fragment {

    private NovelaViewModel novelaViewModel;
    private AuthViewModel authViewModel;

    private TextView username, joindate;
    private ImageView imageProfile, editImageProfile;


    private RecyclerView recyclerView;
    private ListAdapterSettings listAdapter;

    ProgressBar progressBar;
    private Uri imageUri;
    private FirebaseStorage storage;

    private boolean haveImage;
    private String url;


    ArrayList<Novela> novelas = new ArrayList<Novela>();
    private MaterialToolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;


    private EditText textoFiltro;
    private TextView usernameNav;
    private ImageView imageProfileNav;



    String DISPLAY_NAME = null;
    String PROFILE_IMAGE_URL = null;
    int TAKE_IMAGE_CODE = 10001;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentProfileSettings() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentProfileSettings.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentProfileSettings newInstance(String param1, String param2) {
        FragmentProfileSettings fragment = new FragmentProfileSettings();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        authViewModel = new ViewModelProvider(getActivity()).get(AuthViewModel.class);
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
        View v = inflater.inflate(R.layout.fragment_profile_settings, container, false);

        username = v.findViewById(R.id.usernameProfile);
        imageProfile = v.findViewById(R.id.settingProfilePicture);
        joindate = v.findViewById(R.id.joindateprofile);
        editImageProfile = v.findViewById(R.id.editProfilePicture);

        toolbar = v.findViewById(R.id.topbar);
        drawerLayout = v.findViewById(R.id.drawer_layout);
        navigationView = v.findViewById(R.id.navigation_view);

        recyclerView = v.findViewById(R.id.listaNovelasUsuario);

        usernameNav = navigationView.getHeaderView(0).findViewById(R.id.nav_user_name);
        imageProfileNav = navigationView.getHeaderView(0).findViewById(R.id.profilePicture);


        FirebaseFirestore.getInstance().collection("Users").document(authViewModel.getUser().getValue().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    User user = task.getResult().toObject(User.class);
                    usernameNav.setText(user.getUsername());
                    GlideApp.with(getActivity()).load(FirebaseStorage.getInstance().getReference().child("images/"+user.getImage())).diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true).into(imageProfileNav);
                }
            }
        });



        FirebaseFirestore.getInstance().collection("Users").document(authViewModel.getUser().getValue().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                     User user = task.getResult().toObject(User.class);
                     novelaViewModel.getNovelasUsuarios(user.getIdNovelasSubidas());
                }
            }
        });


            listAdapter = new ListAdapterSettings(new ListAdapterSettings.OnItemClickListener() {
            @Override
            public void onItemClick(Novela novela, int id) {
                if(id == R.id.leerMas) {
                    novelaViewModel.setNovelaEditar(novela);
                    novelaViewModel.setVisualizacion(getResources().getString(R.string.VISUALIZACION_EDITAR));
                }
               //Si se ha pulsado para editar creamos un alert dialog
                if(id == R.id.editar) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    //Lo inflamer con nuestro layout
                    View view = getLayoutInflater().inflate(R.layout.edit_dialog, null);
                    //Encontramos los campos por id
                    final EditText nombreNovelaEditar = view.findViewById(R.id.nombreNovelaEditar);
                    final EditText descripcionNovelaEditar = view.findViewById(R.id.descripcionNovelaEditar);
                    final EditText autorNovelaEditar = view.findViewById(R.id.autorNovelaEditar);
                    final Button confirmarEditar = view.findViewById(R.id.botonEditarConfirmar);
                    final Button cancelarEditar = view.findViewById(R.id.botonEditarCancelar);

                    //Ponemos por defecto los datos de la novela que se quiere editar
                    nombreNovelaEditar.setText(novela.getNombre());
                    descripcionNovelaEditar.setText(novela.getDescripcion());
                    autorNovelaEditar.setText(novela.getAutor());

                    builder.setView(view);
                    AlertDialog dialog = builder.create();
                    dialog.show();


                    confirmarEditar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!nombreNovelaEditar.getText().toString().isEmpty() && !descripcionNovelaEditar.getText().toString().isEmpty() && !autorNovelaEditar.getText().toString().isEmpty()) {
                                novela.setNombre(nombreNovelaEditar.getText().toString());
                                novela.setDescripcion(descripcionNovelaEditar.getText().toString());
                                novela.setAutor(autorNovelaEditar.getText().toString());
                                //Actualizamos la novela con los datos nuevo, (la id de la novela sigue siendo la misma)
                                novelaViewModel.actualizarNovela(novela);
                                dialog.dismiss();

                            } else {

                                    LinearLayout linearContenedorEditar = view.findViewById(R.id.linearLayoutEditar);

                                    for(int i = 0; i < linearContenedorEditar.getChildCount() - 1; i++) {

                                        if(((android.widget.EditText)linearContenedorEditar.getChildAt(i)).getText().toString().isEmpty()) {
                                            ((EditText)linearContenedorEditar.getChildAt(i)).setError("No puede estar vacio");
                                        }

                                    }
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
            }
        }, getActivity());


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(listAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);


        novelaViewModel.getNovelasUsuarios().observe(getActivity(), new Observer<List<Novela>>() {
            @Override
            public void onChanged(List<Novela> novelas) {
                listAdapter.setNovelas(novelas);
                listAdapter.notifyDataSetChanged();
            }
        });


        //El item touch helper sirve para gestionar los view holder se un recycler viw, este se lo indicamos nosotros cual es en el attatch to recycler view
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.DOWN) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //Si se arrasta hacia izquiera o derecha se muestra un dialod de si se quiere eliminar la novela
                AlertDialog dialogo = new AlertDialog
                        .Builder(getActivity())
                        .setPositiveButton("Sí, eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Obtenemos la novela en la posicion de ese view holder
                                String idNovelaEliminada = listAdapter.getItemAtPosition(viewHolder.getAdapterPosition()).getId();
                                novelaViewModel.eliminarNovela(listAdapter.getItemAtPosition(viewHolder.getAdapterPosition()));
                                FirebaseFirestore.getInstance().collection("Users").document(authViewModel.getUser().getValue().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()) {
                                            User user = task.getResult().toObject(User.class);
                                            user.getIdNovelasSubidas().remove(idNovelaEliminada);
                                            FirebaseFirestore.getInstance().collection("Users").document(authViewModel.getUser().getValue().getUid()).update("idNovelasSubidas", user.getIdNovelasSubidas());
                                        }
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            //Si se cancela que se sigua mostrando todas las novelas
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listAdapter.setNovelas(novelaViewModel.getNovelasUsuarios().getValue());
                            }
                        })
                        .setTitle("Confirmar")
                        .setMessage("¿Deseas eliminar la novela "+listAdapter.getItemAtPosition(viewHolder.getAdapterPosition()).getNombre()+" ?") // El mensaje
                        .create();

                dialogo.show();
                //Si se ha salido fuera del dialog sin pulsar cancelar, tambien debemos mostrar las novelas como estaban
                listAdapter.setNovelas(novelaViewModel.getNovelasUsuarios().getValue());

            }
        }).attachToRecyclerView(recyclerView);



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        novelaViewModel.setVisualizacion(getResources().getString(R.string.VISUALIZACION_HOME));
                        break;
                    case R.id.nav_settings:
                        novelaViewModel.setVisualizacion(getResources().getString(R.string.VISUALIZACION_SETTINGS));
                        break;
                    case R.id.nav_logout:
                        authViewModel.signOut();
                        break;
                }
                return true;
            }
        });


        storage = FirebaseStorage.getInstance();

        FirebaseFirestore.getInstance().collection("Users").document(authViewModel.getUser().getValue().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    User user = task.getResult().toObject(User.class);
                    username.setText(user.getUsername());
                    if(user.getImage() != "") {
                        GlideApp.with(getActivity()).load(storage.getReference().child("images/" + user.getImage())).diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true).into(imageProfile);
                        haveImage = true;
                        url = user.getImage();
                    }
                    Date date = new Date(Long.parseLong(user.getJoinDate()));
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    joindate.setText("Join date: "+simpleDateFormat.format(date));
                }
            }
        });


        editImageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);

            }
        });


        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageProfile.setImageURI(imageUri);
            uploadImage();
    }
}

    private void uploadImage() {
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setTitle("Uploading Image...");
        pd.show();

        StorageReference storageReference;

        if(!haveImage) {
            final String randomKey = java.util.UUID.randomUUID().toString();
            storageReference = storage.getReference().child("images/"+randomKey);
        } else {
            storageReference = storage.getReference().child("images/"+url);

        }

        storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();
                Map<String, Object> map = new HashMap<>();
                map.put("image", taskSnapshot.getMetadata().getName());
                FirebaseFirestore.getInstance().collection("Users").document(authViewModel.getUser().getValue().getUid()).update(map);
                Toast.makeText(getContext(), "Image Upload", Toast.LENGTH_SHORT).show();
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