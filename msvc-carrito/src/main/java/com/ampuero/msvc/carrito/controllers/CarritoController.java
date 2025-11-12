package com.ampuero.msvc.carrito.controllers;

import com.ampuero.msvc.carrito.dtos.CarritoCreationDTO;
import com.ampuero.msvc.carrito.dtos.CarritoEstadoDTO;
import com.ampuero.msvc.carrito.dtos.CarritoResponseDTO;
import com.ampuero.msvc.carrito.dtos.ItemCarritoCreationDTO;
import com.ampuero.msvc.carrito.dtos.ItemCarritoResponseDTO;
import com.ampuero.msvc.carrito.models.Carrito;
import com.ampuero.msvc.carrito.models.ItemCarrito;
import com.ampuero.msvc.carrito.services.CarritoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    // ========== ENDPOINTS DE CARRITOS ==========

    /**
     * Crear nuevo carrito
     * POST: /carrito
     */
    @PostMapping
    public ResponseEntity<CarritoResponseDTO> crearCarrito(@Valid @RequestBody CarritoCreationDTO carritoDetails) {
        Carrito carrito = carritoService.crearCarrito(carritoDetails);
        CarritoResponseDTO response = convertirCarritoAResponseDTO(carrito);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Obtener todos los carritos
     * GET: /carrito
     */
    @GetMapping
    public ResponseEntity<List<CarritoResponseDTO>> traerTodosCarritos() {
        List<Carrito> carritos = carritoService.traerTodosCarritos();
        List<CarritoResponseDTO> response = carritos.stream()
                .map(this::convertirCarritoAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener carritos por usuario
     * GET: /carrito/usuario/{idUsuario}
     */
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<CarritoResponseDTO>> traerCarritosPorUsuario(@PathVariable Long idUsuario) {
        List<Carrito> carritos = carritoService.traerCarritosPorUsuario(idUsuario);
        List<CarritoResponseDTO> response = carritos.stream()
                .map(this::convertirCarritoAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener carrito activo por usuario
     * GET: /carrito/usuario/{idUsuario}/activo
     */
    @GetMapping("/usuario/{idUsuario}/activo")
    public ResponseEntity<CarritoResponseDTO> traerCarritoActivoPorUsuario(@PathVariable Long idUsuario) {
        Carrito carrito = carritoService.traerCarritoActivoPorUsuario(idUsuario);
        CarritoResponseDTO response = convertirCarritoAResponseDTO(carrito);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Obtener carrito activo del usuario autenticado (compatibilidad con Kotlin y Frontend)
     * GET: /carrito/activo
     * Usa el header X-User-Id para obtener el usuario autenticado
     */
    @GetMapping("/activo")
    public ResponseEntity<CarritoResponseDTO> traerCarritoActivo(@RequestHeader("X-User-Id") Long userId) {
        Carrito carrito = carritoService.traerCarritoActivoPorUsuario(userId);
        CarritoResponseDTO response = convertirCarritoAResponseDTO(carrito);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener carritos por estado
     * GET: /carrito/estado/{estado}
     */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<CarritoResponseDTO>> traerCarritosPorEstado(@PathVariable String estado) {
        List<Carrito> carritos = carritoService.traerCarritosPorEstado(estado);
        List<CarritoResponseDTO> response = carritos.stream()
                .map(this::convertirCarritoAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener carritos expirados
     * GET: /carrito/expirados
     */
    @GetMapping("/expirados")
    public ResponseEntity<List<CarritoResponseDTO>> traerCarritosExpirados() {
        List<Carrito> carritos = carritoService.traerCarritosExpirados();
        List<CarritoResponseDTO> response = carritos.stream()
                .map(this::convertirCarritoAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener carritos por rango de fechas
     * GET: /carrito/fechas?fechaInicio=...&fechaFin=...
     */
    @GetMapping("/fechas")
    public ResponseEntity<List<CarritoResponseDTO>> traerCarritosPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        List<Carrito> carritos = carritoService.traerCarritosPorRangoFechas(fechaInicio, fechaFin);
        List<CarritoResponseDTO> response = carritos.stream()
                .map(this::convertirCarritoAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener carritos por código promocional
     * GET: /carrito/promocion/{codigoPromocional}
     */
    @GetMapping("/promocion/{codigoPromocional}")
    public ResponseEntity<List<CarritoResponseDTO>> traerCarritosPorCodigoPromocional(@PathVariable String codigoPromocional) {
        List<Carrito> carritos = carritoService.traerCarritosPorCodigoPromocional(codigoPromocional);
        List<CarritoResponseDTO> response = carritos.stream()
                .map(this::convertirCarritoAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener carrito por ID
     * GET: /carrito/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<CarritoResponseDTO> traerCarritoPorId(@PathVariable Long id) {
        Carrito carrito = carritoService.traerCarritoPorId(id);
        CarritoResponseDTO response = convertirCarritoAResponseDTO(carrito);
        return ResponseEntity.ok(response);
    }

    /**
     * Limpiar carrito
     * POST: /carrito/{id}/limpiar
     */
    @PostMapping("/{id}/limpiar")
    public ResponseEntity<CarritoResponseDTO> limpiarCarrito(@PathVariable Long id) {
        Carrito carrito = carritoService.limpiarCarrito(id);
        CarritoResponseDTO response = convertirCarritoAResponseDTO(carrito);
        return ResponseEntity.ok(response);
    }

    /**
     * Aplicar promoción
     * POST: /carrito/{id}/promocion
     */
    @PostMapping("/{id}/promocion")
    public ResponseEntity<CarritoResponseDTO> aplicarPromocion(@PathVariable Long id, @RequestParam String codigoPromocional) {
        Carrito carrito = carritoService.aplicarPromocion(id, codigoPromocional);
        CarritoResponseDTO response = convertirCarritoAResponseDTO(carrito);
        return ResponseEntity.ok(response);
    }

    /**
     * Remover promoción
     * DELETE: /carrito/{id}/promocion
     */
    @DeleteMapping("/{id}/promocion")
    public ResponseEntity<CarritoResponseDTO> removerPromocion(@PathVariable Long id) {
        Carrito carrito = carritoService.removerPromocion(id);
        CarritoResponseDTO response = convertirCarritoAResponseDTO(carrito);
        return ResponseEntity.ok(response);
    }

    /**
     * Calcular totales
     * POST: /carrito/{id}/calcular-totales
     */
    @PostMapping("/{id}/calcular-totales")
    public ResponseEntity<CarritoResponseDTO> calcularTotales(@PathVariable Long id) {
        Carrito carrito = carritoService.calcularTotales(id);
        CarritoResponseDTO response = convertirCarritoAResponseDTO(carrito);
        return ResponseEntity.ok(response);
    }

    /**
     * Convertir carrito a pedido
     * POST: /carrito/{id}/convertir-pedido
     */
    @PostMapping("/{id}/convertir-pedido")
    public ResponseEntity<CarritoResponseDTO> convertirCarritoAPedido(@PathVariable Long id, @RequestParam Long idUsuario) {
        Carrito carrito = carritoService.convertirCarritoAPedido(id, idUsuario);
        CarritoResponseDTO response = convertirCarritoAResponseDTO(carrito);
        return ResponseEntity.ok(response);
    }

    /**
     * Duplicar carrito
     * POST: /carrito/{id}/duplicar
     */
    @PostMapping("/{id}/duplicar")
    public ResponseEntity<CarritoResponseDTO> duplicarCarrito(@PathVariable Long id, @RequestParam Long nuevoUsuario) {
        Carrito carrito = carritoService.duplicarCarrito(id, nuevoUsuario);
        CarritoResponseDTO response = convertirCarritoAResponseDTO(carrito);
        return ResponseEntity.ok(response);
    }

    /**
     * Guardar carrito para después
     * POST: /carrito/{id}/guardar
     */
    @PostMapping("/{id}/guardar")
    public ResponseEntity<CarritoResponseDTO> guardarCarritoParaDespues(@PathVariable Long id) {
        Carrito carrito = carritoService.guardarCarritoParaDespues(id);
        CarritoResponseDTO response = convertirCarritoAResponseDTO(carrito);
        return ResponseEntity.ok(response);
    }

    /**
     * Recuperar carrito guardado
     * POST: /carrito/{id}/recuperar
     */
    @PostMapping("/{id}/recuperar")
    public ResponseEntity<CarritoResponseDTO> recuperarCarritoGuardado(@PathVariable Long id) {
        Carrito carrito = carritoService.recuperarCarritoGuardado(id);
        CarritoResponseDTO response = convertirCarritoAResponseDTO(carrito);
        return ResponseEntity.ok(response);
    }

    /**
     * Actualizar carrito
     * PUT: /carrito/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<CarritoResponseDTO> actualizarCarrito(@PathVariable Long id, 
                                                               @Valid @RequestBody Carrito carritoDetails) {
        Carrito carrito = carritoService.actualizarCarrito(id, carritoDetails);
        CarritoResponseDTO response = convertirCarritoAResponseDTO(carrito);
        return ResponseEntity.ok(response);
    }

    /**
     * Actualizar estado de carrito
     * PUT: /carrito/{id}/estado
     */
    @PutMapping("/{id}/estado")
    public ResponseEntity<CarritoResponseDTO> actualizarEstadoCarrito(@PathVariable Long id, 
                                                                     @Valid @RequestBody CarritoEstadoDTO estadoDetails) {
        Carrito carrito = carritoService.actualizarEstadoCarrito(id, estadoDetails);
        CarritoResponseDTO response = convertirCarritoAResponseDTO(carrito);
        return ResponseEntity.ok(response);
    }

    /**
     * Eliminar carrito
     * DELETE: /carrito/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCarrito(@PathVariable Long id) {
        carritoService.eliminarCarrito(id);
        return ResponseEntity.noContent().build();
    }

    // ========== ENDPOINTS DE ITEMS ==========

    /**
     * Agregar item al carrito
     * POST: /carrito/items
     * Si se proporciona X-User-Id, devuelve el carrito completo actualizado (compatibilidad con Kotlin)
     */
    @PostMapping("/items")
    public ResponseEntity<?> agregarItem(@Valid @RequestBody ItemCarritoCreationDTO itemDetails, 
                                        @RequestHeader(value = "X-User-Id", required = false) Long userId) {
        ItemCarrito item = carritoService.agregarItem(itemDetails);
        
        // Si hay userId, devolver el carrito completo actualizado (compatibilidad con Kotlin)
        if (userId != null && item.getCarrito() != null) {
            Carrito carrito = carritoService.traerCarritoPorId(item.getCarrito().getIdCarrito());
            if (carrito != null) {
                CarritoResponseDTO response = convertirCarritoAResponseDTO(carrito);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }
        }
        
        // Si no hay userId, devolver solo el item creado (compatibilidad con otros clientes)
        ItemCarritoResponseDTO response = convertirItemAResponseDTO(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Obtener todos los items
     * GET: /carrito/items
     */
    @GetMapping("/items")
    public ResponseEntity<List<ItemCarritoResponseDTO>> traerTodosItems() {
        List<ItemCarrito> items = carritoService.traerTodosItems();
        List<ItemCarritoResponseDTO> response = items.stream()
                .map(this::convertirItemAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener items por carrito
     * GET: /carrito/items/carrito/{idCarrito}
     */
    @GetMapping("/items/carrito/{idCarrito}")
    public ResponseEntity<List<ItemCarritoResponseDTO>> traerItemsPorCarrito(@PathVariable Long idCarrito) {
        List<ItemCarrito> items = carritoService.traerItemsPorCarrito(idCarrito);
        List<ItemCarritoResponseDTO> response = items.stream()
                .map(this::convertirItemAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener items por estado
     * GET: /carrito/items/estado/{estado}
     */
    @GetMapping("/items/estado/{estado}")
    public ResponseEntity<List<ItemCarritoResponseDTO>> traerItemsPorEstado(@PathVariable String estado) {
        List<ItemCarrito> items = carritoService.traerItemsPorEstado(estado);
        List<ItemCarritoResponseDTO> response = items.stream()
                .map(this::convertirItemAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener items por producto
     * GET: /carrito/items/producto/{idProducto}
     */
    @GetMapping("/items/producto/{idProducto}")
    public ResponseEntity<List<ItemCarritoResponseDTO>> traerItemsPorProducto(@PathVariable Long idProducto) {
        List<ItemCarrito> items = carritoService.traerItemsPorProducto(idProducto);
        List<ItemCarritoResponseDTO> response = items.stream()
                .map(this::convertirItemAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener item por ID
     * GET: /carrito/items/{id}
     */
    @GetMapping("/items/{id}")
    public ResponseEntity<ItemCarritoResponseDTO> traerItemPorId(@PathVariable Long id) {
        ItemCarrito item = carritoService.traerItemPorId(id);
        ItemCarritoResponseDTO response = convertirItemAResponseDTO(item);
        return ResponseEntity.ok(response);
    }

    /**
     * Actualizar cantidad de item
     * PUT: /carrito/items/{id}/cantidad
     */
    @PutMapping("/items/{id}/cantidad")
    public ResponseEntity<ItemCarritoResponseDTO> actualizarCantidadItem(@PathVariable Long id, @RequestParam Integer nuevaCantidad) {
        ItemCarrito item = carritoService.actualizarCantidadItem(id, nuevaCantidad);
        ItemCarritoResponseDTO response = convertirItemAResponseDTO(item);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Actualizar cantidad de item (compatibilidad con Kotlin - devuelve CarritoDto)
     * PUT: /carrito/items/{itemId}
     * Usa el header X-User-Id y body con cantidad
     */
    @PutMapping("/items/{itemId}")
    public ResponseEntity<CarritoResponseDTO> actualizarCantidad(@PathVariable Long itemId, 
                                                                 @RequestHeader(value = "X-User-Id", required = false) Long userId,
                                                                 @RequestBody(required = false) Map<String, Integer> request) {
        // Si hay request body con cantidad, usarlo (compatibilidad con Kotlin)
        if (request != null && request.containsKey("cantidad")) {
            Integer cantidad = request.get("cantidad");
            ItemCarrito item = carritoService.actualizarCantidadItem(itemId, cantidad);
            if (item != null && item.getCarrito() != null && userId != null) {
                Carrito carrito = carritoService.traerCarritoPorId(item.getCarrito().getIdCarrito());
                CarritoResponseDTO response = convertirCarritoAResponseDTO(carrito);
                return ResponseEntity.ok(response);
            }
        }
        
        // Si no hay request body, devolver error
        return ResponseEntity.badRequest().build();
    }

    /**
     * Remover item del carrito (compatibilidad con Kotlin y Frontend - devuelve CarritoDto)
     * DELETE: /carrito/items/{id}
     * Si se proporciona X-User-Id, devuelve el carrito completo actualizado
     */
    @DeleteMapping("/items/{id}")
    public ResponseEntity<?> removerItem(@PathVariable Long id, @RequestHeader(value = "X-User-Id", required = false) Long userId) {
        ItemCarrito item = carritoService.traerItemPorId(id);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        
        carritoService.removerItem(id);
        
        // Si hay userId, devolver el carrito completo actualizado (compatibilidad con Kotlin)
        if (userId != null && item.getCarrito() != null) {
            Carrito carrito = carritoService.traerCarritoPorId(item.getCarrito().getIdCarrito());
            if (carrito != null) {
                CarritoResponseDTO response = convertirCarritoAResponseDTO(carrito);
                return ResponseEntity.ok(response);
            }
        }
        
        // Si no hay userId, devolver solo el item eliminado (compatibilidad con otros clientes)
        ItemCarritoResponseDTO response = convertirItemAResponseDTO(item);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Vaciar carrito (compatibilidad con Kotlin)
     * DELETE: /carrito/vaciar
     * Usa el header X-User-Id para obtener el usuario autenticado
     */
    @DeleteMapping("/vaciar")
    public ResponseEntity<Void> vaciarCarrito(@RequestHeader("X-User-Id") Long userId) {
        Carrito carrito = carritoService.traerCarritoActivoPorUsuario(userId);
        if (carrito != null) {
            carritoService.limpiarCarrito(carrito.getIdCarrito());
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * Contar items en carrito
     * GET: /carrito/{id}/contar-items
     */
    @GetMapping("/{id}/contar-items")
    public ResponseEntity<Long> contarItemsEnCarrito(@PathVariable Long id) {
        Long cantidad = carritoService.contarItemsEnCarrito(id);
        return ResponseEntity.ok(cantidad);
    }

    /**
     * Sumar cantidad de items en carrito
     * GET: /carrito/{id}/sumar-cantidad
     */
    @GetMapping("/{id}/sumar-cantidad")
    public ResponseEntity<Long> sumarCantidadItemsEnCarrito(@PathVariable Long id) {
        Long cantidad = carritoService.sumarCantidadItemsEnCarrito(id);
        return ResponseEntity.ok(cantidad);
    }

    // ========== MÉTODOS DE CONVERSIÓN ==========

    /**
     * Convierte una entidad Carrito a CarritoResponseDTO
     */
    private CarritoResponseDTO convertirCarritoAResponseDTO(Carrito carrito) {
        CarritoResponseDTO response = new CarritoResponseDTO();
        response.setIdCarrito(carrito.getIdCarrito());
        response.setIdUsuario(carrito.getIdUsuario());
        response.setFechaCreacion(carrito.getFechaCreacion());
        response.setFechaActualizacion(carrito.getFechaActualizacion());
        response.setTotalCarrito(carrito.getTotalCarrito());
        response.setTotalDescuentos(carrito.getTotalDescuentos());
        response.setTotalImpuestos(carrito.getTotalImpuestos());
        response.setTotalFinal(carrito.getTotalFinal());
        response.setMoneda(carrito.getMoneda());
        response.setEstadoCarrito(carrito.getEstadoCarrito());
        response.setFechaExpiracion(carrito.getFechaExpiracion());
        response.setCodigoPromocional(carrito.getCodigoPromocional());
        response.setIdPromocionAplicada(carrito.getIdPromocionAplicada());
        response.setNotasCarrito(carrito.getNotasCarrito());
        response.setActivo(carrito.getActivo());
        
        // Convertir items si existen
        if (carrito.getItems() != null) {
            response.setItems(carrito.getItems().stream()
                    .map(this::convertirItemAResponseDTO)
                    .collect(Collectors.toList()));
        }
        
        return response;
    }

    /**
     * Convierte una entidad ItemCarrito a ItemCarritoResponseDTO
     */
    private ItemCarritoResponseDTO convertirItemAResponseDTO(ItemCarrito item) {
        ItemCarritoResponseDTO response = new ItemCarritoResponseDTO();
        response.setIdItem(item.getIdItem());
        response.setIdCarrito(item.getCarrito() != null ? item.getCarrito().getIdCarrito() : null);
        response.setIdProducto(item.getIdProducto());
        response.setNombreProducto(item.getNombreProducto());
        response.setDescripcionProducto(item.getDescripcionProducto());
        response.setPrecioUnitario(item.getPrecioUnitario());
        response.setCantidad(item.getCantidad());
        response.setSubtotal(item.getSubtotal());
        response.setDescuentoAplicado(item.getDescuentoAplicado());
        response.setImpuestoAplicado(item.getImpuestoAplicado());
        response.setTotalItem(item.getTotalItem());
        response.setFechaAgregado(item.getFechaAgregado());
        response.setFechaActualizado(item.getFechaActualizado());
        response.setEstadoItem(item.getEstadoItem());
        response.setNotasItem(item.getNotasItem());
        response.setActivo(item.getActivo());
        return response;
    }
}
