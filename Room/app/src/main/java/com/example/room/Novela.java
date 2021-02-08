package com.example.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Novelas")
public class Novela {

    @PrimaryKey(autoGenerate = true)
    private String nombre;
    private int imagen;
    private String descripcion;

    public Novela(String nombre, int imagen, String descripcion) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
