package com.ampuero.msvc.pagos.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "msvc-usuario", url = "localhost:8082")
public interface UsuarioClientRest {

    @GetMapping("/usuarios/{id}")
    ResponseEntity<Map<String, Object>> obtenerUsuario(@PathVariable Long id);

    @GetMapping("/usuarios/correo/{correo}")
    ResponseEntity<Map<String, Object>> obtenerUsuarioPorCorreo(@PathVariable String correo);

    @GetMapping("/usuarios/validar-pago")
    ResponseEntity<Map<String, Object>> validarUsuarioParaPago(@RequestParam Long usuarioId, @RequestParam Double monto);

    @GetMapping("/usuarios/{id}/limite-pago")
    ResponseEntity<Map<String, Object>> obtenerLimitePagoUsuario(@PathVariable Long id);

    @GetMapping("/usuarios/{id}/preferencias-pago")
    ResponseEntity<Map<String, Object>> obtenerPreferenciasPago(@PathVariable Long id);
}
