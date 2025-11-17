package com.ampuero.msvc.contenido.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArticuloResponseDTO {
    // Campos originales (compatibilidad con TypeScript)
    private Long idArticulo;
    private String tituloArticulo;
    private String contenidoArticulo;
    private String resumenArticulo;
    private String imagenArticulo;
    private String imagenUrl;  // Campo adicional para compatibilidad con Kotlin (URL completa de S3)
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
    
    // Campos adicionales para compatibilidad con Kotlin
    // id: String (alias de idArticulo)
    public String getId() {
        return idArticulo != null ? idArticulo.toString() : null;
    }
    
    // titulo: String (alias de tituloArticulo)
    public String getTitulo() {
        return tituloArticulo;
    }
    
    // contenido: String (alias de contenidoArticulo)
    public String getContenido() {
        return contenidoArticulo;
    }
    
    // resumen: String (alias de resumenArticulo)
    public String getResumen() {
        return resumenArticulo;
    }
    
    // imagenUrl: String (campo adicional para compatibilidad con Kotlin)
    // El getter ahora devuelve el campo imagenUrl si está setado, sino imagenArticulo
    public String getImagenUrl() {
        return imagenUrl != null ? imagenUrl : imagenArticulo;
    }
    
    // categoria: String (alias de categoriaArticulo)
    public String getCategoria() {
        return categoriaArticulo;
    }
    
    // autor: String (alias de autorArticulo)
    public String getAutor() {
        return autorArticulo;
    }
    
    // fechaPublicacion: String (formato ISO)
    public String getFechaPublicacionString() {
        return fechaPublicacion != null ? fechaPublicacion.toString() : null;
    }
    
    // tags: List<String> (parseado de etiquetasArticulo)
    public java.util.List<String> getTags() {
        if (etiquetasArticulo == null || etiquetasArticulo.isEmpty()) {
            return java.util.Collections.emptyList();
        }
        return java.util.Arrays.asList(etiquetasArticulo.split(","));
    }
    
    // destacado: Boolean (alias de esDestacado)
    public Boolean getDestacado() {
        return esDestacado;
    }
}
