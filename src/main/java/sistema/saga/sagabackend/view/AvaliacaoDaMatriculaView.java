package sistema.saga.sagabackend.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.saga.sagabackend.control.AvaliacaoDaMatriculaCtrl;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "avaliacaodamatricula")
public class AvaliacaoDaMatriculaView {

    @Autowired
    AvaliacaoDaMatriculaCtrl avaliacaoDaMatriculaCtrl;

    @PostMapping
    public ResponseEntity<Object> gravarAvaliacaoDaMatricula(@RequestBody Map<String, Object> dados) {
        return avaliacaoDaMatriculaCtrl.gravarAvaliacaoDamatricula(dados);
    }

    @PutMapping
    public ResponseEntity<Object> alterarAvaliacaoDaMatricula(@RequestBody Map<String, Object> dados) {
        return avaliacaoDaMatriculaCtrl.alterar(dados);
    }

    @GetMapping
    public ResponseEntity<Object> recuperarTodas() {
        return avaliacaoDaMatriculaCtrl.buscarTodas();
    }

    @GetMapping("/{idMat}")
    public ResponseEntity<Object> recuperarAvaliacoes(@PathVariable int idMat) {
        return avaliacaoDaMatriculaCtrl.buscarAvaliacoesDeUmaMatricula(idMat);
    }

    @GetMapping("/{idMat}/{idHab}")
    public ResponseEntity<Object> recuperarAvaliacao(@PathVariable int idMat, @PathVariable int idHab) {
        return avaliacaoDaMatriculaCtrl.buscarAvaliacao(idMat, idHab);
    }

    @DeleteMapping("/apagar/{idMat}/{idHab}")
    public ResponseEntity<Object> apagarAvaliacaoDaMatricula(@PathVariable int idMat, @PathVariable int idHab) {
        return avaliacaoDaMatriculaCtrl.apagar(idMat, idHab);
    }

}
