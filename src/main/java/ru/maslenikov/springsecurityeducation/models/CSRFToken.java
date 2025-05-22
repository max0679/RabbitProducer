package ru.maslenikov.springsecurityeducation.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "csrf_token")
@Getter
@Setter
public class CSRFToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "identifier", nullable = false, length = 45)
    private String identifier;

    @Column(name = "token")
    private String token;

}
