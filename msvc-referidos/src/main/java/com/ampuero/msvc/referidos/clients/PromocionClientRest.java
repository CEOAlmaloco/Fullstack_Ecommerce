package com.ampuero.msvc.referidos.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "msvc-promociones", url = "${MSVC_PROMOCIONES_URL:http://localhost:8091}")
public interface PromocionClientRest {

    @GetMapping("/api/v1/promociones/activas")
    List<Map<String, Object>> obtenerPromocionesActivas();

    @GetMapping("/api/v1/promociones/nivel/{nivel}")
    List<Map<String, Object>> obtenerPromocionesPorNivel(@PathVariable String nivel);

    @GetMapping("/api/v1/promociones/puntos/{puntos}")
    List<Map<String, Object>> obtenerPromocionesPorPuntos(@PathVariable Integer puntos);

    @PostMapping("/api/v1/promociones/aplicar")
    Map<String, Object> aplicarPromocion(@RequestBody Map<String, Object> promocionData);

    @GetMapping("/api/v1/promociones/usuario/{idUsuario}/disponibles")
    List<Map<String, Object>> obtenerPromocionesDisponiblesUsuario(@PathVariable Long idUsuario);
}
