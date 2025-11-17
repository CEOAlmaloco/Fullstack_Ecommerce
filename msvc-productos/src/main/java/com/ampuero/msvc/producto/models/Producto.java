package com.ampuero.msvc.producto.models;

import com.ampuero.msvc.producto.dtos.ReseniaResumenDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    
    // Campo de compatibilidad para frontend React y Kotlin (alias de titulo)
    @JsonProperty("nombre")
    public String getNombre() {
        return titulo;
    }
    
    public void setNombre(String nombre) {
        this.titulo = nombre;
    }

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

    @Column(columnDefinition = "TEXT")
    @Schema(description = "URL de la imagen principal en Base64", examples = "data:image/png;base64,...")
    private String imagen;
    
    // Campo de compatibilidad para frontend React y Kotlin (alias de imagen)
    @JsonProperty("imagenUrl")
    public String getImagenUrl() {
        return imagen;
    }
    
    public void setImagenUrl(String imagenUrl) {
        this.imagen = imagenUrl;
    }

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

    @Transient
    private List<ReseniaResumenDTO> reviews = new ArrayList<>();

    @Transient
    private Double ratingPromedioCalculado;

    @Transient
    private Boolean ofertaActiva;

    @Transient
    private Double descuentoCalculado;

    @Transient
    private Double precioConDescuentoCalculado;

    @Transient
    private Boolean destacadoHome;

    @Column(name = "fabricante", length = 120)
    private String fabricante;

    @Column(name = "distribuidor", length = 120)
    private String distribuidor;

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

    public List<ReseniaResumenDTO> getReviews() {
        return reviews == null ? Collections.emptyList() : Collections.unmodifiableList(reviews);
    }

    public void setReviews(List<ReseniaResumenDTO> reviews) {
        if (reviews == null) {
            this.reviews = new ArrayList<>();
        } else {
            this.reviews = new ArrayList<>(reviews);
        }
    }
    
    public void appendReview(ReseniaResumenDTO review) {
        if (this.reviews == null) {
            this.reviews = new ArrayList<>();
        }
        if (review != null) {
            this.reviews.add(review);
        }
    }
}
