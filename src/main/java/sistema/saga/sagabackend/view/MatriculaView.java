package sistema.saga.sagabackend.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.saga.sagabackend.controller.MatriculaCtrl;

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
    ResponseEntity<Object> buscarTodasFiltradas(@RequestParam(name = "serie") int serie,@RequestParam(name = "anoLetivo") int anoLetivo ){
        return matriculaCtrl.buscarTodasFiltradas(serie,anoLetivo);
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
}
