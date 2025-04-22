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
        int ra = Integer.parseInt((String) dados.get("ra"));
        String restricaoMedica = (String) dados.get("restricaoMedica");
        Map<String, Object> pessoa = (Map<String, Object>) dados.get("pessoa");
        String cpf = (String) pessoa.get("cpf");

        if (Regras.verificaIntegridade(ra) &&
                Regras.verificaIntegridade(restricaoMedica) &&
                Regras.verificaIntegridade(cpf)) {
            GerenciaConexao gerenciaConexao;
            try {
                gerenciaConexao = new GerenciaConexao();
                try {
                    gerenciaConexao.getConexao().iniciarTransacao();
                    //begin transaction


                    Pessoa pessoaAux = new Pessoa(cpf);
                    Map<String,Object> end= new HashMap<>();
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

        if (Regras.verificaIntegridade(ra) &&
                Regras.verificaIntegridade(restricaoMedica) &&
                Regras.verificaIntegridade(cpf)) {
            GerenciaConexao gerenciaConexao;
            try {
                gerenciaConexao = new GerenciaConexao();
                try {
                    gerenciaConexao.getConexao().iniciarTransacao();
                    //begin transaction


                    Pessoa pessoaAux = new Pessoa(cpf);
                    Map<String,Object> end= new HashMap<>();
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
            resposta.put("mensagem", "Dados inválidos, preencha todas as informações corretamente");
            return ResponseEntity.badRequest().body(resposta);
        }

    }
    public ResponseEntity<Object> apagarAluno(int  ra) {
        Map<String, Object> resposta = new HashMap<>();

        if (Regras.verificaIntegridade(ra)) {
            try {
                Aluno aluno = new Aluno(ra);
                GerenciaConexao gerenciaConexao = new GerenciaConexao();
                if (aluno.apagar(gerenciaConexao.getConexao())) {
                    resposta.put("status", true);
                    resposta.put("mensagem", "Aluno excluído com sucesso!");
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
                    alunoList.get(i).setPessoa(Regras.HashToPessoa(pessoa));

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


    public ResponseEntity<Object> buscarTodosSemMatricula() {
        Map<String, Object> resposta = new HashMap<>();
        GerenciaConexao gerenciaConexao = new GerenciaConexao();
        try {
            Aluno aluno = new Aluno();
            List<Map<String, Object>> pessoas = new ArrayList<>();
            List<Aluno> alunoList = aluno.buscarTodosSemMatricula(gerenciaConexao.getConexao(), pessoas);
            if (alunoList != null) {
                for (int i = 0; i < pessoas.size(); i++) {
                    Map<String, Object> pessoa = pessoas.get(i);
                    alunoList.get(i).setPessoa(Regras.HashToPessoa(pessoa));

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

    public ResponseEntity<Object> buscarAluno(int ra) {
        Map<String, Object> resposta = new HashMap<>();
        GerenciaConexao gerenciaConexao = new GerenciaConexao();
        try {
            Aluno aluno = new Aluno(ra);
            Map<String, Object> pessoa= new HashMap<>();
            aluno = aluno.buscaAluno(gerenciaConexao.getConexao(), aluno,pessoa);
            if (aluno != null) {
               aluno.setPessoa(Regras.HashToPessoa(pessoa));
                resposta.put("status", true);
                resposta.put("aluno", aluno);
                gerenciaConexao.Desconectar();
                return ResponseEntity.ok(resposta);
            } else {
                resposta.put("status", false);
                resposta.put("mensagem", "Não existe aluno cadastrado com o RA: "+ra);
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

