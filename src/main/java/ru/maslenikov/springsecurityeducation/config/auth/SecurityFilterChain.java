package ru.maslenikov.springsecurityeducation.config.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import ru.maslenikov.springsecurityeducation.config.auth.csrf.CsrfLogFilter;
import ru.maslenikov.springsecurityeducation.config.auth.csrf.CustomCsrfTokenRepository;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityFilterChain {

    private final JWTFilter jwtFilter;
    private final CustomCsrfTokenRepository csrfTokenRepository;

    public SecurityFilterChain(JWTFilter jwtFilter, CustomCsrfTokenRepository csrfTokenRepository) {
        this.jwtFilter = jwtFilter;
        this.csrfTokenRepository = csrfTokenRepository;
    }

    @Bean
    public org.springframework.security.web.SecurityFilterChain configure(HttpSecurity http) throws Exception {
        // some-new-feat
        http
                //.addFilterBefore(requestValidationFilter, BasicAuthenticationFilter.class)
                .addFilterAt(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                //.addFilterAfter(new CsrfLogFilter(), CsrfFilter.class)
                .exceptionHandling(exception -> {
                    exception.authenticationEntryPoint(new CustomEntryPoint());
                })
                .authorizeHttpRequests(c -> {
                    c.requestMatchers("/login", "/registration").permitAll();
                    //c.anyRequest().hasRole("USER"); // some more
                    c.anyRequest().authenticated(); // some more
                })

                .csrf(c -> {
                    c.disable();
                    //c.ignoringRequestMatchers("/users/{name}");
                    //c.csrfTokenRepository(csrfTokenRepository);
                    //c.csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()); // CsrfTokenRequestAttributeHandler для управления обработкой токена CSRF в HTTP-запросе.
                })
                // какие хосты смогут использовать инфо с наших запросов, и какие методы и заголовки разрешены
                .cors(c -> {
                    CorsConfigurationSource source = request -> {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(
                                List.of("example.com", "example.org"));
                        config.setAllowedMethods(
                                List.of("GET", "POST", "PUT", "DELETE"));
                        config.setAllowedHeaders(List.of("*"));
                        return config;
                    };
                    c.configurationSource(source);
                });;
                //.headers(c -> c.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
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
