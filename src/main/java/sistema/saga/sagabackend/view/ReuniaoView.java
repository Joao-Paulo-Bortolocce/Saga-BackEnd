package sistema.saga.sagabackend.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.saga.sagabackend.control.ReuniaoCtrl;
import sistema.saga.sagabackend.control.TurmaCtrl;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/reuniao")
public class ReuniaoView {

    @Autowired
    private ReuniaoCtrl reuniaoCtrl;
    @Autowired
    private TurmaCtrl turmaCtrl;

    @PostMapping("/gravar")
    public ResponseEntity<Object> gravar(@RequestBody Map<String, Object> dados) {
        return reuniaoCtrl.gravarReuniao(dados);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> alterar(@PathVariable int id, @RequestBody Map<String, Object> dados) {
        return reuniaoCtrl.alterarReuniao(id, dados);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> excluir(@PathVariable int id) {
        return reuniaoCtrl.excluirReuniao(id);
    }

    @GetMapping("/buscarTodos")
    public ResponseEntity<Object> buscarTodos() {
        return reuniaoCtrl.buscarReunioes("");
    }

    @GetMapping("/buscar/{termo}")
    public ResponseEntity<Object> buscarPorTermo(@PathVariable String termo) {
        return reuniaoCtrl.buscarReunioes(termo);
    }

    @GetMapping("/turmas")
    public ResponseEntity<Object> buscarTurmas() {
        return turmaCtrl.buscarTurmas("");
    }
}
