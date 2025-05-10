package sistema.saga.sagabackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sistema.saga.sagabackend.model.Avaliacao;
import sistema.saga.sagabackend.repository.GerenciaConexao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AvaliacaoCtrl {

    public ResponseEntity<Object> gravarAvaliacao(Map<String, Object> dados) {
        Map<String, Object> resposta = new HashMap<>();
        Integer avaliacao_cod = (Integer) dados.get("avaliacao_cod");
        Integer avaliacao_aluno_ra = (Integer) dados.get("avaliacao_aluno_cod");
        Integer avaliacao_serie_cod = (Integer) dados.get("avaliacao_serie_cod");
        Integer avaliacao_materia_cod = (Integer) dados.get("avaliacao_materia_cod");
        Integer avaliacao_habilidade_cod = (Integer) dados.get("avaliacao_habilidade_cod");
        String descricao = (String) dados.get("avaliacao_descricao");

        if(avaliacao_cod >= 0 && avaliacao_aluno_ra >= 0 && avaliacao_serie_cod >= 0 && avaliacao_materia_cod >= 0 && avaliacao_habilidade_cod >= 0 && !descricao.trim().isEmpty() && descricao != null) {
            GerenciaConexao gerenciaConexao;
            try {
                gerenciaConexao = new GerenciaConexao();
                try{
                    gerenciaConexao.getConexao().iniciarTransacao();
                    Avaliacao avaliacao = new Avaliacao(avaliacao_cod, avaliacao_aluno_ra, avaliacao_serie_cod, avaliacao_materia_cod, avaliacao_habilidade_cod, descricao);
                    if(avaliacao.gravarAvaliacao(gerenciaConexao.getConexao())) {
                        resposta.put("status", true);
                        resposta.put("mensagem", "Avaliacao registrada com sucesso");
                        gerenciaConexao.getConexao().commit();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.ok(resposta);
                    } else {
                        resposta.put("status", false);
                        resposta.put("mensagem", "Avaliacao não foi registrada!");
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

    public ResponseEntity<Object> buscarAvaliacoesPorAlunoESerie(Integer alunoRa, Integer serieId) {
        Map<String, Object> resposta = new HashMap<>();
        GerenciaConexao gerenciaConexao = new GerenciaConexao();

        try {
            Avaliacao avaliacaoFiltro = new Avaliacao();
            avaliacaoFiltro.setAvaliacao_aluno_cod(alunoRa);
            avaliacaoFiltro.setAvaliacao_serie_cod(serieId);

            List<Avaliacao> avaliacoes = avaliacaoFiltro.getAlunoESerie(avaliacaoFiltro,gerenciaConexao.getConexao());

            if (avaliacoes != null && !avaliacoes.isEmpty()) {
                List<Map<String, Object>> avaliacoesFormatadas = new ArrayList<>();

                for (Avaliacao av : avaliacoes) {
                    Map<String, Object> item = new HashMap<>();
                    item.put("materiaId", av.getAvaliacao_materia_cod());
                    item.put("habilidadeCod", av.getAvaliacao_habilidade_cod());
                    item.put("habilidade_descricao", av.getAvaliacao_descricao());
                    avaliacoesFormatadas.add(item);
                }

                resposta.put("alunoRa", alunoRa);
                resposta.put("serieId", serieId);
                resposta.put("avaliacoes", avaliacoesFormatadas);
                resposta.put("status", true);
                gerenciaConexao.Desconectar();
                return ResponseEntity.ok(resposta);
            } else {
                resposta.put("status", false);
                resposta.put("mensagem", "Não foram encontradas avaliações com os filtros fornecidos.");
                gerenciaConexao.Desconectar();
                return ResponseEntity.badRequest().body(resposta);
            }

        } catch (Exception e) {
            resposta.put("status", false);
            resposta.put("mensagem", "Erro ao buscar avaliações.");
            gerenciaConexao.Desconectar();
            return ResponseEntity.badRequest().body(resposta);
        }
    }

    public ResponseEntity<Object> buscarTodas() {
        GerenciaConexao gerenciaConexao = new GerenciaConexao();
        Map<String, Object> resposta = new HashMap<>();

        try {
            Avaliacao avaliacao = new Avaliacao();
            List<Avaliacao> avaliacoes = avaliacao.getAll(gerenciaConexao.getConexao());

            if (avaliacoes != null && !avaliacoes.isEmpty()) {
                // Mapa agrupando por aluno + série
                Map<String, Map<String, Object>> agrupados = new HashMap<>();

                for (Avaliacao av : avaliacoes) {
                    String chave = av.getAvaliacao_aluno_cod() + "-" + av.getAvaliacao_serie_cod();

                    if (!agrupados.containsKey(chave)) {
                        Map<String, Object> agrupado = new HashMap<>();
                        agrupado.put("alunoRa", av.getAvaliacao_aluno_cod());
                        agrupado.put("serieId", av.getAvaliacao_serie_cod());
                        agrupado.put("avaliacoes", new ArrayList<Map<String, Object>>());
                        agrupado.put("status", true);
                        agrupados.put(chave, agrupado);
                    }

                    Map<String, Object> avaliacaoItem = new HashMap<>();
                    avaliacaoItem.put("habilidadeCod", av.getAvaliacao_habilidade_cod());
                    avaliacaoItem.put("materiaId", av.getAvaliacao_materia_cod());
                    avaliacaoItem.put("habilidade_descricao", av.getAvaliacao_descricao());

                    List<Map<String, Object>> avaliacoesLista =
                            (List<Map<String, Object>>) agrupados.get(chave).get("avaliacoes");
                    avaliacoesLista.add(avaliacaoItem);
                }

                // Converter o map para uma lista de resultados
                List<Map<String, Object>> resultadoFinal = new ArrayList<>(agrupados.values());

                gerenciaConexao.Desconectar();
                return ResponseEntity.ok(resultadoFinal);
            } else {
                resposta.put("status", false);
                resposta.put("mensagem", "Não foram encontradas avaliações.");
                gerenciaConexao.Desconectar();
                return ResponseEntity.badRequest().body(resposta);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resposta.put("status", false);
            resposta.put("mensagem", "Erro ao buscar avaliações.");
            gerenciaConexao.Desconectar();
            return ResponseEntity.badRequest().body(resposta);
        }
    }


}


