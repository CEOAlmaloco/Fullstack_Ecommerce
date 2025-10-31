package com.ampuero.msvc.pedido.services;

import com.ampuero.msvc.pedido.dtos.PedidoCreationDTO;
import com.ampuero.msvc.pedido.dtos.PedidoEstadoDTO;
import com.ampuero.msvc.pedido.models.Pedido;

import java.util.List;

public interface PedidoService {
    Pedido crearPedido(PedidoCreationDTO pedidoDTO);
    Pedido traerPedidoPorId(Long id);
    Pedido traerPedidoPorCodigo(String codigo);
    List<Pedido> traerPedidosPorUsuario(Long idUsuario);
    List<Pedido> traerTodosPedidos();
    Pedido actualizarEstadoPedido(Long id, PedidoEstadoDTO estadoDTO);
    void eliminarPedido(Long id);
}

