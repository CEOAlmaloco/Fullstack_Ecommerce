package com.ampuero.msvc.contenido.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArticuloResponseDTO {
    private Long idArticulo;
    private String tituloArticulo;
    private String contenidoArticulo;
    private String resumenArticulo;
    private String imagenArticulo;
    private String categoriaArticulo;
    private String etiquetasArticulo;
    private String autorArticulo;
    private LocalDateTime fechaPublicacion;
    private LocalDateTime fechaActualizacion;
    private String estadoArticulo;
    private Integer vistasArticulo;
    private Integer likesArticulo;
    private Integer compartidosArentario;
    private Integer tiempoLectura;
    private Boolean esDestacado;
    private Boolean esPremium;
    private Boolean activo;
}
