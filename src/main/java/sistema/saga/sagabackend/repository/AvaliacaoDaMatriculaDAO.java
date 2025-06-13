package sistema.saga.sagabackend.repository;

import sistema.saga.sagabackend.model.AvaliacaoDaMatricula;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AvaliacaoDaMatriculaDAO {

    public AvaliacaoDaMatriculaDAO() {
    }

    public boolean gravar(AvaliacaoDaMatricula avaliacaoDaMatricula, Conexao conexao) {
        String sql = """
                    INSERT INTO avaliacaodamatricula (avaliacaodamatricula_habilidadesdaficha_habilidadesdaficha_id, avaliacaodamatricula_av) VALUES ('#1', '#2')
                    
                """;
        sql = sql.replace("#1", "" + avaliacaoDaMatricula.getAvaHabId());
        sql = sql.replace("#2", avaliacaoDaMatricula.getAvaAv());

        return conexao.manipular(sql);
    }

    public boolean alterar(AvaliacaoDaMatricula avaliacaoDaMatricula, Conexao conexao) {
        String sql = """
                    UPDATE avaliacaodamatricula SET avaliacaodamatricula_habilidadesdaficha_habilidadesdaficha_id = '#1', avaliacaodamatricula_av = '#2' WHERE avaliacaodamatricula_matricula_matricula_id = '#3'
                """;
        sql = sql.replace("#1", "" + avaliacaoDaMatricula.getAvaHabId());
        sql = sql.replace("#2", avaliacaoDaMatricula.getAvaAv());
        sql = sql.replace("#3", "" + avaliacaoDaMatricula.getAvaMatId());

        return conexao.manipular(sql);
    }

    public boolean apagar(AvaliacaoDaMatricula avaliacaoDaMatricula, Conexao conexao) {
        String sql = """
                DELETE FROM avaliacaodamatricula WHERE avaliacaodamatricula_matricula_matricula_id = '#1' AND avaliacaodamatricula_habilidadesdaficha_habilidadesdaficha_id = '#2'
                """;
        sql = sql.replace("#1", "" + avaliacaoDaMatricula.getAvaMatId());
        sql = sql.replace("#2", "" + avaliacaoDaMatricula.getAvaHabId());

        return conexao.manipular(sql);
    }

    public List<AvaliacaoDaMatricula> recuperaTodos(Conexao conexao) {
        String sql = """
                SELECT * from avaliacaodamatricula
                """;

        List<AvaliacaoDaMatricula> avaliacoes = new ArrayList<>();
        try{
            ResultSet resultSet = conexao.consultar(sql);
            while(resultSet.next()) {
                avaliacoes.add(new AvaliacaoDaMatricula(resultSet.getInt("avaliacaodamatricula_matricula_matricula_id"),resultSet.getInt("avaliacaodamatricula_habilidadesdaficha_habilidadesdaficha_id"), resultSet.getString("avaliacaodamatricula_av")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return avaliacoes;
    }

    public List<AvaliacaoDaMatricula> recuperaAvaliacoes(int id, Conexao conexao) {
        String sql = """
                SELECT * FROM avaliacaodamatricula WHERE avaliacaodamatricula_matricula_matricula_id = '#1'
                """;
        sql = sql.replace("#1", "" + id);
        List<AvaliacaoDaMatricula> avaliacoes = new ArrayList<>();
        try{
            ResultSet resultSet = conexao.consultar(sql);
            while(resultSet.next()) {
                avaliacoes.add(new AvaliacaoDaMatricula(resultSet.getInt("avaliacaodamatricula_matricula_matricula_id"),resultSet.getInt("avaliacaodamatricula_habilidadesdaficha_habilidadesdaficha_id"), resultSet.getString("avaliacaodamatricula_av")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return avaliacoes;
    }

    public AvaliacaoDaMatricula recuperaUmaAvaliacao(AvaliacaoDaMatricula avaliacaoDaMatricula, Conexao conexao) {
        String sql = """
                SELECT * FROM avaliacaodamatricula WHERE avaliacaodamatricula_matricula_matricula_id = '#1' AND avaliacaodamatricula_habilidadesdaficha_habilidadesdaficha_id = '#2'
                """;
        sql = sql.replace("#1", "" + avaliacaoDaMatricula.getAvaMatId());
        sql = sql.replace("#2", "" + avaliacaoDaMatricula.getAvaHabId());
        try {
            ResultSet resultSet = conexao.consultar(sql);
            if (resultSet.next()) {
                avaliacaoDaMatricula.setAvaAv(resultSet.getString("avaliacaodamatricula_av"));
                return avaliacaoDaMatricula;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
