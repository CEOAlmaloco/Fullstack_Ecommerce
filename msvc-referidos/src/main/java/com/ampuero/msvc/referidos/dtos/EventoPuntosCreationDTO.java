package com.ampuero.msvc.referidos.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventoPuntosCreationDTO {
    @NotNull(message = "El tipo de evento es requerido")
    private String tipoEvento;

    @NotBlank(message = "El nombre del evento es requerido")
    private String nombreEvento;

    private String descripcionEvento;

    @NotNull(message = "Los puntos otorgados son requeridos")
    private Integer puntosOtorgados;

    private String codigoEvento;

    private LocalDateTime fechaEvento;

    private String lugarEvento;

    private String direccionEvento;

    private Long idUsuario;

    private Long idProducto;

    private Long idPedido;
}

