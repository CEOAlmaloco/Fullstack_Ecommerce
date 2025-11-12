package com.ampuero.msvc.usuario.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * Cliente Feign para comunicación con msvc-referidos
 * Permite gestionar el sistema de referidos y gamificación
 */
@FeignClient(name = "msvc-referidos", url = "${MSVC_REFERIDOS_URL:http://localhost:8005}")
public interface ReferidosClientRest {

    /**
     * Obtiene los referidos de un usuario
     * @param usuarioId ID del usuario
     * @return Lista de referidos como Map
     */
    @GetMapping("/referidos/usuario/{usuarioId}/referidos")
    List<Map<String, Object>> getReferidosPorUsuario(@PathVariable Long usuarioId);

    /**
     * Registra una nueva referencia
     * @param referenciaData Datos de la referencia
     * @return Respuesta de la operación
     */
    @PostMapping("/api/v1/referidos/registrar")
    ResponseEntity<Map<String, Object>> registrarReferencia(@RequestBody Map<String, Object> referenciaData);

    /**
     * Agrega puntos LevelUp a un usuario
     * @param puntosData Datos de los puntos a agregar
     * @return Respuesta de la operación
     */
    @PostMapping("/api/v1/referidos/agregar-puntos")
    ResponseEntity<Map<String, Object>> agregarPuntosLevelUp(@RequestBody Map<String, Object> puntosData);

    /**
     * Actualiza el nivel de un usuario
     * @param nivelData Datos del nivel a actualizar
     * @return Respuesta de la operación
     */
    @PostMapping("/api/v1/referidos/actualizar-nivel")
    ResponseEntity<Map<String, Object>> actualizarNivelUsuario(@RequestBody Map<String, Object> nivelData);

    /**
     * Obtiene estadísticas de referidos de un usuario
     * @param estadisticasData Datos para obtener estadísticas
     * @return Estadísticas del usuario
     */
    @PostMapping("/api/v1/referidos/estadisticas")
    ResponseEntity<Map<String, Object>> obtenerEstadisticasReferidos(@RequestBody Map<String, Object> estadisticasData);
}
