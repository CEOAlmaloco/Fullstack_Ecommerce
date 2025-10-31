package com.ampuero.msvc.pedido.controllers;

import com.ampuero.msvc.pedido.dtos.PedidoCreationDTO;
import com.ampuero.msvc.pedido.dtos.PedidoEstadoDTO;
import com.ampuero.msvc.pedido.dtos.PedidoItemResponseDTO;
import com.ampuero.msvc.pedido.dtos.PedidoResponseDTO;
import com.ampuero.msvc.pedido.models.Pedido;
import com.ampuero.msvc.pedido.models.PedidoItem;
import com.ampuero.msvc.pedido.repositories.PedidoItemRepository;
import com.ampuero.msvc.pedido.services.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174", "http://localhost:3000"})
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoItemRepository pedidoItemRepository;

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> crearPedido(@Valid @RequestBody PedidoCreationDTO pedidoDTO) {
        Pedido pedido = pedidoService.crearPedido(pedidoDTO);
        PedidoResponseDTO response = convertirPedidoAResponseDTO(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> traerPedidoPorId(@PathVariable Long id) {
        Pedido pedido = pedidoService.traerPedidoPorId(id);
        PedidoResponseDTO response = convertirPedidoAResponseDTO(pedido);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<PedidoResponseDTO> traerPedidoPorCodigo(@PathVariable String codigo) {
        Pedido pedido = pedidoService.traerPedidoPorCodigo(codigo);
        PedidoResponseDTO response = convertirPedidoAResponseDTO(pedido);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<PedidoResponseDTO>> traerPedidosPorUsuario(@PathVariable Long idUsuario) {
        List<Pedido> pedidos = pedidoService.traerPedidosPorUsuario(idUsuario);
        List<PedidoResponseDTO> response = pedidos.stream()
                .map(this::convertirPedidoAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> traerTodosPedidos() {
        List<Pedido> pedidos = pedidoService.traerTodosPedidos();
        List<PedidoResponseDTO> response = pedidos.stream()
                .map(this::convertirPedidoAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<PedidoResponseDTO> actualizarEstadoPedido(
            @PathVariable Long id,
            @Valid @RequestBody PedidoEstadoDTO estadoDTO) {
        Pedido pedido = pedidoService.actualizarEstadoPedido(id, estadoDTO);
        PedidoResponseDTO response = convertirPedidoAResponseDTO(pedido);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Long id) {
        pedidoService.eliminarPedido(id);
        return ResponseEntity.noContent().build();
    }

    private PedidoResponseDTO convertirPedidoAResponseDTO(Pedido pedido) {
        PedidoResponseDTO response = new PedidoResponseDTO();
        response.setId(pedido.getId());
        response.setCodigo(pedido.getCodigo());
        response.setIdUsuario(pedido.getIdUsuario());
        response.setNombreEnvio(pedido.getNombreEnvio());
        response.setApellidoEnvio(pedido.getApellidoEnvio());
        response.setEmailEnvio(pedido.getEmailEnvio());
        response.setTelefonoEnvio(pedido.getTelefonoEnvio());
        response.setDireccionEnvio(pedido.getDireccionEnvio());
        response.setDepartamentoEnvio(pedido.getDepartamentoEnvio());
        response.setRegionEnvio(pedido.getRegionEnvio());
        response.setComunaEnvio(pedido.getComunaEnvio());
        response.setIndicadoresEntrega(pedido.getIndicadoresEntrega());
        response.setSubtotal(pedido.getSubtotal());
        response.setDescuento(pedido.getDescuento());
        response.setIva(pedido.getIva());
        response.setTotal(pedido.getTotal());
        response.setEstado(pedido.getEstado().name().toLowerCase());
        response.setFechaCreacion(pedido.getFechaCreacion());
        response.setFechaActualizacion(pedido.getFechaActualizacion());
        response.setIdCarrito(pedido.getIdCarrito());
        response.setIdPago(pedido.getIdPago());

        // Cargar items del pedido
        List<PedidoItem> items = pedidoItemRepository.findByIdPedido(pedido.getId());
        response.setItems(items.stream()
                .map(this::convertirItemAResponseDTO)
                .collect(Collectors.toList()));

        return response;
    }

    private PedidoItemResponseDTO convertirItemAResponseDTO(PedidoItem item) {
        PedidoItemResponseDTO response = new PedidoItemResponseDTO();
        response.setId(item.getId());
        response.setIdPedido(item.getIdPedido());
        response.setIdProducto(item.getIdProducto());
        response.setNombreProducto(item.getNombreProducto());
        response.setPrecio(item.getPrecio());
        response.setCantidad(item.getCantidad());
        response.setSubtotal(item.getSubtotal());
        response.setImagenUrl(item.getImagenUrl());
        return response;
    }
}

