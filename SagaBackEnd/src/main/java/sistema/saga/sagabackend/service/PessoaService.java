package sistema.saga.sagabackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sistema.saga.sagabackend.dto.PessoaDTO;
import sistema.saga.sagabackend.model.Pessoa;
import sistema.saga.sagabackend.repository.PessoaDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PessoaService {
    @Autowired
    private PessoaDAO pessoaDAO;

    public Pessoa salvarPessoa(Pessoa pessoa) {
        return pessoaDAO.save(pessoa);
    }

    public void alterarPessoa(Pessoa pessoa) {
        pessoaDAO.save(pessoa);
    }

    public void apagarPessoa(Pessoa pessoa) {
        pessoaDAO.delete(pessoa);
    }

    public Pessoa buscarPessoaPorCpf(String cpf) {
        return pessoaDAO.findByCpf(cpf);
    }

    public List<Pessoa> buscarPessoasPorNome(String nome) {
        return pessoaDAO.findByNomeContaining(nome);
    }

    public List<Pessoa> buscarTodos() {
        return pessoaDAO.findAll();
    }

    public boolean existe(String cpf) {
        return pessoaDAO.existsById(cpf);
    }
}
