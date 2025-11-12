package com.ampuero.msvc.usuario.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * Cliente Feign para comunicación con msvc-productos
 * Permite obtener información de productos relacionados con usuarios
 */
@FeignClient(name = "msvc-productos", url = "${MSVC_PRODUCTOS_URL:http://localhost:8003}")
public interface ProductoClientRest {

    /**
     * Obtiene productos recomendados para un usuario
     * @param usuarioId ID del usuario
     * @return Lista de productos recomendados
     */
    @GetMapping("/api/v1/productos/recomendados/{usuarioId}")
    ResponseEntity<Map<String, Object>> obtenerProductosRecomendados(@PathVariable Long usuarioId);

    /**
     * Obtiene productos favoritos de un usuario
     * @param usuarioId ID del usuario
     * @return Lista de productos favoritos
     */
    @GetMapping("/api/v1/productos/favoritos/{usuarioId}")
    ResponseEntity<Map<String, Object>> obtenerProductosFavoritos(@PathVariable Long usuarioId);

    /**
     * Obtiene historial de productos comprados por un usuario
     * @param usuarioId ID del usuario
     * @return Historial de compras
     */
    @GetMapping("/api/v1/productos/historial-compras/{usuarioId}")
    ResponseEntity<Map<String, Object>> obtenerHistorialCompras(@PathVariable Long usuarioId);
}
