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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private static final Logger logger = LoggerFactory.getLogger(PedidoController.class);

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoItemRepository pedidoItemRepository;

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> crearPedido(@Valid @RequestBody PedidoCreationDTO pedidoDTO) {
        try {
            logger.info("Recibida solicitud para crear pedido para usuario: {}", pedidoDTO.getIdUsuario());
            Pedido pedido = pedidoService.crearPedido(pedidoDTO);
            logger.debug("Pedido creado, convirtiendo a DTO. ID: {}", pedido.getId());
            PedidoResponseDTO response = convertirPedidoAResponseDTO(pedido);
            logger.info("Pedido creado exitosamente con código: {}", response.getCodigo());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            logger.error("Error al crear pedido: {}", e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> traerPedidoPorId(@PathVariable Long id) {
        Pedido pedido = pedidoService.traerPedidoPorId(id);
        PedidoResponseDTO response = convertirPedidoAResponseDTO(pedido);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<PedidoResponseDTO> traerPedidoPorCodigo(@PathVariable String codigo) {
        try {
            logger.debug("Buscando pedido con código: {}", codigo);
            Pedido pedido = pedidoService.traerPedidoPorCodigo(codigo);
            PedidoResponseDTO response = convertirPedidoAResponseDTO(pedido);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error al buscar pedido por código {}: {}", codigo, e.getMessage(), e);
            throw e;
        }
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
        try {
            logger.debug("Convirtiendo pedido ID {} a DTO", pedido.getId());
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
            response.setEstado(pedido.getEstado() != null ? pedido.getEstado().name().toLowerCase() : "pendiente");
            response.setFechaCreacion(pedido.getFechaCreacion());
            response.setFechaActualizacion(pedido.getFechaActualizacion());
            response.setIdCarrito(pedido.getIdCarrito());
            response.setIdPago(pedido.getIdPago());

            // Cargar items del pedido
            logger.debug("Cargando items para pedido ID: {}", pedido.getId());
            List<PedidoItem> items = pedidoItemRepository.findByIdPedido(pedido.getId());
            logger.debug("Encontrados {} items para pedido ID: {}", items.size(), pedido.getId());
            response.setItems(items.stream()
                    .map(this::convertirItemAResponseDTO)
                    .collect(Collectors.toList()));

            return response;
        } catch (Exception e) {
            logger.error("Error al convertir pedido a DTO. Pedido ID: {}, Error: {}", pedido.getId(), e.getMessage(), e);
            throw e;
        }
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

