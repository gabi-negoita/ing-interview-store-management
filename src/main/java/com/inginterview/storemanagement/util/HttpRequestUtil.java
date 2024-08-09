package com.inginterview.storemanagement.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import static org.springframework.util.StringUtils.hasText;

@Component
public class HttpRequestUtil {

    public String getTokenValue(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}
