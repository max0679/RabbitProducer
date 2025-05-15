package ru.maslenikov.manualtest1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import ru.maslenikov.manualtest1.config.filters.AutheticationLoggingFilter;
import ru.maslenikov.manualtest1.config.filters.RequestValidationFilter;
import ru.maslenikov.manualtest1.config.filters.StaticKeyAuthFilter;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SecurityFilterConfig {

    private final StaticKeyAuthFilter staticKeyAuthFilter;
    private final RequestValidationFilter requestValidationFilter;
    private final AutheticationLoggingFilter autheticationLoggingFilter;

    public SecurityFilterConfig(StaticKeyAuthFilter staticKeyAuthFilter, RequestValidationFilter requestValidationFilter, AutheticationLoggingFilter autheticationLoggingFilter) {
        this.staticKeyAuthFilter = staticKeyAuthFilter;
        this.requestValidationFilter = requestValidationFilter;
        this.autheticationLoggingFilter = autheticationLoggingFilter;
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
        http
            .addFilterBefore(requestValidationFilter, BasicAuthenticationFilter.class)
            .addFilterAt(staticKeyAuthFilter, BasicAuthenticationFilter.class)
            .addFilterAfter(autheticationLoggingFilter, BasicAuthenticationFilter.class)
            .httpBasic(c -> {
                c.authenticationEntryPoint(new CustomEntryPoint());
            }) //  BasicAuthenticationFilter добавится в цепочку фильтров.
            .authorizeHttpRequests(c -> c.anyRequest().permitAll())
            .csrf(AbstractHttpConfigurer::disable)
            .headers(c -> c.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //return new BCryptPasswordEncoder();
        //return NoOpPasswordEncoder.getInstance();

/*        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("bcrypt", new BCryptPasswordEncoder());*/
        //encoders.put("scrypt", new SCryptPasswordEncoder());
        // return new DelegatingPasswordEncoder("bcrypt", encoders);
        /** строка ниже уже имеет функционал, указанный выше, и определяет по умолчанию алгоритм bcrypt **/
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
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
