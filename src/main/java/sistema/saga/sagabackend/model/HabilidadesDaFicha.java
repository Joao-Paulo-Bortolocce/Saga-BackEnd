package sistema.saga.sagabackend.model;

import sistema.saga.sagabackend.repository.Conexao;
import sistema.saga.sagabackend.repository.HabilidadesDaFichaDAO;

import java.util.List;

public class HabilidadesDaFicha {
    int habilidadesDaFicha_id;
    int habilidadesDaFicha_habilidades_id;
    int habilidadesDaFicha_ficha_id;

    public HabilidadesDaFicha() {
    }

    public HabilidadesDaFicha(int habilidadesDaFicha_id) {
        this.habilidadesDaFicha_id = habilidadesDaFicha_id;
    }

    public HabilidadesDaFicha(int habilidadesDaFicha_habilidades_id, int habilidadesDaFicha_ficha_id) {
        this.habilidadesDaFicha_habilidades_id = habilidadesDaFicha_habilidades_id;
        this.habilidadesDaFicha_ficha_id = habilidadesDaFicha_ficha_id;
    }

    public HabilidadesDaFicha(int habilidadesDaFicha_id, int habilidadesDaFicha_habilidades_id, int habilidadesDaFicha_ficha_id) {
        this.habilidadesDaFicha_id = habilidadesDaFicha_id;
        this.habilidadesDaFicha_habilidades_id = habilidadesDaFicha_habilidades_id;
        this.habilidadesDaFicha_ficha_id = habilidadesDaFicha_ficha_id;
    }

    public int getHabilidadesDaFicha_id() {
        return habilidadesDaFicha_id;
    }

    public void setHabilidadesDaFicha_id(int habilidadesDaFicha_id) {
        this.habilidadesDaFicha_id = habilidadesDaFicha_id;
    }

    public int getHabilidadesDaFicha_habilidades_id() {
        return habilidadesDaFicha_habilidades_id;
    }

    public void setHabilidadesDaFicha_habilidades_id(int habilidadesDaFicha_habilidades_id) {
        this.habilidadesDaFicha_habilidades_id = habilidadesDaFicha_habilidades_id;
    }

    public int getHabilidadesDaFicha_ficha_id() {
        return habilidadesDaFicha_ficha_id;
    }

    public void setHabilidadesDaFicha_ficha_id(int habilidadesDaFicha_ficha_id) {
        this.habilidadesDaFicha_ficha_id = habilidadesDaFicha_ficha_id;
    }

    public boolean gravar(Conexao conexao) {
        HabilidadesDaFichaDAO habilidadesDaFichaDAO = new HabilidadesDaFichaDAO();
        return habilidadesDaFichaDAO.gravar(this, conexao);
    }

    public boolean alterar(Conexao conexao) {
        HabilidadesDaFichaDAO habilidadesDaFichaDAO = new HabilidadesDaFichaDAO();
        return habilidadesDaFichaDAO.alterar(this, conexao);
    }

    public List<HabilidadesDaFicha> buscarTodas(Conexao conexao) {
        HabilidadesDaFichaDAO habilidadesDaFichaDAO = new HabilidadesDaFichaDAO();
        return habilidadesDaFichaDAO.getHabilidadesDaFicha(conexao);
    }

    public List<HabilidadesDaFicha> buscarTodasHabFicha(int idFicha, Conexao conexao) {
        HabilidadesDaFichaDAO habilidadesDaFichaDAO = new HabilidadesDaFichaDAO();
        return habilidadesDaFichaDAO.getHabilidadesDaFichaId(idFicha, conexao);
    }

    public boolean apagar(Conexao conexao) {
        HabilidadesDaFichaDAO habilidadesDaFichaDAO = new HabilidadesDaFichaDAO();
        return habilidadesDaFichaDAO.apagar(this.getHabilidadesDaFicha_habilidades_id(), this.getHabilidadesDaFicha_ficha_id(), conexao);
    }
}
