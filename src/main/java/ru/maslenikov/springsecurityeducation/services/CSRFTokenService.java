package ru.maslenikov.springsecurityeducation.services;

import org.springframework.stereotype.Service;
import ru.maslenikov.springsecurityeducation.models.CSRFToken;
import ru.maslenikov.springsecurityeducation.repositories.CSRFTokenRepository;

import java.util.Optional;

@Service
public class CSRFTokenService {

    private final CSRFTokenRepository csrfTokenRepository;

    public CSRFTokenService(CSRFTokenRepository csrfTokenRepository) {
        this.csrfTokenRepository = csrfTokenRepository;
    }

    public Optional<CSRFToken> findTokenByIdentifier(String identifier) {
        return csrfTokenRepository.findTokenByIdentifier(identifier);
    }

    public void saveToken(CSRFToken token) {
        csrfTokenRepository.save(token);
    }

}
