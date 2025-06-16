package sistema.saga.sagabackend.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.saga.sagabackend.control.FichaDaMatriculaCtrl;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("fichaDaMatricula")
public class FichaDaMatriculaView {

    @Autowired
    FichaDaMatriculaCtrl fichaDaMatriculaCtrl;

//    @PostMapping
//    public ResponseEntity<Object> gravar(@RequestBody Map<String, Object> dados) {
//        return fichaDaMatriculaCtrl.gravar(dados);
//    }
//
//    @PutMapping
//    public ResponseEntity<Object> alterar(@RequestBody Map<String, Object> dados) {
//        return fichaDaMatriculaCtrl.alterar(dados);
//    }
//
//    @DeleteMapping("/apagar/{id}")
//    public ResponseEntity<Object> apagar(@PathVariable(name = "id") int id) {
//        return fichaDaMatriculaCtrl.apagar(id);
//    }

    @GetMapping
    public ResponseEntity<Object> buscarTodas(@RequestParam(name = "validadas", required = false) boolean valid) {
        return fichaDaMatriculaCtrl.buscarTodas(valid);
    }

    @GetMapping("/buscaAvaliacoes/{matId}/{fichaId}")
    public ResponseEntity<?> buscarTodas(
            @PathVariable("matId") int matId,
            @PathVariable("fichaId") int fichaId
    ) {
        return fichaDaMatriculaCtrl.buscarTodasAvaliacoesDafichaDaMatricula(matId, fichaId);
    }

}
