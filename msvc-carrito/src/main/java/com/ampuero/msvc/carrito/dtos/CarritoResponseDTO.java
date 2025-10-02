package com.ampuero.msvc.carrito.dtos;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CarritoResponseDTO {
    private Long idCarrito;
    private Long idUsuario;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private Double totalCarrito;
    private Double totalDescuentos;
    private Double totalImpuestos;
    private Double totalFinal;
    private String moneda;
    private String estadoCarrito;
    private LocalDateTime fechaExpiracion;
    private String codigoPromocional;
    private Long idPromocionAplicada;
    private String notasCarrito;
    private Boolean activo;
    private List<ItemCarritoResponseDTO> items;
}
