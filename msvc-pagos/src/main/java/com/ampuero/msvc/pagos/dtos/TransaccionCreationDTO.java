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
public class TransaccionCreationDTO {
    @NotNull(message = "El ID del pago no puede estar vacío")
    private Long idPago;
    
    @NotBlank(message = "El tipo de transacción no puede estar vacío")
    private String tipoTransaccion;
    
    @NotNull(message = "El monto de la transacción no puede estar vacío")
    @Positive(message = "El monto debe ser positivo")
    private Double montoTransaccion;
    
    private String monedaTransaccion = "CLP";
    private String numeroTransaccionExterna;
    private String proveedorPago;
    private String ipCliente;
    private String userAgent;
}
