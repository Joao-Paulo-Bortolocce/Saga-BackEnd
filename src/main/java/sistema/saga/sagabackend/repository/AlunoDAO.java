package sistema.saga.sagabackend.repository;

import org.springframework.stereotype.Repository;
import sistema.saga.sagabackend.model.Aluno;
import sistema.saga.sagabackend.model.Pessoa;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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

    public List<Aluno> get( Conexao conexao,List<String> cpfsPessoa) {
        String sql = "SELECT * FROM aluno ORDER BY aluno_ra";
        List<Aluno> lista = new ArrayList<>();
        try {
            ResultSet rs = conexao.consultar(sql);
            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setRa(rs.getInt("aluno_ra"));
                aluno.setRestricaoMedica(rs.getString("aluno_restricaomedica"));
                aluno.setPessoa(null);
                cpfsPessoa.add(rs.getString("aluno_pessoa_cpf"));
                lista.add(aluno);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return lista;
    }
}