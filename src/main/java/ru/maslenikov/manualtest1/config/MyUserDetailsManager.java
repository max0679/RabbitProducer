package ru.maslenikov.manualtest1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@Configuration
public class MyUserDetailsManager {
    // если бы использовали БД
    @Bean
    public UserDetailsService h2MyUserDetailsService(DataSource dataSource) {
        var userDetailsManager = new JdbcUserDetailsManager(dataSource);
        userDetailsManager.setUsersByUsernameQuery("select username, password, enabled from spring.users where username = ?");
        userDetailsManager.setAuthoritiesByUsernameQuery("select username, authority from spring.authorities where username = ?");
        return userDetailsManager;
    }

}
