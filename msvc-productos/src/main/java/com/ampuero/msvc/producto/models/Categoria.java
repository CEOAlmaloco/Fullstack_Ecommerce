package com.ampuero.msvc.producto.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "categorias")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa una categoría de producto")
public class Categoria {
    @Id
    @Column(name = "id", length = 10)
    @Schema(description = "ID único de la categoría", examples = "CO")
    private String id;

    @Column(nullable = false, length = 100)
    @Schema(description = "Nombre de la categoría", examples = "Consolas")
    private String nombre;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Subcategoria> subcategorias;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Producto> productos;
}

