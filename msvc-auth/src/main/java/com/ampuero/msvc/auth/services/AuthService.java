package com.ampuero.msvc.auth.services;

import com.ampuero.msvc.auth.dtos.*;
import com.ampuero.msvc.auth.models.Auth;

import java.util.List;

public interface AuthService {

    // Autenticación
    AuthResponseDTO login(LoginRequestDTO loginRequest, String ipCliente, String userAgent);

    AuthResponseDTO register(RegisterRequestDTO registerRequest, String ipCliente, String userAgent);

    AuthResponseDTO refreshToken(RefreshTokenRequestDTO refreshRequest);

    // Validación de tokens
    TokenValidationResponseDTO validateToken(String token);

    boolean isTokenValid(String token);

    // Gestión de sesiones
    void logout(String token);

    void logoutAll(Long userId);

    List<Auth> getUserSessions(Long userId);

    void revokeSession(Long tokenId);

    // Limpieza
    void cleanExpiredTokens();
}