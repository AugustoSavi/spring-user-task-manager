package com.spring.todolist.middlware;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.spring.todolist.user.User;
import com.spring.todolist.user.repository.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class middlwareTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository iUserRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!request.getServletPath().startsWith("/tasks")){
            filterChain.doFilter(request, response);
        }
        else {
            String authorization = request.getHeader("Authorization").substring("Basic".length()).trim();
            String decoded = new String(Base64.getDecoder().decode(authorization));
            String[] userData = decoded.split(":");
            String username = userData[0];
            String password = userData[1];

            User usuario = iUserRepository.findByUsername(username);
            if (usuario == null) {
                response.sendError(401);
            } else {
                BCrypt.Result verified = BCrypt.verifyer().verify(password.toCharArray(), usuario.getPassword());
                if (!verified.verified) {
                    response.sendError(401);
                }
                request.setAttribute("idUsuario", usuario.getId());
                filterChain.doFilter(request, response);
            }
        }
    }
}
