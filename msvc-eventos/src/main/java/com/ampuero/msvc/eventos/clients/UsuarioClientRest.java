package com.ampuero.msvc.eventos.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "msvc-usuario", url = "${MSVC_USUARIO_URL:http://localhost:8095}")
public interface UsuarioClientRest {

    @GetMapping("/usuarios/{id}")
    ResponseEntity<Map<String, Object>> obtenerUsuario(@PathVariable Long id);

    @GetMapping("/usuarios/correo/{correo}")
    ResponseEntity<Map<String, Object>> obtenerUsuarioPorCorreo(@PathVariable String correo);

    @GetMapping("/usuarios/{id}/edad")
    ResponseEntity<Map<String, Object>> obtenerEdadUsuario(@PathVariable Long id);

    @GetMapping("/usuarios/{id}/preferencias-eventos")
    ResponseEntity<Map<String, Object>> obtenerPreferenciasEventos(@PathVariable Long id);

    @GetMapping("/usuarios/validar-edad")
    ResponseEntity<Map<String, Object>> validarEdadUsuario(@RequestParam Long usuarioId, @RequestParam Integer edadMinima);
}
