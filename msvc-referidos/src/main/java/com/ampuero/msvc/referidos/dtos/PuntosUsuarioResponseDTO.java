package com.ampuero.msvc.referidos.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PuntosUsuarioResponseDTO {
    private Long id;
    private Long idUsuario;
    private Integer puntosTotales;
    private Integer puntosDisponibles;
    private Integer puntosUsados;
    private String nivelUsuario;
    private String codigoReferido;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}

