package sistema.saga.sagabackend.model;

import sistema.saga.sagabackend.repository.AvaliacaoDaMatriculaDAO;
import sistema.saga.sagabackend.repository.Conexao;

import java.util.List;

public class AvaliacaoDaMatricula {
    private int avaMatId;
    private int avaHabId;
    private String avaAv;

    public AvaliacaoDaMatricula() {
    }

    public AvaliacaoDaMatricula(int avaMatId) {
        this.avaMatId = avaMatId;
    }

    public AvaliacaoDaMatricula(int avaMatId, int avaHabId) {
        this.avaMatId = avaMatId;
        this.avaHabId = avaHabId;
    }

    public AvaliacaoDaMatricula(int avaHabId, String avaAv) {
        this.avaHabId = avaHabId;
        this.avaAv = avaAv;
    }

    public AvaliacaoDaMatricula(int avaMatId, int avaHabId, String avaAv) {
        this.avaMatId = avaMatId;
        this.avaHabId = avaHabId;
        this.avaAv = avaAv;
    }

    public int getAvaMatId() {
        return avaMatId;
    }

    public void setAvaMatId(int avaMatId) {
        this.avaMatId = avaMatId;
    }

    public int getAvaHabId() {
        return avaHabId;
    }

    public void setAvaHabId(int avaHabId) {
        this.avaHabId = avaHabId;
    }

    public String getAvaAv() {
        return avaAv;
    }

    public void setAvaAv(String avaAv) {
        this.avaAv = avaAv;
    }

    public boolean gravar(int arrayInt[], String arrayString[], Conexao conexao) {
        AvaliacaoDaMatriculaDAO avaliacaoDaMatriculaDAO = new AvaliacaoDaMatriculaDAO();
        return avaliacaoDaMatriculaDAO.gravar(this.avaMatId, arrayInt, arrayString, conexao);
    }

    public boolean alterar(Conexao conexao) {
        AvaliacaoDaMatriculaDAO avaliacaoDaMatriculaDAO = new AvaliacaoDaMatriculaDAO();
        return avaliacaoDaMatriculaDAO.alterar(this, conexao);
    }

    public boolean apagar(Conexao conexao) {
        AvaliacaoDaMatriculaDAO avaliacaoDaMatriculaDAO = new AvaliacaoDaMatriculaDAO();
        return avaliacaoDaMatriculaDAO.apagar(this, conexao);
    }

    public List<AvaliacaoDaMatricula> recuperaTodos(Conexao conexao) {
        AvaliacaoDaMatriculaDAO avaliacaoDaMatriculaDAO = new AvaliacaoDaMatriculaDAO();
        return avaliacaoDaMatriculaDAO.recuperaTodos(conexao);
    }

    public List<AvaliacaoDaMatricula> recuperaAvaliacoes(Conexao conexao) {
        AvaliacaoDaMatriculaDAO avaliacaoDaMatriculaDAO = new AvaliacaoDaMatriculaDAO();
        return avaliacaoDaMatriculaDAO.recuperaAvaliacoes(this.avaMatId, conexao);
    }

    public AvaliacaoDaMatricula recuperaAvaliacao(Conexao conexao) {
        AvaliacaoDaMatriculaDAO avaliacaoDaMatriculaDAO = new AvaliacaoDaMatriculaDAO();
        return avaliacaoDaMatriculaDAO.recuperaUmaAvaliacao(this, conexao);
    }
}
