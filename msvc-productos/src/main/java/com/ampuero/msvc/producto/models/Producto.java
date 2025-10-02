package com.ampuero.msvc.producto.models;

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

    @Column(name = "categoria_id")
    @Schema(description = "ID de la categoría", examples = "CO")
    private String categoriaId;

    @Column(name = "subcategoria_id")
    @Schema(description = "ID de la subcategoría", examples = "HA")
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

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
