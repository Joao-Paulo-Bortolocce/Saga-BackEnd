package sistema.saga.sagabackend.control;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sistema.saga.sagabackend.model.Materia;
import sistema.saga.sagabackend.repository.GerenciaConexao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MateriaCtrl {

    public ResponseEntity<Object> gravarMateria(Map<String, Object> dados) {
        Map<String, Object> resposta = new HashMap<>();
        Long id = (Long) dados.get("materia_id");
        String nome = (String) dados.get("materia_nome");
        int carga = (int) dados.get("materia_carga");
        if(id >= 0 && nome != null && !nome.trim().isEmpty() && carga > 0) {
            GerenciaConexao gerenciaConexao;
            try {
                gerenciaConexao = new GerenciaConexao();
                try{
                    gerenciaConexao.getConexao().iniciarTransacao();
                    Materia materia = new Materia(id, nome, carga);
                    if(materia.gravar(gerenciaConexao.getConexao())) {
                        resposta.put("status", true);
                        resposta.put("mensagem", "Matéria Inserida com sucesso");
                        gerenciaConexao.getConexao().commit();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.ok(resposta);
                    } else {
                        resposta.put("status", false);
                        resposta.put("mensagem", "Matéria não foi inserida!");
                        gerenciaConexao.getConexao().rollback();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }
                }catch (Exception e) {
                    resposta.put("status", false);
                    resposta.put("mensagem", "Ocorreu um erro durante a insercao");
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

    public ResponseEntity<Object> alterarMateria(Map<String, Object> dados) {
        Map<String, Object> resposta = new HashMap<>();
        Long id = (Long) dados.get("materia_id");
        String nome = (String) dados.get("materia_nome");
        int carga = (int) dados.get("materia_carga");

        if(id >= 0 && nome != null && !nome.trim().isEmpty() && carga > 0) {
            GerenciaConexao gerenciaConexao;
            try {
                gerenciaConexao = new GerenciaConexao();
                try {
                    gerenciaConexao.getConexao().iniciarTransacao();
                    Materia materia = new Materia(id, nome, carga);
                    if(materia.alterar(gerenciaConexao.getConexao())) {
                        resposta.put("status", true);
                        resposta.put("mensagem", "Matéria: " + materia.getNome() + "alterada com sucesso!");
                        gerenciaConexao.getConexao().commit();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();

                        return ResponseEntity.ok(resposta);
                    } else {
                        resposta.put("status", false);
                        resposta.put("mensagem", "Matéria não foi alterada!");
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

    public ResponseEntity<Object> buscarMateria(Long id) {
        Map<String, Object> resposta = new HashMap<>();
        GerenciaConexao gerenciaConexao = new GerenciaConexao();
        try {
            Materia materia = new Materia(id);
            if(materia != null) {
                resposta.put("status", true);
                resposta.put("Materia", materia);
                gerenciaConexao.Desconectar();

                return ResponseEntity.ok(resposta);
            } else {
                resposta.put("status", false);
                resposta.put("mensagem", "Não existem matérias cadastradas");
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

    public ResponseEntity<Object> buscarTodas() {
        Map<String, Object> resposta = new HashMap<>();
        GerenciaConexao gerenciaConexao = new GerenciaConexao();
        try {
            Materia materia = new Materia();
            List<Materia> materias = materia.buscarMaterias(gerenciaConexao.getConexao());
            if(materias != null && materias.size() > 0) {
                resposta.put("status", true);
                resposta.put("listaDeMaterias", materias);
                gerenciaConexao.Desconectar();
                return ResponseEntity.ok(resposta);
            } else {
                resposta.put("status", false);
                resposta.put("mensagem", "Não existem matérias cadastradas");
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

    public ResponseEntity<Object> apagarMateria(Long id) {
        Map<String, Object> resposta = new HashMap<>();
        if(id >= 0) {
            try {
                Materia materia = new Materia(id);
                GerenciaConexao gerenciaConexao = new GerenciaConexao();
                if(materia.apagar(gerenciaConexao.getConexao())) {
                    resposta.put("status", true);
                    resposta.put("mensagem", "Matéria excluída com sucesso!");
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
