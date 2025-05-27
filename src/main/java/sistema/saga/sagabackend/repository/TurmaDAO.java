package sistema.saga.sagabackend.repository;

import org.springframework.stereotype.Repository;
import sistema.saga.sagabackend.model.*;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class TurmaDAO {

    public boolean gravar(Turma turma, Conexao conexao) {
        String sql = """
        INSERT INTO turma (turma_letra, serieturma_id, turmaanoletivo_id, turmaprofissional_rn, turmasala_id)
        VALUES ('#1', '#2', '#3', '#4', '#5')
        """;

        sql = sql.replace("#1", String.valueOf(turma.getLetra()));
        sql = sql.replace("#2", String.valueOf(turma.getSerie().getSerieId()));
        sql = sql.replace("#3", String.valueOf(turma.getAnoLetivo().getId()));
        sql = sql.replace("#4", String.valueOf(turma.getProfissional().getProfissional_rn()));
        sql = sql.replace("#5", String.valueOf(turma.getSala().getId()));

        return conexao.manipular(sql);
    }

    public boolean apagar(Turma turma, Conexao conexao) {
        String sql = """
        DELETE FROM turma WHERE turma_letra = '#1' 
          AND serieturma_id = #2 
          AND turmaanoletivo_id = #3
          AND turmaprofissional_rn = #4
          AND turmasala_id = #5
        """;

        sql = sql.replace("#1", String.valueOf(turma.getLetra()));
        sql = sql.replace("#2", String.valueOf(turma.getSerie().getSerieId()));
        sql = sql.replace("#3", String.valueOf(turma.getAnoLetivo().getId()));
        sql = sql.replace("#4", String.valueOf(turma.getProfissional().getProfissional_rn()));
        sql = sql.replace("#5", String.valueOf(turma.getSala().getId()));

        return conexao.manipular(sql);
    }

    public List<Turma> get(String filtro, Conexao conexao) {
        List<Turma> turmas = new ArrayList<>();
        String sql;
        ResultSet rs = null;

        if (filtro == null || filtro.isEmpty()) {
            sql = """
        SELECT * FROM turma
        ORDER BY turma_letra, serieturma_id, turmaanoletivo_id, turmaprofissional_rn, turmasala_id
        """;
        }
        else {
            sql = """
        SELECT * FROM turma 
        WHERE turma_letra ILIKE '%#1%' 
           OR CAST(serieturma_id AS TEXT) ILIKE '%#1%' 
           OR CAST(turmaanoletivo_id AS TEXT) ILIKE '%#1%'
        ORDER BY turma_letra, serieturma_id, turmaanoletivo_id
        """;
            sql = sql.replace("#1", filtro);
        }

        try {
            rs = conexao.consultar(sql);

            if(rs == null){
                System.out.println("ResultSet is null");
                return null;
            }
            while (rs.next()) {
                Turma turma = new Turma();
                turma.setLetra(rs.getString("turma_letra").charAt(0));

                Serie serie = new Serie();
                serie.setSerieId(rs.getInt("serieturma_id"));
                serie.buscaSerie(conexao);
                turma.setSerie(serie);

                AnoLetivo ano = new AnoLetivo();
                ano.setId(rs.getInt("turmaanoletivo_id"));
                ano.buscaAnos(conexao);
                turma.setAnoLetivo(ano);

                Profissional prof = new Profissional();
                prof.setProfissional_rn(rs.getInt("turmaprofissional_rn"));
                prof = prof.buscaProfissional(conexao, prof, new HashMap<>(), new HashMap<>());
                turma.setProfissional(prof);

                Sala sala = new Sala();
                sala.setId(rs.getInt("turmasala_id"));
                sala.buscaSerie(conexao);
                turma.setSala(sala);

                turmas.add(turma);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao consultar turmas", e);
        }

        return turmas;
    }

}
