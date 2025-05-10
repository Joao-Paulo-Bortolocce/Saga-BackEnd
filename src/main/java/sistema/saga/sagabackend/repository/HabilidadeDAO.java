package sistema.saga.sagabackend.repository;

import sistema.saga.sagabackend.model.Habilidade;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HabilidadeDAO {

    public HabilidadeDAO() {
    }

    public boolean gravar(Habilidade habilidade ,Conexao conexao) {
        String sql = """
                    INSERT INTO habilidades(habilidades_descricao, habilidades_materia_id) VALUES
                    ('#1', '#2');
                """;
        sql = sql.replace("#1", habilidade.getDescricao());
        sql = sql.replace("#2", "" + habilidade.getMateria_id());

        return conexao.manipular(sql);
    }

    public boolean alterar(Habilidade habilidade, Conexao conexao) {
        String sql = """
                    UPDATE habilidades SET habilidades_descricao = '#1' WHERE habilidade_cod = '#2' AND habilidade_materia_id = '#3'
                """;
        sql = sql.replace("#1", habilidade.getDescricao());
        sql = sql.replace("#2", "" + habilidade.getCod());
        sql = sql.replace("#3", "" + habilidade.getMateria_id());

        return conexao.manipular(sql);
    }

    public boolean deletar(Habilidade habilidade, Conexao conexao) {
        String sql = """
                DELETE FROM habilidades WHERE habilidades_cod = '#1' AND habilidades_mat_id = '#2'
                """;
        sql = sql.replace("#1", "" + habilidade.getCod());
        sql = sql.replace("#2", "" + habilidade.getMateria_id());

        return conexao.manipular(sql);
    }

    public List<Habilidade> getHabilidadeMS(int idMat, int idSerie, Conexao conexao) {
        String sql = """
                    SELECT * FROM habilidades WHERE habilidades_materia_id = '#1' AND habilidades_serie_id = '#2'
                    ORDER BY habilidades_descricao
                    """;
        sql = sql.replace("#1", "" + idMat);
        sql = sql.replace("#2", "" + idSerie);
        List<Habilidade> habilidades = new ArrayList<>();
        try{
            ResultSet resultSet = conexao.consultar(sql);
            while(resultSet.next()) {
                habilidades.add(new Habilidade(resultSet.getInt("habilidades_cod"), resultSet.getString("habilidades_descricao"), resultSet.getInt("habilidades_materia_id")));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return habilidades;
    }

    public List<Habilidade> getHabilidadeM(int idMat, Conexao conexao) {
        String sql = """
                    SELECT * FROM habilidades WHERE habilidades_materia_id = '#1' ORDER BY habilidades_descricao
                    """;
        sql = sql.replace("#1", "" + idMat);
        List<Habilidade> habilidades = new ArrayList<>();
        try{
            ResultSet resultSet = conexao.consultar(sql);
            while(resultSet.next()) {
                habilidades.add(new Habilidade(resultSet.getInt("habilidades_cod"), resultSet.getString("habilidades_descricao"), resultSet.getInt("habilidades_materia_id")));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return habilidades;
    }
}
