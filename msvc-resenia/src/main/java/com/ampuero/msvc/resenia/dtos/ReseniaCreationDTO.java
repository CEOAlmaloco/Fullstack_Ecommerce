package com.ampuero.msvc.resenia.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.Data;

@Data
public class ReseniaCreationDTO {
    // idProducto se establece desde el path variable, no necesita validación aquí
    private Long idProducto;

    @NotNull(message = "El ID de usuario es requerido")
    private Long idUsuario;

    @NotBlank(message = "El nombre de usuario es requerido")
    private String usuarioNombre;

    @NotNull(message = "El rating es requerido")
    @Min(value = 1, message = "El rating debe ser al menos 1")
    @Max(value = 5, message = "El rating no puede ser mayor a 5")
    private Integer rating;

    @NotBlank(message = "El comentario es requerido")
    private String comentario;
}

