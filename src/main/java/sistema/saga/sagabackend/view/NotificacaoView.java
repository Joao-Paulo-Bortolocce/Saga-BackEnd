package sistema.saga.sagabackend.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.saga.sagabackend.controller.NotificacaoCtrl;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/notificacao")
public class NotificacaoView {
    @Autowired
    NotificacaoCtrl notificacaoCtrl;

    @PostMapping
    public ResponseEntity<Object> gravar(@RequestBody Map<String, Object> dados) {
        return notificacaoCtrl.gravarNotificacao(dados);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> alterar(@PathVariable int id) {
        return notificacaoCtrl.alterarNotificacao(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> patch(@PathVariable int id) {
        return notificacaoCtrl.alterarNotificacao(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> excluir(@PathVariable int id) {
        return notificacaoCtrl.excluirNotificacao(id);
    }

    @GetMapping
    public ResponseEntity<Object> buscarTodos() {
        return notificacaoCtrl.buscarNotificacao();
    }
}

