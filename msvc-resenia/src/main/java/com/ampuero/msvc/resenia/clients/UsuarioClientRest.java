package com.ampuero.msvc.resenia.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "msvc-usuario", url = "${MSVC_USUARIO_URL:http://localhost:8095}")
public interface UsuarioClientRest {

    @GetMapping("/usuarios/{id}")
    ResponseEntity<Map<String, Object>> obtenerUsuario(@PathVariable Long id);

    @GetMapping("/usuarios/{id}/resenias")
    ResponseEntity<Map<String, Object>> obtenerReseniasUsuario(@PathVariable Long id);
}
