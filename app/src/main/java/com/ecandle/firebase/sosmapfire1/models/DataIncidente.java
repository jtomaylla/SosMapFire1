package com.ecandle.firebase.sosmapfire1.models;

/**
 * Created by juantomaylla on 15/01/17.
 */

public class DataIncidente {

    public String incidente_id;
    public String tipo_incidente;
    public String tipo_incidente_img;
    public String tipo_via;
    public String descripcion_incidente;
    public String latitud;
    public String longitud;
    public String fecha_incidente;
    public String hora_incidente;
    public String evidencia;
    public String foto_url;

    public DataIncidente(String incidente_id, String tipo_incidente, String tipo_incidente_img, String tipo_via,
                         String descripcion_incidente, String latitud, String longitud, String fecha_incidente,
                         String hora_incidente, String evidencia, String foto_url) {
        this.incidente_id = incidente_id;
        this.tipo_incidente = tipo_incidente;
        this.tipo_incidente_img = tipo_incidente_img;
        this.tipo_via = tipo_via;
        this.descripcion_incidente = descripcion_incidente;
        this.latitud = latitud;
        this.longitud = longitud;
        this.fecha_incidente = fecha_incidente;
        this.hora_incidente = hora_incidente;
        this.evidencia = evidencia;
        this.foto_url=foto_url;
    }
    public DataIncidente(){

    }
    public String getIncidente_id() {
        return incidente_id;
    }

    public void setIncidente_id(String incidente_id) {
        this.incidente_id = incidente_id;
    }

    public String getTipo_incidente() {
        return tipo_incidente;
    }

    public void setTipo_incidente(String tipo_incidente) {
        this.tipo_incidente = tipo_incidente;
    }

    public String getTipo_incidente_img() {
        return tipo_incidente_img;
    }

    public void setTipo_incidente_img(String tipo_incidente_img) {
        this.tipo_incidente_img = tipo_incidente_img;
    }

    public String getTipo_via() {
        return tipo_via;
    }

    public void setTipo_via(String tipo_via) {
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

    public String getFoto_url() {
        return foto_url;
    }

    public void setFoto_url(String foto_url) {
        this.foto_url = foto_url;
    }
}
