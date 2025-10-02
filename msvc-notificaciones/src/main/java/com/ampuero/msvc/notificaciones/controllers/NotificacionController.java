package com.ampuero.msvc.notificaciones.controllers;

import com.ampuero.msvc.notificaciones.dtos.NotificacionCreationDTO;
import com.ampuero.msvc.notificaciones.dtos.NotificacionEstadoDTO;
import com.ampuero.msvc.notificaciones.dtos.NotificacionResponseDTO;
import com.ampuero.msvc.notificaciones.dtos.PlantillaCreationDTO;
import com.ampuero.msvc.notificaciones.dtos.PlantillaResponseDTO;
import com.ampuero.msvc.notificaciones.models.Notificacion;
import com.ampuero.msvc.notificaciones.models.PlantillaNotificacion;
import com.ampuero.msvc.notificaciones.services.NotificacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notificaciones")
@CrossOrigin(origins = "*")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    // ========== ENDPOINTS DE NOTIFICACIONES ==========

    /**
     * Crear nueva notificación
     * POST: /notificaciones
     */
    @PostMapping
    public ResponseEntity<NotificacionResponseDTO> crearNotificacion(@Valid @RequestBody NotificacionCreationDTO notificacionDetails) {
        Notificacion notificacion = notificacionService.crearNotificacion(notificacionDetails);
        NotificacionResponseDTO response = convertirNotificacionAResponseDTO(notificacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Obtener todas las notificaciones
     * GET: /notificaciones
     */
    @GetMapping
    public ResponseEntity<List<NotificacionResponseDTO>> traerTodasNotificaciones() {
        List<Notificacion> notificaciones = notificacionService.traerTodasNotificaciones();
        List<NotificacionResponseDTO> response = notificaciones.stream()
                .map(this::convertirNotificacionAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener notificaciones por usuario
     * GET: /notificaciones/usuario/{idUsuario}
     */
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<NotificacionResponseDTO>> traerNotificacionesPorUsuario(@PathVariable Long idUsuario) {
        List<Notificacion> notificaciones = notificacionService.traerNotificacionesPorUsuario(idUsuario);
        List<NotificacionResponseDTO> response = notificaciones.stream()
                .map(this::convertirNotificacionAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener notificaciones por estado
     * GET: /notificaciones/estado/{estado}
     */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<NotificacionResponseDTO>> traerNotificacionesPorEstado(@PathVariable String estado) {
        List<Notificacion> notificaciones = notificacionService.traerNotificacionesPorEstado(estado);
        List<NotificacionResponseDTO> response = notificaciones.stream()
                .map(this::convertirNotificacionAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener notificaciones por tipo
     * GET: /notificaciones/tipo/{tipo}
     */
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<NotificacionResponseDTO>> traerNotificacionesPorTipo(@PathVariable String tipo) {
        List<Notificacion> notificaciones = notificacionService.traerNotificacionesPorTipo(tipo);
        List<NotificacionResponseDTO> response = notificaciones.stream()
                .map(this::convertirNotificacionAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener notificaciones por canal
     * GET: /notificaciones/canal/{canal}
     */
    @GetMapping("/canal/{canal}")
    public ResponseEntity<List<NotificacionResponseDTO>> traerNotificacionesPorCanal(@PathVariable String canal) {
        List<Notificacion> notificaciones = notificacionService.traerNotificacionesPorCanal(canal);
        List<NotificacionResponseDTO> response = notificaciones.stream()
                .map(this::convertirNotificacionAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener notificaciones por destinatario
     * GET: /notificaciones/destinatario/{destinatario}
     */
    @GetMapping("/destinatario/{destinatario}")
    public ResponseEntity<List<NotificacionResponseDTO>> traerNotificacionesPorDestinatario(@PathVariable String destinatario) {
        List<Notificacion> notificaciones = notificacionService.traerNotificacionesPorDestinatario(destinatario);
        List<NotificacionResponseDTO> response = notificaciones.stream()
                .map(this::convertirNotificacionAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener notificación por ID
     * GET: /notificaciones/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<NotificacionResponseDTO> traerNotificacionPorId(@PathVariable Long id) {
        Notificacion notificacion = notificacionService.traerNotificacionPorId(id);
        NotificacionResponseDTO response = convertirNotificacionAResponseDTO(notificacion);
        return ResponseEntity.ok(response);
    }

    /**
     * Enviar notificación
     * POST: /notificaciones/{id}/enviar
     */
    @PostMapping("/{id}/enviar")
    public ResponseEntity<NotificacionResponseDTO> enviarNotificacion(@PathVariable Long id) {
        Notificacion notificacion = notificacionService.enviarNotificacion(id);
        NotificacionResponseDTO response = convertirNotificacionAResponseDTO(notificacion);
        return ResponseEntity.ok(response);
    }

    /**
     * Marcar como entregada
     * PUT: /notificaciones/{id}/entregada
     */
    @PutMapping("/{id}/entregada")
    public ResponseEntity<NotificacionResponseDTO> marcarComoEntregada(@PathVariable Long id) {
        Notificacion notificacion = notificacionService.marcarComoEntregada(id);
        NotificacionResponseDTO response = convertirNotificacionAResponseDTO(notificacion);
        return ResponseEntity.ok(response);
    }

    /**
     * Marcar como abierta
     * PUT: /notificaciones/{id}/abierta
     */
    @PutMapping("/{id}/abierta")
    public ResponseEntity<NotificacionResponseDTO> marcarComoAbierta(@PathVariable Long id) {
        Notificacion notificacion = notificacionService.marcarComoAbierta(id);
        NotificacionResponseDTO response = convertirNotificacionAResponseDTO(notificacion);
        return ResponseEntity.ok(response);
    }

    /**
     * Reintentar envío
     * POST: /notificaciones/{id}/reintentar
     */
    @PostMapping("/{id}/reintentar")
    public ResponseEntity<NotificacionResponseDTO> reintentarEnvio(@PathVariable Long id) {
        Notificacion notificacion = notificacionService.reintentarEnvio(id);
        NotificacionResponseDTO response = convertirNotificacionAResponseDTO(notificacion);
        return ResponseEntity.ok(response);
    }

    /**
     * Actualizar notificación
     * PUT: /notificaciones/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<NotificacionResponseDTO> actualizarNotificacion(@PathVariable Long id, 
                                                                          @Valid @RequestBody Notificacion notificacionDetails) {
        Notificacion notificacion = notificacionService.actualizarNotificacion(id, notificacionDetails);
        NotificacionResponseDTO response = convertirNotificacionAResponseDTO(notificacion);
        return ResponseEntity.ok(response);
    }

    /**
     * Actualizar estado de notificación
     * PUT: /notificaciones/{id}/estado
     */
    @PutMapping("/{id}/estado")
    public ResponseEntity<NotificacionResponseDTO> actualizarEstadoNotificacion(@PathVariable Long id, 
                                                                                @Valid @RequestBody NotificacionEstadoDTO estadoDetails) {
        Notificacion notificacion = notificacionService.actualizarEstadoNotificacion(id, estadoDetails);
        NotificacionResponseDTO response = convertirNotificacionAResponseDTO(notificacion);
        return ResponseEntity.ok(response);
    }

    /**
     * Eliminar notificación
     * DELETE: /notificaciones/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarNotificacion(@PathVariable Long id) {
        notificacionService.eliminarNotificacion(id);
        return ResponseEntity.noContent().build();
    }

    // ========== ENDPOINTS DE PLANTILLAS ==========

    /**
     * Crear nueva plantilla
     * POST: /notificaciones/plantillas
     */
    @PostMapping("/plantillas")
    public ResponseEntity<PlantillaResponseDTO> crearPlantilla(@Valid @RequestBody PlantillaCreationDTO plantillaDetails) {
        PlantillaNotificacion plantilla = notificacionService.crearPlantilla(plantillaDetails);
        PlantillaResponseDTO response = convertirPlantillaAResponseDTO(plantilla);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Obtener todas las plantillas
     * GET: /notificaciones/plantillas
     */
    @GetMapping("/plantillas")
    public ResponseEntity<List<PlantillaResponseDTO>> traerTodasPlantillas() {
        List<PlantillaNotificacion> plantillas = notificacionService.traerTodasPlantillas();
        List<PlantillaResponseDTO> response = plantillas.stream()
                .map(this::convertirPlantillaAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener plantillas por tipo
     * GET: /notificaciones/plantillas/tipo/{tipo}
     */
    @GetMapping("/plantillas/tipo/{tipo}")
    public ResponseEntity<List<PlantillaResponseDTO>> traerPlantillasPorTipo(@PathVariable String tipo) {
        List<PlantillaNotificacion> plantillas = notificacionService.traerPlantillasPorTipo(tipo);
        List<PlantillaResponseDTO> response = plantillas.stream()
                .map(this::convertirPlantillaAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener plantillas activas
     * GET: /notificaciones/plantillas/activas
     */
    @GetMapping("/plantillas/activas")
    public ResponseEntity<List<PlantillaResponseDTO>> traerPlantillasActivas() {
        List<PlantillaNotificacion> plantillas = notificacionService.traerPlantillasActivas();
        List<PlantillaResponseDTO> response = plantillas.stream()
                .map(this::convertirPlantillaAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener plantilla por ID
     * GET: /notificaciones/plantillas/{id}
     */
    @GetMapping("/plantillas/{id}")
    public ResponseEntity<PlantillaResponseDTO> traerPlantillaPorId(@PathVariable Long id) {
        PlantillaNotificacion plantilla = notificacionService.traerPlantillaPorId(id);
        PlantillaResponseDTO response = convertirPlantillaAResponseDTO(plantilla);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener plantilla por código
     * GET: /notificaciones/plantillas/codigo/{codigo}
     */
    @GetMapping("/plantillas/codigo/{codigo}")
    public ResponseEntity<PlantillaResponseDTO> traerPlantillaPorCodigo(@PathVariable String codigo) {
        PlantillaNotificacion plantilla = notificacionService.traerPlantillaPorCodigo(codigo);
        PlantillaResponseDTO response = convertirPlantillaAResponseDTO(plantilla);
        return ResponseEntity.ok(response);
    }

    /**
     * Actualizar plantilla
     * PUT: /notificaciones/plantillas/{id}
     */
    @PutMapping("/plantillas/{id}")
    public ResponseEntity<PlantillaResponseDTO> actualizarPlantilla(@PathVariable Long id, 
                                                                    @Valid @RequestBody PlantillaNotificacion plantillaDetails) {
        PlantillaNotificacion plantilla = notificacionService.actualizarPlantilla(id, plantillaDetails);
        PlantillaResponseDTO response = convertirPlantillaAResponseDTO(plantilla);
        return ResponseEntity.ok(response);
    }

    /**
     * Eliminar plantilla
     * DELETE: /notificaciones/plantillas/{id}
     */
    @DeleteMapping("/plantillas/{id}")
    public ResponseEntity<Void> eliminarPlantilla(@PathVariable Long id) {
        notificacionService.eliminarPlantilla(id);
        return ResponseEntity.noContent().build();
    }

    // ========== ENDPOINTS DE ENVÍO MASIVO ==========

    /**
     * Enviar notificación masiva
     * POST: /notificaciones/masivo
     */
    @PostMapping("/masivo")
    public ResponseEntity<List<NotificacionResponseDTO>> enviarNotificacionMasiva(@RequestParam List<Long> idsUsuarios,
                                                                                  @RequestParam String tipoNotificacion,
                                                                                  @RequestParam String canalNotificacion,
                                                                                  @RequestParam String asunto,
                                                                                  @RequestParam String contenido) {
        List<Notificacion> notificaciones = notificacionService.enviarNotificacionMasiva(idsUsuarios, tipoNotificacion, canalNotificacion, asunto, contenido);
        List<NotificacionResponseDTO> response = notificaciones.stream()
                .map(this::convertirNotificacionAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Enviar notificación masiva por plantilla
     * POST: /notificaciones/masivo/plantilla
     */
    @PostMapping("/masivo/plantilla")
    public ResponseEntity<List<NotificacionResponseDTO>> enviarNotificacionPorPlantilla(@RequestParam List<Long> idsUsuarios,
                                                                                         @RequestParam String codigoPlantilla,
                                                                                         @RequestParam(required = false) String metadata) {
        List<Notificacion> notificaciones = notificacionService.enviarNotificacionPorPlantilla(idsUsuarios, codigoPlantilla, metadata);
        List<NotificacionResponseDTO> response = notificaciones.stream()
                .map(this::convertirNotificacionAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    // ========== MÉTODOS DE CONVERSIÓN ==========

    /**
     * Convierte una entidad Notificacion a NotificacionResponseDTO
     */
    private NotificacionResponseDTO convertirNotificacionAResponseDTO(Notificacion notificacion) {
        NotificacionResponseDTO response = new NotificacionResponseDTO();
        response.setIdNotificacion(notificacion.getIdNotificacion());
        response.setIdUsuario(notificacion.getIdUsuario());
        response.setTipoNotificacion(notificacion.getTipoNotificacion());
        response.setCanalNotificacion(notificacion.getCanalNotificacion());
        response.setAsuntoNotificacion(notificacion.getAsuntoNotificacion());
        response.setContenidoNotificacion(notificacion.getContenidoNotificacion());
        response.setPlantillaNotificacion(notificacion.getPlantillaNotificacion());
        response.setDestinatarioNotificacion(notificacion.getDestinatarioNotificacion());
        response.setFechaCreacion(notificacion.getFechaCreacion());
        response.setFechaProgramada(notificacion.getFechaProgramada());
        response.setFechaEnvio(notificacion.getFechaEnvio());
        response.setFechaEntrega(notificacion.getFechaEntrega());
        response.setFechaApertura(notificacion.getFechaApertura());
        response.setEstadoNotificacion(notificacion.getEstadoNotificacion());
        response.setIntentosEnvio(notificacion.getIntentosEnvio());
        response.setMaxIntentos(notificacion.getMaxIntentos());
        response.setErrorMensaje(notificacion.getErrorMensaje());
        response.setMetadataNotificacion(notificacion.getMetadataNotificacion());
        response.setPrioridadNotificacion(notificacion.getPrioridadNotificacion());
        response.setActivo(notificacion.getActivo());
        return response;
    }

    /**
     * Convierte una entidad PlantillaNotificacion a PlantillaResponseDTO
     */
    private PlantillaResponseDTO convertirPlantillaAResponseDTO(PlantillaNotificacion plantilla) {
        PlantillaResponseDTO response = new PlantillaResponseDTO();
        response.setIdPlantilla(plantilla.getIdPlantilla());
        response.setCodigoPlantilla(plantilla.getCodigoPlantilla());
        response.setNombrePlantilla(plantilla.getNombrePlantilla());
        response.setDescripcionPlantilla(plantilla.getDescripcionPlantilla());
        response.setTipoPlantilla(plantilla.getTipoPlantilla());
        response.setAsuntoPlantilla(plantilla.getAsuntoPlantilla());
        response.setContenidoPlantilla(plantilla.getContenidoPlantilla());
        response.setVariablesPlantilla(plantilla.getVariablesPlantilla());
        response.setFechaCreacion(plantilla.getFechaCreacion());
        response.setFechaActualizacion(plantilla.getFechaActualizacion());
        response.setVersionPlantilla(plantilla.getVersionPlantilla());
        response.setActivo(plantilla.getActivo());
        return response;
    }
}
