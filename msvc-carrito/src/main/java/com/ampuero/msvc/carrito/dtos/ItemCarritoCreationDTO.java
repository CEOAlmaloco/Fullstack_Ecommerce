package com.ampuero.msvc.carrito.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ItemCarritoCreationDTO {
    @NotNull(message = "El ID del carrito no puede estar vacío")
    private Long idCarrito;
    
    @NotNull(message = "El ID del producto no puede estar vacío")
    private Long idProducto;
    
    @NotNull(message = "La cantidad no puede estar vacía")
    @Positive(message = "La cantidad debe ser positiva")
    private Integer cantidad;
    
    private String notasItem;
}
