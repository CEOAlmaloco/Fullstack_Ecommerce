package com.ampuero.msvc.carrito.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "carritos")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carrito")
    private Long idCarrito;

    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @Column(name = "total_carrito")
    private Double totalCarrito = 0.0;

    @Column(name = "total_descuentos")
    private Double totalDescuentos = 0.0;

    @Column(name = "total_impuestos")
    private Double totalImpuestos = 0.0;

    @Column(name = "total_final")
    private Double totalFinal = 0.0;

    @Column(name = "moneda")
    private String moneda = "CLP";

    @Column(name = "estado_carrito")
    private String estadoCarrito = "ACTIVO"; // ACTIVO, CONVERTIDO, ABANDONADO, EXPIRADO

    @Column(name = "fecha_expiracion")
    private LocalDateTime fechaExpiracion;

    @Column(name = "codigo_promocional")
    private String codigoPromocional;

    @Column(name = "id_promocion_aplicada")
    private Long idPromocionAplicada;

    @Column(name = "notas_carrito")
    private String notasCarrito;

    @Column(nullable = false)
    private Boolean activo = true;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemCarrito> items;
}
