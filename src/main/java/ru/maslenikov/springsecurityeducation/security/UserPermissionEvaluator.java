package ru.maslenikov.springsecurityeducation.security;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.maslenikov.springsecurityeducation.dto.UserDTO;

import java.io.Serializable;

/**
 Данный контракт предоставляет два способа реализации логики
 разрешений:
  по объекту и разрешению – используется в текущем примере,
 предполагает, что оценщик разрешений получает два объекта:
 один, который подчиняется правилу авторизации, и один, ко
 торый предлагает дополнительные сведения, необходимые для
 реализации логики разрешений;
  по идентификатору объекта, типу объекта и разрешению –
 предполагает, что оценщик разрешений получает идентифика
 тор объекта, который он может использовать для получения не
 обходимого объекта. Он также получает тип объекта, который
 может использоваться, если один и тот же оценщик разрешений применяется к нескольким типам объектов, и ему нужен
 объект, предлагающий дополнительные сведения для оценки
 разрешения.
 */


@Component
public class UserPermissionEvaluator implements PermissionEvaluator {
    // использую в @PostAuthorize (@PostAuthorize("hasPermission(returnObject, 'ROLE_ADMIN')"))
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
