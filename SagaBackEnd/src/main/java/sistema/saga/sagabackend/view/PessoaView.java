package sistema.saga.sagabackend.view;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.saga.sagabackend.controller.PessoaCtrl;
import sistema.saga.sagabackend.dto.PessoaDTO;
import sistema.saga.sagabackend.service.PessoaService;

import java.util.Date;

@CrossOrigin
@RestController
@RequestMapping(value = "pessoa")
public class PessoaView {
    //não preciso instanciar nenhuma vez?
    private static PessoaCtrl pessoaCtrl= new PessoaCtrl();

//    @Autowired
//    private PessoaService pessoaService;

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

//    @GetMapping("/test-pessoa")
//    public Pessoa testPessoaService(@RequestParam String cpf) {
//        // Testando o método do serviço
//        Pessoa teste= pessoaService.buscarPessoaPorCpf(cpf);
//        return null;
//    }

}
