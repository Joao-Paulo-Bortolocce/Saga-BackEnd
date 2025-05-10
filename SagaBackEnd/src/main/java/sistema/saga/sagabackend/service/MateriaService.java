package sistema.saga.sagabackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sistema.saga.sagabackend.dto.MateriaDTO;
import sistema.saga.sagabackend.model.Materia;
import sistema.saga.sagabackend.repository.MateriaDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MateriaService {

    @Autowired
    private MateriaDAO materiaDAO;

    public Materia salvarMateria(Materia materia) {
        return materiaDAO.save(materia);
    }

    public void alterarMateria(Materia materia) {
        materiaDAO.save(materia);
    }

    public void apagarMateria(Materia materia) {
        materiaDAO.delete(materia);
    }

    public Materia buscarMateriaPorId(int id) {
        return pessoaDAO.findById(id);
    }

    public List<Materia> buscarMateriaPorNome(String nome) {
        return materiaDAO.findByNomeContaining(nome);
    }

    public List<Materia> buscarTodos() {
        return materiaDAO.findAll();
    }

    public boolean existe(int id) {
        return materiaDAO.existsById(id);
    }

}