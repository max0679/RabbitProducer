package ru.maslenikov.springsecurityeducation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.maslenikov.springsecurityeducation.models.Role;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
public class UserDTO {

    private String username;
    private String password;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
    private LocalDate birthday;

    private Set<String> roles;

}
