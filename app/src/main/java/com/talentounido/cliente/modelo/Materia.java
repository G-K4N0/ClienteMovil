package com.talentounido.cliente.modelo;

public class Materia {
    private int id;
    private String materia;

    public Materia(int id, String materia) {
        this.id = id;
        this.materia = materia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }
}
