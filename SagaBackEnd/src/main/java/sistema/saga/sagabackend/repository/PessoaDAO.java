package sistema.saga.sagabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sistema.saga.sagabackend.model.Pessoa;

import java.util.List;

public interface PessoaDAO extends JpaRepository<Pessoa, String> {

    Pessoa findByCpf(String cpf);

    List<Pessoa> findByNomeContaining(String nome);
}
