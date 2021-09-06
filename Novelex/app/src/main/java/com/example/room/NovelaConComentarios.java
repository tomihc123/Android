package com.example.room;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class NovelaConComentarios {

    @Embedded
    private Novela novela;
    @Relation(
            parentColumn = "id",
            entityColumn = "idNovela"
    )
    private List<Comentario> comentarios;


    public NovelaConComentarios(Novela novela, List<Comentario> comentarios) {
        this.novela = novela;
        this.comentarios = comentarios;
    }

    public Novela getNovela() {
        return novela;
    }

    public void setNovela(Novela novela) {
        this.novela = novela;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }
}


