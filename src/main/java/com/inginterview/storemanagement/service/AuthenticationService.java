package com.inginterview.storemanagement.service;

import com.inginterview.storemanagement.model.JwtAuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private static final String TOKEN_TYPE = "Bearer";

    private final AuthenticationManager authenticationManager;
    private final TokenAuthenticationService tokenProvider;

    public JwtAuthenticationResponse authenticateAndGenerateToken(String username, String password) {
        final var authToken = new UsernamePasswordAuthenticationToken(username, password);
        final var authentication = authenticationManager.authenticate(authToken);
        final var jwt = tokenProvider.generate(authentication);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return JwtAuthenticationResponse.builder()
                .tokenType(TOKEN_TYPE)
                .accessToken(jwt)
                .build();
    }
}
