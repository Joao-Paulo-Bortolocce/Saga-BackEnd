package sistema.saga.sagabackend.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.saga.sagabackend.control.HabilidadesDaFichaCtrl;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("habilidades-da-ficha")
public class HabilidadesDaFichaView {

    @Autowired
    HabilidadesDaFichaCtrl habilidadesDaFichaCtrl;

    @PostMapping
    public ResponseEntity<Object> gravar(@RequestBody Map<String, Object> dados) {
        return habilidadesDaFichaCtrl.gravarHabilidadesDaFicha(dados);
    }

    @PutMapping
    public ResponseEntity<Object> alterar(@RequestBody Map<String, Object> dados) {
        return habilidadesDaFichaCtrl.alterar(dados);
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Object> apagar(@PathVariable(name = "id") int id) {
//        return habilidadesDaFichaCtrl.apagar(id);
//    }

    @DeleteMapping("/{idHab}/{idFicha}")
    public ResponseEntity<Object> apagarHabFicha(@PathVariable(name = "idHab") int idHab, @PathVariable(name = "idFicha") int idFicha){
        return habilidadesDaFichaCtrl.apagar(idHab, idFicha);
    }

    @GetMapping
    public ResponseEntity<Object> buscarTodas() {
        return habilidadesDaFichaCtrl.buscarTodas();
    }

    @GetMapping("/{idFicha}")
    public ResponseEntity<Object> buscarHabilidadesDaFicha(@PathVariable(name = "idFicha") int idFicha) {
        return habilidadesDaFichaCtrl.buscarHabDaFicha(idFicha);
    }
}
