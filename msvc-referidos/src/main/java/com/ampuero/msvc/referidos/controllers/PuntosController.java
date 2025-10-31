package com.ampuero.msvc.referidos.controllers;

import com.ampuero.msvc.referidos.dtos.*;
import com.ampuero.msvc.referidos.entities.*;
import com.ampuero.msvc.referidos.services.PuntosService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/puntos")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174", "http://localhost:3000"})
public class PuntosController {

    @Autowired
    private PuntosService puntosService;

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<PuntosUsuarioResponseDTO> obtenerPuntosUsuario(@PathVariable Long idUsuario) {
        PuntosUsuario puntosUsuario = puntosService.obtenerPuntosUsuario(idUsuario);
        PuntosUsuarioResponseDTO response = convertirPuntosUsuarioAResponseDTO(puntosUsuario);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/usuario/{idUsuario}/codigo")
    public ResponseEntity<Map<String, String>> obtenerCodigoReferido(@PathVariable Long idUsuario) {
        String codigo = puntosService.obtenerCodigoReferido(idUsuario);
        return ResponseEntity.ok(Map.of("codigoReferido", codigo));
    }

    @PostMapping("/usuario/{idUsuario}/evento")
    public ResponseEntity<TransaccionPuntosResponseDTO> otorgarPuntosPorEvento(
            @PathVariable Long idUsuario,
            @Valid @RequestBody EventoPuntosCreationDTO eventoDTO) {
        TransaccionPuntos transaccion = puntosService.otorgarPuntosPorEvento(idUsuario, eventoDTO);
        TransaccionPuntosResponseDTO response = convertirTransaccionAResponseDTO(transaccion);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/usuario/{idUsuario}/inicio-sesion")
    public ResponseEntity<TransaccionPuntosResponseDTO> otorgarPuntosInicioSesion(@PathVariable Long idUsuario) {
        TransaccionPuntos transaccion = puntosService.otorgarPuntosPorTipo(
                idUsuario, 
                EventoPuntos.TipoEvento.INICIO_SESION, 
                10, 
                "Puntos por inicio de sesión diario"
        );
        TransaccionPuntosResponseDTO response = convertirTransaccionAResponseDTO(transaccion);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/usuario/{idUsuario}/canje")
    public ResponseEntity<TransaccionPuntosResponseDTO> canjearPuntos(
            @PathVariable Long idUsuario,
            @Valid @RequestBody CanjePuntosDTO canjeDTO) {
        TransaccionPuntos transaccion = puntosService.canjearPuntos(idUsuario, canjeDTO);
        TransaccionPuntosResponseDTO response = convertirTransaccionAResponseDTO(transaccion);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/usuario/{idUsuario}/canje-codigo")
    public ResponseEntity<TransaccionPuntosResponseDTO> canjearCodigoEvento(
            @PathVariable Long idUsuario,
            @Valid @RequestBody CanjeCodigoEventoDTO canjeDTO) {
        TransaccionPuntos transaccion = puntosService.canjearCodigoEvento(idUsuario, canjeDTO);
        TransaccionPuntosResponseDTO response = convertirTransaccionAResponseDTO(transaccion);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/usuario/{idUsuario}/historial")
    public ResponseEntity<List<TransaccionPuntosResponseDTO>> obtenerHistorialPuntos(@PathVariable Long idUsuario) {
        List<TransaccionPuntos> transacciones = puntosService.obtenerHistorialPuntos(idUsuario);
        List<TransaccionPuntosResponseDTO> response = transacciones.stream()
                .map(this::convertirTransaccionAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    private PuntosUsuarioResponseDTO convertirPuntosUsuarioAResponseDTO(PuntosUsuario puntosUsuario) {
        PuntosUsuarioResponseDTO response = new PuntosUsuarioResponseDTO();
        response.setId(puntosUsuario.getId());
        response.setIdUsuario(puntosUsuario.getIdUsuario());
        response.setPuntosTotales(puntosUsuario.getPuntosTotales());
        response.setPuntosDisponibles(puntosUsuario.getPuntosDisponibles());
        response.setPuntosUsados(puntosUsuario.getPuntosUsados());
        response.setNivelUsuario(puntosUsuario.getNivelUsuario().name());
        response.setCodigoReferido(puntosUsuario.getCodigoReferido());
        response.setFechaCreacion(puntosUsuario.getFechaCreacion());
        response.setFechaActualizacion(puntosUsuario.getFechaActualizacion());
        return response;
    }

    private TransaccionPuntosResponseDTO convertirTransaccionAResponseDTO(TransaccionPuntos transaccion) {
        TransaccionPuntosResponseDTO response = new TransaccionPuntosResponseDTO();
        response.setId(transaccion.getId());
        response.setIdUsuario(transaccion.getIdUsuario());
        response.setIdEvento(transaccion.getIdEvento());
        response.setTipoTransaccion(transaccion.getTipoTransaccion().name());
        response.setPuntos(transaccion.getPuntos());
        response.setPuntosAnteriores(transaccion.getPuntosAnteriores());
        response.setPuntosNuevos(transaccion.getPuntosNuevos());
        response.setDescripcion(transaccion.getDescripcion());
        response.setCodigoReferencia(transaccion.getCodigoReferencia());
        response.setFechaTransaccion(transaccion.getFechaTransaccion());
        
        if (transaccion.getEvento() != null) {
            EventoPuntosResponseDTO eventoDTO = convertirEventoAResponseDTO(transaccion.getEvento());
            response.setEvento(eventoDTO);
        }
        
        return response;
    }

    private EventoPuntosResponseDTO convertirEventoAResponseDTO(EventoPuntos evento) {
        EventoPuntosResponseDTO response = new EventoPuntosResponseDTO();
        response.setId(evento.getId());
        response.setTipoEvento(evento.getTipoEvento().name());
        response.setNombreEvento(evento.getNombreEvento());
        response.setDescripcionEvento(evento.getDescripcionEvento());
        response.setPuntosOtorgados(evento.getPuntosOtorgados());
        response.setCodigoEvento(evento.getCodigoEvento());
        response.setFechaEvento(evento.getFechaEvento());
        response.setLugarEvento(evento.getLugarEvento());
        response.setDireccionEvento(evento.getDireccionEvento());
        response.setIdUsuario(evento.getIdUsuario());
        response.setIdProducto(evento.getIdProducto());
        response.setIdPedido(evento.getIdPedido());
        response.setFechaCreacion(evento.getFechaCreacion());
        response.setActivo(evento.getActivo());
        return response;
    }
}

