package sistema.saga.sagabackend.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.saga.sagabackend.control.MatriculaCtrl;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "matricula")
public class MatriculaView {
    @Autowired
    private MatriculaCtrl matriculaCtrl;

    @PostMapping
    public ResponseEntity<Object> gravar(@RequestBody Map<String, Object> dados) {
        return matriculaCtrl.gravarMatricula(dados);
    }

    @GetMapping
    ResponseEntity<Object> buscarTodas(){
        return matriculaCtrl.buscarTodas();
    }

    @GetMapping(value = "/buscarTodasFiltradas")
    ResponseEntity<Object> buscarTodasFiltradas(
            @RequestParam(name = "serie") int serie,
            @RequestParam(name = "anoLetivo") int anoLetivo,
            @RequestParam(name = "valido") int valido,
            @RequestParam(name = "turmaLetra", required = false) String turmaLetra
    ) {
        return matriculaCtrl.buscarTodasFiltradas(serie, anoLetivo, valido, turmaLetra);
    }

    @GetMapping(value = "/{ra}")
    ResponseEntity<Object> buscar(@PathVariable(name="ra")int ra){
        return matriculaCtrl.buscarMatricula(ra);
    }

    @DeleteMapping(value = "/{ra}")
    ResponseEntity<Object> apagar(@PathVariable(name = "ra") int ra){
        return matriculaCtrl.apagarMatricula(ra);
    }

    @PutMapping
    ResponseEntity<Object> alterar(@RequestBody Map<String, Object> dados) {
        return matriculaCtrl.alterarMatricula(dados);
    }

    @GetMapping(value = "/buscarSemTurma")
    public ResponseEntity<Object> buscarMatriculasSemTurma(@RequestParam(name = "serie") int serieId,
                                                           @RequestParam(name = "anoLetivo") int anoLetivoId) {
        return matriculaCtrl.buscarMatriculasSemTurma(serieId, anoLetivoId);
    }

    @PutMapping("/removerTurma/{id}")
    public ResponseEntity<Object> removerTurma(@PathVariable(name = "id") int id) {
        return matriculaCtrl.removerTurmaDaMatricula(id);
    }
}