package com.example.room;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class NovelaViewModel extends AndroidViewModel {

    private NovelaRepository novelaRepository;
    private LiveData<List<Novela>> novelas;
    private MutableLiveData<String> visualizacion = new MutableLiveData<>();
    private MutableLiveData<Novela> novelaParaEditar = new MutableLiveData<>();

    public NovelaViewModel(Application application) {

        super(application);
        novelaRepository = new NovelaRepository(application);
        novelas = novelaRepository.obtenerNovelas();

    }

    public LiveData<List<Novela>> obtenerNovelas() {
        return novelas;
    }

    public void insertar(Novela novela) {
        novelaRepository.insertar(novela);
    }

    public void actualizar(Novela novela) {
        novelaRepository.actualizar(novela);
    }

    public void eliminar(Novela novela) {
        novelaRepository.eliminar(novela);
    }



    public MutableLiveData<String> getVisualizacion() {
        return visualizacion;
    }

    public void setVisualizacion(String visualizacion) {
        this.visualizacion.setValue(visualizacion);
    }

    public MutableLiveData<Novela> getNovelaParaEditar() {
        return novelaParaEditar;
    }

    public void setNovelaParaEditar(Novela novela) {
        this.novelaParaEditar.setValue(novela);
    }
}
