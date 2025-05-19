package sistema.saga.sagabackend.repository;

import org.springframework.stereotype.Repository;
import sistema.saga.sagabackend.model.Notificacao;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class NotificacaoDAO {
    public boolean gravar(Notificacao notificacao, Conexao conexao) {
        String sql = """
        INSERT INTO notificacao (notificacao_mensagem, notificacao_data)
        VALUES ('#1', '#2')
        """;
        sql = sql.replace("#1", notificacao.getNot_texto());
    sql = sql.replace("#2", ""+Date.valueOf(notificacao.getNot_data()));

        return conexao.manipular(sql);
    }

    public boolean alterar(Notificacao notificacao, Conexao conexao) {
        String sql = """
        UPDATE notificacao SET notificacao_visto = #1
        WHERE notificacao_id = '#2'
        """;
        sql = sql.replace("#1", "true");
        sql = sql.replace("#2", ""+notificacao.getNot_id());
        return conexao.manipular(sql);
    }

    public boolean apagar(Notificacao notificacao, Conexao conexao) {
        String sql = "DELETE FROM notificacao WHERE notificacao_id = #1";
        sql = sql.replace("#1", ""+notificacao.getNot_id());
        return conexao.manipular(sql);
    }

    public List<Notificacao> get(Conexao conexao) {
        List<Notificacao> listaNotificacao = new ArrayList<>();
        String sql = "SELECT * FROM notificacao ORDER BY notificacao_data";
        try {
            ResultSet rs = conexao.consultar(sql);
            while (rs.next()) {
                Notificacao notificacao = new Notificacao(
                        rs.getInt("notificacao_id"),
                        rs.getBoolean("notificacao_visto"),
                        rs.getString("notificacao_mensagem"),
                        rs.getDate("notificacao_data").toLocalDate()
                );
                listaNotificacao.add(notificacao);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar as notificações", e);
        }

        return listaNotificacao;
    }
}
