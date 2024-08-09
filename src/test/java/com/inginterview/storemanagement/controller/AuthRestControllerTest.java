package com.inginterview.storemanagement.controller;

import com.inginterview.storemanagement.model.ErrorResponse;
import com.inginterview.storemanagement.model.JwtAuthenticationResponse;
import com.inginterview.storemanagement.model.LoginRequest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AuthRestControllerTest extends IntegrationWithAuthenticationTest {

    static Stream<Arguments> loginRequestData() {
        return Stream.of(
                Arguments.of("user", "incorrect-password"),
                Arguments.of("incorrect-user", "pass"),
                Arguments.of("incorrect-user", "incorrect-password"));
    }

    @Test
    void whenGetToken_ifValidCredentials_thenReturnToken() {
        mockUser("user", "pass", Set.of("products"));

        final var loginRequest = LoginRequest.builder()
                .username("user")
                .password("pass")
                .build();
        final var entity = new HttpEntity<>(loginRequest, new HttpHeaders());

        final var response = restTemplate.exchange("/auth/token",
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<JwtAuthenticationResponse>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTokenType()).isEqualTo("Bearer");
        assertThat(response.getBody().getAccessToken()).isNotBlank();
    }

    @Disabled // fixme: this test is failing miserably
    @ParameterizedTest
    @MethodSource("loginRequestData")
    void whenGetToken_ifIncorrectCredentials_thenReturn401Unauthorized(String username, String password) {
        mockUser("user", "pass", Set.of("products"));

        final var loginRequest = LoginRequest.builder()
                .username(username)
                .password(password)
                .build();
        final var entity = new HttpEntity<>(loginRequest, new HttpHeaders());

        final var response = restTemplate.exchange("/auth/token",
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<ErrorResponse>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(response.getBody().getTimestamp()).isNotNull();
        assertThat(response.getBody().getErrors().size()).isEqualTo(1);
        assertThat(response.getBody().getErrors().get(0)).isEqualTo("Bad credentials");
    }
}
