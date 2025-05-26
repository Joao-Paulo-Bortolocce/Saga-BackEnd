package sistema.saga.sagabackend.repository;

import org.springframework.stereotype.Repository;
import sistema.saga.sagabackend.model.Aluno;
import sistema.saga.sagabackend.model.Matricula;

import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MatriculaDAO {

    public boolean gravar(Matricula matricula, Conexao conexao) {
        String sql = """
                    INSERT INTO matricula (matricula_aluno_ra, matricula_anoletivo_id, matricula_serie_id, matricula_aprovado, matricula_data, matricula_valido) 
                    VALUES (#1, #2, #3, #5, '#6',#7)
                """;

        sql = sql.replace("#1", "" + matricula.getAluno().getRa());
        sql = sql.replace("#2", "" + matricula.getAnoLetivo().getId());
        sql = sql.replace("#3", "" + matricula.getSerie().getSerieId());
        sql = sql.replace("#5", matricula.isAprovado() ? "true" : "false");
        sql = sql.replace("#6", "" + Date.valueOf(matricula.getData()));
        sql = sql.replace("#7", matricula.isValido() ? "true" : "false");
        return conexao.manipular(sql);
    }

    public boolean alterar(Matricula matricula, Conexao conexao) {
        String sql;
        if(matricula.getTurma()!=null){
            sql = """
                    UPDATE matricula SET
                        matricula_aluno_ra = #1,
                        matricula_anoletivo_id = #2,
                        matricula_serie_id = #3,
                        matricula_turma_letra = '#4',
                        matricula_aprovado = #5,
                        matricula_data = '#6',
                        matricula_valido = #7
                    WHERE matricula_id = #8
                """;
            sql = sql.replace("#1", "" + matricula.getAluno().getRa());
            sql = sql.replace("#2", "" + matricula.getAnoLetivo().getId());
            sql = sql.replace("#3", "" + matricula.getSerie().getSerieId());
            sql = sql.replace("#4", "" + matricula.getTurma().getLetra());
            sql = sql.replace("#5", matricula.isAprovado() ? "true" : "false");
            sql = sql.replace("#6", "" + Date.valueOf(matricula.getData()));
            sql = sql.replace("#7", matricula.isValido() ? "true" : "false");
            sql = sql.replace("#8", "" + matricula.getId());
        }
        else{
            sql = """
                    UPDATE matricula SET
                        matricula_aluno_ra = #1,
                        matricula_anoletivo_id = #2,
                        matricula_serie_id = #3,
                        matricula_aprovado = #5,
                        matricula_data = '#6',
                        matricula_valido = #7
                    WHERE matricula_id = #8
                """;
            sql = sql.replace("#1", "" + matricula.getAluno().getRa());
            sql = sql.replace("#2", "" + matricula.getAnoLetivo().getId());
            sql = sql.replace("#3", "" + matricula.getSerie().getSerieId());
            sql = sql.replace("#5", matricula.isAprovado() ? "true" : "false");
            sql = sql.replace("#6", "" + Date.valueOf(matricula.getData()));
            sql = sql.replace("#7", matricula.isValido() ? "true" : "false");
            sql = sql.replace("#8", "" + matricula.getId());
        }
        return conexao.manipular(sql);
    }

    public boolean apagar(int id, Conexao conexao) {
        String sql = "DELETE FROM matricula WHERE matricula_id = " + id;
        return conexao.manipular(sql);
    }

    public List<Matricula> getMatricula(Matricula mat, Conexao conexao, List< Map<String, Object>> alunos, List< Map<String, Object>> anos, List< Map<String, Object>> series, List< Map<String, Object>> turmas) {
        String sql = """
        SELECT * 
        FROM matricula 
        JOIN aluno ON matricula_aluno_ra = aluno_ra
        JOIN pessoa ON aluno_pessoa_cpf = pessoa_cpf
        JOIN endereco ON pessoa_enderecoid = endereco_id
        JOIN anoLetivo ON anoletivo_id = matricula_anoletivo_id
        JOIN serie ON serie_id = matricula_serie_id
        LEFT JOIN turma ON turmaanoletivo_id = matricula_anoletivo_id 
                        AND serieturma_id = matricula_serie_id 
                        AND (
                            matricula_turma_letra IS NULL 
                            OR turma_letra = matricula_turma_letra
                        )
        WHERE matricula_aluno_ra = '#1'
        ORDER BY pessoa_nome
    """;

        sql = sql.replace("#1", String.valueOf(mat.getAluno().getRa()));
        List<Matricula> matriculaList= new ArrayList<>();
        try {
            ResultSet rs = conexao.consultar(sql);
            while (rs.next()) {
                Matricula matricula= new Matricula();
                matricula.setId(rs.getInt("matricula_id"));
                matricula.setAprovado(rs.getBoolean("matricula_aprovado"));
                matricula.setValido(rs.getBoolean("matricula_valido"));
                matricula.setData(rs.getDate("matricula_data").toLocalDate());

                Map<String, Object> end = new HashMap<>();
                end.put("endereco_rua", rs.getString("endereco_rua"));
                end.put("endereco_num", rs.getInt("endereco_numero"));
                end.put("endereco_complemento", rs.getString("endereco_complemento"));
                end.put("endereco_cep", rs.getString("endereco_cep"));
                end.put("endereco_id", rs.getInt("endereco_id"));
                end.put("endereco_cidade", rs.getString("endereco_cidade"));
                end.put("endereco_uf", rs.getString("endereco_uf"));
                Map<String, Object> pessoa = new HashMap<>();
                pessoa.put("pessoa_cpf", rs.getString("pessoa_cpf"));
                pessoa.put("pessoa_nome", rs.getString("pessoa_nome"));
                pessoa.put("pessoa_rg", rs.getString("pessoa_rg"));
                pessoa.put("pessoa_datanascimento", rs.getDate("pessoa_datanascimento"));
                pessoa.put("pessoa_sexo", rs.getString("pessoa_sexo"));
                pessoa.put("pessoa_locNascimento", rs.getString("pessoa_locnascimento"));
                pessoa.put("pessoa_estadoNascimento", rs.getString("pessoa_estadonascimento"));
                pessoa.put("pessoa_estadoCivil", rs.getString("pessoa_estadocivil"));
                pessoa.put("endereco", end);

                Map<String, Object> aluno = new HashMap<>();
                aluno.put("aluno_ra", rs.getInt("aluno_ra"));
                aluno.put("aluno_restricaomedica", rs.getString("aluno_restricaomedica"));
                aluno.put("pessoa", pessoa);
                alunos.add(aluno);

                Map<String, Object> ano= new HashMap<>();
                ano.put("anoletivo_id",rs.getInt("anoletivo_id"));
                ano.put("anoletivo_inicio",rs.getDate("anoletivo_inicio"));
                ano.put("anoletivo_fim",rs.getDate("anoletivo_fim"));
                anos.add(ano);

                Map<String, Object> serie= new HashMap<>();
                serie.put("serie_id",rs.getInt("serie_id"));
                serie.put("serie_num",rs.getInt("serie_num"));
                serie.put("serie_descr",rs.getString("serie_descr"));
                series.add(serie);

                Map<String, Object> turma= new HashMap<>();
                turma.put("serie",serie);
                turma.put("anoletivo",ano);
                turma.put("turma_letra",rs.getString("turma_letra")); //mudei para get str
                turmas.add(turma);

                matriculaList.add(matricula);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar matrícula", e);
        }

        return matriculaList;
    }


    public List<Matricula> get(Conexao conexao, List<Map<String, Object>> alunos, List<Map<String, Object>> anos, List<Map<String, Object>> series, List<Map<String, Object>> turmas) {
        String sql = """
                 SELECT * FROM matricula JOIN aluno ON matricula_aluno_ra = aluno_ra
                 JOIN pessoa ON aluno_pessoa_cpf = pessoa_cpf
                 JOIN endereco ON pessoa_enderecoid = endereco_id
                 JOIN anoLetivo ON anoletivo_id = matricula_anoletivo_id
                 JOIN serie ON serie_id = matricula_serie_id
                 LEFT JOIN turma ON turmaanoletivo_id = matricula_anoletivo_id AND serieturma_id = matricula_serie_id
                 AND (matricula_turma_letra IS NULL OR turma_letra = matricula_turma_letra)                
                 ORDER BY pessoa_nome;
                """;
        List<Matricula> lista = new ArrayList<>();
        try {
            ResultSet rs = conexao.consultar(sql);
            while (rs.next()) {
                // Criação e preenchimento da matrícula
                Matricula matricula = new Matricula();
                matricula.setId(rs.getInt("matricula_id"));
                matricula.setAprovado(rs.getBoolean("matricula_aprovado"));
                matricula.setValido(rs.getBoolean("matricula_valido"));
                matricula.setData(rs.getDate("matricula_data").toLocalDate());

                Map<String, Object> end = new HashMap<>();
                end.put("endereco_rua", rs.getString("endereco_rua"));
                end.put("endereco_num", rs.getInt("endereco_numero"));
                end.put("endereco_complemento", rs.getString("endereco_complemento"));
                end.put("endereco_cep", rs.getString("endereco_cep"));
                end.put("endereco_id", rs.getInt("endereco_id"));
                end.put("endereco_cidade", rs.getString("endereco_cidade"));
                end.put("endereco_uf", rs.getString("endereco_uf"));
                Map<String, Object> pessoa = new HashMap<>();
                pessoa.put("pessoa_cpf", rs.getString("pessoa_cpf"));
                pessoa.put("pessoa_nome", rs.getString("pessoa_nome"));
                pessoa.put("pessoa_rg", rs.getString("pessoa_rg"));
                pessoa.put("pessoa_datanascimento", rs.getDate("pessoa_datanascimento"));
                pessoa.put("pessoa_sexo", rs.getString("pessoa_sexo"));
                pessoa.put("pessoa_locNascimento", rs.getString("pessoa_locnascimento"));
                pessoa.put("pessoa_estadoNascimento", rs.getString("pessoa_estadonascimento"));
                pessoa.put("pessoa_estadoCivil", rs.getString("pessoa_estadocivil"));
                pessoa.put("endereco", end);

                Map<String, Object> aluno = new HashMap<>();
                aluno.put("aluno_ra", rs.getInt("aluno_ra"));
                aluno.put("aluno_restricaomedica", rs.getString("aluno_restricaomedica"));
                aluno.put("pessoa", pessoa);
                alunos.add(aluno);

                Map<String, Object> ano= new HashMap<>();
                ano.put("anoletivo_id",rs.getInt("anoletivo_id"));
                ano.put("anoletivo_inicio",rs.getDate("anoletivo_inicio"));
                ano.put("anoletivo_fim",rs.getDate("anoletivo_fim"));
                anos.add(ano);

                Map<String, Object> serie= new HashMap<>();
                serie.put("serie_id",rs.getInt("serie_id"));
                serie.put("serie_num",rs.getInt("serie_num"));
                serie.put("serie_descr",rs.getString("serie_descr"));
                series.add(serie);

                Map<String, Object> turma= new HashMap<>();
                turma.put("serie",serie);
                turma.put("anoletivo",ano);
                turma.put("turma_letra",rs.getString("turma_letra")); //mudei para string
                turmas.add(turma);



                lista.add(matricula);
            }
        } catch (Exception e) {
            e.printStackTrace();  // ADICIONE ISSO
            lista = null;
            throw new RuntimeException(e);
        }

        return lista;
    }

    public List<Matricula> buscarTodasFiltradas(
            Conexao conexao, Matricula mat, int valido, String turmaLetra,
            List<Map<String, Object>> alunos, List<Map<String, Object>> anos,
            List<Map<String, Object>> series, List<Map<String, Object>> turmas
    ) {
        boolean temCondicao = false;
        String where = "WHERE";

        if (mat.getSerie().getSerieId() != 0) {
            where += " serie_id = " + mat.getSerie().getSerieId();
            temCondicao = true;
        }
        if (mat.getAnoLetivo().getId() != 0) {
            if (temCondicao) where += " AND";
            where += " anoletivo_id = " + mat.getAnoLetivo().getId();
            temCondicao = true;
        }
        if (valido > 0) {
            if (temCondicao) where += " AND";
            where += (valido == 1) ? " matricula_valido = true" : " matricula_valido = false";
            temCondicao = true;
        }
        if (turmaLetra != null && !turmaLetra.isEmpty()) {
            if (temCondicao) where += " AND";
            where += " matricula_turma_letra = '" + turmaLetra + "'";
        }

        String sql = """
        SELECT * FROM matricula 
        JOIN aluno ON matricula_aluno_ra = aluno_ra
        JOIN pessoa ON aluno_pessoa_cpf = pessoa_cpf
        JOIN endereco ON pessoa_enderecoid = endereco_id
        JOIN anoLetivo ON anoletivo_id = matricula_anoletivo_id
        JOIN serie ON serie_id = matricula_serie_id
        LEFT JOIN turma ON turmaanoletivo_id = matricula_anoletivo_id 
                        AND serieturma_id = matricula_serie_id 
                        AND (matricula_turma_letra IS NULL OR turma_letra = matricula_turma_letra)
    """;

        if (temCondicao) sql += " " + where;
        sql += " ORDER BY pessoa_nome";

        List<Matricula> lista = new ArrayList<>();
        try {
            ResultSet rs = conexao.consultar(sql);
            while (rs.next()) {
                Matricula matricula = new Matricula();
                matricula.setId(rs.getInt("matricula_id"));
                matricula.setAprovado(rs.getBoolean("matricula_aprovado"));
                matricula.setValido(rs.getBoolean("matricula_valido"));
                matricula.setData(rs.getDate("matricula_data").toLocalDate());

                Map<String, Object> end = new HashMap<>();
                end.put("endereco_rua", rs.getString("endereco_rua"));
                end.put("endereco_num", rs.getInt("endereco_numero"));
                end.put("endereco_complemento", rs.getString("endereco_complemento"));
                end.put("endereco_cep", rs.getString("endereco_cep"));
                end.put("endereco_id", rs.getInt("endereco_id"));
                end.put("endereco_cidade", rs.getString("endereco_cidade"));
                end.put("endereco_uf", rs.getString("endereco_uf"));

                Map<String, Object> pessoa = new HashMap<>();
                pessoa.put("pessoa_cpf", rs.getString("pessoa_cpf"));
                pessoa.put("pessoa_nome", rs.getString("pessoa_nome"));
                pessoa.put("pessoa_rg", rs.getString("pessoa_rg"));
                pessoa.put("pessoa_datanascimento", rs.getDate("pessoa_datanascimento"));
                pessoa.put("pessoa_sexo", rs.getString("pessoa_sexo"));
                pessoa.put("pessoa_locNascimento", rs.getString("pessoa_locnascimento"));
                pessoa.put("pessoa_estadoNascimento", rs.getString("pessoa_estadonascimento"));
                pessoa.put("pessoa_estadoCivil", rs.getString("pessoa_estadocivil"));
                pessoa.put("endereco", end);

                Map<String, Object> aluno = new HashMap<>();
                aluno.put("aluno_ra", rs.getInt("aluno_ra"));
                aluno.put("aluno_restricaomedica", rs.getString("aluno_restricaomedica"));
                aluno.put("pessoa", pessoa);
                alunos.add(aluno);

                Map<String, Object> ano = new HashMap<>();
                ano.put("anoletivo_id", rs.getInt("anoletivo_id"));
                ano.put("anoletivo_inicio", rs.getDate("anoletivo_inicio"));
                ano.put("anoletivo_fim", rs.getDate("anoletivo_fim"));
                anos.add(ano);

                Map<String, Object> serie = new HashMap<>();
                serie.put("serie_id", rs.getInt("serie_id"));
                serie.put("serie_num", rs.getInt("serie_num"));
                serie.put("serie_descr", rs.getString("serie_descr"));
                series.add(serie);

                Map<String, Object> turma = new HashMap<>();
                turma.put("serie", serie);
                turma.put("anoletivo", ano);
                turma.put("turma_letra", rs.getString("turma_letra")); // correto
                turmas.add(turma);

                lista.add(matricula);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar matrículas filtradas", e);
        }

        return lista;
    }

    public List<Matricula> buscarMatriculasSemTurma(Conexao conexao, Matricula mat,
                                                    List<Map<String, Object>> alunos,
                                                    List<Map<String, Object>> anos,
                                                    List<Map<String, Object>> series,
                                                    List<Map<String, Object>> turmas) {

        String sql = """
        SELECT * FROM matricula 
        JOIN aluno ON matricula_aluno_ra = aluno_ra
        JOIN pessoa ON aluno_pessoa_cpf = pessoa_cpf
        JOIN endereco ON pessoa_enderecoid = endereco_id
        JOIN anoletivo ON anoletivo_id = matricula_anoletivo_id
        JOIN serie ON serie_id = matricula_serie_id
        WHERE matricula_anoletivo_id = %d 
          AND matricula_serie_id = %d 
          AND matricula_valido = true 
          AND matricula_turma_letra IS NULL
        ORDER BY pessoa_nome;
    """.formatted(mat.getAnoLetivo().getId(), mat.getSerie().getSerieId());

        List<Matricula> lista = new ArrayList<>();

        try {
            ResultSet rs = conexao.consultar(sql);
            while (rs.next()) {
                Matricula matricula = new Matricula();
                matricula.setId(rs.getInt("matricula_id"));
                matricula.setAprovado(rs.getBoolean("matricula_aprovado"));
                matricula.setValido(rs.getBoolean("matricula_valido"));
                matricula.setData(rs.getDate("matricula_data").toLocalDate());

                Map<String, Object> end = new HashMap<>();
                end.put("endereco_rua", rs.getString("endereco_rua"));
                end.put("endereco_num", rs.getInt("endereco_numero"));
                end.put("endereco_complemento", rs.getString("endereco_complemento"));
                end.put("endereco_cep", rs.getString("endereco_cep"));
                end.put("endereco_id", rs.getInt("endereco_id"));
                end.put("endereco_cidade", rs.getString("endereco_cidade"));
                end.put("endereco_uf", rs.getString("endereco_uf"));

                Map<String, Object> pessoa = new HashMap<>();
                pessoa.put("pessoa_cpf", rs.getString("pessoa_cpf"));
                pessoa.put("pessoa_nome", rs.getString("pessoa_nome"));
                pessoa.put("pessoa_rg", rs.getString("pessoa_rg"));
                pessoa.put("pessoa_datanascimento", rs.getDate("pessoa_datanascimento"));
                pessoa.put("pessoa_sexo", rs.getString("pessoa_sexo"));
                pessoa.put("pessoa_locNascimento", rs.getString("pessoa_locnascimento"));
                pessoa.put("pessoa_estadoNascimento", rs.getString("pessoa_estadonascimento"));
                pessoa.put("pessoa_estadoCivil", rs.getString("pessoa_estadocivil"));
                pessoa.put("endereco", end);

                Map<String, Object> aluno = new HashMap<>();
                aluno.put("aluno_ra", rs.getInt("aluno_ra"));
                aluno.put("aluno_restricaomedica", rs.getString("aluno_restricaomedica"));
                aluno.put("pessoa", pessoa);
                alunos.add(aluno);

                Map<String, Object> ano = new HashMap<>();
                ano.put("anoletivo_id", rs.getInt("anoletivo_id"));
                ano.put("anoletivo_inicio", rs.getDate("anoletivo_inicio"));
                ano.put("anoletivo_fim", rs.getDate("anoletivo_fim"));
                anos.add(ano);

                Map<String, Object> serie = new HashMap<>();
                serie.put("serie_id", rs.getInt("serie_id"));
                serie.put("serie_num", rs.getInt("serie_num"));
                serie.put("serie_descr", rs.getString("serie_descr"));
                series.add(serie);

                turmas.add(null); // forçar sem turma

                lista.add(matricula);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar matrículas sem turma", e);
        }

        return lista;
    }

    public boolean removerTurmaDaMatricula(Matricula m, Conexao conexao) {
        String sql = """
        UPDATE matricula
        SET matricula_turma_letra = NULL
        WHERE matricula_id = %d
    """.formatted(m.getId());
        return conexao.manipular(sql);
    }
}