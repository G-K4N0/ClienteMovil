package com.talentounido.cliente.modelo;

public class HorarioActual {
    private String inicio;
    private String fin;
    private String labNombre;
    private String materia;
    private String docente;
    private String carrera;
    private String grupo;
    private String image;
    private boolean isOcupado;

    public HorarioActual(String inicio, String fin, String labNombre, String materia, String carrera, String grupo, String docente, String image, boolean isOcupado) {
        this.inicio = inicio;
        this.fin = fin;
        this.labNombre = labNombre;
        this.materia = materia;
        this.grupo = grupo;
        this.docente = docente;
        this.carrera = carrera;
        this.image = image;
        this.isOcupado = isOcupado;
    }
    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public String getLabNombre() {
        return labNombre;
    }

    public void setLabNombre(String labNombre) {
        this.labNombre = labNombre;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getDocente() {
        return docente;
    }

    public void setDocente(String docente) {
        this.docente = docente;
    }

    public boolean isOcupado() {
        return isOcupado;
    }

    public void setOcupado(boolean ocupado) {
        this.isOcupado = ocupado;
    }
    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
