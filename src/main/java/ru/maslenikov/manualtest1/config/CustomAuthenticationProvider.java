package ru.maslenikov.manualtest1.config;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final MyUserDetailsService userDetailsService;

    public CustomAuthenticationProvider(MyUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (userDetails.getPassword().equals(password)) {
            return new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        } else {
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
