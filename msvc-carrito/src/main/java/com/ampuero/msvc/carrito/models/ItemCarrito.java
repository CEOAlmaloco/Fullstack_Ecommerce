package com.ampuero.msvc.carrito.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "items_carrito")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ItemCarrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item")
    private Long idItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carrito")
    private Carrito carrito;

    @Column(name = "id_producto")
    private Long idProducto;

    @Column(name = "nombre_producto")
    private String nombreProducto;

    @Column(name = "descripcion_producto")
    private String descripcionProducto;

    @Column(name = "precio_unitario")
    private Double precioUnitario;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "subtotal")
    private Double subtotal;

    @Column(name = "descuento_aplicado")
    private Double descuentoAplicado = 0.0;

    @Column(name = "impuesto_aplicado")
    private Double impuestoAplicado = 0.0;

    @Column(name = "total_item")
    private Double totalItem;

    @Column(name = "fecha_agregado")
    private LocalDateTime fechaAgregado;

    @Column(name = "fecha_actualizado")
    private LocalDateTime fechaActualizado;

    @Column(name = "estado_item")
    private String estadoItem = "ACTIVO"; // ACTIVO, ELIMINADO, NO_DISPONIBLE

    @Column(name = "notas_item")
    private String notasItem;

    @Column(nullable = false)
    private Boolean activo = true;
}
