package com.ampuero.msvc.inventario.controllers;

import com.ampuero.msvc.inventario.dtos.InventarioCreationDTO;
import com.ampuero.msvc.inventario.models.Inventario;
import com.ampuero.msvc.inventario.services.InventarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventario")
@Validated
@Tag(name = "Inventario API", description = "Gestión de inventario y control de stock")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    @Operation(summary = "Obtener todo el inventario", description = "Devuelve la lista completa del inventario")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    public ResponseEntity<List<Inventario>> obtenerTodo() {
        return ResponseEntity.ok(inventarioService.traerTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener inventario por ID", description = "Devuelve un registro de inventario específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventario encontrado"),
            @ApiResponse(responseCode = "404", description = "Inventario no encontrado")
    })
    public ResponseEntity<Inventario> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(inventarioService.traerPorId(id));
    }

    @GetMapping("/producto/{productoId}")
    @Operation(summary = "Obtener inventario por producto", description = "Devuelve el inventario de un producto específico")
    public ResponseEntity<Inventario> obtenerPorProductoId(@PathVariable Long productoId) {
        try {
            return ResponseEntity.ok(inventarioService.traerPorProductoId(productoId));
        } catch (com.ampuero.msvc.inventario.exceptions.ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear nuevo inventario", description = "Crea un nuevo registro de inventario para un producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Inventario creado exitosamente"),
            @ApiResponse(responseCode = "409", description = "Ya existe inventario para este producto")
    })
    public ResponseEntity<Inventario> crearInventario(@Valid @RequestBody InventarioCreationDTO inventarioDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(inventarioService.crearInventario(inventarioDTO));
    }

    @PutMapping("/{id}/stock")
    @Operation(summary = "Actualizar stock", description = "Actualiza la cantidad disponible en inventario")
    public ResponseEntity<Inventario> actualizarStock(@PathVariable Long id, @RequestParam Integer cantidad) {
        return ResponseEntity.ok(inventarioService.actualizarStock(id, cantidad));
    }

    @PostMapping("/{productoId}/reservar")
    @Operation(summary = "Reservar stock", description = "Reserva una cantidad específica de stock")
    public ResponseEntity<Inventario> reservarStock(@PathVariable Long productoId, @RequestParam Integer cantidad) {
        return ResponseEntity.ok(inventarioService.reservarStock(productoId, cantidad));
    }

    @PostMapping("/{productoId}/liberar")
    @Operation(summary = "Liberar reserva", description = "Libera stock previamente reservado")
    public ResponseEntity<Inventario> liberarReserva(@PathVariable Long productoId, @RequestParam Integer cantidad) {
        return ResponseEntity.ok(inventarioService.liberarReserva(productoId, cantidad));
    }

    @GetMapping("/stock-critico")
    @Operation(summary = "Obtener productos con stock crítico", description = "Lista productos que están por debajo del stock crítico")
    public ResponseEntity<List<Inventario>> obtenerStockCritico() {
        return ResponseEntity.ok(inventarioService.obtenerStockCritico());
    }

    @GetMapping("/agotados")
    @Operation(summary = "Obtener productos agotados", description = "Lista productos sin stock disponible")
    public ResponseEntity<List<Inventario>> obtenerProductosAgotados() {
        return ResponseEntity.ok(inventarioService.obtenerProductosAgotados());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar inventario", description = "Desactiva un registro de inventario")
    @ApiResponse(responseCode = "204", description = "Inventario eliminado exitosamente")
    public ResponseEntity<Void> eliminarInventario(@PathVariable Long id) {
        inventarioService.eliminarInventario(id);
        return ResponseEntity.noContent().build();
    }
}