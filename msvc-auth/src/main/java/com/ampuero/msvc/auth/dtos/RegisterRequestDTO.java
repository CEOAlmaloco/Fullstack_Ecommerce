package com.ampuero.msvc.auth.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequestDTO {

    @NotBlank(message = "El RUN es requerido")
    @Size(min = 7, max = 9, message = "El RUN debe tener entre 7 y 9 caracteres")
    @Pattern(regexp = "^[0-9]{7,8}[0-9Kk]$", message = "RUN debe tener formato válido")
    private String runUsuario;

    @NotBlank(message = "El nombre es requerido")
    @Size(max = 50, message = "El nombre no puede exceder 50 caracteres")
    private String nombreUsuario;

    @NotBlank(message = "Los apellidos son requeridos")
    @Size(max = 100, message = "Los apellidos no pueden exceder 100 caracteres")
    private String apellidosUsuario;

    @NotBlank(message = "El correo es requerido")
    @Email(message = "Formato de correo inválido")
    @Pattern(regexp = ".*(@duoc\\.cl|@profesor\\.duoc\\.cl|@gmail\\.com)$",
            message = "Solo se permiten correos @duoc.cl, @profesor.duoc.cl y @gmail.com")
    private String correoUsuario;

    @NotBlank(message = "La contraseña es requerida")
    @Size(min = 8, max = 100, message = "La contraseña debe tener entre 8 y 100 caracteres")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",
            message = "La contraseña debe contener al menos: 1 minúscula, 1 mayúscula y 1 número")
    private String password;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Fecha debe tener formato YYYY-MM-DD")
    private String fechaNacimiento;

    @NotBlank(message = "La región es requerida")
    private String region;

    @NotBlank(message = "La comuna es requerida")
    private String comuna;

    @NotBlank(message = "La dirección es requerida")
    @Size(max = 300, message = "La dirección no puede exceder 300 caracteres")
    private String direccionUsuario;
}