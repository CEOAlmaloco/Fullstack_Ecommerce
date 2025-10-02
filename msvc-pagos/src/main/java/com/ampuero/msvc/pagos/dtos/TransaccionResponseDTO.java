package com.ampuero.msvc.pagos.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransaccionResponseDTO {
    private Long idTransaccion;
    private Long idPago;
    private String tipoTransaccion;
    private Double montoTransaccion;
    private String monedaTransaccion;
    private String numeroTransaccionExterna;
    private LocalDateTime fechaTransaccion;
    private String estadoTransaccion;
    private String codigoRespuestaExterna;
    private String mensajeRespuestaExterna;
    private String datosRespuesta;
    private Long tiempoProcesamiento;
    private String proveedorPago;
    private String ipCliente;
    private String userAgent;
    private Boolean activo;
}
