package com.ampuero.msvc.carrito.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CarritoCreationDTO {
    @NotNull(message = "El ID del usuario no puede estar vacío")
    private Long idUsuario;
    
    private String codigoPromocional;
    private String notasCarrito;
}
