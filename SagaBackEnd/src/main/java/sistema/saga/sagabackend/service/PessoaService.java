package sistema.saga.sagabackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sistema.saga.sagabackend.model.Pessoa;
import sistema.saga.sagabackend.repository.PessoaDAO;

import java.util.List;

@Service
public class PessoaService {
    private final PessoaDAO pessoaDAO;

    @Autowired
    public PessoaService(PessoaDAO pessoaDAO) {
        this.pessoaDAO = pessoaDAO;
    }

    public Pessoa salvarPessoa(Pessoa pessoa) {
        return pessoaDAO.save(pessoa);
    }

    public Pessoa buscarPessoaPorCpf(String cpf) {
        return pessoaDAO.findByCpf(cpf);
    }

    public List<Pessoa> buscarPessoasPorNome(String nome) {
        return pessoaDAO.findByNomeContaining(nome);
    }
}
