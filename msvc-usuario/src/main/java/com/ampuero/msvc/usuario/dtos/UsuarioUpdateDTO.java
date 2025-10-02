package com.ampuero.msvc.usuario.dtos;

import com.ampuero.msvc.usuario.entities.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

/**
 * DTO para actualización de usuarios
 * Contiene los datos que pueden ser actualizados de un usuario
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioUpdateDTO {

    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombre;

    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    private String apellido;

    @Email(message = "El formato del correo no es válido")
    private String correo;

    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    private String telefono;

    private LocalDate fechaNacimiento;

    private Usuario.Genero genero;

    private String direccion;

    private String ciudad;

    private String pais;

    private String codigoPostal;

    private String avatarUrl;

    private Usuario.TipoUsuario tipoUsuario;

    private Usuario.EstadoUsuario estado;

    private Boolean emailVerificado;

    private Boolean telefonoVerificado;

    private Boolean aceptaMarketing;

    private Integer puntosLevelUp;

    private Usuario.NivelUsuario nivelUsuario;
}
