package com.example.room.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.room.Model.Novela;
import com.example.room.repository.NovelaFireRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NovelaViewModel extends AndroidViewModel implements NovelaFireRepository.onFirestoreTaskComplete {

    private NovelaFireRepository novelaRepository;
    private MutableLiveData<List<Novela>> novelas = new MutableLiveData<>();
    private MutableLiveData<List<Novela>> novelasUsuarios = new MutableLiveData<>();
    private boolean seACargadoYa = false;


    private MutableLiveData<String> idNovelaSubida = new MutableLiveData<>();

    private MutableLiveData<String> visualizacion = new MutableLiveData<>();
    private Novela novelaEditar;;

    public NovelaViewModel(Application application) {
        super(application);
        novelaRepository = new NovelaFireRepository(this);
    }

    @Override
    public void novelaData(List<Novela> novelas) {
        this.novelas.setValue(novelas);
    }

    public MutableLiveData<List<Novela>> getNovelas() {
         return novelas;
    }

    public void cargarDatosNovelas() {
        if(!seACargadoYa) {novelaRepository.getNovelas(); seACargadoYa=true; }
    }


    @Override
    public void novelaDataUser(List<Novela> novelas) {
        this.novelasUsuarios.setValue(novelas);
    }

    public MutableLiveData<List<Novela>> getNovelasUsuarios(ArrayList<String> idNovelas) {
        novelaRepository.getNovelas(idNovelas);
        return novelasUsuarios;
    }

    public MutableLiveData<List<Novela>> getNovelasUsuarios() {
        return novelasUsuarios;
    }

    public void setNovelasUsuarios(ArrayList<Novela> novelasUsuarios) {
        this.novelasUsuarios.setValue(novelasUsuarios);
    }


    public void actualizarNovela(Novela novela) {
        novelaRepository.actualizarNovela(novela);
    }

    public void eliminarNovela(Novela novela) { novelaRepository.eliminiarNovela(novela); }

    public MutableLiveData<String> anadirNovela(HashMap<String, Object> novela) { novelaRepository.anadirNovela(novela); return idNovelaSubida; }

    @Override
    public void idNovelaSubida(String id) {
        this.idNovelaSubida.setValue(id);
    }

    public MutableLiveData<String> getIdNovelaSubida() {
        return idNovelaSubida;
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

    @Override
    public void onError(Exception e) {
        Log.d("Novelas Errores", "onError" + e.getMessage());
    }

}
