package com.ampuero.msvc.inventario.dtos;

import lombok.Data;

@Data
public class InventarioResponseDTO {
    private Long idInventario;
    private Long productoId;
    private Integer cantidadDisponible;
    private Integer cantidadReservada;
    private Integer stockCritico;
    private String ubicacionAlmacen;
    private Boolean activo;
}

