package sistema.saga.sagabackend.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.saga.sagabackend.control.GraduacaoCtrl;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/graduacao")
public class GraduacaoView {
    @Autowired
    private GraduacaoCtrl graduacaoCtrl;

    @PostMapping
    public ResponseEntity<Object> gravar(@RequestBody Map<String, Object> dados) {
        return graduacaoCtrl.gravarGraduacao(dados);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> alterar(@RequestBody Map<String, Object> dados) {
        return graduacaoCtrl.alterarGraduacao(dados);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> excluir(@PathVariable int id) {
        return graduacaoCtrl.excluirGraduacao(id);
    }

    @GetMapping(value = "/buscarTodos")
    public ResponseEntity<Object> buscarTodos() {
        return graduacaoCtrl.buscarGraduacao("");
    }

    @GetMapping("/{termo}")
    public ResponseEntity<Object> buscarPorTermo(@PathVariable String termo) {
        return graduacaoCtrl.buscarGraduacao(termo);
    }
}
