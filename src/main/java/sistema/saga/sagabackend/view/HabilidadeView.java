package sistema.saga.sagabackend.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.saga.sagabackend.control.HabilidadeCtrl;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("habilidade")
public class HabilidadeView {

    @Autowired
    private HabilidadeCtrl habilidadeCtrl;

    @PostMapping
    public ResponseEntity<Object> gravar(@RequestBody Map<String, Object> dados) {
        return habilidadeCtrl.gravarHab(dados);
    }

    @PutMapping
    public ResponseEntity<Object> alterar(@RequestBody Map<String, Object> dados) {
        return habilidadeCtrl.alterar(dados);
    }

    @DeleteMapping("apagar/{id}")
    public ResponseEntity<Object> apagarHab(@PathVariable(name="id") int id) {
        return habilidadeCtrl.apagarHabilidade(id);
    }

    @DeleteMapping("/apagar/{idHabMat}/{idHabSer}")
    public ResponseEntity<Object> apagarHabMatSer(@PathVariable(name = "idHabMat") int idHabMat, @PathVariable(name = "idHabSer") int idHabSer) {
        return habilidadeCtrl.apagarHabMatSer(idHabMat, idHabSer);
    }

    @GetMapping("buscarTodas/{idMat}/{idSerie}")
    public ResponseEntity<Object> getHabilidadeMatSerie(@PathVariable("idMat") int idMat, @PathVariable("idSerie") int idSerie) {
        return habilidadeCtrl.buscarHabMatSer(idMat, idSerie);
    }

    @GetMapping("buscarTodasMat/{idMat}")
    public ResponseEntity<Object> getHabilidadeMat(@PathVariable("idMat") int idMat) {
        return habilidadeCtrl.buscarHabMat(idMat);
    }

    @GetMapping("buscarTodasSer/{idSer}")
    public ResponseEntity<Object> getHabilidadeSer(@PathVariable("idSer") int idSer) {
        return habilidadeCtrl.buscarHabSer(idSer);
    }
}
