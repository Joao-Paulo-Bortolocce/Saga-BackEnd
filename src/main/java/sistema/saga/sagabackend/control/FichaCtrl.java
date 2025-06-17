package sistema.saga.sagabackend.control;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sistema.saga.sagabackend.model.Ficha;
import sistema.saga.sagabackend.repository.GerenciaConexao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FichaCtrl {

    public ResponseEntity<Object> gravarFicha(Map<String, Object> dados) {
        Map<String, Object> resposta = new HashMap<>();
        int ficha_id = (int) dados.get("ficha_id");
        int ficha_bimestre_id = (int) dados.get("ficha_bimestre_id");
        int ficha_bimestre_anoLetivo_id = (int) dados.get("ficha_bimestre_anoLetivo_id");
        int ficha_bimestre_serie_id = (int) dados.get("ficha_bimestre_serie_id");

        if (ficha_id >= 0 && ficha_bimestre_id >= 0 && ficha_bimestre_anoLetivo_id >= 0 && ficha_bimestre_serie_id >= 0) {
            GerenciaConexao gerenciaConexao;
            try {
                gerenciaConexao = new GerenciaConexao();
                try {
                    gerenciaConexao.getConexao().iniciarTransacao();
                    Ficha ficha = new Ficha(ficha_id, ficha_bimestre_id, ficha_bimestre_anoLetivo_id, ficha_bimestre_serie_id);
                    if (ficha.gravar(gerenciaConexao.getConexao())) {
                        resposta.put("status", true);
                        resposta.put("mensagem", "Ficha registrada com sucesso");
                        gerenciaConexao.getConexao().commit();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.ok(resposta);
                    } else {
                        resposta.put("status", false);
                        resposta.put("mensagem", "Ficha não foi registrada!");
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
        int ficha_id = (int) dados.get("ficha_id");
        int ficha_bimestre_id = (int) dados.get("ficha_bimestre_id");
        int ficha_bimestre_anoLetivo_id = (int) dados.get("ficha_bimestre_anoLetivo_id");
        int ficha_bimestre_serie_id = (int) dados.get("ficha_bimestre_serie_id");

        if(ficha_id >= 0 && ficha_bimestre_id >= 0 && ficha_bimestre_anoLetivo_id >= 0 && ficha_bimestre_serie_id >= 0) {
            GerenciaConexao gerenciaConexao;
            try {
                gerenciaConexao = new GerenciaConexao();
                try {
                    gerenciaConexao.getConexao().iniciarTransacao();
                    Ficha ficha = new Ficha(ficha_id, ficha_bimestre_id, ficha_bimestre_anoLetivo_id, ficha_bimestre_serie_id);
                    if(ficha.alterar(gerenciaConexao.getConexao())) {
                        resposta.put("status", true);
                        resposta.put("mensagem", "Ficha: " + ficha.getFicha_id() + "alterada com sucesso!");
                        gerenciaConexao.getConexao().commit();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();

                        return ResponseEntity.ok(resposta);
                    } else {
                        resposta.put("status", false);
                        resposta.put("mensagem", "Ficha não foi alterada!");
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
            Ficha ficha = new Ficha();
            List<Ficha> fichas = ficha.buscarTodas(gerenciaConexao.getConexao());
            if(fichas != null && fichas.size() > 0) {
                resposta.put("status", true);
                resposta.put("listaDeFichas", fichas);
                gerenciaConexao.Desconectar();
                return ResponseEntity.ok(resposta);
            } else {
                resposta.put("status", false);
                resposta.put("mensagem", "Não existem fichas cadastradas");
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

    public ResponseEntity<Object> apagar(int ficha_id) {
        Map<String, Object> resposta = new HashMap<>();
        if(ficha_id >= 0) {
            try {
                Ficha ficha = new Ficha(ficha_id);
                GerenciaConexao gerenciaConexao = new GerenciaConexao();
                if(ficha.apagar(gerenciaConexao.getConexao())) {
                    resposta.put("status", true);
                    resposta.put("mensagem", "Ficha excluída com sucesso!");
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
