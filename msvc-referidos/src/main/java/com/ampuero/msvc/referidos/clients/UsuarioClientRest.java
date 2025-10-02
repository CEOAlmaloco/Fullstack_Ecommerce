package com.ampuero.msvc.referidos.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "msvc-usuario", url = "localhost:8002")
public interface UsuarioClientRest {

    @GetMapping("/api/v1/usuarios/{id}")
    Map<String, Object> obtenerUsuarioPorId(@PathVariable Long id);

    @GetMapping("/api/v1/usuarios/email/{email}")
    Map<String, Object> obtenerUsuarioPorEmail(@PathVariable String email);

    @GetMapping("/api/v1/usuarios/run/{run}")
    Map<String, Object> obtenerUsuarioPorRun(@PathVariable String run);

    @PostMapping("/api/v1/usuarios/verificar")
    Boolean verificarUsuario(@RequestBody Map<String, String> datosUsuario);

    @PutMapping("/api/v1/usuarios/{id}/nivel")
    Map<String, Object> actualizarNivelUsuario(@PathVariable Long id, @RequestBody Map<String, String> nivel);
}
