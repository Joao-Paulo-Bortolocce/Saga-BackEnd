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

    public Habilidade() {
    }

    public Habilidade(int habilidades_materia_id) {
        this.habilidades_materia_id = habilidades_materia_id;
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

    public boolean gravarHabilidade(Conexao conexao) {
        HabilidadeDAO habilidadeDAO = new HabilidadeDAO();
        return habilidadeDAO.gravar(this, conexao);
    }

    public boolean alterarHabilidade(Conexao conexao) {
        HabilidadeDAO habilidadeDAO = new HabilidadeDAO();
        return habilidadeDAO.alterar(this, conexao);
    }

    public boolean deletarHabilidade(Conexao conexao) {
        HabilidadeDAO habilidadeDAO = new HabilidadeDAO();
        return habilidadeDAO.deletar(this, conexao);
    }

    public List<Habilidade> buscarHabiliMatSer(int idMat, int idSer, Conexao conexao) {
        HabilidadeDAO habiliadadeDAO = new HabilidadeDAO();
        return habiliadadeDAO.getHabilidadeMS(idMat, idSer, conexao);
    }

    public List<Habilidade> buscarHabiliMat(int idMat, Conexao conexao) {
        HabilidadeDAO habiliadadeDAO = new HabilidadeDAO();
        return habiliadadeDAO.getHabilidadeM(idMat, conexao);
    }
}
