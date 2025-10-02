package com.ampuero.msvc.usuario.dtos;

import com.ampuero.msvc.usuario.entities.Usuario;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para respuesta de usuarios
 * Contiene los datos del usuario para mostrar en respuestas de API
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO {

    private Long idUsuario;
    private String nombre;
    private String apellido;
    private String correo;
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
    private LocalDateTime fechaRegistro;
    private LocalDateTime ultimoAcceso;
    private Boolean emailVerificado;
    private Boolean telefonoVerificado;
    private Boolean aceptaMarketing;
    private String codigoReferido;
    private String referidoPor;
    private Integer puntosLevelUp;
    private Usuario.NivelUsuario nivelUsuario;
    
    // Información adicional
    private List<DireccionUsuarioResponseDTO> direcciones;
    private List<PreferenciaUsuarioResponseDTO> preferencias;
    
    // Campos calculados
    private String nombreCompleto;
    private Integer edad;
    private String nivelDescripcion;
    
    /**
     * Constructor que calcula campos derivados
     */
    public UsuarioResponseDTO(Usuario usuario) {
        this.idUsuario = usuario.getIdUsuario();
        this.nombre = usuario.getNombre();
        this.apellido = usuario.getApellido();
        this.correo = usuario.getCorreo();
        this.telefono = usuario.getTelefono();
        this.fechaNacimiento = usuario.getFechaNacimiento();
        this.genero = usuario.getGenero();
        this.direccion = usuario.getDireccion();
        this.ciudad = usuario.getCiudad();
        this.pais = usuario.getPais();
        this.codigoPostal = usuario.getCodigoPostal();
        this.avatarUrl = usuario.getAvatarUrl();
        this.tipoUsuario = usuario.getTipoUsuario();
        this.estado = usuario.getEstado();
        this.fechaRegistro = usuario.getFechaRegistro();
        this.ultimoAcceso = usuario.getUltimoAcceso();
        this.emailVerificado = usuario.getEmailVerificado();
        this.telefonoVerificado = usuario.getTelefonoVerificado();
        this.aceptaMarketing = usuario.getAceptaMarketing();
        this.codigoReferido = usuario.getCodigoReferido();
        this.referidoPor = usuario.getReferidoPor();
        this.puntosLevelUp = usuario.getPuntosLevelUp();
        this.nivelUsuario = usuario.getNivelUsuario();
        
        // Calcular campos derivados
        this.nombreCompleto = this.nombre + " " + this.apellido;
        this.nivelDescripcion = obtenerDescripcionNivel(this.nivelUsuario);
        
        if (this.fechaNacimiento != null) {
            this.edad = calcularEdad(this.fechaNacimiento);
        }
    }
    
    private String obtenerDescripcionNivel(Usuario.NivelUsuario nivel) {
        return switch (nivel) {
            case NOVATO -> "Novato (0-99 puntos)";
            case BRONCE -> "Bronce (100-299 puntos)";
            case PLATA -> "Plata (300-599 puntos)";
            case ORO -> "Oro (600-999 puntos)";
            case PLATINO -> "Platino (1000-1499 puntos)";
            case DIAMANTE -> "Diamante (1500-2499 puntos)";
            case MAESTRO -> "Maestro (2500-3999 puntos)";
            case GRAN_MAESTRO -> "Gran Maestro (4000+ puntos)";
        };
    }
    
    private Integer calcularEdad(LocalDate fechaNacimiento) {
        return LocalDate.now().getYear() - fechaNacimiento.getYear();
    }
}
