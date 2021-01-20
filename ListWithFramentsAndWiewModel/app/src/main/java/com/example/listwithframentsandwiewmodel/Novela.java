package com.example.listwithframentsandwiewmodel;

public class Novela {

    private String nombre;
    private int logo;
    private String descripcion;

    public Novela(String nombre, int logo, String descripcion) {
        this.nombre = nombre;
        this.logo = logo;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
