package com.inginterview.storemanagement.service;

import com.inginterview.storemanagement.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TokenAuthenticationService {

    private final static String PERMISSIONS = "permissions";

    private final PublicKey publicKey;
    private final PrivateKey privateKey;

    @Value("${spring.application.name}")
    private String serviceName;

    @Value("${ism.auth.token.expiration}")
    private Long tokenExpirationInMs;

    public String generate(Authentication authentication) {
        final var now = new Date();
        final var expiryDate = new Date(now.getTime() + tokenExpirationInMs);
        final var username = authentication.getName();

        return Jwts.builder()
                .subject(username)
                .issuer(serviceName)
                .issuedAt(now)
                .expiration(expiryDate)
                .claims(Map.of(PERMISSIONS, authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()))
                .signWith(privateKey, Jwts.SIG.RS512)
                .compact();
    }

    public String getUsername(String token) {
        return getPayload(token).get("sub", String.class);
    }

    public boolean isValid(String token) {
        try {
            getPayload(token); // Throws an JwtException exception if the token is invalid
            return true;
        } catch (JwtException exception) {
            throw new InvalidTokenException("Token is invalid", exception);
        }
    }

    private Claims getPayload(String token) {
        return Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
