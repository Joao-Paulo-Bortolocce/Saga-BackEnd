package sistema.saga.sagabackend.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.saga.sagabackend.controller.SerieCtrl;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/serie")
public class SerieView {

    @Autowired
    private SerieCtrl serieCtrl;

    @PostMapping(value = "/gravar")
    public ResponseEntity<Object> gravar(@RequestBody Map<String, Object> dados) {
        return serieCtrl.gravarSerie(dados);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> alterar(@PathVariable int id, @RequestBody Map<String, Object> dados) {
        return serieCtrl.alterarSerie(id, dados);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> patch(@PathVariable int id, @RequestBody Map<String, Object> dados) {
        return serieCtrl.alterarSerie(id, dados);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> excluir(@PathVariable int id) {
        return serieCtrl.excluirSerie(id);
    }

    @GetMapping(value = "buscarTodos")
    public ResponseEntity<Object> buscarTodos() {
        return serieCtrl.buscarSeries("");
    }

    @GetMapping("/buscar/{termo}")
    public ResponseEntity<Object> buscarPorTermo(@PathVariable String termo) {
        return serieCtrl.buscarSeries(termo);
    }
}
