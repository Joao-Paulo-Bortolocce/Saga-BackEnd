package sistema.saga.sagabackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sistema.saga.sagabackend.model.*;
import sistema.saga.sagabackend.repository.GerenciaConexao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MatriculaCtrl {
    private final Matricula matricula;

    public MatriculaCtrl(Matricula matricula) {
        this.matricula = matricula;
    }

    public ResponseEntity<Object> gravarMatricula(Map<String, Object> dados) {
        Map<String, Object> resposta = new HashMap<>();
        int ra =(int) dados.get("ra");
        int  anoletivoId = Integer.parseInt((String) dados.get("anoLetivo_id"));
        int  serieId = Integer.parseInt((String) dados.get("serie_id"));
        String dataStr = (String) dados.get("data");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate data = LocalDate.parse(dataStr, formatter);
        if (Regras.verificaIntegridade(ra) &&
                Regras.verificaIntegridade(anoletivoId) &&
                Regras.verificaIntegridade(serieId) &&
                Regras.verificaIntegridade(data)) {
            GerenciaConexao gerenciaConexao;
            try {
                gerenciaConexao = new GerenciaConexao();
                try {
                    gerenciaConexao.getConexao().iniciarTransacao();
                    //begin transaction
                    Aluno aluno= new Aluno(ra);
                    Serie serie = new Serie();
                    serie.setSerieId(serieId);
                    AnoLetivo anoLetivo = new AnoLetivo(anoletivoId);
                    Map<String,Object> pessoa= new HashMap<>();
                    aluno=aluno.buscaAluno(gerenciaConexao.getConexao(),aluno,pessoa);
                    if (aluno==null) {
                        resposta.put("status", false);
                        resposta.put("mensagem", "O aluno que deseja matricular não está cadastrado!");
                        //roolback; end trasaction;
                        gerenciaConexao.getConexao().rollback();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }

                    if (serie.buscaSerie(gerenciaConexao.getConexao())==0) {
                        resposta.put("status", false);
                        resposta.put("mensagem", "A serie que deseja matricular não está cadastrada!");
                        //roolback; end trasaction;
                        gerenciaConexao.getConexao().rollback();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }

                    anoLetivo=anoLetivo.buscaAnoLetivo(gerenciaConexao.getConexao());
                    if (anoLetivo==null) {
                        resposta.put("status", false);
                        resposta.put("mensagem", "O ano Letivo que deseja matricular não está cadastrado!");
                        //roolback; end trasaction;
                        gerenciaConexao.getConexao().rollback();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }

                   Matricula matricula= new Matricula(aluno,anoLetivo,serie,null,false,data);
                    Map<String, Object> alunoMap= new HashMap<>();
                    Map<String, Object> anoMap= new HashMap<>();
                    Map<String, Object> serieMap= new HashMap<>();
                    Map<String, Object> turmaMap= new HashMap<>();

                    if (matricula.buscaMatricula(gerenciaConexao.getConexao(),alunoMap,anoMap,serieMap,turmaMap)!=null) {
                        resposta.put("status", false);
                        resposta.put("mensagem", "Esta matricula ja está cadastrada!");
                        //roolback; end trasaction;
                        gerenciaConexao.getConexao().rollback();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }

                    aluno.setPessoa(Regras.HashToPessoa(pessoa));
                    if (matricula.gravar(gerenciaConexao.getConexao())) {
                        resposta.put("status", true);
                        resposta.put("mensagem", "Matricula Inserida com sucesso");
                        resposta.put("matricula", matricula);
                        //commit; end transaction;
                        gerenciaConexao.getConexao().commit();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.ok(resposta);
                    } else {
                        resposta.put("status", false);
                        resposta.put("mensagem", "Matricula não foi inserida!");
                        //rollback end transaction;
                        gerenciaConexao.getConexao().rollback();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }
                } catch (Exception e) {
                    resposta.put("status", false);
                    resposta.put("mensagem", "Ocorreu um erro na durante a insercao");
                    gerenciaConexao.getConexao().rollback();
                    gerenciaConexao.getConexao().fimTransacao();
                    gerenciaConexao.Desconectar();
                    return ResponseEntity.badRequest().body(resposta);
                }
            } catch (Exception e) {
                resposta.put("status", false);
                resposta.put("mensagem", "Ocorreu um erro ao iniciar conexao");
                return ResponseEntity.badRequest().body(resposta);
            }
        } else {
            resposta.put("status", false);
            resposta.put("mensagem", "Dados inválidos");
            return ResponseEntity.badRequest().body(resposta);
        }

    }

    public ResponseEntity<Object> alterarMatricula(Map<String, Object> dados) {
        Map<String, Object> resposta = new HashMap<>();
        int ra =(int) dados.get("ra");
        int  anoletivoId = Integer.parseInt((String) dados.get("anoLetivo_id"));
        int  serieId = Integer.parseInt((String) dados.get("serie_id"));
        String dataStr = (String) dados.get("data");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate data = LocalDate.parse(dataStr, formatter);
        if (Regras.verificaIntegridade(ra) &&
                Regras.verificaIntegridade(anoletivoId) &&
                Regras.verificaIntegridade(serieId) &&
                Regras.verificaIntegridade(data)) {
            GerenciaConexao gerenciaConexao;
            try {
                gerenciaConexao = new GerenciaConexao();
                try {
                    gerenciaConexao.getConexao().iniciarTransacao();
                    //begin transaction
                    Aluno aluno= new Aluno(ra);
                    Serie serie = new Serie();
                    serie.setSerieId(serieId);
                    AnoLetivo anoLetivo = new AnoLetivo(anoletivoId);
                    Map<String,Object> pessoa= new HashMap<>();
                    aluno=aluno.buscaAluno(gerenciaConexao.getConexao(),aluno,pessoa);
                    if (aluno==null) {
                        resposta.put("status", false);
                        resposta.put("mensagem", "O aluno que deseja matricular não está cadastrado!");
                        //roolback; end trasaction;
                        gerenciaConexao.getConexao().rollback();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }

                    /*serie=serie.buscaSerie(gerenciaConexao.getConexao(),serie);
                    if (serie==null) {
                        resposta.put("status", false);
                        resposta.put("mensagem", "A serie que deseja matricular não está cadastrada!");
                        //roolback; end trasaction;
                        gerenciaConexao.getConexao().rollback();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }*/

                    /*anoLetivo=anoLetivo.buscaAnoLetivo(gerenciaConexao.getConexao(),anoLetivo);
                    if (serie==null) {
                        resposta.put("status", false);
                        resposta.put("mensagem", "O ano Letivo que deseja matricular não está cadastrado!");
                        //roolback; end trasaction;
                        gerenciaConexao.getConexao().rollback();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }*/

                    aluno.setPessoa(Regras.HashToPessoa(pessoa));
                    Matricula matricula= new Matricula(aluno,anoLetivo,serie,null,false,data);
                    if (matricula.alterar(gerenciaConexao.getConexao())) {
                        resposta.put("status", true);
                        resposta.put("mensagem", "Matricula alterada com sucesso");
                        //commit; end transaction;
                        gerenciaConexao.getConexao().commit();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.ok(resposta);
                    } else {
                        resposta.put("status", false);
                        resposta.put("mensagem", "Matricula não foi alterada!");
                        //rollback end transaction;
                        gerenciaConexao.getConexao().rollback();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }
                } catch (Exception e) {
                    resposta.put("status", false);
                    resposta.put("mensagem", "Ocorreu um erro na durante a alteracao");
                    gerenciaConexao.getConexao().rollback();
                    gerenciaConexao.getConexao().fimTransacao();
                    gerenciaConexao.Desconectar();
                    return ResponseEntity.badRequest().body(resposta);
                }
            } catch (Exception e) {
                resposta.put("status", false);
                resposta.put("mensagem", "Ocorreu um erro ao iniciar conexao");
                return ResponseEntity.badRequest().body(resposta);
            }
        } else {
            resposta.put("status", false);
            resposta.put("mensagem", "Dados inválidos");
            return ResponseEntity.badRequest().body(resposta);
        }

    }
    public ResponseEntity<Object> apagarMatricula(int  ra) {
        Map<String, Object> resposta = new HashMap<>();

        if (Regras.verificaIntegridade(ra)) {
            try {
                Matricula matricula = new Matricula(ra);
                GerenciaConexao gerenciaConexao = new GerenciaConexao();
                if (matricula.apagar(gerenciaConexao.getConexao())) {
                    resposta.put("status", true);
                    resposta.put("mensagem", "Matricula excluído com sucesso!");
                    gerenciaConexao.Desconectar();
                    return ResponseEntity.ok(resposta);
                } else {
                    resposta.put("status", false);
                    resposta.put("mensagem", "Exclusão não foi realizada!");
                    gerenciaConexao.Desconectar();
                    return ResponseEntity.badRequest().body(resposta);
                }
            } catch (Exception e) {
                resposta.put("status", false);
                resposta.put("mensagem", "Ocorreu um erro de conexão");
                return ResponseEntity.badRequest().body(resposta);
            }
        } else {
            resposta.put("status", false);
            resposta.put("mensagem", "Dados inválidos para exclusão!");
            return ResponseEntity.badRequest().body(resposta);
        }

    }


    public ResponseEntity<Object> buscarTodas() {
        Map<String, Object> resposta = new HashMap<>();
        GerenciaConexao gerenciaConexao = new GerenciaConexao();
        try {
            Matricula matricula = new Matricula();
            List<Map<String, Object>> alunos = new ArrayList<>();
            List<Map<String, Object>> anos = new ArrayList<>();
            List<Map<String, Object>> series = new ArrayList<>();
            List<Map<String, Object>> turmas = new ArrayList<>();
            List<Matricula> matriculaList = matricula.buscarTodas(gerenciaConexao.getConexao(), alunos,anos,series,turmas);
            if (matriculaList != null) {
                for (int i = 0; i < alunos.size(); i++) {
                    Matricula mat= matriculaList.get(i);
                    mat.setAluno(Regras.HashToAluno(alunos.get(i)));
                    mat.setAnoLetivo(Regras.HashToAnoLetivo(anos.get(i)));
                    mat.setSerie(Regras.HashToSerie(series.get(i)));
                    mat.setTurma(Regras.HashToTurma(turmas.get(i)));
                }
                resposta.put("status", true);
                resposta.put("listaDeMatriculas", matriculaList);
                gerenciaConexao.Desconectar();
                return ResponseEntity.ok(resposta);
            } else {
                resposta.put("status", false);
                resposta.put("mensagem", "Não existem matriculas cadastradas");
                gerenciaConexao.Desconectar();
                return ResponseEntity.badRequest().body(resposta);
            }
        } catch (Exception e) {
            resposta.put("status", false);
            resposta.put("mensagem", "Ocorreu um erro de conexão");
            gerenciaConexao.Desconectar();
            return ResponseEntity.badRequest().body(resposta);
        }
    }

    public ResponseEntity<Object> buscarMatricula(int id) {
        Map<String, Object> resposta = new HashMap<>();
        GerenciaConexao gerenciaConexao = new GerenciaConexao();
        try {
            Matricula matricula = new Matricula(id);
            Map<String, Object> aluno= new HashMap<>();
            Map<String, Object> ano= new HashMap<>();
            Map<String, Object> serie= new HashMap<>();
            Map<String, Object> turma= new HashMap<>();
            matricula = matricula.buscaMatricula(gerenciaConexao.getConexao(),aluno,ano,serie,turma);
            if (matricula != null) {
                matricula.setAluno(Regras.HashToAluno(aluno));
                matricula.setAnoLetivo(Regras.HashToAnoLetivo(ano));
                matricula.setSerie(Regras.HashToSerie(serie));
                matricula.setTurma(Regras.HashToTurma(turma));
                resposta.put("status", true);
                resposta.put("matricula", matricula);
                gerenciaConexao.Desconectar();
                return ResponseEntity.ok(resposta);
            } else {
                resposta.put("status", false);
                resposta.put("mensagem", "Não existe matricula cadastrado com o ID: "+id);
                gerenciaConexao.Desconectar();
                return ResponseEntity.badRequest().body(resposta);
            }
        } catch (Exception e) {
            resposta.put("status", false);
            resposta.put("mensagem", "Ocorreu um erro de conexão");
            gerenciaConexao.Desconectar();
            return ResponseEntity.badRequest().body(resposta);
        }
    }


    public ResponseEntity<Object> buscarTodasFiltradas(int serieId, int anoLetivoId) {
        Map<String, Object> resposta = new HashMap<>();
        GerenciaConexao gerenciaConexao = new GerenciaConexao();
        try {
            Matricula matricula = new Matricula();
            matricula.setSerie(new Serie(serieId,0,""));
            matricula.setAnoLetivo(new AnoLetivo(anoLetivoId));
            List<Map<String, Object>> alunos = new ArrayList<>();
            List<Map<String, Object>> anos = new ArrayList<>();
            List<Map<String, Object>> series = new ArrayList<>();
            List<Map<String, Object>> turmas = new ArrayList<>();
            List<Matricula> matriculaList = matricula.buscarTodasFiltradas(gerenciaConexao.getConexao(),matricula, alunos,anos,series,turmas);
            if (matriculaList != null) {
                for (int i = 0; i < alunos.size(); i++) {
                    Matricula mat= matriculaList.get(i);
                    mat.setAluno(Regras.HashToAluno(alunos.get(i)));
                    mat.setAnoLetivo(Regras.HashToAnoLetivo(anos.get(i)));
                    mat.setSerie(Regras.HashToSerie(series.get(i)));
                    mat.setTurma(Regras.HashToTurma(turmas.get(i)));
                }
                resposta.put("status", true);
                resposta.put("listaDeMatriculas", matriculaList);
                gerenciaConexao.Desconectar();
                return ResponseEntity.ok(resposta);
            } else {
                resposta.put("status", false);
                resposta.put("mensagem", "Não existem matriculas cadastradas");
                gerenciaConexao.Desconectar();
                return ResponseEntity.badRequest().body(resposta);
            }
        } catch (Exception e) {
            resposta.put("status", false);
            resposta.put("mensagem", "Ocorreu um erro de conexão");
            gerenciaConexao.Desconectar();
            return ResponseEntity.badRequest().body(resposta);
        }
    }
}

