package com.ampuero.msvc.inventario.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-productos", url = "${MSVC_PRODUCTOS_API_URL:http://localhost:8003/api/v1}")
public interface ProductoClientRest {

    @GetMapping("/productos/{id}")
    Object obtenerProductoPorId(@PathVariable Long id);

    @GetMapping("/productos/{id}/disponible")
    Boolean verificarProductoDisponible(@PathVariable Long id);
}
