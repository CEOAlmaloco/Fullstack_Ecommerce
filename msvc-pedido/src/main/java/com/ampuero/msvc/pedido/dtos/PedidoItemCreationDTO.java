package com.ampuero.msvc.pedido.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PedidoItemCreationDTO {
    @NotNull(message = "El ID de producto es requerido")
    private Long idProducto;

    @NotBlank(message = "El nombre del producto es requerido")
    private String nombreProducto;

    @NotNull(message = "El precio es requerido")
    private Double precio;

    @NotNull(message = "La cantidad es requerida")
    private Integer cantidad;

    @NotNull(message = "El subtotal es requerido")
    private Double subtotal;

    private String imagenUrl;
}

