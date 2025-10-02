package com.ampuero.msvc.referidos.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "msvc-inventario", url = "localhost:8004")
public interface InventarioClientRest {

    @GetMapping("/api/v1/inventario/producto/{idProducto}/stock")
    Map<String, Object> obtenerStockProducto(@PathVariable Long idProducto);

    @PostMapping("/api/v1/inventario/producto/{idProducto}/reservar")
    Map<String, Object> reservarProducto(@PathVariable Long idProducto, @RequestParam Integer cantidad);

    @PostMapping("/api/v1/inventario/producto/{idProducto}/liberar")
    Map<String, Object> liberarReservaProducto(@PathVariable Long idProducto, @RequestParam Integer cantidad);

    @GetMapping("/api/v1/inventario/producto/{idProducto}/disponible")
    Boolean verificarDisponibilidadProducto(@PathVariable Long idProducto, @RequestParam Integer cantidad);
}
