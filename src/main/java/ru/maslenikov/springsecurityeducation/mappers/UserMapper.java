package ru.maslenikov.springsecurityeducation.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;
import ru.maslenikov.springsecurityeducation.dto.UserDTO;
import ru.maslenikov.springsecurityeducation.models.Role;
import ru.maslenikov.springsecurityeducation.models.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {RoleListMapper.class, UserPasswordMapper.class})
public interface UserMapper {

    default Set<String> mapRoles(Set<Role> roles) {
        if (roles == null) {
            return null;
        }
        return roles.stream()
                .map(Role::getName) // Предполагаем, что у Role есть метод getName()
                .collect(Collectors.toSet());
    }

    @Mappings({
            @Mapping(target = "roles", ignore = true),
    })
    UserDTO toUserDTO(User user);
//
//    List<UserDTO> toUserDTOList(List<User> users);

    @Mappings({
            @Mapping(source = "birthday", target = "birth"),
            @Mapping(source = "username", target = "name"),
            @Mapping(source = "roles", target = "roles", qualifiedByName = {"RoleListMapper", "getRole"}),
            @Mapping(source = "password", target = "password", qualifiedByName = {"UserPasswordMapper", "convertPassword"})
    })
    User toUser(UserDTO userDTO);

    List<User> toUserList(List<UserDTO> userDTOList);

}
