package sistema.saga.sagabackend.repository;

import sistema.saga.sagabackend.model.*;

import java.sql.ResultSet;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReuniaoDAO {

    public boolean gravar(Reuniao reuniao, Conexao conexao) {
        String sql = """
        INSERT INTO reuniao (reuniao_data, reuniao_turma_letra, reuniao_serie_id, reuniao_anoletivo_id, reuniao_tipo)
        VALUES ('#1', '#2', #3, #4, '#5')
    """;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dataFormatada = reuniao.getReuniaoData().format(formatter);

        sql = sql.replace("#1", dataFormatada);
        sql = sql.replace("#2", String.valueOf(reuniao.getTurma().getLetra()));
        sql = sql.replace("#3", String.valueOf(reuniao.getSerie().getSerieId()));
        sql = sql.replace("#4", String.valueOf(reuniao.getAnoLetivo().getId()));
        sql = sql.replace("#5", reuniao.getReuniaoTipo());
        System.out.println(">>> SQL Gerado para gravação:");
        System.out.println(sql);

        return conexao.manipular(sql);
    }

    public boolean alterar(Reuniao reuniao, Conexao conexao) {
        String sql = """
            UPDATE reuniao
            SET reuniao_data = '#1',
                reuniao_turma_letra = '#2',
                reuniao_serie_id = #3,
                reuniao_anoletivo_id = #4,
                reuniao_tipo = '#5'
            WHERE reuniao_id = #6
        """;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        sql = sql.replace("#1", reuniao.getReuniaoData().format(formatter));
        sql = sql.replace("#2", String.valueOf(reuniao.getTurma().getLetra()));
        sql = sql.replace("#3", String.valueOf(reuniao.getSerie().getSerieId()));
        sql = sql.replace("#4", String.valueOf(reuniao.getAnoLetivo().getId()));
        sql = sql.replace("#5", reuniao.getReuniaoTipo());
        sql = sql.replace("#6", String.valueOf(reuniao.getReuniaoId()));

        return conexao.manipular(sql);
    }

    public boolean apagar(Reuniao reuniao, Conexao conexao) {
        String sql = "DELETE FROM reuniao WHERE reuniao_id = " + reuniao.getReuniaoId();
        return conexao.manipular(sql);
    }

    public List<Reuniao> get(String filtro, Conexao conexao) {
        List<Reuniao> reunioes = new ArrayList<>();
        String sql;

        if (filtro == null || filtro.trim().isEmpty()) {
            sql = "SELECT * FROM reuniao ORDER BY reuniao_data DESC";
        } else {
            filtro = filtro.trim(); // Remove espaços extras
            sql = "SELECT * FROM reuniao WHERE " +
                    "CAST(reuniao_id AS TEXT) ILIKE '%" + filtro + "%' OR " +
                    "CAST(reuniao_data AS TEXT) ILIKE '%" + filtro + "%' OR " +
                    "reuniao_turma_letra ILIKE '%" + filtro + "%' OR " +
                    "reuniao_tipo ILIKE '%" + filtro + "%' " +
                    "ORDER BY reuniao_data DESC";
        }

        try {
            ResultSet rs = conexao.consultar(sql);
            while (rs.next()) {
                Reuniao reuniao = new Reuniao();
                reuniao.setReuniaoId(rs.getInt("reuniao_id"));
                reuniao.setReuniaoData(rs.getTimestamp("reuniao_data").toLocalDateTime());
                reuniao.setReuniaoTipo(rs.getString("reuniao_tipo"));

                Turma turma = new Turma();
                turma.setLetra(rs.getString("reuniao_turma_letra").charAt(0));
                reuniao.setTurma(turma);

                Serie serie = new Serie();
                serie.setSerieId(rs.getInt("reuniao_serie_id"));
                serie.buscaSerie(conexao);
                reuniao.setSerie(serie);

                AnoLetivo ano = new AnoLetivo();
                ano.setId(rs.getInt("reuniao_anoletivo_id"));
                ano.buscaAnos(conexao);
                reuniao.setAnoLetivo(ano);

                reunioes.add(reuniao);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar reuniões", e);
        }

        return reunioes;
    }
}