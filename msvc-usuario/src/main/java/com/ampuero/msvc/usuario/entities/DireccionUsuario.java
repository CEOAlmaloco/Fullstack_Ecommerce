package com.ampuero.msvc.usuario.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entidad DireccionUsuario para Level-Up Gamer
 * Representa las direcciones de un usuario
 */
@Entity
@Table(name = "direcciones_usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DireccionUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_direccion")
    private Long idDireccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 255, message = "La dirección no puede exceder 255 caracteres")
    @Column(name = "direccion", nullable = false, length = 255)
    private String direccion;

    @Column(name = "direccion_2", length = 255)
    private String direccion2;

    @NotBlank(message = "La ciudad es obligatoria")
    @Size(max = 100, message = "La ciudad no puede exceder 100 caracteres")
    @Column(name = "ciudad", nullable = false, length = 100)
    private String ciudad;

    @Size(max = 100, message = "El estado/provincia no puede exceder 100 caracteres")
    @Column(name = "estado_provincia", length = 100)
    private String estadoProvincia;

    @NotBlank(message = "El código postal es obligatorio")
    @Size(max = 10, message = "El código postal no puede exceder 10 caracteres")
    @Column(name = "codigo_postal", nullable = false, length = 10)
    private String codigoPostal;

    @NotBlank(message = "El país es obligatorio")
    @Size(max = 100, message = "El país no puede exceder 100 caracteres")
    @Column(name = "pais", nullable = false, length = 100)
    private String pais;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_direccion", nullable = false, length = 20)
    private TipoDireccion tipoDireccion = TipoDireccion.PRINCIPAL;

    @Column(name = "es_principal", nullable = false)
    private Boolean esPrincipal = false;

    @Column(name = "activa", nullable = false)
    private Boolean activa = true;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;

    @PreUpdate
    public void preUpdate() {
        this.fechaModificacion = LocalDateTime.now();
    }

    /**
     * Enums para DireccionUsuario
     */
    public enum TipoDireccion {
        PRINCIPAL, FACTURACION, ENVIO, ALTERNATIVA
    }
}
