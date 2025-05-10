package sistema.saga.sagabackend.model;

import sistema.saga.sagabackend.repository.AvaliacaoDAO;
import sistema.saga.sagabackend.repository.Conexao;

import java.util.List;

public class Avaliacao
{
    private Integer avaliacao_cod;
    private Integer avaliacao_aluno_cod;
    private Integer avaliacao_serie_cod;
    private Integer avaliacao_materia_cod;
    private Integer avaliacao_habilidade_cod;
    private String avaliacao_descricao;

    public Avaliacao() {
    }

    public Avaliacao(Integer avaliacao_cod, Integer avaliacao_aluno_cod, Integer avaliacao_serie_cod, Integer avaliacao_materia_cod, Integer avaliacao_habilidade_cod, String avaliacao_descricao) {
        this.avaliacao_cod = 0;
        this.avaliacao_aluno_cod = avaliacao_aluno_cod;
        this.avaliacao_serie_cod = avaliacao_serie_cod;
        this.avaliacao_materia_cod = avaliacao_materia_cod;
        this.avaliacao_habilidade_cod = avaliacao_habilidade_cod;
        this.avaliacao_descricao = avaliacao_descricao;
    }

    public Integer getAvaliacao_cod() {
        return avaliacao_cod;
    }

    public void setAvaliacao_cod(Integer avaliacao_cod) {
        this.avaliacao_cod = avaliacao_cod;
    }

    public Integer getAvaliacao_aluno_cod() {
        return avaliacao_aluno_cod;
    }

    public void setAvaliacao_aluno_cod(Integer avaliacao_aluno_cod) {
        this.avaliacao_aluno_cod = avaliacao_aluno_cod;
    }

    public long getAvaliacao_serie_cod() {
        return avaliacao_serie_cod;
    }

    public void setAvaliacao_serie_cod(Integer avaliacao_serie_cod) {
        this.avaliacao_serie_cod = avaliacao_serie_cod;
    }

    public Integer getAvaliacao_materia_cod() {
        return avaliacao_materia_cod;
    }

    public void setAvaliacao_materia_cod(Integer avaliacao_materia_cod) {
        this.avaliacao_materia_cod = avaliacao_materia_cod;
    }

    public Integer getAvaliacao_habilidade_cod() {
        return avaliacao_habilidade_cod;
    }

    public void setAvaliacao_habilidade_cod(Integer avaliacao_habilidade_cod) {
        this.avaliacao_habilidade_cod = avaliacao_habilidade_cod;
    }

    public String getAvaliacao_descricao() {
        return avaliacao_descricao;
    }

    public void setAvaliacao_descricao(String avaliacao_descricao) {
        this.avaliacao_descricao = avaliacao_descricao;
    }

    public boolean gravarAvaliacao(Conexao conexao) {
        AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO();
        return avaliacaoDAO.gravar(this, conexao);
    }

    public List<Avaliacao> getAll(Conexao conexao) {
        AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO();
        return avaliacaoDAO.getAll(conexao);
    }

    public List<Avaliacao> getAlunoESerie(Avaliacao avaliacao, Conexao conexao) {
        AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO();
        return avaliacaoDAO.getPorAlunoESerie(avaliacao, conexao);
    }
}
