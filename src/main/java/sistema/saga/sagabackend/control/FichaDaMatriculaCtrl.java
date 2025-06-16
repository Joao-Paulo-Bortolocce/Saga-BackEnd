package sistema.saga.sagabackend.control;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sistema.saga.sagabackend.model.AvaliacaoDaMatricula;
import sistema.saga.sagabackend.model.Ficha;
import sistema.saga.sagabackend.model.FichaDaMatricula;
import sistema.saga.sagabackend.model.Matricula;
import sistema.saga.sagabackend.repository.GerenciaConexao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FichaDaMatriculaCtrl {
   
    public ResponseEntity<Object> buscarTodas(boolean valid) {
        Map<String, Object> resposta = new HashMap<>();
        GerenciaConexao gerenciaConexao = new GerenciaConexao();
        try {
            FichaDaMatricula ficha = new FichaDaMatricula();
            List<FichaDaMatricula> fichas = ficha.buscarTodas(valid,gerenciaConexao.getConexao());
            if(fichas != null && fichas.size() > 0) {
                for (int i = 0; i < fichas.size(); i++) {
                    Map<String, Object> aluno= new HashMap<>();
                    Map<String, Object> ano= new HashMap<>();
                    Map<String, Object> serie= new HashMap<>();
                    Map<String, Object> turma= new HashMap<>();
                    Matricula matricula = new Matricula(fichas.get(i).getMatricula().getId());
                    matricula=matricula.buscaMatricula(gerenciaConexao.getConexao(),matricula,aluno,ano,serie,turma);
                    matricula.setTurma(Regras.HashToTurma(turma));
                    matricula.setSerie(Regras.HashToSerie(serie));
                    matricula.setAluno(Regras.HashToAluno(aluno));
                    matricula.setAnoLetivo(Regras.HashToAnoLetivo(ano));
                    fichas.get(i).setMatricula(matricula);
                    fichas.get(i).setFicha(fichas.get(i).getFicha().buscaFicha(gerenciaConexao.getConexao()));
                }
                resposta.put("status", true);
                resposta.put("listaDeFichaDaMatriculas", fichas);
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


    public ResponseEntity<Object> buscarTodasAvaliacoesDafichaDaMatricula(int matId, int fichaId) {
        Map<String, Object> resposta = new HashMap<>();
        GerenciaConexao gerenciaConexao = new GerenciaConexao();
        try {
            AvaliacaoDaMatricula avaliacaoDaMatricula = new AvaliacaoDaMatricula();
            List<AvaliacaoDaMatricula> avaliacaoDaMatriculas = avaliacaoDaMatricula.buscarTodasAvaliacoesDafichaDaMatricula(gerenciaConexao.getConexao(),matId,fichaId);
            if(avaliacaoDaMatriculas != null && avaliacaoDaMatriculas.size() > 0) {
                resposta.put("status", true);
                resposta.put("listaDeAvaliacoes", avaliacaoDaMatriculas);
                gerenciaConexao.Desconectar();
                return ResponseEntity.ok(resposta);
            } else {
                resposta.put("status", false);
                resposta.put("mensagem", "Não existem avaliacoes cadastradas");
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
}
