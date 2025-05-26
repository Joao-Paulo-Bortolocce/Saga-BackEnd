package sistema.saga.sagabackend.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.saga.sagabackend.control.AvaliacaoCtrl;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("avaliacao")
public class AvaliacaoView {

    @Autowired
    AvaliacaoCtrl avaliacaoCtrl;

    @PostMapping("")
    public ResponseEntity<Object> gravar(@RequestBody Map<String, Object> dados) {
        return avaliacaoCtrl.gravarAvaliacao(dados);
    }

    @GetMapping("buscarTodas")
    public ResponseEntity<Object> buscarTodas() {
        return avaliacaoCtrl.buscarTodas();
    }

    @GetMapping("buscarTodas/{ra}/{idSerie}")
    public ResponseEntity<Object> buscarAvaAluSer(@PathVariable Integer ra, @PathVariable Integer idSerie) {
        return avaliacaoCtrl.buscarAvaliacoesPorAlunoESerie(ra, idSerie);
    }
}
