package ru.maslenikov.springsecurityeducation.conrollers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.maslenikov.springsecurityeducation.dto.UserDTO;
import ru.maslenikov.springsecurityeducation.mappers.UserMapper;
import ru.maslenikov.springsecurityeducation.models.User;
import ru.maslenikov.springsecurityeducation.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("")
    public List<UserDTO> getAllUsers() {
        return userMapper.toUserDTOList(userService.getAllUsers());
    }

    @GetMapping("/{name}")
    public UserDTO getUser(@PathVariable String name) {
        return userMapper.toUserDTO(userService.findByUsername(name).orElse(null));
    }

}
