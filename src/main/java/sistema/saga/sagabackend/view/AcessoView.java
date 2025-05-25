package sistema.saga.sagabackend.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.saga.sagabackend.controller.AcessoCtrl;

@CrossOrigin
@RestController
@RequestMapping("autenticacao")
public class AcessoView {

    @Autowired
    AcessoCtrl acessoCtrl;

    @GetMapping("/{ra}/{senha}")
    ResponseEntity<Object> autenticar(@PathVariable int ra, @PathVariable String senha){
        return  acessoCtrl.autenticar(ra,senha);
    }
}