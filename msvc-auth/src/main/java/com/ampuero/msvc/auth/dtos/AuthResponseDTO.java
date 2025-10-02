package com.ampuero.msvc.auth.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthResponseDTO {

    private final String tokenType = "Bearer";
    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
    private UsuarioInfoDTO usuario;

    public AuthResponseDTO(String accessToken, String refreshToken, Long expiresIn, UsuarioInfoDTO usuario) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.usuario = usuario;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UsuarioInfoDTO {
        private Long id;
        private String nombre;
        private String apellidos;
        private String correo;
        private String tipoUsuario;
        private Boolean descuentoDuoc;
        private String region;
        private String comuna;
    }
}