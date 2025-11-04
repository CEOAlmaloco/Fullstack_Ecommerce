package com.ampuero.msvc.producto.filters;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Interceptor para validar API Keys en microservicios individuales
 * Usar este interceptor en microservicios que no pasan por el Gateway
 */
@Slf4j
@Component
public class ApiKeyInterceptor implements HandlerInterceptor {

    @Value("${api.key:}")
    private String validApiKey;

    @Value("${api.key.enabled:true}")
    private boolean apiKeyEnabled;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Si las API keys están deshabilitadas, continuar
        if (!apiKeyEnabled || !StringUtils.hasText(validApiKey)) {
            return true;
        }

        // Obtener API key del header
        String apiKey = request.getHeader("X-API-Key");

        // También verificar en query params (para flexibilidad)
        if (!StringUtils.hasText(apiKey)) {
            apiKey = request.getParameter("apiKey");
        }

        // Validar API key
        if (!StringUtils.hasText(apiKey) || !validApiKey.equals(apiKey)) {
            log.warn("Invalid or missing API key from IP: {}", request.getRemoteAddr());
            
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("""
                {
                    "error": "Unauthorized",
                    "message": "Invalid or missing API key. Please include X-API-Key header.",
                    "status": 401
                }
                """);
            
            return false;
        }

        log.debug("Valid API key received from: {}", request.getRemoteAddr());
        return true;
    }
}

