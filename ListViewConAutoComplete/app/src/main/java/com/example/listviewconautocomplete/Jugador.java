package com.example.listviewconautocomplete;

public class Jugador {

    private String nombre;
    private int numGoles;

    public Jugador(String nombre, int numGoles) {
        this.nombre = nombre;
        this.numGoles = numGoles;
    }


    public String getNombre() {
        return nombre;
    }

    public int getNumGoles() {
        return numGoles;
    }
}
