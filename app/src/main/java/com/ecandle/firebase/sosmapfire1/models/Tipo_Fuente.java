package com.ecandle.firebase.sosmapfire1.models;

/**
 * Created by juantomaylla on 19/01/17.
 */

public class Tipo_Fuente {
    public int id;
    public String nombre;
    public int anulado;

    public Tipo_Fuente(int id, String nombre, int anulado) {
        this.id = id;
        this.nombre = nombre;
        this.anulado = anulado;

    }
    public Tipo_Fuente(String nombre, int anulado) {

        this.nombre = nombre;
        this.anulado = anulado;

    }

    public Tipo_Fuente() {

    }
    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAnulado() {
        return anulado;
    }

    public void setAnulado(int anulado) {
        this.anulado = anulado;
    }

}
