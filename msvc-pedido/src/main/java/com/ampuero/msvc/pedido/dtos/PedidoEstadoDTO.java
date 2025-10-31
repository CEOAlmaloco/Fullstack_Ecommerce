package com.ampuero.msvc.pedido.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PedidoEstadoDTO {
    @NotBlank(message = "El estado es requerido")
    private String estado;
}

