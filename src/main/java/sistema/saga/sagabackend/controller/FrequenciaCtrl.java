package sistema.saga.sagabackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sistema.saga.sagabackend.model.*;
import sistema.saga.sagabackend.repository.GerenciaConexao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FrequenciaCtrl {

    private final Frequencia frequencia;

    public FrequenciaCtrl(Frequencia frequencia) {
        this.frequencia = frequencia;
    }

    public ResponseEntity<Object> gravarFrequencia(Map<String, Object> dados) {
        Map<String, Object> resposta = new HashMap<>();

        try {
            int id = (int) dados.get("id");
            String dataStr = (String) dados.get("data");
            boolean presente = Boolean.parseBoolean(dados.get("presente").toString());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate data = LocalDate.parse(dataStr, formatter);

            if (Regras.verificaIntegridade(id) && Regras.verificaIntegridade(data)) {
                GerenciaConexao gerenciaConexao = new GerenciaConexao();
                try {
                    gerenciaConexao.getConexao().iniciarTransacao();

                    Matricula matricula = new Matricula(id);
                    Map<String, Object> aluno = new HashMap<>();
                    Map<String, Object> ano = new HashMap<>();
                    Map<String, Object> serie = new HashMap<>();
                    Map<String, Object> turma = new HashMap<>();
                    matricula = matricula.buscaMatricula(gerenciaConexao.getConexao(), matricula, aluno, ano, serie, turma);

                    if (matricula == null) {
                        resposta.put("status", false);
                        resposta.put("mensagem", "O aluno informado não está cadastrado.");
                        gerenciaConexao.getConexao().rollback();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }
                    else{
                        matricula.setAluno(Regras.HashToAluno(aluno));
                        matricula.setAnoLetivo(Regras.HashToAnoLetivo(ano));
                        matricula.setSerie(Regras.HashToSerie(serie));
                        matricula.setTurma(Regras.HashToTurma(turma));
                    }
                    Frequencia freq = new Frequencia(matricula, presente, data);

                    if (freq.gravar(gerenciaConexao.getConexao())) {
                        resposta.put("status", true);
                        resposta.put("mensagem", "Frequência registrada com sucesso.");
                        gerenciaConexao.getConexao().commit();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.ok(resposta);
                    } else {
                        resposta.put("status", false);
                        resposta.put("mensagem", "Erro ao registrar frequência.");
                        gerenciaConexao.getConexao().rollback();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }
                } catch (Exception e) {
                    resposta.put("status", false);
                    resposta.put("mensagem", "Erro durante o processo de gravação.");
                    gerenciaConexao.getConexao().rollback();
                    gerenciaConexao.getConexao().fimTransacao();
                    gerenciaConexao.Desconectar();
                    return ResponseEntity.badRequest().body(resposta);
                }
            } else {
                resposta.put("status", false);
                resposta.put("mensagem", "Dados inválidos.");
                return ResponseEntity.badRequest().body(resposta);
            }
        } catch (Exception e) {
            resposta.put("status", false);
            resposta.put("mensagem", "Erro inesperado.");
            return ResponseEntity.badRequest().body(resposta);
        }
    }


    public ResponseEntity<Object> buscarFreqAluno(int id) {
        Map<String, Object> resposta = new HashMap<>();
        try {
            GerenciaConexao gc = new GerenciaConexao();

            Matricula matricula = new Matricula(id);
            Map<String, Object> aluno = new HashMap<>();
            Map<String, Object> ano = new HashMap<>();
            Map<String, Object> serie = new HashMap<>();
            Map<String, Object> turma = new HashMap<>();
            matricula = matricula.buscaMatricula(gc.getConexao(), matricula, aluno, ano, serie, turma);

            if (matricula == null) {
                resposta.put("status", false);
                resposta.put("mensagem", "O aluno informado não está cadastrado.");
                gc.getConexao().rollback();
                gc.getConexao().fimTransacao();
                gc.Desconectar();
                return ResponseEntity.badRequest().body(resposta);
            }
            else{
                matricula.setAluno(Regras.HashToAluno(aluno));
                matricula.setAnoLetivo(Regras.HashToAnoLetivo(ano));
                matricula.setSerie(Regras.HashToSerie(serie));
                matricula.setTurma(Regras.HashToTurma(turma));
            }

            Frequencia freq = new Frequencia();
            freq.setMatricula(matricula);
            List<Frequencia> frequencias = new ArrayList<>();
            frequencias = freq.buscar(gc.getConexao());
            gc.Desconectar();

            if (frequencias != null && !frequencias.isEmpty()) {
                resposta.put("status", true);
                resposta.put("frequencias", frequencias);
                return ResponseEntity.ok(resposta);
            }
            else {
                resposta.put("status", false);
                resposta.put("mensagem", "Nenhuma frequencia encontrada.");
                return ResponseEntity.badRequest().body(resposta);
            }

        } catch (Exception e) {
            resposta.put("status", false);
            resposta.put("mensagem", "Erro ao buscar frequencias");
            return ResponseEntity.badRequest().body(resposta);
        }
    }
}
