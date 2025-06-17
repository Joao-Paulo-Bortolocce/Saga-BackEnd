package sistema.saga.sagabackend.control;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sistema.saga.sagabackend.model.AvaliacaoDaMatricula;
import sistema.saga.sagabackend.model.Ficha;
import sistema.saga.sagabackend.model.FichaDaMatricula;
import sistema.saga.sagabackend.model.Matricula;
import sistema.saga.sagabackend.repository.GerenciaConexao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FichaDaMatriculaCtrl {

    public ResponseEntity<Object> gravar(Map<String, Object> dados) {
        HashMap<String, Object> resposta = new HashMap<>();
        GerenciaConexao gerenciaConexao = new GerenciaConexao();
        try {
            HashMap<String, Object> mat = (HashMap<String, Object>) dados.get("matricula");
            HashMap<String, Object> ficha = (HashMap<String, Object>) dados.get("ficha");
            String observacao = (String) dados.get("observacao");
            int status = Integer.parseInt(""+ dados.get("status"));
            int ra = Integer.parseInt(""+ mat.get("id"));
            int id = Integer.parseInt(""+ ficha.get("ficha_id"));

            if (Regras.verificaIntegridade(ra) &&
                    Regras.verificaIntegridade(id) &&
                    Regras.verificaIntegridade(status) &&
                    Regras.verificaIntegridade(observacao)) {

                FichaDaMatricula fichaDaMatricula = new FichaDaMatricula(
                        new Matricula(ra),
                        new Ficha(id),
                        observacao,
                        status
                );

                if (fichaDaMatricula.gravar(gerenciaConexao.getConexao())) {
                    resposta.put("status", true);
                    resposta.put("mensagem", "Ficha da matrícula gravada com sucesso!");
                    gerenciaConexao.Desconectar();
                    return ResponseEntity.ok(resposta);
                } else {
                    resposta.put("status", false);
                    resposta.put("mensagem", "Erro ao gravar a ficha da matrícula");
                    gerenciaConexao.Desconectar();
                    return ResponseEntity.badRequest().body(resposta);
                }

            } else {
                resposta.put("status", false);
                resposta.put("mensagem", "Dados inválidos");
                gerenciaConexao.Desconectar();
                return ResponseEntity.badRequest().body(resposta);
            }

        } catch (Exception e) {
            resposta.put("status", false);
            resposta.put("mensagem", "Erro ao processar os dados: " + e.getMessage());
            gerenciaConexao.Desconectar();
            return ResponseEntity.badRequest().body(resposta);
        }
    }


    public ResponseEntity<Object> buscarTodas(int valid) {
        Map<String, Object> resposta = new HashMap<>();
        GerenciaConexao gerenciaConexao = new GerenciaConexao();
        try {
            FichaDaMatricula ficha = new FichaDaMatricula();
            List<FichaDaMatricula> fichas = ficha.buscarTodas(valid, gerenciaConexao.getConexao());
            if (fichas != null && !fichas.isEmpty()) {
                for (FichaDaMatricula f : fichas) {
                    Map<String, Object> aluno = new HashMap<>();
                    Map<String, Object> ano = new HashMap<>();
                    Map<String, Object> serie = new HashMap<>();
                    Map<String, Object> turma = new HashMap<>();
                    Matricula matricula = new Matricula(f.getMatricula().getId());
                    matricula = matricula.buscaMatricula(gerenciaConexao.getConexao(), matricula, aluno, ano, serie, turma);
                    matricula.setTurma(Regras.HashToTurma(turma));
                    matricula.setSerie(Regras.HashToSerie(serie));
                    matricula.setAluno(Regras.HashToAluno(aluno));
                    matricula.setAnoLetivo(Regras.HashToAnoLetivo(ano));
                    f.setMatricula(matricula);
                    f.setFicha(f.getFicha().buscaFicha(gerenciaConexao.getConexao()));
                }
                resposta.put("status", true);
                resposta.put("listaDeFichaDaMatriculas", fichas);
                gerenciaConexao.Desconectar();
                return ResponseEntity.ok(resposta);
            } else {
                resposta.put("status", false);
                resposta.put("mensagem", "Não existem fichas cadastradas");
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

    public ResponseEntity<Object> buscarTodasAvaliacoesDafichaDaMatricula(int matId, int fichaId) {
        Map<String, Object> resposta = new HashMap<>();
        GerenciaConexao gerenciaConexao = new GerenciaConexao();
        try {
            AvaliacaoDaMatricula avaliacaoDaMatricula = new AvaliacaoDaMatricula();
            List<AvaliacaoDaMatricula> avaliacaoDaMatriculas = avaliacaoDaMatricula.buscarTodasAvaliacoesDafichaDaMatricula(
                    gerenciaConexao.getConexao(), matId, fichaId);

            if (avaliacaoDaMatriculas != null && !avaliacaoDaMatriculas.isEmpty()) {
                resposta.put("status", true);
                resposta.put("listaDeAvaliacoes", avaliacaoDaMatriculas);
                gerenciaConexao.Desconectar();
                return ResponseEntity.ok(resposta);
            } else {
                resposta.put("status", false);
                resposta.put("mensagem", "Não existem avaliações cadastradas");
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

    public ResponseEntity<Object> alterar(Map<String, Object> dados) {
        HashMap<String, Object> resposta = new HashMap<>();
        GerenciaConexao gerenciaConexao = new GerenciaConexao();

        try {
            HashMap<String, Object> mat = (HashMap<String, Object>) dados.get("matricula");
            HashMap<String, Object> ficha = (HashMap<String, Object>) dados.get("ficha");
            String observacao = (String) dados.get("observacao");
            int status = Integer.parseInt(""+ dados.get("status"));
            int ra = Integer.parseInt(""+ mat.get("id"));
            int id = Integer.parseInt(""+ ficha.get("ficha_id"));

            if (Regras.verificaIntegridade(ra) &&
                    Regras.verificaIntegridade(id) &&
                    Regras.verificaIntegridade(status) &&
                    Regras.verificaIntegridade(observacao)) {

                FichaDaMatricula fichaDaMatricula = new FichaDaMatricula(
                        new Matricula(ra),
                        new Ficha(id),
                        observacao,
                        status
                );

                if (fichaDaMatricula.alterar(gerenciaConexao.getConexao())) {
                    resposta.put("status", true);
                    resposta.put("mensagem", "Ficha da matrícula alterada com sucesso!");
                    gerenciaConexao.Desconectar();
                    return ResponseEntity.ok(resposta);
                } else {
                    resposta.put("status", false);
                    resposta.put("mensagem", "Erro ao alterar a ficha da matrícula");
                    gerenciaConexao.Desconectar();
                    return ResponseEntity.badRequest().body(resposta);
                }

            } else {
                resposta.put("status", false);
                resposta.put("mensagem", "Dados inválidos");
                gerenciaConexao.Desconectar();
                return ResponseEntity.badRequest().body(resposta);
            }

        } catch (Exception e) {
            resposta.put("status", false);
            resposta.put("mensagem", "Erro ao processar os dados: " + e.getMessage());
            gerenciaConexao.Desconectar();
            return ResponseEntity.badRequest().body(resposta);
        }
    }
}
