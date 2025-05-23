package ru.maslenikov.springsecurityeducation.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
@PropertySource("classpath:security.properties")
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    private static final String ISSUER = "ru.maslenikov";
    private static final String SUBJECT = "User details";

    public String generateToken(String username) {
        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(60).toInstant());

        return JWT.create()
                .withSubject(SUBJECT)
                .withClaim("username", username)
                .withExpiresAt(expirationDate)
                .withIssuer(ISSUER)
                .withIssuedAt(new Date())
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetrieveUsername(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withIssuer(ISSUER)
                .withSubject(SUBJECT)
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("username").asString();
    }


}
