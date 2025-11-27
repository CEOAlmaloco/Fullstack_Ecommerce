package com.ampuero.msvc.gateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

/**
 * Filtro de Gateway para validar API Keys
 * Valida que todas las peticiones incluyan una API key válida
 */
@Slf4j
@Component
public class ApiKeyFilter extends AbstractGatewayFilterFactory<ApiKeyFilter.Config> {

    @Value("${api.key:}")
    private String validApiKey;

    @Value("${api.key.enabled:true}")
    private boolean apiKeyEnabled;

    public ApiKeyFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // Si las API keys están deshabilitadas, continuar
            if (!apiKeyEnabled || !StringUtils.hasText(validApiKey)) {
                log.debug("API Key validation disabled or not configured");
                return chain.filter(exchange);
            }

            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            // Permitir peticiones OPTIONS (preflight de CORS) sin API key
            if ("OPTIONS".equalsIgnoreCase(request.getMethod().name())) {
                log.debug("Allowing OPTIONS preflight request without API key");
                return chain.filter(exchange);
            }

            // Obtener API key del header
            String apiKey = request.getHeaders().getFirst("X-API-Key");

            // También verificar en query params (para flexibilidad)
            if (!StringUtils.hasText(apiKey)) {
                apiKey = request.getQueryParams().getFirst("apiKey");
            }

            // Validar API key
            if (!StringUtils.hasText(apiKey) || !validApiKey.equals(apiKey)) {
                log.warn("Invalid or missing API key from IP: {}", 
                         request.getRemoteAddress() != null ? 
                         request.getRemoteAddress().getAddress().getHostAddress() : "unknown");
                
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                response.getHeaders().add("Content-Type", "application/json");
                
                String errorMessage = """
                    {
                        "error": "Unauthorized",
                        "message": "Invalid or missing API key. Please include X-API-Key header.",
                        "status": 401
                    }
                    """;
                
                return response.writeWith(
                    Mono.just(response.bufferFactory().wrap(errorMessage.getBytes()))
                );
            }

            log.debug("Valid API key received from: {}", 
                     request.getRemoteAddress() != null ? 
                     request.getRemoteAddress().getAddress().getHostAddress() : "unknown");

            // API key válida, continuar con la petición
            return chain.filter(exchange);
        };
    }

    public static class Config {
        // Configuración adicional si es necesario
    }
}

