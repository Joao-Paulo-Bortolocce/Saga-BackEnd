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
        char turmaLetra = dados.get("turmaLetra").toString().charAt(0);
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
                    e.printStackTrace();
                    gerenciaConexao.getConexao().rollback();
                    gerenciaConexao.getConexao().fimTransacao();
                    gerenciaConexao.Desconectar();
                    resposta.put("status", false);
                    resposta.put("mensagem", "Erro durante a inserção");
                    return ResponseEntity.badRequest().body(resposta);
                }
            } catch (Exception e) {
                e.printStackTrace();
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

    public ResponseEntity<Object> alterarTurma(Map<String, Object> dados) {
        Map<String, Object> resposta = new HashMap<>();

        try {
            // Verificação de campos obrigatórios
            if (!dados.containsKey("letraAtual") || !dados.containsKey("turmaLetra") ||
                    !dados.containsKey("serie_id") || !dados.containsKey("anoletivo_id") ||
                    !dados.containsKey("profissional_rn") || !dados.containsKey("sala_id") ||
                    !dados.containsKey("serieAntiga") || !dados.containsKey("anoAntigo") ||
                    !dados.containsKey("profAntigo") || !dados.containsKey("salaAntiga")) {

                resposta.put("status", false);
                resposta.put("mensagem", "Dados incompletos ou inválidos para alteração.");
                return ResponseEntity.badRequest().body(resposta);
            }

            // Valores novos
            char novaLetra = dados.get("turmaLetra").toString().charAt(0);
            int novaSerie = (int) dados.get("serie_id");
            int novoAno = (int) dados.get("anoletivo_id");
            int novoProf = (int) dados.get("profissional_rn");
            int novaSala = (int) dados.get("sala_id");

            // Valores antigos (para WHERE)
            char letraAntiga = dados.get("letraAtual").toString().charAt(0);
            int serieAntiga = (int) dados.get("serieAntiga");
            int anoAntigo = (int) dados.get("anoAntigo");
            int profAntigo = (int) dados.get("profAntigo");
            int salaAntiga = (int) dados.get("salaAntiga");

            GerenciaConexao gc = new GerenciaConexao();

            Serie serie = new Serie(novaSerie, 0, "");
            AnoLetivo ano = new AnoLetivo(novoAno, null, null);
            Profissional prof = new Profissional(novoProf);
            Sala sala = new Sala(novaSala, 0, "");

            Turma turma = new Turma();
            turma.setLetra(letraAntiga); // valor atual (antigo)
            turma.setSerie(serie);
            turma.setAnoLetivo(ano);
            turma.setProfissional(prof);
            turma.setSala(sala);

            gc.getConexao().iniciarTransacao();
            boolean sucesso = turma.alterar(novaLetra, gc.getConexao(), serieAntiga, anoAntigo, profAntigo, salaAntiga);

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
