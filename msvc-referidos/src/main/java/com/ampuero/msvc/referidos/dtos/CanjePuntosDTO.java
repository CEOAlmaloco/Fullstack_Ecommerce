package com.ampuero.msvc.referidos.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CanjePuntosDTO {
    @NotNull(message = "Los puntos a canjear son requeridos")
    private Integer puntosACanjear;

    private String descripcion;

    private String codigoReferencia;
}

