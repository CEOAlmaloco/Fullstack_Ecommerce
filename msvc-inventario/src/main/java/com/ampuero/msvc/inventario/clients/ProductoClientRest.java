package com.ampuero.msvc.inventario.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-productos", url = "localhost:8083")
public interface ProductoClientRest {

    @GetMapping("/productos/{id}")
    Object obtenerProductoPorId(@PathVariable Long id);

    @GetMapping("/productos/{id}/disponible")
    Boolean verificarProductoDisponible(@PathVariable Long id);
}
