package com.ampuero.msvc.pedido.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "msvc-carrito", url = "localhost:8087")
public interface CarritoClientRest {

    @GetMapping("/carrito/{idUsuario}")
    ResponseEntity<Map<String, Object>> obtenerCarritoUsuario(@PathVariable Long idUsuario);

    @PostMapping("/carrito/{idUsuario}/convertir-pedido")
    ResponseEntity<Map<String, Object>> convertirCarritoAPedido(@PathVariable Long idUsuario, @RequestBody Map<String, Object> datosPedido);

    @PostMapping("/carrito/{idUsuario}/limpiar")
    ResponseEntity<Map<String, Object>> limpiarCarrito(@PathVariable Long idUsuario);
}
