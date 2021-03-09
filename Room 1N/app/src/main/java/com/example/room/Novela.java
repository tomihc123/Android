package com.example.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Novelas")
public class Novela {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nombre;
    private int imagen;
    private String descripcion;
    private String autor;
    private String enlaceDescarga;

    public Novela(String nombre, int imagen, String descripcion, String autor, String enlaceDescarga) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.autor = autor;
        this.enlaceDescarga = enlaceDescarga;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEnlaceDescarga() {
        return enlaceDescarga;
    }

    public void setEnlaceDescarga(String enlaceDescarga) {
        this.enlaceDescarga = enlaceDescarga;
    }
}
