package com.example.room.repository;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.room.Model.Novela;
import com.example.room.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AuthRepository {

    private Application application;
    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private MutableLiveData<Boolean> usserLoggedMutableLiveData;
    private FirebaseAuth auth;


    private onFirestoreTaskCompleteUser onFirestoreTaskComplete;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference usersRef = firebaseFirestore.collection("Users");



    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }

    public MutableLiveData<Boolean> getUsserLoggedMutableLiveData() {
        return usserLoggedMutableLiveData;
    }

    public AuthRepository(Application applicationn, onFirestoreTaskCompleteUser onFirestoreTaskComplete ) {
        this.application = application;
        firebaseUserMutableLiveData = new MutableLiveData<>();
        usserLoggedMutableLiveData = new MutableLiveData<>();
        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() != null) {
            firebaseUserMutableLiveData.postValue(auth.getCurrentUser());
        }

        this.onFirestoreTaskComplete = onFirestoreTaskComplete;

    }

    public void register(String username, String email, String pass) {
        auth.createUserWithEmailAndPassword(email , pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()) {

                    User user = new User();
                    user.setUsername(username);
                    user.setEmail(email);
                    user.setJoinDate(auth.getCurrentUser().getMetadata().getCreationTimestamp()+"");
                    user.setImage("yinyang.png");
                    user.setIdNovelasSubidas(new ArrayList<String>());


                    FirebaseFirestore.getInstance().collection("Users").document(auth.getCurrentUser().getUid()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {

                                firebaseUserMutableLiveData.postValue(auth.getCurrentUser());
                                usserLoggedMutableLiveData.postValue(false);
                                userData();

                            }
                        }
                    });

                } else {

                    Toast.makeText(application, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    public void login(String email, String pass) {
            auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        firebaseUserMutableLiveData.postValue(auth.getCurrentUser());
                        usserLoggedMutableLiveData.postValue(false);
                        userData();

                    } else {
                        Toast.makeText(application, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
    }






    public void signOut() {
        auth.signOut();
        usserLoggedMutableLiveData.postValue(true);
    }


    public void userData() {

        if (usersRef != null) {

            if(auth.getCurrentUser() != null) {

                usersRef.document(auth.getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if(documentSnapshot != null) {
                            User user = documentSnapshot.toObject(User.class);
                            onFirestoreTaskComplete.userData(user);
                        }
                    }
                });

            }

        }

    }


    public interface onFirestoreTaskCompleteUser {
        void userData(User user);
    }


}
