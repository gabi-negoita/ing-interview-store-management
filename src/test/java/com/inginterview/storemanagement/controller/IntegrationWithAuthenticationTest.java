package com.inginterview.storemanagement.controller;

import com.inginterview.storemanagement.entity.Permission;
import com.inginterview.storemanagement.entity.Role;
import com.inginterview.storemanagement.entity.User;
import com.inginterview.storemanagement.repository.UserRepository;
import com.inginterview.storemanagement.service.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@ActiveProfiles("integration-tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationWithAuthenticationTest {

    @Autowired
    protected TestRestTemplate restTemplate;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;

    private Set<Permission> getMockedPermissions() {
        return Set.of(Permission.builder()
                .id(1L)
                .name("products")
                .build());
    }

    protected String mockUserAndGetJwtToken(String username, String password, Set<String> permissions) {
        mockUser(username, password, permissions);
        return getJwtToken(username, password, permissions);
    }

    protected void mockUser(String username, String password, Set<String> permissions) {
        final var mockedUser = getMockedUser(username, password, permissions);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockedUser));
    }

    protected String getJwtToken(String username, String password, Set<String> permissions) {
        return tokenAuthenticationService.generate(getAuthenticationWithPermissions(username, password, permissions));
    }

    private User getMockedUser(String username, String password, Set<String> permissions) {
        return User.builder()
                .id(1L)
                .username(username)
                .password(getEncodedPassword(password))
                .enabled(true)
                .roles(Set.of(getMockedRole(permissions)))
                .build();
    }

    private String getEncodedPassword(String password) {
        return new BCryptPasswordEncoder(10).encode(password);
    }

    private Role getMockedRole(Set<String> permissions) {
        return Role.builder()
                .id(1L)
                .name("ROLE_USER")
                .permissions(getMockedPermissions(permissions))
                .build();
    }

    private Set<Permission> getMockedPermissions(Set<String> permissions) {
        final var idCounter = new AtomicLong(1);
        return permissions.stream()
                .map(permission -> Permission.builder()
                        .id(idCounter.getAndIncrement())
                        .name(permission)
                        .build())
                .collect(Collectors.toSet());
    }

    private Authentication getAuthenticationWithPermissions(String username, String password, Set<String> permissions) {
        return new UsernamePasswordAuthenticationToken(
                username,
                password,
                permissions.stream().map(SimpleGrantedAuthority::new).toList());
    }
}
