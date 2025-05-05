package sistema.saga.sagabackend.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sistema.saga.sagabackend.model.*;
import sistema.saga.sagabackend.repository.GerenciaConexao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReuniaoCtrl {

    public ResponseEntity<Object> gravarReuniao(Map<String, Object> dados) {
        Map<String, Object> resposta = new HashMap<>();
        System.out.println(">>> Dados recebidos no backend:");
        dados.forEach((k, v) -> System.out.println(k + ": " + v));

        try {
            // Validar e converter a data
            String dataStr = (String) dados.get("data");
            System.out.println(">>> Valor de data: " + dataStr);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime data = LocalDateTime.parse(dataStr, formatter);
            System.out.println(">>> Data convertida: " + data);

            // Validar e extrair os outros campos
            String letra = dados.get("letra") != null ? dados.get("letra").toString().toUpperCase() : "";
            int serieId = Integer.parseInt(dados.get("serie_id").toString());
            int anoId = Integer.parseInt(dados.get("anoletivo_id").toString());
            String tipo = dados.get("tipo") != null ? dados.get("tipo").toString() : "";

            // Verificação de integridade
            if (!verificaIntegridade(letra) || !verificaIntegridade(serieId)
                    || !verificaIntegridade(anoId) || !verificaIntegridade(tipo)) {
                resposta.put("status", false);
                resposta.put("mensagem", "Dados inválidos.");
                return ResponseEntity.badRequest().body(resposta);
            }

            // Abertura de conexão e transação
            GerenciaConexao gc = new GerenciaConexao();
            gc.getConexao().iniciarTransacao();

            // Buscar Série
            Serie serie = new Serie();
            serie.setSerieId(serieId);
            if (serie.buscaSerie(gc.getConexao()) == 0) {
                gc.Desconectar();
                resposta.put("status", false);
                resposta.put("mensagem", "Série não encontrada.");
                return ResponseEntity.badRequest().body(resposta);
            }

            // Buscar Ano Letivo
            AnoLetivo ano = new AnoLetivo();
            ano.setId(anoId);
            if (ano.buscaAnos(gc.getConexao()) == 0) {
                gc.Desconectar();
                resposta.put("status", false);
                resposta.put("mensagem", "Ano letivo não encontrado.");
                return ResponseEntity.badRequest().body(resposta);
            }

            // Construir e gravar reunião
            Turma turma = new Turma();
            turma.setLetra(letra.charAt(0));

            Reuniao reuniao = new Reuniao(0, tipo, ano, serie, turma, data);
            boolean sucesso = reuniao.gravar(gc.getConexao());

            if (sucesso) {
                gc.getConexao().commit();
                resposta.put("status", true);
                resposta.put("mensagem", "Reunião cadastrada com sucesso!");
            } else {
                gc.getConexao().rollback();
                resposta.put("status", false);
                resposta.put("mensagem", "Erro ao gravar reunião.");
            }

            gc.getConexao().fimTransacao();
            gc.Desconectar();
            return ResponseEntity.ok(resposta);

        } catch (Exception e) {
            e.printStackTrace();
            resposta.put("status", false);
            resposta.put("mensagem", "Erro interno ao gravar reunião: " + e.getMessage());
            return ResponseEntity.badRequest().body(resposta);
        }
    }

    public ResponseEntity<Object> alterarReuniao(int id, Map<String, Object> dados) {
        Map<String, Object> resposta = new HashMap<>();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime data = LocalDateTime.parse(dados.get("data").toString(), formatter);
            String letra = dados.get("letra").toString();
            int serieId = (int) dados.get("serie_id");
            int anoId = (int) dados.get("anoletivo_id");
            String tipo = dados.get("tipo").toString();

            if (!verificaIntegridade(letra) || !verificaIntegridade(serieId) || !verificaIntegridade(anoId) || !verificaIntegridade(tipo)) {
                resposta.put("status", false);
                resposta.put("mensagem", "Dados inválidos.");
                return ResponseEntity.badRequest().body(resposta);
            }

            GerenciaConexao gc = new GerenciaConexao();
            gc.getConexao().iniciarTransacao();

            Serie serie = new Serie();
            serie.setSerieId(serieId);

            AnoLetivo ano = new AnoLetivo();
            ano.setId(anoId);

            Turma turma = new Turma();
            turma.setLetra(letra.charAt(0));

            Reuniao reuniao = new Reuniao(id, tipo, ano, serie, turma, data);
            if (reuniao.alterar(gc.getConexao())) {
                gc.getConexao().commit();
                resposta.put("status", true);
                resposta.put("mensagem", "Reunião alterada com sucesso.");
            } else {
                gc.getConexao().rollback();
                resposta.put("status", false);
                resposta.put("mensagem", "Erro ao alterar reunião.");
            }

            gc.getConexao().fimTransacao();
            gc.Desconectar();
            return ResponseEntity.ok(resposta);

        } catch (Exception e) {
            resposta.put("status", false);
            resposta.put("mensagem", "Erro interno ao alterar reunião.");
            return ResponseEntity.badRequest().body(resposta);
        }
    }

    public ResponseEntity<Object> excluirReuniao(int id) {
        Map<String, Object> resposta = new HashMap<>();
        try {
            GerenciaConexao gc = new GerenciaConexao();
            Reuniao reuniao = new Reuniao();
            reuniao.setReuniaoId(id);

            if (reuniao.apagar(gc.getConexao())) {
                resposta.put("status", true);
                resposta.put("mensagem", "Reunião excluída com sucesso.");
            } else {
                resposta.put("status", false);
                resposta.put("mensagem", "Erro ao excluir reunião.");
            }

            gc.Desconectar();
            return ResponseEntity.ok(resposta);

        } catch (Exception e) {
            resposta.put("status", false);
            resposta.put("mensagem", "Erro interno ao excluir reunião.");
            return ResponseEntity.badRequest().body(resposta);
        }
    }

    public ResponseEntity<Object> buscarReunioes(String termo) {
        Map<String, Object> resposta = new HashMap<>();
        try {
            GerenciaConexao gc = new GerenciaConexao();
            Reuniao reuniao = new Reuniao();
            List<Reuniao> lista;

            if (termo == null || termo.isEmpty()) {
                lista = reuniao.buscarTodos(gc.getConexao());
            } else {
                lista = reuniao.buscarPorTermo(termo, gc.getConexao());
            }

            gc.Desconectar();

            if (!lista.isEmpty()) {
                resposta.put("status", true);
                resposta.put("reunioes", lista);
                return ResponseEntity.ok(resposta);
            } else {
                resposta.put("status", false);
                resposta.put("mensagem", "Nenhuma reunião encontrada.");
                return ResponseEntity.badRequest().body(resposta);
            }

        } catch (Exception e) {
            resposta.put("status", false);
            resposta.put("mensagem", "Erro ao buscar reuniões.");
            return ResponseEntity.badRequest().body(resposta);
        }
    }

    private boolean verificaIntegridade(String elemento) {
        return elemento != null && !elemento.trim().isEmpty();
    }

    private boolean verificaIntegridade(int elemento) {
        return elemento > 0;
    }
}