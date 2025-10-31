package com.ampuero.msvc.resenia.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReseniaResponseDTO {
    private Long id;
    private Long idProducto;
    private Long idUsuario;
    private String usuarioNombre;
    private Integer rating;
    private String comentario;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private Boolean activo;
}

