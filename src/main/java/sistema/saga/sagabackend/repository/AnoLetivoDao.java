package sistema.saga.sagabackend.repository;

import org.springframework.stereotype.Repository;
import sistema.saga.sagabackend.model.AnoLetivo;
import sistema.saga.sagabackend.model.Serie;

import java.sql.ResultSet;

@Repository
public class AnoLetivoDao {
    public AnoLetivoDao() {
    }

    public AnoLetivo getAno(AnoLetivo anoLetivo, Conexao conexao) {
        String sql = "SELECT * FROM anoletivo WHERE anoletivo_id = '#1'";
        sql = sql.replace("#1", String.valueOf(anoLetivo.getId()));

        try {
            ResultSet rs = conexao.consultar(sql);
            if (rs.next()) {
                anoLetivo.setId(rs.getInt("anoletivo_id"));
                anoLetivo.setInicio(rs.getDate("anoletivo_inicio").toLocalDate());
                anoLetivo.setFim(rs.getDate("anoletivo_termino").toLocalDate());
               return anoLetivo;
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar série por ID", e);
        }

        return null;
    }
}
