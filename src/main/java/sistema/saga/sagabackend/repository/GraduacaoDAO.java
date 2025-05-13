package sistema.saga.sagabackend.repository;

import org.springframework.stereotype.Repository;
import sistema.saga.sagabackend.model.Graduacao;
import sistema.saga.sagabackend.model.Sala;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GraduacaoDAO {
    public boolean gravar(Graduacao graduacao, Conexao conexao) {
        String sql = """
        INSERT INTO graduacao (graduacao_descricao, graduacao_data)
        VALUES ('#1', '#2')
        """;
        sql = sql.replace("#1", graduacao.getDescricao());
        sql = sql.replace("#2", ""+graduacao.getData());

        return conexao.manipular(sql);
    }

    public boolean alterar(Graduacao graduacao, Conexao conexao) {
        String sql = """
            UPDATE graduacao 
            SET graduacao_descricao = '#1', graduacao_data = '#2'
            WHERE graduacao_id = '#3'
        """;
        sql = sql.replace("#1", graduacao.getDescricao());
        sql = sql.replace("#2", ""+graduacao.getData());
        sql = sql.replace("#3", ""+graduacao.getId());
        return conexao.manipular(sql);
    }

    public boolean apagar(Graduacao graduacao, Conexao conexao) {
        String sql = "DELETE FROM graduacao WHERE graduacao_id = '#1'";
        sql = sql.replace("#1", ""+graduacao.getId());
        return conexao.manipular(sql);
    }

    public int getGraduacao(Graduacao graduacao, Conexao conexao) {
        String sql = "SELECT * FROM graduacao WHERE graduacao_id = '#1'";
        sql = sql.replace("#1", ""+graduacao.getId());
        try {
            ResultSet rs = conexao.consultar(sql);
            if (rs.next()) {
                graduacao.setId(rs.getInt("graduacao_id"));
                graduacao.setDescricao(rs.getString("graduacao_descricao"));
                graduacao.setData(rs.getDate("graduacao_data").toLocalDate());
                return rs.getInt("graduacao_id");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar sala por ID", e);
        }
        return 0;
    }

    public List<Graduacao> get(String filtro, Conexao conexao) {
        List<Graduacao> graduacaos = new ArrayList<>();
        String sql;

        if (filtro == null || filtro.isEmpty()) {
            sql = "SELECT * FROM graduacao ORDER BY graduacao_descricao";
        }
        else {
            sql = "SELECT * FROM graduacao WHERE graduacao_descricao ILIKE '%"+filtro+"%'";
        }
        try {
            ResultSet rs = conexao.consultar(sql);
            while (rs.next()) {
                Graduacao graduacao = new Graduacao(
                        rs.getInt("graduacao_id"),
                        rs.getString("descricao_descricao"),
                        rs.getDate("graduacao_data").toLocalDate()
                );
                graduacaos.add(graduacao);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar graduações", e);
        }
        return graduacaos;
    }
}
