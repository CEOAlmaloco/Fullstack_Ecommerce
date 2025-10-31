package com.ampuero.msvc.referidos.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventoPuntosResponseDTO {
    private Long id;
    private String tipoEvento;
    private String nombreEvento;
    private String descripcionEvento;
    private Integer puntosOtorgados;
    private String codigoEvento;
    private LocalDateTime fechaEvento;
    private String lugarEvento;
    private String direccionEvento;
    private Long idUsuario;
    private Long idProducto;
    private Long idPedido;
    private LocalDateTime fechaCreacion;
    private Boolean activo;
}

