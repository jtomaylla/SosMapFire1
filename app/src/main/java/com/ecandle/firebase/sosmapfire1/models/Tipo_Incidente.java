package com.ecandle.firebase.sosmapfire1.models;

/**
 * Created by juantomaylla on 19/01/17.
 */

public class Tipo_Incidente {
    public int id;
    public String nombre;
    public String imagen;
    public int anulado;

    public Tipo_Incidente(String nombre, String imagen, int anulado) {

        this.nombre = nombre;
        this.imagen = imagen;
        this.anulado = anulado;
    }
    public Tipo_Incidente(int id, String nombre, String imagen, int anulado) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
        this.anulado = anulado;
    }
    public Tipo_Incidente(String nombre, String imagen) {
        this.nombre = nombre;
        this.imagen = imagen;
    }

    public Tipo_Incidente() {

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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getAnulado() {
        return anulado;
    }

    public void setAnulado(int anulado) {
        this.anulado = anulado;
    }


}
