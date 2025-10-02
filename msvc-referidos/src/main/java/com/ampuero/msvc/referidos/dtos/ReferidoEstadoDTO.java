package com.ampuero.msvc.referidos.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReferidoEstadoDTO {

    @NotNull(message = "El estado es obligatorio")
    private Boolean activo;
}
