package sistema.saga.sagabackend.model;

import org.springframework.stereotype.Component;
import sistema.saga.sagabackend.repository.Conexao;
import sistema.saga.sagabackend.repository.SerieDAO;

import java.util.List;

@Component
public class Serie {
    private int serieId;
    private int serieNum;
    private String serieDescr;

    public Serie() {}

    public Serie(int serieNum, String serieDescr) {
        this.serieNum = serieNum;
        this.serieDescr = serieDescr;
    }

    public Serie(int serieId, int serieNum, String serieDescr) {
        this.serieId = serieId;
        this.serieNum = serieNum;
        this.serieDescr = serieDescr;
    }

    public int getSerieId() {
        return serieId;
    }

    public void setSerieId(int serieId) {
        this.serieId = serieId;
    }

    public int getSerieNum() {
        return serieNum;
    }

    public void setSerieNum(int serieNum) {
        this.serieNum = serieNum;
    }

    public String getSerieDescr() {
        return serieDescr;
    }

    public void setSerieDescr(String serieDescr) {
        this.serieDescr = serieDescr;
    }

    public boolean apagar(Conexao conexao) {
        SerieDAO serieDAO = new SerieDAO();
        return serieDAO.apagar(this,conexao);
    }

    public int buscaSerie(Conexao conexao){
        SerieDAO serieDAO = new SerieDAO();
        return serieDAO.getSerie(this,conexao);
    }

    public List<Serie> buscarTodos(Conexao conexao) {
        SerieDAO serieDAO = new SerieDAO();
        return serieDAO.get("",conexao);
    }

    public boolean alterar(Conexao conexao) {
        SerieDAO serieDAO = new SerieDAO();
        return serieDAO.alterar(this,conexao);
    }

    public boolean gravar(Conexao conexao) {
        SerieDAO serieDAO = new SerieDAO();
        return serieDAO.gravar(this,conexao);
    }
}
