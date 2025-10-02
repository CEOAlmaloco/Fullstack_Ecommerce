package com.ampuero.msvc.pedido.services;

import com.ampuero.msvc.pedido.dtos.ClienteCreationDTO;
import com.ampuero.msvc.pedido.dtos.ClienteEstadoDTO;
import com.ampuero.msvc.pedido.models.Cliente;

import java.util.List;

public interface ClienteService {
    List<Cliente> traerTodos();

    Cliente traerPorId(Long id);

    Cliente crearCliente(ClienteCreationDTO clienteDetails);

    void eliminarCliente(Long id);

    Cliente actualizarCliente(Long id, Cliente cliente);

    Cliente actualizarEstadoCliente(Long id, ClienteEstadoDTO clienteEstadoDetails);
}
