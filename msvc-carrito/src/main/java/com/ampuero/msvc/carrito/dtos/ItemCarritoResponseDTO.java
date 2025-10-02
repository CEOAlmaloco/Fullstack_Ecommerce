package com.ampuero.msvc.carrito.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ItemCarritoResponseDTO {
    private Long idItem;
    private Long idCarrito;
    private Long idProducto;
    private String nombreProducto;
    private String descripcionProducto;
    private Double precioUnitario;
    private Integer cantidad;
    private Double subtotal;
    private Double descuentoAplicado;
    private Double impuestoAplicado;
    private Double totalItem;
    private LocalDateTime fechaAgregado;
    private LocalDateTime fechaActualizado;
    private String estadoItem;
    private String notasItem;
    private Boolean activo;
}
