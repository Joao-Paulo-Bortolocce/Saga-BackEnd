package sistema.saga.sagabackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import sistema.saga.sagabackend.model.Endereco;
import sistema.saga.sagabackend.model.Pessoa;
import sistema.saga.sagabackend.repository.GerenciaConexao;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PessoaCtrl {
    public ResponseEntity<Object> gravarPessoa(String cpf, String rg, String nome, LocalDate dataNascimento, String sexo, String locNascimento, String estadoNascimento, int idEndereco, String estadoCivil) {
        Map<String, Object> resposta = new HashMap<>();
        try {
            if (verificaIntegridade(cpf) &&
                    verificaIntegridade(rg) &&
                    verificaIntegridade(nome) &&
                    verificaIntegridade(sexo) &&
                    verificaIntegridade(locNascimento) &&
                    verificaIntegridade(estadoNascimento) &&
                    verificaIntegridade(estadoCivil) &&
                    verificaIntegridade(idEndereco) &&
                    verificaIntegridade(dataNascimento)) {
                Endereco endereco = new Endereco(idEndereco);
                endereco = endereco.buscaEndereco(idEndereco); // supondo que o método buscarEndereco existe

                Pessoa pessoa = new Pessoa(
                        cpf,
                        rg,
                        nome,
                        dataNascimento,
                        sexo,
                        locNascimento,
                        estadoNascimento,
                        endereco,
                        estadoCivil
                );
                GerenciaConexao gerenciaConexao= new GerenciaConexao();
                if(pessoa.gravar(gerenciaConexao.getConexao())){
                    resposta.put("status", true);
                    resposta.put("mensagem", "Pessoa alterada com sucesso");
                    gerenciaConexao.Desconectar();
                    return ResponseEntity.ok(resposta);
                }
                else{
                    resposta.put("status", false);
                    resposta.put("mensagem", "Pessoa não foi inserida!");
                    gerenciaConexao.Desconectar();
                    return ResponseEntity.badRequest().body(resposta);
                }

            } else {
                resposta.put("status", false);
                resposta.put("mensagem", "Dados inválidos");
                return ResponseEntity.badRequest().body(resposta);
            }
        } catch (Exception e) {
            resposta.put("status", false);
            resposta.put("mensagem", "Ocorreu um erro de conexão");
            return ResponseEntity.badRequest().body(resposta);
        }
    }

    public ResponseEntity<Object> alterarPessoa(String cpf, String rg, String nome, LocalDate dataNascimento, String sexo, String locNascimento, String estadoNascimento, int idEndereco, String estadoCivil) {
        Map<String, Object> resposta = new HashMap<>();
        try {
            if (verificaIntegridade(cpf) &&
                    verificaIntegridade(rg) &&
                    verificaIntegridade(nome) &&
                    verificaIntegridade(sexo) &&
                    verificaIntegridade(locNascimento) &&
                    verificaIntegridade(estadoNascimento) &&
                    verificaIntegridade(estadoCivil) &&
                    verificaIntegridade(idEndereco) &&
                    verificaIntegridade(dataNascimento)) {

                Endereco endereco = new Endereco(idEndereco);
                endereco = endereco.buscaEndereco(idEndereco);

                Pessoa pessoa = new Pessoa(
                        cpf,
                        rg,
                        nome,
                        dataNascimento,
                        sexo,
                        locNascimento,
                        estadoNascimento,
                        endereco,
                        estadoCivil
                );
                GerenciaConexao gerenciaConexao= new GerenciaConexao();
                if(pessoa.alterar(gerenciaConexao.getConexao())){
                    resposta.put("status", true);
                    resposta.put("mensagem", "Pessoa alterada com sucesso");
                    gerenciaConexao.Desconectar();
                    return ResponseEntity.ok(resposta);
                }
                else{
                    resposta.put("status", false);
                    resposta.put("mensagem", "Pessoa não foi alterada!");
                    gerenciaConexao.Desconectar();
                    return ResponseEntity.badRequest().body(resposta);
                }
            } else {
                resposta.put("status", false);
                resposta.put("mensagem", "Dados inválidos");
                return ResponseEntity.badRequest().body(resposta);
            }
        } catch (Exception e) {
            resposta.put("status", false);
            resposta.put("mensagem", "Ocorreu um erro de conexão");
            return ResponseEntity.badRequest().body(resposta);
        }
    }

    public ResponseEntity<Object> buscarTodos() {
        Map<String, Object> resposta = new HashMap<>();
        try {
            Pessoa pessoa = new Pessoa();
            GerenciaConexao gerenciaConexao= new GerenciaConexao();
            List<Pessoa> pessoaList = pessoa.buscarTodos(gerenciaConexao.getConexao());
            gerenciaConexao.Desconectar();
            if (pessoaList != null && pessoaList.size() > 0) {
                resposta.put("status", true);
                resposta.put("listaDePessoas", pessoaList);
                return ResponseEntity.ok(resposta);
            } else {
                resposta.put("status", false);
                resposta.put("mensagem", "Não existem pessoas cadastradas");
                return ResponseEntity.badRequest().body(resposta);
            }
        } catch (Exception e) {
            resposta.put("status", false);
            resposta.put("mensagem", "Ocorreu um erro de conexão");
            return ResponseEntity.badRequest().body(resposta);
        }
    }

    public ResponseEntity<Object> apagarPessoa(String cpf) {
        Map<String, Object> resposta = new HashMap<>();
        try {
            if (verificaIntegridade(cpf)) {
                Pessoa pessoa = new Pessoa(cpf);
                GerenciaConexao gerenciaConexao= new GerenciaConexao();
                if(pessoa.apagar(gerenciaConexao.getConexao())){
                    resposta.put("status", true);
                    resposta.put("mensagem", "Pessoa excluída com sucesso!");
                    gerenciaConexao.Desconectar();
                    return ResponseEntity.ok(resposta);
                }
                else{
                    resposta.put("status", false);
                    resposta.put("mensagem", "Exclusão não foi realizada!");
                    gerenciaConexao.Desconectar();
                    return ResponseEntity.badRequest().body(resposta);
                }
            } else {
                resposta.put("status", false);
                resposta.put("mensagem", "Dados inválidos para exclusão!");
                return ResponseEntity.badRequest().body(resposta);
            }
        } catch (Exception e) {
            resposta.put("status", false);
            resposta.put("mensagem", "Ocorreu um erro de conexão");
            return ResponseEntity.badRequest().body(resposta);
        }
    }

    private boolean verificaIntegridade(String elemento) {
        return elemento != null && !elemento.trim().isEmpty();
    }

    private boolean verificaIntegridade(int elemento) {
        return elemento > 0;
    }

    private boolean verificaIntegridade(LocalDate elemento) {
        return elemento != null;
    }

    private boolean verificaIntegridade(Endereco elemento) {
        return elemento != null;
    }
}

