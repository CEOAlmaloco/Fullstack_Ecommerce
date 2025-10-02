package com.ampuero.msvc.carrito.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "msvc-inventario", url = "localhost:8084")
public interface InventarioClientRest {

    @GetMapping("/inventario/producto/{productoId}")
    ResponseEntity<Map<String, Object>> obtenerStockProducto(@PathVariable Long productoId);

    @GetMapping("/inventario/verificar-stock")
    ResponseEntity<Map<String, Object>> verificarStock(@RequestParam Long productoId, @RequestParam Integer cantidad);
}
