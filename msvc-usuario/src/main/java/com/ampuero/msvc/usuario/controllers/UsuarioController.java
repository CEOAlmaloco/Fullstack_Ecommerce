package com.ampuero.msvc.usuario.controllers;

import com.ampuero.msvc.usuario.dtos.UsuarioCreationDTO;
import com.ampuero.msvc.usuario.dtos.UsuarioResponseDTO;
import com.ampuero.msvc.usuario.dtos.UsuarioUpdateDTO;
import com.ampuero.msvc.usuario.entities.Usuario;
import com.ampuero.msvc.usuario.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;
import com.ampuero.msvc.usuario.entities.DireccionUsuario;
import com.ampuero.msvc.usuario.dtos.DireccionUsuarioResponseDTO;

/**
 * Controlador REST para la gestión de usuarios
 * Expone endpoints para operaciones CRUD y consultas específicas de usuarios
 */
@RestController
@RequestMapping("/api/v1/usuarios")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173", "http://10.0.2.2:8094"})
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Usuario API", description = "API para gestión de usuarios Level-Up Gamer")
public class UsuarioController {

    private final UsuarioService usuarioService;

    /**
     * Crea un nuevo usuario
     */
    @PostMapping
    @Operation(summary = "Crear usuario", description = "Registra un nuevo usuario en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "409", description = "Usuario ya existe")
    })
    public ResponseEntity<UsuarioResponseDTO> crearUsuario(@Valid @RequestBody UsuarioCreationDTO usuarioCreationDTO) {
        log.info("Creando nuevo usuario: {}", usuarioCreationDTO.getCorreo());
        UsuarioResponseDTO usuarioCreado = usuarioService.crearUsuario(usuarioCreationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreado);
    }

    /**
     * Obtiene un usuario por ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID", description = "Busca un usuario específico por su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<UsuarioResponseDTO> obtenerUsuarioPorId(
            @Parameter(description = "ID del usuario") @PathVariable Long id) {
        log.info("Obteniendo usuario por ID: {}", id);
        UsuarioResponseDTO usuario = usuarioService.obtenerUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }

    /**
     * Obtiene un usuario por correo electrónico
     */
    @GetMapping("/correo/{correo}")
    @Operation(summary = "Obtener usuario por correo", description = "Busca un usuario por su correo electrónico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<UsuarioResponseDTO> obtenerUsuarioPorCorreo(
            @Parameter(description = "Correo electrónico del usuario") @PathVariable String correo) {
        log.info("Obteniendo usuario por correo: {}", correo);
        UsuarioResponseDTO usuario = usuarioService.obtenerUsuarioPorCorreo(correo);
        return ResponseEntity.ok(usuario);
    }

    /**
     * Obtiene un usuario por código de referido
     */
    @GetMapping("/referido/{codigoReferido}")
    @Operation(summary = "Obtener usuario por código de referido", description = "Busca un usuario por su código de referido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<UsuarioResponseDTO> obtenerUsuarioPorCodigoReferido(
            @Parameter(description = "Código de referido") @PathVariable String codigoReferido) {
        log.info("Obteniendo usuario por código de referido: {}", codigoReferido);
        UsuarioResponseDTO usuario = usuarioService.obtenerUsuarioPorCodigoReferido(codigoReferido);
        return ResponseEntity.ok(usuario);
    }

    /**
     * Obtiene todos los usuarios con paginación
     */
    @GetMapping
    @Operation(summary = "Listar usuarios", description = "Obtiene una lista paginada de todos los usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente")
    })
    public ResponseEntity<Page<UsuarioResponseDTO>> obtenerTodosLosUsuarios(
            @PageableDefault(size = 20) Pageable pageable) {
        log.info("Obteniendo todos los usuarios con paginación");
        Page<UsuarioResponseDTO> usuarios = usuarioService.obtenerTodosLosUsuarios(pageable);
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Obtiene usuarios por tipo
     */
    @GetMapping("/tipo/{tipoUsuario}")
    @Operation(summary = "Listar usuarios por tipo", description = "Obtiene usuarios filtrados por tipo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente")
    })
    public ResponseEntity<Page<UsuarioResponseDTO>> obtenerUsuariosPorTipo(
            @Parameter(description = "Tipo de usuario") @PathVariable Usuario.TipoUsuario tipoUsuario,
            @PageableDefault(size = 20) Pageable pageable) {
        log.info("Obteniendo usuarios por tipo: {}", tipoUsuario);
        Page<UsuarioResponseDTO> usuarios = usuarioService.obtenerUsuariosPorTipo(tipoUsuario, pageable);
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Obtiene usuarios por estado
     */
    @GetMapping("/estado/{estado}")
    @Operation(summary = "Listar usuarios por estado", description = "Obtiene usuarios filtrados por estado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente")
    })
    public ResponseEntity<Page<UsuarioResponseDTO>> obtenerUsuariosPorEstado(
            @Parameter(description = "Estado del usuario") @PathVariable Usuario.EstadoUsuario estado,
            @PageableDefault(size = 20) Pageable pageable) {
        log.info("Obteniendo usuarios por estado: {}", estado);
        Page<UsuarioResponseDTO> usuarios = usuarioService.obtenerUsuariosPorEstado(estado, pageable);
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Obtiene usuarios por nivel
     */
    @GetMapping("/nivel/{nivelUsuario}")
    @Operation(summary = "Listar usuarios por nivel", description = "Obtiene usuarios filtrados por nivel LevelUp")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente")
    })
    public ResponseEntity<Page<UsuarioResponseDTO>> obtenerUsuariosPorNivel(
            @Parameter(description = "Nivel del usuario") @PathVariable Usuario.NivelUsuario nivelUsuario,
            @PageableDefault(size = 20) Pageable pageable) {
        log.info("Obteniendo usuarios por nivel: {}", nivelUsuario);
        Page<UsuarioResponseDTO> usuarios = usuarioService.obtenerUsuariosPorNivel(nivelUsuario, pageable);
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Obtiene usuarios referidos por otro usuario
     */
    @GetMapping("/referidos/{codigoReferido}")
    @Operation(summary = "Listar usuarios referidos", description = "Obtiene usuarios que fueron referidos por otro usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios referidos obtenida exitosamente")
    })
    public ResponseEntity<Page<UsuarioResponseDTO>> obtenerUsuariosReferidos(
            @Parameter(description = "Código de referido del usuario") @PathVariable String codigoReferido,
            @PageableDefault(size = 20) Pageable pageable) {
        log.info("Obteniendo usuarios referidos por: {}", codigoReferido);
        Page<UsuarioResponseDTO> usuarios = usuarioService.obtenerUsuariosReferidos(codigoReferido, pageable);
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Actualiza un usuario
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario", description = "Actualiza los datos de un usuario existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflicto en los datos")
    })
    public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(
            @Parameter(description = "ID del usuario") @PathVariable Long id,
            @Valid @RequestBody UsuarioUpdateDTO usuarioUpdateDTO) {
        log.info("Actualizando usuario con ID: {}", id);
        UsuarioResponseDTO usuarioActualizado = usuarioService.actualizarUsuario(id, usuarioUpdateDTO);
        return ResponseEntity.ok(usuarioActualizado);
    }

    /**
     * Actualiza el estado de un usuario
     */
    @PatchMapping("/{id}/estado")
    @Operation(summary = "Actualizar estado de usuario", description = "Cambia el estado de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<UsuarioResponseDTO> actualizarEstadoUsuario(
            @Parameter(description = "ID del usuario") @PathVariable Long id,
            @Parameter(description = "Nuevo estado") @RequestBody Usuario.EstadoUsuario nuevoEstado) {
        log.info("Actualizando estado del usuario ID: {} a {}", id, nuevoEstado);
        UsuarioResponseDTO usuarioActualizado = usuarioService.actualizarEstadoUsuario(id, nuevoEstado);
        return ResponseEntity.ok(usuarioActualizado);
    }

    /**
     * Actualiza el nivel de un usuario
     */
    @PatchMapping("/{id}/nivel")
    @Operation(summary = "Actualizar nivel de usuario", description = "Cambia el nivel LevelUp de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Nivel actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<UsuarioResponseDTO> actualizarNivelUsuario(
            @Parameter(description = "ID del usuario") @PathVariable Long id,
            @Parameter(description = "Nuevo nivel") @RequestBody Usuario.NivelUsuario nuevoNivel) {
        log.info("Actualizando nivel del usuario ID: {} a {}", id, nuevoNivel);
        UsuarioResponseDTO usuarioActualizado = usuarioService.actualizarNivelUsuario(id, nuevoNivel);
        return ResponseEntity.ok(usuarioActualizado);
    }

    /**
     * Agrega puntos LevelUp a un usuario
     */
    @PostMapping("/{id}/puntos")
    @Operation(summary = "Agregar puntos LevelUp", description = "Suma puntos LevelUp a un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Puntos agregados exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<UsuarioResponseDTO> agregarPuntosLevelUp(
            @Parameter(description = "ID del usuario") @PathVariable Long id,
            @Parameter(description = "Puntos a agregar") @RequestBody Integer puntos) {
        log.info("Agregando {} puntos LevelUp al usuario ID: {}", puntos, id);
        UsuarioResponseDTO usuarioActualizado = usuarioService.agregarPuntosLevelUp(id, puntos);
        return ResponseEntity.ok(usuarioActualizado);
    }

    /**
     * Elimina un usuario (soft delete)
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario del sistema (soft delete)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Void> eliminarUsuario(
            @Parameter(description = "ID del usuario") @PathVariable Long id) {
        log.info("Eliminando usuario con ID: {}", id);
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Reactiva un usuario eliminado
     */
    @PatchMapping("/{id}/reactivar")
    @Operation(summary = "Reactivar usuario", description = "Reactiva un usuario que fue eliminado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario reactivado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<UsuarioResponseDTO> reactivarUsuario(
            @Parameter(description = "ID del usuario") @PathVariable Long id) {
        log.info("Reactivando usuario con ID: {}", id);
        UsuarioResponseDTO usuarioReactivado = usuarioService.reactivarUsuario(id);
        return ResponseEntity.ok(usuarioReactivado);
    }

    /**
     * Busca usuarios por criterios
     */
    @GetMapping("/buscar")
    @Operation(summary = "Buscar usuarios", description = "Busca usuarios por diferentes criterios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente")
    })
    public ResponseEntity<Page<UsuarioResponseDTO>> buscarUsuarios(
            @Parameter(description = "Nombre a buscar") @RequestParam(required = false) String nombre,
            @Parameter(description = "Correo a buscar") @RequestParam(required = false) String correo,
            @Parameter(description = "Ciudad a buscar") @RequestParam(required = false) String ciudad,
            @PageableDefault(size = 20) Pageable pageable) {
        log.info("Buscando usuarios con criterios - nombre: {}, correo: {}, ciudad: {}", nombre, correo, ciudad);
        Page<UsuarioResponseDTO> usuarios = usuarioService.buscarUsuarios(nombre, correo, ciudad, pageable);
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Obtiene estadísticas de usuarios
     */
    @GetMapping("/estadisticas")
    @Operation(summary = "Obtener estadísticas", description = "Obtiene estadísticas generales de usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estadísticas obtenidas exitosamente")
    })
    public ResponseEntity<Map<String, Object>> obtenerEstadisticasUsuarios() {
        log.info("Obteniendo estadísticas de usuarios");
        Map<String, Object> estadisticas = usuarioService.obtenerEstadisticasUsuarios();
        return ResponseEntity.ok(estadisticas);
    }

    /**
     * Verifica si un correo está disponible
     */
    @GetMapping("/verificar-correo/{correo}")
    @Operation(summary = "Verificar disponibilidad de correo", description = "Verifica si un correo electrónico está disponible")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Verificación completada")
    })
    public ResponseEntity<Map<String, Boolean>> verificarDisponibilidadCorreo(
            @Parameter(description = "Correo a verificar") @PathVariable String correo) {
        log.info("Verificando disponibilidad del correo: {}", correo);
        boolean disponible = !usuarioService.existeUsuarioConCorreo(correo);
        return ResponseEntity.ok(Map.of("disponible", disponible));
    }

    /**
     * Verifica si un código de referido está disponible
     */
    @GetMapping("/verificar-codigo-referido/{codigoReferido}")
    @Operation(summary = "Verificar disponibilidad de código de referido", description = "Verifica si un código de referido está disponible")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Verificación completada")
    })
    public ResponseEntity<Map<String, Boolean>> verificarDisponibilidadCodigoReferido(
            @Parameter(description = "Código de referido a verificar") @PathVariable String codigoReferido) {
        log.info("Verificando disponibilidad del código de referido: {}", codigoReferido);
        boolean disponible = !usuarioService.existeUsuarioConCodigoReferido(codigoReferido);
        return ResponseEntity.ok(Map.of("disponible", disponible));
    }

    // ===== Endpoints de perfil y direcciones para el frontend =====
    @GetMapping("/perfil")
    public ResponseEntity<UsuarioResponseDTO> getPerfil(@RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.ok(usuarioService.getCurrentUserProfile(userId));
    }

    @PutMapping("/perfil")
    public ResponseEntity<UsuarioResponseDTO> updatePerfil(@RequestHeader("X-User-Id") Long userId,
                                                           @Valid @RequestBody UsuarioUpdateDTO request) {
        return ResponseEntity.ok(usuarioService.updateProfile(userId, request));
    }

    @GetMapping("/direcciones")
    public ResponseEntity<List<DireccionUsuarioResponseDTO>> getDirecciones(@RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.ok(usuarioService.getUserAddresses(userId));
    }

    @PostMapping("/direcciones")
    public ResponseEntity<DireccionUsuarioResponseDTO> addDireccion(@RequestHeader("X-User-Id") Long userId,
                                                                     @Valid @RequestBody DireccionUsuario direccion) {
        return new ResponseEntity<>(usuarioService.addAddress(userId, direccion), HttpStatus.CREATED);
    }

    @DeleteMapping("/direcciones/{id}")
    public ResponseEntity<Void> deleteDireccion(@RequestHeader("X-User-Id") Long userId, @PathVariable Long id) {
        usuarioService.deleteAddress(userId, id);
        return ResponseEntity.noContent().build();
    }
}
