package com.inginterview.storemanagement.security;

import com.inginterview.storemanagement.service.TokenAuthenticationService;
import com.inginterview.storemanagement.util.HttpRequestUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.inginterview.storemanagement.util.Endpoints.PUBLIC_ENDPOINTS;
import static org.springframework.util.StringUtils.hasText;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final TokenAuthenticationService tokenService;
    private final HttpRequestUtil httpRequestUtil;

    @Value("${server.servlet.context-path}")
    private String basePath;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Skip public endpoints
        if (PUBLIC_ENDPOINTS.contains(getRequestURIWithoutBasePath(request))) {
            filterChain.doFilter(request, response);
            return;
        }

        final var token = httpRequestUtil.getTokenValue(request);
        if (hasText(token) && tokenService.isValid(token)) {
            final var username = tokenService.getUsername(token);
            final var userDetails = userDetailsService.loadUserByUsername(username);
            final var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String getRequestURIWithoutBasePath(HttpServletRequest request) {
        return request.getRequestURI().replace(basePath, "");
    }
}

