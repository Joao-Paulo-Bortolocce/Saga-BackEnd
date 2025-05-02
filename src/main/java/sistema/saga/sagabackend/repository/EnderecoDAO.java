package sistema.saga.sagabackend.repository;

import org.springframework.stereotype.Repository;
import sistema.saga.sagabackend.model.Endereco;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EnderecoDAO {

    public EnderecoDAO() {
    }

    public boolean gravar(Endereco endereco, Conexao conexao) {
        String sql;
        if(endereco.getComplemento()==null){
            sql = """
                INSERT INTO endereco(endereco_rua, endereco_numero, endereco_cep, endereco_cidade, endereco_uf) 
                VALUES ('#1', '#2', '#4', '#5', '#6')
                """;
        }
        else{
            sql = """
                INSERT INTO endereco(endereco_rua, endereco_numero, endereco_complemento, endereco_cep, endereco_cidade, endereco_uf) 
                VALUES ('#1', '#2', '#3', '#4', '#5', '#6')
                """;
            sql = sql.replace("#3", endereco.getComplemento());
        }
        sql = sql.replace("#1", endereco.getRua());
        sql = sql.replace("#2",""+endereco.getNumero());
        sql = sql.replace("#4", endereco.getCep());
        sql = sql.replace("#5", endereco.getCidade());
        sql = sql.replace("#6", endereco.getUf());

        return conexao.manipular(sql);
    }

    public boolean alterar(Endereco endereco, Conexao conexao) {
        String sql;
        if(endereco.getComplemento()==null){
            sql = """
                UPDATE endereco 
                SET endereco_rua = '#1', endereco_numero = '#2', endereco_cep = '#4', endereco_cidade = '#5', endereco_uf = '#6' 
                WHERE endereco_id = '#7'
                """;
        }
        else{
            sql = """
                UPDATE endereco 
                SET endereco_rua = '#1', endereco_numero = '#2', endereco_complemento = '#3', 
                endereco_cep = '#4', endereco_cidade = '#5', endereco_uf = '#6' 
                WHERE endereco_id = '#7'
                """;
            sql = sql.replace("#3", endereco.getComplemento());
        }
        sql = sql.replace("#1", endereco.getRua());
        sql = sql.replace("#2", ""+endereco.getNumero());
        sql = sql.replace("#4", endereco.getCep());
        sql = sql.replace("#5", endereco.getCidade());
        sql = sql.replace("#6", endereco.getUf());
        sql = sql.replace("#7", ""+endereco.getId());

        return conexao.manipular(sql);
    }

    public boolean apagar(Endereco endereco, Conexao conexao) {
        String sql = "DELETE FROM endereco WHERE endereco_id = '#1'";
        sql = sql.replace("#1", ""+endereco.getId());

        return conexao.manipular(sql);
    }

    public Endereco getEndereco(Endereco endereco, Conexao conexao) {
        String sql = "SELECT * FROM endereco WHERE endereco_rua like '#1' and endereco_numero = '#2'";
        sql = sql.replace("#1", endereco.getRua());
        sql = sql.replace("#2",""+ endereco.getNumero());
        try {
            ResultSet resultSet= conexao.consultar(sql);
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

    public Endereco getEndereco(int id, Conexao conexao) {
        String sql = "SELECT * FROM endereco WHERE endereco_id = '#1'";
        sql = sql.replace("#1", ""+id);
        try {
            ResultSet resultSet= conexao.consultar(sql);
            if (resultSet.next()) {
                String complemento= resultSet.getString("endereco_complemento");
                return new Endereco(
                        resultSet.getLong("endereco_id"),
                        resultSet.getString("endereco_rua"),
                        resultSet.getInt("endereco_numero"),
                        complemento,
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

    public List<Endereco> get(String filtro,Conexao conexao) {
        String sql = "SELECT * FROM endereco WHERE endereco_cidade LIKE '%#1%'";
        sql = sql.replace("#1", filtro);

        List<Endereco> enderecos = new ArrayList<>();
        try {
            ResultSet resultSet = conexao.consultar(sql);
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
