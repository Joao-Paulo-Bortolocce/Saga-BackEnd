package sistema.saga.sagabackend.model;

import sistema.saga.sagabackend.repository.Conexao;
import sistema.saga.sagabackend.repository.GraduacaoDAO;

import java.time.LocalDate;
import java.util.List;

public class Graduacao {
    private int id;
    private String descricao;
    private LocalDate data;

    public Graduacao() {
        this(0,"",null);
    }

    public Graduacao(String descricao, LocalDate data) {
        this.descricao = descricao;
        this.data = data;
    }

    public Graduacao(int id, String descricao, LocalDate data) {
        this.id = id;
        this.descricao = descricao;
        this.data = data;
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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
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
