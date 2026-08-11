package org.example.sec.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.security")
public record SecurityProperty(
        String username,
        String password,
        String role
) {
}
