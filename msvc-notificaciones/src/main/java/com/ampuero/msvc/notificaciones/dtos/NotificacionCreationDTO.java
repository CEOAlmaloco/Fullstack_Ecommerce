package com.ampuero.msvc.notificaciones.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionCreationDTO {
    @NotNull(message = "El ID del usuario no puede estar vacío")
    private Long idUsuario;
    
    @NotBlank(message = "El tipo de notificación no puede estar vacío")
    private String tipoNotificacion;
    
    @NotBlank(message = "El canal de notificación no puede estar vacío")
    private String canalNotificacion;
    
    @NotBlank(message = "El asunto no puede estar vacío")
    private String asuntoNotificacion;
    
    private String contenidoNotificacion;
    private String plantillaNotificacion;
    private String destinatarioNotificacion;
    private LocalDateTime fechaProgramada;
    private String prioridadNotificacion = "MEDIA";
    private String metadataNotificacion;
}
