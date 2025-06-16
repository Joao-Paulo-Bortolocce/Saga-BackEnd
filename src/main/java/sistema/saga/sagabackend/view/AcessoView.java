package sistema.saga.sagabackend.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.saga.sagabackend.control.AcessoCtrl;

@CrossOrigin
@RestController
@RequestMapping("autenticacao")
public class AcessoView {

    @Autowired
    AcessoCtrl acessoCtrl;

    @GetMapping("/{ra}/{senha}")
    ResponseEntity<Object> autenticar(@PathVariable(name = "ra") int ra, @PathVariable(name = "senha") String senha){
        return  acessoCtrl.autenticar(ra,senha);
    }
}