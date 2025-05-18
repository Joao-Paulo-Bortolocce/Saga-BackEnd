package sistema.saga.sagabackend.repository;
import org.springframework.stereotype.Repository;
import sistema.saga.sagabackend.model.Profissional;


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProfissionalDAO {

    public boolean gravar(Profissional profissional, Conexao conexao) {
        String sql = """
            INSERT INTO profissional(profissional_ra, profissional_tipo, profissional_dataadmissao, profissional_pessoa_cpf, profissional_graduacao_id, profissional_usuario, profissional_senha)
            VALUES (#1, #2, '#3', '#4', #5, '#6', '#7')
        """;
        sql = sql.replace("#1", String.valueOf(profissional.getProfissional_ra()));
        sql = sql.replace("#2", String.valueOf(profissional.getProfissional_tipo()));
        sql = sql.replace("#3", String.valueOf(profissional.getProfissional_dataAdmissao()));
        sql = sql.replace("#4", profissional.getProfissional_pessoa().getCpf());
        sql = sql.replace("#5", String.valueOf(profissional.getProfissional_graduacao().getId()));
        sql = sql.replace("#6", profissional.getProfissional_usuario());
        sql = sql.replace("#7", profissional.getProfissional_senha());

        return conexao.manipular(sql);
    }

    public boolean alterar(Profissional profissional, Conexao conexao) {
        String sql = """
            UPDATE profissional
            SET profissional_tipo = #1,
                profissional_dataadmissao = '#2',
                profissional_graduacao_id = #3,
                profissional_usuario = '#4',
                profissional_senha = '#5'
                profissional_pessoa_cpf = '#6'
            WHERE profissional_ra = #7
        """;
        sql = sql.replace("#1", String.valueOf(profissional.getProfissional_tipo()));
        sql = sql.replace("#2", String.valueOf(profissional.getProfissional_dataAdmissao()));
        sql = sql.replace("#3", String.valueOf(profissional.getProfissional_graduacao().getId()));
        sql = sql.replace("#4", profissional.getProfissional_usuario());
        sql = sql.replace("#5", profissional.getProfissional_senha());
        sql = sql.replace("#6", profissional.getProfissional_pessoa().getCpf());
        sql = sql.replace("#7", String.valueOf(profissional.getProfissional_ra()));

        return conexao.manipular(sql);
    }

    public boolean apagar(int ra, Conexao conexao) {
        String sql = "DELETE FROM profissional WHERE profissional_ra = " + ra;
        return conexao.manipular(sql);
    }

    public List<Profissional> get(Conexao conexao, List<Map<String, Object>> pessoas, List<Map<String, Object>> graduacoes) {
        String sql = "SELECT * FROM profissional " +
                "JOIN pessoa ON profissional_pessoa_cpf = pessoa_cpf " +
                "JOIN endereco ON pessoa_enderecoid = endereco_id " +
                "JOIN graduacao ON profissional_graduacao_id = graduacao_id "+
                "ORDER BY pessoa_nome;";
        List<Profissional> lista = new ArrayList<>();
        try {
            ResultSet rs = conexao.consultar(sql);
            while (rs.next()) {
                Profissional profissional = new Profissional();
                profissional.setProfissional_ra(rs.getInt("profissional_ra"));
                profissional.setProfissional_tipo(rs.getInt("profissional_tipo"));
                profissional.setProfissional_dataAdmissao(rs.getDate("profissional_dataadmissao").toLocalDate());
                profissional.setProfissional_usuario(rs.getString("profissional_usuario"));
                profissional.setProfissional_senha(rs.getString("profissional_senha"));
                profissional.setProfissional_pessoa(null);
                profissional.setProfissional_graduacao(null);

                Map<String, Object> pessoa = new HashMap<>();
                pessoa.put("pessoa_cpf", rs.getString("pessoa_cpf"));
                pessoa.put("pessoa_nome", rs.getString("pessoa_nome"));
                pessoa.put("pessoa_rg", rs.getString("pessoa_rg"));
                pessoa.put("pessoa_datanascimento", rs.getDate("pessoa_datanascimento"));
                pessoa.put("pessoa_sexo", rs.getString("pessoa_sexo"));
                pessoa.put("pessoa_locNascimento", rs.getString("pessoa_locnascimento"));
                pessoa.put("pessoa_estadoNascimento", rs.getString("pessoa_estadonascimento"));
                pessoa.put("pessoa_estadoCivil", rs.getString("pessoa_estadocivil"));

                Map<String, Object> end = new HashMap<>();
                end.put("endereco_rua", rs.getString("endereco_rua"));
                end.put("endereco_num", rs.getInt("endereco_numero"));
                end.put("endereco_complemento", rs.getString("endereco_complemento"));
                end.put("endereco_cep", rs.getString("endereco_cep"));
                end.put("endereco_id", rs.getInt("endereco_id"));
                end.put("endereco_cidade", rs.getString("endereco_cidade"));
                end.put("endereco_uf", rs.getString("endereco_uf"));

                pessoa.put("endereco", end);
                pessoas.add(pessoa);

                Map<String, Object> graduacao= new HashMap<>();
                graduacao.put("graduacao_id",rs.getInt("graduacao_id"));
                graduacao.put("graduacao_descricao",rs.getString("graduacao_descricao"));
                graduacoes.add(graduacao);

                lista.add(profissional);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    public Profissional getProfissional(Conexao conexao, Profissional profissional,Map<String, Object> pessoa,Map<String, Object> graduacao) {
        String sql = "SELECT * FROM profissional " +
                "JOIN pessoa ON profissional_pessoa_cpf = pessoa_cpf " +
                "JOIN endereco ON pessoa_enderecoid = endereco_id " +
                "JOIN graduacao ON profissional_graduacao_id = graduacao_id "+
                "WHERE profissional_ra=#1 ORDER BY pessoa_nome;";
        sql=sql.replace("#1",""+profissional.getProfissional_ra());

        try {
            ResultSet rs = conexao.consultar(sql);
            if (rs.next()) {
                profissional = new Profissional();
                profissional.setProfissional_ra(rs.getInt("profissional_ra"));
                profissional.setProfissional_tipo(rs.getInt("profissional_tipo"));
                profissional.setProfissional_dataAdmissao(rs.getDate("profissional_dataadmissao").toLocalDate());
                profissional.setProfissional_usuario(rs.getString("profissional_usuario"));
                profissional.setProfissional_senha(rs.getString("profissional_senha"));
                profissional.setProfissional_pessoa(null);
                profissional.setProfissional_graduacao(null);


                pessoa.put("pessoa_cpf", rs.getString("pessoa_cpf"));
                pessoa.put("pessoa_nome", rs.getString("pessoa_nome"));
                pessoa.put("pessoa_rg", rs.getString("pessoa_rg"));
                pessoa.put("pessoa_datanascimento", rs.getDate("pessoa_datanascimento"));
                pessoa.put("pessoa_sexo", rs.getString("pessoa_sexo"));
                pessoa.put("pessoa_locNascimento", rs.getString("pessoa_locnascimento"));
                pessoa.put("pessoa_estadoNascimento", rs.getString("pessoa_estadonascimento"));
                pessoa.put("pessoa_estadoCivil", rs.getString("pessoa_estadocivil"));

                Map<String, Object> end = new HashMap<>();
                end.put("endereco_rua", rs.getString("endereco_rua"));
                end.put("endereco_num", rs.getInt("endereco_numero"));
                end.put("endereco_complemento", rs.getString("endereco_complemento"));
                end.put("endereco_cep", rs.getString("endereco_cep"));
                end.put("endereco_id", rs.getInt("endereco_id"));
                end.put("endereco_cidade", rs.getString("endereco_cidade"));
                end.put("endereco_uf", rs.getString("endereco_uf"));

                pessoa.put("endereco", end);

                graduacao.put("graduacao_id",rs.getInt("graduacao_id"));
                graduacao.put("graduacao_descricao",rs.getString("graduacao_descricao"));


                return profissional;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Profissional getUsuario(Conexao conexao, int ra, String senha) {
        String sql = """
                SELECT * FROM profissional WHERE profissional_ra=#1 and profissional_senha='#2';
                """;
        sql=sql.replace("#1",""+ra);
        sql=sql.replace("#2",senha);
        List<Profissional> lista = new ArrayList<>();
        try {
            ResultSet rs = conexao.consultar(sql);
            if (rs.next()) {
                Profissional profissional = new Profissional();
                profissional.setProfissional_ra(rs.getInt("profissional_ra"));
                profissional.setProfissional_tipo(rs.getInt("profissional_tipo"));
                profissional.setProfissional_dataAdmissao(rs.getDate("profissional_dataadmissao").toLocalDate());
                profissional.setProfissional_usuario(rs.getString("profissional_usuario"));
                profissional.setProfissional_senha(rs.getString("profissional_senha"));
                profissional.setProfissional_pessoa(null);
                profissional.setProfissional_graduacao(null);
                return profissional;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
