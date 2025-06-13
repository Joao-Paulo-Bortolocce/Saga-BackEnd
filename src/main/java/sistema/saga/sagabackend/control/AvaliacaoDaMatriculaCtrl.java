package sistema.saga.sagabackend.control;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sistema.saga.sagabackend.model.AvaliacaoDaMatricula;
import sistema.saga.sagabackend.repository.GerenciaConexao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AvaliacaoDaMatriculaCtrl {

    public ResponseEntity<Object> gravarAvaliacaoDamatricula(Map<String, Object> dados) {
        Map<String, Object> resposta = new HashMap<>();
        int idMat = (int) dados.get("avaliacaodamatricula_matricula_matricula_id");
        int idHab = (int) dados.get("avaliacaodamatricula_habilidadesdaficha_habilidadesdaficha_id");
        String av = (String) dados.get("avaliacaodamatricula_av");

        if (idMat >= 0 && idHab >= 0 && av != null && !av.trim().isEmpty()) {
            GerenciaConexao gerenciaConexao;
            try {
                gerenciaConexao = new GerenciaConexao();
                try {
                    gerenciaConexao.getConexao().iniciarTransacao();
                    AvaliacaoDaMatricula avaliacaoDaMatricula = new AvaliacaoDaMatricula(idMat, idHab, av);
                    if (avaliacaoDaMatricula.gravar(gerenciaConexao.getConexao())) {
                        resposta.put("status", true);
                        resposta.put("mensagem", "Avaliacao da Matrícula registrada com sucesso");
                        gerenciaConexao.getConexao().commit();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.ok(resposta);
                    } else {
                        resposta.put("status", false);
                        resposta.put("mensagem", "Avaliação da Matrícula não foi registrada!");
                        gerenciaConexao.getConexao().rollback();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }
                } catch (Exception e) {
                    resposta.put("status", false);
                    resposta.put("mensagem", "Ocorreu um erro durante o registro");
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

    public ResponseEntity<Object> alterar(Map<String, Object> dados) {
        Map<String, Object> resposta = new HashMap<>();
        int idMat = (int) dados.get("avaliacaodamatricula_matricula_matricula_id");
        int idHab = (int) dados.get("avaliacaodamatricula_habilidadesdaficha_habilidadesdaficha_id");
        String av = (String) dados.get("avaliacaodamatricula_av");

        if(idMat >= 0 && idHab >= 0 && av != null && !av.trim().isEmpty()) {
            GerenciaConexao gerenciaConexao;
            try {
                gerenciaConexao = new GerenciaConexao();
                try {
                    gerenciaConexao.getConexao().iniciarTransacao();
                    AvaliacaoDaMatricula avaliacaoDaMatricula = new AvaliacaoDaMatricula(idMat, idHab, av);
                    if(avaliacaoDaMatricula.alterar(gerenciaConexao.getConexao())) {
                        resposta.put("status", true);
                        resposta.put("mensagem", "Avaliacao da Matrícula: " + avaliacaoDaMatricula.getAvaMatId() + "alterada com sucesso!");
                        gerenciaConexao.getConexao().commit();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();

                        return ResponseEntity.ok(resposta);
                    } else {
                        resposta.put("status", false);
                        resposta.put("mensagem", "Avaliação da Matrícula não foi alterada!");
                        gerenciaConexao.getConexao().rollback();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();

                        return ResponseEntity.badRequest().body(resposta);
                    }
                } catch(Exception e) {
                    resposta.put("status", false);
                    resposta.put("mensagem", "Ocorreu um erro durante a alteracao");
                    gerenciaConexao.getConexao().rollback();
                    gerenciaConexao.getConexao().fimTransacao();
                    gerenciaConexao.Desconectar();
                    return ResponseEntity.badRequest().body(resposta);
                }
            } catch(Exception e) {
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

    public ResponseEntity<Object> buscarTodas() {
        Map<String, Object> resposta = new HashMap<>();
        GerenciaConexao gerenciaConexao = new GerenciaConexao();
        try {
            AvaliacaoDaMatricula avaliacaoDaMatricula = new AvaliacaoDaMatricula();
            List<AvaliacaoDaMatricula> avaliacaoDaMatriculas = avaliacaoDaMatricula.recuperaTodos(gerenciaConexao.getConexao());
            if(avaliacaoDaMatriculas != null && avaliacaoDaMatriculas.size() > 0) {
                resposta.put("status", true);
                resposta.put("listaDeAvaliacoes", avaliacaoDaMatriculas);
                gerenciaConexao.Desconectar();
                return ResponseEntity.ok(resposta);
            } else {
                resposta.put("status", false);
                resposta.put("mensagem", "Não existem avaliacoes cadastradas");
                gerenciaConexao.Desconectar();
                return ResponseEntity.badRequest().body(resposta);
            }
        } catch(Exception e) {
            resposta.put("status", false);
            resposta.put("mensagem", "Ocorreu um erro de conexão");
            gerenciaConexao.Desconectar();
            return ResponseEntity.badRequest().body(resposta);
        }
    }

    public ResponseEntity<Object> buscarAvaliacoesDeUmaMatricula(int id) {
        Map<String, Object> resposta = new HashMap<>();
        GerenciaConexao gerenciaConexao = new GerenciaConexao();
        try {
            AvaliacaoDaMatricula avaliacaoDaMatricula = new AvaliacaoDaMatricula(id);
            List<AvaliacaoDaMatricula> avaliacaoDaMatriculas = avaliacaoDaMatricula.recuperaAvaliacoes(gerenciaConexao.getConexao());
            if(avaliacaoDaMatriculas != null && avaliacaoDaMatriculas.size() > 0) {
                resposta.put("status", true);
                resposta.put("listaDeAvaliacoes", avaliacaoDaMatriculas);
                gerenciaConexao.Desconectar();
                return ResponseEntity.ok(resposta);
            } else {
                resposta.put("status", false);
                resposta.put("mensagem", "Não existem avaliacoes cadastradas");
                gerenciaConexao.Desconectar();
                return ResponseEntity.badRequest().body(resposta);
            }
        } catch(Exception e) {
            resposta.put("status", false);
            resposta.put("mensagem", "Ocorreu um erro de conexão");
            gerenciaConexao.Desconectar();
            return ResponseEntity.badRequest().body(resposta);
        }
    }

    public ResponseEntity<Object> buscarAvaliacao(int idMat, int idHab) {
        Map<String, Object> resposta = new HashMap<>();
        GerenciaConexao gerenciaConexao = new GerenciaConexao();
        try {
            AvaliacaoDaMatricula avaliacaoDaMatricula = new AvaliacaoDaMatricula(idMat, idHab);
            avaliacaoDaMatricula = avaliacaoDaMatricula.recuperaAvaliacao(gerenciaConexao.getConexao());
            if(avaliacaoDaMatricula != null) {
                resposta.put("status", true);
                resposta.put("Avaliação da Matrícula", avaliacaoDaMatricula);
                gerenciaConexao.Desconectar();

                return ResponseEntity.ok(resposta);
            } else {
                resposta.put("status", false);
                resposta.put("mensagem", "Não existe tal avalição da matrícula");
                gerenciaConexao.Desconectar();

                return ResponseEntity.badRequest().body(resposta);
            }
        } catch(Exception e) {
            resposta.put("status", false);
            resposta.put("mensagem", "Ocorreu um erro de conexão");
            gerenciaConexao.Desconectar();
            return ResponseEntity.badRequest().body(resposta);
        }
    }

    public ResponseEntity<Object> apagar(int idMat, int idHab) {
        Map<String, Object> resposta = new HashMap<>();
        if(idMat >= 0 && idHab >= 0) {
            try {
                AvaliacaoDaMatricula avaliacaoDaMatricula = new AvaliacaoDaMatricula(idMat, idHab);
                GerenciaConexao gerenciaConexao = new GerenciaConexao();
                if(avaliacaoDaMatricula.apagar(gerenciaConexao.getConexao())) {
                    resposta.put("status", true);
                    resposta.put("mensagem", "Avaliação da Matrícula excluída com sucesso!");
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
}
