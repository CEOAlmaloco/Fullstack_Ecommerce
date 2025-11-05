package com.ampuero.msvc.usuario.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * DTO para validación de credenciales
 * Usado por msvc-auth para validar login
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CredentialsValidationDTO {
    private String correoUsuario;
    private String password;
}

