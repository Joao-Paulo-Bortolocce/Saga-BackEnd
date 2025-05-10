package sistema.saga.sagabackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sistema.saga.sagabackend.model.AnoLetivo;
import sistema.saga.sagabackend.model.Serie;
import sistema.saga.sagabackend.model.Turma;
import sistema.saga.sagabackend.repository.GerenciaConexao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TurmaCtrl {
    public ResponseEntity<Object> gravarTurma(Map<String, Object> dados) {
        Map<String, Object> resposta = new HashMap<>();
        char turmaLetra = dados.get("turmaletra").toString().charAt(0);
        int turmaSerieId = (int) dados.get("serie_id");
        int turmaAnoLetivoId = (int) dados.get("anoletivo_id");

        if (verificaIntegridade(turmaLetra) && verificaIntegridade(turmaSerieId) && verificaIntegridade(turmaAnoLetivoId)) {
            GerenciaConexao gerenciaConexao;
            try {
                gerenciaConexao = new GerenciaConexao();
                try {
                    gerenciaConexao.getConexao().iniciarTransacao();

                    // Verifica se a série existe
                    Serie serie = new Serie();
                    serie.setSerieId(turmaSerieId);
                    int idSerieConfirmado = serie.buscaSerie(gerenciaConexao.getConexao());
                    if (idSerieConfirmado == 0) {
                        resposta.put("status", false);
                        resposta.put("mensagem", "Série não encontrada.");
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }

                    // Verifica se o ano letivo existe
                    AnoLetivo anoLetivo = new AnoLetivo();
                    anoLetivo.setId(turmaAnoLetivoId);
<<<<<<< HEAD
                    int idAnoConfirmado = anoLetivo.buscaAnos(gerenciaConexao.getConexao()); // você precisa criar esse método
=======
                    int idAnoConfirmado = anoLetivo.buscaAnos(gerenciaConexao.getConexao());
>>>>>>> ffbd4c8b9f14764ab1933c9bae2da73391cdc403
                    if (idAnoConfirmado == 0) {
                        resposta.put("status", false);
                        resposta.put("mensagem", "Ano letivo não encontrado.");
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }

                    // Criando objeto Turma
                    Turma turma = new Turma();
                    turma.setLetra(turmaLetra);
                    turma.setSerie(serie);
                    turma.setAnoLetivo(anoLetivo);

                    if (turma.gravar(gerenciaConexao.getConexao())) {
                        resposta.put("status", true);
                        resposta.put("mensagem", "Turma inserida com sucesso!");
                        gerenciaConexao.getConexao().commit();
                    }
                    else {
                        resposta.put("status", false);
                        resposta.put("mensagem", "Erro ao inserir turma!");
                        gerenciaConexao.getConexao().rollback();
                    }

                    gerenciaConexao.getConexao().fimTransacao();
                    gerenciaConexao.Desconectar();
                    return ResponseEntity.ok(resposta);

                }
                catch (Exception e) {
                    gerenciaConexao.getConexao().rollback();
                    gerenciaConexao.getConexao().fimTransacao();
                    gerenciaConexao.Desconectar();
                    resposta.put("status", false);
                    resposta.put("mensagem", "Erro durante a inserção");
                    return ResponseEntity.badRequest().body(resposta);
                }
            } catch (Exception e) {
                resposta.put("status", false);
                resposta.put("mensagem", "Erro ao iniciar conexão");
                return ResponseEntity.badRequest().body(resposta);
            }
        }
        else {
            resposta.put("status", false);
            resposta.put("mensagem", "Dados inválidos");
            return ResponseEntity.badRequest().body(resposta);
        }
    }

    public ResponseEntity<Object> alterarTurma(String letraAntiga, int serieId, int anoLetivoId, Map<String, Object> dados) {
        Map<String, Object> resposta = new HashMap<>();

        if (!dados.containsKey("novaLetra") || !verificaIntegridade(dados.get("novaLetra").toString())) {
            resposta.put("status", false);
            resposta.put("mensagem", "Nova letra inválida");
            return ResponseEntity.badRequest().body(resposta);
        }
        else{
            try {
                GerenciaConexao gc = new GerenciaConexao();

                Turma turma = new Turma();
                turma.setLetra(letraAntiga.charAt(0));

                Serie serie = new Serie();
                serie.setSerieId(serieId);
                turma.setSerie(serie);

                AnoLetivo ano = new AnoLetivo();
                ano.setId(anoLetivoId);
                turma.setAnoLetivo(ano);

                char novaLetra = dados.get("novaLetra").toString().charAt(0);

                gc.getConexao().iniciarTransacao();
                boolean sucesso = turma.alterar(novaLetra, gc.getConexao());
                if (sucesso) {
                    gc.getConexao().commit();
                    resposta.put("status", true);
                    resposta.put("mensagem", "Letra da turma alterada com sucesso!");
                } else {
                    gc.getConexao().rollback();
                    resposta.put("status", false);
                    resposta.put("mensagem", "Erro ao alterar a turma.");
                }
                gc.getConexao().fimTransacao();
                gc.Desconectar();
                return ResponseEntity.ok(resposta);

            } catch (Exception e) {
                resposta.put("status", false);
                resposta.put("mensagem", "Erro na alteração");
                return ResponseEntity.badRequest().body(resposta);
            }
        }
    }

    public ResponseEntity<Object> excluirTurma(String letra, int serieId, int anoLetivoId) {
        Map<String, Object> resposta = new HashMap<>();

        if (verificaIntegridade(letra) && verificaIntegridade(serieId) && verificaIntegridade(anoLetivoId)) {
            try {
                // Monta objeto Turma
                Turma turma = new Turma();
                turma.setLetra(letra.charAt(0));

                Serie serie = new Serie();
                serie.setSerieId(serieId);
                turma.setSerie(serie);

                AnoLetivo anoLetivo = new AnoLetivo();
                anoLetivo.setId(anoLetivoId);
                turma.setAnoLetivo(anoLetivo);

                GerenciaConexao gerenciaConexao = new GerenciaConexao();
                if (turma.apagar(gerenciaConexao.getConexao())) {
                    resposta.put("status", true);
                    resposta.put("mensagem", "Turma excluída com sucesso!");
                } else {
                    resposta.put("status", false);
                    resposta.put("mensagem", "Erro ao excluir turma!");
                }
                gerenciaConexao.Desconectar();
                return ResponseEntity.ok(resposta);
            } catch (Exception e) {
                resposta.put("status", false);
                resposta.put("mensagem", "Erro de conexão");
                return ResponseEntity.badRequest().body(resposta);
            }
        } else {
            resposta.put("status", false);
            resposta.put("mensagem", "Parâmetros inválidos");
            return ResponseEntity.badRequest().body(resposta);
        }
    }

    public ResponseEntity<Object> buscarTurmas(String termo) {
        Map<String, Object> resposta = new HashMap<>();
        try {
            GerenciaConexao gc = new GerenciaConexao();
            Turma turma = new Turma();
<<<<<<< HEAD
            List<Turma> turmas = turma.buscarTodos(gc.getConexao());
=======

            List<Turma> turmas;
            if (termo == null || termo.isEmpty()) {
                turmas = turma.buscarTodos(gc.getConexao());
            }
            else {
                turmas = turma.buscarPorTermo(termo, gc.getConexao());
            }

>>>>>>> ffbd4c8b9f14764ab1933c9bae2da73391cdc403
            gc.Desconectar();

            if (turmas != null && !turmas.isEmpty()) {
                resposta.put("status", true);
                resposta.put("turmas", turmas);
                return ResponseEntity.ok(resposta);
            }
            else {
                resposta.put("status", false);
                resposta.put("mensagem", "Nenhuma turma encontrada.");
                return ResponseEntity.badRequest().body(resposta);
            }

        } catch (Exception e) {
            resposta.put("status", false);
            resposta.put("mensagem", "Erro ao buscar turmas");
            return ResponseEntity.badRequest().body(resposta);
        }
    }

    private boolean verificaIntegridade(String elemento) {
        return elemento != null && !elemento.trim().isEmpty();
    }

    private boolean verificaIntegridade(char elemento) {
        return elemento != 0;
    }

    private boolean verificaIntegridade(int elemento) {
        return elemento > 0;
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> ffbd4c8b9f14764ab1933c9bae2da73391cdc403
