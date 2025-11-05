package com.ampuero.msvc.eventos.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "eventos")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento")
    private Long idEvento;

    @Column(nullable = false, name = "nombre_evento")
    private String nombreEvento;

    @Column(name = "descripcion_evento")
    private String descripcionEvento;

    @Column(nullable = false, name = "fecha_inicio")
    private LocalDateTime fechaInicio;

    @Column(nullable = false, name = "fecha_fin")
    private LocalDateTime fechaFin;

    @Column(name = "ubicacion_evento")
    private String ubicacionEvento;

    @Column(name = "ciudad")
    private String ciudad;

    @Column(name = "coordenadas_latitud")
    private Double coordenadasLatitud;

    @Column(name = "coordenadas_longitud")
    private Double coordenadasLongitud;

    @Column(name = "imagen", columnDefinition = "TEXT")
    private String imagen;

    @Column(name = "imagenes", columnDefinition = "TEXT")
    private String imagenes;

    @Column(name = "tipo_evento")
    private String tipoEvento; // TORNEO, LANZAMIENTO, MEETUP, WORKSHOP

    @Column(name = "cupos_maximos")
    private Integer cuposMaximos;

    @Column(name = "cupos_disponibles")
    private Integer cuposDisponibles;

    @Column(name = "costo_entrada")
    private Double costoEntrada;

    @Column(name = "puntos_levelup")
    private Integer puntosLevelUp;

    @Column(nullable = false)
    private Boolean activo = true;

    @Column(name = "requisitos_edad")
    private Integer requisitosEdad;

    @Column(name = "equipos_requeridos")
    private String equiposRequeridos;
}
