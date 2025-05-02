package sistema.saga.sagabackend.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.saga.sagabackend.controller.SalaCtrl;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/sala")
public class SalaView {
    @Autowired
    private SalaCtrl salaCtrl;

    @PostMapping(value = "/gravar")
    public ResponseEntity<Object> gravar(@RequestBody Map<String, Object> dados) {
        return salaCtrl.gravarSala(dados);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> alterar(@PathVariable int id, @RequestBody Map<String, Object> dados) {
        return salaCtrl.alterarSala(id, dados);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> patch(@PathVariable int id, @RequestBody Map<String, Object> dados) {
        return salaCtrl.alterarSala(id, dados);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> excluir(@PathVariable int id) {
        return salaCtrl.excluirSala(id);
    }

    @GetMapping(value = "buscarTodos")
    public ResponseEntity<Object> buscarTodos() {
        return salaCtrl.buscarSala("");
    }

    @GetMapping("/{termo}")
    public ResponseEntity<Object> buscarPorTermo(@PathVariable String termo) {
        return salaCtrl.buscarSala(termo);
    }
}