package com.ampuero.msvc.producto.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "subcategorias")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa una subcategoría de producto")
public class Subcategoria {
    @Id
    @Column(name = "id", length = 10)
    @Schema(description = "ID único de la subcategoría", examples = "HA")
    private String id;

    @Column(nullable = false, length = 100)
    @Schema(description = "Nombre de la subcategoría", examples = "Hardware")
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    @Schema(description = "Categoría a la que pertenece esta subcategoría")
    private Categoria categoria;

    @OneToMany(mappedBy = "subcategoria", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Producto> productos;
}

