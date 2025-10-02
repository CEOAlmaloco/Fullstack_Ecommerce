package com.ampuero.msvc.notificaciones.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificacionResponseDTO {
    private Long idNotificacion;
    private Long idUsuario;
    private String tipoNotificacion;
    private String canalNotificacion;
    private String asuntoNotificacion;
    private String contenidoNotificacion;
    private String plantillaNotificacion;
    private String destinatarioNotificacion;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaProgramada;
    private LocalDateTime fechaEnvio;
    private LocalDateTime fechaEntrega;
    private LocalDateTime fechaApertura;
    private String estadoNotificacion;
    private Integer intentosEnvio;
    private Integer maxIntentos;
    private String errorMensaje;
    private String metadataNotificacion;
    private String prioridadNotificacion;
    private Boolean activo;
}
