package com.ampuero.msvc.referidos.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "msvc-notificaciones", url = "localhost:8086")
public interface NotificacionClientRest {

    @PostMapping("/api/v1/notificaciones/email")
    Map<String, Object> enviarNotificacionEmail(@RequestBody Map<String, Object> notificacionData);

    @PostMapping("/api/v1/notificaciones/referido-exitoso")
    Map<String, Object> notificarReferidoExitoso(@RequestBody Map<String, Object> referidoData);

    @PostMapping("/api/v1/notificaciones/puntos-otorgados")
    Map<String, Object> notificarPuntosOtorgados(@RequestBody Map<String, Object> puntosData);

    @PostMapping("/api/v1/notificaciones/nivel-ascendido")
    Map<String, Object> notificarNivelAscendido(@RequestBody Map<String, Object> nivelData);

    @PostMapping("/api/v1/notificaciones/canje-realizado")
    Map<String, Object> notificarCanjeRealizado(@RequestBody Map<String, Object> canjeData);
}
