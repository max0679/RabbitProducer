package ru.maslenikov.manualtest1.config.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@PropertySource("classpath:security.properties")
@Getter
@Setter
public class StaticKeyAuthFilter extends OncePerRequestFilter {

    @Value("${auth.key}")
    private String authKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            System.out.println("in StaticKeyAuthFilter");
            String header = request.getHeader("Authorization");

            if (header!= null && !header.equals(authKey)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            if (header != null)
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("userr1", "passwordd1", List.of(() -> "ROLE_USER")));
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
       // return  (request.getHeader("Authorization").startsWith("Basic "));
        //return true;
        return true;
    }

}
