package com.ampuero.msvc.producto.dtos;

/**
 * ProductoResponseDTO.java
 * <p>
 * Descripción:
 * Objeto de transferencia de datos (DTO) utilizado para las respuestas detalladas de productos.
 * Este DTO incluye toda la información del producto más metadatos adicionales como fechas
 * de creación y modificación. Diseñado para ser usado con HATEOAS.
 * <p>
 * Uso común:
 * - Respuestas GET detalladas de productos.
 * - Respuestas POST/PUT tras crear o actualizar productos.
 * - Integración con sistemas externos que necesitan información completa.
 * - Soporte para navegación HATEOAS.
 * <p>
 * Atributos:
 * - idProducto: Identificador único del producto.
 * - nombreProducto: Nombre descriptivo del producto.
 * - descripcionProducto: Descripción detallada del producto.
 * - precioProducto: Precio actual del producto.
 * - fechaCreacion: Timestamp de cuando fue creado el producto (opcional).
 * - fechaModificacion: Timestamp de la última modificación (opcional).
 * - activo: Indica si el producto está disponible para venta.
 * <p>
 * Autor: Alex Ignacio Ampuero Ahumada
 * Fecha de creación: [25-06-25]
 * Última modificación: [25-06-25]
 */

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "DTO de respuesta completa para productos")
public class ProductoResponseDTO extends RepresentationModel<ProductoResponseDTO> {

    @Schema(description = "Identificador único del producto", example = "1")
    private Long idProducto;

    @Schema(description = "Nombre del producto", example = "Laptop Gaming Asus ROG")
    private String nombreProducto;

    @Schema(description = "Descripción detallada del producto", example = "Laptop para gaming con procesador Intel i7, 16GB RAM, tarjeta gráfica RTX 4060")
    private String descripcionProducto;

    @Schema(description = "Precio del producto en pesos chilenos", example = "899990.99")
    private Double precioProducto;

    @Schema(description = "Fecha de creación del producto", example = "2024-06-25T10:30:00")
    private LocalDateTime fechaCreacion;

    @Schema(description = "Fecha de última modificación", example = "2024-06-25T14:45:00")
    private LocalDateTime fechaModificacion;

    @Schema(description = "Indica si el producto está activo", example = "true")
    private Boolean activo;

    @Schema(description = "Número de unidades disponibles en inventario", example = "25")
    private Integer stock;

    @Schema(description = "URL de la imagen principal del producto (desde S3)", example = "https://bucket.s3.amazonaws.com/productos/123/imagen.jpg")
    private String imagenUrl;

    @Schema(description = "URLs de imágenes adicionales del producto (JSON array desde S3)", example = "[\"https://bucket.s3.amazonaws.com/productos/123/img1.jpg\",\"https://bucket.s3.amazonaws.com/productos/123/img2.jpg\"]")
    private String imagenesUrls;

    @Schema(description = "Referencia a S3 de la imagen principal (key guardada en BD)", example = "productos/123/imagen.jpg")
    private String imagenS3Key;

    @Schema(description = "Referencias a S3 de imágenes adicionales (JSON array de keys guardadas en BD)", example = "[\"productos/123/img1.jpg\",\"productos/123/img2.jpg\"]")
    private String imagenesS3Keys;

    @Schema(description = "Título del producto (alias de nombreProducto)", example = "Laptop Gaming Asus ROG")
    private String titulo;

    @Schema(description = "Descripción del producto (alias de descripcionProducto)", example = "Laptop para gaming con procesador Intel i7")
    private String descripcion;

    @Schema(description = "Precio del producto (alias de precioProducto)", example = "899990.99")
    private Double precio;

    @Schema(description = "ID del producto (alias de idProducto)", example = "1")
    private Long id;

    @Schema(description = "Calificación promedio del producto", example = "4.5")
    private Double rating;

    @Schema(description = "Calificación calculada a partir de reseñas del microservicio de reseñas", example = "4.7")
    private Double ratingPromedio;

    @Schema(description = "Reseñas asociadas al producto")
    private List<ReseniaResumenDTO> reviews;

    @Schema(description = "Indica si el producto se encuentra con promoción activa", example = "true")
    private Boolean enOferta;

    @Schema(description = "Porcentaje de descuento aplicable", example = "5")
    private Double descuento;

    @Schema(description = "Precio final con descuento aplicado", example = "522490.5")
    private Double precioConDescuento;

    @Schema(description = "Marca o fabricante reportado por el microservicio", example = "Sony")
    private String fabricante;

    @Schema(description = "Distribuidor oficial del producto", example = "Level-Up Gamer")
    private String distribuidor;

    @Schema(description = "Bandera para destacar el producto en catálogos", example = "true")
    private Boolean destacado;

    @Schema(description = "Identificador de la categoría asociada", example = "CO")
    private String categoriaId;

    @Schema(description = "Nombre legible de la categoría", example = "Consola")
    private String categoriaNombre;

    @Schema(description = "Identificador de la subcategoría asociada", example = "MA")
    private String subcategoriaId;

    @Schema(description = "Nombre legible de la subcategoría", example = "Mandos")
    private String subcategoriaNombre;

    @Schema(description = "Código interno del producto", example = "CO001")
    private String codigoProducto;

    // Getters para compatibilidad con frontend
    public String getTitulo() {
        return titulo != null ? titulo : nombreProducto;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
        this.nombreProducto = titulo;
    }

    public String getDescripcion() {
        return descripcion != null ? descripcion : descripcionProducto;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
        this.descripcionProducto = descripcion;
    }

    public Double getPrecio() {
        return precio != null ? precio : precioProducto;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
        this.precioProducto = precio;
    }

    public Long getId() {
        return id != null ? id : idProducto;
    }

    public void setId(Long id) {
        this.id = id;
        this.idProducto = id;
    }

    public List<ReseniaResumenDTO> getReviews() {
        return reviews == null ? Collections.emptyList() : reviews;
    }

    public void setReviews(List<ReseniaResumenDTO> reviews) {
        this.reviews = reviews == null ? Collections.emptyList() : reviews;
    }

    public String getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(String categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getCategoriaNombre() {
        return categoriaNombre;
    }

    public void setCategoriaNombre(String categoriaNombre) {
        this.categoriaNombre = categoriaNombre;
    }

    public String getSubcategoriaId() {
        return subcategoriaId;
    }

    public void setSubcategoriaId(String subcategoriaId) {
        this.subcategoriaId = subcategoriaId;
    }

    public String getSubcategoriaNombre() {
        return subcategoriaNombre;
    }

    public void setSubcategoriaNombre(String subcategoriaNombre) {
        this.subcategoriaNombre = subcategoriaNombre;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }
} 