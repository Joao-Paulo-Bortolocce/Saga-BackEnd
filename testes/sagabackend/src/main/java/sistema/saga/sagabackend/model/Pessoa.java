package sistema.saga.sagabackend.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sistema.saga.sagabackend.repository.PessoaDAO;

import java.time.LocalDate;
import java.util.List;

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

    @Autowired
    private PessoaDAO pessoaDAO;

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

    public boolean apagar() {
        return pessoaDAO.apagar(this);
    }

    public List<Pessoa> buscarTodos() {
        return pessoaDAO.get("");
    }

    public boolean alterar() {
        return pessoaDAO.alterar(this);
    }

    public boolean gravar() {
        return pessoaDAO.gravar(this);
    }
}
