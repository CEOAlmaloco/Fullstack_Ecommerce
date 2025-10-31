package com.ampuero.msvc.referidos.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "puntos_usuario")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PuntosUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_puntos")
    private Long id;

    @Column(nullable = false, name = "id_usuario")
    private Long idUsuario;

    @Column(nullable = false, name = "puntos_totales")
    private Integer puntosTotales = 0;

    @Column(nullable = false, name = "puntos_disponibles")
    private Integer puntosDisponibles = 0;

    @Column(nullable = false, name = "puntos_usados")
    private Integer puntosUsados = 0;

    @Column(nullable = false, name = "nivel_usuario")
    @Enumerated(EnumType.STRING)
    private NivelUsuario nivelUsuario = NivelUsuario.BRONZE;

    @Column(name = "codigo_referido")
    private String codigoReferido;

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
        if (puntosTotales == null) puntosTotales = 0;
        if (puntosDisponibles == null) puntosDisponibles = 0;
        if (puntosUsados == null) puntosUsados = 0;
        if (nivelUsuario == null) nivelUsuario = NivelUsuario.BRONZE;
        if (activo == null) activo = true;
        // Generar código referido si no existe
        if (codigoReferido == null || codigoReferido.isEmpty()) {
            codigoReferido = generarCodigoReferido(idUsuario);
        }
    }

    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
        // Actualizar nivel según puntos totales
        actualizarNivel();
    }

    private void actualizarNivel() {
        if (puntosTotales >= 10000) {
            nivelUsuario = NivelUsuario.DIAMANTE;
        } else if (puntosTotales >= 5000) {
            nivelUsuario = NivelUsuario.PLATINO;
        } else if (puntosTotales >= 2500) {
            nivelUsuario = NivelUsuario.ORO;
        } else if (puntosTotales >= 1000) {
            nivelUsuario = NivelUsuario.PLATA;
        } else {
            nivelUsuario = NivelUsuario.BRONZE;
        }
    }

    private String generarCodigoReferido(Long idUsuario) {
        return "REF-" + idUsuario + "-" + System.currentTimeMillis();
    }

    public enum NivelUsuario {
        BRONZE, PLATA, ORO, PLATINO, DIAMANTE
    }
}

