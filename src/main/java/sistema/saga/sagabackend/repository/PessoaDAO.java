package sistema.saga.sagabackend.repository;

import org.springframework.stereotype.Repository;
import sistema.saga.sagabackend.model.Endereco;
import sistema.saga.sagabackend.model.Pessoa;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PessoaDAO {


    public boolean gravar(Pessoa entidade,Conexao conexao) {
        String sql = """
                INSERT INTO pessoa(pessoa_cpf, pessoa_rg, pessoa_nome, pessoa_datanascimento, pessoa_sexo, 
                pessoa_locnascimento, pessoa_estadonascimento, pessoa_enderecoid, pessoa_estadocivil) 
                VALUES ('#1', '#2', '#3', '#4', '#5', '#6', '#7', '#8', '#9')
                """;
        sql = sql.replace("#1", entidade.getCpf());
        sql = sql.replace("#2", entidade.getRg());
        sql = sql.replace("#3", entidade.getNome());
        sql = sql.replace("#4", "" + entidade.getDataNascimento());
        sql = sql.replace("#5", entidade.getSexo());
        sql = sql.replace("#6", entidade.getLocNascimento());
        sql = sql.replace("#7", entidade.getEstadoNascimento());
        sql = sql.replace("#8", "" + entidade.getEndereco().getId());
        sql = sql.replace("#9", entidade.getEstadoCivil());
        return conexao.manipular(sql);
    }

    public boolean alterar(Pessoa entidade,Conexao conexao) {
        String sql = """
                UPDATE pessoa 
                SET pessoa_rg = '#1', pessoa_nome = '#2', pessoa_datanascimento = '#3', pessoa_sexo = '#4', 
                pessoa_locnascimento = '#5', pessoa_estadonascimento = '#6', pessoa_enderecoid = '#7', 
                pessoa_estadocivil = '#8' 
                WHERE pessoa_cpf = '#9'
                """;
        sql = sql.replace("#1", entidade.getRg());
        sql = sql.replace("#2", entidade.getNome());
        sql = sql.replace("#3", "" + entidade.getDataNascimento());
        sql = sql.replace("#4", entidade.getSexo());
        sql = sql.replace("#5", entidade.getLocNascimento());
        sql = sql.replace("#6", entidade.getEstadoNascimento());
        sql = sql.replace("#7", "" + entidade.getEndereco().getId());
        sql = sql.replace("#8", entidade.getEstadoCivil());
        sql = sql.replace("#9", entidade.getCpf());
        return conexao.manipular(sql);
    }

    public boolean apagar(Pessoa entidade,Conexao conexao) {
        String sql = "DELETE FROM pessoa WHERE pessoa_cpf = '#1'";
        sql = sql.replace("#1", entidade.getCpf());
        return conexao.manipular(sql);
    }

    public Pessoa getPessoa(String cpf,Conexao conexao) {
        String sql = "SELECT * FROM pessoa WHERE pessoa_cpf = '#1'";
        sql = sql.replace("#1", cpf);

        try{
            ResultSet resultSet = conexao.consultar(sql);
            if (resultSet.next()) {
                String pessoaCpf = resultSet.getString("pessoa_cpf");
                String pessoaRg = resultSet.getString("pessoa_rg");
                String pessoaNome = resultSet.getString("pessoa_nome");
                Date pessoaDataNascimento = resultSet.getDate("pessoa_datanascimento");
                String pessoaSexo = resultSet.getString("pessoa_sexo");
                String pessoaLocNascimento = resultSet.getString("pessoa_locnascimento");
                String pessoaEstadoNascimento = resultSet.getString("pessoa_estadonascimento");
                int pessoaEnderecoId = resultSet.getInt("pessoa_enderecoid");
                String pessoaEstadoCivil = resultSet.getString("pessoa_estadocivil");
                Endereco endereco = new Endereco(pessoaEnderecoId);
                endereco=endereco.buscaEndereco(pessoaEnderecoId,conexao);
                Pessoa pessoa =new Pessoa(pessoaCpf, pessoaRg, pessoaNome, pessoaDataNascimento.toLocalDate(),
                        pessoaSexo, pessoaLocNascimento, pessoaEstadoNascimento, endereco, pessoaEstadoCivil);
                return pessoa;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public List<Pessoa> get(String filtro,Conexao conexao) {
        String sql = "SELECT * FROM pessoa WHERE pessoa_nome LIKE '%#1%'";
        sql = sql.replace("#1", filtro);

        List<Pessoa> pessoas = new ArrayList<>();
        try{
            ResultSet resultSet = conexao.consultar(sql);
            while (resultSet.next()) {
                String pessoaCpf = resultSet.getString("pessoa_cpf");
                String pessoaRg = resultSet.getString("pessoa_rg");
                String pessoaNome = resultSet.getString("pessoa_nome");
                Date pessoaDataNascimento = resultSet.getDate("pessoa_datanascimento");
                String pessoaSexo = resultSet.getString("pessoa_sexo");
                String pessoaLocNascimento = resultSet.getString("pessoa_locnascimento");
                String pessoaEstadoNascimento = resultSet.getString("pessoa_estadonascimento");
                int pessoaEnderecoId = resultSet.getInt("pessoa_enderecoid");
                String pessoaEstadoCivil = resultSet.getString("pessoa_estadocivil");
                Endereco endereco = new Endereco(pessoaEnderecoId);
                endereco=endereco.buscaEndereco(pessoaEnderecoId,conexao);
                pessoas.add(new Pessoa(pessoaCpf, pessoaRg, pessoaNome, pessoaDataNascimento.toLocalDate(),
                        pessoaSexo, pessoaLocNascimento, pessoaEstadoNascimento, endereco, pessoaEstadoCivil));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return pessoas;
    }
}
