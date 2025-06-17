package sistema.saga.sagabackend.control;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import sistema.saga.sagabackend.model.Profissional;
import sistema.saga.sagabackend.repository.GerenciaConexao;
import sistema.saga.sagabackend.security.JWTTokenProvider;

import java.util.HashMap;
import java.util.Map;

@Service
public class AcessoCtrl {
    public ResponseEntity<Object> autenticar(int  ra, String senha){
        String token=null;
        Map<String, Object> resposta = new HashMap<>();
        GerenciaConexao gerenciaConexao = new GerenciaConexao();
        try {
            Profissional usuario = new Profissional().buscaUsuario(gerenciaConexao.getConexao(), ra,senha);
            if (usuario != null) {
                resposta.put("status", true);
                resposta.put("usuario", usuario);
                resposta.put("token", JWTTokenProvider.getToken(usuario.getProfissional_usuario(),""+ usuario.getProfissional_senha()));
                gerenciaConexao.Desconectar();
                return ResponseEntity.ok(resposta);
            } else {
                usuario= new Profissional(ra);
                usuario=usuario.buscaProfissional(gerenciaConexao.getConexao(), usuario, new HashMap<>(), new HashMap<>());
                resposta.put("status", false);
                if(usuario!=null)
                    resposta.put("mensagem", "Senha incorreta para o usuário informado");
                else
                    resposta.put("mensagem", "Este Ra não está cadastrado!");
                gerenciaConexao.Desconectar();
                return ResponseEntity.badRequest().body(resposta);
            }
        } catch (Exception e) {
            resposta.put("status", false);
            resposta.put("mensagem", "Ocorreu um erro de conexão");
            gerenciaConexao.Desconectar();
            return ResponseEntity.badRequest().body(resposta);
        }
    }
}
