package sistema.saga.sagabackend.control;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sistema.saga.sagabackend.model.Graduacao;
import sistema.saga.sagabackend.model.Profissional;
import sistema.saga.sagabackend.model.Pessoa;
import sistema.saga.sagabackend.model.Turma;
import sistema.saga.sagabackend.repository.GerenciaConexao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProfissionalCtrl {
    public ResponseEntity<Object> gravarProfissional(Map<String, Object> dados) {
        Map<String, Object> resposta = new HashMap<>();
        int rn = Integer.parseInt((String) dados.get("profissional_rn"));
        int tipo = Integer.parseInt((String) dados.get("profissional_tipo"));
        String dataStr = (String) dados.get("profissional_dataAdmissao");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dataAdmissao = LocalDate.parse(dataStr, formatter);
        String user = (String) dados.get("profissional_usuario");
        String senha = (String) dados.get("profissional_senha");
        Map<String, Object> pessoa = (Map<String, Object>) dados.get("profissional_pessoa");
        String cpf = (String) pessoa.get("cpf");
        Map<String, Object> graduacao = (Map<String, Object>) dados.get("profissional_graduacao");
        int idGraduacao = Integer.parseInt((String) graduacao.get("id"));

        if (Regras.verificaIntegridade(rn) &&
                Regras.verificaIntegridade(tipo) &&
                Regras.verificaIntegridade(cpf)&&
                Regras.verificaIntegridade(dataAdmissao) &&
                Regras.verificaIntegridade(user) &&
                Regras.verificaIntegridade(senha) &&
                Regras.verificaIntegridade(idGraduacao)) {
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
                        resposta.put("mensagem", "As informações pessoais do profissional não estão cadastrados");
                        //roolback; end trasaction;
                        gerenciaConexao.getConexao().rollback();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }

                    Graduacao graduacaoAux= new Graduacao(idGraduacao,"");

                    if (graduacaoAux.buscaGraduacao(gerenciaConexao.getConexao())==0) {
                        resposta.put("status", false);
                        resposta.put("mensagem", "As informações da graduação do profissional não estão cadastradas");
                        //roolback; end trasaction;
                        gerenciaConexao.getConexao().rollback();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }

                    Profissional profissional= new Profissional(rn,tipo,pessoaAux, graduacaoAux,dataAdmissao,user,senha);
                    if(profissional.buscaProfissional(gerenciaConexao.getConexao(),profissional,pessoa,graduacao)!=null){
                        resposta.put("status", false);
                        resposta.put("mensagem", "Este rn já esta cadastrado!");
                        //roolback; end trasaction;
                        gerenciaConexao.getConexao().rollback();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }
                    pessoaAux.setEndereco(Regras.HashToEndereco(end));
                    if (profissional.gravar(gerenciaConexao.getConexao())) {
                        resposta.put("status", true);
                        resposta.put("mensagem", "Profissional Inserida com sucesso");
                        //commit; end transaction;
                        gerenciaConexao.getConexao().commit();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.ok(resposta);
                    } else {
                        resposta.put("status", false);
                        resposta.put("mensagem", "Profissional não foi inserido!");
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

    public ResponseEntity<Object> alterarProfissional(Map<String, Object> dados) {
        Map<String, Object> resposta = new HashMap<>();
        int rn = Integer.parseInt(""+ dados.get("profissional_rn"));
        int tipo = Integer.parseInt(""+ dados.get("profissional_tipo"));
        String dataStr = (String) dados.get("profissional_dataAdmissao");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dataAdmissao = LocalDate.parse(dataStr, formatter);
        String user = (String) dados.get("profissional_usuario");
        String senha = (String) dados.get("profissional_senha");
        Map<String, Object> pessoa = (Map<String, Object>) dados.get("profissional_pessoa");
        String cpf = (String) pessoa.get("cpf");
        Map<String, Object> graduacao = (Map<String, Object>) dados.get("profissional_graduacao");
        int idGraduacao = Integer.parseInt(""+ graduacao.get("id"));

        if (Regras.verificaIntegridade(rn) &&
                Regras.verificaIntegridade(tipo) &&
                Regras.verificaIntegridade(cpf)&&
                Regras.verificaIntegridade(dataAdmissao) &&
                Regras.verificaIntegridade(user) &&
                Regras.verificaIntegridade(senha) &&
                Regras.verificaIntegridade(idGraduacao)) {
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
                        resposta.put("mensagem", "As informações pessoais do profissional não estão cadastrados");
                        //roolback; end trasaction;
                        gerenciaConexao.getConexao().rollback();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }

                    Graduacao graduacaoAux= new Graduacao(idGraduacao,"");

                    if (graduacaoAux.buscaGraduacao(gerenciaConexao.getConexao())==0) {
                        resposta.put("status", false);
                        resposta.put("mensagem", "As informações da graduação do profissional não estão cadastradas");
                        //roolback; end trasaction;
                        gerenciaConexao.getConexao().rollback();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }

                    Profissional profissional= new Profissional(rn,tipo,pessoaAux, graduacaoAux,dataAdmissao,user,senha);
                    if(profissional.buscaProfissional(gerenciaConexao.getConexao(),profissional,pessoa,graduacao)==null){
                        resposta.put("status", false);
                        resposta.put("mensagem", "Este rn não esta cadastrado!");
                        //roolback; end trasaction;
                        gerenciaConexao.getConexao().rollback();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }
                    pessoaAux.setEndereco(Regras.HashToEndereco(end));
                    if (profissional.alterar(gerenciaConexao.getConexao())) {
                        resposta.put("status", true);
                        resposta.put("mensagem", "Profissional alterado com sucesso");
                        //commit; end transaction;
                        gerenciaConexao.getConexao().commit();
                        gerenciaConexao.getConexao().fimTransacao();
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.ok(resposta);
                    } else {
                        resposta.put("status", false);
                        resposta.put("mensagem", "Profissional não foi alterado!");
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

    public ResponseEntity<Object> apagarProfissional(int  rn) {
        Map<String, Object> resposta = new HashMap<>();

        if (Regras.verificaIntegridade(rn)) {
            try {
                Profissional profissional = new Profissional(rn);
                GerenciaConexao gerenciaConexao = new GerenciaConexao();
                List<Map<String, Object>> pessoas = new ArrayList<>();
//                List<Profissional> profissionalList= profissional.buscarTodosSemMatricula(gerenciaConexao.getConexao(),0,pessoas); Ver se não há registros desse funcionario
//                int i;
//                for (i = 0; i < profissionalList.size() && rn!=profissionalList.get(i).getRa(); i++) ;
//                if(i==profissionalList.size()){
//                    resposta.put("status", false);
//                    resposta.put("mensagem", "Exclusão não pode ser realizada, pois existem matriculas cadastrados para esse profissional!");
//                    gerenciaConexao.Desconectar();
//                    return ResponseEntity.badRequest().body(resposta);
//                }
                Map<String, Object> aux= new HashMap<>();
                profissional=profissional.buscaProfissional(gerenciaConexao.getConexao(),profissional,aux,aux);
                if(profissional.getProfissional_tipo()==3){
                   List<Map<String,Object>> graduacoes= new ArrayList<>();
                    if(profissional.buscarGestao(gerenciaConexao.getConexao(),pessoas,graduacoes).size()==1){
                        resposta.put("status", false);
                        resposta.put("mensagem", "Sistema não pode ficar sem nenhum profissional de gestão cadastrado, insira outro para poder excluir este!");
                        gerenciaConexao.Desconectar();
                        return ResponseEntity.badRequest().body(resposta);
                    }
                }

                if(new Turma().buscaTurmasDoProfessor(gerenciaConexao.getConexao(),profissional.getProfissional_rn()).size()>0){
                    resposta.put("status", false);
                    resposta.put("mensagem", "Este profissional possui registros e não pode ser excluido");
                    gerenciaConexao.Desconectar();
                    return ResponseEntity.badRequest().body(resposta);
                }
                if (profissional.apagar(gerenciaConexao.getConexao())) {
                    resposta.put("status", true);
                    resposta.put("mensagem", "Profissional excluído com sucesso!");
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
            Profissional profissional = new Profissional();
            List<Map<String, Object>> pessoas = new ArrayList<>();
            List<Map<String, Object>> graduacoes = new ArrayList<>();
            List<Profissional> profissionalList = profissional.buscarTodos(gerenciaConexao.getConexao(), pessoas,graduacoes);
            if (profissionalList != null) {
                for (int i = 0; i < pessoas.size(); i++) {
                    Map<String, Object> pessoa = pessoas.get(i);
                    Map<String, Object> graduacao = graduacoes.get(i);
                    profissionalList.get(i).setProfissional_pessoa(Regras.HashToPessoa(pessoa));
                    profissionalList.get(i).setProfissional_graduacao(Regras.HashToGraduacao(graduacao));

                }
                resposta.put("status", true);
                resposta.put("listaDeProfissionais", profissionalList);
                gerenciaConexao.Desconectar();
                return ResponseEntity.ok(resposta);
            } else {
                resposta.put("status", false);
                resposta.put("mensagem", "Não existem profissionais cadastrados");
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

    public ResponseEntity<Object> buscarProfissional(int rn) {
        Map<String, Object> resposta = new HashMap<>();
        GerenciaConexao gerenciaConexao = new GerenciaConexao();
        try {
            Profissional profissional = new Profissional(rn);
            Map<String, Object> pessoa= new HashMap<>();
            Map<String, Object> graduacao= new HashMap<>();
            profissional = profissional.buscaProfissional(gerenciaConexao.getConexao(), profissional,pessoa,graduacao);
            if (profissional != null) {
                profissional.setProfissional_pessoa(Regras.HashToPessoa(pessoa));
                profissional.setProfissional_graduacao(Regras.HashToGraduacao(graduacao));
                resposta.put("status", true);
                resposta.put("profissional", profissional);
                gerenciaConexao.Desconectar();
                return ResponseEntity.ok(resposta);
            } else {
                resposta.put("status", false);
                resposta.put("mensagem", "Não existe profissional cadastrado com o rn: "+rn);
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

    public ResponseEntity<Object> buscarUsuario(int rn, String senha) {
        Map<String, Object> resposta = new HashMap<>();
        GerenciaConexao gerenciaConexao = new GerenciaConexao();
        try {
            Profissional usuario = new Profissional().buscaUsuario(gerenciaConexao.getConexao(), rn,senha);
            if (usuario != null) {
                resposta.put("status", true);
                resposta.put("usuario", usuario);
                gerenciaConexao.Desconectar();
                return ResponseEntity.ok(resposta);
            } else {
                usuario= new Profissional(rn);
                usuario=usuario.buscaProfissional(gerenciaConexao.getConexao(), usuario, new HashMap<>(), new HashMap<>());
                resposta.put("status", false);
                if(usuario!=null)
                    resposta.put("mensagem", "Senha incorreta para o usuário informado");
                else
                    resposta.put("mensagem", "Este rn não está cadastrado!");
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
