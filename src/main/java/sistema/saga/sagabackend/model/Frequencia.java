package sistema.saga.sagabackend.model;

import org.springframework.stereotype.Component;
import sistema.saga.sagabackend.repository.Conexao;
import sistema.saga.sagabackend.repository.FrequenciaDAO;

import java.time.LocalDate;
import java.util.List;

@Component
public class Frequencia {
    private Matricula  matricula;
    private boolean presente;
    private LocalDate data;

    public Frequencia(Matricula matricula, boolean presente, LocalDate data) {
        this.matricula = matricula;
        this.presente = presente;
        this.data = data;
    }

    public Frequencia() {
        this(null, false, null);
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public boolean isPresente() {
        return presente;
    }

    public void setPresente(boolean presente) {
        this.presente = presente;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public boolean gravar(Conexao conexao) {
        FrequenciaDAO frequenciaDAO = new FrequenciaDAO();
        return frequenciaDAO.gravar(this,conexao);
    }

    public List<Frequencia> buscar(Conexao conexao) {
        FrequenciaDAO frequenciaDAO = new FrequenciaDAO();
        return frequenciaDAO.buscarId(this.matricula.getId(),conexao);
    }
}
