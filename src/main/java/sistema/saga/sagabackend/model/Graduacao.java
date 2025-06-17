package sistema.saga.sagabackend.model;

import sistema.saga.sagabackend.repository.Conexao;
import sistema.saga.sagabackend.repository.GraduacaoDAO;

import java.time.LocalDate;
import java.util.List;

public class Graduacao {
    private int id;
    private String descricao;

    public Graduacao() {
        this(0,"");
    }

    public Graduacao(String descricao) {
        this.descricao = descricao;
    }

    public Graduacao(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean apagar(Conexao conexao) {
        GraduacaoDAO graduacaoDAO = new GraduacaoDAO();
        return graduacaoDAO.apagar(this,conexao);
    }

    public int buscaGraduacao(Conexao conexao){
        GraduacaoDAO graduacaoDAO = new GraduacaoDAO();
        return graduacaoDAO.getGraduacao(this,conexao);
    }

    public List<Graduacao> buscarTodos(Conexao conexao) {
        GraduacaoDAO graduacaoDAO = new GraduacaoDAO();
        return graduacaoDAO.get("",conexao);
    }

    public boolean alterar(Conexao conexao) {
        GraduacaoDAO graduacaoDAO = new GraduacaoDAO();
        return graduacaoDAO.alterar(this,conexao);
    }

    public boolean gravar(Conexao conexao) {
        GraduacaoDAO graduacaoDAO = new GraduacaoDAO();
        return graduacaoDAO.gravar(this,conexao);
    }
}
