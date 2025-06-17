package sistema.saga.sagabackend.model;

import sistema.saga.sagabackend.repository.BimestreDAO;
import sistema.saga.sagabackend.repository.Conexao;

import java.util.List;

public class Bimestre {
    int bimestre_id;
    int bimestre_anoletivo_id;

    public Bimestre() {
    }

    public Bimestre(int bimestre_id) {
        this.bimestre_id = bimestre_id;
    }

    public Bimestre(int bimestre_id, int bimestre_anoletivo_id) {
        this.bimestre_id = bimestre_id;
        this.bimestre_anoletivo_id = bimestre_anoletivo_id;
    }

    public int getBimestre_id() {
        return bimestre_id;
    }

    public void setBimestre_id(int bimestre_id) {
        this.bimestre_id = bimestre_id;
    }

    public int getBimestre_anoletivo_id() {
        return bimestre_anoletivo_id;
    }

    public void setBimestre_anoletivo_id(int bimestre_anoletivo_id) {
        this.bimestre_anoletivo_id = bimestre_anoletivo_id;
    }

    public List<Bimestre> buscarTodos(Conexao conexao) {
        BimestreDAO bimestreDAO = new BimestreDAO();
        return bimestreDAO.getAll(conexao);
    }
}
