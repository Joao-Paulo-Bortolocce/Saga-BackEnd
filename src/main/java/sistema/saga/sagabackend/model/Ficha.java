package sistema.saga.sagabackend.model;

import sistema.saga.sagabackend.repository.Conexao;
import sistema.saga.sagabackend.repository.FichaDAO;

import java.util.List;

public class Ficha {

    int ficha_id;
    int ficha_bimestre_id;
    int ficha_bimestre_anoLetivo_id;
    int ficha_bimestre_serie_id;

    public Ficha() {
        this.ficha_id = 0;
        this.ficha_bimestre_id = 0;
        this.ficha_bimestre_anoLetivo_id = 0;
        this.ficha_bimestre_serie_id = 0;
    }

    public Ficha(int ficha_id) {
        this(ficha_id, 0, 0, 0);
    }

    public Ficha(int ficha_id, int ficha_bimestre_id, int ficha_bimestre_anoLetivo_id, int ficha_bimestre_serie_id) {
        this.ficha_id = ficha_id;
        this.ficha_bimestre_id = ficha_bimestre_id;
        this.ficha_bimestre_anoLetivo_id = ficha_bimestre_anoLetivo_id;
        this.ficha_bimestre_serie_id = ficha_bimestre_serie_id;
    }

    public int getFicha_id() {
        return ficha_id;
    }

    public void setFicha_id(int ficha_id) {
        this.ficha_id = ficha_id;
    }

    public int getFicha_bimestre_id() {
        return ficha_bimestre_id;
    }

    public void setFicha_bimestre_id(int ficha_bimestre_id) {
        this.ficha_bimestre_id = ficha_bimestre_id;
    }

    public int getFicha_bimestre_anoLetivo_id() {
        return ficha_bimestre_anoLetivo_id;
    }

    public void setFicha_bimestre_anoLetivo_id(int ficha_bimestre_anoLetivo_id) {
        this.ficha_bimestre_anoLetivo_id = ficha_bimestre_anoLetivo_id;
    }

    public int getFicha_bimestre_serie_id() {
        return ficha_bimestre_serie_id;
    }

    public void setFicha_bimestre_serie_id(int ficha_bimestre_serie_id) {
        this.ficha_bimestre_serie_id = ficha_bimestre_serie_id;
    }

    public boolean gravar(Conexao conexao) {
        FichaDAO fichaDAO = new FichaDAO();
        return fichaDAO.gravar(this, conexao);
    }

    public boolean alterar(Conexao conexao) {
        FichaDAO fichaDAO = new FichaDAO();
        return fichaDAO.alterar(this, conexao);
    }

    public List<Ficha> buscarTodas(Conexao conexao) {
        FichaDAO fichaDAO = new FichaDAO();
        return fichaDAO.getAll(conexao);
    }

    public boolean apagar(Conexao conexao) {
        FichaDAO fichaDAO = new FichaDAO();
        return fichaDAO.apagar(this.getFicha_id(), conexao);
    }
}
