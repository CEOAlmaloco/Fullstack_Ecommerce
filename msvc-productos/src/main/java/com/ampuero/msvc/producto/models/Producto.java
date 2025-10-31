package com.ampuero.msvc.producto.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "productos")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Schema(description = "Entidad que representa un producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    @Schema(description = "primary key de producto", examples = "1")
    private Long id;

    @NotNull(message = "El título del producto no puede estar vacío")
    @Column(nullable = false)
    @Schema(description = "título del producto", examples = "PlayStation 5")
    private String titulo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    @Schema(description = "Categoría del producto")
    @JsonIgnore
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subcategoria_id", nullable = false)
    @Schema(description = "Subcategoría del producto")
    @JsonIgnore
    private Subcategoria subcategoria;

    // Campos de compatibilidad para acceso rápido a IDs (sin relaciones) - serializables
    @JsonProperty("categoriaId")
    public String getCategoriaId() {
        if (categoria != null) {
            return categoria.getId();
        }
        return categoriaId;
    }
    
    public void setCategoriaId(String categoriaId) {
        this.categoriaId = categoriaId;
    }
    
    @JsonProperty("subcategoriaId")
    public String getSubcategoriaId() {
        if (subcategoria != null) {
            return subcategoria.getId();
        }
        return subcategoriaId;
    }
    
    public void setSubcategoriaId(String subcategoriaId) {
        this.subcategoriaId = subcategoriaId;
    }
    
    // Campos temporales para almacenar IDs (no serializados directamente, se acceden vía getters)
    @Transient
    private String categoriaId;
    
    @Transient
    private String subcategoriaId;

    @Column
    @Schema(description = "URL de la imagen principal", examples = "/img/consolas/4.png")
    private String imagen;

    @Column(columnDefinition = "TEXT")
    @Schema(description = "URLs de imágenes adicionales en formato JSON")
    private String imagenes;

    @NotNull(message = "El precio del producto no puede ser nulo")
    @PositiveOrZero(message = "El precio debe ser positivo o cero")
    @Column
    @Schema(description = "precio del producto", examples = "549990.0")
    private Double precio;

    @Column
    @Schema(description = "disponibilidad del producto")
    private Boolean disponible = true;

    @Column
    @Schema(description = "calificación promedio del producto", examples = "4.5")
    private Double rating = 0.0;

    @Column(columnDefinition = "TEXT")
    @Schema(description = "descripción detallada del producto")
    private String descripcion;

    @Column
    @Schema(description = "stock disponible del producto", examples = "15")
    private Integer stock = 0;

    @Column(name = "codigo_producto", unique = true)
    @Schema(description = "Código único del producto", examples = "PROD-001")
    private String codigoProducto;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PostLoad
    protected void onLoad() {
        // Generar código si no existe después de cargar desde BD
        if ((codigoProducto == null || codigoProducto.isEmpty()) && id != null) {
            codigoProducto = "PROD-" + String.format("%06d", id);
        }
        // Poblar IDs de compatibilidad desde relaciones
        if (categoria != null) {
            this.categoriaId = categoria.getId();
        }
        if (subcategoria != null) {
            this.subcategoriaId = subcategoria.getId();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
