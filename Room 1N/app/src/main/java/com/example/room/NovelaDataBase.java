package com.example.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Novela.class, Comentario.class}, version = 1)

public abstract class NovelaDataBase extends RoomDatabase {

    private static NovelaDataBase instance;

    public abstract NovelaDAO novelaDAO();

    public static synchronized NovelaDataBase getInstance(Context context) {

        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), NovelaDataBase.class, "database").fallbackToDestructiveMigration().addCallback(roomCallBack).build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
             super.onCreate(db);
             new PopulateDatabaseAsycnTask(instance).execute();
        }
    };

        private static class PopulateDatabaseAsycnTask extends AsyncTask<Void, Void , Void> {

        private NovelaDAO novelaDAO;

        public PopulateDatabaseAsycnTask(NovelaDataBase novelaDataBase) {
            this.novelaDAO = novelaDataBase.novelaDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            long idGenerada = novelaDAO.insertar(new Novela("Reverend Insanity",  R.drawable.fang, "Novela llena de accion", "Gu Zhen"));
            Comentario comentario = new Comentario("Esta buena");
            comentario.setIdNovela((int)idGenerada);
            novelaDAO.insertarComentario(comentario);
            return null;
        }
    }
}
