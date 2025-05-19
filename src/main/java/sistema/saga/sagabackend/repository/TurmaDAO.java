package sistema.saga.sagabackend.repository;

import org.springframework.stereotype.Repository;
import sistema.saga.sagabackend.model.AnoLetivo;
import sistema.saga.sagabackend.model.Serie;
import sistema.saga.sagabackend.model.Turma;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TurmaDAO {

    public boolean alterar(Turma turmaAntiga, char novaLetra, Conexao conexao) {
        String sql = """
        UPDATE turma
        SET turma_letra = '#1'
        WHERE turma_letra = '#2'
          AND serieturma_id = #3
          AND turmaanoletivo_id = #4
    """;
        sql = sql.replace("#1", String.valueOf(novaLetra));
        sql = sql.replace("#2", String.valueOf(turmaAntiga.getLetra()));
        sql = sql.replace("#3", String.valueOf(turmaAntiga.getSerie().getSerieId()));
        sql = sql.replace("#4", String.valueOf(turmaAntiga.getAnoLetivo().getId()));

        return conexao.manipular(sql);
    }


    public boolean gravar(Turma turma, Conexao conexao) {
        String sql = """
        INSERT INTO turma (turma_letra, serieturma_id, turmaanoletivo_id)
        VALUES ('#1', '#2', '#3')
    """;

        sql = sql.replace("#1", ""+turma.getLetra());
        sql = sql.replace("#2", String.valueOf(turma.getSerie().getSerieId()));
        sql = sql.replace("#3", String.valueOf(turma.getAnoLetivo().getId()));

        return conexao.manipular(sql);
    }

    public boolean apagar(Turma turma, Conexao conexao) {
        String sql = """
        DELETE FROM turma WHERE turma_letra = '#1' 
          AND serieturma_id = #2 
          AND turmaanoletivo_id = #3
    """;
        sql = sql.replace("#1", String.valueOf(turma.getLetra()));
        sql = sql.replace("#2", String.valueOf(turma.getSerie().getSerieId()));
        sql = sql.replace("#3", String.valueOf(turma.getAnoLetivo().getId()));
        return conexao.manipular(sql);
    }

    public List<Turma> get(String filtro, Conexao conexao) {
        List<Turma> turmas = new ArrayList<>();
        String sql;

        if (filtro == null || filtro.isEmpty()) {
            sql = """
            SELECT * FROM turma
            ORDER BY turma_letra, serieturma_id, turmaanoletivo_id
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
            ResultSet rs = conexao.consultar(sql);
            while (rs.next()) {
                Turma turma = new Turma();
                turma.setLetra(rs.getString("turma_letra").charAt(0));

                Serie serie = new Serie();
                serie.setSerieId(rs.getInt("serieturma_id"));
                serie.buscaSerie(conexao); // <-- já busca nome e número
                turma.setSerie(serie);

                AnoLetivo ano = new AnoLetivo();
                ano.setId(rs.getInt("turmaanoletivo_id"));
                ano.buscaAnos(conexao); // <-- busca data início/fim
                turma.setAnoLetivo(ano);

                turmas.add(turma);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar turmas", e);
        }

        return turmas;
    }
}