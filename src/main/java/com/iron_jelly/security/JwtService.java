package com.iron_jelly.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expirationHours}")
    private int expirationHours;

    private Algorithm algorithm;
    private JWTVerifier verifier;


    @PostConstruct
    public void init() {
        try {
            this.algorithm = Algorithm.HMAC256(secret);
            this.verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build();
        } catch (IllegalArgumentException e) {
            log.error("Error initializing JWT algorithm: {}", e.getMessage());
        }
    }

    public String generateToken(Authentication authentication) {
        Instant expirationTime = Instant.now().plus(expirationHours, ChronoUnit.HOURS);
        Date expirationDate = Date.from(expirationTime);
        String username = authentication.getName();

        return JWT.create()
                .withSubject(username)
                .withIssuer(issuer)
                .withExpiresAt(expirationDate)
                .sign(algorithm);
    }

    public boolean validateToken(String token) {
        try {
            return verifier.verify(token)
                    .getExpiresAt()
                    .after(new Date());
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return false;
    }

    public String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        } else if (authentication != null && authentication.getPrincipal() instanceof String username) {
            return username;
        }

        return "SYSTEM";
    }
}
