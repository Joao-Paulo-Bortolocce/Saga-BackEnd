package sistema.saga.sagabackend.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.saga.sagabackend.control.FichaCtrl;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("ficha")
public class FichaView {

    @Autowired
    FichaCtrl fichaCtrl;

    @PostMapping
    public ResponseEntity<Object> gravar(@RequestBody Map<String, Object> dados) {
        return fichaCtrl.gravarFicha(dados);
    }

    @PutMapping
    public ResponseEntity<Object> alterar(@RequestBody Map<String, Object> dados) {
        return fichaCtrl.alterar(dados);
    }

    @DeleteMapping("/apagar/{id}")
    public ResponseEntity<Object> apagar(@PathVariable(name = "id") int id) {
        return fichaCtrl.apagar(id);
    }

    @GetMapping
    public ResponseEntity<Object> buscarTodas() {
        return fichaCtrl.buscarTodas();
    }
}
