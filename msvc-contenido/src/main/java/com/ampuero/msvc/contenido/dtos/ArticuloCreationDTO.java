package com.ampuero.msvc.contenido.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ArticuloCreationDTO {
    @NotBlank(message = "El título del artículo no puede estar vacío")
    private String tituloArticulo;
    
    private String contenidoArticulo;
    private String resumenArticulo;
    private String imagenArticulo;
    
    @NotBlank(message = "La categoría del artículo no puede estar vacía")
    private String categoriaArticulo;
    
    private String etiquetasArticulo;
    private String autorArticulo;
    private LocalDateTime fechaPublicacion;
    private String estadoArticulo;
    private Integer tiempoLectura;
    private Boolean esDestacado = false;
    private Boolean esPremium = false;
}
