package com.ampuero.msvc.contenido.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ComentarioResponseDTO {
    private Long idComentario;
    private Long idArticulo;
    private Long idUsuario;
    private String nombreUsuario;
    private String contenidoComentario;
    private LocalDateTime fechaComentario;
    private String estadoComentario;
    private Integer likesComentario;
    private Long idComentarioPadre;
    private Boolean activo;
}
