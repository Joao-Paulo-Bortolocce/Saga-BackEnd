package sistema.saga.sagabackend.repository;

import sistema.saga.sagabackend.model.Bimestre;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BimestreDAO {

    public List<Bimestre> getAll(Conexao conexao) {
        String sql = """
                SELECT * FROM bimestre;
                """;
        List<Bimestre> bimestres = new ArrayList<>();
        try{
            ResultSet resultSet = conexao.consultar(sql);
            while(resultSet.next()) {
                bimestres.add(new Bimestre(resultSet.getInt("bimestre_id"), resultSet.getInt("bimestre_anoletivo_id")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bimestres;
    }
}
