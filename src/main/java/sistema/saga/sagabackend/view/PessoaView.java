package sistema.saga.sagabackend.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.saga.sagabackend.controller.PessoaCtrl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "pessoa")
public class PessoaView {
    @Autowired
    private PessoaCtrl pessoaCtrl;

    @PostMapping(value = "")
    public ResponseEntity<Object> gravar(@RequestBody Map<String, Object> dados) {
        return pessoaCtrl.gravarPessoa(dados);
    }

    @GetMapping(value = "/buscarTodos")
    ResponseEntity<Object> buscarTodos(){
        return pessoaCtrl.buscarTodos();
    }

    @GetMapping(value = "/buscarTodosSemAlunos")
    ResponseEntity<Object> buscarTodosSemAlunos(){
        return pessoaCtrl.buscarTodosSemAlunos(true);
    }

    @GetMapping(value = "/buscarSemAlunos/{cpf}")
    ResponseEntity<Object> buscarSemAlunos(){
        return pessoaCtrl.buscarTodosSemAlunos(true);
    }

    @GetMapping(value = "/buscarTodosSemProfissional")
    ResponseEntity<Object> buscarTodosSemProfissional(){
        return pessoaCtrl.buscarTodosSemProfissional();
    }

    @GetMapping(value = "/{cpf}")
    ResponseEntity<Object> buscar(@PathVariable(name="cpf")String cpf){
        return pessoaCtrl.buscarPessoa(cpf);
    }

    @DeleteMapping(value = "/{cpf}")
    ResponseEntity<Object> apagar(@PathVariable(name = "cpf") String cpf){
        return pessoaCtrl.apagarPessoa(cpf);
    }

    @PutMapping(value = "")
    ResponseEntity<Object> alterar(@RequestBody Map<String, Object> dados) {

        return pessoaCtrl.alterarPessoa(dados);
    }
}