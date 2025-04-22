package sistema.saga.sagabackend.model;

import org.springframework.stereotype.Component;
import sistema.saga.sagabackend.repository.AnoLetivoDAO;
import sistema.saga.sagabackend.repository.Conexao;

import java.time.LocalDate;

@Component
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

    public int buscarAnoLetivo(Conexao conexao) {
        AnoLetivoDAO dao = new AnoLetivoDAO();
        return dao.getAnoLetivo(this, conexao);
    }

}
