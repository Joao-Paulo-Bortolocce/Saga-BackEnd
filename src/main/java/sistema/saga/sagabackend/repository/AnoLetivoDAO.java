package sistema.saga.sagabackend.repository;

import sistema.saga.sagabackend.model.AnoLetivo;

import java.sql.ResultSet;

public class AnoLetivoDAO {
    public int getAnoLetivo(AnoLetivo anoLetivo, Conexao conexao) {
        String sql = "SELECT * FROM anoletivo WHERE anoletivo_id = '#1'";
        sql = sql.replace("#1", String.valueOf(anoLetivo.getId()));

        try {
            ResultSet rs = conexao.consultar(sql);
            if (rs.next()) {
                anoLetivo.setId(rs.getInt("anoletivo_id"));
                anoLetivo.setInicio(rs.getDate("anoletivo_inicio").toLocalDate());
                anoLetivo.setFim(rs.getDate("anoletivo_termino").toLocalDate());
                return rs.getInt("anoletivo_id");
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Erro ao buscar série por ID", e);
        }

        return 0;
    }
}
