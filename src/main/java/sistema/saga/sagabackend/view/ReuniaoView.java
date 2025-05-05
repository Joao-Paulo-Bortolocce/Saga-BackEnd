package sistema.saga.sagabackend.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.saga.sagabackend.controller.ReuniaoCtrl;
import sistema.saga.sagabackend.model.Turma;
import sistema.saga.sagabackend.repository.GerenciaConexao;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/reuniao")
public class ReuniaoView {

    @Autowired
    private ReuniaoCtrl reuniaoCtrl;

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
        GerenciaConexao gc = new GerenciaConexao();
        List<Turma> turmas = Turma.buscarTodos(gc.getConexao());
        gc.Desconectar();
        return ResponseEntity.ok(turmas);
    }
}
