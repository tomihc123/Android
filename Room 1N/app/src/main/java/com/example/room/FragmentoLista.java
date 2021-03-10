package com.example.room;

import android.Manifest;
import android.animation.TimeAnimator;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.telecom.InCallService;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.AlignmentSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterViewAnimator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentoLista#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentoLista extends Fragment {

    //ViewModel
    private NovelaViewModel novelaViewModel;

    //RecyclerView y adaptador
    private RecyclerView recyclerView;
    private ListAdapter adaptador;

    //Widgets
    private Button botonAnadir;
    private EditText textoFiltro;

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
            public void onItemClick(NovelaConComentarios novela, int id) {
                if(id == R.id.leerMas) {
                    novelaViewModel.setNovelaParaEditar(novela);
                    novelaViewModel.setVisualizacion(getResources().getString(R.string.VISUALIZACION_EDITAR));
                }

                if(id == R.id.editar) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    View view = getLayoutInflater().inflate(R.layout.edit_dialog, null);
                    final EditText nombreNovelaEditar = view.findViewById(R.id.nombreNovelaEditar);
                    final EditText descripcionNovelaEditar = view.findViewById(R.id.descripcionNovelaEditar);
                    final EditText autorNovelaEditar = view.findViewById(R.id.autorNovelaEditar);
                    final Button confirmarEditar = view.findViewById(R.id.botonEditarConfirmar);
                    final Button cancelarEditar = view.findViewById(R.id.botonEditarCancelar);

                    nombreNovelaEditar.setText(novela.getNovela().getNombre());
                    descripcionNovelaEditar.setText(novela.getNovela().getDescripcion());
                    autorNovelaEditar.setText(novela.getNovela().getAutor());

                    builder.setView(view);
                    AlertDialog dialog = builder.create();
                    dialog.show();


                    confirmarEditar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!nombreNovelaEditar.getText().toString().isEmpty() && !descripcionNovelaEditar.getText().toString().isEmpty() && !autorNovelaEditar.getText().toString().isEmpty()) {
                                novela.getNovela().setNombre(nombreNovelaEditar.getText().toString());
                                novela.getNovela().setDescripcion(descripcionNovelaEditar.getText().toString());
                                novela.getNovela().setAutor(autorNovelaEditar.getText().toString());
                                novelaViewModel.actualizar(novela.getNovela());
                                dialog.dismiss();
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

                if(id == R.id.download) {

                    BiometricManager biometricManager = BiometricManager.from(getActivity());

                    switch (biometricManager.canAuthenticate()) {

                        case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:

                            Toast.makeText(getContext(), "No tiene sensor de huellas el dispositivo", Toast.LENGTH_SHORT).show();
                        break;
                        case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                            Toast.makeText(getContext(), "No se puede usar actualmente", Toast.LENGTH_SHORT).show();
                        break;
                        case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                            Toast.makeText(getContext(), "No tienes ninguna huella configurada en el dispositivo", Toast.LENGTH_SHORT).show();
                        break;

                    }

                    Executor executor = ContextCompat.getMainExecutor(getActivity());

                    BiometricPrompt biometricPrompt = new BiometricPrompt(getActivity(), executor, new BiometricPrompt.AuthenticationCallback() {
                        @Override
                        public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                            super.onAuthenticationError(errorCode, errString);
                        }

                        @Override
                        public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                                if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                                    descargarNovela(novela.getNovela().getEnlaceDescarga(), novela.getNovela().getNombre());
                                } else {
                                    requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE, novela.getNovela().getEnlaceDescarga(), novela.getNovela().getNombre()}, 1001);
                                }

                            } else {
                                descargarNovela(novela.getNovela().getEnlaceDescarga(), novela.getNovela().getNombre());
                            }
                        }

                        @Override
                        public void onAuthenticationFailed() {
                            super.onAuthenticationFailed();
                        }
                    });


                    final BiometricPrompt.PromptInfo promptInfo =  new BiometricPrompt.PromptInfo.Builder().setTitle("Descargar "+novela.getNovela().getNombre()).setDescription("Utiliza tu huella para descargar").setNegativeButtonText("Cancelar").build();

                    biometricPrompt.authenticate(promptInfo);

                }

            }
        });

        recyclerView = v.findViewById(R.id.lista);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adaptador);


        novelaViewModel.obtenerNovelas().observe(getActivity(), new Observer<List<NovelaConComentarios>>() {
            @Override
            public void onChanged(List<NovelaConComentarios> novelas) {
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
                AlertDialog dialogo = new AlertDialog
                        .Builder(getActivity())
                        .setPositiveButton("Sí, eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                novelaViewModel.eliminar(adaptador.getItemAtPosition(viewHolder.getAdapterPosition()));
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                adaptador.setNovelas(novelaViewModel.obtenerNovelas().getValue());
                            }
                        })
                        .setTitle("Confirmar")
                        .setMessage("¿Deseas eliminar la novela "+adaptador.getItemAtPosition(viewHolder.getAdapterPosition()).getNombre()+" ?") // El mensaje
                        .create();

                dialogo.show();
                adaptador.setNovelas(novelaViewModel.obtenerNovelas().getValue());

            }
        }).attachToRecyclerView(recyclerView);


        textoFiltro = v.findViewById(R.id.buscar);
        textoFiltro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ArrayList<NovelaConComentarios> filtro = new ArrayList<>();
                for(NovelaConComentarios novela: novelaViewModel.obtenerNovelas().getValue()) {

                    if(novela.getNovela().getNombre().toLowerCase().contains(s.toString().toLowerCase())) {
                        filtro.add(novela);
                    }
                    adaptador.setNovelas(filtro);
                }
            }
        });


        botonAnadir = v.findViewById(R.id.botonAnadir);
        botonAnadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                novelaViewModel.setVisualizacion(getResources().getString(R.string.VISUALIZACION_ANADIR));
            }
        });
        return v;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1001) {
             if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                descargarNovela(permissions[1], permissions[2]);
             } else {
                 Toast.makeText(getActivity(), "Permiso denegado", Toast.LENGTH_SHORT).show();
             }

        }
    }

    private void descargarNovela(String enlace, String nombre) {

        Uri downloadUri = Uri.parse(enlace);
        DownloadManager manager = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);

        try {

            if(manager != null) {

                DownloadManager.Request request = new DownloadManager.Request(downloadUri);
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE).setTitle(nombre).setDescription("Descargando... "+nombre).setAllowedOverMetered(true).setAllowedOverRoaming(true).setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED).setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, nombre).setMimeType(MimeTypeMap.getSingleton().getExtensionFromMimeType(getActivity().getContentResolver().getType(Uri.parse(enlace))));
                manager.enqueue(request);
                Toast.makeText(getActivity(), "Download started!!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(enlace));
            }

        } catch (Exception e) {
            Toast.makeText(getActivity(), "Algo malo paso", Toast.LENGTH_SHORT).show();
        }

    }
}