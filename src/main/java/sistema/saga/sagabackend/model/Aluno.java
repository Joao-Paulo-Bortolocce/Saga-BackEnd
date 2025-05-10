package sistema.saga.sagabackend.model;

import org.springframework.stereotype.Component;
import sistema.saga.sagabackend.repository.Conexao;
import sistema.saga.sagabackend.repository.AlunoDAO;

import java.time.LocalDate;
import java.util.List;

import java.util.Map;


@Component
public class Aluno {

    private int ra;
    private String restricaoMedica;
    private Pessoa pessoa;

    public Aluno(int ra, String restricaoMedica, Pessoa pessoa) {
        this.ra = ra;
        this.restricaoMedica = restricaoMedica;
        this.pessoa = pessoa;
    }

    public Aluno(int ra, Pessoa pessoa) {
        this(ra,"",pessoa);
    }

    public Aluno(int ra) {
        this(ra,"",null);
    }

    public Aluno() {
        this(0,"",null);
    }

    public int getRa() {
        return ra;
    }

    public void setRa(int ra) {
        this.ra = ra;
    }

    public String getRestricaoMedica() {
        return restricaoMedica;
    }

    public void setRestricaoMedica(String restricaoMedica) {
        this.restricaoMedica = restricaoMedica;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public boolean apagar(Conexao conexao) {
        AlunoDAO alunoDAO = new AlunoDAO();
        return alunoDAO.apagar(this.getRa(),conexao);
    }


    public Aluno buscaAluno(Conexao conexao, Aluno aluno, Map<String, Object> pessoa){
        AlunoDAO alunoDAO = new AlunoDAO();
        return alunoDAO.getAluno(aluno,conexao,pessoa);
    }

    public List<Aluno> buscarTodos(Conexao conexao,List<Map<String, Object>>  pessoas) {
        AlunoDAO alunoDAO = new AlunoDAO();
        return alunoDAO.get(conexao,pessoas);
    }

    public List<Aluno> buscarTodosSemMatricula(Conexao conexao,int anoLetivo,List<Map<String, Object>>  pessoas) {
        AlunoDAO alunoDAO = new AlunoDAO();
        return alunoDAO.buscaAlunosSemMatricula(conexao,anoLetivo,pessoas);

    }

    public boolean alterar(Conexao conexao) {
        AlunoDAO alunoDAO = new AlunoDAO();
        return alunoDAO.alterar(this,conexao);
    }

    public boolean gravar(Conexao conexao) {
        AlunoDAO alunoDAO = new AlunoDAO();
        return alunoDAO.gravar(this,conexao);
    }
}
