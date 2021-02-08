package com.example.room;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NovelaRepository {

    private NovelaDAO novelaDAO;

    public NovelaRepository(Application application) {

        NovelaDataBase novelaDataBase = NovelaDataBase.getInstance(application);
        novelaDAO = novelaDataBase.novelaDAO();

    }

    //TODO implementar hilos asincronos

    public LiveData<List<Novela>> obtenerNovelas() {

        return novelaDAO.getAll();

    }

    public void insertar(Novela novela) {

        novelaDAO.insertar(novela);

    }

    public void actualizar(Novela novela) {

        novelaDAO.actualizar(novela);

    }

    public void eliminar(Novela novela) {

        novelaDAO.eliminar(novela);
    }


}
