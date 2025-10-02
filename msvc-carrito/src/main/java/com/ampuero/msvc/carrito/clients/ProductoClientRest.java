package com.ampuero.msvc.carrito.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "msvc-productos", url = "localhost:8083")
public interface ProductoClientRest {

    @GetMapping("/productos/{id}")
    ResponseEntity<Map<String, Object>> obtenerProducto(@PathVariable Long id);

    @GetMapping("/productos/{id}/precio")
    ResponseEntity<Map<String, Object>> obtenerPrecioProducto(@PathVariable Long id);

    @GetMapping("/productos/{id}/disponible")
    ResponseEntity<Map<String, Object>> verificarDisponibilidad(@PathVariable Long id);
}
