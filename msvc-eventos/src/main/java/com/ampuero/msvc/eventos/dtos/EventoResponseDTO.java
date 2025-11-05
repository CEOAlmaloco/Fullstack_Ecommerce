package com.ampuero.msvc.eventos.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventoResponseDTO {
    private Long idEvento;
    private String nombreEvento;
    private String descripcionEvento;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String ubicacionEvento;
    private Double coordenadasLatitud;
    private Double coordenadasLongitud;
    private String tipoEvento;
    private Integer cuposMaximos;
    private Integer cuposDisponibles;
    private Double costoEntrada;
    private Integer puntosLevelUp;
    private Boolean activo;
    private Integer requisitosEdad;
    private String equiposRequeridos;
    private String ciudad;
    private String imagen;
    private String imagenes;
}
