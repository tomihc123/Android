package com.example.room;

public class ViewPagerItem {

    private String titulo;
    private String rutaAnimacion;
    private String descripcion;

    public ViewPagerItem(String titulo, String rutaAnimacion, String descripcion) {
        this.titulo = titulo;
        this.rutaAnimacion = rutaAnimacion;
        this.descripcion = descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getRutaAnimacion() {
        return rutaAnimacion;
    }

    public void setRutaAnimacion(String rutaAnimacion) {
        this.rutaAnimacion = rutaAnimacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
