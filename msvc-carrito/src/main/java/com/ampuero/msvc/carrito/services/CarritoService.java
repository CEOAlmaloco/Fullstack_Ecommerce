package com.ampuero.msvc.carrito.services;

import com.ampuero.msvc.carrito.dtos.CarritoCreationDTO;
import com.ampuero.msvc.carrito.dtos.CarritoEstadoDTO;
import com.ampuero.msvc.carrito.dtos.ItemCarritoCreationDTO;
import com.ampuero.msvc.carrito.models.Carrito;
import com.ampuero.msvc.carrito.models.ItemCarrito;

import java.time.LocalDateTime;
import java.util.List;

public interface CarritoService {
    // Gestión de Carritos
    List<Carrito> traerTodosCarritos();
    List<Carrito> traerCarritosPorUsuario(Long idUsuario);
    List<Carrito> traerCarritosPorEstado(String estado);
    List<Carrito> traerCarritosExpirados();
    List<Carrito> traerCarritosPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    List<Carrito> traerCarritosPorCodigoPromocional(String codigoPromocional);
    List<Carrito> traerCarritosPorPromocion(Long idPromocion);
    Carrito traerCarritoPorId(Long id);
    Carrito traerCarritoActivoPorUsuario(Long idUsuario);
    Carrito crearCarrito(CarritoCreationDTO carritoDetails);
    void eliminarCarrito(Long id);
    Carrito actualizarCarrito(Long id, Carrito carrito);
    Carrito actualizarEstadoCarrito(Long id, CarritoEstadoDTO estadoDetails);
    Carrito limpiarCarrito(Long id);
    Carrito aplicarPromocion(Long id, String codigoPromocional);
    Carrito removerPromocion(Long id);
    Carrito calcularTotales(Long id);
    void procesarCarritosExpirados();
    
    // Gestión de Items
    List<ItemCarrito> traerTodosItems();
    List<ItemCarrito> traerItemsPorCarrito(Long idCarrito);
    List<ItemCarrito> traerItemsPorEstado(String estado);
    List<ItemCarrito> traerItemsPorProducto(Long idProducto);
    ItemCarrito traerItemPorId(Long id);
    ItemCarrito agregarItem(ItemCarritoCreationDTO itemDetails);
    void eliminarItem(Long id);
    ItemCarrito actualizarItem(Long id, ItemCarrito item);
    ItemCarrito actualizarCantidadItem(Long id, Integer nuevaCantidad);
    ItemCarrito removerItem(Long id);
    Long contarItemsEnCarrito(Long idCarrito);
    Long sumarCantidadItemsEnCarrito(Long idCarrito);
    
    // Operaciones especiales
    Carrito convertirCarritoAPedido(Long idCarrito, Long idUsuario);
    Carrito duplicarCarrito(Long idCarrito, Long nuevoUsuario);
    Carrito guardarCarritoParaDespues(Long idCarrito);
    Carrito recuperarCarritoGuardado(Long idCarrito);
}
