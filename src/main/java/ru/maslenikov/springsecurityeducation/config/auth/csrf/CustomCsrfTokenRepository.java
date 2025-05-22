package ru.maslenikov.springsecurityeducation.config.auth.csrf;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Component;
import ru.maslenikov.springsecurityeducation.models.CSRFToken;
import ru.maslenikov.springsecurityeducation.services.CSRFTokenService;

import java.util.Optional;
import java.util.UUID;

@Component
public class CustomCsrfTokenRepository implements CsrfTokenRepository {

    private final CSRFTokenService csrfTokenService;

    public CustomCsrfTokenRepository(CSRFTokenService csrfTokenService) {
        this.csrfTokenService = csrfTokenService;
    }

    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        String uuid = UUID.randomUUID().toString();
        //те же имена для заголовка запроса и атрибута, X-CSRF-TOKEN и _csrf,
        //как в реализации по умолчанию, предлагаемой Spring Security.
        return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", uuid);
    }

    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
        String identifier = request.getHeader("X-IDENTIFIER");

//        if (identifier == null || identifier.isBlank()) {
//            throw new IllegalArgumentException("X-CSRF-TOKEN header is missing");
//        }

        Optional<CSRFToken> csrfToken = csrfTokenService.findTokenByIdentifier(identifier);

        if (csrfToken.isPresent()) {
            csrfToken.get().setToken(token.getToken());
        } else {
            CSRFToken newToken = new CSRFToken();
            newToken.setToken(token.getToken());
            newToken.setIdentifier(identifier);
            csrfTokenService.saveToken(newToken);
        }

    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        String token = request.getHeader("X-IDENTIFIER");
        if (token != null && !token.isBlank()) {
            CSRFToken token1 = csrfTokenService.findTokenByIdentifier(token).orElse(null);
            return token1 == null ? null : new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", token1.getToken());
        }
        return null;
    }
}
