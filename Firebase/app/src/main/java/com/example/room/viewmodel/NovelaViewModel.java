package com.example.room.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.room.Model.Novela;
import com.example.room.repository.NovelaFireRepository;

import java.util.HashMap;
import java.util.List;

public class NovelaViewModel extends AndroidViewModel implements NovelaFireRepository.onFirestoreTaskComplete {

    private NovelaFireRepository novelaRepository;
    private MutableLiveData<List<Novela>> novelas = new MutableLiveData<>();
    private MutableLiveData<List<Novela>> novelasUsuarios = new MutableLiveData<>();
    private MutableLiveData<String> visualizacion = new MutableLiveData<>();
    private Novela novelaEditar;


    public NovelaViewModel(Application application) {
        super(application);
        novelaRepository = new NovelaFireRepository(this);
        novelaRepository.getNovelas();
    }

    public Novela getNovelaEditar() {
        return novelaEditar;
    }

    public void setNovelaEditar(Novela novelaEditar) {
        this.novelaEditar = novelaEditar;
    }

    public MutableLiveData<String> getVisualizacion() {
        return visualizacion;
    }

    public void setVisualizacion(String visualizacion) { this.visualizacion.setValue(visualizacion); }

    public MutableLiveData<List<Novela>> getNovelas() {
        return novelas;
    }

    public MutableLiveData<List<Novela>> getNovelas(List<String> idNovelas) { novelaRepository.getNovelas(idNovelas); return novelas; }

    public String anadirNovela(HashMap<String, String> novela) {return novelaRepository.anadirNovela(novela);}

    @Override
    public void novelaData(List<Novela> novelas) {
        this.novelas.setValue(novelas);
    }

    @Override
    public void novelaDataUser(List<Novela> novelas) { this.novelasUsuarios.setValue(novelas); }

    @Override
    public void onError(Exception e) {
        Log.d("Novelas Errores", "onError" + e.getMessage());
    }

}
