package com.ampuero.msvc.auth.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequestDTO {

    @NotBlank(message = "Debes indicar tu correo o nombre de usuario")
    @Size(min = 3, max = 100, message = "El identificador debe tener entre 3 y 100 caracteres")
    private String correoUsuario;

    @NotBlank(message = "La contraseña es requerida")
    @Size(min = 4, max = 10, message = "La contraseña debe tener entre 4 y 10 caracteres")
    private String password;
}