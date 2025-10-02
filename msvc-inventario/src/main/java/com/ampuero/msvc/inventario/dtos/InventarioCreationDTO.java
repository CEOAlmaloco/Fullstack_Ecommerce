package com.ampuero.msvc.inventario.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InventarioCreationDTO {

    @NotNull(message = "El ID del producto es requerido")
    private Long productoId;

    @NotNull(message = "La cantidad disponible es requerida")
    @Min(value = 0, message = "La cantidad no puede ser negativa")
    private Integer cantidadDisponible;

    @NotNull(message = "El stock crítico es requerido")
    @Min(value = 0, message = "El stock crítico no puede ser negativo")
    private Integer stockCritico;

    @NotNull(message = "La ubicación del almacén es requerida")
    private String ubicacionAlmacen;
}