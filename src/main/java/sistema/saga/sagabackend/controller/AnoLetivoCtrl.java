package sistema.saga.sagabackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sistema.saga.sagabackend.model.AnoLetivo;
import sistema.saga.sagabackend.repository.GerenciaConexao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnoLetivoCtrl {
    public ResponseEntity<Object> gravarAno(Map<String, Object> dados) {
        Map<String, Object> resposta = new HashMap<>();
        String inicioStr = (String) dados.get("inicio");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate inicio = LocalDate.parse(inicioStr, formatter);
        String fimStr = (String) dados.get("fim");
        LocalDate fim = LocalDate.parse(fimStr, formatter);

        if (verificaIntegridade(inicio) && verificaIntegridade(fim)) {
            GerenciaConexao gerenciaConexao;
            try {
                gerenciaConexao = new GerenciaConexao();
                try {
                    gerenciaConexao.getConexao().iniciarTransacao();
                    //begin transaction
                    AnoLetivo anoLetivo = new AnoLetivo(inicio,fim);
                    if (anoLetivo.gravar(gerenciaConexao.getConexao())) {
                        resposta.put("status", true);
                        resposta.put("mensagem", "Ano letivo inserido com sucesso");
                        //commit; end transaction;
                        gerenciaConexao.getConexao().commit();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.ok(resposta);
                    } else {
                        resposta.put("status", false);
                        resposta.put("mensagem", "Ano letivo não foi inserido!");
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

    public ResponseEntity<Object> alterarAno(int id, Map<String, Object> dados) {
        Map<String, Object> resposta = new HashMap<>();
        String inicioStr = (String) dados.get("inicio");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate inicio = LocalDate.parse(inicioStr, formatter);
        String fimStr = (String) dados.get("fim");
        LocalDate fim = LocalDate.parse(fimStr, formatter);

        if (verificaIntegridade(id) && verificaIntegridade(inicio) && verificaIntegridade(fim)) {
            GerenciaConexao gerenciaConexao;
            try {
                gerenciaConexao = new GerenciaConexao();
                try {
                    gerenciaConexao.getConexao().iniciarTransacao();
                    AnoLetivo anoLetivo = new AnoLetivo(id,inicio,fim);
                    if (anoLetivo.alterar(gerenciaConexao.getConexao())) {
                        resposta.put("status", true);
                        resposta.put("mensagem", "Ano letivo alterado com sucesso");
                        gerenciaConexao.getConexao().commit();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.ok(resposta);
                    } else {
                        resposta.put("status", false);
                        resposta.put("mensagem", "Ano letivo não foi alterado!");
                        gerenciaConexao.getConexao().rollback();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }
                } catch (Exception e) {
                    resposta.put("status", false);
                    resposta.put("mensagem", "Ocorreu um erro de conexão");
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

    public ResponseEntity<Object> apagarAno(int id) {
        Map<String, Object> resposta = new HashMap<>();
        if (verificaIntegridade(id)) {
            try {
                AnoLetivo anoLetivo = new AnoLetivo();
                anoLetivo.setId(id);
                GerenciaConexao gerenciaConexao = new GerenciaConexao();
                if (anoLetivo.apagar(gerenciaConexao.getConexao())) {
                    resposta.put("status", true);
                    resposta.put("mensagem", "Ano letivo excluido com sucesso!");
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

    public ResponseEntity<Object> buscarAnos(String termo) {
        Map<String, Object> resposta = new HashMap<>();
        try {
            GerenciaConexao gerenciaConexao = new GerenciaConexao();
            AnoLetivo anoLetivo = new AnoLetivo();
            List<AnoLetivo> anoLetivoList = anoLetivo.buscarTodos(gerenciaConexao.getConexao());
            gerenciaConexao.Desconectar();
            if (anoLetivoList != null && !anoLetivoList.isEmpty()) {
                resposta.put("status", true);
<<<<<<< HEAD
                resposta.put("series", anoLetivoList);
=======
                resposta.put("anos", anoLetivoList);
>>>>>>> ffbd4c8b9f14764ab1933c9bae2da73391cdc403
                return ResponseEntity.ok(resposta);
            } else {
                resposta.put("status", false);
                resposta.put("mensagem", "Nenhum ano letivo encontrado.");
                return ResponseEntity.badRequest().body(resposta);
            }

        } catch (Exception e) {
            resposta.put("status", false);
            resposta.put("mensagem", "Erro ao buscar anos letivos");
            return ResponseEntity.badRequest().body(resposta);
        }
    }

    private boolean verificaIntegridade(int elemento) {
        return elemento > 0;
    }

    private boolean verificaIntegridade(LocalDate elemento) {
        return elemento != null;
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> ffbd4c8b9f14764ab1933c9bae2da73391cdc403
