package com.ampuero.msvc.auth.services;

import com.ampuero.msvc.auth.clients.UsuarioClientRest;
import com.ampuero.msvc.auth.dtos.*;
import com.ampuero.msvc.auth.exceptions.AuthException;
import com.ampuero.msvc.auth.models.Auth;
import com.ampuero.msvc.auth.repositories.AuthRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    @Autowired
    private AuthRepository authRepository;
    @Autowired
    private UsuarioClientRest usuarioClient;
    @Value("${jwt.secret:levelUpGamerSecretKey2024SecureForJWT256BitsMinimum}")
    private String jwtSecret;
    @Value("${jwt.access.expiration:1800}") // 30 minutos
    private Long accessTokenExpiration;
    @Value("${jwt.refresh.expiration:604800}") // 7 días
    private Long refreshTokenExpiration;

    private SecretKey getSigningKey() {
        // Asegurar que la clave tenga al menos 256 bits (32 bytes) para JWT
        byte[] keyBytes = jwtSecret.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        
        // Si la clave es menor a 32 bytes, extenderla usando SHA-256
        if (keyBytes.length < 32) {
            try {
                java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
                keyBytes = digest.digest(keyBytes);
            } catch (java.security.NoSuchAlgorithmException e) {
                log.error("Error al generar clave SHA-256: ", e);
                // Si falla, repetir la clave hasta tener 32 bytes
                byte[] extendedKey = new byte[32];
                for (int i = 0; i < 32; i++) {
                    extendedKey[i] = keyBytes[i % keyBytes.length];
                }
                keyBytes = extendedKey;
            }
        }
        
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // ========== AUTENTICACIÓN ==========

    @Transactional
    @Override
    public AuthResponseDTO login(LoginRequestDTO loginRequest, String ipCliente, String userAgent) {
        try {
            // Validar credenciales con msvc-usuario
            java.util.Map<String, Object> credentialsRequest = new java.util.HashMap<>();
            credentialsRequest.put("correoUsuario", loginRequest.getCorreoUsuario());
            credentialsRequest.put("password", loginRequest.getPassword());

            // Llamada a msvc-usuario para validar
            var validationResponse = usuarioClient.validarCredenciales(credentialsRequest);

            log.info("Respuesta de validación: valid={}, userId={}, tipoUsuario={}, nombreUsuario={}", 
                    validationResponse.isValid(), 
                    validationResponse.getUserId(), 
                    validationResponse.getTipoUsuario(),
                    validationResponse.getNombreUsuario());

            if (!validationResponse.isValid()) {
                throw new AuthException("Credenciales inválidas: " + validationResponse.getMensaje());
            }

            // Validar que la respuesta tenga todos los datos necesarios
            if (validationResponse.getUserId() == null) {
                log.error("Error: userId es null en la respuesta de validación");
                throw new AuthException("Error: userId no disponible en la respuesta de validación");
            }
            if (validationResponse.getTipoUsuario() == null || validationResponse.getTipoUsuario().isEmpty()) {
                log.error("Error: tipoUsuario es null o vacío en la respuesta de validación. Valor: '{}'", validationResponse.getTipoUsuario());
                throw new AuthException("Error: tipoUsuario no disponible en la respuesta de validación");
            }

            // Generar tokens JWT
            String accessToken = generateAccessToken(validationResponse.getUserId(), validationResponse.getTipoUsuario());
            String refreshToken = generateRefreshToken(validationResponse.getUserId());

            // Guardar tokens en BD
            saveToken(accessToken, validationResponse.getUserId(), "ACCESS", accessTokenExpiration, ipCliente, userAgent);
            saveToken(refreshToken, validationResponse.getUserId(), "REFRESH", refreshTokenExpiration, ipCliente, userAgent);

            // Crear respuesta
            AuthResponseDTO.UsuarioInfoDTO usuarioInfo = new AuthResponseDTO.UsuarioInfoDTO(
                    validationResponse.getUserId(),
                    validationResponse.getNombreUsuario(),
                    validationResponse.getApellidosUsuario(),
                    validationResponse.getCorreoUsuario(),
                    validationResponse.getTipoUsuario(),
                    validationResponse.getDescuentoDuoc(),
                    validationResponse.getRegion(),
                    validationResponse.getComuna()
            );

            return new AuthResponseDTO(accessToken, refreshToken, accessTokenExpiration, usuarioInfo);

        } catch (AuthException e) {
            // Re-lanzar excepciones de autenticación sin modificar
            throw e;
        } catch (Exception e) {
            log.error("Error en login: ", e);
            log.error("Mensaje de error: {}", e.getMessage());
            log.error("Causa: ", e.getCause());
            throw new AuthException("Error interno en autenticación: " + e.getMessage());
        }
    }

    @Transactional
    @Override
    public AuthResponseDTO register(RegisterRequestDTO registerRequest, String ipCliente, String userAgent) {
        try {
            // Convertir RegisterRequestDTO a Map para enviar a msvc-usuario
            // Nota: UsuarioClientRest usa Object, así que enviamos un Map
            java.util.Map<String, Object> usuarioData = new java.util.HashMap<>();
            usuarioData.put("nombre", registerRequest.getNombreUsuario());
            usuarioData.put("apellido", registerRequest.getApellidosUsuario());
            usuarioData.put("correo", registerRequest.getCorreoUsuario());
            usuarioData.put("password", registerRequest.getPassword());

            String runUsuario = registerRequest.getRunUsuario();
            if (runUsuario != null) {
                runUsuario = runUsuario.trim();
            }
            if (runUsuario != null && !runUsuario.isEmpty()) {
                usuarioData.put("runUsuario", runUsuario);
            }

            String telefono = registerRequest.getTelefono();
            if (telefono != null) {
                telefono = telefono.trim();
                if (!telefono.isEmpty()) {
                    usuarioData.put("telefono", telefono);
                }
            }

            usuarioData.put("region", registerRequest.getRegion());
            usuarioData.put("comuna", registerRequest.getComuna());
            usuarioData.put("direccion", registerRequest.getDireccionUsuario() != null ? registerRequest.getDireccionUsuario() : "");
            usuarioData.put("ciudad", registerRequest.getComuna()); // Usar comuna como ciudad
            if (registerRequest.getCodigoReferido() != null && !registerRequest.getCodigoReferido().isEmpty()) {
                usuarioData.put("referidoPor", registerRequest.getCodigoReferido());
            }
            usuarioData.put("aceptaTerminos", Boolean.TRUE);
            usuarioData.put("aceptaMarketing", registerRequest.getAceptaMarketing() != null ? registerRequest.getAceptaMarketing() : Boolean.FALSE);
            
            // Parsear fecha de nacimiento
            if (registerRequest.getFechaNacimiento() != null && !registerRequest.getFechaNacimiento().isEmpty()) {
                try {
                    java.time.LocalDate fechaNac = java.time.LocalDate.parse(registerRequest.getFechaNacimiento());
                    usuarioData.put("fechaNacimiento", fechaNac.toString());
                } catch (Exception e) {
                    log.warn("Error parseando fecha de nacimiento: {}", registerRequest.getFechaNacimiento());
                }
            }

            // Registrar usuario en msvc-usuario
            usuarioClient.registrarUsuario(usuarioData);

            // Auto-login después del registro
            LoginRequestDTO loginRequest = new LoginRequestDTO();
            loginRequest.setCorreoUsuario(registerRequest.getCorreoUsuario());
            loginRequest.setPassword(registerRequest.getPassword());

            return login(loginRequest, ipCliente, userAgent);

        } catch (Exception e) {
            log.error("Error en registro: ", e);
            throw new AuthException("Error en registro de usuario: " + e.getMessage());
        }
    }

    @Transactional
    @Override
    public AuthResponseDTO refreshToken(RefreshTokenRequestDTO refreshRequest) {
        try {
            // Validar refresh token
            Optional<Auth> tokenOpt = authRepository.findByTokenJwtAndActivoTrue(refreshRequest.getRefreshToken());

            if (tokenOpt.isEmpty()) {
                throw new AuthException("Refresh token inválido o expirado");
            }

            Auth refreshTokenEntity = tokenOpt.get();

            // Verificar expiración
            if (refreshTokenEntity.getFechaExpiracion().isBefore(LocalDateTime.now())) {
                authRepository.revocarToken(refreshRequest.getRefreshToken());
                throw new AuthException("Refresh token expirado");
            }

            // Obtener info del usuario
            var usuarioInfo = usuarioClient.obtenerUsuarioPorId(refreshTokenEntity.getUsuarioId());

            // Generar nuevo access token
            String newAccessToken = generateAccessToken(refreshTokenEntity.getUsuarioId(), usuarioInfo.getTipoUsuario());

            // Guardar nuevo token
            saveToken(newAccessToken, refreshTokenEntity.getUsuarioId(), "ACCESS", accessTokenExpiration,
                    refreshTokenEntity.getIpCliente(), refreshTokenEntity.getUserAgent());

            AuthResponseDTO.UsuarioInfoDTO userInfo = new AuthResponseDTO.UsuarioInfoDTO(
                    usuarioInfo.getIdUsuario(),
                    usuarioInfo.getNombreUsuario(),
                    usuarioInfo.getApellidosUsuario(),
                    usuarioInfo.getCorreoUsuario(),
                    usuarioInfo.getTipoUsuario(),
                    usuarioInfo.getDescuentoDuoc(),
                    usuarioInfo.getRegion(),
                    usuarioInfo.getComuna()
            );

            return new AuthResponseDTO(newAccessToken, refreshRequest.getRefreshToken(), accessTokenExpiration, userInfo);

        } catch (Exception e) {
            log.error("Error en refresh token: ", e);
            throw new AuthException("Error al renovar token");
        }
    }

    // ========== VALIDACIÓN DE TOKENS ==========

    @Override
    public TokenValidationResponseDTO validateToken(String token) {
        try {
            // Verificar en BD
            Optional<Auth> tokenOpt = authRepository.findByTokenJwtAndActivoTrue(token);

            if (tokenOpt.isEmpty()) {
                return new TokenValidationResponseDTO(false, "Token no encontrado o inactivo");
            }

            Auth tokenEntity = tokenOpt.get();

            // Verificar expiración
            if (tokenEntity.getFechaExpiracion().isBefore(LocalDateTime.now())) {
                authRepository.revocarToken(token);
                return new TokenValidationResponseDTO(false, "Token expirado");
            }

            // Validar JWT
            Claims claims = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return new TokenValidationResponseDTO(
                    true,
                    tokenEntity.getUsuarioId(),
                    claims.get("tipoUsuario", String.class),
                    tokenEntity.getFechaExpiracion()
            );

        } catch (Exception e) {
            log.error("Error validando token: ", e);
            return new TokenValidationResponseDTO(false, "Token inválido");
        }
    }

    @Override
    public boolean isTokenValid(String token) {
        return validateToken(token).isValid();
    }

    // ========== GESTIÓN DE SESIONES ==========

    @Transactional
    @Override
    public void logout(String token) {
        authRepository.revocarToken(token);
    }

    @Transactional
    @Override
    public void logoutAll(Long userId) {
        authRepository.revocarTodosLosTokensDelUsuario(userId);
    }

    @Override
    public List<Auth> getUserSessions(Long userId) {
        return authRepository.findByUsuarioIdAndActivoTrue(userId);
    }

    @Transactional
    @Override
    public void revokeSession(Long tokenId) {
        if (tokenId == null) {
            return;
        }
        authRepository.findById(tokenId).ifPresent(token -> {
            token.setActivo(false);
            authRepository.save(token);
        });
    }

    // ========== LIMPIEZA ==========

    @Transactional
    @Override
    public void cleanExpiredTokens() {
        LocalDateTime fechaLimite = LocalDateTime.now().minusDays(30);
        authRepository.eliminarTokensExpirados(fechaLimite);
    }

    // ========== MÉTODOS PRIVADOS ==========

    private String generateAccessToken(Long userId, String tipoUsuario) {
        Date expiration = new Date(System.currentTimeMillis() + accessTokenExpiration * 1000);

        return Jwts.builder()
                .subject(userId.toString())
                .claim("tipoUsuario", tipoUsuario)
                .claim("tokenType", "ACCESS")
                .issuedAt(new Date())
                .expiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }

    private String generateRefreshToken(Long userId) {
        Date expiration = new Date(System.currentTimeMillis() + refreshTokenExpiration * 1000);

        return Jwts.builder()
                .subject(userId.toString())
                .claim("tokenType", "REFRESH")
                .issuedAt(new Date())
                .expiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }

    private void saveToken(String token, Long userId, String tipoToken, Long expiration, String ipCliente, String userAgent) {
        Auth authToken = new Auth();
        authToken.setTokenJwt(token);
        authToken.setUsuarioId(userId);
        authToken.setTipoToken(tipoToken);
        authToken.setFechaCreacion(LocalDateTime.now());
        authToken.setFechaExpiracion(LocalDateTime.now().plusSeconds(expiration));
        authToken.setActivo(true);
        authToken.setIpCliente(ipCliente);
        authToken.setUserAgent(userAgent);

        authRepository.save(authToken);
    }
}