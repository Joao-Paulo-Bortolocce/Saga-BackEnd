package sistema.saga.sagabackend.model;

import org.springframework.stereotype.Component;
import sistema.saga.sagabackend.repository.Conexao;
import sistema.saga.sagabackend.repository.AnoLetivoDAO;

import java.time.LocalDate;
import java.util.List;

public class AnoLetivo {
    private int id;
    private LocalDate inicio, fim;

    public AnoLetivo(int id, LocalDate inicio, LocalDate fim) {
        this.id = id;
        this.inicio = inicio;
        this.fim = fim;
    }

    public AnoLetivo(LocalDate inicio, LocalDate fim) {
        this(0,inicio,fim);
    }

    public AnoLetivo(int id) {
        this(id,null,null);
    }

    public AnoLetivo() {
        this(0,null,null);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getFim() {
        return fim;
    }

    public void setFim(LocalDate fim) {
        this.fim = fim;
    }

    public boolean apagar(Conexao conexao) {
        AnoLetivoDAO anoLetivoDAO = new AnoLetivoDAO();
        return anoLetivoDAO.apagar(this,conexao);
    }

    public int buscaAnos(Conexao conexao){
        AnoLetivoDAO anoLetivoDAO = new AnoLetivoDAO();
        return anoLetivoDAO.getAno(this,conexao);
    }

    public List<AnoLetivo> buscarTodos(Conexao conexao) {
        AnoLetivoDAO anoLetivoDAO = new AnoLetivoDAO();
        return anoLetivoDAO.get("",conexao);
    }

    public boolean alterar(Conexao conexao) {
        AnoLetivoDAO anoLetivoDAO = new AnoLetivoDAO();
        return anoLetivoDAO.alterar(this,conexao);
    }

    public boolean gravar(Conexao conexao) {
        AnoLetivoDAO anoLetivoDAO = new AnoLetivoDAO();
        return anoLetivoDAO.gravar(this,conexao);
    }
}
