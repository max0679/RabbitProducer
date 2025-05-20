package ru.maslenikov.springsecurityeducation.config.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityFilterChain {

    private final JWTFilter jwtFilter;

    public SecurityFilterChain(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public org.springframework.security.web.SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http
            //.addFilterBefore(requestValidationFilter, BasicAuthenticationFilter.class)
            .addFilterAt(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(exception -> {
                exception.authenticationEntryPoint(new CustomEntryPoint());
            })
            .authorizeHttpRequests(c -> {
                c.requestMatchers("/login", "/registration").permitAll();
                c.anyRequest().hasRole("USER");
            })
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
