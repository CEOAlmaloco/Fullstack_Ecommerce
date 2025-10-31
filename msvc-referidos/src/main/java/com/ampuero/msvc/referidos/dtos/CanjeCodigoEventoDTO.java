package com.ampuero.msvc.referidos.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CanjeCodigoEventoDTO {
    @NotBlank(message = "El código del evento es requerido")
    private String codigoEvento;
}

