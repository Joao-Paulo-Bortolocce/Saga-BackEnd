package sistema.saga.sagabackend.model;

import org.springframework.stereotype.Component;
import sistema.saga.sagabackend.repository.Conexao;
import sistema.saga.sagabackend.repository.AlunoDAO;

import java.time.LocalDate;
import java.util.List;

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

    public String buscaAluno(Conexao conexao, Aluno aluno){
        AlunoDAO alunoDAO = new AlunoDAO();
        return alunoDAO.getAluno(aluno,conexao);
    }

    public List<Aluno> buscarTodos(Conexao conexao, List<String> cpfsPessoa) {
        AlunoDAO alunoDAO = new AlunoDAO();
        return alunoDAO.get(conexao,cpfsPessoa);
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
