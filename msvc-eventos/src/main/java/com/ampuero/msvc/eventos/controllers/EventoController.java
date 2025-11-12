package com.ampuero.msvc.eventos.controllers;

import com.ampuero.msvc.eventos.dtos.EventoCreationDTO;
import com.ampuero.msvc.eventos.dtos.EventoEstadoDTO;
import com.ampuero.msvc.eventos.dtos.EventoResponseDTO;
import com.ampuero.msvc.eventos.models.Evento;
import com.ampuero.msvc.eventos.services.EventoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;
    
    @Value("${jwt.secret:levelUpGamerSecretKey2024}")
    private String jwtSecret;

    /**
     * Crear nuevo evento
     * POST: /eventos
     */
    @PostMapping
    public ResponseEntity<EventoResponseDTO> crearEvento(@Valid @RequestBody EventoCreationDTO eventoDetails) {
        Evento evento = eventoService.crearEvento(eventoDetails);
        EventoResponseDTO response = convertirAResponseDTO(evento);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Obtener todos los eventos
     * GET: /eventos
     */
    @GetMapping
    public ResponseEntity<List<EventoResponseDTO>> traerTodos() {
        List<Evento> eventos = eventoService.traerTodos();
        List<EventoResponseDTO> response = eventos.stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener eventos futuros
     * GET: /eventos/futuros
     */
    @GetMapping("/futuros")
    public ResponseEntity<List<EventoResponseDTO>> traerEventosFuturos() {
        List<Evento> eventos = eventoService.traerEventosFuturos();
        List<EventoResponseDTO> response = eventos.stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
    
    /**
     * Obtener eventos próximos (compatibilidad con Kotlin)
     * GET: /eventos/proximos
     */
    @GetMapping("/proximos")
    public ResponseEntity<List<EventoResponseDTO>> traerEventosProximos(@RequestParam(required = false, defaultValue = "6") Integer limit) {
        List<Evento> eventos = eventoService.traerEventosFuturos();
        // Limitar resultados si se especifica
        if (limit != null && limit > 0 && eventos.size() > limit) {
            eventos = eventos.subList(0, limit);
        }
        List<EventoResponseDTO> response = eventos.stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener eventos en curso
     * GET: /eventos/en-curso
     */
    @GetMapping("/en-curso")
    public ResponseEntity<List<EventoResponseDTO>> traerEventosEnCurso() {
        List<Evento> eventos = eventoService.traerEventosEnCurso();
        List<EventoResponseDTO> response = eventos.stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener eventos por tipo
     * GET: /eventos/tipo/{tipo}
     */
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<EventoResponseDTO>> traerEventosPorTipo(@PathVariable String tipo) {
        List<Evento> eventos = eventoService.traerEventosPorTipo(tipo);
        List<EventoResponseDTO> response = eventos.stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener eventos con cupos disponibles
     * GET: /eventos/con-cupos
     */
    @GetMapping("/con-cupos")
    public ResponseEntity<List<EventoResponseDTO>> traerEventosConCupos() {
        List<Evento> eventos = eventoService.traerEventosConCupos();
        List<EventoResponseDTO> response = eventos.stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener eventos con coordenadas para mapa
     * GET: /eventos/mapa
     */
    @GetMapping("/mapa")
    public ResponseEntity<List<EventoResponseDTO>> traerEventosConCoordenadas() {
        List<Evento> eventos = eventoService.traerEventosConCoordenadas();
        List<EventoResponseDTO> response = eventos.stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener evento por ID
     * GET: /eventos/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> traerPorId(@PathVariable Long id) {
        Evento evento = eventoService.traerPorId(id);
        EventoResponseDTO response = convertirAResponseDTO(evento);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener evento por nombre
     * GET: /eventos/nombre/{nombre}
     */
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<EventoResponseDTO> traerPorNombre(@PathVariable String nombre) {
        Evento evento = eventoService.traerPorNombre(nombre);
        EventoResponseDTO response = convertirAResponseDTO(evento);
        return ResponseEntity.ok(response);
    }

    /**
     * Validar disponibilidad de evento
     * GET: /eventos/{id}/disponibilidad
     */
    @GetMapping("/{id}/disponibilidad")
    public ResponseEntity<Boolean> validarDisponibilidad(@PathVariable Long id) {
        boolean disponible = eventoService.validarDisponibilidad(id);
        return ResponseEntity.ok(disponible);
    }

    /**
     * Registrar participación en evento
     * POST: /eventos/{id}/participar
     */
    @PostMapping("/{id}/participar")
    public ResponseEntity<EventoResponseDTO> registrarParticipacion(@PathVariable Long id, 
                                                                   @RequestParam Long usuarioId) {
        Evento evento = eventoService.registrarParticipacion(id, usuarioId);
        EventoResponseDTO response = convertirAResponseDTO(evento);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Inscribirse en evento (compatibilidad con Kotlin)
     * POST: /eventos/{id}/inscribir
     */
    @PostMapping("/{id}/inscribir")
    public ResponseEntity<Map<String, Object>> inscribirseEvento(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Id", required = false) Long userId,
            jakarta.servlet.http.HttpServletRequest request) {
        // Si no se proporciona X-User-Id, intentar extraer del token JWT
        if (userId == null) {
            userId = extractUserIdFromToken(request);
        }
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        Evento evento = eventoService.registrarParticipacion(id, userId);
        EventoResponseDTO response = convertirAResponseDTO(evento);
        
        // Crear respuesta compatible con Kotlin
        Map<String, Object> inscripcion = new java.util.HashMap<>();
        inscripcion.put("id", evento.getIdEvento().toString());
        inscripcion.put("eventoId", evento.getIdEvento().toString());
        inscripcion.put("usuarioId", userId.toString());
        inscripcion.put("fechaInscripcion", java.time.LocalDateTime.now().toString());
        inscripcion.put("estado", "INSCRITO");
        inscripcion.put("puntosGanados", evento.getPuntosLevelUp());
        
        return ResponseEntity.ok(inscripcion);
    }

    /**
     * Cancelar participación en evento
     * DELETE: /eventos/{id}/cancelar-participacion
     */
    @DeleteMapping("/{id}/cancelar-participacion")
    public ResponseEntity<EventoResponseDTO> cancelarParticipacion(@PathVariable Long id, 
                                                                  @RequestParam Long usuarioId) {
        Evento evento = eventoService.cancelarParticipacion(id, usuarioId);
        EventoResponseDTO response = convertirAResponseDTO(evento);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Cancelar inscripción (compatibilidad con Kotlin)
     * DELETE: /eventos/{id}/cancelar
     */
    @DeleteMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarInscripcion(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Id", required = false) Long userId,
            jakarta.servlet.http.HttpServletRequest request) {
        // Si no se proporciona X-User-Id, intentar extraer del token JWT
        if (userId == null) {
            userId = extractUserIdFromToken(request);
        }
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        eventoService.cancelarParticipacion(id, userId);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Obtener mis inscripciones (compatibilidad con Kotlin)
     * GET: /eventos/mis-inscripciones
     */
    @GetMapping("/mis-inscripciones")
    public ResponseEntity<List<Map<String, Object>>> getMisInscripciones(
            @RequestHeader(value = "X-User-Id", required = false) Long userId,
            jakarta.servlet.http.HttpServletRequest request) {
        // Si no se proporciona X-User-Id, intentar extraer del token JWT
        if (userId == null) {
            userId = extractUserIdFromToken(request);
        }
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        // Obtener todos los eventos y filtrar por inscripciones del usuario
        List<Evento> todosEventos = eventoService.traerTodos();
        List<Map<String, Object>> inscripciones = new java.util.ArrayList<>();
        
        // Por ahora, retornar lista vacía - se debe implementar la lógica de inscripciones
        // TODO: Implementar servicio de inscripciones para obtener las inscripciones del usuario
        
        return ResponseEntity.ok(inscripciones);
    }

    /**
     * Actualizar evento
     * PUT: /eventos/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> actualizarEvento(@PathVariable Long id, 
                                                             @Valid @RequestBody Evento eventoDetails) {
        Evento evento = eventoService.actualizarEvento(id, eventoDetails);
        EventoResponseDTO response = convertirAResponseDTO(evento);
        return ResponseEntity.ok(response);
    }

    /**
     * Actualizar estado de evento
     * PUT: /eventos/{id}/estado
     */
    @PutMapping("/{id}/estado")
    public ResponseEntity<EventoResponseDTO> actualizarEstadoEvento(@PathVariable Long id, 
                                                                   @Valid @RequestBody EventoEstadoDTO estadoDetails) {
        Evento evento = eventoService.actualizarEstadoEvento(id, estadoDetails);
        EventoResponseDTO response = convertirAResponseDTO(evento);
        return ResponseEntity.ok(response);
    }

    /**
     * Eliminar evento
     * DELETE: /eventos/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEvento(@PathVariable Long id) {
        eventoService.eliminarEvento(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Convierte una entidad Evento a EventoResponseDTO
     */
    private EventoResponseDTO convertirAResponseDTO(Evento evento) {
        EventoResponseDTO response = new EventoResponseDTO();
        response.setIdEvento(evento.getIdEvento());
        response.setNombreEvento(evento.getNombreEvento());
        response.setDescripcionEvento(evento.getDescripcionEvento());
        response.setFechaInicio(evento.getFechaInicio());
        response.setFechaFin(evento.getFechaFin());
        response.setUbicacionEvento(evento.getUbicacionEvento());
        response.setCoordenadasLatitud(evento.getCoordenadasLatitud());
        response.setCoordenadasLongitud(evento.getCoordenadasLongitud());
        response.setTipoEvento(evento.getTipoEvento());
        response.setCuposMaximos(evento.getCuposMaximos());
        response.setCuposDisponibles(evento.getCuposDisponibles());
        response.setCostoEntrada(evento.getCostoEntrada());
        response.setPuntosLevelUp(evento.getPuntosLevelUp());
        response.setActivo(evento.getActivo());
        response.setRequisitosEdad(evento.getRequisitosEdad());
        response.setEquiposRequeridos(evento.getEquiposRequeridos());
        response.setCiudad(evento.getCiudad());
        response.setImagen(evento.getImagen());
        response.setImagenes(evento.getImagenes());
        return response;
    }
    
    // ========== MÉTODOS PRIVADOS ==========
    
    /**
     * Extrae el userId del token JWT del header Authorization
     */
    private Long extractUserIdFromToken(HttpServletRequest request) {
        try {
            String bearerToken = request.getHeader("Authorization");
            if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
                return null;
            }
            
            String token = bearerToken.substring(7);
            
            // Usar la misma clave secreta que el servicio de autenticación
            SecretKey signingKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
            
            Claims claims = Jwts.parser()
                    .verifyWith(signingKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            
            // El userId está en el claim "sub" o "userId"
            Object userIdObj = claims.get("userId");
            if (userIdObj == null) {
                userIdObj = claims.getSubject();
            }
            
            if (userIdObj instanceof Long) {
                return (Long) userIdObj;
            } else if (userIdObj instanceof Integer) {
                return ((Integer) userIdObj).longValue();
            } else if (userIdObj instanceof String) {
                return Long.parseLong((String) userIdObj);
            }
            
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
