package sistema.saga.sagabackend.repository;

import org.springframework.stereotype.Repository;
import sistema.saga.sagabackend.model.Frequencia;

import java.sql.Date;

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
}
