package sistema.saga.sagabackend.repository;

import org.springframework.stereotype.Repository;
import sistema.saga.sagabackend.model.Endereco;
import sistema.saga.sagabackend.model.Pessoa;

import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Pessoa getPessoa(Pessoa pessoa,Conexao conexao,Map<String, Object> end) {
        String sql = "SELECT * FROM pessoa join endereco on pessoa_enderecoid=endereco_id WHERE pessoa_cpf = '#1'";
        sql = sql.replace("#1", pessoa.getCpf());

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
                String pessoaEstadoCivil = resultSet.getString("pessoa_estadocivil");
                pessoa.setCpf(pessoaCpf);
                pessoa.setRg(pessoaRg);
                pessoa.setNome(pessoaNome);
                pessoa.setDataNascimento(pessoaDataNascimento.toLocalDate());
                pessoa.setSexo(pessoaSexo);
                pessoa.setLocNascimento(pessoaLocNascimento);
                pessoa.setEstadoNascimento(pessoaEstadoNascimento);
                pessoa.setEstadoCivil(pessoaEstadoCivil);
                end.put("endereco_rua", resultSet.getString("endereco_rua"));
                end.put("endereco_num", resultSet.getInt("endereco_numero"));
                end.put("endereco_complemento", resultSet.getString("endereco_complemento"));
                end.put("endereco_cep", resultSet.getString("endereco_cep"));
                end.put("endereco_id", resultSet.getInt("endereco_id"));
                end.put("endereco_cidade", resultSet.getString("endereco_cidade"));
                end.put("endereco_uf", resultSet.getString("endereco_uf"));
                return pessoa;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Pessoa> get(String filtro,Conexao conexao,List<Map<String,Object>> enderecos) {
        String sql = "SELECT * FROM pessoa join endereco on pessoa_enderecoid=endereco_id WHERE pessoa_nome LIKE '%#1%' order by(pessoa_nome)";
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
                String pessoaEstadoCivil = resultSet.getString("pessoa_estadocivil");
                Map<String, Object> end= new HashMap<>();
               end.put("endereco_rua", resultSet.getString("endereco_rua"));
                end.put("endereco_num", resultSet.getInt("endereco_numero"));
                end.put("endereco_complemento", resultSet.getString("endereco_complemento"));
                end.put("endereco_cep", resultSet.getString("endereco_cep"));
                end.put("endereco_id", resultSet.getInt("endereco_id"));
                end.put("endereco_cidade", resultSet.getString("endereco_cidade"));
                end.put("endereco_uf", resultSet.getString("endereco_uf"));
                enderecos.add(end);
                pessoas.add(new Pessoa(pessoaCpf, pessoaRg, pessoaNome, pessoaDataNascimento.toLocalDate(),
                        pessoaSexo, pessoaLocNascimento, pessoaEstadoNascimento, null, pessoaEstadoCivil));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return pessoas;
    }
}
