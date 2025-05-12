package ru.maslenikov.manualtest1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityFilterConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    /***
     *         2. sameOrigin:
     • Этот параметр позволяет загружать фреймы только с того же источника (origin), что и основное приложение. "Происхождение" включает в себя протокол (HTTP/HTTPS), домен и порт.

     • Это означает, что если ваше приложение работает на http://localhost:8080, то оно может загружать фреймы только с этого же адреса. Если вы попытаетесь загрузить фрейм с другого домена или порта, это будет заблокировано.

     ▎Зачем это нужно в H2 Console?

     H2 Console — это веб-интерфейс для работы с базой данных H2. Он часто загружается в виде фрейма. Если вы не разрешите загрузку фреймов из того же источника, браузер может заблокировать отображение H2 Console, и вы получите ошибку 403 (Forbidden).
     ***/

    @Bean
    public SecurityFilterChain configure(HttpSecurity http)  throws Exception {
        http.httpBasic(Customizer.withDefaults());
        http.authorizeHttpRequests(
                c -> {
                    c.anyRequest().authenticated();
                }

        );
        http
            .csrf(AbstractHttpConfigurer::disable)
            .headers((c -> {
            c.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable);
        }));
        return http.build();
    }

//    @Bean
//    UserDetailsService userDetailsService() {
//        UserDetails user = User.withUsername("john")
//                .password("12345")
//                .authorities("read")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }
//
//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }


}
