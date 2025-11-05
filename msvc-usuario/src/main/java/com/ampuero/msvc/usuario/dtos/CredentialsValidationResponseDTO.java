package com.ampuero.msvc.usuario.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * DTO para respuesta de validación de credenciales
 * Retorna información del usuario si las credenciales son válidas
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CredentialsValidationResponseDTO {
    private boolean valid;
    private Long userId;
    private String nombreUsuario;
    private String apellidosUsuario;
    private String correoUsuario;
    private String tipoUsuario;
    private Boolean descuentoDuoc;
    private String region;
    private String comuna;
    private String mensaje;
}

