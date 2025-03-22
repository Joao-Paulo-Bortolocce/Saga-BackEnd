package sistema.saga.sagabackend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sistema.saga.sagabackend.dto.PessoaDTO;
import sistema.saga.sagabackend.model.Endereco;
import sistema.saga.sagabackend.model.Pessoa;
import sistema.saga.sagabackend.service.PessoaService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class PessoaCtrl {

    private final PessoaService pessoaService;

    // Injeção de dependência via construtor
    @Autowired
    public PessoaCtrl(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    //    public void gravarPessoa(String cpf, String rg, String nome, Date dataNascimento, String sexo, String locNascimento, String estadoNascimento, Endereco endereco, String estadoCivil){
    public void gravarPessoa(PessoaDTO pessoaDTO) {
        if (verificaIntegridade(pessoaDTO.getCpf()) &&
                verificaIntegridade(pessoaDTO.getRg()) &&
                verificaIntegridade(pessoaDTO.getNome()) &&
                verificaIntegridade(pessoaDTO.getSexo()) &&
                verificaIntegridade(pessoaDTO.getLocNascimento()) &&
                verificaIntegridade(pessoaDTO.getEstadoNascimento()) &&
                verificaIntegridade(pessoaDTO.getEstadoCivil()) &&
                verificaIntegridade(pessoaDTO.getEndereco()) &&
                verificaIntegridade(pessoaDTO.getDataNascimento())) {

            Pessoa pessoa = new Pessoa(
                    pessoaDTO.getCpf(),
                    pessoaDTO.getRg(),
                    pessoaDTO.getNome(),
                    pessoaDTO.getDataNascimento(),
                    pessoaDTO.getSexo(),
                    pessoaDTO.getLocNascimento(),
                    pessoaDTO.getEstadoNascimento(),
                    pessoaDTO.getEndereco(),
                    pessoaDTO.getEstadoCivil()
            );

            try {
                pessoaService.salvarPessoa(pessoa);
            } catch (Exception e) {

            }
        } else {
            System.out.println("Erro: Dados inválidos.");
        }
    }

    public ResponseEntity<Object> alterarPessoa(PessoaDTO pessoaDTO) {
        Map<String, Object> resposta = new HashMap<>();
        try {
            if (verificaIntegridade(pessoaDTO.getCpf()) &&
                    verificaIntegridade(pessoaDTO.getRg()) &&
                    verificaIntegridade(pessoaDTO.getNome()) &&
                    verificaIntegridade(pessoaDTO.getSexo()) &&
                    verificaIntegridade(pessoaDTO.getLocNascimento()) &&
                    verificaIntegridade(pessoaDTO.getEstadoNascimento()) &&
                    verificaIntegridade(pessoaDTO.getEstadoCivil()) &&
                    verificaIntegridade(pessoaDTO.getEndereco()) &&
                    verificaIntegridade(pessoaDTO.getDataNascimento())) {
               if(pessoaService.existe(pessoaDTO.getCpf())){
                   Pessoa pessoa = new Pessoa(
                           pessoaDTO.getCpf(),
                           pessoaDTO.getRg(),
                           pessoaDTO.getNome(),
                           pessoaDTO.getDataNascimento(),
                           pessoaDTO.getSexo(),
                           pessoaDTO.getLocNascimento(),
                           pessoaDTO.getEstadoNascimento(),
                           pessoaDTO.getEndereco(),
                           pessoaDTO.getEstadoCivil()
                   );
                   pessoaService.alterarPessoa(pessoa);
                   resposta.put("status", true);
                   resposta.put("mensagem", "Pessoa alterada com sucesso");
                   return ResponseEntity.ok(resposta);
               }else {
                   resposta.put("status", false);
                   resposta.put("mensagem", "Pessoa que deseja alterar não está cadastrada");
                   return ResponseEntity.badRequest().body(resposta);
               }
            } else {
                resposta.put("status", false);
                resposta.put("mensagem", "Informe corretamente os dados da pessoa que deseja excluir!");
                return ResponseEntity.badRequest().body(resposta);
            }
        } catch (
                Exception e) {
            resposta.put("status", false);
            resposta.put("Mensagem", "Ocorreu um erro de conexao");
            return ResponseEntity.badRequest().body(resposta);
        }
    }

    public ResponseEntity<Object> buscarTodos() {
        Map<String, Object> resposta = new HashMap<>();
        try {
            List<Pessoa> pessoaList = pessoaService.buscarTodos();
            if (pessoaList != null && pessoaList.size() > 0) {
//                Pessoa[] pessoas = new Pessoa[pessoaList.size()];
//                pessoaList.toArray(pessoas);
                resposta.put("status", true);
                resposta.put("listaPessoas", pessoaList);
                return ResponseEntity.ok(resposta);
            } else {
                resposta.put("status", false);
                resposta.put("Mensagem", "Nao existem pessoas cadastradas");
                return ResponseEntity.badRequest().body(resposta);
            }
        } catch (Exception e) {
            resposta.put("status", false);
            resposta.put("Mensagem", "Ocorreu um erro de conexao");
            return ResponseEntity.badRequest().body(resposta);
        }
    }


    public ResponseEntity<Object> apagarPessoa(String cpf) {
        Map<String, Object> resposta = new HashMap<>();
        try {
            if (verificaIntegridade(cpf)) {
                Pessoa pessoa = new Pessoa(cpf);
                pessoaService.apagarPessoa(pessoa);
                resposta.put("status", true);
                resposta.put("mensagem,", "Pessoa excluida com sucesso!");
                return ResponseEntity.ok(resposta);
            } else {
                resposta.put("status", false);
                resposta.put("mensagem", "Informe corretamente os dados da pessoa que deseja excluir!");
                return ResponseEntity.badRequest().body(resposta);
            }
        } catch (Exception e) {
            resposta.put("status", false);
            resposta.put("Mensagem", "Ocorreu um erro de conexao");
            return ResponseEntity.badRequest().body(resposta);
        }
    }

    private boolean verificaIntegridade(String elemento) {
        return elemento != null && !elemento.trim().isEmpty();
    }

    private boolean verificaIntegridade(LocalDate elemento) {
        return elemento != null;
    }

    private boolean verificaIntegridade(Endereco elemento) {
        return elemento != null;
    }
}
