package sistema.saga.sagabackend.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sistema.saga.sagabackend.repository.EnderecoDAO;

import java.sql.SQLException;

@Component
public class Endereco {

    private long id;
    private String rua;
    private int numero;
    private String complemento;
    private String cep;
    private String uf;
    private String cidade;

    @Autowired
    private EnderecoDAO enderecoDAO;

    public Endereco(long id, String rua, int numero, String complemento, String cep, String cidade, String uf) {
        this.id = id;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.cep = cep;
        this.uf = uf;
        this.cidade = cidade;
    }

    public Endereco() {
        this(0, "", 0, "", "", "", "");
    }

    public Endereco(long id) {
        this(id, "", 0, "", "", "", "");
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Endereco buscaEndereco(int pessoaEnderecoId) throws SQLException {
        return enderecoDAO.getEndereco(pessoaEnderecoId);
    }
}
