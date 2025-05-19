package ru.maslenikov.springsecurityeducation.mappers;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import ru.maslenikov.springsecurityeducation.models.Role;
import ru.maslenikov.springsecurityeducation.services.RoleService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Named("RoleListMapper")
@Component
public class RoleListMapper {
    private final RoleService roleService;

    public RoleListMapper(RoleService roleService) {
        this.roleService = roleService;
    }

    @Named("getRole")
    public Role getRoleByName(String name) {
        return roleService.findRoleByName(name).orElse(new Role(name));
    }

//    @Named("getListOfRoles")
//    public Set<Role> getListOfRoles(Set<String> roles) {
//        return roles.stream().map(el -> roleService.findRoleByName(el).orElse((new Role(el)))).collect(Collectors.toSet());
//    }

}
