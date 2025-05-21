package sistema.saga.sagabackend.repository;

import org.springframework.stereotype.Repository;
import sistema.saga.sagabackend.model.*;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FrequenciaDAO {

    public boolean gravar(Frequencia frequencia, Conexao conexao) {
        String sql = """
                    INSERT INTO frequencia (matricula_id, frequencia_data, frequencia_presente)
                    VALUES (#1, '#2', #3);
                """;
        sql = sql.replace("#1", "" + frequencia.getMatricula().getId());
        sql = sql.replace("#2", "" + Date.valueOf(frequencia.getData()));
        sql = sql.replace("#3", frequencia.isPresente() ? "true" : "false");
        return conexao.manipular(sql);
    }

    public List<Frequencia> buscarId(int id, Conexao conexao) {
        List<Frequencia> frequencias = new ArrayList<>();
        String sql = """
        SELECT * FROM frequencia WHERE matricula_id = #1
        """;
        sql = sql.replace("#1", "" + id);

        try {
            ResultSet rs = conexao.consultar(sql);
            while (rs.next()) {
                Frequencia frequencia = new Frequencia();
                frequencia.setPresente(rs.getBoolean("frequencia_presente"));
                frequencia.setData(rs.getDate("frequencia_data").toLocalDate());

                Matricula matricula = new Matricula();
                Map<String, Object> aluno = new HashMap<>();
                Map<String, Object> ano = new HashMap<>();
                Map<String, Object> serie = new HashMap<>();
                Map<String, Object> turma = new HashMap<>();
                matricula.setId(rs.getInt("matricula_id"));
                matricula.buscaMatricula(conexao, matricula, aluno, ano, serie, turma);
                frequencia.setMatricula(matricula);

                frequencias.add(frequencia);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar frequencia", e);
        }

        return frequencias;
    }
}
