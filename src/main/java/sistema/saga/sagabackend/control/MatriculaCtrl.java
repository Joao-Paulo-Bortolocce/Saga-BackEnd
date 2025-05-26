package sistema.saga.sagabackend.control;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import sistema.saga.sagabackend.model.*;
import sistema.saga.sagabackend.repository.GerenciaConexao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class MatriculaCtrl {
    private final Matricula matricula;
    private final ContentNegotiatingViewResolver contentNegotiatingViewResolver;

    public MatriculaCtrl(Matricula matricula, ContentNegotiatingViewResolver contentNegotiatingViewResolver) {
        this.matricula = matricula;
        this.contentNegotiatingViewResolver = contentNegotiatingViewResolver;
    }

    public ResponseEntity<Object> gravarMatricula(Map<String, Object> dados) {
        Map<String, Object> resposta = new HashMap<>();
        int ra = (int) dados.get("ra");
        int anoletivoId = Integer.parseInt((String) dados.get("anoLetivo_id"));
        int serieId = Integer.parseInt((String) dados.get("serie_id"));
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
                    Aluno aluno = new Aluno(ra);
                    Serie serie = new Serie();
                    serie.setSerieId(serieId);
                    AnoLetivo anoLetivo = new AnoLetivo(anoletivoId);
                    Map<String, Object> pessoa = new HashMap<>();
                    aluno = aluno.buscaAluno(gerenciaConexao.getConexao(), aluno, pessoa);
                    if (aluno == null) {
                        resposta.put("status", false);
                        resposta.put("mensagem", "O aluno que deseja matricular não está cadastrado!");
                        //roolback; end trasaction;
                        gerenciaConexao.getConexao().rollback();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }

                    if (serie.buscaSerie(gerenciaConexao.getConexao()) == 0) {
                        resposta.put("status", false);
                        resposta.put("mensagem", "A serie que deseja matricular não está cadastrada!");
                        //roolback; end trasaction;
                        gerenciaConexao.getConexao().rollback();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }


                    if (anoLetivo.buscaAnos(gerenciaConexao.getConexao()) == 0) {
                        resposta.put("status", false);
                        resposta.put("mensagem", "O ano Letivo que deseja matricular não está cadastrado!");
                        //roolback; end trasaction;
                        gerenciaConexao.getConexao().rollback();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }

                    Matricula matricula = new Matricula(aluno, anoLetivo, serie, null, false, data, true);
                    List<Map<String, Object>> alunoMap = new ArrayList<>();
                    List<Map<String, Object>> anoMap = new ArrayList<>();
                    List<Map<String, Object>> serieMap = new ArrayList<>();
                    List<Map<String, Object>> turmaMap = new ArrayList<>();
                    List<Matricula> matriculaList = matricula.buscaMatricula(gerenciaConexao.getConexao(), alunoMap, anoMap, serieMap, turmaMap);
                    if (matriculaList.size() > 0) {
                        for (int i = 0; i < matriculaList.size(); i++) {
                            Matricula aux = matriculaList.get(i);
                            aux.setAluno(Regras.HashToAluno(alunoMap.get(i)));
                            aux.setAnoLetivo(Regras.HashToAnoLetivo(anoMap.get(i)));
                            aux.setSerie(Regras.HashToSerie(serieMap.get(i)));
                            aux.setTurma(Regras.HashToTurma(turmaMap.get(i)));
                        }
                        int anoAtual = matricula.getAnoLetivo().getInicio().getYear();
                        int serieAtual = matricula.getSerie().getSerieNum();
                        boolean repetiu = false, correto = true;
                        int anoAux, serieAux, anoRepetiu = 0, serieRepetiu = 0;
                        int maior = -1000, menor = 1000000, anoMaior = 0, anoMenor = 0;
                        String serieErro = "";
                        for (int i = 0; i < matriculaList.size() && correto; i++) {
                            anoAux = matriculaList.get(i).getAnoLetivo().getInicio().getYear();
                            serieAux = matriculaList.get(i).getSerie().getSerieNum();
                            if (matriculaList.get(i).getSerie().getSerieNum() > maior) {
                                maior = matriculaList.get(i).getSerie().getSerieNum();
                                anoMaior=anoAux;
                            }
                            if (matriculaList.get(i).getSerie().getSerieNum() < menor) {
                                menor = matriculaList.get(i).getSerie().getSerieNum();
                                anoMenor=anoAux;
                            }
                            if (serieAtual == serieAux) {
                                anoRepetiu = anoAux;
                                serieRepetiu = serieAux;
                                repetiu = true;
                                correto = false;
                            } else {
                                if (anoAtual > anoAux && serieAtual < serieAux) {
                                    anoRepetiu = anoAux;
                                    serieErro = matriculaList.get(i).getSerie().getSerieDescr();
                                    correto = false;
                                } else if (anoAtual < anoAux && serieAtual > serieAux) {
                                    anoRepetiu = anoAux;
                                    serieErro = matriculaList.get(i).getSerie().getSerieDescr();
                                    correto = false;
                                } else {
                                    if(anoAtual==anoAux){
                                        anoRepetiu = anoAux;
                                        correto = false;
                                    }
                                }
                            }
                        }

                        if (repetiu) {
                            if (serieRepetiu == 3 || serieRepetiu == 5) {
                                if (anoAtual != anoRepetiu - 1 && anoAtual != anoRepetiu + 1) {
                                    resposta.put("status", false);
                                    resposta.put("mensagem", "Um aluno só pode repetir anos consecutivos!");
                                    //roolback; end trasaction;
                                    gerenciaConexao.getConexao().rollback();
                                    gerenciaConexao.getConexao().fimTransacao();
                                    gerenciaConexao.Desconectar();
                                    return ResponseEntity.badRequest().body(resposta);
                                }
                                repetiu = false;
                                for (int i = 0; i < matriculaList.size() - 1; i++) {
                                    anoAux = matriculaList.get(i).getAnoLetivo().getInicio().getYear();
                                    serieAux = matriculaList.get(i).getSerie().getSerieNum();
                                    for (int j = i + 1; j < matriculaList.size() && !repetiu; j++) {
                                        if (matriculaList.get(j).getAnoLetivo().getInicio().getYear() == anoAux && matriculaList.get(j).getSerie().getSerieNum() == serieAux)
                                            repetiu = true;
                                    }
                                }
                                if (repetiu) {
                                    resposta.put("status", false);
                                    resposta.put("mensagem", "Este aluno ja reprovou anteriormente e não pode reprovar novamente!");
                                    //roolback; end trasaction;
                                    gerenciaConexao.getConexao().rollback();
                                    gerenciaConexao.getConexao().fimTransacao();
                                    gerenciaConexao.Desconectar();
                                    return ResponseEntity.badRequest().body(resposta);
                                }
                                correto = true;
                            } else {
                                resposta.put("status", false);
                                resposta.put("mensagem", "Um aluno só pode repetir o 3 ou o 5 ano!");
                                //roolback; end trasaction;
                                gerenciaConexao.getConexao().rollback();
                                gerenciaConexao.getConexao().fimTransacao();
                                gerenciaConexao.Desconectar();
                                return ResponseEntity.badRequest().body(resposta);
                            }
                        }

                        if (!correto) {
                            String mensagem = "";
                            if (anoAtual == anoRepetiu)
                                mensagem = "Este aluno ja possui matricula no ano " + anoAtual;
                            else if (anoAtual > anoRepetiu)
                                mensagem = "A serie que o aluno realizou em " + anoRepetiu + " é superior à " + matricula.getSerie().getSerieDescr() + ", informe uma serie superior a " + serieErro + "!";
                            else
                                mensagem = "A serie que o aluno realizou em " + anoRepetiu + " é inferior à " + matricula.getSerie().getSerieDescr() + ", informe uma serie inferior a " + serieErro + "!";
                            resposta.put("status", false);
                            resposta.put("mensagem", mensagem);
                            //roolback; end trasaction;
                            gerenciaConexao.getConexao().rollback();
                            gerenciaConexao.getConexao().fimTransacao();
                            gerenciaConexao.Desconectar();
                            return ResponseEntity.badRequest().body(resposta);
                        }
                        int dist=anoAtual-anoMaior;
                       if(serieAtual-maior>dist){
                           resposta.put("status", false);
                           resposta.put("mensagem", "O aluno esta pulando alguma(s) serie(s)");
                           //roolback; end trasaction;
                           gerenciaConexao.getConexao().rollback();
                           gerenciaConexao.getConexao().fimTransacao();
                           gerenciaConexao.Desconectar();
                           return ResponseEntity.badRequest().body(resposta);
                       }
                        if( serieAtual-maior<dist){
                            resposta.put("status", false);
                            resposta.put("mensagem", "O aluno esta deixando alguma(s) serie(s) para trás");
                            //roolback; end trasaction;
                            gerenciaConexao.getConexao().rollback();
                            gerenciaConexao.getConexao().fimTransacao();
                            gerenciaConexao.Desconectar();
                            return ResponseEntity.badRequest().body(resposta);
                        }
                        dist=anoAtual-anoMenor;
                        if(serieAtual-menor>dist){
                            resposta.put("status", false);
                            resposta.put("mensagem", "O aluno esta pulando alguma(s) serie(s)");
                            //roolback; end trasaction;
                            gerenciaConexao.getConexao().rollback();
                            gerenciaConexao.getConexao().fimTransacao();
                            gerenciaConexao.Desconectar();
                            return ResponseEntity.badRequest().body(resposta);
                        }

                        if( serieAtual-menor<dist){
                            resposta.put("status", false);
                            resposta.put("mensagem", "O aluno esta deixando alguma(s) serie(s) para trás");
                            //roolback; end trasaction;
                            gerenciaConexao.getConexao().rollback();
                            gerenciaConexao.getConexao().fimTransacao();
                            gerenciaConexao.Desconectar();
                            return ResponseEntity.badRequest().body(resposta);
                        }

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
        Matricula matricula = Regras.HashToMatriculaFront(dados);
        if (matricula != null) {
            GerenciaConexao gerenciaConexao;
            try {
                gerenciaConexao = new GerenciaConexao();
                try {
                    gerenciaConexao.getConexao().iniciarTransacao();
                    //begin transaction
                    if (matricula.getAluno().buscaAluno(gerenciaConexao.getConexao(), matricula.getAluno(), new HashMap<String, Object>()) == null) {
                        resposta.put("status", false);
                        resposta.put("mensagem", "O aluno que deseja alterar a matricula não está cadastrado!");
                        //roolback; end trasaction;
                        gerenciaConexao.getConexao().rollback();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }

                    Serie serie = new Serie(matricula.getSerie().getSerieId(), matricula.getSerie().getSerieNum(), matricula.getSerie().getSerieDescr());
                    if (serie.buscaSerie(gerenciaConexao.getConexao()) == 0) {
                        resposta.put("status", false);
                        resposta.put("mensagem", "A serie que deseja matricular não está cadastrada!");
                        //roolback; end trasaction;
                        gerenciaConexao.getConexao().rollback();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }


                    if (matricula.getAnoLetivo().buscaAnos(gerenciaConexao.getConexao()) == 0) {
                        resposta.put("status", false);
                        resposta.put("mensagem", "O ano Letivo que deseja alterar a  matricula não está cadastrado!");
                        //roolback; end trasaction;
                        gerenciaConexao.getConexao().rollback();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }

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

    public ResponseEntity<Object> apagarMatricula(int ra) {
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
            List<Matricula> matriculaList = matricula.buscarTodas(gerenciaConexao.getConexao(), alunos, anos, series, turmas);
            if (matriculaList != null) {
                for (int i = 0; i < alunos.size(); i++) {
                    Matricula mat = matriculaList.get(i);
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

            List<Map<String, Object>> aluno = new ArrayList<>();
            List<Map<String, Object>> ano = new ArrayList<>();
            List<Map<String, Object>> serie = new ArrayList<>();
            List<Map<String, Object>> turma = new ArrayList<>();
            List<Matricula> matriculaList = matricula.buscaMatricula(gerenciaConexao.getConexao(), aluno, ano, serie, turma);


            if (matriculaList.isEmpty()) {
                for (int i = 0; i < matriculaList.size(); i++) {
                    matricula = matriculaList.get(i);
                    matricula.setAluno(Regras.HashToAluno(aluno.get(i)));
                    matricula.setAnoLetivo(Regras.HashToAnoLetivo(ano.get(i)));
                    matricula.setSerie(Regras.HashToSerie(serie.get(i)));
                    matricula.setTurma(Regras.HashToTurma(turma.get(i)));
                }
                resposta.put("status", true);
                resposta.put("matriculas", matriculaList);
                gerenciaConexao.Desconectar();
                return ResponseEntity.ok(resposta);
            } else {
                resposta.put("status", false);
                resposta.put("mensagem", "Não existe matricula cadastrado com o ID: " + id);
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


    public ResponseEntity<Object> buscarTodasFiltradas(int serieId, int anoLetivoId, int valido, String turmaLetra) {
        Map<String, Object> resposta = new HashMap<>();
        GerenciaConexao gerenciaConexao = new GerenciaConexao();
        try {
            Matricula matricula = new Matricula();
            matricula.setSerie(new Serie(serieId, 0, ""));
            matricula.setAnoLetivo(new AnoLetivo(anoLetivoId));
            if(turmaLetra!=null)
                matricula.setTurma(new Turma(null,null,turmaLetra.charAt(0),null,null));
            List<Map<String, Object>> alunos = new ArrayList<>();
            List<Map<String, Object>> anos = new ArrayList<>();
            List<Map<String, Object>> series = new ArrayList<>();
            List<Map<String, Object>> turmas = new ArrayList<>();
            List<Matricula> matriculaList = matricula.buscarTodasFiltradas(gerenciaConexao.getConexao(), matricula, valido, alunos, anos, series, turmas);
            if (matriculaList != null) {
                for (int i = 0; i < alunos.size(); i++) {
                    Matricula mat = matriculaList.get(i);
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

