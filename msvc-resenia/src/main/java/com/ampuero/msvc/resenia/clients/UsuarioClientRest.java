package com.ampuero.msvc.resenia.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "msvc-usuario", url = "localhost:8082")
public interface UsuarioClientRest {

    @GetMapping("/usuarios/{id}")
    ResponseEntity<Map<String, Object>> obtenerUsuario(@PathVariable Long id);

    @GetMapping("/usuarios/{id}/resenias")
    ResponseEntity<Map<String, Object>> obtenerReseniasUsuario(@PathVariable Long id);
}
