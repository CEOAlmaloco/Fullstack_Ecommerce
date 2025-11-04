package com.ampuero.msvc.producto.config;

import com.ampuero.msvc.producto.filters.ApiKeyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración para registrar el interceptor de API Key
 */
@Configuration
public class ApiKeyConfig implements WebMvcConfigurer {

    @Autowired
    private ApiKeyInterceptor apiKeyInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiKeyInterceptor)
                .addPathPatterns("/**")
                // Excluir endpoints públicos (health, swagger, etc.)
                .excludePathPatterns(
                    "/actuator/**",
                    "/health",
                    "/h2-console/**",
                    "/doc/**",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/error"
                );
    }
}

