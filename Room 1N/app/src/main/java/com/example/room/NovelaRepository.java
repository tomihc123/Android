package com.example.room;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NovelaRepository {

    private NovelaDAO novelaDAO;
    private LiveData<List<NovelaConComentarios>> novelas;

    public NovelaRepository(Application application) {

        NovelaDataBase novelaDataBase = NovelaDataBase.getInstance(application);
        novelaDAO = novelaDataBase.novelaDAO();
        novelas = novelaDAO.getAll();

    }

    //Lectura
    public LiveData<List<NovelaConComentarios>> obtenerNovelas() { return novelas; }

    public LiveData<List<Comentario>> obtenerComentarios(int idNovela) { return novelaDAO.getComentarios(idNovela); }

    public LiveData<List<Comentario>> obtenerComenAll() { return novelaDAO.getAllComentarios(); }

    //Insetar
    public void insertar(NovelaConComentarios novela) { new InsertNoteWithComents(novelaDAO).execute(novela); }

    public void insertar(Comentario comentario) { new InsertComentarioAsycnTask(novelaDAO).execute(comentario); }

    public void insertar(Novela novela) { new InsertNoteAsycnTask(novelaDAO).execute(novela); }

    //Eliminar
    public void eliminar(Novela novela) { new DeleteNoteAsycnTask(novelaDAO).execute(novela); }

    public void eliminarComentario(Comentario comentario) { new DeleteComentarioAsycnTask(novelaDAO).execute(comentario); }

    //Actualizar
    public void actualizar(Novela novela) { new UpdateNoteAsycnTask(novelaDAO).execute(novela); }

    public void actualizar(Comentario comentario) { new UpdateComentarioAsycnTask(novelaDAO).execute(comentario);}


    private static class InsertNoteWithComents extends AsyncTask<NovelaConComentarios, Void, Void> {

        private NovelaDAO novelaDAO;

        private InsertNoteWithComents(NovelaDAO novelaDAO) {
            this.novelaDAO = novelaDAO;
        }


        @Override
        protected Void doInBackground(NovelaConComentarios... novelas) {
            long idGenerada = novelaDAO.insertar(novelas[0].getNovela());
            for(Comentario comentario: novelas[0].getComentarios()) {
                comentario.setIdNovela((int)idGenerada);
            }
            novelaDAO.insetarComentarios(novelas[0].getComentarios());
            return null;
        }
    }



    private static class InsertNoteAsycnTask extends AsyncTask<Novela, Void, Void> {

        private NovelaDAO novelaDAO;

        public InsertNoteAsycnTask(NovelaDAO novelaDAO) {
            this.novelaDAO = novelaDAO;
        }

        @Override
        protected Void doInBackground(Novela... novelas) {
            novelaDAO.insertar(novelas[0]);
            return null;
        }
    }


    private static class InsertComentarioAsycnTask extends AsyncTask<Comentario, Void, Void> {

        private NovelaDAO novelaDAO;

        public InsertComentarioAsycnTask(NovelaDAO novelaDAO) {
            this.novelaDAO = novelaDAO;
        }


        @Override
        protected Void doInBackground(Comentario... comentarios) {
            novelaDAO.insertarComentario(comentarios[0]);
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


    private static class DeleteComentarioAsycnTask extends AsyncTask<Comentario, Void, Void> {
        private NovelaDAO novelaDao;

        private DeleteComentarioAsycnTask(NovelaDAO novelaDAO) {
            this.novelaDao = novelaDAO;
        }

        @Override
        protected Void doInBackground(Comentario... comentarios) {
            novelaDao.eliminarComentario(comentarios[0]);
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



    private static class UpdateComentarioAsycnTask extends AsyncTask<Comentario, Void, Void> {

        private NovelaDAO novelaDAO;

        public UpdateComentarioAsycnTask(NovelaDAO novelaDAO) {
            this.novelaDAO = novelaDAO;
        }

        @Override
        protected Void doInBackground(Comentario... comentarios) {
            novelaDAO.actualizar(comentarios[0]);
            return null;
        }
    }


}
