package com.ampuero.msvc.pagos.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PagoResponseDTO {
    private Long idPago;
    private Long idPedido;
    private Long idUsuario;
    private Double montoPago;
    private String monedaPago;
    private String metodoPago;
    private String numeroTarjetaEnmascarado;
    private String tipoTarjeta;
    private String bancoEmisor;
    private String numeroTransaccion;
    private String codigoAutorizacion;
    private LocalDateTime fechaPago;
    private LocalDateTime fechaProcesamiento;
    private String estadoPago;
    private String codigoRespuesta;
    private String mensajeRespuesta;
    private Double comisionPago;
    private Double montoNeto;
    private String datosAdicionales;
    private Integer intentosPago;
    private Integer maxIntentos;
    private LocalDateTime fechaVencimiento;
    private Boolean activo;
}
