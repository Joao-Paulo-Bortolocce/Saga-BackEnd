package sistema.saga.sagabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sistema.saga.sagabackend.model.Materia;


@Repository
public class MateriaDAO extends JpaRepository<Materia> {

    Materia findbyId(int id);

    List<Materia> findByNomeContaining(String nome);

}