package com.ampuero.msvc.resenia.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "msvc-productos", url = "${MSVC_PRODUCTOS_URL:http://localhost:8003}")
public interface ProductoClientRest {

    @GetMapping("/productos/{id}")
    ResponseEntity<Map<String, Object>> obtenerProducto(@PathVariable Long id);

    @GetMapping("/productos/{id}/resenias")
    ResponseEntity<Map<String, Object>> obtenerReseniasProducto(@PathVariable Long id);
}
