package com.ampuero.msvc.usuario.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entidad PreferenciaUsuario para Level-Up Gamer
 * Representa las preferencias de un usuario
 */
@Entity
@Table(name = "preferencias_usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreferenciaUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_preferencia")
    private Long idPreferencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @NotBlank(message = "La clave de preferencia es obligatoria")
    @Size(max = 100, message = "La clave no puede exceder 100 caracteres")
    @Column(name = "clave", nullable = false, length = 100)
    private String clave;

    @Column(name = "valor", columnDefinition = "TEXT")
    private String valor;

    @Size(max = 255, message = "La descripción no puede exceder 255 caracteres")
    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_preferencia", nullable = false, length = 30)
    private TipoPreferencia tipoPreferencia = TipoPreferencia.SISTEMA;

    @Column(name = "categoria", length = 50)
    private String categoria;

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
     * Enums para PreferenciaUsuario
     */
    public enum TipoPreferencia {
        SISTEMA, NOTIFICACION, PRIVACIDAD, GAMING, MARKETING, PERSONALIZACION
    }
}
