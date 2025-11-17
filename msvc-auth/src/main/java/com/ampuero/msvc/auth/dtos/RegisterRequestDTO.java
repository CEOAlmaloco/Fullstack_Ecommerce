package com.ampuero.msvc.auth.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequestDTO {

    @Pattern(regexp = "^$|^[0-9]{7,8}[0-9Kk]$", message = "RUN debe tener formato válido")
    private String runUsuario;

    @NotBlank(message = "El nombre es requerido")
    @Size(max = 50, message = "El nombre no puede exceder 50 caracteres")
    private String nombreUsuario;

    @NotBlank(message = "Los apellidos son requeridos")
    @Size(max = 100, message = "Los apellidos no pueden exceder 100 caracteres")
    private String apellidosUsuario;

    @NotBlank(message = "El correo es requerido")
    @Email(message = "Formato de correo inválido")
    private String correoUsuario;

    @NotBlank(message = "La contraseña es requerida")
    @Size(min = 4, max = 10, message = "La contraseña debe tener entre 4 y 10 caracteres")
    private String password;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Fecha debe tener formato YYYY-MM-DD")
    private String fechaNacimiento;

    @NotBlank(message = "La región es requerida")
    private String region;

    @NotBlank(message = "La comuna es requerida")
    private String comuna;

    @Size(max = 300, message = "La dirección no puede exceder 300 caracteres")
    private String direccionUsuario; // Campo opcional

    @Size(max = 20, message = "El teléfono no puede exceder 20 caracteres")
    @Pattern(regexp = "^$|^\\+?[0-9\\s\\-()]+$", message = "Formato de teléfono inválido")
    private String telefono;

    @Size(max = 50, message = "El código de referido no puede exceder 50 caracteres")
    private String codigoReferido;

    private Boolean aceptaMarketing;
}