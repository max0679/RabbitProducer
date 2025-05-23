package ru.maslenikov.springsecurityeducation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Service
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO {

    private int total;
    private List<UserDTO> users;

}
