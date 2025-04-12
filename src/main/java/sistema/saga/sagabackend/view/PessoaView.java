package sistema.saga.sagabackend.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.saga.sagabackend.controller.PessoaCtrl;

import java.time.LocalDate;

@CrossOrigin
@RestController
@RequestMapping(value = "pessoa")
public class PessoaView {
    @Autowired
    private PessoaCtrl pessoaCtrl;

    @PostMapping(value = "/gravar")
    public ResponseEntity<Object> gravar(@RequestParam String cpf,
                                         @RequestParam String rg,
                                         @RequestParam String nome,
                                         @RequestParam LocalDate dataNascimento,
                                         @RequestParam String sexo,
                                         @RequestParam String locNascimento,
                                         @RequestParam String estadoNascimento,
                                         @RequestParam int idEndereco,
                                         @RequestParam String estadoCivil){
        return pessoaCtrl.gravarPessoa(cpf, rg, nome, dataNascimento, sexo, locNascimento, estadoNascimento, idEndereco, estadoCivil);
    }

    @GetMapping(value = "/buscarTodos")
    ResponseEntity<Object> buscarTodos(){
        return pessoaCtrl.buscarTodos();
    }

    @DeleteMapping(value = "/apagar/{cpf}")
    ResponseEntity<Object> apagar(@PathVariable(name = "cpf") String cpf){
        return pessoaCtrl.apagarPessoa(cpf);
    }

    @PutMapping(value = "/alterar")
    ResponseEntity<Object> alterar(String cpf, String rg, String nome, LocalDate dataNascimento, String sexo, String locNascimento, String estadoNascimento, int idEndereco, String estadoCivil){
        return pessoaCtrl.alterarPessoa(cpf, rg, nome, dataNascimento, sexo, locNascimento, estadoNascimento, idEndereco, estadoCivil);
    }
}
