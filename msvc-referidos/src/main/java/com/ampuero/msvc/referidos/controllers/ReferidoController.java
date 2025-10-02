package com.ampuero.msvc.referidos.controllers;

import com.ampuero.msvc.referidos.dtos.ReferidoCreationDTO;
import com.ampuero.msvc.referidos.dtos.ReferidoEstadoDTO;
import com.ampuero.msvc.referidos.dtos.ErrorDTO;
import com.ampuero.msvc.referidos.entities.Referido;
import com.ampuero.msvc.referidos.services.ReferidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/referidos")
@Validated
@Tag(
        name = "Referidos API",
        description = "API para gestión del sistema de referidos y gamificación Level-Up Gamer"
)
public class ReferidoController {

    @Autowired
    private ReferidoService referidoService;

    @PostMapping
    @Operation(
            summary = "Crear nuevo referido",
            description = "Endpoint que permite crear un nuevo usuario en el sistema de referidos con código de referido opcional"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Referido creado exitosamente"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos o código de referido no válido",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "El email o RUN ya existen en el sistema",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            )
    })
    public ResponseEntity<Referido> crearReferido(@Valid @RequestBody ReferidoCreationDTO referidoCreationDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(referidoService.crearReferido(referidoCreationDTO));
    }

    @GetMapping
    @Operation(
            summary = "Obtener todos los referidos",
            description = "Endpoint que devuelve una lista con todos los usuarios del sistema de referidos"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de referidos obtenida exitosamente"
            )
    })
    public ResponseEntity<List<Referido>> traerTodos() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(referidoService.traerTodos());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener referido por ID",
            description = "Endpoint que devuelve un usuario específico del sistema de referidos por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Referido obtenido exitosamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Referido no encontrado con el ID indicado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            )
    })
    @Parameters(value = {
            @Parameter(
                    name = "id",
                    description = "ID único del referido",
                    required = true
            )
    })
    public ResponseEntity<Referido> traerReferido(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(referidoService.traerPorId(id));
    }

    @GetMapping("/codigo/{codigo}")
    @Operation(
            summary = "Validar código de referido",
            description = "Endpoint que valida si un código de referido existe y devuelve la información del referidor"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Código válido"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Código de referido no válido",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            )
    })
    public ResponseEntity<Referido> validarCodigoReferido(@PathVariable String codigo) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(referidoService.buscarPorCodigoReferido(codigo));
    }

    @GetMapping("/usuario/{usuarioId}/referidos")
    @Operation(
            summary = "Obtener referidos por usuario",
            description = "Endpoint que devuelve todos los usuarios referidos por un usuario específico"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de referidos obtenida exitosamente"
            )
    })
    public ResponseEntity<List<Referido>> obtenerReferidosPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(referidoService.buscarReferidosPorReferidor(usuarioId));
    }

    @GetMapping("/{id}/puntos")
    @Operation(
            summary = "Obtener puntos LevelUp de usuario",
            description = "Endpoint que devuelve los puntos LevelUp acumulados por un usuario"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Puntos obtenidos exitosamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario no encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            )
    })
    public ResponseEntity<Integer> obtenerPuntosUsuario(@PathVariable Long id) {
        Referido referido = referidoService.traerPorId(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(referido.getPuntosLevelup());
    }

    @PostMapping("/{id}/puntos/sumar")
    @Operation(
            summary = "Sumar puntos LevelUp",
            description = "Endpoint que permite sumar puntos LevelUp a un usuario (por referido exitoso)"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Puntos sumados exitosamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario no encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            )
    })
    public ResponseEntity<String> sumarPuntos(@PathVariable Long id, @RequestParam Integer puntos) {
        referidoService.sumarPuntosPorReferido(id, puntos);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Puntos sumados exitosamente");
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar referido",
            description = "Endpoint que permite actualizar la información de un usuario referido"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Referido actualizado exitosamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Referido no encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            )
    })
    public ResponseEntity<Referido> actualizarReferido(@PathVariable Long id, @Valid @RequestBody Referido referido) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(referidoService.actualizarReferido(id, referido));
    }

    @PutMapping("/estado/{id}")
    @Operation(
            summary = "Actualizar estado de referido",
            description = "Endpoint que permite activar/desactivar un usuario referido"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Estado actualizado exitosamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Referido no encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            )
    })
    public ResponseEntity<Referido> actualizarEstadoReferido(@PathVariable Long id, @Valid @RequestBody ReferidoEstadoDTO referidoEstadoDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(referidoService.actualizarEstadoReferido(id, referidoEstadoDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar referido",
            description = "Endpoint que elimina permanentemente un usuario del sistema de referidos"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Referido eliminado exitosamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Referido no encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            )
    })
    public ResponseEntity<Void> eliminarReferido(@PathVariable Long id) {
        referidoService.eliminarReferido(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PostMapping("/{id}/canje")
    @Operation(
            summary = "Canjear producto por puntos",
            description = "Endpoint que permite canjear productos usando puntos LevelUp"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Canje realizado exitosamente"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Puntos insuficientes o error en el canje",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario o producto no encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            )
    })
    public ResponseEntity<Map<String, Object>> canjearProducto(
            @PathVariable Long id,
            @RequestParam Long idProducto,
            @RequestParam Integer puntosRequeridos) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(referidoService.canjearProductoPorPuntos(id, idProducto, puntosRequeridos));
    }

    @GetMapping("/productos/canjeables")
    @Operation(
            summary = "Obtener productos canjeables",
            description = "Endpoint que devuelve productos disponibles para canje según los puntos disponibles"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de productos canjeables obtenida exitosamente"
            )
    })
    public ResponseEntity<List<Map<String, Object>>> obtenerProductosCanjeables(@RequestParam Integer puntos) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(referidoService.obtenerProductosCanjeables(puntos));
    }

    @GetMapping("/{id}/descuentos")
    @Operation(
            summary = "Obtener descuentos disponibles",
            description = "Endpoint que devuelve descuentos y promociones disponibles para el usuario según su nivel y puntos"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Descuentos obtenidos exitosamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario no encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            )
    })
    public ResponseEntity<Map<String, Object>> obtenerDescuentosDisponibles(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(referidoService.obtenerDescuentosDisponibles(id));
    }
}
