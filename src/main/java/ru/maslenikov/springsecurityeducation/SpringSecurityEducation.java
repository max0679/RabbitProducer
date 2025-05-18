package ru.maslenikov.springsecurityeducation;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.maslenikov.springsecurityeducation.services.RoleService;
import ru.maslenikov.springsecurityeducation.services.UserService;

@SpringBootApplication
public class SpringSecurityEducation {

    private final RoleService roleService;

    public SpringSecurityEducation(RoleService roleService) {
        this.roleService = roleService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityEducation.class, args);
    }

    @PostConstruct
    public void init() {

        roleService.initStartData();

    }

}
