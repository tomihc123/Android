package com.example.room.repository;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.room.Model.Novela;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NovelaFireRepository {

    private onFirestoreTaskComplete onFirestoreTaskComplete;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference novelaRef = firebaseFirestore.collection("Novelas");

    private ArrayList<Novela> novelas = new ArrayList<Novela>();
    private ArrayList<Novela> novelasUsuario = new ArrayList<Novela>();
    String idNovelasubida = "";


    public NovelaFireRepository(onFirestoreTaskComplete onFirestoreTaskComplete) {
        this.onFirestoreTaskComplete = onFirestoreTaskComplete;
    }


    public void getNovelas() {

        novelaRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                novelas.clear();

                for(QueryDocumentSnapshot document: queryDocumentSnapshots) {
                    Novela novela = document.toObject(Novela.class);
                    novelas.add(novela);
                    onFirestoreTaskComplete.novelaData(novelas);
                }
            }
        });

    }




       public void getNovelas(List<String> idNovelas) {

        novelaRef.whereIn(FieldPath.documentId(), idNovelas).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                novelasUsuario.clear();

                for(QueryDocumentSnapshot document: queryDocumentSnapshots) {
                    Novela novela = document.toObject(Novela.class);
                    novelasUsuario.add(novela);
                    onFirestoreTaskComplete.novelaDataUser(novelasUsuario);
                }
            }
        });

    }



    public void actualizarNovela(Novela novela) {

        HashMap<String, Object> datos = new HashMap<>();
        datos.put("nombre", novela.getNombre());
        datos.put("descripcion", novela.getDescripcion());
        datos.put("autor", novela.getAutor());

        novelaRef.document(novela.getId()).update(datos);
    }


    public void anadirNovela(HashMap<String, String> novela) {


        novelaRef.add(novela).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                idNovelasubida  = task.getResult().getId();
                onFirestoreTaskComplete.idNovelaSubida(idNovelasubida);
            }
        });
    }

    public void eliminiarNovela(Novela novela) {

        novelaRef.document(novela.getId()).delete();

    }


    public interface onFirestoreTaskComplete {
        void novelaData(List<Novela> novelas);
        void novelaDataUser(List<Novela> novelas);
        void idNovelaSubida(String id);
        void onError(Exception e);
    }

}
