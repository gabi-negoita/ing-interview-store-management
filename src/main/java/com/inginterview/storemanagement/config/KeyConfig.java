package com.inginterview.storemanagement.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
@RequiredArgsConstructor
public class KeyConfig {

    private final ResourceLoader resourceLoader;

    @Bean
    public PublicKey publicKey() throws Exception {
        // todo: store this in a secure location, not in a file
        final var publicKeyAsString = getFileAsString("public_key.pem")
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        final var encoded = Base64.getDecoder().decode(publicKeyAsString);
        final var keySpec = new X509EncodedKeySpec(encoded);
        final var keyFactory = KeyFactory.getInstance("RSA");

        return keyFactory.generatePublic(keySpec);
    }

    @Bean
    public PrivateKey privateKey() throws Exception {
        // todo: store this in a secure location, not in a file
        final var privateKeyAsString = getFileAsString("private_key.pem")
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        final var encoded = Base64.getDecoder().decode(privateKeyAsString);
        final var keySpec = new PKCS8EncodedKeySpec(encoded);
        final var keyFactory = KeyFactory.getInstance("RSA");

        return keyFactory.generatePrivate(keySpec);
    }

    private String getFileAsString(String fileName) throws IOException {
        final var resource = resourceLoader.getResource("classpath:" + fileName);
        final var inputStream = resource.getInputStream();
        final var reader = new BufferedReader(new InputStreamReader(inputStream));
        final var stringBuilder = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        reader.close();

        return stringBuilder.toString();
    }
}
