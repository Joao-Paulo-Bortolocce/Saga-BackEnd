package sistema.saga.sagabackend.repository;

import org.springframework.stereotype.Repository;
import sistema.saga.sagabackend.model.Serie;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SerieDAO {

    public boolean gravar(Serie serie, Conexao conexao) {
        String sql = """
        INSERT INTO serie (serie_num, serie_descr)
        VALUES (#1, '#2')
    """;
        sql = sql.replace("#1", String.valueOf(serie.getSerieNum()));
        sql = sql.replace("#2", serie.getSerieDescr().replace("'", "''"));

        System.out.println("SQL executado: " + sql); // <--- debug aqui

        return conexao.manipular(sql);
    }

    public boolean alterar(Serie serie, Conexao conexao) {
        String sql = """
            UPDATE serie 
            SET serie_num = '#1', serie_descr = '#2'
            WHERE serie_id = '#3'
        """;
        sql = sql.replace("#1", String.valueOf(serie.getSerieNum()));
        sql = sql.replace("#2", serie.getSerieDescr());
        sql = sql.replace("#3", String.valueOf(serie.getSerieId()));
        return conexao.manipular(sql);
    }

    public boolean apagar(Serie serie, Conexao conexao) {
        String sql = "DELETE FROM serie WHERE serie_id = '#1'";
        sql = sql.replace("#1", String.valueOf(serie.getSerieId()));
        return conexao.manipular(sql);
    }

    public int getSerie(Serie serie, Conexao conexao) {
        String sql = "SELECT * FROM serie WHERE serie_id = '#1'";
        sql = sql.replace("#1", String.valueOf(serie.getSerieId()));

        try {
            ResultSet rs = conexao.consultar(sql);
            if (rs.next()) {
                serie.setSerieId(rs.getInt("serie_id"));
                serie.setSerieNum(rs.getInt("serie_num"));
                serie.setSerieDescr(rs.getString("serie_descr"));
                return rs.getInt("serie_id");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar série por ID", e);
        }

        return 0;
    }

    public List<Serie> get(String filtro, Conexao conexao) {
        List<Serie> series = new ArrayList<>();
        String sql;

        if (filtro == null || filtro.isEmpty()) {
            sql = "SELECT * FROM serie ORDER BY serie_num";
        }
        else {
            sql = "SELECT * FROM serie WHERE serie_descr ILIKE '%" + filtro + "%' OR CAST(serie_num AS TEXT) LIKE '%" + filtro + "%'";
        }

        try {
            ResultSet rs = conexao.consultar(sql);
            while (rs.next()) {
                Serie serie = new Serie(
                        rs.getInt("serie_id"),
                        rs.getInt("serie_num"),
                        rs.getString("serie_descr")
                );
                series.add(serie);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar séries", e);
        }

        return series;
    }
}
