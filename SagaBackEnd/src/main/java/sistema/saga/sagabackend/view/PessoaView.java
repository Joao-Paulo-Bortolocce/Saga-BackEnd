package sistema.saga.sagabackend.view;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.saga.sagabackend.controller.PessoaCtrl;
import sistema.saga.sagabackend.dto.PessoaDTO;


@CrossOrigin
@RestController
@RequestMapping(value = "pessoa")
public class PessoaView {
    @Autowired//não preciso instanciar nenhuma vez?
    private  PessoaCtrl pessoaCtrl;


//    ResponseEntity<Object> gravar(String cpf, String rg, String nome, Date dataNascimento, String sexo, String locNascimento, String estadoNascimento, Endereco endereco, String estadoCivil){
//        pessoaCtrl.gravarPessoa( cpf, rg, nome,dataNascimento, sexo,  locNascimento, estadoNascimento,  endereco,  estadoCivil);
    @PostMapping(value = "/gravar")
    ResponseEntity<Object> gravar(@RequestBody PessoaDTO pessoaDTO){
       pessoaCtrl.gravarPessoa(pessoaDTO);
        return null;
    }

    @GetMapping(value = "/buscarTodos")
    ResponseEntity<Object> buscarTodos(){
        return pessoaCtrl.buscarTodos();
    }

    @DeleteMapping(value = "/apagar")
    ResponseEntity<Object> apagar(String cpf){
        return pessoaCtrl.apagarPessoa(cpf);
    }

    @PutMapping(value = "/alterar")
    ResponseEntity<Object> alterar(@RequestBody PessoaDTO pessoaDTO){
        return pessoaCtrl.alterarPessoa(pessoaDTO);
    }

}
