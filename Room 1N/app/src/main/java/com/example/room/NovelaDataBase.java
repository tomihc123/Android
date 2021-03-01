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
            instance = Room.databaseBuilder(context.getApplicationContext(), NovelaDataBase.class, "database").fallbackToDestructiveMigration().build();
        }
        return instance;
    }

}
