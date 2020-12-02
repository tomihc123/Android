package com.example.myapplication;
import java.io.Serializable;

public class Novelas implements Serializable {

    private int imagen;
    private String nombre;

    public Novelas(int imagen, String nombre) {
        this.imagen = imagen;
        this.nombre = nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public int getImagen() {
        return imagen;
    }


    @Override
    public String toString() {

        return this.getNombre();

    }

}
