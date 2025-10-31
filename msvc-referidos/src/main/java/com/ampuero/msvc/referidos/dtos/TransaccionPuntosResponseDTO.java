package com.ampuero.msvc.referidos.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransaccionPuntosResponseDTO {
    private Long id;
    private Long idUsuario;
    private Long idEvento;
    private String tipoTransaccion;
    private Integer puntos;
    private Integer puntosAnteriores;
    private Integer puntosNuevos;
    private String descripcion;
    private String codigoReferencia;
    private LocalDateTime fechaTransaccion;
    private EventoPuntosResponseDTO evento;
}

