package com.ampuero.msvc.usuario.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * Cliente Feign para comunicación con msvc-inventario
 * Permite gestionar el inventario relacionado con usuarios
 */
@FeignClient(name = "msvc-inventario", url = "${MSVC_INVENTARIO_URL:http://localhost:8004}")
public interface InventarioClientRest {

    /**
     * Crea un historial de inventario para un usuario
     * @param historialData Datos del historial
     * @return Respuesta de la operación
     */
    @PostMapping("/api/v1/inventario/historial-usuario")
    ResponseEntity<Map<String, Object>> crearHistorialUsuario(@RequestBody Map<String, Object> historialData);

    /**
     * Actualiza el historial de un usuario
     * @param historialData Datos del historial a actualizar
     * @return Respuesta de la operación
     */
    @PostMapping("/api/v1/inventario/actualizar-historial")
    ResponseEntity<Map<String, Object>> actualizarHistorialUsuario(@RequestBody Map<String, Object> historialData);

    /**
     * Obtiene el historial de un usuario
     * @param usuarioData Datos del usuario
     * @return Historial del usuario
     */
    @PostMapping("/api/v1/inventario/obtener-historial")
    ResponseEntity<Map<String, Object>> obtenerHistorialUsuario(@RequestBody Map<String, Object> usuarioData);
}
