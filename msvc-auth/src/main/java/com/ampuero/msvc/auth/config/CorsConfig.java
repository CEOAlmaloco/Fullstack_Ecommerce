package com.ampuero.msvc.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${cors.allowed-origins:http://localhost:5173}")
    private String corsAllowedOrigins;

    @Value("${cors.allowed-methods:GET,POST,PUT,DELETE,OPTIONS}")
    private String corsAllowedMethods;

    @Value("${cors.allowed-headers:*}")
    private String corsAllowedHeaders;

    @Value("${cors.exposed-headers:Authorization,Set-Cookie}")
    private String corsExposedHeaders;

    @Value("${cors.allow-credentials:true}")
    private String corsAllowCredentials;

    @Value("${cors.max-age:3600}")
    private long corsMaxAge;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String[] origins = parseList(corsAllowedOrigins);
        if (origins.length == 0) {
            origins = new String[]{"http://localhost:5173"};
        }

        registry.addMapping("/**")
                .allowedOrigins(origins)
                .allowedMethods(parseList(corsAllowedMethods))
                .allowedHeaders(parseList(corsAllowedHeaders, true))
                .exposedHeaders(parseList(corsExposedHeaders, true))
                .allowCredentials(Boolean.parseBoolean(corsAllowCredentials))
                .maxAge(corsMaxAge);
    }

    private String[] parseList(String value) {
        return parseList(value, false);
    }

    private String[] parseList(String value, boolean allowWildcard) {
        if (value == null || value.isBlank()) {
            return new String[0];
        }

        return Arrays.stream(value.split(","))
                .map(String::trim)
                .filter(entry -> !entry.isEmpty())
                .map(entry -> allowWildcard ? entry : entry.replaceAll("\\s+", ""))
                .toArray(String[]::new);
    }
}

