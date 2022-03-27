package com.example.room.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.example.room.adapters.GlideApp;
import com.example.room.adapters.ListAdapterSettings;
import com.example.room.Model.Novela;
import com.example.room.Model.User;
import com.example.room.R;
import com.example.room.viewmodel.AuthViewModel;
import com.example.room.viewmodel.NovelaViewModel;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentProfileSettings#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProfileSettings extends Fragment {

    private NovelaViewModel novelaViewModel;
    private AuthViewModel authViewModel;

    private TextView username, joindate, aboutMe;
    private ImageView imageProfile, editImageProfile, editUsername;


    private RecyclerView recyclerView;
    private ListAdapterSettings listAdapter;

    ProgressBar progressBar;
    private Uri imageUri;
    private FirebaseStorage storage;

    private PieChart pieChart;

    private boolean haveImage;
    private String url;

    private MaterialToolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    User datosUsuario;

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

        editUsername = v.findViewById(R.id.editUsername);

        recyclerView = v.findViewById(R.id.listaNovelasUsuario);

        pieChart = v.findViewById(R.id.pieChart);

        usernameNav = navigationView.getHeaderView(0).findViewById(R.id.nav_user_name);
        imageProfileNav = navigationView.getHeaderView(0).findViewById(R.id.profilePicture);


        aboutMe = v.findViewById(R.id.aboutMe);

        initPieChart();
        showPieChart();

        authViewModel.datosUser().observe(getActivity(), new Observer<User>() {
            @Override
            public void onChanged(User user) {

                datosUsuario = user;
                username.setText(datosUsuario.getUsername());
                GlideApp.with(getActivity()).load(FirebaseStorage.getInstance().getReference().child("images/"+datosUsuario.getImage())).into(imageProfile);
                novelaViewModel.setIdNovelas(datosUsuario.getIdNovelasSubidas());
                usernameNav.setText(datosUsuario.getUsername());
                GlideApp.with(getActivity()).load(FirebaseStorage.getInstance().getReference().child("images/"+datosUsuario.getImage())).into(imageProfileNav);
                url = datosUsuario.getImage();
                haveImage = !datosUsuario.getImage().equals("yinyang.png");
                Date date = new Date(Long.parseLong(datosUsuario.getJoinDate()));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                joindate.setText("Join date: "+simpleDateFormat.format(date));
                aboutMe.setText(datosUsuario.getAboutMe());

            }
        });


            novelaViewModel.getIdNovelas().observe(getActivity(), new Observer<ArrayList<String>>() {
                @Override
                public void onChanged(ArrayList<String> strings) {
                    novelaViewModel.getNovelasUsuarios(strings);
                }
            });





            listAdapter = new ListAdapterSettings(new ListAdapterSettings.OnItemClickListener() {
            @Override
            public void onItemClick(Novela novela, int id) {

                if(id == R.id.eliminar) {
                    AlertDialog dialogo = new AlertDialog
                            .Builder(getActivity())
                            .setPositiveButton("Sí, eliminar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Obtenemos la novela en la posicion de ese view holder
                                    String idNovelaEliminada = novela.getId();
                                    String idImagen = novela.getImagen();
                                    novelaViewModel.eliminarNovela(novela);
                                    FirebaseFirestore.getInstance().collection("Users").document(authViewModel.getUser().getValue().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if(task.isSuccessful()) {
                                                User user = task.getResult().toObject(User.class);
                                                user.getIdNovelasSubidas().remove(idNovelaEliminada);
                                                //Para cuando tengas solo una novela y la elimines que se quite no se porque no te actualiza cuando eliminas todas, no se actualiza la ui
                                                if(user.getIdNovelasSubidas().isEmpty()) {
                                                    novelaViewModel.setNovelasUsuarios(new ArrayList<>());
                                                }
                                                FirebaseFirestore.getInstance().collection("Users").document(authViewModel.getUser().getValue().getUid()).update("idNovelasSubidas", user.getIdNovelasSubidas());
                                                eliminarImagen(idImagen);
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
                            .setMessage("¿Deseas eliminar la novela "+novela.getNombre()+" ?") // El mensaje
                            .create();

                    dialogo.show();
                    //Si se ha salido fuera del dialog sin pulsar cancelar, tambien debemos mostrar las novelas como estaban
                    listAdapter.setNovelas(novelaViewModel.getNovelasUsuarios().getValue());

                }

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
                                String idImagen = listAdapter.getItemAtPosition(viewHolder.getAdapterPosition()).getImagen();
                                novelaViewModel.eliminarNovela(listAdapter.getItemAtPosition(viewHolder.getAdapterPosition()));
                                FirebaseFirestore.getInstance().collection("Users").document(authViewModel.getUser().getValue().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()) {
                                            User user = task.getResult().toObject(User.class);
                                            user.getIdNovelasSubidas().remove(idNovelaEliminada);
                                            //Para cuando tengas solo una novela y la elimines que se quite no se porque no te actualiza cuando eliminas todas, no se actualiza la ui
                                            if(user.getIdNovelasSubidas().isEmpty()) {
                                                novelaViewModel.setNovelasUsuarios(new ArrayList<>());
                                            }
                                            FirebaseFirestore.getInstance().collection("Users").document(authViewModel.getUser().getValue().getUid()).update("idNovelasSubidas", user.getIdNovelasSubidas());
                                            eliminarImagen(idImagen);
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
                        novelaViewModel.vaciarIds();
                        novelaViewModel.isSeACargadoYa(false);
                        novelaViewModel.isSeHaCargadaYaNovelasUsuario(false);
                        authViewModel.signOut();
                        break;
                }
                return true;
            }
        });


        storage = FirebaseStorage.getInstance();







        editUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                //Lo inflamer con nuestro layout
                View view = getLayoutInflater().inflate(R.layout.edit_dialog_username, null);
                //Encontramos los campos por id
                final EditText nombreUsuarioEditar = view.findViewById(R.id.nuevNombreUsuario);
                nombreUsuarioEditar.setText(username.getText().toString());
                final Button confirmarEditar = view.findViewById(R.id.botonEditarConfirmar);
                final Button cancelarEditar = view.findViewById(R.id.botonEditarCancelar);

                builder.setView(view);
                AlertDialog dialog = builder.create();
                dialog.show();


                confirmarEditar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!nombreUsuarioEditar.getText().toString().isEmpty() && nombreUsuarioEditar.getText().toString().length() <= 18) {

                            FirebaseFirestore.getInstance().collection("Users").document(authViewModel.getUser().getValue().getUid()).update("username", nombreUsuarioEditar.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    username.setText(nombreUsuarioEditar.getText().toString());
                                    usernameNav.setText(nombreUsuarioEditar.getText().toString());
                                    dialog.dismiss();
                                }
                            });

                        } else {

                            LinearLayout linearContenedorEditar = view.findViewById(R.id.linearLayoutEditar);

                            for(int i = 0; i < linearContenedorEditar.getChildCount() - 1; i++) {

                                if(((android.widget.EditText)linearContenedorEditar.getChildAt(i)).getText().toString().isEmpty()) {
                                    ((EditText)linearContenedorEditar.getChildAt(i)).setError("No puede estar vacio");
                                } else {
                                    ((EditText)linearContenedorEditar.getChildAt(i)).setError("El nombre es muy largo");
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


    private void eliminarImagen(String id) {

        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setTitle("Eliminando  Novela...");
        pd.show();

        StorageReference storageReference;

        String[] aux = id.split("https://firebasestorage.googleapis.com/v0/b/proyecto-v-f094d.appspot.com/o/");
        //8a3059fb-64e6-4300-adcf-3f49276fea6d?alt=media&token=7a55761e-0027-4d4f-b754-2f7c721ac729")
        String auxiliar[] = aux[1].split("\\?alt=media&token=");

        storageReference = storage.getReference().child(auxiliar[0]);

        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Cannot delete image", Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void initPieChart(){
        //using percentage as values instead of amount
        pieChart.setUsePercentValues(true);

        //remove the description label on the lower left corner, default true if not set
        pieChart.getDescription().setEnabled(false);

        pieChart.getLegend().setEnabled(false);



        //enabling the user to rotate the chart, default true
        pieChart.setRotationEnabled(true);
        //adding friction when rotating the pie chart
        pieChart.setDragDecelerationFrictionCoef(0.9f);
        //setting the first entry start from right hand side, default starting from top
        pieChart.setRotationAngle(0);

        //highlight the entry when it is tapped, default true if not set
        pieChart.setHighlightPerTapEnabled(true);
        //adding animation so the entries pop up from 0 degree
        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

    }

    private void showPieChart() {

        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        String label = "type";

        //initializing data
        Map<String, Integer> typeAmountMap = new HashMap<>();
        typeAmountMap.put("Diciembre",30);
        typeAmountMap.put("Enero",10);
        typeAmountMap.put("Febrero",60);

        //initializing colors for the entries
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#0DECBE"));
        colors.add(Color.parseColor("#2BA7BA"));
        colors.add(Color.parseColor("#33EBFF"));

        //input data and fit data into pie chart entry
        for(String type: typeAmountMap.keySet()){
            pieEntries.add(new PieEntry(typeAmountMap.get(type).floatValue(), type));
        }

        //collecting the entries with label name
        PieDataSet pieDataSet = new PieDataSet(pieEntries,label);
        //providing color list for coloring different entries
        pieDataSet.setColors(colors);
        //grouping the data set from entry to chart
        PieData pieData = new PieData(pieDataSet);

        pieDataSet.setValueTextSize(16f);

        //showing the value of the entries, default true if not set
        pieData.setDrawValues(true);

        pieData.setValueFormatter(new PercentFormatter());

        pieChart.setData(pieData);
        pieChart.invalidate();

    }

    private void uploadImage() {
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setTitle("Uploading Image...");
        pd.show();

        StorageReference storageReference;
        final String randomKey = java.util.UUID.randomUUID().toString();

        if(!haveImage) {
            storageReference = storage.getReference().child("images/"+randomKey);
            url = randomKey;
            haveImage = true;
        } else {
            storageReference = storage.getReference().child("images/"+url);
            url = randomKey;
        }

        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                StorageReference storageNuevo = storage.getReference().child("images/"+url);
                storageNuevo.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
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
        });
    }
}