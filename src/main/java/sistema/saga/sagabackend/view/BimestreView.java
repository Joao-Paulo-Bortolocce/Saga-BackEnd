package sistema.saga.sagabackend.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sistema.saga.sagabackend.control.BimestreCtrl;

@CrossOrigin
@RestController
@RequestMapping("bimestre")
public class BimestreView {

    @Autowired
    BimestreCtrl bimestreCtrl;

    @GetMapping
    public ResponseEntity<Object> buscarTodos() {
        return bimestreCtrl.buscarTodos();
    }

}
