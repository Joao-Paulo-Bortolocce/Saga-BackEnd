package sistema.saga.sagabackend.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.saga.sagabackend.control.AnoLetivoCtrl;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/anoletivo")
public class AnoLetivoView {
    @Autowired
    private AnoLetivoCtrl anoLetivoCtrl;

    @PostMapping(value = "/gravar")
    public ResponseEntity<Object> gravar(@RequestBody Map<String, Object> dados) {
        return anoLetivoCtrl.gravarAno(dados);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> alterar(@PathVariable int id, @RequestBody Map<String, Object> dados) {
        return anoLetivoCtrl.alterarAno(id, dados);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> patch(@PathVariable int id, @RequestBody Map<String, Object> dados) {
        return anoLetivoCtrl.alterarAno(id, dados);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> apagar(@PathVariable int id) {
        return anoLetivoCtrl.apagarAno(id);
    }

    @GetMapping(value = "buscarTodos")
    public ResponseEntity<Object> buscarTodos() {
        return anoLetivoCtrl.buscarAnos("");
    }

    @GetMapping("/{termo}")
    public ResponseEntity<Object> buscarPorTermo(@PathVariable String termo) {
        return anoLetivoCtrl.buscarAnos(termo);
    }
}
