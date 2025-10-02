package com.ampuero.msvc.referidos.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "msvc-auth", url = "localhost:8001")
public interface AuthClientRest {

    @GetMapping("/api/v1/auth/validate-token")
    Map<String, Object> validarToken(@RequestHeader("Authorization") String token);

    @GetMapping("/api/v1/auth/usuario/{id}/perfil")
    Map<String, Object> obtenerPerfilUsuario(@PathVariable Long id);

    @PostMapping("/api/v1/auth/usuario/crear")
    Map<String, Object> crearUsuario(@RequestBody Map<String, Object> usuarioData);

    @GetMapping("/api/v1/auth/usuario/email/{email}")
    Boolean verificarEmailExistente(@PathVariable String email);

    @GetMapping("/api/v1/auth/usuario/run/{run}")
    Boolean verificarRunExistente(@PathVariable String run);
}
