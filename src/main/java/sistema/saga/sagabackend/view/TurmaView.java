package sistema.saga.sagabackend.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.saga.sagabackend.control.TurmaCtrl;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/turma")
public class TurmaView {

    @Autowired
    private TurmaCtrl turmaCtrl;

    @PostMapping("/gravar")
    public ResponseEntity<Object> gravar(@RequestBody Map<String, Object> dados) {
        return turmaCtrl.gravarTurma(dados);
    }

    @PutMapping("/alterar")
    public ResponseEntity<Object> alterar(@RequestBody Map<String, Object> dados) {
        return turmaCtrl.alterarTurma(dados);
    }

    @DeleteMapping("/{letra}/{serieId}/{anoLetivoId}/{profissionalRa}/{salaId}")
    public ResponseEntity<Object> excluir(@PathVariable String letra,
                                          @PathVariable int serieId,
                                          @PathVariable int anoLetivoId,
                                          @PathVariable int profissionalRa,
                                          @PathVariable int salaId) {
        return turmaCtrl.excluirTurma(letra, serieId, anoLetivoId, profissionalRa, salaId);
    }

    @GetMapping("/buscarTodos")
    public ResponseEntity<Object> buscarTodos() {
        return turmaCtrl.buscarTurmas("");
    }

    @GetMapping("/buscar/{termo}")
    public ResponseEntity<Object> buscarPorTermo(@PathVariable String termo) {
        return turmaCtrl.buscarTurmas(termo);
    }
}
