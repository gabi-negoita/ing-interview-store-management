package com.inginterview.storemanagement.config;

import com.inginterview.storemanagement.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.inginterview.storemanagement.util.Endpoints.PUBLIC_ENDPOINTS;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final int PASSWORD_ENCODER_STRENGTH = 10;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(PASSWORD_ENCODER_STRENGTH);
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(POST, PUBLIC_ENDPOINTS.toArray(new String[0])).permitAll()

                        .requestMatchers(GET, "/products/**").hasAnyAuthority("products", "products:read")
                        .requestMatchers(GET, "/product-categories/**").hasAnyAuthority("product-categories", "product-categories:read")

                        .requestMatchers(POST, "/products/**").hasAnyAuthority("products", "products:write")
                        .requestMatchers(POST, "/product-categories/**").hasAnyAuthority("product-categories", "product-categories:write")

                        .requestMatchers(PUT, "/products/**").hasAnyAuthority("products", "products:write")
                        .requestMatchers(PUT, "/product-categories/**").hasAnyAuthority("product-categories", "product-categories:write")

                        .requestMatchers(DELETE, "/products/**").hasAnyAuthority("products", "products:delete")
                        .requestMatchers(DELETE, "/product-categories/**").hasAnyAuthority("products", "product-categories:delete")

                        .anyRequest().authenticated())
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
