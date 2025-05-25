package sistema.saga.sagabackend.repository;

import org.springframework.stereotype.Repository;
import sistema.saga.sagabackend.model.Sala;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SalaDAO {
    public boolean gravar(Sala sala, Conexao conexao) {
        String sql = """
        INSERT INTO salas (salas_ncarteiras, salas_descricao)
        VALUES ('#1', '#2')
    """;
        sql = sql.replace("#1", String.valueOf(sala.getNcarterias()));
        sql = sql.replace("#2", sala.getDescricao());

        System.out.println("SQL executado: " + sql); // <--- debug aqui

        return conexao.manipular(sql);
    }

    public boolean alterar(Sala sala, Conexao conexao) {
        String sql = """
            UPDATE salas 
            SET salas_ncarteiras = '#1', salas_descricao = '#2'
            WHERE salas_id = '#3'
        """;
        sql = sql.replace("#1", String.valueOf(sala.getNcarterias()));
        sql = sql.replace("#2", sala.getDescricao());
        sql = sql.replace("#3", String.valueOf(sala.getId()));
        return conexao.manipular(sql);
    }

    public boolean apagar(Sala sala, Conexao conexao) {
        String sql = "DELETE FROM salas WHERE salas_id = '#1'";
        sql = sql.replace("#1", String.valueOf(sala.getId()));
        return conexao.manipular(sql);
    }

    public int getSala(Sala sala, Conexao conexao) {
        String sql = "SELECT * FROM salas WHERE salas_id = '#1'";
        sql = sql.replace("#1", String.valueOf(sala.getId()));
        try {
            ResultSet rs = conexao.consultar(sql);
            if (rs.next()) {
                sala.setId(rs.getInt("salas_id"));
                sala.setNcarterias(rs.getInt("salas_ncarteiras"));
                sala.setDescricao(rs.getString("salas_descricao"));
                return rs.getInt("salas_id");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar sala por ID", e);
        }

        return 0;
    }

    public List<Sala> get(String filtro, Conexao conexao) {
        List<Sala> salas = new ArrayList<>();
        String sql;

        if (filtro == null || filtro.isEmpty()) {
            sql = "SELECT * FROM salas ORDER BY salas_ncarteiras";
        }
        else {
            sql = "SELECT * FROM salas WHERE salas_descricao ILIKE '%" + filtro + "%' OR CAST(salas_ncarteiras AS TEXT) LIKE '%" + filtro + "%'";
        }
        try {
            ResultSet rs = conexao.consultar(sql);
            while (rs.next()) {
                Sala sala = new Sala(
                        rs.getInt("salas_id"),
                        rs.getInt("salas_ncarteiras"),
                        rs.getString("salas_descricao")
                );
                salas.add(sala);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar salas", e);
        }

        return salas;
    }
}