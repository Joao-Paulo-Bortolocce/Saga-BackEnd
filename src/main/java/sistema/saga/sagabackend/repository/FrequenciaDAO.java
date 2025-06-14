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

    public boolean alterar(Frequencia frequencia, Conexao conexao) {
        String sql = """
            UPDATE frequencia SET frequencia_presente = #1
            WHERE matricula_id = #2 AND frequencia_data = '#3'
        """;
        sql = sql.replace("#1", frequencia.isPresente() ? "true" : "false");
        sql = sql.replace("#2", "" + frequencia.getMatricula().getId());
        sql = sql.replace("#3", "" + Date.valueOf(frequencia.getData()));

        return conexao.manipular(sql);
    }

    public boolean apagar(Frequencia frequencia, Conexao conexao) {
        String sql = "DELETE FROM frequencia WHERE matricula_id = '#1' AND frequencia_data = #2";

        sql = sql.replace("#1", "" + frequencia.getMatricula().getId());
        sql = sql.replace("#2", "" + Date.valueOf(frequencia.getData()));

        return conexao.manipular(sql);
    }

    public List<Frequencia> buscarId(Frequencia frequencia, Conexao conexao) {
        List<Frequencia> frequencias = new ArrayList<>();
        String sql = "SELECT * FROM frequencia WHERE matricula_id = #1 AND EXTRACT(YEAR FROM frequencia_data) = #2";
        sql = sql.replace("#1", "" + frequencia.getMatricula().getId());
        sql = sql.replace("#2", String.valueOf(frequencia.getData().getYear()));

        try {
            ResultSet rs = conexao.consultar(sql);
            while (rs.next()) {
                Frequencia freq = new Frequencia();
                freq.setPresente(rs.getBoolean("frequencia_presente"));
                freq.setData(rs.getDate("frequencia_data").toLocalDate());

                Matricula matricula = new Matricula();
                Map<String, Object> aluno = new HashMap<>();
                Map<String, Object> ano = new HashMap<>();
                Map<String, Object> serie = new HashMap<>();
                Map<String, Object> turma = new HashMap<>();
                matricula.setId(rs.getInt("matricula_id"));
                matricula.buscaMatricula(conexao, matricula, aluno, ano, serie, turma);
                freq.setMatricula(matricula);

                frequencias.add(freq);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar frequencia", e);
        }
        return frequencias;
    }

    public List<Frequencia> buscarData(Frequencia frequencia, Conexao conexao) {
        List<Frequencia> frequencias = new ArrayList<>();
        String sql = "SELECT * FROM frequencia WHERE frequencia_data = '#1'";
        sql = sql.replace("#1", String.valueOf(frequencia.getData()));

        try {
            ResultSet rs = conexao.consultar(sql);
            while (rs.next()) {
                Frequencia freq = new Frequencia();
                freq.setPresente(rs.getBoolean("frequencia_presente"));
                freq.setData(rs.getDate("frequencia_data").toLocalDate());

                Matricula matricula = new Matricula();
                matricula.setId(rs.getInt("matricula_id"));
                Map<String, Object> aluno = new HashMap<>();
                Map<String, Object> ano = new HashMap<>();
                Map<String, Object> serie = new HashMap<>();
                Map<String, Object> turma = new HashMap<>();
                Matricula matri = matricula.buscaMatricula(conexao, matricula, aluno, ano, serie, turma);
                freq.setMatricula(matri);

                frequencias.add(freq);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar frequencia", e);
        }
        return frequencias;
    }
}
