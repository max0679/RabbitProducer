package ru.maslenikov.springsecurityeducation.config.auth;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.maslenikov.springsecurityeducation.security.MyUserDetailsService;

import java.time.LocalDateTime;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final MyUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(MyUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        System.out.println("in Authentication authenticate " + LocalDateTime.now().toString());

        String username = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        //if (userDetails.getPassword().equals(passwordEncoder.encode(password))) {
        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            System.out.println("Authentication Success");
            return new UsernamePasswordAuthenticationToken(
                    userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        } else {
            System.out.println("Authentication Failure");
            throw new AuthenticationCredentialsNotFoundException(username);
        }

        /*if ("john".equals(username) &&
                "12345".equals(password))*/


    }

    @Override
    public boolean supports(Class<?> authenticationType) {
        return UsernamePasswordAuthenticationToken
                .class
                .isAssignableFrom(authenticationType);
    }
}
