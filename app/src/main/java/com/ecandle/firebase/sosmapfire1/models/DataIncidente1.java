package com.ecandle.firebase.sosmapfire1.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by juantomaylla on 15/01/17.
 */
@IgnoreExtraProperties
public class DataIncidente1 {

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

    public DataIncidente1(String tipo_incidente, String tipo_incidente_img, String tipo_via, String descripcion_incidente, String latitud, String longitud, String fecha_incidente, String hora_incidente, String evidencia, String foto_url) {

        this.tipo_incidente = tipo_incidente;
        this.tipo_incidente_img = tipo_incidente_img;
        this.tipo_via = tipo_via;
        this.descripcion_incidente = descripcion_incidente;
        this.latitud = latitud;
        this.longitud = longitud;
        this.fecha_incidente = fecha_incidente;
        this.hora_incidente = hora_incidente;
        this.evidencia = evidencia;
        this.foto_url = foto_url;
    }
    public DataIncidente1(){

    }
}
