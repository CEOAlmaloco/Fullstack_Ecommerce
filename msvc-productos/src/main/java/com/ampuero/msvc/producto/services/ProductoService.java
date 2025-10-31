package com.ampuero.msvc.producto.services;

import com.ampuero.msvc.producto.models.Producto;

import java.util.List;
import java.util.Map;

public interface ProductoService {

    List<Producto> traerTodo();

    Producto traerPorId(Long id);

    Producto crearProducto(Producto producto);

    Producto actualizarProducto(Long id, Producto producto);

    void eliminarProducto(Long id);

    Map<String, Object> obtenerCategorias();

    List<Producto> buscarPorNombre(String nombre);

    List<Producto> obtenerPorCategoria(String categoria);

    List<Producto> obtenerDisponibles();

    com.ampuero.msvc.producto.dtos.ProductoPaginadoResponseDTO filtrarProductos(com.ampuero.msvc.producto.dtos.ProductoFiltroDTO filtros);
}
