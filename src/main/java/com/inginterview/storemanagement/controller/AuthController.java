package com.inginterview.storemanagement.controller;

import com.inginterview.storemanagement.model.JwtAuthenticationResponse;
import com.inginterview.storemanagement.model.LoginRequest;
import com.inginterview.storemanagement.service.TokenAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenAuthenticationService tokenProvider;

    @PostMapping(value = "/token")
    @ResponseStatus(HttpStatus.OK)
    public JwtAuthenticationResponse authenticate(@RequestBody LoginRequest loginRequest) {
        final var authToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword());
        final var authentication = authenticationManager.authenticate(authToken);
        final var jwt = tokenProvider.generate(authentication);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return JwtAuthenticationResponse.builder()
                .tokenType("Bearer")
                .accessToken(jwt)
                .build();
    }
}
