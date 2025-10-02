package com.ampuero.msvc.inventario.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "inventario")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inventario")
    private Long idInventario;

    @Column(nullable = false, name = "producto_id")
    private Long productoId;

    @Column(nullable = false, name = "cantidad_disponible")
    private Integer cantidadDisponible;

    @Column(nullable = false, name = "cantidad_reservada")
    private Integer cantidadReservada = 0;

    @Column(nullable = false, name = "stock_critico")
    private Integer stockCritico;

    @Column(nullable = false, name = "ubicacion_almacen")
    private String ubicacionAlmacen;

    @Column(nullable = false)
    private Boolean activo = true;
}
