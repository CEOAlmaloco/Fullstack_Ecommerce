package com.ampuero.msvc.contenido.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "msvc-usuario", url = "${MSVC_USUARIO_URL:http://localhost:8095}")
public interface UsuarioClientRest {

    @GetMapping("/usuarios/{id}")
    ResponseEntity<Map<String, Object>> obtenerUsuario(@PathVariable Long id);

    @GetMapping("/usuarios/correo/{correo}")
    ResponseEntity<Map<String, Object>> obtenerUsuarioPorCorreo(@PathVariable String correo);

    @GetMapping("/usuarios/{id}/permisos-contenido")
    ResponseEntity<Map<String, Object>> obtenerPermisosContenido(@PathVariable Long id);

    @GetMapping("/usuarios/{id}/suscripcion-premium")
    ResponseEntity<Map<String, Object>> verificarSuscripcionPremium(@PathVariable Long id);
}
