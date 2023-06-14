package com.talentounido.cliente.modelo;

public class SinRegisterContent {
    private int idGrupo;
    private int idMateria;
    private String inicia;
    private String finaliza;
    private String dia;
    private int idActividad;
    private int idUsuario;
    private String lab;

    public SinRegisterContent(int idGrupo, int idMateria, String inicia, String finaliza, String dia, int idActividad, int idUsuario, String lab) {
        this.idGrupo = idGrupo;
        this.idMateria = idMateria;
        this.inicia = inicia;
        this.finaliza = finaliza;
        this.dia = dia;
        this.idActividad = idActividad;
        this.idUsuario = idUsuario;
        this.lab = lab;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public String getInicia() {
        return inicia;
    }

    public void setInicia(String inicia) {
        this.inicia = inicia;
    }

    public String getFinaliza() {
        return finaliza;
    }

    public void setFinaliza(String finaliza) {
        this.finaliza = finaliza;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getLab() {
        return lab;
    }

    public void setLab(String lab) {
        this.lab = lab;
    }
}
