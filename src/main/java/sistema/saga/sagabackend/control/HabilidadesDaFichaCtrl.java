package sistema.saga.sagabackend.control;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sistema.saga.sagabackend.model.HabilidadesDaFicha;
import sistema.saga.sagabackend.repository.GerenciaConexao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HabilidadesDaFichaCtrl {
    public ResponseEntity<Object> gravarHabilidadesDaFicha(Map<String, Object> dados) {
        Map<String, Object> resposta = new HashMap<>();
        int habilidadesDaFicha_habilidades_id = (int) dados.get("habilidadesDaFicha_habilidades_id");
        int habilidadesDaFicha_ficha_id = (int) dados.get("habilidadesDaFicha_ficha_id");

        if ( habilidadesDaFicha_habilidades_id >= 0 && habilidadesDaFicha_ficha_id >= 0) {
            GerenciaConexao gerenciaConexao;
            try {
                gerenciaConexao = new GerenciaConexao();
                try {
                    gerenciaConexao.getConexao().iniciarTransacao();
                    HabilidadesDaFicha habilidadesDaFicha = new HabilidadesDaFicha(0, habilidadesDaFicha_habilidades_id, habilidadesDaFicha_ficha_id);
                    if (habilidadesDaFicha.gravar(gerenciaConexao.getConexao())) {
                        resposta.put("status", true);
                        resposta.put("mensagem", "Habilidades Da Ficha registrada com sucesso");
                        gerenciaConexao.getConexao().commit();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.ok(resposta);
                    } else {
                        resposta.put("status", false);
                        resposta.put("mensagem", "Habilidades Da Ficha não foi registrada!");
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
        int habilidadesDaFicha_id = (int) dados.get("habilidadesDaFicha_id");
        int habilidadesDaFicha_habilidades_id = (int) dados.get("habilidadesDaFicha_habilidades_id");
        int habilidadesDaFicha_ficha_id = (int) dados.get("habilidadesDaFicha_ficha_id");

        if(habilidadesDaFicha_id >= 0 && habilidadesDaFicha_habilidades_id >= 0 && habilidadesDaFicha_ficha_id >= 0) {
            GerenciaConexao gerenciaConexao;
            try {
                gerenciaConexao = new GerenciaConexao();
                try {
                    gerenciaConexao.getConexao().iniciarTransacao();
                    HabilidadesDaFicha habilidadesDaFicha = new HabilidadesDaFicha(habilidadesDaFicha_id, habilidadesDaFicha_habilidades_id, habilidadesDaFicha_ficha_id);
                    if(habilidadesDaFicha.alterar(gerenciaConexao.getConexao())) {
                        resposta.put("status", true);
                        resposta.put("mensagem", "Habilidades da Ficha: " + habilidadesDaFicha.getHabilidadesDaFicha_id() + "alterada com sucesso!");
                        gerenciaConexao.getConexao().commit();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();

                        return ResponseEntity.ok(resposta);
                    } else {
                        resposta.put("status", false);
                        resposta.put("mensagem", "Habilidades da Ficha não foi alterada!");
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
            HabilidadesDaFicha habilidadesDaFicha = new HabilidadesDaFicha();
            List<HabilidadesDaFicha> habilidadesDaFichas = habilidadesDaFicha.buscarTodas(gerenciaConexao.getConexao());
            if(habilidadesDaFichas != null && habilidadesDaFichas.size() > 0) {
                resposta.put("status", true);
                resposta.put("listaDeHabilidadesDeFichas", habilidadesDaFichas);
                gerenciaConexao.Desconectar();
                return ResponseEntity.ok(resposta);
            } else {
                resposta.put("status", false);
                resposta.put("mensagem", "Não existem habilidades de fichas cadastradas");
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

    public ResponseEntity<Object> buscarHabDaFicha(int idFicha) {
        Map<String, Object> resposta = new HashMap<>();
        GerenciaConexao gerenciaConexao = new GerenciaConexao();
        try {
            HabilidadesDaFicha habilidadesDaFicha = new HabilidadesDaFicha();
            List<HabilidadesDaFicha> habilidadesDaFichas = habilidadesDaFicha.buscarTodasHabFicha(idFicha,gerenciaConexao.getConexao());
            if (habilidadesDaFicha != null && habilidadesDaFichas.size() > 0) {
                resposta.put("status", true);
                resposta.put("listaDeHabilidadesDaFicha", habilidadesDaFichas);
                gerenciaConexao.Desconectar();
                return ResponseEntity.ok(resposta);
            } else {
                resposta.put("status", false);
                resposta.put("mensagem", "Não existem habilidades da ficha cadastradas com os filtros selecionados");
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

    public ResponseEntity<Object> apagar(int idHab, int idFicha) {
        Map<String, Object> resposta = new HashMap<>();
        if(idHab >= 0 && idFicha >= 0) {
            try {
                HabilidadesDaFicha habilidadesDaFicha = new HabilidadesDaFicha(idHab, idFicha);
                GerenciaConexao gerenciaConexao = new GerenciaConexao();
                if(habilidadesDaFicha.apagar(gerenciaConexao.getConexao())) {
                    resposta.put("status", true);
                    resposta.put("mensagem", "Habilidades da Ficha excluída com sucesso!");
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
