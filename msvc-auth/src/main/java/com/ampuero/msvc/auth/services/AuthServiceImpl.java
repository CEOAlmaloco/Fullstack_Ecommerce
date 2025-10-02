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
    @Value("${jwt.secret:levelUpGamerSecretKey2024}")
    private String jwtSecret;
    @Value("${jwt.access.expiration:1800}") // 30 minutos
    private Long accessTokenExpiration;
    @Value("${jwt.refresh.expiration:604800}") // 7 días
    private Long refreshTokenExpiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    // ========== AUTENTICACIÓN ==========

    @Transactional
    @Override
    public AuthResponseDTO login(LoginRequestDTO loginRequest, String ipCliente, String userAgent) {
        try {
            // Validar credenciales con msvc-usuario
            var credentialsRequest = new Object() {
                public String correoUsuario = loginRequest.getCorreoUsuario();
                public String password = loginRequest.getPassword();
            };

            // Llamada a msvc-usuario para validar
            var validationResponse = usuarioClient.validarCredenciales(credentialsRequest);

            if (!validationResponse.isValid()) {
                throw new AuthException("Credenciales inválidas: " + validationResponse.getMensaje());
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

        } catch (Exception e) {
            log.error("Error en login: ", e);
            throw new AuthException("Error interno en autenticación");
        }
    }

    @Transactional
    @Override
    public AuthResponseDTO register(RegisterRequestDTO registerRequest, String ipCliente, String userAgent) {
        try {
            // Registrar usuario en msvc-usuario
            usuarioClient.registrarUsuario(registerRequest);

            // Auto-login después del registro
            LoginRequestDTO loginRequest = new LoginRequestDTO();
            loginRequest.setCorreoUsuario(registerRequest.getCorreoUsuario());
            loginRequest.setPassword(registerRequest.getPassword());

            return login(loginRequest, ipCliente, userAgent);

        } catch (Exception e) {
            log.error("Error en registro: ", e);
            throw new AuthException("Error en registro de usuario");
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
        Optional<Auth> tokenOpt = authRepository.findById(tokenId);
        if (tokenOpt.isPresent()) {
            Auth token = tokenOpt.get();
            token.setActivo(false);
            authRepository.save(token);
        }
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