package sistema.saga.sagabackend.model;

import org.springframework.stereotype.Component;
import sistema.saga.sagabackend.repository.Conexao;
import sistema.saga.sagabackend.repository.SalaDAO;

import java.util.List;

@Component
public class Sala {
    private int id;
    private String descricao;
    private int ncarteiras;

    public Sala() {
        this(0,0,"");
    }

    public Sala(int ncarteiras, String descricao) {
        this.ncarteiras = ncarteiras;
        this.descricao = descricao;
    }

    public Sala(int id,int ncarteiras,  String descricao) {
        this.id = id;
        this.descricao = descricao;
        this.ncarteiras = ncarteiras;
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

    public int getNcarteiras() {
        return ncarteiras;
    }

    public void setNcarteiras(int ncarteiras) {
        this.ncarteiras = ncarteiras;
    }

    public boolean apagar(Conexao conexao) {
        SalaDAO salaDAO = new SalaDAO();
        return salaDAO.apagar(this,conexao);
    }

    public int buscaSerie(Conexao conexao){
        SalaDAO salaDAO = new SalaDAO();
        return salaDAO.getSala(this,conexao);
    }

    public List<Sala> buscarTodos(Conexao conexao) {
        SalaDAO salaDAO = new SalaDAO();
        return salaDAO.get("",conexao);
    }

    public boolean alterar(Conexao conexao) {
        SalaDAO salaDAO = new SalaDAO();
        return salaDAO.alterar(this,conexao);
    }

    public boolean gravar(Conexao conexao) {
        SalaDAO salaDAO = new SalaDAO();
        return salaDAO.gravar(this,conexao);
    }
}
