package ru.maslenikov.manualtest1.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.maslenikov.manualtest1.models.User;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class UsersBase {

    @Bean
    public List<User> getUsers() {
        return List.of(
            User.builder().name("max").password("max123").build(),
            User.builder().name("dima").password("dima123").build(),
            User.builder().name("stas").password("stas123").build()
        );
    }

}
