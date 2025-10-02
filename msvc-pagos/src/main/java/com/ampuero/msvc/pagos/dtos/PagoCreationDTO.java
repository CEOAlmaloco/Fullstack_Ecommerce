package com.ampuero.msvc.pagos.dtos;

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
public class PagoCreationDTO {
    @NotNull(message = "El ID del pedido no puede estar vacío")
    private Long idPedido;
    
    @NotNull(message = "El ID del usuario no puede estar vacío")
    private Long idUsuario;
    
    @NotNull(message = "El monto del pago no puede estar vacío")
    @Positive(message = "El monto debe ser positivo")
    private Double montoPago;
    
    private String monedaPago = "CLP";
    
    @NotBlank(message = "El método de pago no puede estar vacío")
    private String metodoPago;
    
    private String numeroTarjetaEnmascarado;
    private String tipoTarjeta;
    private String bancoEmisor;
    private String datosAdicionales;
    private LocalDateTime fechaVencimiento;
}
