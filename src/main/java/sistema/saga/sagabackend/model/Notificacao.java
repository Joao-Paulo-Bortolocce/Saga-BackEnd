package sistema.saga.sagabackend.model;

import org.springframework.stereotype.Component;
import sistema.saga.sagabackend.repository.Conexao;
import sistema.saga.sagabackend.repository.NotificacaoDAO;

import java.time.LocalDate;
import java.util.List;

@Component
public class Notificacao {
    private int not_id;
    private boolean not_visto;
    private String not_texto;
    private LocalDate not_data;

    public Notificacao() {
    }

    public Notificacao(String not_texto, LocalDate not_data) {
        this.not_texto = not_texto;
        this.not_data = not_data;
    }

    public Notificacao(int not_id, boolean not_visto, String not_texto,  LocalDate not_data) {
        this.not_id = not_id;
        this.not_visto = not_visto;
        this.not_texto = not_texto;
        this.not_data = not_data;
    }

    public int getNot_id() {
        return not_id;
    }

    public void setNot_id(int not_id) {
        this.not_id = not_id;
    }

    public boolean isNot_visto() {
        return not_visto;
    }

    public void setNot_visto(boolean not_visto) {
        this.not_visto = not_visto;
    }

    public String getNot_texto() {
        return not_texto;
    }

    public void setNot_texto(String not_texto) {
        this.not_texto = not_texto;
    }

    public LocalDate getNot_data() {
        return not_data;
    }

    public void setNot_data(LocalDate not_data) {
        this.not_data = not_data;
    }

    public boolean apagar(Conexao conexao) {
        NotificacaoDAO notificacaoDAO = new NotificacaoDAO();
        return notificacaoDAO.apagar(this,conexao);
    }


    public List<Notificacao> buscarTodos(Conexao conexao) {
        NotificacaoDAO notificacaoDAO = new NotificacaoDAO();
        return notificacaoDAO.get(conexao);
    }

    public boolean alterar(Conexao conexao) {
        NotificacaoDAO notificacaoDAO = new NotificacaoDAO();
        return notificacaoDAO.alterar(this,conexao);
    }

    public boolean gravar(Conexao conexao) {
        NotificacaoDAO notificacaoDAO = new NotificacaoDAO();
        return notificacaoDAO.gravar(this,conexao);
    }
}
