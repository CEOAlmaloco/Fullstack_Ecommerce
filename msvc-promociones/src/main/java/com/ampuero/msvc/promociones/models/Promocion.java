package com.ampuero.msvc.promociones.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "promociones")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Promocion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_promocion")
    private Long idPromocion;

    @Column(nullable = false, name = "codigo_promocion")
    private String codigoPromocion;

    @Column(nullable = false, name = "nombre_promocion")
    private String nombrePromocion;

    @Column(name = "descripcion_promocion")
    private String descripcionPromocion;

    @Column(nullable = false, name = "tipo_descuento")
    private String tipoDescuento; // PORCENTAJE, MONTO_FIJO, ENVIO_GRATIS

    @Column(nullable = false, name = "valor_descuento")
    private Double valorDescuento;

    @Column(name = "monto_minimo")
    private Double montoMinimo;

    @Column(name = "monto_maximo_descuento")
    private Double montoMaximoDescuento;

    @Column(nullable = false, name = "fecha_inicio")
    private LocalDateTime fechaInicio;

    @Column(nullable = false, name = "fecha_fin")
    private LocalDateTime fechaFin;

    @Column(name = "usos_maximos")
    private Integer usosMaximos;

    @Column(name = "usos_actuales")
    private Integer usosActuales = 0;

    @Column(name = "usos_por_usuario")
    private Integer usosPorUsuario = 1;

    @Column(nullable = false)
    private Boolean activo = true;

    @Column(name = "aplicable_duoc")
    private Boolean aplicableDuoc = false;

    @Column(name = "categoria_aplicable")
    private String categoriaAplicable;
}
