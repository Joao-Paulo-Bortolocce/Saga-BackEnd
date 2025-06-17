package sistema.saga.sagabackend.control;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sistema.saga.sagabackend.model.Graduacao;
import sistema.saga.sagabackend.repository.GerenciaConexao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GraduacaoCtrl {
    public ResponseEntity<Object> gravarGraduacao(Map<String, Object> dados) {
        Map<String, Object> resposta = new HashMap<>();
        String gradDescricao = (String) dados.get("descricao");

        if (Regras.verificaIntegridade(gradDescricao)) {
            GerenciaConexao gerenciaConexao;
            try {
                gerenciaConexao = new GerenciaConexao();
                try {
                    gerenciaConexao.getConexao().iniciarTransacao();
                    Graduacao graduacao = new Graduacao(gradDescricao);
                    if (graduacao.gravar(gerenciaConexao.getConexao())) {
                        resposta.put("status", true);
                        resposta.put("mensagem", "Graduação inserida com sucesso!");
                        gerenciaConexao.getConexao().commit();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.ok(resposta);
                    } else {
                        resposta.put("status", false);
                        resposta.put("mensagem", "Erro ao inserir graduação!");
                        gerenciaConexao.getConexao().rollback();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }
                } catch (Exception e) {
                    resposta.put("status", false);
                    resposta.put("mensagem", "Erro durante a inserção");
                    gerenciaConexao.getConexao().rollback();
                    gerenciaConexao.getConexao().fimTransacao();
                    gerenciaConexao.Desconectar();
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

    public ResponseEntity<Object> alterarGraduacao(Map<String, Object> dados) {
        Map<String, Object> resposta = new HashMap<>();
        int id = (int) dados.get("id");
        String gradDescricao = (String) dados.get("descricao");

        if (Regras.verificaIntegridade(id) && Regras.verificaIntegridade(gradDescricao)) {
            GerenciaConexao gerenciaConexao;
            try {
                gerenciaConexao = new GerenciaConexao();
                try {
                    gerenciaConexao.getConexao().iniciarTransacao();
                    Graduacao graduacao = new Graduacao(id, gradDescricao);
                    if (graduacao.alterar(gerenciaConexao.getConexao())) {
                        resposta.put("status", true);
                        resposta.put("mensagem", "Graduação alterada com sucesso!");
                        gerenciaConexao.getConexao().commit();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.ok(resposta);
                    } else {
                        resposta.put("status", false);
                        resposta.put("mensagem", "Erro ao alterar graduação!");
                        gerenciaConexao.getConexao().rollback();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }
                } catch (Exception e) {
                    resposta.put("status", false);
                    resposta.put("mensagem", "Erro durante a alteração");
                    gerenciaConexao.getConexao().rollback();
                    gerenciaConexao.getConexao().fimTransacao();
                    gerenciaConexao.Desconectar();
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

    public ResponseEntity<Object> excluirGraduacao(int id) {
        Map<String, Object> resposta = new HashMap<>();

        if (Regras.verificaIntegridade(id)) {
            try {
                Graduacao graduacao = new Graduacao();
                graduacao.setId(id);
                GerenciaConexao gerenciaConexao = new GerenciaConexao();
                if (graduacao.apagar(gerenciaConexao.getConexao())) {
                    resposta.put("status", true);
                    resposta.put("mensagem", "Graduação excluída com sucesso!");
                    gerenciaConexao.Desconectar();
                    return ResponseEntity.ok(resposta);
                } else {
                    resposta.put("status", false);
                    resposta.put("mensagem", "Erro ao excluir graduação!");
                    gerenciaConexao.Desconectar();
                    return ResponseEntity.badRequest().body(resposta);
                }
            } catch (Exception e) {
                resposta.put("status", false);
                resposta.put("mensagem", "Erro de conexão");
                return ResponseEntity.badRequest().body(resposta);
            }
        } else {
            resposta.put("status", false);
            resposta.put("mensagem", "ID inválido");
            return ResponseEntity.badRequest().body(resposta);
        }
    }

    public ResponseEntity<Object> buscarGraduacao(String termo) {
        Map<String, Object> resposta = new HashMap<>();
        try {
            GerenciaConexao gerenciaConexao = new GerenciaConexao();
            Graduacao graduacao = new Graduacao();
            List<Graduacao> graduacaos = graduacao.buscarTodos(gerenciaConexao.getConexao());
            gerenciaConexao.Desconectar();

            if (graduacaos != null && !graduacaos.isEmpty()) {
                resposta.put("status", true);
                resposta.put("listaGraduacao", graduacaos);
                return ResponseEntity.ok(resposta);
            } else {
                resposta.put("status", false);
                resposta.put("mensagem", "Nenhuma graduação encontrada.");
                return ResponseEntity.badRequest().body(resposta);
            }

        } catch (Exception e) {
            resposta.put("status", false);
            resposta.put("mensagem", "Erro ao buscar graduações");
            return ResponseEntity.badRequest().body(resposta);
        }
    }
}