package ru.maslenikov.manualtest1.config.auth;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.maslenikov.manualtest1.models.User;

import java.util.List;

@Configuration
public class UsersBase {

    private final PasswordEncoder passwordEncoder;

    public UsersBase(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public List<User> getUsers() {
        return List.of(
            User.builder().name("max").password(passwordEncoder.encode("max123")).role("ROLE_ADMIN").build(),
            User.builder().name("dima").password(passwordEncoder.encode("dima123")).role("ROLE_USER").build(),
            User.builder().name("stas").password(passwordEncoder.encode("stas123")).role("ROLE_USER").build()
        );
    }

}
