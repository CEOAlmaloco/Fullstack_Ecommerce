package com.ampuero.msvc.resenia.services;

import com.ampuero.msvc.resenia.dtos.ClienteCreationDTO;
import com.ampuero.msvc.resenia.dtos.ClienteEstadoDTO;
import com.ampuero.msvc.resenia.models.Cliente;

import java.util.List;

public interface ClienteService {
    List<Cliente> traerTodos();

    Cliente traerPorId(Long id);

    Cliente crearCliente(ClienteCreationDTO clienteDetails);

    void eliminarCliente(Long id);

    Cliente actualizarCliente(Long id, Cliente cliente);

    Cliente actualizarEstadoCliente(Long id, ClienteEstadoDTO clienteEstadoDetails);
}
