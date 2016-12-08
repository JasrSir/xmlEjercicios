package com.jasrsir.xmljasr.clases;

/**
 * Created by jasrsir on 8/12/16.
 */

public class Bizi {
    private String id;
    private String title;
    private String estado;
    private String uri;
    private String bicisDisponibles;
    private String anclajesDisponibles;
    private String lastUpdate;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getBicisDisponibles() {
        return bicisDisponibles;
    }

    public void setBicisDisponibles(String bicisDisponibles) {
        this.bicisDisponibles = bicisDisponibles;
    }

    public String getAnclajesDisponibles() {
        return anclajesDisponibles;
    }

    public void setAnclajesDisponibles(String anclajesDisponibles) {
        this.anclajesDisponibles = anclajesDisponibles;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
