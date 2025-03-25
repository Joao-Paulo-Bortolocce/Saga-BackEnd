package sistema.saga.sagabackend.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import sistema.saga.sagabackend.model.Endereco;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EnderecoDAO {

    private final JdbcTemplate jdbcTemplate;

    public EnderecoDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean gravar(Endereco endereco) {
        String sql = """
                INSERT INTO endereco(endereco_rua, endereco_numero, endereco_complemento, endereco_cep, endereco_cidade, endereco_uf) 
                VALUES ('#1', '#2', '#3', '#4', '#5', '#6')
                """;
        sql = sql.replace("#1", endereco.getRua());
        sql = sql.replace("#2", String.valueOf(endereco.getNumero()));
        sql = sql.replace("#3", endereco.getComplemento());
        sql = sql.replace("#4", endereco.getCep());
        sql = sql.replace("#5", endereco.getCidade());
        sql = sql.replace("#6", endereco.getUf());

        return jdbcTemplate.update(sql) > 0;
    }

    public boolean alterar(Endereco endereco) {
        String sql = """
                UPDATE endereco 
                SET endereco_rua = '#1', endereco_numero = '#2', endereco_complemento = '#3', 
                endereco_cep = '#4', endereco_cidade = '#5', endereco_uf = '#6' 
                WHERE endereco_id = '#7'
                """;
        sql = sql.replace("#1", endereco.getRua());
        sql = sql.replace("#2", String.valueOf(endereco.getNumero()));
        sql = sql.replace("#3", endereco.getComplemento());
        sql = sql.replace("#4", endereco.getCep());
        sql = sql.replace("#5", endereco.getCidade());
        sql = sql.replace("#6", endereco.getUf());
        sql = sql.replace("#7", String.valueOf(endereco.getId()));

        return jdbcTemplate.update(sql) > 0;
    }

    public boolean apagar(Endereco endereco) {
        String sql = "DELETE FROM endereco WHERE endereco_id = '#1'";
        sql = sql.replace("#1", String.valueOf(endereco.getId()));

        return jdbcTemplate.update(sql) > 0;
    }

    public Endereco getEndereco(long id) throws SQLException {
        String sql = "SELECT * FROM endereco WHERE endereco_id = '#1'";
        sql = sql.replace("#1", String.valueOf(id));

        ResultSet resultSet = jdbcTemplate.getDataSource().getConnection().createStatement().executeQuery(sql);
        try {
            if (resultSet.next()) {
                return new Endereco(
                        resultSet.getLong("endereco_id"),
                        resultSet.getString("endereco_rua"),
                        resultSet.getInt("endereco_numero"),
                        resultSet.getString("endereco_complemento"),
                        resultSet.getString("endereco_cep"),
                        resultSet.getString("endereco_cidade"),
                        resultSet.getString("endereco_uf")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Endereco> get(String filtro) {
        String sql = "SELECT * FROM endereco WHERE endereco_cidade LIKE '%#1%'";
        sql = sql.replace("#1", filtro);

        List<Endereco> enderecos = new ArrayList<>();
        try {
            ResultSet resultSet = jdbcTemplate.getDataSource().getConnection().createStatement().executeQuery(sql);
            while (resultSet.next()) {
                enderecos.add(new Endereco(
                        resultSet.getLong("endereco_id"),
                        resultSet.getString("endereco_rua"),
                        resultSet.getInt("endereco_numero"),
                        resultSet.getString("endereco_complemento"),
                        resultSet.getString("endereco_cep"),
                        resultSet.getString("endereco_cidade"),
                        resultSet.getString("endereco_uf")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return enderecos;
    }
}
