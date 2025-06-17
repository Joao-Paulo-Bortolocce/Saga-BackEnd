package sistema.saga.sagabackend.repository;

import sistema.saga.sagabackend.model.HabilidadesDaFicha;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HabilidadesDaFichaDAO {

    public boolean gravar(HabilidadesDaFicha habilidadesDaFicha, Conexao conexao) {
        String sql = """
                   INSERT INTO habilidadesDaFicha(habilidadesDaFicha_habilidades_id, habilidadesDaFicha_ficha_id) VALUES ('#1', '#2');
                """;
        sql = sql.replace("#1", "" + habilidadesDaFicha.getHabilidadesDaFicha_habilidades_id());
        sql = sql.replace("#2", "" + habilidadesDaFicha.getHabilidadesDaFicha_ficha_id());

        return conexao.manipular(sql);
    }

    public boolean alterar(HabilidadesDaFicha habilidadesDaFicha, Conexao conexao) {
        String sql = """
                UPDATE habilidadesDaFicha SET habilidadesDaFicha_habilidades_id = '#1', habilidadesDaFicha_ficha_id = '#2' WHERE habilidadesDaFicha_id = '#3';
                """;
        sql = sql.replace("#1", "" + habilidadesDaFicha.getHabilidadesDaFicha_habilidades_id());
        sql = sql.replace("#2", "" + habilidadesDaFicha.getHabilidadesDaFicha_ficha_id());
        sql = sql.replace("#3", "" + habilidadesDaFicha.getHabilidadesDaFicha_id());

        return conexao.manipular(sql);
    }

    public boolean apagar(int idHab, int idFicha ,Conexao conexao) {
        String sql = """
                DELETE FROM habilidadesDaFicha WHERE habilidadesdaficha_habilidades_id = '#1' AND habilidadesDaFicha_ficha_id = '#2';
                """;
        sql = sql.replace("#1", "" + idHab);
        sql = sql.replace("#2", "" + idFicha);

        return conexao.manipular(sql);
    }

    public List<HabilidadesDaFicha> getHabilidadesDaFicha(Conexao conexao) {
        String sql = """
                SELECT * FROM habilidadesDaFicha;
                """;
        List<HabilidadesDaFicha> habilidadesDaFichas = new ArrayList<>();
        try{
            ResultSet resultSet = conexao.consultar(sql);
            while(resultSet.next()) {
                habilidadesDaFichas.add(new HabilidadesDaFicha(resultSet.getInt("habilidadesDaFicha_id"), resultSet.getInt("habilidadesDaFicha_habilidades_id"), resultSet.getInt("habilidadesDaFicha_ficha_id")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return habilidadesDaFichas;
    }

    public List<HabilidadesDaFicha> getHabilidadesDaFichaId(int idFicha, Conexao conexao) {
        String sql = """
                SELECT * FROM habilidadesDaFicha WHERE habilidadesdaficha_ficha_id = '#1';
                """;
        sql = sql.replace("#1", "" + idFicha);
        List<HabilidadesDaFicha> habilidadesDaFichas = new ArrayList<>();
        try{
            ResultSet resultSet = conexao.consultar(sql);
            while(resultSet.next()) {
                habilidadesDaFichas.add(new HabilidadesDaFicha(resultSet.getInt("habilidadesDaFicha_id"), resultSet.getInt("habilidadesDaFicha_habilidades_id"), resultSet.getInt("habilidadesDaFicha_ficha_id")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return habilidadesDaFichas;
    }
}
