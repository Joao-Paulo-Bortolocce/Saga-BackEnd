package sistema.saga.sagabackend.model;


import jakarta.persistence.*;

@Entity
@Table(name = "endereco")
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "endereco_id")
    private long id;

    @Column(name = "endereco_rua")
    private String rua;

    @Column(name = "endereco_numero")
    private int numero;

    @Column(name = "endereco_complemento")
    private String complemento;

    @Column(name = "endereco_cep")
    private String cep;

    public Endereco(long id, String rua, int numero, String complemento, String cep) {
        this.id = id;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.cep = cep;
    }

    public Endereco() {
        this(0,"",0,"","");
    }

    public Endereco(long id) {
        this(id,"",0,"","");
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
}
