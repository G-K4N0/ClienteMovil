package modelo;

public class HorarioActual {
    private int id;
    private String inicio;
    private String fin;
    private String labNombre;
    private String materia;
    private String docente;
    private boolean isOcupado;

    public HorarioActual(int id, String inicio, String fin, String labNombre, String materia, String docente, boolean isOcupado) {
        this.id = id;
        this.inicio = inicio;
        this.fin = fin;
        this.labNombre = labNombre;
        this.materia = materia;
        this.docente = docente;
        this.isOcupado = isOcupado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
