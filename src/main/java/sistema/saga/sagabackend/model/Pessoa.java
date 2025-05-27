package sistema.saga.sagabackend.model;

import org.springframework.stereotype.Component;
import sistema.saga.sagabackend.repository.Conexao;
import sistema.saga.sagabackend.repository.PessoaDAO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
public class Pessoa {

    private String cpf;
    private String rg;
    private String nome;
    private LocalDate dataNascimento;
    private String sexo;
    private String locNascimento;
    private String estadoNascimento;
    private Endereco endereco;
    private String estadoCivil;

    public Pessoa(String cpf, String rg, String nome, LocalDate dataNascimento, String sexo,
                  String locNascimento, String estadoNascimento, Endereco endereco, String estadoCivil) {
        this.cpf = cpf;
        this.rg = rg;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.locNascimento = locNascimento;
        this.estadoNascimento = estadoNascimento;
        this.endereco = endereco;
        this.estadoCivil = estadoCivil;
    }

    public Pessoa(String cpf) {
        this(cpf, "", "", null, "", "", "", null, "");
    }

    public Pessoa() {
        this("", "", "", null, "", "", "", null, "");
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getLocNascimento() {
        return locNascimento;
    }

    public void setLocNascimento(String locNascimento) {
        this.locNascimento = locNascimento;
    }

    public String getEstadoNascimento() {
        return estadoNascimento;
    }

    public void setEstadoNascimento(String estadoNascimento) {
        this.estadoNascimento = estadoNascimento;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public boolean apagar(Conexao conexao) {
        PessoaDAO pessoaDAO = new PessoaDAO();
        return pessoaDAO.apagar(this,conexao);
    }

    public Pessoa buscaPessoa(Conexao conexao,Map<String,Object> end){
        PessoaDAO pessoaDAO = new PessoaDAO();
        return pessoaDAO.getPessoa(this,conexao,end);
    }

    public List<Pessoa> buscarTodos(Conexao conexao,List<Map<String,Object>> enderecos) {
        PessoaDAO pessoaDAO = new PessoaDAO();
        return pessoaDAO.get("",conexao,enderecos);
    }

    public List<Pessoa> buscarTodosSemAlunos(Conexao conexao,List<Map<String,Object>> enderecos,boolean aluno) {
        PessoaDAO pessoaDAO = new PessoaDAO();
        return pessoaDAO.buscarTodosSemAlunos("",conexao,enderecos,aluno);
    }

    public List<Pessoa> buscarTodosSemProfissional(Conexao conexao,List<Map<String,Object>> enderecos) {
        PessoaDAO pessoaDAO = new PessoaDAO();
        return pessoaDAO.buscarTodosSemProfissional("",conexao,enderecos);
    }

    public boolean alterar(Conexao conexao) {
        PessoaDAO pessoaDAO = new PessoaDAO();
        return pessoaDAO.alterar(this,conexao);
    }

    public boolean gravar(Conexao conexao) {
        PessoaDAO pessoaDAO = new PessoaDAO();
        return pessoaDAO.gravar(this,conexao);
    }

    public boolean validarCPF(String cpf) {
        if (cpf == null || cpf.length() != 14)
            return false;
        String numeros = cpf.replace(".", "").replace("-", "");
        if (numeros.matches("(\\d)\\1{10}"))
            return false;
        try {
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += (numeros.charAt(i) - '0') * (10 - i);
            }
            int dig1 = 11 - (soma % 11);
            if (dig1 >= 10) dig1 = 0;
            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += (numeros.charAt(i) - '0') * (11 - i);
            }
            int dig2 = 11 - (soma % 11);
            if (dig2 >= 10) dig2 = 0;
            return (dig1 == (numeros.charAt(9) - '0')) &&
                    (dig2 == (numeros.charAt(10) - '0'));

        } catch (Exception e) {
            return false;
        }
    }

    public  boolean validarCEP(String cep) {
        return cep != null && cep.matches("\\d{5}-\\d{3}");
    }

    public  boolean validarRG(String rg) {
        return rg != null && rg.matches("\\d{7,9}");
    }
}
