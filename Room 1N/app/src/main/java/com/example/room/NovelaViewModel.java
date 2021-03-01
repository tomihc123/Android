package com.example.room;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class NovelaViewModel extends AndroidViewModel {

    private NovelaRepository novelaRepository;
    private LiveData<List<NovelaConComentarios>> novelas;
    private MutableLiveData<String> visualizacion = new MutableLiveData<>();
    private NovelaConComentarios novelaParaEditar;
    private Comentario comentarioParaEditar;

    public NovelaViewModel(Application application) {

        super(application);
        novelaRepository = new NovelaRepository(application);
        novelas = novelaRepository.obtenerNovelas();
    }

    public LiveData<List<NovelaConComentarios>> obtenerNovelas() {
        return novelas;
    }

    public LiveData<List<Comentario>> obtenerComentarios(int idNovela) {
         return novelaRepository.obtenerComentarios(idNovela);
    }

    public void insertar(NovelaConComentarios novela) { novelaRepository.insertar(novela); }

    public void insertar(Comentario comentario) { novelaRepository.insertar(comentario); }

    public void eliminar(Comentario comentario) { novelaRepository.eliminarComentario(comentario); }

    public void eliminar(Novela novela) {
        novelaRepository.eliminar(novela);
    }

    public void actualizar(Novela novela) {
        novelaRepository.actualizar(novela);
    }

    public void actualizar(Comentario comentario) { novelaRepository.actualizar(comentario); }


    public Comentario getComentarioParaEditar() { return comentarioParaEditar; }

    public void setComentarioParaEditar(Comentario comentarioParaEditar) { this.comentarioParaEditar = comentarioParaEditar; }

    public MutableLiveData<String> getVisualizacion() {
        return visualizacion;
    }

    public void setVisualizacion(String visualizacion) { this.visualizacion.setValue(visualizacion); }

    public NovelaConComentarios getNovelaParaEditar() {
        return novelaParaEditar;
    }

    public void setNovelaParaEditar(NovelaConComentarios novela) {
        this.novelaParaEditar = novela;
    }

}
