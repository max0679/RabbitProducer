package ru.maslenikov.springsecurityeducation.config.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.method.MethodSecurityBeanDefinitionParser;
import org.springframework.stereotype.Component;
import ru.maslenikov.springsecurityeducation.security.UserPermissionEvaluator;


@Configuration
public class PermissionConfig  {

    private final UserPermissionEvaluator evaluator;

    public PermissionConfig(UserPermissionEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    // используется для @PreAuthorize и @PostAuthorize (например, @PostAuthorize("hasPermission(returnObject, 'ROLE_ADMIN')"))
    @Bean
    public  MethodSecurityExpressionHandler createExpressionHandler() {
        var expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(evaluator);
        return expressionHandler;
    }
}
