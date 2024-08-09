package com.inginterview.storemanagement.controller;

import com.inginterview.storemanagement.model.JwtAuthenticationResponse;
import com.inginterview.storemanagement.model.LoginRequest;
import com.inginterview.storemanagement.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
public class AuthRestController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/token")
    @ResponseStatus(HttpStatus.OK)
    public JwtAuthenticationResponse authenticate(@RequestBody LoginRequest loginRequest) {
        return authenticationService.authenticateAndGenerateToken(loginRequest.getUsername(), loginRequest.getPassword());
    }
}
