package com.ampuero.msvc.pedido.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "msvc-inventario", url = "localhost:8004")
public interface InventarioClientRest {

    @PostMapping("/inventario/reservar-stock")
    ResponseEntity<Map<String, Object>> reservarStock(@RequestBody Map<String, Object> reservaData);

    @PostMapping("/inventario/confirmar-reserva")
    ResponseEntity<Map<String, Object>> confirmarReserva(@RequestBody Map<String, Object> confirmacionData);

    @PostMapping("/inventario/cancelar-reserva")
    ResponseEntity<Map<String, Object>> cancelarReserva(@RequestBody Map<String, Object> cancelacionData);
}
