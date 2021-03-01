package com.example.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NovelaDAO {

    //Para leer
    @Transaction
    @Query("SELECT * FROM Novelas")
    LiveData<List<NovelaConComentarios>> getAll();

    @Query("SELECT * FROM Comentarios WHERE idNovela = :idNovela")
    LiveData<List<Comentario>> getComentarios(int idNovela);

    //Para insertar
    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertar(Novela novela);

    @Insert
    void insetarComentarios(List<Comentario> comentarios);

    @Insert
    void insertarComentario(Comentario comentario);


    //Para actualizar
    @Update
    void actualizar(Novela novela);

    @Update
    void actualizar(Comentario comentario);

    //Para eliminar

    @Delete
    void eliminar(Novela novela);

    @Delete
    void eliminarComentario(Comentario comentarios);

}
