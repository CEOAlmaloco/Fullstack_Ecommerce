package com.ampuero.msvc.pagos.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "msvc-notificaciones", url = "${MSVC_NOTIFICACIONES_URL:http://localhost:8006}")
public interface NotificacionClientRest {

    @PostMapping("/notificaciones")
    ResponseEntity<Map<String, Object>> crearNotificacion(@RequestBody Map<String, Object> notificacionData);

    @PostMapping("/notificaciones/pago-exitoso")
    ResponseEntity<Map<String, Object>> notificarPagoExitoso(@RequestBody Map<String, Object> pagoData);

    @PostMapping("/notificaciones/pago-fallido")
    ResponseEntity<Map<String, Object>> notificarPagoFallido(@RequestBody Map<String, Object> pagoData);

    @PostMapping("/notificaciones/reembolso")
    ResponseEntity<Map<String, Object>> notificarReembolso(@RequestBody Map<String, Object> reembolsoData);
}
