package sistema.saga.sagabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sistema.saga.sagabackend.model.Pessoa;

import java.util.List;

@Repository
public interface PessoaDAO extends JpaRepository<Pessoa, String> {



    Pessoa findByCpf(String cpf);

    List<Pessoa> findByNomeContaining(String nome);  //select * from pessoa where pessoa_nome like %nome%

}
