package sistema.saga.sagabackend.repository;

import org.springframework.stereotype.Repository;
import sistema.saga.sagabackend.model.Aluno;
import sistema.saga.sagabackend.model.Pessoa;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AlunoDAO {

    public boolean gravar(Aluno aluno, Conexao conexao) {
        String sql = """
            INSERT INTO aluno(aluno_ra, aluno_restricaomedica, aluno_pessoa_cpf)
            VALUES (#1, '#2', '#3')
        """;
        sql = sql.replace("#1", String.valueOf(aluno.getRa()));
        sql = sql.replace("#2", aluno.getRestricaoMedica());
        sql = sql.replace("#3", aluno.getPessoa().getCpf());

        return conexao.manipular(sql);
    }

    public boolean alterar(Aluno aluno, Conexao conexao) {
        String sql = """
            UPDATE aluno
            SET aluno_restricaomedica = '#1'
            WHERE aluno_ra = #2
        """;
        sql = sql.replace("#1", aluno.getRestricaoMedica());
        sql = sql.replace("#2", String.valueOf(aluno.getRa()));

        return conexao.manipular(sql);
    }

    public boolean apagar(int ra, Conexao conexao) {
        String sql = "DELETE FROM aluno WHERE aluno_ra = " + ra;
        return conexao.manipular(sql);
    }

    public String getAluno(Aluno aluno, Conexao conexao) {
        String sql = "SELECT * FROM aluno WHERE aluno_ra = " + aluno.getRa();
        try {
            ResultSet rs = conexao.consultar(sql);
            if (rs.next()) {

                aluno.setRa(rs.getInt("aluno_ra"));
                aluno.setRestricaoMedica(rs.getString("aluno_restricaomedica"));
                aluno.setPessoa(null);
                String cpf=rs.getString("aluno_pessoa_cpf");
                return cpf;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Aluno> get( Conexao conexao,List<Map<String, Object>> pessoas) {
        String sql = "SELECT * FROM aluno JOIN pessoa ON aluno_pessoa_cpf = pessoa_cpf JOIN endereco ON pessoa_enderecoid = endereco_id ORDER BY pessoa_nome;";
        List<Aluno> lista = new ArrayList<>();
        Map<String, Object> resposta = new HashMap<>();
        try {
            ResultSet rs = conexao.consultar(sql);
            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setRa(rs.getInt("aluno_ra"));
                aluno.setRestricaoMedica(rs.getString("aluno_restricaomedica"));
                aluno.setPessoa(null);
                Map<String,Object> pessoa= new HashMap<>();

                pessoa.put("cpf", rs.getString("pessoa_cpf"));
                pessoa.put("nome", rs.getString("pessoa_nome"));
                pessoa.put("rg", rs.getString("pessoa_rg"));
                pessoa.put("dataNascimento", rs.getDate("pessoa_datanascimento"));
                pessoa.put("sexo", rs.getString("pessoa_sexo"));
                pessoa.put("locNascimento", rs.getString("pessoa_locnascimento"));
                pessoa.put("estadoNascimento", rs.getString("pessoa_estadonascimento"));
                pessoa.put("estadoCivil", rs.getString("pessoa_estadocivil"));
                Map<String, Object> end= new HashMap<>();
                end.put("endereco_rua", rs.getString("endereco_rua"));
                end.put("endereco_num", rs.getInt("endereco_numero"));
                end.put("endereco_complemento", rs.getString("endereco_complemento"));
                end.put("endereco_cep", rs.getString("endereco_cep"));
                end.put("endereco_id", rs.getInt("endereco_id"));
                end.put("endereco_cidade", rs.getString("endereco_cidade"));
                end.put("endereco_uf", rs.getString("endereco_uf"));
                pessoa.put("endereco",end);

                pessoas.add(pessoa);
                lista.add(aluno);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return lista;
    }
}