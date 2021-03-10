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

            //Primera novela con id generado las demas sin comentarios
            long idGenerada = novelaDAO.insertar(new Novela("Reverend Insanity",  R.drawable.fangyuan, "Novela llena de accion", "Gu Zhen", "http://download941.mediafire.com/neqneahrh5bg/bqnfd6je1ugj919/Reverend+Insanity+%5B01-100%5D.pdf"));
            Comentario comentario = new Comentario("Esta muy buena esta novela");
            comentario.setIdNovela((int)idGenerada);
            novelaDAO.insertarComentario(comentario);

            novelaDAO.insertar(new Novela("Martial Peak", R.drawable.martialpeak, "Novela llena de secretos, aunque corta", "Momo", "http://download941.mediafire.com/neqneahrh5bg/bqnfd6je1ugj919/Reverend+Insanity+%5B01-100%5D.pdf"));
            novelaDAO.insertar(new Novela("I shall seal the heavens", R.drawable.shall, "Esta novela es una de las mas populares en los foros", "Er Gen", "http://download941.mediafire.com/neqneahrh5bg/bqnfd6je1ugj919/Reverend+Insanity+%5B01-100%5D.pdf"));
            novelaDAO.insertar(new Novela("A record of a mortal journey to immortality", R.drawable.po, "Nos cuenta una historia de como un mortal llega a ser inmortal", "Fu xi", "http://download941.mediafire.com/neqneahrh5bg/bqnfd6je1ugj919/Reverend+Insanity+%5B01-100%5D.pdf"));
            novelaDAO.insertar(new Novela("Heavenly jewel change", R.drawable.perless, "Adentrate en un mundo de fantasia y magia", "Feng Qian", "http://download941.mediafire.com/neqneahrh5bg/bqnfd6je1ugj919/Reverend+Insanity+%5B01-100%5D.pdf"));
            novelaDAO.insertar(new Novela("Tales Of Demonds And Gods", R.drawable.talesofdemonds, "El protagonista viaja 500 años al pasado y reparara sus errores en la vida", "Xian Xu ", "http://download941.mediafire.com/neqneahrh5bg/bqnfd6je1ugj919/Reverend+Insanity+%5B01-100%5D.pdf"));
            return null;
        }
    }
}
