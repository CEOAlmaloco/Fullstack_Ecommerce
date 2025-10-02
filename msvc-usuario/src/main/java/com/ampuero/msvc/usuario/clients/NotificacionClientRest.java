package com.ampuero.msvc.usuario.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * Cliente Feign para comunicación con msvc-notificaciones
 * Permite enviar notificaciones relacionadas con usuarios
 */
@FeignClient(name = "msvc-notificaciones", url = "localhost:8006")
public interface NotificacionClientRest {

    /**
     * Envía notificación de registro de usuario
     * @param notificacionData Datos de la notificación
     * @return Respuesta de la operación
     */
    @PostMapping("/api/v1/notificaciones/registro-usuario")
    ResponseEntity<Map<String, Object>> enviarNotificacionRegistro(@RequestBody Map<String, Object> notificacionData);

    /**
     * Envía notificación de actualización de perfil
     * @param notificacionData Datos de la notificación
     * @return Respuesta de la operación
     */
    @PostMapping("/api/v1/notificaciones/actualizacion-perfil")
    ResponseEntity<Map<String, Object>> enviarNotificacionActualizacion(@RequestBody Map<String, Object> notificacionData);

    /**
     * Envía notificación de cambio de nivel
     * @param notificacionData Datos de la notificación
     * @return Respuesta de la operación
     */
    @PostMapping("/api/v1/notificaciones/cambio-nivel")
    ResponseEntity<Map<String, Object>> enviarNotificacionCambioNivel(@RequestBody Map<String, Object> notificacionData);

    /**
     * Envía notificación de bienvenida
     * @param notificacionData Datos de la notificación
     * @return Respuesta de la operación
     */
    @PostMapping("/api/v1/notificaciones/bienvenida")
    ResponseEntity<Map<String, Object>> enviarNotificacionBienvenida(@RequestBody Map<String, Object> notificacionData);

    /**
     * Envía notificación de verificación de email
     * @param notificacionData Datos de la notificación
     * @return Respuesta de la operación
     */
    @PostMapping("/api/v1/notificaciones/verificacion-email")
    ResponseEntity<Map<String, Object>> enviarNotificacionVerificacionEmail(@RequestBody Map<String, Object> notificacionData);
}
