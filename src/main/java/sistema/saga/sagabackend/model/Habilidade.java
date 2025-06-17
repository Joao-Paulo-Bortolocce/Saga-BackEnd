package sistema.saga.sagabackend.model;

import org.springframework.stereotype.Component;
import sistema.saga.sagabackend.repository.Conexao;
import sistema.saga.sagabackend.repository.HabilidadeDAO;

import java.util.List;

@Component
public class Habilidade {
    private int habilidades_cod;
    private String habilidades_descricao;
    private int habilidades_materia_id;
    private int habilidades_serie_id;

    public Habilidade() {
    }

    public Habilidade(int habilidades_cod) {
        this.habilidades_cod = habilidades_cod;
    }

    public Habilidade(int habilidades_cod , int habilidades_materia_id, int habilidades_serie_id) {
        this.habilidades_cod = habilidades_cod;
        this.habilidades_materia_id = habilidades_materia_id;
        this.habilidades_serie_id = habilidades_serie_id;
    }

    public Habilidade(int habilidades_cod, String habilidades_descricao, int habilidades_materia_id) {
        this.habilidades_cod = habilidades_cod;
        this.habilidades_descricao = habilidades_descricao;
        this.habilidades_materia_id = habilidades_materia_id;

    }

    public Habilidade(int habilidades_cod, int habilidades_materia_id) {
        this.habilidades_cod = habilidades_cod;
        this.habilidades_materia_id = habilidades_materia_id;
    }

    public Habilidade(int habilidades_cod, String habilidades_descricao, int habilidades_materia_id, int habilidades_serie_id) {
        this.habilidades_cod = habilidades_cod;
        this.habilidades_descricao = habilidades_descricao;
        this.habilidades_materia_id = habilidades_materia_id;
        this.habilidades_serie_id = habilidades_serie_id;
    }

    public int getCod() {
        return habilidades_cod;
    }

    public void setCod(int habilidades_cod) {
        this.habilidades_cod = habilidades_cod;
    }

    public String getDescricao() {
        return habilidades_descricao;
    }

    public void setDescricao(String habilidades_descricao) {
        this.habilidades_descricao = habilidades_descricao;
    }

    public int getMateria_id() {
        return habilidades_materia_id;
    }

    public void setMateria_id(int habilidades_materia_id) {
        this.habilidades_materia_id = habilidades_materia_id;
    }

    public int getHabilidades_serie_id() {
        return habilidades_serie_id;
    }

    public void setHabilidades_serie_id(int habilidades_serie_id) {
        this.habilidades_serie_id = habilidades_serie_id;
    }

    public boolean gravarHabilidade(Conexao conexao) {
        HabilidadeDAO habilidadeDAO = new HabilidadeDAO();
        return habilidadeDAO.gravar(this, conexao);
    }

    public boolean alterarHabilidade(Conexao conexao) {
        HabilidadeDAO habilidadeDAO = new HabilidadeDAO();
        return habilidadeDAO.alterar(this, conexao);
    }

    public boolean deletarHabMatSer(Conexao conexao) {
        HabilidadeDAO habilidadeDAO = new HabilidadeDAO();
        return habilidadeDAO.deletarMatSer(this, conexao);
    }

    public boolean deletarHabilidade(Conexao conexao) {
        HabilidadeDAO habilidadeDAO = new HabilidadeDAO();
        return habilidadeDAO.deletarHabilidade(this.habilidades_cod, conexao);
    }

    public List<Habilidade> buscarHabiliMatSer(Conexao conexao) {
        HabilidadeDAO habiliadadeDAO = new HabilidadeDAO();
        return habiliadadeDAO.getHabilidadeMS(this.getMateria_id(), this.getHabilidades_serie_id(), conexao);
    }

    public List<Habilidade> buscarHabiliMat(Conexao conexao) {
        HabilidadeDAO habiliadadeDAO = new HabilidadeDAO();
        return habiliadadeDAO.getHabilidadeM(this.getMateria_id(), conexao);
    }

    public List<Habilidade> buscarHabiliSer(Conexao conexao) {
        HabilidadeDAO habilidadeDAO = new HabilidadeDAO();
        return habilidadeDAO.getHabilidadesS(this.getHabilidades_serie_id() ,conexao);
    }
}
