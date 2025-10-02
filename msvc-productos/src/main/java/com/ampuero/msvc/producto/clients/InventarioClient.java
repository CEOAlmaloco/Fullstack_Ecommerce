package com.ampuero.msvc.producto.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-inventario", url = "localhost:8084")
public interface InventarioClient {

    @GetMapping("/inventario/producto/{productoId}")
    Object obtenerInventarioPorProducto(@PathVariable Long productoId);

    @GetMapping("/inventario/stock-critico")
    Object obtenerProductosStockCritico();
}
