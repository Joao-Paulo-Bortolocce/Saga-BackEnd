package sistema.saga.sagabackend.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.saga.sagabackend.controller.TurmaCtrl;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/turma")
public class TurmaView {

    @Autowired
    private TurmaCtrl turmaCtrl;

    @PostMapping(value = "/gravar")
    public ResponseEntity<Object> gravar(@RequestBody Map<String, Object> dados) {
        return turmaCtrl.gravarTurma(dados);
    }

    @PutMapping("/{letra}/{serieId}/{anoLetivoId}")
    public ResponseEntity<Object> alterar(@PathVariable String letra,
                                          @PathVariable int serieId,
                                          @PathVariable int anoLetivoId,
                                          @RequestBody Map<String, Object> dados) {
        return turmaCtrl.alterarTurma(letra,serieId, anoLetivoId, dados);
    }

    @DeleteMapping("/{letra}/{serieId}/{anoLetivoId}")
    public ResponseEntity<Object> excluir(@PathVariable String letra, @PathVariable int serieId, @PathVariable int anoLetivoId) {
        return turmaCtrl.excluirTurma(letra,serieId,anoLetivoId);
    }

    @GetMapping("/buscarTodos")
    public ResponseEntity<Object> buscarTodos() {
        return turmaCtrl.buscarTurmas("");
    }

    @GetMapping("/{termo}")
    public ResponseEntity<Object> buscarPorTermo(@PathVariable String termo) {
        return turmaCtrl.buscarTurmas(termo);
    }

}