package com.example.room;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.room.Model.Novela;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "Comentarios", foreignKeys = @ForeignKey(entity = Novela.class,
        parentColumns = "id",
        childColumns = "idNovela",
        onDelete = CASCADE,
        onUpdate = CASCADE))
public class Comentario {

    @PrimaryKey(autoGenerate = true)
    private int idComentario;
    private int idNovela;

    private String texto;

    public Comentario(String texto) {
        this.texto = texto;
    }

    public int getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(int idComentario) {
        this.idComentario = idComentario;
    }

    public int getIdNovela() {
        return idNovela;
    }

    public void setIdNovela(int idNovela) {
        this.idNovela = idNovela;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
