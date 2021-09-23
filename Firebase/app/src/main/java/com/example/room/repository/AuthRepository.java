package com.example.room.repository;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthRepository {

    private Application application;
    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private MutableLiveData<Boolean> usserLoggedMutableLiveData;
    private FirebaseAuth auth;


    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }

    public MutableLiveData<Boolean> getUsserLoggedMutableLiveData() {
        return usserLoggedMutableLiveData;
    }

    public AuthRepository(Application application) {
        this.application = application;
        firebaseUserMutableLiveData = new MutableLiveData<>();
        usserLoggedMutableLiveData = new MutableLiveData<>();
        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() != null) {
            firebaseUserMutableLiveData.postValue(auth.getCurrentUser());
        }

    }

    public void register(String email, String pass) {
        auth.createUserWithEmailAndPassword(email , pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()) {

                    firebaseUserMutableLiveData.postValue(auth.getCurrentUser());
                    usserLoggedMutableLiveData.postValue(false);

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

}
