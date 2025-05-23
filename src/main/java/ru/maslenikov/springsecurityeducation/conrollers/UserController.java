package ru.maslenikov.springsecurityeducation.conrollers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.bind.annotation.*;
import ru.maslenikov.springsecurityeducation.dto.UserDTO;
import ru.maslenikov.springsecurityeducation.mappers.UserMapper;
import ru.maslenikov.springsecurityeducation.models.CSRFToken;
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
    @PostFilter("filterObject.username.equals(authentication.name)")
    public List<UserDTO> getAllUsers() {
        return userMapper.toUserDTOList(userService.getAllUsers());
    }

    @GetMapping("/{name}")
    //@PostAuthorize("returnObject.username.equals('max')")
    @PreAuthorize("hasRole('ADMIN')")
    public UserDTO getUser(HttpServletRequest request, @PathVariable String name) {
        System.out.println("внутри контроллера");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //CsrfToken handler = (CsrfToken) request.getAttribute("_csrf");
        //System.out.println("Этот токен был сгенерирован при вызове get-запроса: " + handler.getToken());
        return userMapper.toUserDTO(userService.findByUsername(name).orElse(null));
    }

    @GetMapping("/{name}/one_more_test")
    @PostAuthorize("hasPermission(returnObject, 'ROLE_ADMIN')")
    public UserDTO getUserOneMore(HttpServletRequest request, @PathVariable String name) {
        System.out.println("внутри контроллера");
        return userMapper.toUserDTO(userService.findByUsername(name).orElse(null));
    }

    @PostMapping("/{name}")
    //@CrossOrigin("/someHost")
    public String getUserPost(@PathVariable String name, @RequestParam(value = "test", defaultValue = "defaultVal") String test) {
        return name + " " + test;
    }

}
