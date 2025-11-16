package com.gestao.back.model.filter;

import com.gestao.back.model.context.UsuarioContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class UsuarioFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String usuario = request.getHeader("X-User");
            if (usuario != null && !usuario.isBlank()) {
                UsuarioContext.setUsuario(usuario);
            }

            filterChain.doFilter(request, response);

        } finally {
            UsuarioContext.limpar();
        }
    }
}
