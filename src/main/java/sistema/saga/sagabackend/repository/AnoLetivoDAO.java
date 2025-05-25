package sistema.saga.sagabackend.repository;

import org.springframework.stereotype.Repository;
import sistema.saga.sagabackend.model.AnoLetivo;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AnoLetivoDAO {
    public boolean gravar(AnoLetivo anoLetivo, Conexao conexao) {
        String sql = """
        INSERT INTO anoletivo (anoletivo_inicio, anoletivo_fim)
        VALUES (#1, '#2')
    """;
        sql = sql.replace("#1", "" + anoLetivo.getInicio());
        sql = sql.replace("#2", "" + anoLetivo.getFim());
        return conexao.manipular(sql);
    }

    public boolean alterar(AnoLetivo anoLetivo, Conexao conexao) {
        String sql = """
            UPDATE anoletivo SET anoletivo_inicio = '#1', anoletivo_fim = '#2'
            WHERE anoletivo_id = '#3'
        """;
        sql = sql.replace("#1", "" + anoLetivo.getInicio());
        sql = sql.replace("#2", "" + anoLetivo.getFim());
        sql = sql.replace("#3", String.valueOf(anoLetivo.getId()));
        return conexao.manipular(sql);
    }

    public boolean apagar(AnoLetivo anoLetivo, Conexao conexao) {
        String sql = "DELETE FROM anoletivo WHERE anoletivo_id = '#1'";
        sql = sql.replace("#1", String.valueOf(anoLetivo.getId()));
        return conexao.manipular(sql);
    }

    public int getAno(AnoLetivo anoLetivo, Conexao conexao) {
        String sql = "SELECT * FROM anoletivo WHERE anoletivo_id = '#1'";
        sql = sql.replace("#1", String.valueOf(anoLetivo.getId()));

        try {
            ResultSet rs = conexao.consultar(sql);
            if (rs.next()) {
                anoLetivo.setId(rs.getInt("anoletivo_id"));
                anoLetivo.setInicio(rs.getDate("anoletivo_inicio").toLocalDate());
                anoLetivo.setFim(rs.getDate("anoletivo_fim").toLocalDate());
                return rs.getInt("anoletivo_id");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar ano por ID", e);
        }

        return 0;
    }

    public List<AnoLetivo> get(String filtro, Conexao conexao) {
        List<AnoLetivo> anoLetivoList = new ArrayList<>();
        String sql;

        if (filtro == null || filtro.isEmpty()) {
            sql = "SELECT * FROM anoletivo ORDER BY anoletivo_inicio";
        }
        else {
            sql = "SELECT * FROM anoletivo WHERE EXTRACT(YEAR FROM anoletivo_inicio)::TEXT ILIKE '%" + filtro + "%'";
        }

        try {
            ResultSet rs = conexao.consultar(sql);
            while (rs.next()) {
                AnoLetivo anoLetivo = new AnoLetivo(
                        rs.getInt("anoletivo_id"),
                        rs.getDate("anoletivo_inicio").toLocalDate(),
                        rs.getDate("anoletivo_fim").toLocalDate()
                );
                anoLetivoList.add(anoLetivo);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar ano letivo", e);
        }

        return anoLetivoList;
    }
}
