package com.ampuero.msvc.notificaciones.clients;

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

    @GetMapping("/usuarios/{id}/preferencias-notificacion")
    ResponseEntity<Map<String, Object>> obtenerPreferenciasNotificacion(@PathVariable Long id);

    @GetMapping("/usuarios/{id}/datos-contacto")
    ResponseEntity<Map<String, Object>> obtenerDatosContacto(@PathVariable Long id);

    @GetMapping("/usuarios/{id}/preferencias-canal")
    ResponseEntity<Map<String, Object>> obtenerPreferenciasCanal(@PathVariable Long id);
}
