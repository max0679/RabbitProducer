package ru.maslenikov.manualtest1.config.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.Assert;

import java.io.IOException;

//public class RequestValidationFilter implements Filter {
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        System.out.println("in our filter!");
//
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//
//        String needHeader = request.getHeader("Request-Id");
//
//        if (needHeader == null || needHeader.isBlank()) {
//            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Missing request ID");
//            return;
//        }
//
//        filterChain.doFilter(request, response);
//
//    }
//}
