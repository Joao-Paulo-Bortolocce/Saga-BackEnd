package sistema.saga.sagabackend.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.saga.sagabackend.controller.FrequenciaCtrl;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "frequencia")
public class FrequenciaView {
    @Autowired
    private FrequenciaCtrl frequenciaCtrl;

    @PostMapping
    public ResponseEntity<Object> gravar(@RequestBody Map<String, Object> dados) {
        return frequenciaCtrl.gravarFrequencia(dados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarPorId(@PathVariable int id) {
        return frequenciaCtrl.buscarFreqAluno(id);
    }

}
