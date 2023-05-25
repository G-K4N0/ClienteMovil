package com.talentounido.cliente.modelo;

public class RegisterContent {
    private int idHorario;
    private String actividad;
    private String labName;

    public RegisterContent(int idHorario, String actividad, String labName) {
        this.idHorario = idHorario;
        this.actividad = actividad;
        this.labName = labName;
    }

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }
}
