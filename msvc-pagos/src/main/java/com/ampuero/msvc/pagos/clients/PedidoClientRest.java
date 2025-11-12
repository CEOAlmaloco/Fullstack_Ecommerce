package com.ampuero.msvc.pagos.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "msvc-pedido", url = "${MSVC_PEDIDO_URL:http://localhost:8085}")
public interface PedidoClientRest {

    @GetMapping("/pedidos/{id}")
    ResponseEntity<Map<String, Object>> obtenerPedido(@PathVariable Long id);

    @PutMapping("/pedidos/{id}/estado")
    ResponseEntity<Map<String, Object>> actualizarEstadoPedido(@PathVariable Long id, @RequestParam String estado);

    @PutMapping("/pedidos/{id}/pago")
    ResponseEntity<Map<String, Object>> marcarPedidoComoPagado(@PathVariable Long id, @RequestParam String numeroTransaccion);

    @GetMapping("/pedidos/{id}/total")
    ResponseEntity<Map<String, Object>> obtenerTotalPedido(@PathVariable Long id);

    @GetMapping("/pedidos/{id}/items")
    ResponseEntity<Map<String, Object>> obtenerItemsPedido(@PathVariable Long id);
}
