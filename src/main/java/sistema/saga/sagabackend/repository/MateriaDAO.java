package sistema.saga.sagabackend.repository;

import sistema.saga.sagabackend.model.Materia;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MateriaDAO {

    public MateriaDAO() {
    }

    public boolean gravar(Materia materia, Conexao conexao) {
        String sql = """
                INSERT INTO materia(materia_nome, materia_carga) VALUES ('#1', '#2');
                """;
        sql = sql.replace("#1", materia.getNome());
        sql = sql.replace("#2","" + materia.getCarga());

        return conexao.manipular(sql);
    }

    public boolean alterar(Materia materia, Conexao conexao) {
        String sql = """
                    UPDATE materia SET materia_nome = '#1', materia_carga = '#2' WHERE materia_id = '#3'
                """;
        sql = sql.replace("#1", materia.getNome());
        sql = sql.replace("#2", "" +  materia.getCarga());
        sql = sql.replace("#3", "" + materia.getId());

        return conexao.manipular(sql);
    }

    public boolean apagar(Materia materia, Conexao conexao) {
        String sql = """
                DELETE FROM materia WHERE materia_id = '#1'
                """;
        sql = sql.replace("#1", "" + materia.getId());

        return conexao.manipular(sql);
    }

    public Materia getId(Long id, Conexao conexao) {
        String sql = """
                    SELECT * WHERE materia_id = '#1' ORDER BY materia_nome
                """;
        sql.replace("#1", "" + id);
        try{
            ResultSet resultSet = conexao.consultar(sql);
            if(resultSet.next()) {
                return new Materia(resultSet.getLong("materia_id"), resultSet.getString("materia_nome"), resultSet.getInt("materia_carga"));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Materia> get(String filtro, Conexao conexao) {
        String sql;
        if(filtro.isEmpty())
        {
            sql = """
                    SELECT * FROM materia ORDER BY materia_nome
                    """;
        } else {
            sql = """
                     SELECT * FROM materia WHERE materia_nome LIKE '%#1%'" ORDER BY materia_nome
                    """;
            sql.replace("#1", filtro);
        }
        List<Materia> materias = new ArrayList<>();
        try{
            ResultSet resultSet = conexao.consultar(sql);
            while(resultSet.next()) {
                materias.add(new Materia(resultSet.getLong("materia_id"), resultSet.getString("materia_nome"), resultSet.getInt("materia_carga")));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return materias;
    }
}
