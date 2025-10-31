package com.ampuero.msvc.promociones.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PromocionResponseDTO {
    private Long idPromocion;
    private String codigoPromocion;
    private String nombrePromocion;
    private String descripcionPromocion;
    private String tipoDescuento;
    private Double valorDescuento;
    private Double montoMinimo;
    private Double montoMaximoDescuento;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Integer usosMaximos;
    private Integer usosActuales;
    private Integer usosPorUsuario;
    private Boolean activo;
    private Boolean aplicableDuoc;
    private String categoriaAplicable;
    private Integer puntosRequeridos;
    private String tipoPromocion;
}
