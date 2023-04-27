package modelo;

public class Reporte {
    private String titulo;
    private String problema;
    private int idUsuario;

    public Reporte(String nota, String sugerencia, int idUsuario) {
        this.titulo = nota;
        this.problema = sugerencia;
        this.idUsuario = idUsuario;
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
