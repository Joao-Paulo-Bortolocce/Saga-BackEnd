package sistema.saga.sagabackend.model;

import org.springframework.stereotype.Component;
import sistema.saga.sagabackend.repository.MatriculaDAO;
import sistema.saga.sagabackend.repository.Conexao;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
public class Matricula {

    private int id;
    private Aluno aluno;
    private AnoLetivo anoLetivo;
    private Serie serie;
    private Turma turma;
    private boolean aprovado;
    private LocalDate data;
    private boolean valido;


    public Matricula(int id, Aluno aluno, AnoLetivo anoLetivo, Serie serie, Turma turma, boolean aprovado, LocalDate data, boolean valido) {
        this.id = id;
        this.aluno = aluno;
        this.anoLetivo = anoLetivo;
        this.serie = serie;
        this.turma = turma;
        this.aprovado = aprovado;
        this.data = data;
        this.valido=valido;
    }

    public Matricula(Aluno aluno, AnoLetivo anoLetivo, Serie serie, Turma turma, boolean aprovado, LocalDate data, boolean valido) {
        this(0,  aluno,  anoLetivo,  serie,  turma,  aprovado,  data,valido);
    }

    public Matricula(int id) {
        this(id,null,null,null,null,false,null,false);
    }

    public Matricula() {
        this(0,null,null,null,null,false,null,false);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public AnoLetivo getAnoLetivo() {
        return anoLetivo;
    }

    public void setAnoLetivo(AnoLetivo anoLetivo) {
        this.anoLetivo = anoLetivo;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public boolean isAprovado() {
        return aprovado;
    }

    public void setAprovado(boolean aprovado) {
        this.aprovado = aprovado;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public boolean isValido() {
        return valido;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
    }

    public boolean apagar(Conexao conexao) {
        MatriculaDAO matriculaDAO = new MatriculaDAO();
        return matriculaDAO.apagar(this.getId(),conexao);
    }

    public List<Matricula> buscaMatricula(Conexao conexao, List< Map<String, Object>> aluno, List< Map<String, Object>> ano, List< Map<String, Object>> serie, List< Map<String, Object>> turma){
        MatriculaDAO matriculaDAO = new MatriculaDAO();
        return matriculaDAO.getMatricula(this,conexao,aluno,ano,serie,turma);
    }

    public List<Matricula> buscarTodas(Conexao conexao, List<Map<String, Object>>alunos, List<Map<String, Object>> anos, List<Map<String, Object>> series, List<Map<String, Object>> turmas) {
        MatriculaDAO matriculaDAO = new MatriculaDAO();
        return matriculaDAO.get(conexao,alunos,anos,series,turmas);
    }

    public List<Matricula> buscarTodasFiltradas(Conexao conexao,Matricula matricula,int valido, List<Map<String, Object>>alunos, List<Map<String, Object>> anos, List<Map<String, Object>> series, List<Map<String, Object>> turmas) {
        MatriculaDAO matriculaDAO = new MatriculaDAO();
        return matriculaDAO.buscarTodasFiltradas(conexao,matricula,valido,alunos,anos,series,turmas);
    }

    public boolean alterar(Conexao conexao) {
        MatriculaDAO matriculaDAO = new MatriculaDAO();
        return matriculaDAO.alterar(this,conexao);
    }

    public boolean gravar(Conexao conexao) {
        MatriculaDAO matriculaDAO = new MatriculaDAO();
        return matriculaDAO.gravar(this,conexao);
    }
}