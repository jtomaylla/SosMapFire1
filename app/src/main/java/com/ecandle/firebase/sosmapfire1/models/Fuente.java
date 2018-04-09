package com.ecandle.firebase.sosmapfire1.models;

/**
 * Created by juantomaylla on 19/01/17.
 */

public class Fuente {
    public int id;
    public String nombre;
    public int tipo_fuente;
    public int anulado;

    public Fuente(String nombre, int tipo_fuente, int anulado) {
        this.nombre = nombre;
        this.tipo_fuente = tipo_fuente;
        this.anulado = anulado;
    }

    public Fuente(int id, String nombre, int tipo_fuente, int anulado) {
        this.id = id;
        this.nombre = nombre;
        this.tipo_fuente = tipo_fuente;
        this.anulado = anulado;
    }

    public Fuente(){

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

    public int getTipo_fuente() {
        return tipo_fuente;
    }

    public void setTipo_fuente(int tipo_fuente) {
        this.tipo_fuente = tipo_fuente;
    }

    public int getAnulado() {
        return anulado;
    }

    public void setAnulado(int anulado) {
        this.anulado = anulado;
    }

}
