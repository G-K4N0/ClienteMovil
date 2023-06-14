package com.talentounido.cliente.modelo;

public class Reporte {
    private int laboratorio;
    private String titulo;
    private String problema;
    private int idUsuario;

    public Reporte( int laboratorio,String titulo, String problema, int idUsuario) {
        this.titulo = titulo;
        this.problema = problema;
        this.idUsuario = idUsuario;
        this.laboratorio = laboratorio;
    }

    public int getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(int laboratorio) {
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
