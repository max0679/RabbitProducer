package ru.maslenikov.springsecurityeducation.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maslenikov.springsecurityeducation.models.Role;
import ru.maslenikov.springsecurityeducation.models.User;
import ru.maslenikov.springsecurityeducation.repositories.UserRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    public UserService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findUserByName(username);
    }

    public void saveUser(User user) {
        //user.setRoles(Collections.singleton(roleService.findRoleByName("ROLE_USER").orElse(new Role("ROLE_USER"))));
        userRepository.save(user);
        log.info("User saved: " + user);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        //return userRepository.getAllUsers(PageRequest.of(0, 3));
        Page<User> users = userRepository.findAll(PageRequest.of(0, 3));
        return userRepository.findUsersByUsers(users.getContent());
    }
}
