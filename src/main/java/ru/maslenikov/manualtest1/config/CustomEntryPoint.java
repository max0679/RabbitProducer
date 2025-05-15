package ru.maslenikov.manualtest1.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class CustomEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            AuthenticationException e)
            throws IOException, ServletException {
        httpServletResponse.addHeader("messagee",  "error ");
       /* httpServletResponse
                .sendError(HttpStatus.UNAUTHORIZED.value(), "Поймали ошибку "  + e.getMessage());*/

        // Запишите тело ответа
        httpServletResponse.getWriter().write(e.getMessage());
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.flushBuffer();
        System.out.println("здесь");

    }

}