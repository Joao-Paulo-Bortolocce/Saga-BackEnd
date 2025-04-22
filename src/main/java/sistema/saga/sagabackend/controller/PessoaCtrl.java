package sistema.saga.sagabackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sistema.saga.sagabackend.model.Endereco;
import sistema.saga.sagabackend.model.Pessoa;
import sistema.saga.sagabackend.repository.GerenciaConexao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PessoaCtrl {
    public ResponseEntity<Object> gravarPessoa(Map<String, Object> dados) {
        Map<String, Object> resposta = new HashMap<>();
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
        Map<String, Object> end = (Map<String, Object>) dados.get("endereco");
        String rua = (String) end.get("rua");
        int numero = Integer.parseInt(end.get("numero").toString());
        String complemento = (String) end.get("complemento");
        String cep = (String) end.get("cep");
        String uf = (String) end.get("uf");
        String cidade = (String) end.get("cidade");
        if (verificaIntegridade(cpf) &&
                verificaIntegridade(rg) &&
                verificaIntegridade(nome) &&
                verificaIntegridade(sexo) &&
                verificaIntegridade(locNascimento) &&
                verificaIntegridade(estadoNascimento) &&
                verificaIntegridade(estadoCivil) &&
                verificaIntegridade(rua) &&
                verificaIntegridade(numero) &&
                verificaIntegridade(cep) &&
                verificaIntegridade(uf) &&
                verificaIntegridade(cidade) &&
                verificaIntegridade(dataNascimento)) {
            GerenciaConexao gerenciaConexao;
            try {
                gerenciaConexao = new GerenciaConexao();
                try {
                    gerenciaConexao.getConexao().iniciarTransacao();
                    //begin transaction
                    Endereco endereco = new Endereco(rua, numero, complemento, cep, cidade, uf);
                    if (!endereco.gravar(gerenciaConexao.getConexao())) {
                        resposta.put("status", false);
                        resposta.put("mensagem", "Erro ao cadastrar endereco");
                        //roolback; end trasaction;
                        gerenciaConexao.getConexao().rollback();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }

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

                    if (pessoa.gravar(gerenciaConexao.getConexao())) {
                        resposta.put("status", true);
                        resposta.put("mensagem", "Pessoa Inserida com sucesso");
                        //commit; end transaction;
                        gerenciaConexao.getConexao().commit();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.ok(resposta);
                    } else {
                        resposta.put("status", false);
                        resposta.put("mensagem", "Pessoa não foi inserida!");
                        //rollback end transaction;
                        gerenciaConexao.getConexao().rollback();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }
                } catch (Exception e) {
                    resposta.put("status", false);
                    resposta.put("mensagem", "Ocorreu um erro na durante a insercao");
                    gerenciaConexao.getConexao().rollback();
                    gerenciaConexao.getConexao().fimTransacao();
                    gerenciaConexao.Desconectar();
                    return ResponseEntity.badRequest().body(resposta);
                }
            } catch (Exception e) {
                resposta.put("status", false);
                resposta.put("mensagem", "Ocorreu um erro ao iniciar conexao");
                return ResponseEntity.badRequest().body(resposta);
            }
        } else {
            resposta.put("status", false);
            resposta.put("mensagem", "Dados inválidos");
            return ResponseEntity.badRequest().body(resposta);
        }

    }

    public ResponseEntity<Object> alterarPessoa(Map<String, Object> dados) {
        Map<String, Object> resposta = new HashMap<>();
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
        Map<String, Object> end = (Map<String, Object>) dados.get("endereco");
        String rua = (String) end.get("rua");
        int numero = Integer.parseInt(end.get("numero").toString());
        String complemento = (String) end.get("complemento");
        String cep = (String) end.get("cep");
        String uf = (String) end.get("uf");
        String cidade = (String) end.get("cidade");

        if (verificaIntegridade(cpf) &&
                verificaIntegridade(rg) &&
                verificaIntegridade(nome) &&
                verificaIntegridade(sexo) &&
                verificaIntegridade(locNascimento) &&
                verificaIntegridade(estadoNascimento) &&
                verificaIntegridade(estadoCivil) &&
                verificaIntegridade(rua) &&
                verificaIntegridade(numero) &&
                verificaIntegridade(cep) &&
                verificaIntegridade(uf) &&
                verificaIntegridade(cidade) &&
                verificaIntegridade(dataNascimento)) {
            GerenciaConexao gerenciaConexao;
            try {
                gerenciaConexao = new GerenciaConexao();
                try {
                    gerenciaConexao.getConexao().iniciarTransacao();
                    if(complemento==null)
                        complemento="";
                    Endereco endereco = new Endereco(rua, numero, complemento, cep, cidade, uf);
                    if (!endereco.gravar(gerenciaConexao.getConexao())) {
                        resposta.put("status", false);
                        resposta.put("mensagem", "Erro ao cadastrar endereco");
                        gerenciaConexao.getConexao().rollback();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }
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
                    if (pessoa.alterar(gerenciaConexao.getConexao())) {
                        resposta.put("status", true);
                        resposta.put("mensagem", "Pessoa alterada com sucesso");
                        gerenciaConexao.getConexao().commit();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.ok(resposta);
                    } else {
                        resposta.put("status", false);
                        resposta.put("mensagem", "Pessoa não foi alterada!");
                        gerenciaConexao.getConexao().rollback();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }
                } catch (Exception e) {
                    resposta.put("status", false);
                    resposta.put("mensagem", "Ocorreu um erro de conexão");
                    gerenciaConexao.getConexao().rollback();
                    gerenciaConexao.getConexao().fimTransacao();
                    gerenciaConexao.Desconectar();
                    return ResponseEntity.badRequest().body(resposta);
                }
            } catch (Exception e) {
                resposta.put("status", false);
                resposta.put("mensagem", "Ocorreu um erro ao iniciar conexao");
                return ResponseEntity.badRequest().body(resposta);
            }
        } else {
            resposta.put("status", false);
            resposta.put("mensagem", "Dados inválidos");
            return ResponseEntity.badRequest().body(resposta);
        }

    }

    public ResponseEntity<Object> apagarPessoa(String cpf) {
        Map<String, Object> resposta = new HashMap<>();

        if (verificaIntegridade(cpf)) {
            try {
                Pessoa pessoa = new Pessoa(cpf);
                GerenciaConexao gerenciaConexao = new GerenciaConexao();
                if (pessoa.apagar(gerenciaConexao.getConexao())) {
                    resposta.put("status", true);
                    resposta.put("mensagem", "Pessoa excluída com sucesso!");
                    gerenciaConexao.Desconectar();
                    return ResponseEntity.ok(resposta);
                } else {
                    resposta.put("status", false);
                    resposta.put("mensagem", "Exclusão não foi realizada!");
                    gerenciaConexao.Desconectar();
                    return ResponseEntity.badRequest().body(resposta);
                }
            } catch (Exception e) {
                resposta.put("status", false);
                resposta.put("mensagem", "Ocorreu um erro de conexão");
                return ResponseEntity.badRequest().body(resposta);
            }
        } else {
            resposta.put("status", false);
            resposta.put("mensagem", "Dados inválidos para exclusão!");
            return ResponseEntity.badRequest().body(resposta);
        }

    }

    public ResponseEntity<Object> buscarTodos() {
        Map<String, Object> resposta = new HashMap<>();
        GerenciaConexao gerenciaConexao = new GerenciaConexao();
        try {
            Pessoa pessoa = new Pessoa();
            List<Map<String,Object>> enderecos = new ArrayList<>();
            List<Pessoa> pessoaList = pessoa.buscarTodos(gerenciaConexao.getConexao(), enderecos);
            if (pessoaList != null ) {
                for (int i = 0; i < enderecos.size(); i++) {
                    Map<String, Object>  end= enderecos.get(i);
                    Endereco endereco = new Endereco(
                            ((Number) end.get("endereco_id")).longValue(),
                            (String) end.get("endereco_rua"),
                            (int) end.get("endereco_num"),
                            (String) end.get("endereco_complemento"),
                            (String) end.get("endereco_cep"),
                            (String) end.get("endereco_cidade"),
                            (String) end.get("endereco_uf")
                    );
                    pessoaList.get(i).setEndereco(endereco);
                }
                resposta.put("status", true);
                resposta.put("listaDePessoas", pessoaList);
                gerenciaConexao.Desconectar();
                return ResponseEntity.ok(resposta);
            } else {
                resposta.put("status", false);
                resposta.put("mensagem", "Não existem pessoas cadastradas");
                gerenciaConexao.Desconectar();
                return ResponseEntity.badRequest().body(resposta);
            }
        } catch (Exception e) {
            resposta.put("status", false);
            resposta.put("mensagem", "Ocorreu um erro de conexão");
            gerenciaConexao.Desconectar();
            return ResponseEntity.badRequest().body(resposta);
        }
    }

    public ResponseEntity<Object> buscarPessoa(String cpf) {
        Map<String, Object> resposta = new HashMap<>();
        GerenciaConexao gerenciaConexao = new GerenciaConexao();
        try {
            Pessoa pessoa = new Pessoa(cpf);
            Map<String, Object> end= new HashMap<>();
            pessoa = pessoa.buscaPessoa(gerenciaConexao.getConexao(), end);
            if (pessoa != null) {
                Endereco endereco = new Endereco(
                        ((Number) end.get("endereco_id")).longValue(),
                        (String) end.get("endereco_rua"),
                        (int) end.get("endereco_num"),
                        (String) end.get("endereco_complemento"),
                        (String) end.get("endereco_cep"),
                        (String) end.get("endereco_cidade"),
                        (String) end.get("endereco_uf")
                );
                pessoa.setEndereco(endereco);
                resposta.put("status", true);
                resposta.put("pessoa", pessoa);
                gerenciaConexao.Desconectar();
                return ResponseEntity.ok(resposta);
            } else {
                resposta.put("status", false);
                resposta.put("mensagem", "Não existem pessoas cadastradas");
                gerenciaConexao.Desconectar();
                return ResponseEntity.badRequest().body(resposta);
            }
        } catch (Exception e) {
            resposta.put("status", false);
            resposta.put("mensagem", "Ocorreu um erro de conexão");
            gerenciaConexao.Desconectar();
            return ResponseEntity.badRequest().body(resposta);
        }
    }

    public ResponseEntity<Object> buscarTodosSemAlunos() {
        Map<String, Object> resposta = new HashMap<>();
        GerenciaConexao gerenciaConexao = new GerenciaConexao();
        try {
            Pessoa pessoa = new Pessoa();
            List<Map<String,Object>> enderecos = new ArrayList<>();
            List<Pessoa> pessoaList = pessoa.buscarTodosSemAlunos(gerenciaConexao.getConexao(), enderecos);
            if (pessoaList != null ) {
                for (int i = 0; i < enderecos.size(); i++) {
                    Map<String, Object>  end= enderecos.get(i);
                    Endereco endereco = new Endereco(
                            ((Number) end.get("endereco_id")).longValue(),
                            (String) end.get("endereco_rua"),
                            (int) end.get("endereco_num"),
                            (String) end.get("endereco_complemento"),
                            (String) end.get("endereco_cep"),
                            (String) end.get("endereco_cidade"),
                            (String) end.get("endereco_uf")
                    );
                    pessoaList.get(i).setEndereco(endereco);
                }
                resposta.put("status", true);
                resposta.put("listaDePessoas", pessoaList);
                gerenciaConexao.Desconectar();
                return ResponseEntity.ok(resposta);
            } else {
                resposta.put("status", false);
                resposta.put("mensagem", "Não existem pessoas cadastradas");
                gerenciaConexao.Desconectar();
                return ResponseEntity.badRequest().body(resposta);
            }
        } catch (Exception e) {
            resposta.put("status", false);
            resposta.put("mensagem", "Ocorreu um erro de conexão");
            gerenciaConexao.Desconectar();
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

