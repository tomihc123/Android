package com.example.myapplication;

public class Lugar {

    private String nombre;
    private int imagen;

    public Lugar(String nombre, int imagen) {
        this.nombre = nombre;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public int getImagen() {
        return imagen;
    }
}
