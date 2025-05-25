package sistema.saga.sagabackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sistema.saga.sagabackend.model.*;
import sistema.saga.sagabackend.repository.GerenciaConexao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TurmaCtrl {
    public ResponseEntity<Object> gravarTurma(Map<String, Object> dados) {
        Map<String, Object> resposta = new HashMap<>();
        char turmaLetra = dados.get("turmaletra").toString().charAt(0);
        Object serieObj = dados.get("serie_id");
        Object anoObj = dados.get("anoletivo_id");
        Object profObj = dados.get("profissional_rn");
        Object salaObj = dados.get("sala_id");


        if (serieObj == null || anoObj == null || profObj == null || salaObj == null) {
            resposta.put("status", false);
            resposta.put("mensagem", "Dados obrigatórios ausentes");
            return ResponseEntity.badRequest().body(resposta);
        }

        int turmaSerieId = ((Number) serieObj).intValue();
        int turmaAnoLetivoId = ((Number) anoObj).intValue();
        int turmaProfissionalRa = ((Number) profObj).intValue();
        int turmaSalaId = ((Number) salaObj).intValue();

        if (verificaIntegridade(turmaLetra) && verificaIntegridade(turmaSerieId)
                && verificaIntegridade(turmaAnoLetivoId) && verificaIntegridade(turmaProfissionalRa)
                && verificaIntegridade(turmaSalaId)) {

            GerenciaConexao gerenciaConexao;
            try {
                gerenciaConexao = new GerenciaConexao();
                try {
                    gerenciaConexao.getConexao().iniciarTransacao();

                    Serie serie = new Serie();
                    serie.setSerieId(turmaSerieId);
                    if (serie.buscaSerie(gerenciaConexao.getConexao()) == 0) {
                        resposta.put("status", false);
                        resposta.put("mensagem", "Série não encontrada.");
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }

                    AnoLetivo anoLetivo = new AnoLetivo();
                    anoLetivo.setId(turmaAnoLetivoId);
                    if (anoLetivo.buscaAnos(gerenciaConexao.getConexao()) == 0) {
                        resposta.put("status", false);
                        resposta.put("mensagem", "Ano letivo não encontrado.");
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }

                    Profissional prof = new Profissional();
                    prof.setProfissional_rn(turmaProfissionalRa);
                    if (prof.buscaProfissional(gerenciaConexao.getConexao(), prof, new HashMap<>(), new HashMap<>()) == null) {
                        resposta.put("status", false);
                        resposta.put("mensagem", "Profissional não encontrado.");
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }

                    Sala sala = new Sala();
                    sala.setId(turmaSalaId);
                    if (sala.buscaSerie(gerenciaConexao.getConexao()) == 0) {
                        resposta.put("status", false);
                        resposta.put("mensagem", "Sala não encontrada.");
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }

                    Turma turma = new Turma();
                    turma.setLetra(turmaLetra);
                    turma.setSerie(serie);
                    turma.setAnoLetivo(anoLetivo);
                    turma.setProfissional(prof);
                    turma.setSala(sala);

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

                } catch (Exception e) {
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
        } else {
            resposta.put("status", false);
            resposta.put("mensagem", "Dados inválidos");
            return ResponseEntity.badRequest().body(resposta);
        }
    }

    public ResponseEntity<Object> alterarTurma(Map<String, Object> dados) {
        Map<String, Object> resposta = new HashMap<>();

        try {
            // Validações básicas
            if (!dados.containsKey("letraAtual") || !verificaIntegridade(dados.get("letraAtual").toString()) ||
                    !dados.containsKey("novaLetra") || !verificaIntegridade(dados.get("novaLetra").toString()) ||
                    !dados.containsKey("serie_id") || !dados.containsKey("anoletivo_id") ||
                    !dados.containsKey("profissional_rn") || !dados.containsKey("sala_id")) {
                resposta.put("status", false);
                resposta.put("mensagem", "Dados incompletos ou inválidos para alteração.");
                return ResponseEntity.badRequest().body(resposta);
            }

            // Conversão dos dados recebidos
            char letraAntiga = dados.get("letraAtual").toString().charAt(0);
            char novaLetra = dados.get("novaLetra").toString().charAt(0);
            int serieId = (int) dados.get("serie_id");
            int anoLetivoId = (int) dados.get("anoletivo_id");
            int profissionalRa = (int) dados.get("profissional_rn");
            int salaId = (int) dados.get("sala_id");

            GerenciaConexao gc = new GerenciaConexao();

            // Verificação e montagem dos objetos necessários
            Serie serie = new Serie(serieId, 0, "");
            if (serie.buscaSerie(gc.getConexao()) == 0) {
                resposta.put("status", false);
                resposta.put("mensagem", "Série não encontrada.");
                gc.Desconectar();
                return ResponseEntity.badRequest().body(resposta);
            }

            AnoLetivo ano = new AnoLetivo(anoLetivoId, null, null);
            if (ano.buscaAnos(gc.getConexao()) == 0) {
                resposta.put("status", false);
                resposta.put("mensagem", "Ano letivo não encontrado.");
                gc.Desconectar();
                return ResponseEntity.badRequest().body(resposta);
            }

            Profissional prof = new Profissional(profissionalRa);
            if (prof.buscaProfissional(gc.getConexao(), prof, new HashMap<>(), new HashMap<>()) == null) {
                resposta.put("status", false);
                resposta.put("mensagem", "Profissional não encontrado.");
                gc.Desconectar();
                return ResponseEntity.badRequest().body(resposta);
            }

            Sala sala = new Sala(salaId, 0, "");
            if (sala.buscaSerie(gc.getConexao()) == 0) {
                resposta.put("status", false);
                resposta.put("mensagem", "Sala não encontrada.");
                gc.Desconectar();
                return ResponseEntity.badRequest().body(resposta);
            }

            // Montagem da turma
            Turma turma = new Turma();
            turma.setLetra(letraAntiga);
            turma.setSerie(serie);
            turma.setAnoLetivo(ano);
            turma.setProfissional(prof);
            turma.setSala(sala);

            gc.getConexao().iniciarTransacao();
            boolean sucesso = turma.alterar(novaLetra, gc.getConexao());

            if (sucesso) {
                gc.getConexao().commit();
                resposta.put("status", true);
                resposta.put("mensagem", "Turma alterada com sucesso!");
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
            resposta.put("mensagem", "Erro interno na alteração.");
            return ResponseEntity.badRequest().body(resposta);
        }
    }


    public ResponseEntity<Object> excluirTurma(String letra, int serieId, int anoLetivoId, int profissionalRa, int salaId) {
        Map<String, Object> resposta = new HashMap<>();

        if (verificaIntegridade(letra) && verificaIntegridade(serieId) && verificaIntegridade(anoLetivoId)
                && verificaIntegridade(profissionalRa) && verificaIntegridade(salaId)) {
            try {
                Turma turma = new Turma();
                turma.setLetra(letra.charAt(0));

                Serie serie = new Serie();
                serie.setSerieId(serieId);
                turma.setSerie(serie);

                AnoLetivo anoLetivo = new AnoLetivo();
                anoLetivo.setId(anoLetivoId);
                turma.setAnoLetivo(anoLetivo);

                Profissional profissional = new Profissional();
                profissional.setProfissional_rn(profissionalRa);
                turma.setProfissional(profissional);

                Sala sala = new Sala();
                sala.setId(salaId);
                turma.setSala(sala);

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

            List<Turma> turmas;
            if (termo == null || termo.isEmpty()) {
                turmas = turma.buscarTodos(gc.getConexao());
            }
            else {
                turmas = turma.buscarPorTermo(termo, gc.getConexao());
            }

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
}
