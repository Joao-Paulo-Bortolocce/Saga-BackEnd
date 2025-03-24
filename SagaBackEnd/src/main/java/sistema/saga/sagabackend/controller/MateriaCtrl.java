package sistema.saga.sagabackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sistema.saga.sagabackend.dto.MateriaDTO;
import sistema.saga.sagabackend.model.Materia;
import sistema.saga.sagabackend.service.MateriaService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class MateriaCtrl {
    private final MateriaService materiaService;

    @Autowired
    public MateriaCtrl(MateriaService materiaService) {
        this.materiaService = materiaService;
    }

    public void gravarMateria(MateriaDTO materiaDTO) {
        if(verificaIntegridade(materiaDTO.buscarId()) && verificaIntegridade(materiaDTO.buscarNome())) {
            Materia materia = new Materia(
                    materiaDTO.getId(),
                    materiaDTO.getNome()
            );
            try{
                materiaService.salvarMateria(materia);
            }catch (Exception e) {
                System.out.println("Erro ao salvar");
            }
        } else {
            System.out.println("erro na checagem de integridade de matéria: salvar");
        }
    }

    public ResponseEntity<Object> alterarMateria(MateriaDTO materiaDTO) {
        Map<String, Object> resposta = new HashMap<>();

        try {
            if (verificaIntegridade(materiaDTO.buscarId()) && verificaIntegridade(materiaDTO.buscarNome())) {
                if (materiaService.existe(materiaDTO.getId())) {
                    Materia materia = new Materia(
                            materiaDTO.getId(),
                            materiaDTO.getNome()
                    );
                    materiaService.alterarMateria(materia);
                    esposta.put("status", true);
                    resposta.put("mensagem", "Matéria alterada com sucesso");
                    return ResponseEntity.ok(resposta);
                } else {
                    resposta.put("status", false);
                    resposta.put("mensagem", "Matéria que deseja alterar não está cadastrada");
                    return ResponseEntity.badRequest().body(resposta);
                }
            } else {
                resposta.put("status", false);
                resposta.put("mensagem", "Informe corretamente os dados da matéria que deseja excluir!");
                return ResponseEntity.badRequest().body(resposta);
            }
        } catch (Exception e) {
            resposta.put("status", false);
            resposta.put("Mensagem", "Ocorreu um erro de conexao");
            return ResponseEntity.badRequest().body(resposta);
        }
    }

    private boolean verificaIntegridade(int elemento) {
        return elemento != 0
    }
    private boolean verificaIntegridade(String elemento) {
        return elemento != null && !elemento.trim().isEmpty();
    }
}