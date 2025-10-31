package com.ampuero.msvc.resenia.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "resenias")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Resenia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resenia")
    private Long id;

    @Column(nullable = false, name = "id_producto")
    private Long idProducto;

    @Column(nullable = false, name = "id_usuario")
    private Long idUsuario;

    @Column(nullable = false, name = "usuario_nombre")
    private String usuarioNombre;

    @Column(nullable = false, columnDefinition = "INT CHECK (rating >= 1 AND rating <= 5)")
    private Integer rating;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String comentario;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @Column(nullable = false)
    private Boolean activo = true;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
        if (activo == null) {
            activo = true;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
}

