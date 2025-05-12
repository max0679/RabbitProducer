package ru.maslenikov.manualtest1.config;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.maslenikov.manualtest1.models.User;

import java.util.List;
import java.util.Optional;

@Component("MyUserDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    private final List<User> users;

    public MyUserDetailsService(List<User> users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new MyUserDetails(users.stream().filter(user1 -> user1.getName().equals(username)).findFirst().orElseThrow(() -> new UsernameNotFoundException(username)));
    }
}
