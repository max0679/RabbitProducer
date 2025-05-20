package ru.maslenikov.springsecurityeducation.mappers;

import org.mapstruct.Named;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Named("UserPasswordMapper")
public class UserPasswordMapper {

    private final PasswordEncoder passwordEncoder;

    public UserPasswordMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Named("convertPassword")
    public String convertPassword(String password) {
        return passwordEncoder.encode(password);
    }

}
