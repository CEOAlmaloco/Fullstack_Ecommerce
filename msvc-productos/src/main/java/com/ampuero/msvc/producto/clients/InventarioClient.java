package com.ampuero.msvc.producto.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "msvc-inventario", url = "http://localhost:8004/api/v1")
public interface InventarioClient {

    @GetMapping("/inventario/producto/{productoId}")
    Map<String, Object> obtenerInventarioPorProducto(@PathVariable Long productoId);

    @GetMapping("/inventario/stock-critico")
    Object obtenerProductosStockCritico();

    @PostMapping("/inventario/{productoId}/reservar")
    Map<String, Object> reservarStock(@PathVariable Long productoId, @RequestParam Integer cantidad);

    @PostMapping("/inventario/{productoId}/liberar")
    Map<String, Object> liberarReserva(@PathVariable Long productoId, @RequestParam Integer cantidad);
}
