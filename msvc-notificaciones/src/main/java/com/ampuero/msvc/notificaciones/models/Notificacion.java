package com.ampuero.msvc.notificaciones.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificacion")
    private Long idNotificacion;

    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(nullable = false, name = "tipo_notificacion")
    private String tipoNotificacion; // EMAIL, WHATSAPP, SMS, PUSH

    @Column(nullable = false, name = "canal_notificacion")
    private String canalNotificacion; // TRANSACCIONAL, PROMOCIONAL, OPERACIONAL

    @Column(nullable = false, name = "asunto_notificacion")
    private String asuntoNotificacion;

    @Column(name = "contenido_notificacion", columnDefinition = "TEXT")
    private String contenidoNotificacion;

    @Column(name = "plantilla_notificacion")
    private String plantillaNotificacion;

    @Column(name = "destinatario_notificacion")
    private String destinatarioNotificacion;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_programada")
    private LocalDateTime fechaProgramada;

    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio;

    @Column(name = "fecha_entrega")
    private LocalDateTime fechaEntrega;

    @Column(name = "fecha_apertura")
    private LocalDateTime fechaApertura;

    @Column(name = "estado_notificacion")
    private String estadoNotificacion; // CREADA, EN_COLA, ENVIANDO, ENVIADA, ENTREGADA, ABIERTA, FALLIDA, CANCELADA

    @Column(name = "intentos_envio")
    private Integer intentosEnvio = 0;

    @Column(name = "max_intentos")
    private Integer maxIntentos = 3;

    @Column(name = "error_mensaje")
    private String errorMensaje;

    @Column(name = "metadata_notificacion")
    private String metadataNotificacion; // JSON con datos adicionales

    @Column(name = "prioridad_notificacion")
    private String prioridadNotificacion; // ALTA, MEDIA, BAJA

    @Column(nullable = false)
    private Boolean activo = true;
}
