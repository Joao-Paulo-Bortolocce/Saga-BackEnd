package sistema.saga.sagabackend.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.saga.sagabackend.controller.MateriaCtrl;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("materia")
public class MateriaView {
    @Autowired
    private MateriaCtrl materiaCtrl;

    @PostMapping("/gravar")
    public ResponseEntity<Object> gravar(@RequestBody Map<String, Object> dados) {
        return materiaCtrl.gravarMateria(dados);
    }

    @PutMapping("/alterar")
    public ResponseEntity<Object> alterar(@RequestBody Map<String, Object> dados) {
        return materiaCtrl.alterarMateria(dados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> consultarMateria(@PathVariable(name = "id") Long id) {
        return materiaCtrl.buscarMateria(id);
    }

    @GetMapping("/buscarTodas")
    public ResponseEntity<Object> consultarTodas() {
        return materiaCtrl.buscarTodas();
    }

    @DeleteMapping("/apagar/{id}")
    public ResponseEntity<Object> apagar(@PathVariable(name = "id") Long id) {
        return materiaCtrl.apagarMateria(id);
    }
}
