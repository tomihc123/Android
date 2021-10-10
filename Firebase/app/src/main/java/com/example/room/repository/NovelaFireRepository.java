package com.example.room.repository;

import androidx.annotation.NonNull;

import com.example.room.Model.Novela;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;

public class NovelaFireRepository {

    private onFirestoreTaskComplete onFirestoreTaskComplete;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference novelaRef = firebaseFirestore.collection("Novelas");

    public NovelaFireRepository(onFirestoreTaskComplete onFirestoreTaskComplete) {
        this.onFirestoreTaskComplete = onFirestoreTaskComplete;
    }


    public void getNovelas() {
        novelaRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    onFirestoreTaskComplete.novelaData(task.getResult().toObjects(Novela.class));

                } else {

                    onFirestoreTaskComplete.onError(task.getException());

                }
            }
        });
    }

    public void getNovelas(List<String> idNovelas) {

        novelaRef.whereIn(FieldPath.documentId(), idNovelas).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    onFirestoreTaskComplete.novelaDataUser(task.getResult().toObjects(Novela.class));
                } else {
                    onFirestoreTaskComplete.onError(task.getException());
                }
            }
        });
    }

    public String anadirNovela(HashMap<String, String> novela) {

        final String[] id = new String[1];

        novelaRef.add(novela).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                 id[0] = task.getResult().getId();
            }
        });
        return id[0];
    }


    public interface onFirestoreTaskComplete {
        void novelaData(List<Novela> novelas);
        void novelaDataUser(List<Novela> novelas);
        void onError(Exception e);
    }

}
