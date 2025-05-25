package sistema.saga.sagabackend.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sistema.saga.sagabackend.repository.Conexao;
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

    public Endereco(long id, String rua, int numero, String complemento, String cep, String cidade, String uf) {
        this.id = id;
        this.rua = rua.toUpperCase();
        this.numero = numero;
        setComplemento(complemento);
        this.cep = cep.toUpperCase();
        this.uf = uf.toUpperCase();
        this.cidade = cidade.toUpperCase();
    }

    public Endereco(String rua, int numero, String complemento, String cep, String cidade, String uf) {
        this(0,rua,numero,complemento,cep,cidade,uf);
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
        if (complemento!=null)
            this.complemento = complemento.toUpperCase();
        this.complemento=null;
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

    public Endereco buscaEndereco(Conexao conexao)  {
        EnderecoDAO enderecoDAO= new EnderecoDAO();
        return enderecoDAO.getEndereco(this,conexao);
    }

    public Endereco buscaEndereco(int id,Conexao conexao)  {
        EnderecoDAO enderecoDAO= new EnderecoDAO();
        return enderecoDAO.getEndereco(id,conexao);
    }

    public boolean gravar(Conexao conexao) {
        Endereco aux= buscaEndereco(conexao);
        if(aux!=null){
            setId(aux.getId());
            setNumero(aux.getNumero());
            setCep(aux.getCep());
            setCidade(aux.getCidade());
            setRua(aux.getRua());
            setUf(aux.getUf());
            setComplemento(aux.getComplemento());
            return true;
        }
        EnderecoDAO enderecoDAO= new EnderecoDAO();
        if(enderecoDAO.gravar(this,conexao)){
            setId(conexao.getMaxPK("endereco","endereco_id"));
            return true;
        }
        return false;
    }
}