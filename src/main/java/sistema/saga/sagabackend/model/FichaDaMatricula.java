package sistema.saga.sagabackend.model;

import sistema.saga.sagabackend.repository.Conexao;
import sistema.saga.sagabackend.repository.FichaDaMatriculaDAO;
import sistema.saga.sagabackend.repository.FichaDaMatriculaDAO;

import java.util.List;

public class FichaDaMatricula {
    private Matricula matricula;
    private Ficha ficha;
    private String observacao;
    private int status;

    public FichaDaMatricula(Matricula matricula, Ficha ficha, String observacao, int status) {
        this.matricula = matricula;
        this.ficha = ficha;
        this.observacao = observacao;
        this.status = status;
    }

    public FichaDaMatricula() {
        this(null,null,null,0);
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public Ficha getFicha() {
        return ficha;
    }

    public void setFicha(Ficha ficha) {
        this.ficha = ficha;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean gravar(Conexao conexao) {
        FichaDaMatriculaDAO fichaDAO = new FichaDaMatriculaDAO();
        return fichaDAO.gravar(this, conexao);
    }

    public boolean alterar(Conexao conexao) {
        FichaDaMatriculaDAO fichaDAO = new FichaDaMatriculaDAO();
        return fichaDAO.alterar(this, conexao);
    }

    public List<FichaDaMatricula> buscarTodas(int validada,Conexao conexao) {
        FichaDaMatriculaDAO fichaDAO = new FichaDaMatriculaDAO();
        return fichaDAO.getAll(validada,conexao);
    }

    public boolean apagar(Conexao conexao) {
        FichaDaMatriculaDAO fichaDAO = new FichaDaMatriculaDAO();
        return fichaDAO.apagar(this, conexao);
    }
}
