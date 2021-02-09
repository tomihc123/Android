package com.example.room;

import android.app.Application;
import android.os.AsyncTask;
import android.sax.TextElementListener;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NovelaRepository {

    private NovelaDAO novelaDAO;
    private LiveData<List<Novela>> novelas;

    public NovelaRepository(Application application) {

        NovelaDataBase novelaDataBase = NovelaDataBase.getInstance(application);
        novelaDAO = novelaDataBase.novelaDAO();
        novelas = novelaDAO.getAll();

    }

    //TODO implementar hilos asincronos

    public LiveData<List<Novela>> obtenerNovelas() {

        return novelas;

    }

    public void insertar(Novela novela) {

        new InsertNoteAsycnTask(novelaDAO).execute(novela);

    }

    public void eliminar(Novela novela) {

        new DeleteNoteAsycnTask(novelaDAO).execute(novela);

    }

    public void actualizar(Novela novela) {

        new UpdateNoteAsycnTask(novelaDAO).execute(novela);
    }

    private static class InsertNoteAsycnTask extends AsyncTask<Novela, Void, Void> {

        private NovelaDAO novelaDAO;

        private InsertNoteAsycnTask(NovelaDAO novelaDAO) {
            this.novelaDAO = novelaDAO;
        }


        @Override
        protected Void doInBackground(Novela... novelas) {
            novelaDAO.insertar(novelas[0]);
            return null;
        }
    }


    private static class DeleteNoteAsycnTask extends AsyncTask<Novela, Void, Void> {

        private NovelaDAO novelaDAO;

        private DeleteNoteAsycnTask(NovelaDAO novelaDAO) {
            this.novelaDAO = novelaDAO;
        }


        @Override
        protected Void doInBackground(Novela... novelas) {
            novelaDAO.eliminar(novelas[0]);
            return null;
        }
    }




    private static class UpdateNoteAsycnTask extends AsyncTask<Novela, Void, Void> {

        private NovelaDAO novelaDAO;

        private UpdateNoteAsycnTask(NovelaDAO novelaDAO) {
            this.novelaDAO = novelaDAO;
        }


        @Override
        protected Void doInBackground(Novela... novelas) {
            novelaDAO.actualizar(novelas[0]);
            return null;
        }
    }



}
