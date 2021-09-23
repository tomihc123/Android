package com.example.room.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.room.R;
import com.example.room.repository.AuthRepository;
import com.google.firebase.auth.FirebaseUser;

public class AuthViewModel extends AndroidViewModel {

    private AuthRepository authRepository;
    private MutableLiveData<FirebaseUser> user;
    private MutableLiveData<Boolean> loggedStatus;
    private MutableLiveData<String> visualizacion = new MutableLiveData<>();


    public MutableLiveData<FirebaseUser> getUser() {
        return user;
    }

    public MutableLiveData<String> getVisualizacion() {
        return visualizacion;
    }

    public void setVisualizacion(String visualizacion) {
        this.visualizacion.setValue(visualizacion);
    }

    public MutableLiveData<Boolean> getLoggedStatus() {
        return loggedStatus;
    }

    public AuthViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);
        user = authRepository.getFirebaseUserMutableLiveData();
        loggedStatus = authRepository.getUsserLoggedMutableLiveData();


    }

    public void register(String email, String pass) {
        authRepository.register(email, pass);
    }

    public void signIn(String email, String pass) {
        authRepository.login(email, pass);
    }

    public void signOut() {
        authRepository.signOut();

    }


}
