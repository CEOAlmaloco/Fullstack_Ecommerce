package com.ampuero.msvc.usuario.dtos;

import com.ampuero.msvc.usuario.entities.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

/**
 * DTO para creación de usuarios
 * Contiene los datos necesarios para registrar un nuevo usuario
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioCreationDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    private String apellido;

    @Email(message = "El formato del correo no es válido")
    @NotBlank(message = "El correo es obligatorio")
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    private String telefono;

    private LocalDate fechaNacimiento;

    private Usuario.Genero genero;

    private String direccion;

    private String ciudad;

    private String pais;

    private String codigoPostal;

    private Usuario.TipoUsuario tipoUsuario = Usuario.TipoUsuario.CLIENTE;

    private String codigoReferido;

    private String referidoPor;

    @NotNull(message = "Debe aceptar los términos y condiciones")
    private Boolean aceptaTerminos = false;

    private Boolean aceptaMarketing = false;
}
