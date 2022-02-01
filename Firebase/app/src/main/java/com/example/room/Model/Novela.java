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
    private int likes;
    private String publicador;

    public Novela() {}

    public Novela(String nombre, String imagen, String descripcion, String autor, String enlaceDescarga, int likes, String publicador) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.autor = autor;
        this.enlaceDescarga = enlaceDescarga;
        this.likes = likes;
        this.publicador = publicador;
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

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getPublicador() {
        return publicador;
    }

    public void setPublicador(String publicador) {
        this.publicador = publicador;
    }

}
