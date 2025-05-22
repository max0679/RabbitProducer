package ru.maslenikov.springsecurityeducation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.maslenikov.springsecurityeducation.models.CSRFToken;

import java.util.Optional;

public interface CSRFTokenRepository extends JpaRepository<CSRFToken, Integer> {
    Optional<CSRFToken> findTokenByIdentifier(String identifier);
}
