package com.ampuero.msvc.usuario.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * Cliente Feign para comunicación con msvc-auth
 * Permite validar tokens y obtener información de autenticación
 */
@FeignClient(name = "msvc-auth", url = "${MSVC_AUTH_URL:http://localhost:8001}")
public interface AuthClientRest {

    /**
     * Valida un token JWT
     * @param token Token a validar
     * @return Información de validación del token
     */
    @GetMapping("/api/v1/auth/validar-token/{token}")
    ResponseEntity<Map<String, Object>> validarToken(@PathVariable String token);

    /**
     * Obtiene información del usuario desde el token
     * @param token Token JWT
     * @return Información del usuario
     */
    @GetMapping("/api/v1/auth/usuario-info/{token}")
    ResponseEntity<Map<String, Object>> obtenerUsuarioInfo(@PathVariable String token);

    /**
     * Verifica si un token está activo
     * @param token Token a verificar
     * @return Estado del token
     */
    @GetMapping("/api/v1/auth/token-activo/{token}")
    ResponseEntity<Map<String, Boolean>> verificarTokenActivo(@PathVariable String token);
}
