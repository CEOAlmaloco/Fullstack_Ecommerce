package com.ampuero.msvc.auth.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entidad Auth - Gestión de Tokens JWT para Level-Up Gamer
 * <p>
 * Esta entidad representa los tokens de autenticación JWT almacenados en la base de datos.
 * Permite el control de sesiones, revocación de tokens y seguimiento de accesos.
 * <p>
 * Funcionalidades principales:
 * - Almacena tokens JWT (ACCESS y REFRESH)
 * - Control de expiración de tokens
 * - Seguimiento de sesiones por usuario
 * - Revocación de tokens (campo activo)
 * - Auditoría de accesos (IP y User-Agent)
 * <p>
 * Casos de uso:
 * - Login: Se crea un token ACCESS y opcionalmente REFRESH
 * - Logout: Se marca el token como inactivo
 * - Renovación: Se crea nuevo token ACCESS usando REFRESH
 * - Seguridad: Se puede revocar todos los tokens de un usuario
 *
 * @author Level-Up Gamer Team
 * @version 1.0
 */
@Entity
@Table(name = "auth_tokens")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Auth {

    /**
     * Estado del token
     * - true: Token válido y usable
     * - false: Token revocado/invalidado (logout, cambio contraseña, etc.)
     */
    @Column(nullable = false)
    private Boolean activo = true;
    /**
     * Identificador único del token en la base de datos
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_token")
    private Long idToken;
    /**
     * ID del usuario al que pertenece este token
     * Referencia al usuario en msvc-usuario
     */
    @Column(nullable = false, name = "usuario_id")
    private Long usuarioId;
    /**
     * Token JWT completo (Header.Payload.Signature)
     * Debe ser único para evitar duplicados
     */
    @Column(nullable = false, unique = true, name = "token_jwt")
    private String tokenJwt;
    /**
     * Fecha y hora de creación del token
     * Usado para auditoría y limpieza de tokens antiguos
     */
    @Column(nullable = false, name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    /**
     * Fecha y hora de expiración del token
     * Después de esta fecha, el token no debe ser válido
     */
    @Column(nullable = false, name = "fecha_expiracion")
    private LocalDateTime fechaExpiracion;
    /**
     * Tipo de token JWT
     * - ACCESS: Token de acceso corto (15-30 min)
     * - REFRESH: Token de renovación largo (7-30 días)
     */
    @Column(nullable = false, name = "tipo_token")
    private String tipoToken;
    /**
     * Dirección IP desde donde se generó el token
     * Usado para detección de accesos sospechosos
     */
    @Column(name = "ip_cliente")
    private String ipCliente;

    /**
     * User-Agent del navegador/aplicación
     * Permite identificar el dispositivo/navegador usado
     */
    @Column(name = "user_agent")
    private String userAgent;
}
