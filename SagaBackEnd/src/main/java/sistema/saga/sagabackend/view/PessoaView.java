package sistema.saga.sagabackend.view;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.saga.sagabackend.controller.PessoaCtrl;
import sistema.saga.sagabackend.dto.PessoaDTO;
import sistema.saga.sagabackend.model.Endereco;

import java.util.Date;

@CrossOrigin
@RestController
@RequestMapping(value = "pessoa")
public class PessoaView {
    @Autowired //não preciso instanciar nenhuma vez?
    private PessoaCtrl pessoaCtrl;

    ResponseEntity<Object> getPerson(String term){
        return null;
    }

//    ResponseEntity<Object> gravar(String cpf, String rg, String nome, Date dataNascimento, String sexo, String locNascimento, String estadoNascimento, Endereco endereco, String estadoCivil){
//        pessoaCtrl.gravarPessoa( cpf, rg, nome,dataNascimento, sexo,  locNascimento, estadoNascimento,  endereco,  estadoCivil);
    @PostMapping(value = "gravar")
    ResponseEntity<Object> gravar(@RequestBody PessoaDTO pessoaDTO){
       pessoaCtrl.gravarPessoa(pessoaDTO);
        return null;
    }

}
