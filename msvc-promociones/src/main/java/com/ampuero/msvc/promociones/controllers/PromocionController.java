package com.ampuero.msvc.promociones.controllers;

import com.ampuero.msvc.promociones.dtos.PromocionCreationDTO;
import com.ampuero.msvc.promociones.dtos.PromocionEstadoDTO;
import com.ampuero.msvc.promociones.dtos.PromocionResponseDTO;
import com.ampuero.msvc.promociones.models.Promocion;
import com.ampuero.msvc.promociones.services.PromocionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/promociones")
@CrossOrigin(origins = "*")
public class PromocionController {

    @Autowired
    private PromocionService promocionService;

    /**
     * Crear nueva promoción
     * POST: /promociones
     */
    @PostMapping
    public ResponseEntity<PromocionResponseDTO> crearPromocion(@Valid @RequestBody PromocionCreationDTO promocionDetails) {
        Promocion promocion = promocionService.crearPromocion(promocionDetails);
        PromocionResponseDTO response = convertirAResponseDTO(promocion);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Obtener todas las promociones
     * GET: /promociones
     */
    @GetMapping
    public ResponseEntity<List<PromocionResponseDTO>> traerTodas() {
        List<Promocion> promociones = promocionService.traerTodos();
        List<PromocionResponseDTO> response = promociones.stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener promociones activas
     * GET: /promociones/activas
     */
    @GetMapping("/activas")
    public ResponseEntity<List<PromocionResponseDTO>> traerPromocionesActivas() {
        List<Promocion> promociones = promocionService.traerPromocionesActivas();
        List<PromocionResponseDTO> response = promociones.stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener promociones aplicables para usuarios Duoc
     * GET: /promociones/duoc
     */
    @GetMapping("/duoc")
    public ResponseEntity<List<PromocionResponseDTO>> traerPromocionesDuoc() {
        List<Promocion> promociones = promocionService.traerPromocionesDuocActivas();
        List<PromocionResponseDTO> response = promociones.stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener promociones por categoría
     * GET: /promociones/categoria/{categoria}
     */
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<PromocionResponseDTO>> traerPromocionesPorCategoria(@PathVariable String categoria) {
        List<Promocion> promociones = promocionService.traerPromocionesPorCategoria(categoria);
        List<PromocionResponseDTO> response = promociones.stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener promoción por ID
     * GET: /promociones/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<PromocionResponseDTO> traerPorId(@PathVariable Long id) {
        Promocion promocion = promocionService.traerPorId(id);
        PromocionResponseDTO response = convertirAResponseDTO(promocion);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener promoción por código
     * GET: /promociones/codigo/{codigo}
     */
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<PromocionResponseDTO> traerPorCodigo(@PathVariable String codigo) {
        Promocion promocion = promocionService.traerPorCodigo(codigo);
        PromocionResponseDTO response = convertirAResponseDTO(promocion);
        return ResponseEntity.ok(response);
    }

    /**
     * Validar promoción
     * POST: /promociones/validar
     */
    @PostMapping("/validar")
    public ResponseEntity<Boolean> validarPromocion(@RequestParam String codigo, 
                                                   @RequestParam Double montoTotal, 
                                                   @RequestParam String correoUsuario) {
        boolean esValida = promocionService.validarPromocion(codigo, montoTotal, correoUsuario);
        return ResponseEntity.ok(esValida);
    }

    /**
     * Aplicar promoción
     * POST: /promociones/aplicar
     */
    @PostMapping("/aplicar")
    public ResponseEntity<PromocionResponseDTO> aplicarPromocion(@RequestParam String codigo, 
                                                                @RequestParam Double montoTotal, 
                                                                @RequestParam String correoUsuario) {
        Promocion promocion = promocionService.aplicarPromocion(codigo, montoTotal, correoUsuario);
        PromocionResponseDTO response = convertirAResponseDTO(promocion);
        return ResponseEntity.ok(response);
    }

    /**
     * Actualizar promoción
     * PUT: /promociones/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<PromocionResponseDTO> actualizarPromocion(@PathVariable Long id, 
                                                                   @Valid @RequestBody Promocion promocionDetails) {
        Promocion promocion = promocionService.actualizarPromocion(id, promocionDetails);
        PromocionResponseDTO response = convertirAResponseDTO(promocion);
        return ResponseEntity.ok(response);
    }

    /**
     * Actualizar estado de promoción
     * PUT: /promociones/{id}/estado
     */
    @PutMapping("/{id}/estado")
    public ResponseEntity<PromocionResponseDTO> actualizarEstadoPromocion(@PathVariable Long id, 
                                                                         @Valid @RequestBody PromocionEstadoDTO estadoDetails) {
        Promocion promocion = promocionService.actualizarEstadoPromocion(id, estadoDetails);
        PromocionResponseDTO response = convertirAResponseDTO(promocion);
        return ResponseEntity.ok(response);
    }

    /**
     * Eliminar promoción
     * DELETE: /promociones/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPromocion(@PathVariable Long id) {
        promocionService.eliminarPromocion(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Convierte una entidad Promocion a PromocionResponseDTO
     */
    private PromocionResponseDTO convertirAResponseDTO(Promocion promocion) {
        PromocionResponseDTO response = new PromocionResponseDTO();
        response.setIdPromocion(promocion.getIdPromocion());
        response.setCodigoPromocion(promocion.getCodigoPromocion());
        response.setNombrePromocion(promocion.getNombrePromocion());
        response.setDescripcionPromocion(promocion.getDescripcionPromocion());
        response.setTipoDescuento(promocion.getTipoDescuento());
        response.setValorDescuento(promocion.getValorDescuento());
        response.setMontoMinimo(promocion.getMontoMinimo());
        response.setMontoMaximoDescuento(promocion.getMontoMaximoDescuento());
        response.setFechaInicio(promocion.getFechaInicio());
        response.setFechaFin(promocion.getFechaFin());
        response.setUsosMaximos(promocion.getUsosMaximos());
        response.setUsosActuales(promocion.getUsosActuales());
        response.setUsosPorUsuario(promocion.getUsosPorUsuario());
        response.setActivo(promocion.getActivo());
        response.setAplicableDuoc(promocion.getAplicableDuoc());
        response.setCategoriaAplicable(promocion.getCategoriaAplicable());
        return response;
    }
}
