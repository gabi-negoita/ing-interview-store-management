package com.inginterview.storemanagement.controller;

import com.inginterview.storemanagement.entity.Product;
import com.inginterview.storemanagement.model.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ProductRestControllerTest extends IntegrationWithAuthenticationTest {

    @Test
    void whenGetAllProducts_ifAuthenticated_thenReturnsListOfProducts() {
        final var jwtToken = mockUserAndGetJwtToken("user", "pass", Set.of("products"));

        final var headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);

        final var entity = new HttpEntity<>(headers);

        final var response = restTemplate.exchange("/products",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Product>>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isGreaterThan(0);
    }

    @NullSource
    @ValueSource(strings = {"Bearer", "Bearer    ", "Bearer invalid-token"})
    @ParameterizedTest
    void whenGetAllProducts_ifTokenAbsentOrInvalid_thenReturn401Unauthorized(String token) {
        mockUser("user", "pass", Set.of("products"));

        final var headers = new HttpHeaders();
        if (token != null) {
            headers.set("Authorization", token);
        }
        final var entity = new HttpEntity<>(headers);

        final var response = restTemplate.exchange("/products",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<ErrorResponse>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(response.getBody().getTimestamp()).isNotNull();
        assertThat(response.getBody().getErrors().size()).isEqualTo(1);
        assertThat(response.getBody().getErrors().get(0)).isEqualTo("Full authentication is required to access this resource");
    }
}
