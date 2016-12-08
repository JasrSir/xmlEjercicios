package com.jasrsir.xmljasr.clases;

import android.media.Image;
import android.widget.ImageView;

/**
 * Created by jasrsir on 1/12/16.
 */

public class Weather {
    private String periodo;
    private String precipiacion;
    private String estadoCielo;
    private String tempMax;
    private String tempMin;
    private String temp;
    private String imagen;


    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo + " h";
    }

    public String getPrecipiacion() {
        return precipiacion;
    }

    public void setPrecipiacion(String precipiacion) {
        this.precipiacion = "Precipitaciones: "  + precipiacion + " %";
    }

    public String getEstadoCielo() {
        return estadoCielo;
    }

    public void setEstadoCielo(String estadoCielo) {
        this.estadoCielo = estadoCielo;
    }

    public String getTempMax() {
        return tempMax;
    }

    public void setTempMax(String tempMax) {
        this.tempMax = "Máxima de " +tempMax + " ºC";
    }

    public String getTempMin() {
        return tempMin;
    }

    public void setTempMin(String tempMin) {
        this.tempMin = "Mínima de " +tempMin + " ºC";
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp + " ºC";
    }


    public String  getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = "http://www.aemet.es/imagenes/png/estado_cielo/"+ imagen+"_g.png";
    }


}
