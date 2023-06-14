package com.talentounido.cliente.modelo;

public class Actividad {
    private int id;
    private String actividad;

    public Actividad(int id, String actividad) {
        this.id = id;
        this.actividad = actividad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }
}
