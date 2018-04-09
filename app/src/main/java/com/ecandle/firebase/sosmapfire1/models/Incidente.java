package com.ecandle.firebase.sosmapfire1.models;

/**
 * Created by juantomaylla on 19/01/17.
 */

public class Incidente {

    public int id;
    public int tipo_incidente;
    public int tipo_via;
    public String descripcion_incidente;
    public String latitud;
    public String longitud;
    public String fecha_incidente;
    public String hora_incidente;
    public String evidencia;
    public int fuente_id;
    public int anulado;


    public Incidente(int tipo_incidente, int tipo_via, String descripcion_incidente, String latitud, String longitud, String fecha_incidente, String hora_incidente, String evidencia, int fuente_id, int anulado) {

        this.tipo_incidente = tipo_incidente;
        this.tipo_via = tipo_via;
        this.descripcion_incidente = descripcion_incidente;
        this.latitud = latitud;
        this.longitud = longitud;
        this.fecha_incidente = fecha_incidente;
        this.hora_incidente = hora_incidente;
        this.evidencia = evidencia;
        this.fuente_id = fuente_id;
        this.anulado = anulado;
    }

    public Incidente(int id, int tipo_incidente, int tipo_via, String descripcion_incidente, String latitud, String longitud, String fecha_incidente, String hora_incidente, String evidencia, int fuente_id, int anulado) {
        this.id = id;
        this.tipo_incidente = tipo_incidente;
        this.tipo_via = tipo_via;
        this.descripcion_incidente = descripcion_incidente;
        this.latitud = latitud;
        this.longitud = longitud;
        this.fecha_incidente = fecha_incidente;
        this.hora_incidente = hora_incidente;
        this.evidencia = evidencia;
        this.fuente_id = fuente_id;
        this.anulado = anulado;
    }

    public Incidente() {

    }
    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public int getTipo_incidente() {
        return tipo_incidente;
    }

    public void setTipo_incidente(int tipo_incidente) {
        this.tipo_incidente = tipo_incidente;
    }

    public int getTipo_via() {
        return tipo_via;
    }

    public void setTipo_via(int tipo_via) {
        this.tipo_via = tipo_via;
    }

    public String getDescripcion_incidente() {
        return descripcion_incidente;
    }

    public void setDescripcion_incidente(String descripcion_incidente) {
        this.descripcion_incidente = descripcion_incidente;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getFecha_incidente() {
        return fecha_incidente;
    }

    public void setFecha_incidente(String fecha_incidente) {
        this.fecha_incidente = fecha_incidente;
    }

    public String getHora_incidente() {
        return hora_incidente;
    }

    public void setHora_incidente(String hora_incidente) {
        this.hora_incidente = hora_incidente;
    }

    public String getEvidencia() {
        return evidencia;
    }

    public void setEvidencia(String evidencia) {
        this.evidencia = evidencia;
    }

    public int getFuente_id() {
        return fuente_id;
    }

    public void setFuente_id(int fuente_id) {
        this.fuente_id = fuente_id;
    }

    public int getAnulado() {
        return anulado;
    }

    public void setAnulado(int anulado) {
        this.anulado = anulado;
    }

}