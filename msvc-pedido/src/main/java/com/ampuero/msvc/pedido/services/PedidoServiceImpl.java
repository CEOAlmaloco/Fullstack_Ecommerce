package com.ampuero.msvc.pedido.services;

import com.ampuero.msvc.pedido.dtos.PedidoCreationDTO;
import com.ampuero.msvc.pedido.dtos.PedidoEstadoDTO;
import com.ampuero.msvc.pedido.dtos.PedidoItemCreationDTO;
import com.ampuero.msvc.pedido.exceptions.ResourceNotFoundException;
import com.ampuero.msvc.pedido.models.Pedido;
import com.ampuero.msvc.pedido.models.PedidoItem;
import com.ampuero.msvc.pedido.repositories.PedidoItemRepository;
import com.ampuero.msvc.pedido.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoItemRepository pedidoItemRepository;

    @Override
    @Transactional
    public Pedido crearPedido(PedidoCreationDTO pedidoDTO) {
        Pedido pedido = new Pedido();
        pedido.setIdUsuario(pedidoDTO.getIdUsuario());
        pedido.setNombreEnvio(pedidoDTO.getNombreEnvio());
        pedido.setApellidoEnvio(pedidoDTO.getApellidoEnvio());
        pedido.setEmailEnvio(pedidoDTO.getEmailEnvio());
        pedido.setTelefonoEnvio(pedidoDTO.getTelefonoEnvio());
        pedido.setDireccionEnvio(pedidoDTO.getDireccionEnvio());
        pedido.setDepartamentoEnvio(pedidoDTO.getDepartamentoEnvio());
        pedido.setRegionEnvio(pedidoDTO.getRegionEnvio());
        pedido.setComunaEnvio(pedidoDTO.getComunaEnvio());
        pedido.setIndicadoresEntrega(pedidoDTO.getIndicadoresEntrega());
        pedido.setSubtotal(pedidoDTO.getSubtotal());
        pedido.setDescuento(pedidoDTO.getDescuento());
        pedido.setIva(pedidoDTO.getIva());
        pedido.setTotal(pedidoDTO.getTotal());
        pedido.setEstado(Pedido.EstadoPedido.PENDIENTE);
        pedido.setIdCarrito(pedidoDTO.getIdCarrito());

        // Generar código único
        String codigo = generarCodigoPedido();
        pedido.setCodigo(codigo);

        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        // Guardar items del pedido
        if (pedidoDTO.getItems() != null && !pedidoDTO.getItems().isEmpty()) {
            for (PedidoItemCreationDTO itemDTO : pedidoDTO.getItems()) {
                PedidoItem item = new PedidoItem();
                item.setIdPedido(pedidoGuardado.getId());
                item.setIdProducto(itemDTO.getIdProducto());
                item.setNombreProducto(itemDTO.getNombreProducto());
                item.setPrecio(itemDTO.getPrecio());
                item.setCantidad(itemDTO.getCantidad());
                item.setSubtotal(itemDTO.getSubtotal());
                item.setImagenUrl(itemDTO.getImagenUrl());
                pedidoItemRepository.save(item);
            }
        }

        return pedidoGuardado;
    }

    private String generarCodigoPedido() {
        LocalDateTime ahora = LocalDateTime.now();
        String fecha = ahora.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String hora = ahora.format(DateTimeFormatter.ofPattern("HHmmss"));
        String random = UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        return fecha + "-" + hora + "-" + random;
    }

    @Override
    public Pedido traerPedidoPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con ID: " + id));
    }

    @Override
    public Pedido traerPedidoPorCodigo(String codigo) {
        return pedidoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con código: " + codigo));
    }

    @Override
    public List<Pedido> traerPedidosPorUsuario(Long idUsuario) {
        return pedidoRepository.findByIdUsuario(idUsuario);
    }

    @Override
    public List<Pedido> traerTodosPedidos() {
        return pedidoRepository.findAll();
    }

    @Override
    @Transactional
    public Pedido actualizarEstadoPedido(Long id, PedidoEstadoDTO estadoDTO) {
        Pedido pedido = traerPedidoPorId(id);
        try {
            Pedido.EstadoPedido nuevoEstado = Pedido.EstadoPedido.valueOf(estadoDTO.getEstado().toUpperCase());
            pedido.setEstado(nuevoEstado);
            return pedidoRepository.save(pedido);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Estado inválido: " + estadoDTO.getEstado());
        }
    }

    @Override
    @Transactional
    public void eliminarPedido(Long id) {
        Pedido pedido = traerPedidoPorId(id);
        pedidoRepository.delete(pedido);
    }
}

