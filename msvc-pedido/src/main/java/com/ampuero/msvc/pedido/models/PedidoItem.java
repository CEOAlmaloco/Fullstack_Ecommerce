package com.ampuero.msvc.pedido.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pedido_items")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PedidoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item")
    private Long id;

    @Column(nullable = false, name = "id_pedido")
    private Long idPedido;

    @Column(nullable = false, name = "id_producto")
    private Long idProducto;

    @Column(nullable = false, name = "nombre_producto")
    private String nombreProducto;

    @Column(nullable = false)
    private Double precio;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private Double subtotal;

    @Column(name = "imagen_url")
    private String imagenUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido", insertable = false, updatable = false)
    private Pedido pedido;
}

