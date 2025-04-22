package sistema.saga.sagabackend.model;

public class Turma {
    private AnoLetivo anoLetivo;
    private Serie serie;
    private char letra;

    public Turma(AnoLetivo anoLetivo, Serie serie, char letra) {
        this.anoLetivo = anoLetivo;
        this.serie = serie;
        this.letra = letra;
    }

    public Turma() {
        this(null,null,'z');
    }

    public AnoLetivo getAnoLetivo() {
        return anoLetivo;
    }

    public void setAnoLetivo(AnoLetivo anoLetivo) {
        this.anoLetivo = anoLetivo;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public char getLetra() {
        return letra;
    }

    public void setLetra(char letra) {
        this.letra = letra;
    }
}
