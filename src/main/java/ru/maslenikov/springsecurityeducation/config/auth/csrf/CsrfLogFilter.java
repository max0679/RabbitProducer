package ru.maslenikov.springsecurityeducation.config.auth.csrf;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class CsrfLogFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // потом в хедере прописать X-CSRF-TOKEN с выведенным значением

        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");

        System.out.println("Сгенерированный csrf: " + token.getToken());

        filterChain.doFilter(request, response);

    }
}
