package ru.maslenikov.springsecurityeducation.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maslenikov.springsecurityeducation.models.Role;
import ru.maslenikov.springsecurityeducation.models.User;
import ru.maslenikov.springsecurityeducation.repositories.RoleRepository;
import ru.maslenikov.springsecurityeducation.repositories.UserRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;

    @PersistenceContext
    private EntityManager em;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Optional<Role> findRoleByName(String name) {
        return roleRepository.findRoleByName(name);
    }

//    @Transactional
//    public void initStartData() {
//        List<Role> roleList = Arrays.asList(new Role("ROLE_ADMIN"), new Role("ROLE_USER"), new Role("ROLE_OPERATOR"));
//        roleRepository.saveAll(roleList);
//    }

}

