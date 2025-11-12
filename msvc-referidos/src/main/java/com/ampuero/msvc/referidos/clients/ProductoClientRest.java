package com.ampuero.msvc.referidos.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "msvc-productos", url = "${MSVC_PRODUCTOS_URL:http://localhost:8003}")
public interface ProductoClientRest {

    @GetMapping("/api/v1/productos/{id}")
    Map<String, Object> obtenerProductoPorId(@PathVariable Long id);

    @GetMapping("/api/v1/productos/categoria/{categoria}")
    List<Map<String, Object>> obtenerProductosPorCategoria(@PathVariable String categoria);

    @GetMapping("/api/v1/productos/puntos/{puntos}")
    List<Map<String, Object>> obtenerProductosCanjeablesPorPuntos(@PathVariable Integer puntos);

    @PostMapping("/api/v1/productos/canje")
    Map<String, Object> procesarCanjeProducto(@RequestBody Map<String, Object> canjeData);

    @GetMapping("/api/v1/productos/descuento/{nivel}")
    Map<String, Object> obtenerDescuentoPorNivel(@PathVariable String nivel);
}
