package sistema.saga.sagabackend.model;

import  sistema.saga.sagabackend.model.Endereco;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import sistema.saga.sagabackend.repository.PessoaDAO;

import java.time.LocalDate;

@Entity
@Table( name="pessoa")
public class Pessoa {
    @Autowired
    private PessoaDAO pessoaDAO;

    @Id  // indica que é chave primaria
    @Column(name = "pessoa_cpf") // Como o atributo tem um nome diferente da tabela preciso colocar essa linha para que o JPA consiga gerar corretamente.
    private String cpf;

    @Column(name = "pessoa_rg")
    private String rg;

    @Column(name = "pessoa_nome")
    private String nome;

    @Temporal(value = LocalDate)
    @Column(name = "pessoa_dataNascimento")
    private LocalDate dataNascimento;

    @Column(name = "pessoa_sexo")
    private String sexo;

    @Column(name = "pessoa_locNascimento")
    private String locNascimento;
    
    @Column(name = "pessoa_estadoNascimento")
    private String estadoNascimento;

    @Column(name = "pessoa_enderecoId")
    private Endereco endereco;

    @Column(name = "pessoa_estadoCivil")
    private String estadoCivil;

    public Pessoa(String cpf, String rg, String nome, LocalDate dataNascimento, String sexo, String locNascimento, String estadoNascimento, Endereco endereco, String estadoCivil) {
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

    public Pessoa() {
        this("","","",null,"","","",null,"");
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

    public void gravarPessoa(){
        pessoaDAO.save(this);
    }
}
