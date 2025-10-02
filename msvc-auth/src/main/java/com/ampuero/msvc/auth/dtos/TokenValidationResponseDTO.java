package com.ampuero.msvc.auth.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TokenValidationResponseDTO {

    private boolean valid;
    private String mensaje;
    private Long userId;
    private String tipoUsuario;
    private LocalDateTime expiresAt;

    // Constructor para token válido
    public TokenValidationResponseDTO(boolean valid, Long userId, String tipoUsuario, LocalDateTime expiresAt) {
        this.valid = valid;
        this.userId = userId;
        this.tipoUsuario = tipoUsuario;
        this.expiresAt = expiresAt;
        this.mensaje = valid ? "Token válido" : "Token inválido";
    }

    // Constructor para token inválido
    public TokenValidationResponseDTO(boolean valid, String mensaje) {
        this.valid = valid;
        this.mensaje = mensaje;
    }
}
