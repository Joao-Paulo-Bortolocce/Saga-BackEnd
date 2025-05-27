package sistema.saga.sagabackend.control;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sistema.saga.sagabackend.model.Habilidade;
import sistema.saga.sagabackend.repository.GerenciaConexao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HabilidadeCtrl {

    public ResponseEntity<Object> gravarHab(Map<String, Object> dados) {
        Map<String, Object> resposta = new HashMap<>();
        int id = (int) dados.get("habilidades_cod");
        String descricao = (String) dados.get("habilidades_descricao");
        int idHabMat = (int) dados.get("habilidades_mat_id");
        int idHabSer = (int) dados.get("habilidades_serie_id");
        if(id >= 0 && !descricao.trim().isEmpty() && descricao != null && idHabMat >= 0 && idHabSer >= 0) {
            GerenciaConexao gerenciaConexao;
            try {
                gerenciaConexao = new GerenciaConexao();
                try{
                    gerenciaConexao.getConexao().iniciarTransacao();
                    Habilidade habilidade = new Habilidade(id, descricao, idHabMat, idHabSer);
                    if(habilidade.gravarHabilidade(gerenciaConexao.getConexao())) {
                        resposta.put("status", true);
                        resposta.put("mensagem", "Habilidade Inserida com sucesso");
                        gerenciaConexao.getConexao().commit();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.ok(resposta);
                    } else {
                        resposta.put("status", false);
                        resposta.put("mensagem", "Habilidade não foi inserida!");
                        gerenciaConexao.getConexao().rollback();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }
                } catch (Exception e) {
                    resposta.put("status", false);
                    resposta.put("mensagem", "Ocorreu um erro durante a insercao");
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
        int id = (int) dados.get("habilidades_cod");
        String descricao = (String) dados.get("habilidades_descricao");
        int idHabMat = (int) dados.get("habilidades_mat_id");
        int idHabSer = (int) dados.get("habilidades_serie_id");
        if(id >= 0 && !descricao.trim().isEmpty() && descricao != null && idHabMat >= 0 && idHabSer >= 0) {
            GerenciaConexao gerenciaConexao;
            try {
                gerenciaConexao = new GerenciaConexao();
                try{
                    gerenciaConexao.getConexao().iniciarTransacao();
                    Habilidade habilidade = new Habilidade(id, descricao, idHabMat, idHabSer);
                    if(habilidade.alterarHabilidade(gerenciaConexao.getConexao())) {
                        resposta.put("status", true);
                        resposta.put("mensagem", "Habilidade " + habilidade.getDescricao() + " alterada com sucesso");
                        gerenciaConexao.getConexao().commit();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.ok(resposta);
                    } else {
                        resposta.put("status", false);
                        resposta.put("mensagem", "Habilidade não foi alterada!");
                        gerenciaConexao.getConexao().rollback();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }
                } catch (Exception e) {
                    resposta.put("status", false);
                    resposta.put("mensagem", "Ocorreu um erro durante a alteracao");
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

    public ResponseEntity<Object> apagarHabMatSer(int codHabMat, int codHabSer) {
        Map<String, Object> resposta = new HashMap<>();
        if(codHabMat >= 0 && codHabSer >= 0) {
            try {
                Habilidade habilidade = new Habilidade(0, codHabMat, codHabSer);
                GerenciaConexao gerenciaConexao = new GerenciaConexao();
                if(habilidade.deletarHabilidade(gerenciaConexao.getConexao())) {
                    resposta.put("status", true);
                    resposta.put("mensagem", "Habilidade excluída com sucesso!");
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

    public ResponseEntity<Object> apagarHabilidade(int id) {
        Map<String, Object> resposta = new HashMap<>();
        if(id >= 0) {
            try {
                Habilidade habilidade = new Habilidade(id);
                GerenciaConexao gerenciaConexao = new GerenciaConexao();
                if(habilidade.deletarHabilidade(gerenciaConexao.getConexao())) {
                    resposta.put("status", true);
                    resposta.put("mensagem", "Habilidade excluída com sucesso!");
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

    public ResponseEntity<Object> buscarHabMatSer(int idMat, int idSer) {
        Map<String, Object> resposta = new HashMap<>();
        GerenciaConexao gerenciaConexao = new GerenciaConexao();
        try {
            Habilidade habilidade = new Habilidade(0, idMat, idSer);
            List<Habilidade> habilidades = habilidade.buscarHabiliMatSer(gerenciaConexao.getConexao());
            if (habilidades != null && habilidades.size() > 0) {
                resposta.put("status", true);
                resposta.put("listaDeHabilidades", habilidades);
                gerenciaConexao.Desconectar();
                return ResponseEntity.ok(resposta);
            } else {
                resposta.put("status", false);
                resposta.put("mensagem", "Não existem habilidades cadastradas com os filtros selecionados");
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

    public ResponseEntity<Object> buscarHabMat(int idMat) {
        Map<String, Object> resposta = new HashMap<>();
        GerenciaConexao gerenciaConexao = new GerenciaConexao();
        try {
            Habilidade habilidade = new Habilidade(0, idMat);
            List<Habilidade> habilidades = habilidade.buscarHabiliMat(gerenciaConexao.getConexao());
            if (habilidades != null && habilidades.size() > 0) {
                resposta.put("status", true);
                resposta.put("listaDeHabilidades", habilidades);
                gerenciaConexao.Desconectar();
                return ResponseEntity.ok(resposta);
            } else {
                resposta.put("status", false);
                resposta.put("mensagem", "Não existem habilidades cadastradas com os filtros selecionados");
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

    public ResponseEntity<Object> buscarHabSer(int idSer) {
        Map<String, Object> resposta = new HashMap<>();
        GerenciaConexao gerenciaConexao = new GerenciaConexao();
        try {
            Habilidade habilidade = new Habilidade(0, 0, idSer);
            List<Habilidade> habilidades = habilidade.buscarHabiliSer(gerenciaConexao.getConexao());
            if (habilidades != null && habilidades.size() > 0) {
                resposta.put("status", true);
                resposta.put("listaDeHabilidades", habilidades);
                gerenciaConexao.Desconectar();
                return ResponseEntity.ok(resposta);
            } else {
                resposta.put("status", false);
                resposta.put("mensagem", "Não existem habilidades cadastradas com os filtros selecionados");
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
}
