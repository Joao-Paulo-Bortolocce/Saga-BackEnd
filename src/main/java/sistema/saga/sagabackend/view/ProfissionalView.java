package sistema.saga.sagabackend.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.saga.sagabackend.control.ProfissionalCtrl;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping(value = "profissional")
public class ProfissionalView {
    @Autowired
    private ProfissionalCtrl profissionalCtrl;


    @PostMapping
    public ResponseEntity<Object> gravar(@RequestBody Map<String, Object> dados) {
        return profissionalCtrl.gravarProfissional(dados);
    }

    @GetMapping
    ResponseEntity<Object> buscarTodos(){
        return profissionalCtrl.buscarTodos();
    }

    @GetMapping(value = "/{rn}/{senha}")
    ResponseEntity<Object> buscarUsuario(@PathVariable(name="rn")int rn,@PathVariable(name="senha")String senha){
        return profissionalCtrl.buscarUsuario(rn,senha);
    }

    @GetMapping(value = "/{rn}")
    ResponseEntity<Object> buscar(@PathVariable(name="rn")int rn){
        return profissionalCtrl.buscarProfissional(rn);
    }

    @DeleteMapping(value = "/{rn}")
    ResponseEntity<Object> apagar(@PathVariable(name = "rn") int rn){
        return profissionalCtrl.apagarProfissional(rn);
    }

    @PutMapping
    ResponseEntity<Object> alterar(@RequestBody Map<String, Object> dados) {
        return profissionalCtrl.alterarProfissional(dados);
    }
}
