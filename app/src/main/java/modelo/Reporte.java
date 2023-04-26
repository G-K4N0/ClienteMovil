package modelo;

public class Reporte {
    private String nota;
    private String sugerencia;
    private int idUsuario;

    public Reporte(String nota, String sugerencia, int idUsuario) {
        this.nota = nota;
        this.sugerencia = sugerencia;
        this.idUsuario = idUsuario;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getSugerencia() {
        return sugerencia;
    }

    public void setSugerencia(String sugerencia) {
        this.sugerencia = sugerencia;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
