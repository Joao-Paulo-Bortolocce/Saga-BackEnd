package sistema.saga.sagabackend.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.saga.sagabackend.controller.PessoaCtrl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "pessoa")
public class PessoaView {
    @Autowired
    private PessoaCtrl pessoaCtrl;

    @PostMapping(value = "/gravar")
    public ResponseEntity<Object> gravar(@RequestBody Map<String, Object> dados) {
            String cpf = (String) dados.get("cpf");
        String rg = (String) dados.get("rg");
        String nome = (String) dados.get("nome");
        String dataNascimentoStr = (String) dados.get("dataNascimento");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dataNascimento = LocalDate.parse(dataNascimentoStr, formatter);
        String sexo = (String) dados.get("sexo");
        String locNascimento = (String) dados.get("locNascimento");
        String estadoNascimento = (String) dados.get("estadoNascimento");
        String estadoCivil = (String) dados.get("estadoCivil");
        Map<String, Object> endereco = (Map<String, Object>) dados.get("endereco");

        String rua = (String) endereco.get("rua");
        int numero = Integer.parseInt(endereco.get("numero").toString());
        String complemento = (String) endereco.get("complemento");
        String cep = (String) endereco.get("cep");
        String uf = (String) endereco.get("uf");
        String cidade = (String) endereco.get("cidade");

        return pessoaCtrl.gravarPessoa(cpf, rg, nome, dataNascimento, sexo, locNascimento,
                estadoNascimento, estadoCivil, rua, numero, complemento, cep, uf, cidade);
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
    ResponseEntity<Object> alterar(@RequestBody Map<String, Object> dados) {
        String cpf = (String) dados.get("cpf");
        String rg = (String) dados.get("rg");
        String nome = (String) dados.get("nome");
        String dataNascimentoStr = (String) dados.get("dataNascimento");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dataNascimento = LocalDate.parse(dataNascimentoStr, formatter);
        String sexo = (String) dados.get("sexo");
        String locNascimento = (String) dados.get("locNascimento");
        String estadoNascimento = (String) dados.get("estadoNascimento");
        String estadoCivil = (String) dados.get("estadoCivil");
        Map<String, Object> endereco = (Map<String, Object>) dados.get("endereco");

        String rua = (String) endereco.get("rua");
        int numero = Integer.parseInt(endereco.get("numero").toString());
        String complemento = (String) endereco.get("complemento");
        String cep = (String) endereco.get("cep");
        String uf = (String) endereco.get("uf");
        String cidade = (String) endereco.get("cidade");
        return pessoaCtrl.alterarPessoa(cpf, rg, nome, dataNascimento, sexo, locNascimento,
                estadoNascimento, estadoCivil, rua, numero, complemento,
                cep, uf, cidade);
    }
}
