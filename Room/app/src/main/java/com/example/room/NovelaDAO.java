package com.example.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NovelaDAO {

    @Query("SELECT * FROM Novelas")
    LiveData<List<Novela>> getAll();


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertar(Novela novela);

    @Update
    void actualizar(Novela novela);

    @Delete
    void eliminar(Novela novela);

}
