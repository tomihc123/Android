package com.example.room.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.firestore.DocumentId;


public class Novela {

    @DocumentId
    private String id;
    private String nombre;
    private String imagen;
    private String descripcion;
    private String autor;
    private String enlaceDescarga;

    public Novela() {}

    public Novela(String nombre, String imagen, String descripcion, String autor, String enlaceDescarga) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
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
