package ru.maslenikov.springsecurityeducation.conrollers;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.maslenikov.springsecurityeducation.dto.UserDTO;
import ru.maslenikov.springsecurityeducation.mappers.UserMapper;
import ru.maslenikov.springsecurityeducation.models.Role;
import ru.maslenikov.springsecurityeducation.models.User;
import ru.maslenikov.springsecurityeducation.response.ExeceptionHandler;
import ru.maslenikov.springsecurityeducation.security.JWTUtil;
import ru.maslenikov.springsecurityeducation.services.UserService;

import java.util.Collections;

@RestController
public class LoginController extends ExeceptionHandler {

    private final UserMapper userMapper;
    private final UserService userService;
    private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public LoginController(UserMapper userMapper, UserService userService, JWTUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/registration")
    public HttpEntity<UserDTO> registration(@RequestBody UserDTO userDTO) {
        User user = userMapper.toUser(userDTO);
        userService.saveUser(user);
        return new ResponseEntity<UserDTO>(userMapper.toUserDTO(user), HttpStatus.OK);
    }

    @PostMapping("/login")
    public HttpEntity<String> login(@RequestBody UserDTO userDTO) {
        User user = userService.findByUsername(userDTO.getUsername()).orElseThrow(() -> new RuntimeException("Неверные учетные данные"));

        if (passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            return new ResponseEntity<>(jwtUtil.generateToken(user.getName()), HttpStatus.OK);
        }

        throw new RuntimeException("Неверные учетные данные");
    }

}
