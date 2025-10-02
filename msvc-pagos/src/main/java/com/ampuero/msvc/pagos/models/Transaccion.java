package com.ampuero.msvc.pagos.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transacciones")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Transaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaccion")
    private Long idTransaccion;

    @Column(name = "id_pago")
    private Long idPago;

    @Column(nullable = false, name = "tipo_transaccion")
    private String tipoTransaccion; // PAGO, REEMBOLSO, REVERSO, CONSULTA

    @Column(nullable = false, name = "monto_transaccion")
    private Double montoTransaccion;

    @Column(name = "moneda_transaccion")
    private String monedaTransaccion = "CLP";

    @Column(name = "numero_transaccion_externa")
    private String numeroTransaccionExterna;

    @Column(name = "fecha_transaccion")
    private LocalDateTime fechaTransaccion;

    @Column(name = "estado_transaccion")
    private String estadoTransaccion; // INICIADA, PROCESANDO, COMPLETADA, FALLIDA, CANCELADA

    @Column(name = "codigo_respuesta_externa")
    private String codigoRespuestaExterna;

    @Column(name = "mensaje_respuesta_externa")
    private String mensajeRespuestaExterna;

    @Column(name = "datos_respuesta")
    private String datosRespuesta; // JSON con respuesta completa del proveedor

    @Column(name = "tiempo_procesamiento")
    private Long tiempoProcesamiento; // en milisegundos

    @Column(name = "proveedor_pago")
    private String proveedorPago; // WEBPAY, PAYPAL, BANCO_CHILE, etc.

    @Column(name = "ip_cliente")
    private String ipCliente;

    @Column(name = "user_agent")
    private String userAgent;

    @Column(nullable = false)
    private Boolean activo = true;
}
