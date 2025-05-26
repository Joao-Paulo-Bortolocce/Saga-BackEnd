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

    @PostMapping("")
    public ResponseEntity<Object> gravar(@RequestBody Map<String, Object> dados) {
        return habilidadeCtrl.gravarHab(dados);
    }

    @PutMapping("")
    public ResponseEntity<Object> alterar(@RequestBody Map<String, Object> dados) {
        return habilidadeCtrl.alterar(dados);
    }

    @DeleteMapping("/apagar/{id}/{idHabMat}")
    public ResponseEntity<Object> apagar(@PathVariable(name = "id") int id, @PathVariable(name = "idHabMat") int idHabMat) {
        return habilidadeCtrl.apagar(id, idHabMat);
    }

    @GetMapping("buscarTodas/{idMat}/{idSerie}")
    public ResponseEntity<Object> getHabilidadeMatSerie(@PathVariable("idMat") int idMat, @PathVariable("idSerie") int idSerie) {
        return habilidadeCtrl.buscarHabMatSer(idMat, idSerie);
    }

    @GetMapping("buscarTodas/{idMat}")
    public ResponseEntity<Object> getHabilidadeMat(@PathVariable("idMat") int idMat) {
        return habilidadeCtrl.buscarHabMat(idMat);
    }

}
