package sistema.saga.sagabackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;
import sistema.saga.sagabackend.model.Endereco;
import sistema.saga.sagabackend.model.Aluno;
import sistema.saga.sagabackend.model.Pessoa;
import sistema.saga.sagabackend.repository.GerenciaConexao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class AlunoCtrl {
    public ResponseEntity<Object> gravarAluno(Map<String, Object> dados) {
        Map<String, Object> resposta = new HashMap<>();
        int ra = (int) dados.get("ra");
        String restricaoMedica = (String) dados.get("restricaoMedica");
        Map<String, Object> pessoa = (Map<String, Object>) dados.get("pessoa");
        String cpf = (String) pessoa.get("cpf");
        String rg = (String) pessoa.get("rg");
        String nome = (String) pessoa.get("nome");
        String dataNascimentoStr = (String) pessoa.get("dataNascimento");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dataNascimento = LocalDate.parse(dataNascimentoStr, formatter);
        String sexo = (String) pessoa.get("sexo");
        String locNascimento = (String) pessoa.get("locNascimento");
        String estadoNascimento = (String) pessoa.get("estadoNascimento");
        String estadoCivil = (String) pessoa.get("estadoCivil");
        Map<String, Object> end = (Map<String, Object>) pessoa.get("endereco");
        String rua = (String) end.get("rua");
        int numero = Integer.parseInt(end.get("numero").toString());
        String complemento = (String) end.get("complemento");
        String cep = (String) end.get("cep");
        String uf = (String) end.get("uf");
        String cidade = (String) end.get("cidade");
        if (Regras.verificaIntegridade(ra) &&
                Regras.verificaIntegridade(restricaoMedica) &&
                Regras.verificaIntegridade(cpf) &&
                Regras.verificaIntegridade(rg) &&
                Regras.verificaIntegridade(nome) &&
                Regras.verificaIntegridade(sexo) &&
                Regras.verificaIntegridade(locNascimento) &&
                Regras.verificaIntegridade(estadoNascimento) &&
                Regras.verificaIntegridade(estadoCivil) &&
                Regras.verificaIntegridade(rua) &&
                Regras.verificaIntegridade(numero) &&
                Regras.verificaIntegridade(cep) &&
                Regras.verificaIntegridade(uf) &&
                Regras.verificaIntegridade(cidade) &&
                Regras.verificaIntegridade(dataNascimento)) {
            GerenciaConexao gerenciaConexao;
            try {
                gerenciaConexao = new GerenciaConexao();
                try {
                    gerenciaConexao.getConexao().iniciarTransacao();
                    //begin transaction


                    Pessoa pessoaAux = new Pessoa(
                            cpf,
                            rg,
                            nome,
                            dataNascimento,
                            sexo,
                            locNascimento,
                            estadoNascimento,
                            null,
                            estadoCivil
                    );
                    end= new HashMap<>();
                    pessoaAux=pessoaAux.buscaPessoa(gerenciaConexao.getConexao(),end);
                    if (pessoaAux==null) {
                        resposta.put("status", false);
                        resposta.put("mensagem", "As informações pessoais do aluno não estão cadastradas");
                        //roolback; end trasaction;
                        gerenciaConexao.getConexao().rollback();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }
                    pessoaAux.setEndereco(Regras.HashToEndereco(end));
                    Aluno aluno= new Aluno(ra,restricaoMedica,pessoaAux);
                    if (aluno.gravar(gerenciaConexao.getConexao())) {
                        resposta.put("status", true);
                        resposta.put("mensagem", "Aluno Inserida com sucesso");
                        //commit; end transaction;
                        gerenciaConexao.getConexao().commit();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.ok(resposta);
                    } else {
                        resposta.put("status", false);
                        resposta.put("mensagem", "Aluno não foi inserida!");
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

    public ResponseEntity<Object> alterarAluno(Map<String, Object> dados) {
        Map<String, Object> resposta = new HashMap<>();
        int ra = (int) dados.get("ra");
        String restricaoMedica = (String) dados.get("restricaoMedica");
        Map<String, Object> pessoa = (Map<String, Object>) dados.get("pessoa");
        String cpf = (String) pessoa.get("cpf");
        String rg = (String) pessoa.get("rg");
        String nome = (String) pessoa.get("nome");
        String dataNascimentoStr = (String) pessoa.get("dataNascimento");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dataNascimento = LocalDate.parse(dataNascimentoStr, formatter);
        String sexo = (String) pessoa.get("sexo");
        String locNascimento = (String) pessoa.get("locNascimento");
        String estadoNascimento = (String) pessoa.get("estadoNascimento");
        String estadoCivil = (String) pessoa.get("estadoCivil");
        Map<String, Object> end = (Map<String, Object>) pessoa.get("endereco");
        String rua = (String) end.get("rua");
        int numero = Integer.parseInt(end.get("numero").toString());
        String complemento = (String) end.get("complemento");
        String cep = (String) end.get("cep");
        String uf = (String) end.get("uf");
        String cidade = (String) end.get("cidade");
        if (Regras.verificaIntegridade(ra) &&
                Regras.verificaIntegridade(restricaoMedica) &&
                Regras.verificaIntegridade(cpf) &&
                Regras.verificaIntegridade(rg) &&
                Regras.verificaIntegridade(nome) &&
                Regras.verificaIntegridade(sexo) &&
                Regras.verificaIntegridade(locNascimento) &&
                Regras.verificaIntegridade(estadoNascimento) &&
                Regras.verificaIntegridade(estadoCivil) &&
                Regras.verificaIntegridade(rua) &&
                Regras.verificaIntegridade(numero) &&
                Regras.verificaIntegridade(cep) &&
                Regras.verificaIntegridade(uf) &&
                Regras.verificaIntegridade(cidade) &&
                Regras.verificaIntegridade(dataNascimento)) {
            GerenciaConexao gerenciaConexao;
            try {
                gerenciaConexao = new GerenciaConexao();
                try {
                    gerenciaConexao.getConexao().iniciarTransacao();
                    //begin transaction


                    Pessoa pessoaAux = new Pessoa(
                            cpf,
                            rg,
                            nome,
                            dataNascimento,
                            sexo,
                            locNascimento,
                            estadoNascimento,
                            null,
                            estadoCivil
                    );
                    end= new HashMap<>();
                    pessoaAux=pessoaAux.buscaPessoa(gerenciaConexao.getConexao(),end);
                    if (pessoaAux==null) {
                        resposta.put("status", false);
                        resposta.put("mensagem", "As informações pessoais do aluno não estão cadastradas");
                        //roolback; end trasaction;
                        gerenciaConexao.getConexao().rollback();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }
                    pessoaAux.setEndereco(Regras.HashToEndereco(end));
                    Aluno aluno= new Aluno(ra,restricaoMedica,pessoaAux);
                    if (aluno.alterar(gerenciaConexao.getConexao())) {
                        resposta.put("status", true);
                        resposta.put("mensagem", "Aluno alterado com sucesso");
                        //commit; end transaction;
                        gerenciaConexao.getConexao().commit();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.ok(resposta);
                    } else {
                        resposta.put("status", false);
                        resposta.put("mensagem", "Aluno não foi alterado!");
                        //rollback end transaction;
                        gerenciaConexao.getConexao().rollback();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }
                } catch (Exception e) {
                    resposta.put("status", false);
                    resposta.put("mensagem", "Ocorreu um erro na durante a alteração");
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
    public ResponseEntity<Object> apagarAluno(String ra) {
        Map<String, Object> resposta = new HashMap<>();

        if (Regras.verificaIntegridade(ra)) {
            try {
                Aluno aluno = new Aluno(ra);
                GerenciaConexao gerenciaConexao = new GerenciaConexao();
                if (aluno.apagar(gerenciaConexao.getConexao())) {
                    resposta.put("status", true);
                    resposta.put("mensagem", "Aluno excluída com sucesso!");
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
            Aluno aluno = new Aluno();
            List<Map<String, Object>> pessoas = new ArrayList<>();
            List<Aluno> alunoList = aluno.buscarTodos(gerenciaConexao.getConexao(), pessoas);
            if (alunoList != null) {
                for (int i = 0; i < pessoas.size(); i++) {
                    Map<String, Object> pessoa = pessoas.get(i);
                    String cpf = (String) pessoa.get("cpf");
                    String rg = (String) pessoa.get("rg");
                    String nome = (String) pessoa.get("nome");
                    java.sql.Date data = (java.sql.Date) pessoa.get("dataNascimento");
                    LocalDate dataNascimento = data.toLocalDate();
                    String sexo = (String) pessoa.get("sexo");
                    String locNascimento = (String) pessoa.get("locNascimento");
                    String estadoNascimento = (String) pessoa.get("estadoNascimento");
                    String estadoCivil = (String) pessoa.get("estadoCivil");
                    Map<String, Object>  end= (Map<String, Object>) pessoa.get("endereco");

                    Endereco endereco = new Endereco(
                            ((Number) end.get("endereco_id")).longValue(),
                            (String) end.get("endereco_rua"),
                            (int) end.get("endereco_num"),
                            (String) end.get("endereco_complemento"),
                            (String) end.get("endereco_cep"),
                            (String) end.get("endereco_cidade"),
                            (String) end.get("endereco_uf")
                    );

                    Pessoa novaPessoa = new Pessoa(cpf, rg, nome, dataNascimento, sexo, locNascimento, estadoNascimento, endereco, estadoCivil);
                    alunoList.get(i).setPessoa(novaPessoa);

                }
                resposta.put("status", true);
                resposta.put("listaDeAlunos", alunoList);
                gerenciaConexao.Desconectar();
                return ResponseEntity.ok(resposta);
            } else {
                resposta.put("status", false);
                resposta.put("mensagem", "Não existem alunos cadastradas");
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

    public ResponseEntity<Object> buscarAluno(String ra) {
        Map<String, Object> resposta = new HashMap<>();
        GerenciaConexao gerenciaConexao = new GerenciaConexao();
        try {
            Aluno aluno = new Aluno(ra);
            int idEndereco;
            idEndereco = aluno.buscaAluno(gerenciaConexao.getConexao(), aluno);
            if (aluno != null) {
                Endereco endereco = new Endereco();
                aluno.setEndereco(endereco.buscaEndereco(idEndereco, gerenciaConexao.getConexao()));
                resposta.put("status", true);
                resposta.put("Aluno", aluno);
                gerenciaConexao.Desconectar();
                return ResponseEntity.ok(resposta);
            } else {
                resposta.put("status", false);
                resposta.put("mensagem", "Não existem alunos cadastradas");
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



}

