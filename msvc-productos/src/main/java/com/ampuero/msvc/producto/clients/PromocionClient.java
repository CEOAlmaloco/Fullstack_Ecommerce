package com.ampuero.msvc.producto.clients;

import com.ampuero.msvc.producto.dtos.PromocionResumenDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "msvc-promociones", url = "${MSVC_PROMOCIONES_API_URL:http://localhost:8091/api/v1}")
public interface PromocionClient {

    @GetMapping("/promociones/activas")
    List<PromocionResumenDTO> obtenerPromocionesActivas();
}
