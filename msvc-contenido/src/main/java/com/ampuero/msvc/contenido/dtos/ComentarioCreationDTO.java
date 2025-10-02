package com.ampuero.msvc.contenido.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ComentarioCreationDTO {
    @NotNull(message = "El ID del artículo no puede estar vacío")
    private Long idArticulo;
    
    @NotNull(message = "El ID del usuario no puede estar vacío")
    private Long idUsuario;
    
    private String nombreUsuario;
    
    @NotBlank(message = "El contenido del comentario no puede estar vacío")
    private String contenidoComentario;
    
    private Long idComentarioPadre; // Para respuestas anidadas
}
