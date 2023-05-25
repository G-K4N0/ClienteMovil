package com.talentounido.cliente.modelo;

public class Reporte {
    private String laboratorio;
    private String titulo;
    private String problema;
    private int idUsuario;

    public Reporte( String laboratorio,String titulo, String problema, int idUsuario) {
        this.titulo = titulo;
        this.problema = problema;
        this.idUsuario = idUsuario;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getProblema() {
        return problema;
    }

    public void setProblema(String problema) {
        this.problema = problema;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
