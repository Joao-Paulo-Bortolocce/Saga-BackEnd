package sistema.saga.sagabackend.repository;

import sistema.saga.sagabackend.model.Avaliacao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AvaliacaoDAO {

    public AvaliacaoDAO() {
    }

    public boolean gravar(Avaliacao avaliacao, Conexao conexao) {
        String sql = """
            INSERT INTO avaliacao(avaliacao_aluno_cod, avaliacao_serie_cod, avaliacao_materia_cod, avaliacao_habilidade_cod, avaliacao_descricao)
            VALUES ('#1', '#2', '#3', '#4', '#5');
        """;
        sql = sql.replace("#1", "" + avaliacao.getAvaliacao_aluno_cod());
        sql = sql.replace("#2", "" + avaliacao.getAvaliacao_serie_cod());
        sql = sql.replace("#3", "" + avaliacao.getAvaliacao_materia_cod());
        sql = sql.replace("#4", "" + avaliacao.getAvaliacao_habilidade_cod());
        sql = sql.replace("#5", avaliacao.getAvaliacao_descricao());

        return conexao.manipular(sql);
    }

//    public boolean apagarPorAlunoESerie(long ra, long serieId, Conexao conexao) {
//        String sql = """
//            DELETE FROM avaliacao WHERE avaliacao_aluno_ra = '#1' AND avaliacao_serie_cod = '#2'
//        """;
//        sql = sql.replace("#1", "" + ra);
//        sql = sql.replace("#2", "" + serieId);
//
//        return conexao.manipular(sql);
//    }

    public List<Avaliacao> getAll(Conexao conexao) {
        String sql = """
                SELECT * FROM avaliacao
                """;
        List<Avaliacao> avaliacoes = new ArrayList<>();
        try {
            ResultSet rs = conexao.consultar(sql);
            while (rs.next()) {
                Avaliacao av = new Avaliacao();
                av.setAvaliacao_cod(rs.getInt("avaliacao_cod"));
                av.setAvaliacao_aluno_cod(rs.getInt("avaliacao_aluno_cod"));
                av.setAvaliacao_serie_cod(rs.getInt("avaliacao_serie_cod"));
                av.setAvaliacao_materia_cod(rs.getInt("avaliacao_materia_cod"));
                av.setAvaliacao_habilidade_cod(rs.getInt("avaliacao_habilidade_cod"));
                av.setAvaliacao_descricao(rs.getString("avaliacao_descricao"));

                avaliacoes.add(av);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return avaliacoes;
    }

    public List<Avaliacao> getPorAlunoESerie(Avaliacao avaliacao, Conexao conexao) {
        String sql = """
        SELECT * FROM avaliacao
        WHERE avaliacao_aluno_cod = '#1' AND avaliacao_serie_cod = '#2'
    """;
        sql = sql.replace("#1", String.valueOf(avaliacao.getAvaliacao_aluno_cod()));
        sql = sql.replace("#2", String.valueOf(avaliacao.getAvaliacao_serie_cod()));

        List<Avaliacao> avaliacoes = new ArrayList<>();
        try {
            ResultSet rs = conexao.consultar(sql);
            while (rs.next()) {
                Avaliacao av = new Avaliacao();
                av.setAvaliacao_cod(rs.getInt("avaliacao_cod"));
                av.setAvaliacao_aluno_cod(rs.getInt("avaliacao_aluno_cod"));
                av.setAvaliacao_serie_cod(rs.getInt("avaliacao_serie_cod"));
                av.setAvaliacao_materia_cod(rs.getInt("avaliacao_materia_cod"));
                av.setAvaliacao_habilidade_cod(rs.getInt("avaliacao_habilidade_cod"));
                av.setAvaliacao_descricao(rs.getString("avaliacao_descricao"));

                avaliacoes.add(av);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return avaliacoes;
    }

}
