package sistema.saga.sagabackend.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.saga.sagabackend.controller.AlunoCtrl;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "aluno")
public class AlunoView {
    @Autowired
    private AlunoCtrl alunoCtrl;

   /* @PostMapping(value = "/gravar")
    public ResponseEntity<Object> gravar(@RequestBody Map<String, Object> dados) {
        return alunoCtrl.gravarAluno(dados);
    }*/

    @GetMapping(value = "/buscarTodos")
    ResponseEntity<Object> buscarTodos(){
        return alunoCtrl.buscarTodos();
    }

   /* @GetMapping(value = "/{ra}")
    ResponseEntity<Object> buscar(@PathVariable(name="ra")String ra){
        return alunoCtrl.buscarAluno(ra);
    }

    @DeleteMapping(value = "/apagar/{ra}")
    ResponseEntity<Object> apagar(@PathVariable(name = "ra") String ra){
        return alunoCtrl.apagarAluno(ra);
    }

    @PutMapping(value = "/alterar")
    ResponseEntity<Object> alterar(@RequestBody Map<String, Object> dados) {

        return alunoCtrl.alterarAluno(dados);
    }*/
}
