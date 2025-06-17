package sistema.saga.sagabackend.model;

import org.springframework.stereotype.Component;
import sistema.saga.sagabackend.repository.Conexao;
import sistema.saga.sagabackend.repository.MateriaDAO;

import java.util.List;

@Component
public class Materia {
    private int materia_id;
    private String materia_nome;
    private int materia_carga;

    public Materia() {
    }

    public Materia(int materia_id, String materia_nome, int materia_carga) {
        this.materia_id = materia_id;
        this.materia_nome = materia_nome;
        this.materia_carga = materia_carga;
    }

    public Materia(int materia_id) {
        this.materia_id = materia_id;
    }

    public int getId() {
        return materia_id;
    }

    public void setId(int materia_id) {
        this.materia_id = materia_id;
    }

    public String getNome() {
        return materia_nome;
    }

    public void setNome(String materia_nome) {
        this.materia_nome = materia_nome;
    }

    public int getCarga() {
        return materia_carga;
    }

    public void setCarga(int materia_carga) {
        this.materia_carga = materia_carga;
    }

    public boolean gravar(Conexao conexao) {
        MateriaDAO materiaDAO = new MateriaDAO();
        return materiaDAO.gravar(this, conexao);
    }

    public boolean alterar(Conexao conexao) {
        MateriaDAO materiaDAO = new MateriaDAO();
        return materiaDAO.alterar(this, conexao);
    }

    public boolean apagar(Conexao conexao) {
        MateriaDAO materiaDAO = new MateriaDAO();
        return materiaDAO.apagar(this, conexao);
    }

    public Materia buscarId(Materia materia, Conexao conexao) {
        MateriaDAO materiaDAO = new MateriaDAO();
        return materiaDAO.getId(materia.materia_id, conexao);
    }

    public List<Materia> buscarMaterias(Conexao conexao) {
        MateriaDAO materiaDAO = new MateriaDAO();
        return materiaDAO.get("", conexao);
    }

}
