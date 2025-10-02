package com.ampuero.msvc.pagos.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Long idPago;

    @Column(name = "id_pedido")
    private Long idPedido;

    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(nullable = false, name = "monto_pago")
    private Double montoPago;

    @Column(nullable = false, name = "moneda_pago")
    private String monedaPago = "CLP";

    @Column(nullable = false, name = "metodo_pago")
    private String metodoPago; // TARJETA_CREDITO, TARJETA_DEBITO, TRANSFERENCIA, PAYPAL, WEBPAY

    @Column(name = "numero_tarjeta_enmascarado")
    private String numeroTarjetaEnmascarado;

    @Column(name = "tipo_tarjeta")
    private String tipoTarjeta; // VISA, MASTERCARD, AMERICAN_EXPRESS

    @Column(name = "banco_emisor")
    private String bancoEmisor;

    @Column(name = "numero_transaccion")
    private String numeroTransaccion;

    @Column(name = "codigo_autorizacion")
    private String codigoAutorizacion;

    @Column(name = "fecha_pago")
    private LocalDateTime fechaPago;

    @Column(name = "fecha_procesamiento")
    private LocalDateTime fechaProcesamiento;

    @Column(name = "estado_pago")
    private String estadoPago; // PENDIENTE, PROCESANDO, APROBADO, RECHAZADO, CANCELADO, REEMBOLSADO

    @Column(name = "codigo_respuesta")
    private String codigoRespuesta;

    @Column(name = "mensaje_respuesta")
    private String mensajeRespuesta;

    @Column(name = "comision_pago")
    private Double comisionPago;

    @Column(name = "monto_neto")
    private Double montoNeto;

    @Column(name = "datos_adicionales")
    private String datosAdicionales; // JSON con datos adicionales

    @Column(name = "intentos_pago")
    private Integer intentosPago = 0;

    @Column(name = "max_intentos")
    private Integer maxIntentos = 3;

    @Column(name = "fecha_vencimiento")
    private LocalDateTime fechaVencimiento;

    @Column(nullable = false)
    private Boolean activo = true;
}
