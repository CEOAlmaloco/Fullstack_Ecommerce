package com.ampuero.msvc.promociones.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PromocionCreationDTO {
    @NotBlank(message = "El código de promoción no puede estar vacío")
    private String codigoPromocion;
    
    @NotBlank(message = "El nombre de promoción no puede estar vacío")
    private String nombrePromocion;
    
    private String descripcionPromocion;
    
    @NotBlank(message = "El tipo de descuento no puede estar vacío")
    private String tipoDescuento;
    
    @NotNull(message = "El valor de descuento no puede estar vacío")
    @Positive(message = "El valor de descuento debe ser positivo")
    private Double valorDescuento;
    
    private Double montoMinimo;
    private Double montoMaximoDescuento;
    
    @NotNull(message = "La fecha de inicio no puede estar vacía")
    private LocalDateTime fechaInicio;
    
    @NotNull(message = "La fecha de fin no puede estar vacía")
    private LocalDateTime fechaFin;
    
    private Integer usosMaximos;
    private Integer usosPorUsuario = 1;
    private Boolean aplicableDuoc = false;
    private String categoriaAplicable;
    private Integer puntosRequeridos = 0;
    private String tipoPromocion = "DESCUENTO"; // DESCUENTO, OFERTA, CANJE_PUNTOS
}
