package sistema.saga.sagabackend.model;

import org.springframework.stereotype.Component;
import sistema.saga.sagabackend.repository.Conexao;
import sistema.saga.sagabackend.repository.ProfissionalDAO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
public class Profissional {
    private int profissional_ra;
    private int profissional_tipo;
    private Pessoa profissional_pessoa;
    private Graduacao profissional_graduacao;
    private LocalDate profissional_dataAdmissao;
    private String profissional_usuario;
    private String profissional_senha;

    public Profissional(int profissional_ra, int profissional_tipo, Pessoa profissional_pessoa, Graduacao profissional_graduacao, LocalDate profissional_dataAdmissao, String profissional_usuario, String profissional_senha) {
        this.profissional_ra = profissional_ra;
        this.profissional_tipo = profissional_tipo;
        this.profissional_pessoa = profissional_pessoa;
        this.profissional_graduacao = profissional_graduacao;
        this.profissional_dataAdmissao = profissional_dataAdmissao;
        this.profissional_usuario = profissional_usuario;
        this.profissional_senha = profissional_senha;
    }

    public Profissional(int profissional_ra) {
        this(profissional_ra, 0, null, null, null,"","");

    }

    public Profissional() {
        this(0, 0, null, null, null,"","");
    }

    public int getProfissional_ra() {
        return profissional_ra;
    }

    public void setProfissional_ra(int profissional_ra) {
        this.profissional_ra = profissional_ra;
    }

    public int getProfissional_tipo() {
        return profissional_tipo;
    }

    public void setProfissional_tipo(int profissional_tipo) {
        this.profissional_tipo = profissional_tipo;
    }

    public Pessoa getProfissional_pessoa() {
        return profissional_pessoa;
    }

    public void setProfissional_pessoa(Pessoa profissional_pessoa) {
        this.profissional_pessoa = profissional_pessoa;
    }

    public Graduacao getProfissional_graduacao() {
        return profissional_graduacao;
    }

    public void setProfissional_graduacao(Graduacao profissional_graduacao) {
        this.profissional_graduacao = profissional_graduacao;
    }

    public LocalDate getProfissional_dataAdmissao() {
        return profissional_dataAdmissao;
    }

    public void setProfissional_dataAdmissao(LocalDate profissional_dataAdmissao) {
        this.profissional_dataAdmissao = profissional_dataAdmissao;
    }

    public String getProfissional_usuario() {
        return profissional_usuario;
    }

    public void setProfissional_usuario(String profissional_usuario) {
        this.profissional_usuario = profissional_usuario;
    }

    public String getProfissional_senha() {
        return profissional_senha;
    }

    public void setProfissional_senha(String profissional_senha) {
        this.profissional_senha = profissional_senha;
    }


    public Profissional buscaProfissional(Conexao conexao, Profissional profissional, Map<String, Object> pessoa, Map<String, Object> graduacao) {
        ProfissionalDAO profissionalDAO = new ProfissionalDAO();
        return  profissionalDAO.getProfissional(conexao,profissional,pessoa,graduacao);
    }

    public List<Profissional> buscarTodos(Conexao conexao, List<Map<String, Object>> pessoas, List<Map<String, Object>> graduacoes) {
        ProfissionalDAO profissionalDAO = new ProfissionalDAO();
        return  profissionalDAO.get(conexao,pessoas,graduacoes);
    }

    public Profissional buscaUsuario(Conexao conexao, int ra, String senha) {
        ProfissionalDAO profissionalDAO = new ProfissionalDAO();
        return  profissionalDAO.getUsuario(conexao,ra,senha);
    }

    public boolean apagar(Conexao conexao) {
        ProfissionalDAO profissionalDAO = new ProfissionalDAO();
        return  profissionalDAO.apagar(this.profissional_ra,conexao);
    }

    public boolean gravar(Conexao conexao) {
        ProfissionalDAO profissionalDAO = new ProfissionalDAO();
        return  profissionalDAO.gravar(this,conexao);
    }

    public boolean alterar(Conexao conexao) {
        ProfissionalDAO profissionalDAO = new ProfissionalDAO();
        return  profissionalDAO.alterar(this,conexao);
    }
}
