package sistema.saga.sagabackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sistema.saga.sagabackend.model.Endereco;
import sistema.saga.sagabackend.model.Aluno;
import sistema.saga.sagabackend.model.Pessoa;
import sistema.saga.sagabackend.repository.GerenciaConexao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AlunoCtrl {
   /* public ResponseEntity<Object> gravarAluno(Map<String, Object> dados) {
        Map<String, Object> resposta = new HashMap<>();
        String ra = (String) dados.get("ra");
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
        if (verificaIntegridade(ra) &&
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

                    Aluno aluno = new Aluno(
                            ra,
                            rg,
                            nome,
                            dataNascimento,
                            sexo,
                            locNascimento,
                            estadoNascimento,
                            endereco,
                            estadoCivil
                    );

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
        String ra = (String) dados.get("ra");
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

        if (verificaIntegridade(ra) &&
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

                    Endereco endereco = new Endereco(rua, numero, complemento, cep, cidade, uf);
                    if (!endereco.gravar(gerenciaConexao.getConexao())) {
                        resposta.put("status", false);
                        resposta.put("mensagem", "Erro ao cadastrar endereco");
                        gerenciaConexao.getConexao().rollback();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }
                    Aluno aluno = new Aluno(
                            ra,
                            rg,
                            nome,
                            dataNascimento,
                            sexo,
                            locNascimento,
                            estadoNascimento,
                            endereco,
                            estadoCivil
                    );
                    if (aluno.alterar(gerenciaConexao.getConexao())) {
                        resposta.put("status", true);
                        resposta.put("mensagem", "Aluno alterada com sucesso");
                        gerenciaConexao.getConexao().commit();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.ok(resposta);
                    } else {
                        resposta.put("status", false);
                        resposta.put("mensagem", "Aluno não foi alterada!");
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
    public ResponseEntity<Object> apagarAluno(String ra) {
        Map<String, Object> resposta = new HashMap<>();

        if (verificaIntegridade(ra)) {
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
    */

    public ResponseEntity<Object> buscarTodos() {
        Map<String, Object> resposta = new HashMap<>();
        GerenciaConexao gerenciaConexao = new GerenciaConexao();
        try {
            Aluno aluno = new Aluno();
            List<String> cpfsPessoa = new ArrayList<>();
            List<Aluno> alunoList = aluno.buscarTodos(gerenciaConexao.getConexao(), cpfsPessoa);
            if (alunoList != null ) {
                for (int i = 0; i < cpfsPessoa.size(); i++) {
                    Pessoa pessoa= new Pessoa(cpfsPessoa.get(i));
                    int id=pessoa.buscaPessoa(gerenciaConexao.getConexao(),pessoa);
                    pessoa.setEndereco(new Endereco().buscaEndereco(id,gerenciaConexao.getConexao()));
                    alunoList.get(i).setPessoa(pessoa);
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

   /* public ResponseEntity<Object> buscarAluno(String ra) {
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
    }*/



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

