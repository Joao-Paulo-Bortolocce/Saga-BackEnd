package sistema.saga.sagabackend.model;

import sistema.saga.sagabackend.repository.Conexao;
import sistema.saga.sagabackend.repository.ReuniaoDAO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Reuniao {
    private int reuniaoId;
    private LocalDateTime reuniaoData;
    private Turma turma;
    private Serie  serie;
    private AnoLetivo anoLetivo;
    private String reuniaoTipo;

    public Reuniao() {
    }

    public Reuniao(int reuniaoId, String reuniaoTipo, AnoLetivo anoLetivo, Serie serie, Turma turma, LocalDateTime reuniaoData) {
        this.reuniaoId = reuniaoId;
        this.reuniaoTipo = reuniaoTipo;
        this.anoLetivo = anoLetivo;
        this.serie = serie;
        this.turma = turma;
        this.reuniaoData = reuniaoData;
    }

    public int getReuniaoId() {
        return reuniaoId;
    }

    public void setReuniaoId(int reuniaoId) {
        this.reuniaoId = reuniaoId;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public LocalDateTime getReuniaoData() {
        return reuniaoData;
    }

    public void setReuniaoData(LocalDateTime reuniaoData) {
        this.reuniaoData = reuniaoData;
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

    public String getReuniaoTipo() {
        return reuniaoTipo;
    }

    public void setReuniaoTipo(String reuniaoTipo) {
        this.reuniaoTipo = reuniaoTipo;
    }

    public boolean gravar(Conexao conexao) {
        ReuniaoDAO reuniaoDAO = new ReuniaoDAO();
        return reuniaoDAO.gravar(this, conexao);
    }

    public List<Reuniao> buscarTodos(Conexao conexao) {
        ReuniaoDAO reuniaoDAO = new ReuniaoDAO();
        return reuniaoDAO.get("", conexao);
    }

    public List<Reuniao> buscarPorTermo(String termo, Conexao conexao) {
        ReuniaoDAO reuniaoDAO = new ReuniaoDAO();
        return reuniaoDAO.get(termo, conexao);
    }

    public boolean alterar(Conexao conexao) {
        ReuniaoDAO reuniaoDAO = new ReuniaoDAO();
        return reuniaoDAO.alterar(this, conexao);
    }

    public boolean apagar(Conexao conexao) {
        ReuniaoDAO reuniaoDAO = new ReuniaoDAO();
        return reuniaoDAO.apagar(this, conexao);
    }
}
