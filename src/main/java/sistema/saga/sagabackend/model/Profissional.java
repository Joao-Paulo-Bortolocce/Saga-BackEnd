package sistema.saga.sagabackend.model;


import org.springframework.stereotype.Component;
import sistema.saga.sagabackend.repository.Conexao;
import sistema.saga.sagabackend.repository.ProfissionalDAO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
public class Profissional {
    private int profissional_rn;
    private int profissional_tipo;
    private Pessoa profissional_pessoa;
    private Graduacao profissional_graduacao;
    private LocalDate profissional_dataAdmissao;
    private String profissional_usuario;
    private String profissional_senha;

    public Profissional(int profissional_rn, int profissional_tipo, Pessoa profissional_pessoa, Graduacao profissional_graduacao, LocalDate profissional_dataAdmissao, String profissional_usuario, String profissional_senha) {
        this.profissional_rn = profissional_rn;
        this.profissional_tipo = profissional_tipo;
        this.profissional_pessoa = profissional_pessoa;
        this.profissional_graduacao = profissional_graduacao;
        this.profissional_dataAdmissao = profissional_dataAdmissao;
        this.profissional_usuario = profissional_usuario;
        this.profissional_senha = profissional_senha;
    }

    public Profissional(int profissional_rn) {
        this(profissional_rn, 0, null, null, null,"","");

    }

    public Profissional() {
        this(0, 0, null, null, null,"","");
    }

    public int getProfissional_rn() {
        return profissional_rn;
    }

    public void setProfissional_rn(int profissional_rn) {
        this.profissional_rn = profissional_rn;
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

    public Profissional buscaUsuario(Conexao conexao, int rn, String senha) {
        ProfissionalDAO profissionalDAO = new ProfissionalDAO();
        return  profissionalDAO.getUsuario(conexao,rn,senha);
    }

    public boolean apagar(Conexao conexao) {
        ProfissionalDAO profissionalDAO = new ProfissionalDAO();
        return  profissionalDAO.apagar(this.profissional_rn,conexao);
    }

    public boolean gravar(Conexao conexao) {
        ProfissionalDAO profissionalDAO = new ProfissionalDAO();
        return  profissionalDAO.gravar(this,conexao);
    }

    public boolean alterar(Conexao conexao) {
        ProfissionalDAO profissionalDAO = new ProfissionalDAO();
        return  profissionalDAO.alterar(this,conexao);
    }

    public List<Profissional> buscarGestao(Conexao conexao, List<Map<String, Object>> pessoas, List<Map<String, Object>> graduacoes) {
        ProfissionalDAO profissionalDAO= new ProfissionalDAO();
        return profissionalDAO.buscarGestao(conexao,pessoas,graduacoes);
    }
}
