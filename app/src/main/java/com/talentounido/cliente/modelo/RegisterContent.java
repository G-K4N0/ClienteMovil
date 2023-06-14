package com.talentounido.cliente.modelo;

public class RegisterContent {
    private int idHorario;
    private int idActividad;
    private String labName;

    public RegisterContent(int idHorario, int idActividad, String labName) {
        this.idHorario = idHorario;
        this.idActividad = idActividad;
        this.labName = labName;
    }

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public int getActividad() {
        return idActividad;
    }

    public void setActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }
}
