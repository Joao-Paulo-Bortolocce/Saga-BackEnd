package sistema.saga.sagabackend.repository;

import sistema.saga.sagabackend.model.Ficha;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FichaDAO
{
    public FichaDAO() {

    }

    public boolean gravar(Ficha ficha, Conexao conexao) {
        String sql = """
                        INSERT INTO ficha (ficha_bimestre_id, ficha_bimestre_anoLetivo_id, ficha_bimestre_serie_id) 
                        VALUES('#1', '#2', '#3')
                """;
        sql = sql.replace("#1", "" + ficha.getFicha_bimestre_id());
        sql = sql.replace("#2", "" + ficha.getFicha_bimestre_anoLetivo_id());
        sql = sql.replace("#3", "" + ficha.getFicha_bimestre_serie_id());

        return conexao.manipular(sql);
    }

    public boolean alterar(Ficha ficha, Conexao conexao) {
        String sql = """
                            UPDATE ficha SET ficha_bimestre_anoLetivo_id = '#1', ficha_bimestre_serie_id = '#2',ficha_bimestre_id = '#3'
                            WHERE ficha_id = '#4';
                """;
        sql = sql.replace("#3", "" + ficha.getFicha_bimestre_id());
        sql = sql.replace("#1", "" + ficha.getFicha_bimestre_anoLetivo_id());
        sql = sql.replace("#2", "" + ficha.getFicha_bimestre_serie_id());
        sql = sql.replace("#4", "" + ficha.getFicha_id());

        return conexao.manipular(sql);
    }

    public boolean apagar(int id, Conexao conexao) {
        String sql = """
                            DELETE FROM ficha WHERE ficha_id = '#1';
                """;
        sql = sql.replace("#1", "" + id);

        return conexao.manipular(sql);
    }

    public List<Ficha> getAll(Conexao conexao) {
        String sql = """
                SELECT * FROM ficha;
                """;
        List<Ficha> fichas = new ArrayList<>();
        try{
            ResultSet resultSet = conexao.consultar(sql);
            while(resultSet.next()) {
                fichas.add(new Ficha(resultSet.getInt("ficha_id"),resultSet.getInt("ficha_bimestre_id"), resultSet.getInt("ficha_bimestre_anoLetivo_id"), resultSet.getInt("ficha_bimestre_serie_id")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fichas;
    }

    public Ficha getFicha(Ficha ficha, Conexao conexao) {
        String sql = """
                SELECT * FROM ficha WHERE ficha_id = '#1'
                """;
        sql = sql.replace("#1", "" + ficha.getFicha_id());
        try {
            ResultSet resultSet = conexao.consultar(sql);
            if (resultSet.next()) {
                ficha.setFicha_bimestre_id(resultSet.getInt("ficha_bimestre_id"));
                ficha.setFicha_bimestre_anoLetivo_id(resultSet.getInt("ficha_bimestre_anoLetivo_id"));
                ficha.setFicha_bimestre_serie_id(resultSet.getInt("ficha_bimestre_serie_id"));
                return ficha;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
