package ru.maslenikov.springsecurityeducation.security;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.maslenikov.springsecurityeducation.dto.UserDTO;

import java.io.Serializable;

@Component
public class UserPermissionEvaluator implements PermissionEvaluator {
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        UserDTO user = (UserDTO) targetDomainObject;
        String p = (String) permission;
        boolean admin = authentication.getAuthorities().stream().anyMatch(g -> g.getAuthority().equals(permission));

        return admin ||
               user.getUsername().equals("max");
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
