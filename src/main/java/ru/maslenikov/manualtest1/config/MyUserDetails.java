package ru.maslenikov.manualtest1.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.maslenikov.manualtest1.models.User;

import java.util.Collection;
import java.util.List;

public class MyUserDetails implements UserDetails {

    private final User user;

    public MyUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }
}
