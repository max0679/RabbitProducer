package ru.maslenikov.manualtest1.config.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class AutheticationLoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) servletRequest;
        var requestId = httpRequest.getHeader("Request-Id");
        log.info("Successfully authenticated  request with id " + requestId);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
