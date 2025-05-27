package sistema.saga.sagabackend.model;

import org.springframework.stereotype.Component;
import sistema.saga.sagabackend.repository.Conexao;
import sistema.saga.sagabackend.repository.TurmaDAO;

import java.util.List;

@Component
public class Turma {
    private AnoLetivo anoLetivo;
    private Serie serie;
    private char letra;
    private Profissional profissional;
    private Sala sala;

    public Turma(AnoLetivo anoLetivo, Serie serie, char letra, Profissional profissional, Sala sala) {
        this.anoLetivo = anoLetivo;
        this.serie = serie;
        this.letra = letra;
        this.profissional = profissional;
        this.sala = sala;
    }

    public Profissional getProfissional() {
        return profissional;
    }

    public void setProfissional(Profissional profissional) {
        this.profissional = profissional;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Turma() {
        this(null,null,'z',null,null);
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

    public boolean apagar(Conexao conexao) {
        TurmaDAO turmaDAO = new TurmaDAO();
        return turmaDAO.apagar(this,conexao);
    }

    public static List<Turma> buscarTodos(Conexao conexao) {
        TurmaDAO turmaDAO = new TurmaDAO();
        return turmaDAO.get("", conexao);
    }

    public List<Turma> buscarPorTermo(String termo, Conexao conexao) {
        TurmaDAO turmaDAO = new TurmaDAO();
        return turmaDAO.get(termo, conexao);
    }


    public boolean gravar(Conexao conexao) { //abuu
        TurmaDAO turmaDAO = new TurmaDAO();
        return turmaDAO.gravar(this,conexao);
    }
}
