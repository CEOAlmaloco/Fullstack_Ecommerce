package com.ampuero.msvc.auth.controllers;

import com.ampuero.msvc.auth.dtos.*;
import com.ampuero.msvc.auth.models.Auth;
import com.ampuero.msvc.auth.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
@Tag(name = "Auth API", description = "Autenticación JWT Level-Up Gamer")
public class AuthController {

    @Autowired
    private AuthService authService;

    // ========== AUTENTICACIÓN ==========

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Login con email/password - genera JWT")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest,
                                                 HttpServletRequest request) {
        String ipCliente = getClientIp(request);
        String userAgent = request.getHeader("User-Agent");

        AuthResponseDTO response = authService.login(loginRequest, ipCliente, userAgent);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar usuario", description = "Registro + auto-login")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO registerRequest,
                                                    HttpServletRequest request) {
        String ipCliente = getClientIp(request);
        String userAgent = request.getHeader("User-Agent");

        AuthResponseDTO response = authService.register(registerRequest, ipCliente, userAgent);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Renovar token", description = "Genera nuevo ACCESS token usando REFRESH")
    public ResponseEntity<AuthResponseDTO> refreshToken(@Valid @RequestBody RefreshTokenRequestDTO refreshRequest) {
        AuthResponseDTO response = authService.refreshToken(refreshRequest);
        return ResponseEntity.ok(response);
    }

    // ========== VALIDACIÓN DE TOKENS ==========

    @GetMapping("/validate")
    @Operation(summary = "Validar token", description = "Para Gateway - valida JWT del header")
    public ResponseEntity<TokenValidationResponseDTO> validateToken(HttpServletRequest request) {
        String token = extractTokenFromHeader(request);
        if (token == null) {
            return ResponseEntity.ok(new TokenValidationResponseDTO(false, "Token no encontrado en header"));
        }

        TokenValidationResponseDTO response = authService.validateToken(token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/validate")
    @Operation(summary = "Validar token específico", description = "Valida token enviado en body")
    public ResponseEntity<TokenValidationResponseDTO> validateSpecificToken(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        if (token == null) {
            return ResponseEntity.ok(new TokenValidationResponseDTO(false, "Token requerido"));
        }

        TokenValidationResponseDTO response = authService.validateToken(token);
        return ResponseEntity.ok(response);
    }

    // ========== GESTIÓN DE SESIONES ==========

    @PostMapping("/logout")
    @Operation(summary = "Cerrar sesión", description = "Revoca token actual")
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request) {
        String token = extractTokenFromHeader(request);
        if (token != null) {
            authService.logout(token);
        }
        return ResponseEntity.ok(Map.of("message", "Sesión cerrada exitosamente"));
    }

    @PostMapping("/logout-all")
    @Operation(summary = "Cerrar todas las sesiones", description = "Revoca todos los tokens del usuario")
    public ResponseEntity<Map<String, String>> logoutAll(@RequestBody Map<String, Long> request) {
        Long userId = request.get("userId");
        if (userId != null) {
            authService.logoutAll(userId);
        }
        return ResponseEntity.ok(Map.of("message", "Todas las sesiones cerradas"));
    }

    @GetMapping("/sessions/{userId}")
    @Operation(summary = "Sesiones activas", description = "Lista sesiones del usuario")
    public ResponseEntity<List<Auth>> getUserSessions(@PathVariable Long userId) {
        List<Auth> sessions = authService.getUserSessions(userId);
        return ResponseEntity.ok(sessions);
    }

    @DeleteMapping("/sessions/{tokenId}")
    @Operation(summary = "Revocar sesión", description = "Revoca sesión específica")
    public ResponseEntity<Map<String, String>> revokeSession(@PathVariable Long tokenId) {
        authService.revokeSession(tokenId);
        return ResponseEntity.ok(Map.of("message", "Sesión revocada"));
    }

    // ========== UTILIDADES ==========

    @PostMapping("/clean-expired")
    @Operation(summary = "Limpiar tokens", description = "Elimina tokens expirados (admin)")
    public ResponseEntity<Map<String, String>> cleanExpiredTokens() {
        authService.cleanExpiredTokens();
        return ResponseEntity.ok(Map.of("message", "Tokens expirados eliminados"));
    }

    // ========== MÉTODOS PRIVADOS ==========

    private String extractTokenFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private String getClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }

        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty()) {
            return xRealIp;
        }

        return request.getRemoteAddr();
    }
}