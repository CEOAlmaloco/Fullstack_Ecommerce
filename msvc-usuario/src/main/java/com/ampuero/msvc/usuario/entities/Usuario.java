package com.ampuero.msvc.usuario.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entidad Usuario para Level-Up Gamer
 * Representa un usuario del sistema de ecommerce gaming
 */
@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    @Column(name = "apellido", nullable = false, length = 50)
    private String apellido;

    @Email(message = "El formato del correo no es válido")
    @NotBlank(message = "El correo es obligatorio")
    @Column(name = "correo", nullable = false, unique = true, length = 100)
    private String correo;
    
    @Column(name = "run_usuario", length = 12, unique = true)
    private String runUsuario;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 4, max = 128, message = "La contraseña debe tener entre 4 y 128 caracteres")
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero", length = 20)
    private Genero genero;

    @Column(name = "direccion", length = 255)
    private String direccion;

    @Column(name = "region", length = 100)
    private String region;

    @Column(name = "comuna", length = 100)
    private String comuna;

    @Column(name = "ciudad", length = 100)
    private String ciudad;

    @Column(name = "pais", length = 100)
    private String pais;

    @Column(name = "codigo_postal", length = 10)
    private String codigoPostal;

    @Column(name = "avatar_url", length = 500)
    private String avatarUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario", nullable = false, length = 20)
    private TipoUsuario tipoUsuario = TipoUsuario.CLIENTE;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private EstadoUsuario estado = EstadoUsuario.ACTIVO;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    @Column(name = "ultimo_acceso")
    private LocalDateTime ultimoAcceso;

    @Column(name = "email_verificado", nullable = false)
    private Boolean emailVerificado = false;

    @Column(name = "telefono_verificado", nullable = false)
    private Boolean telefonoVerificado = false;

    @Column(name = "acepta_terminos", nullable = false)
    private Boolean aceptaTerminos = false;

    @Column(name = "acepta_marketing", nullable = false)
    private Boolean aceptaMarketing = false;

    @Column(name = "codigo_referido", length = 20, unique = true)
    private String codigoReferido;

    @Column(name = "referido_por", length = 20)
    private String referidoPor;

    @Column(name = "puntos_levelup", nullable = false)
    private Integer puntosLevelUp = 0;

    @Column(name = "codigos_canjeados", columnDefinition = "TEXT")
    private String codigosCanjeados; // JSON array de códigos canjeados

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_usuario", nullable = false, length = 20)
    private NivelUsuario nivelUsuario = NivelUsuario.NOVATO;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DireccionUsuario> direcciones;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PreferenciaUsuario> preferencias;

    /**
     * Enums para el modelo Usuario
     */
    public enum Genero {
        MASCULINO, FEMENINO, OTRO, NO_ESPECIFICAR
    }

    public enum TipoUsuario {
        CLIENTE, ADMINISTRADOR, MODERADOR, VENDEDOR
    }

    public enum EstadoUsuario {
        ACTIVO, INACTIVO, SUSPENDIDO, BANEADO, PENDIENTE_VERIFICACION
    }

    public enum NivelUsuario {
        NOVATO, BRONCE, PLATA, ORO, PLATINO, DIAMANTE, MAESTRO, GRAN_MAESTRO
    }
}
