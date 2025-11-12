package com.ampuero.msvc.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

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

    @Value("${cors.service-enabled:false}")
    private boolean serviceCorsEnabled;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        if (!serviceCorsEnabled) {
            return;
        }

        String[] allowedOrigins = uniqueList(corsAllowedOrigins);
        if (allowedOrigins.length == 0) {
            allowedOrigins = new String[]{"http://localhost:5173"};
        }

        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods(uniqueList(corsAllowedMethods))
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

    private String[] uniqueList(String value) {
        String[] entries = parseList(value, false);
        if (entries.length == 0) {
            return entries;
        }
        Set<String> unique = new LinkedHashSet<>();
        for (String entry : entries) {
            if (!entry.isBlank()) {
                unique.add(entry.trim());
            }
        }
        return unique.toArray(new String[0]);
    }
}

