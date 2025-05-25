package sistema.saga.sagabackend.security.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sistema.saga.sagabackend.security.JWTTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;

public class AccessFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String token = req.getHeader("Authorization");

        boolean isAutenticacaoEndpoint = req.getRequestURI().contains("/autenticacao");

        if ((token != null && JWTTokenProvider.verifyToken(token)) || isAutenticacaoEndpoint) {
            chain.doFilter(request, response);
        } else {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
            res.setContentType("application/json");

            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("mensagem", "Não autorizado. Faça login para prosseguir.");

            ObjectMapper mapper = new ObjectMapper();
            res.getWriter().write(mapper.writeValueAsString(responseBody));
        }
    }
}


