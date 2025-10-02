package com.ampuero.msvc.inventario.services;

import com.ampuero.msvc.inventario.dtos.InventarioCreationDTO;
import com.ampuero.msvc.inventario.models.Inventario;

import java.util.List;

public interface InventarioService {
    List<Inventario> traerTodos();

    Inventario traerPorId(Long id);

    Inventario traerPorProductoId(Long productoId);

    Inventario crearInventario(InventarioCreationDTO inventarioDetails);

    Inventario actualizarStock(Long id, Integer cantidad);

    Inventario reservarStock(Long productoId, Integer cantidad);

    Inventario liberarReserva(Long productoId, Integer cantidad);

    List<Inventario> obtenerStockCritico();

    List<Inventario> obtenerProductosAgotados();

    void eliminarInventario(Long id);
}
