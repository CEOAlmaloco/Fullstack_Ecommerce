package com.ampuero.msvc.producto.clients;

import com.ampuero.msvc.producto.dtos.ReseniaResumenDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "msvc-resenia", url = "${MSVC_RESENIA_API_URL:http://localhost:8010/api/v1}")
public interface ReseniaClient {

    @GetMapping("/resenias")
    List<ReseniaResumenDTO> obtenerResenias();
}
