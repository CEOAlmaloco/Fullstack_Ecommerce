package com.ampuero.msvc.pedido.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "msvc-pagos", url = "localhost:8011")
public interface PagoClientRest {

    @PostMapping("/pagos")
    ResponseEntity<Map<String, Object>> crearPago(@RequestBody Map<String, Object> pagoData);

    @PostMapping("/pagos/{id}/procesar")
    ResponseEntity<Map<String, Object>> procesarPago(@PathVariable Long id);

    @PostMapping("/pagos/{id}/confirmar")
    ResponseEntity<Map<String, Object>> confirmarPago(@PathVariable Long id);
}
