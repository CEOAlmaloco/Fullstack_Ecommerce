package com.ampuero.msvc.carrito.services;

import com.ampuero.msvc.carrito.dtos.CarritoCreationDTO;
import com.ampuero.msvc.carrito.dtos.CarritoEstadoDTO;
import com.ampuero.msvc.carrito.dtos.ItemCarritoCreationDTO;
import com.ampuero.msvc.carrito.exceptions.CarritoException;
import com.ampuero.msvc.carrito.models.Carrito;
import com.ampuero.msvc.carrito.models.ItemCarrito;
import com.ampuero.msvc.carrito.repositories.CarritoRepository;
import com.ampuero.msvc.carrito.repositories.ItemCarritoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio de gestión de carritos.
 * <p>
 * Esta clase maneja todas las operaciones CRUD relacionados con carritos e items.
 *
 * @author Level-Up Gamer Team
 * @version 1.0
 */
@Service
public class CarritoServiceImpl implements CarritoService {
    private static final Logger log = LoggerFactory.getLogger(CarritoServiceImpl.class);
    
    @Autowired
    private CarritoRepository carritoRepository;
    
    @Autowired
    private ItemCarritoRepository itemCarritoRepository;

    // ========== GESTIÓN DE CARRITOS ==========

    /**
     * Crea un nuevo carrito en el sistema
     */
    @Transactional
    @Override
    public Carrito crearCarrito(CarritoCreationDTO carritoDetails) {
        // Verificar si el usuario ya tiene un carrito activo
        Optional<Carrito> carritoExistente = carritoRepository.findCarritoActivoPorUsuario(carritoDetails.getIdUsuario());
        if (carritoExistente.isPresent()) {
            throw new CarritoException("El usuario ya tiene un carrito activo");
        }

        Carrito carritoEntity = new Carrito();
        carritoEntity.setIdUsuario(carritoDetails.getIdUsuario());
        carritoEntity.setFechaCreacion(LocalDateTime.now());
        carritoEntity.setFechaActualizacion(LocalDateTime.now());
        carritoEntity.setTotalCarrito(0.0);
        carritoEntity.setTotalDescuentos(0.0);
        carritoEntity.setTotalImpuestos(0.0);
        carritoEntity.setTotalFinal(0.0);
        carritoEntity.setEstadoCarrito("ACTIVO");
        carritoEntity.setFechaExpiracion(LocalDateTime.now().plusDays(7)); // Expira en 7 días
        carritoEntity.setCodigoPromocional(carritoDetails.getCodigoPromocional());
        carritoEntity.setNotasCarrito(carritoDetails.getNotasCarrito());

        return carritoRepository.save(carritoEntity);
    }

    /**
     * Obtiene todos los carritos
     */
    @Transactional(readOnly = true)
    @Override
    public List<Carrito> traerTodosCarritos() {
        List<Carrito> carritos = carritoRepository.findAll();
        if (carritos.isEmpty()) {
            throw new CarritoException("No hay carritos registrados");
        }
        return carritos;
    }

    /**
     * Obtiene carritos por usuario
     */
    @Transactional(readOnly = true)
    @Override
    public List<Carrito> traerCarritosPorUsuario(Long idUsuario) {
        return carritoRepository.findCarritosPorUsuario(idUsuario);
    }

    /**
     * Obtiene carrito activo por usuario
     */
    @Transactional(readOnly = true)
    @Override
    public Carrito traerCarritoActivoPorUsuario(Long idUsuario) {
        return carritoRepository.findCarritoActivoPorUsuario(idUsuario).orElseThrow(
                () -> new CarritoException("No se encontró carrito activo para el usuario " + idUsuario)
        );
    }

    /**
     * Obtiene carritos por estado
     */
    @Transactional(readOnly = true)
    @Override
    public List<Carrito> traerCarritosPorEstado(String estado) {
        return carritoRepository.findCarritosPorEstado(estado);
    }

    /**
     * Obtiene carritos expirados
     */
    @Transactional(readOnly = true)
    @Override
    public List<Carrito> traerCarritosExpirados() {
        return carritoRepository.findCarritosExpirados(LocalDateTime.now());
    }

    /**
     * Obtiene carritos por rango de fechas
     */
    @Transactional(readOnly = true)
    @Override
    public List<Carrito> traerCarritosPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return carritoRepository.findCarritosPorRangoFechas(fechaInicio, fechaFin);
    }

    /**
     * Obtiene carritos por código promocional
     */
    @Transactional(readOnly = true)
    @Override
    public List<Carrito> traerCarritosPorCodigoPromocional(String codigoPromocional) {
        return carritoRepository.findCarritosPorCodigoPromocional(codigoPromocional);
    }

    /**
     * Obtiene carritos por promoción
     */
    @Transactional(readOnly = true)
    @Override
    public List<Carrito> traerCarritosPorPromocion(Long idPromocion) {
        return carritoRepository.findCarritosPorPromocion(idPromocion);
    }

    /**
     * Obtiene un carrito por ID
     */
    @Transactional(readOnly = true)
    @Override
    public Carrito traerCarritoPorId(Long id) {
        return carritoRepository.findById(id).orElseThrow(
                () -> new CarritoException("Carrito con id " + id + " no encontrado")
        );
    }

    /**
     * Actualiza un carrito
     */
    @Transactional
    @Override
    public Carrito actualizarCarrito(Long id, Carrito carritoDetails) {
        return carritoRepository.findById(id).map(carrito -> {
            carrito.setNotasCarrito(carritoDetails.getNotasCarrito());
            carrito.setFechaActualizacion(LocalDateTime.now());
            return carritoRepository.save(carrito);
        }).orElseThrow(() -> new CarritoException("Carrito con id " + id + " no encontrado"));
    }

    /**
     * Actualiza el estado de un carrito
     */
    @Transactional
    @Override
    public Carrito actualizarEstadoCarrito(Long id, CarritoEstadoDTO estadoDetails) {
        return carritoRepository.findById(id).map(carrito -> {
            carrito.setEstadoCarrito(estadoDetails.getEstadoCarrito());
            carrito.setFechaActualizacion(LocalDateTime.now());
            log.info("Estado actualizado: {} {}", estadoDetails.getEstadoCarrito(), carrito);
            return carritoRepository.save(carrito);
        }).orElseThrow(() -> new CarritoException("Carrito con id " + id + " no encontrado"));
    }

    /**
     * Limpia un carrito (elimina todos los items)
     */
    @Transactional
    @Override
    public Carrito limpiarCarrito(Long id) {
        Carrito carrito = traerCarritoPorId(id);
        List<ItemCarrito> items = traerItemsPorCarrito(id);
        
        for (ItemCarrito item : items) {
            item.setActivo(false);
            item.setEstadoItem("ELIMINADO");
            itemCarritoRepository.save(item);
        }
        
        carrito.setTotalCarrito(0.0);
        carrito.setTotalDescuentos(0.0);
        carrito.setTotalImpuestos(0.0);
        carrito.setTotalFinal(0.0);
        carrito.setFechaActualizacion(LocalDateTime.now());
        
        return carritoRepository.save(carrito);
    }

    /**
     * Aplica una promoción al carrito
     */
    @Transactional
    @Override
    public Carrito aplicarPromocion(Long id, String codigoPromocional) {
        Carrito carrito = traerCarritoPorId(id);
        carrito.setCodigoPromocional(codigoPromocional);
        carrito.setFechaActualizacion(LocalDateTime.now());
        
        // Aquí se calcularían los descuentos según la promoción
        // Por ahora solo actualizamos el código
        return carritoRepository.save(carrito);
    }

    /**
     * Remueve la promoción del carrito
     */
    @Transactional
    @Override
    public Carrito removerPromocion(Long id) {
        Carrito carrito = traerCarritoPorId(id);
        carrito.setCodigoPromocional(null);
        carrito.setIdPromocionAplicada(null);
        carrito.setTotalDescuentos(0.0);
        carrito.setFechaActualizacion(LocalDateTime.now());
        
        // Recalcular totales sin promoción
        return calcularTotales(id);
    }

    /**
     * Calcula los totales del carrito
     */
    @Transactional
    @Override
    public Carrito calcularTotales(Long id) {
        Carrito carrito = traerCarritoPorId(id);
        List<ItemCarrito> items = traerItemsPorCarrito(id);
        
        double totalCarrito = 0.0;
        double totalDescuentos = 0.0;
        double totalImpuestos = 0.0;
        
        for (ItemCarrito item : items) {
            if (item.getActivo() && "ACTIVO".equals(item.getEstadoItem())) {
                totalCarrito += item.getSubtotal();
                totalDescuentos += item.getDescuentoAplicado();
                totalImpuestos += item.getImpuestoAplicado();
            }
        }
        
        carrito.setTotalCarrito(totalCarrito);
        carrito.setTotalDescuentos(totalDescuentos);
        carrito.setTotalImpuestos(totalImpuestos);
        carrito.setTotalFinal(totalCarrito - totalDescuentos + totalImpuestos);
        carrito.setFechaActualizacion(LocalDateTime.now());
        
        return carritoRepository.save(carrito);
    }

    /**
     * Procesa carritos expirados
     */
    @Transactional
    @Scheduled(fixedRate = 3600000) // Cada hora
    @Override
    public void procesarCarritosExpirados() {
        List<Carrito> carritosExpirados = traerCarritosExpirados();
        
        for (Carrito carrito : carritosExpirados) {
            try {
                carrito.setEstadoCarrito("EXPIRADO");
                carrito.setFechaActualizacion(LocalDateTime.now());
                carritoRepository.save(carrito);
                log.info("Carrito expirado procesado: {}", carrito.getIdCarrito());
            } catch (Exception e) {
                log.error("Error procesando carrito expirado {}: {}", carrito.getIdCarrito(), e.getMessage());
            }
        }
    }

    /**
     * Elimina un carrito
     */
    @Transactional
    @Override
    public void eliminarCarrito(Long id) {
        Optional<Carrito> carritoOptional = carritoRepository.findById(id);
        if (carritoOptional.isEmpty()) {
            throw new CarritoException("No se pudo eliminar: Carrito con id " + id + " no encontrado");
        }
        carritoRepository.deleteById(id);
    }

    // ========== GESTIÓN DE ITEMS ==========

    /**
     * Agrega un item al carrito
     */
    @Transactional
    @Override
    public ItemCarrito agregarItem(ItemCarritoCreationDTO itemDetails) {
        Carrito carrito = traerCarritoPorId(itemDetails.getIdCarrito());
        
        // Verificar si el item ya existe en el carrito
        Optional<ItemCarrito> itemExistente = itemCarritoRepository.findItemPorCarritoYProducto(
            itemDetails.getIdCarrito(), itemDetails.getIdProducto());
        
        if (itemExistente.isPresent()) {
            // Si existe, actualizar la cantidad
            ItemCarrito item = itemExistente.get();
            item.setCantidad(item.getCantidad() + itemDetails.getCantidad());
            item.setFechaActualizado(LocalDateTime.now());
            return itemCarritoRepository.save(item);
        } else {
            // Si no existe, crear nuevo item
            ItemCarrito itemEntity = new ItemCarrito();
            itemEntity.setCarrito(carrito);
            itemEntity.setIdProducto(itemDetails.getIdProducto());
            itemEntity.setCantidad(itemDetails.getCantidad());
            itemEntity.setFechaAgregado(LocalDateTime.now());
            itemEntity.setFechaActualizado(LocalDateTime.now());
            itemEntity.setEstadoItem("ACTIVO");
            itemEntity.setNotasItem(itemDetails.getNotasItem());
            
            // Aquí se obtendría la información del producto desde msvc-productos
            // Por ahora usamos valores por defecto
            itemEntity.setNombreProducto("Producto " + itemDetails.getIdProducto());
            itemEntity.setPrecioUnitario(10000.0); // Precio por defecto
            itemEntity.setSubtotal(itemEntity.getPrecioUnitario() * itemEntity.getCantidad());
            itemEntity.setTotalItem(itemEntity.getSubtotal());
            
            ItemCarrito itemGuardado = itemCarritoRepository.save(itemEntity);
            
            // Recalcular totales del carrito
            calcularTotales(itemDetails.getIdCarrito());
            
            return itemGuardado;
        }
    }

    /**
     * Obtiene todos los items
     */
    @Transactional(readOnly = true)
    @Override
    public List<ItemCarrito> traerTodosItems() {
        return itemCarritoRepository.findAll();
    }

    /**
     * Obtiene items por carrito
     */
    @Transactional(readOnly = true)
    @Override
    public List<ItemCarrito> traerItemsPorCarrito(Long idCarrito) {
        return itemCarritoRepository.findItemsPorCarrito(idCarrito);
    }

    /**
     * Obtiene items por estado
     */
    @Transactional(readOnly = true)
    @Override
    public List<ItemCarrito> traerItemsPorEstado(String estado) {
        return itemCarritoRepository.findItemsPorEstado(estado);
    }

    /**
     * Obtiene items por producto
     */
    @Transactional(readOnly = true)
    @Override
    public List<ItemCarrito> traerItemsPorProducto(Long idProducto) {
        return itemCarritoRepository.findItemsPorProducto(idProducto);
    }

    /**
     * Obtiene un item por ID
     */
    @Transactional(readOnly = true)
    @Override
    public ItemCarrito traerItemPorId(Long id) {
        return itemCarritoRepository.findById(id).orElseThrow(
                () -> new CarritoException("Item con id " + id + " no encontrado")
        );
    }

    /**
     * Actualiza un item
     */
    @Transactional
    @Override
    public ItemCarrito actualizarItem(Long id, ItemCarrito itemDetails) {
        return itemCarritoRepository.findById(id).map(item -> {
            item.setCantidad(itemDetails.getCantidad());
            item.setNotasItem(itemDetails.getNotasItem());
            item.setFechaActualizado(LocalDateTime.now());
            item.setSubtotal(item.getPrecioUnitario() * item.getCantidad());
            item.setTotalItem(item.getSubtotal());
            return itemCarritoRepository.save(item);
        }).orElseThrow(() -> new CarritoException("Item con id " + id + " no encontrado"));
    }

    /**
     * Actualiza la cantidad de un item
     */
    @Transactional
    @Override
    public ItemCarrito actualizarCantidadItem(Long id, Integer nuevaCantidad) {
        ItemCarrito item = traerItemPorId(id);
        item.setCantidad(nuevaCantidad);
        item.setSubtotal(item.getPrecioUnitario() * nuevaCantidad);
        item.setTotalItem(item.getSubtotal());
        item.setFechaActualizado(LocalDateTime.now());
        
        ItemCarrito itemActualizado = itemCarritoRepository.save(item);
        
        // Recalcular totales del carrito
        calcularTotales(item.getCarrito().getIdCarrito());
        
        return itemActualizado;
    }

    /**
     * Remueve un item del carrito
     */
    @Transactional
    @Override
    public ItemCarrito removerItem(Long id) {
        ItemCarrito item = traerItemPorId(id);
        item.setActivo(false);
        item.setEstadoItem("ELIMINADO");
        item.setFechaActualizado(LocalDateTime.now());
        
        ItemCarrito itemRemovido = itemCarritoRepository.save(item);
        
        // Recalcular totales del carrito
        calcularTotales(item.getCarrito().getIdCarrito());
        
        return itemRemovido;
    }

    /**
     * Elimina un item
     */
    @Transactional
    @Override
    public void eliminarItem(Long id) {
        Optional<ItemCarrito> itemOptional = itemCarritoRepository.findById(id);
        if (itemOptional.isEmpty()) {
            throw new CarritoException("No se pudo eliminar: Item con id " + id + " no encontrado");
        }
        itemCarritoRepository.deleteById(id);
    }

    /**
     * Cuenta items en carrito
     */
    @Transactional(readOnly = true)
    @Override
    public Long contarItemsEnCarrito(Long idCarrito) {
        return itemCarritoRepository.contarItemsEnCarrito(idCarrito);
    }

    /**
     * Suma cantidad de items en carrito
     */
    @Transactional(readOnly = true)
    @Override
    public Long sumarCantidadItemsEnCarrito(Long idCarrito) {
        return itemCarritoRepository.sumarCantidadItemsEnCarrito(idCarrito);
    }

    // ========== OPERACIONES ESPECIALES ==========

    /**
     * Convierte carrito a pedido
     */
    @Transactional
    @Override
    public Carrito convertirCarritoAPedido(Long idCarrito, Long idUsuario) {
        Carrito carrito = traerCarritoPorId(idCarrito);
        
        if (!carrito.getIdUsuario().equals(idUsuario)) {
            throw new CarritoException("El carrito no pertenece al usuario especificado");
        }
        
        if (!"ACTIVO".equals(carrito.getEstadoCarrito())) {
            throw new CarritoException("Solo se pueden convertir carritos activos");
        }
        
        carrito.setEstadoCarrito("CONVERTIDO");
        carrito.setFechaActualizacion(LocalDateTime.now());
        
        return carritoRepository.save(carrito);
    }

    /**
     * Duplica un carrito para otro usuario
     */
    @Transactional
    @Override
    public Carrito duplicarCarrito(Long idCarrito, Long nuevoUsuario) {
        Carrito carritoOriginal = traerCarritoPorId(idCarrito);
        List<ItemCarrito> itemsOriginales = traerItemsPorCarrito(idCarrito);
        
        // Crear nuevo carrito
        Carrito nuevoCarrito = new Carrito();
        nuevoCarrito.setIdUsuario(nuevoUsuario);
        nuevoCarrito.setFechaCreacion(LocalDateTime.now());
        nuevoCarrito.setFechaActualizacion(LocalDateTime.now());
        nuevoCarrito.setTotalCarrito(carritoOriginal.getTotalCarrito());
        nuevoCarrito.setTotalDescuentos(carritoOriginal.getTotalDescuentos());
        nuevoCarrito.setTotalImpuestos(carritoOriginal.getTotalImpuestos());
        nuevoCarrito.setTotalFinal(carritoOriginal.getTotalFinal());
        nuevoCarrito.setEstadoCarrito("ACTIVO");
        nuevoCarrito.setFechaExpiracion(LocalDateTime.now().plusDays(7));
        nuevoCarrito.setNotasCarrito("Carrito duplicado de " + carritoOriginal.getIdUsuario());
        
        Carrito carritoGuardado = carritoRepository.save(nuevoCarrito);
        
        // Duplicar items
        for (ItemCarrito itemOriginal : itemsOriginales) {
            if (itemOriginal.getActivo() && "ACTIVO".equals(itemOriginal.getEstadoItem())) {
                ItemCarrito nuevoItem = new ItemCarrito();
                nuevoItem.setCarrito(carritoGuardado);
                nuevoItem.setIdProducto(itemOriginal.getIdProducto());
                nuevoItem.setNombreProducto(itemOriginal.getNombreProducto());
                nuevoItem.setDescripcionProducto(itemOriginal.getDescripcionProducto());
                nuevoItem.setPrecioUnitario(itemOriginal.getPrecioUnitario());
                nuevoItem.setCantidad(itemOriginal.getCantidad());
                nuevoItem.setSubtotal(itemOriginal.getSubtotal());
                nuevoItem.setDescuentoAplicado(itemOriginal.getDescuentoAplicado());
                nuevoItem.setImpuestoAplicado(itemOriginal.getImpuestoAplicado());
                nuevoItem.setTotalItem(itemOriginal.getTotalItem());
                nuevoItem.setFechaAgregado(LocalDateTime.now());
                nuevoItem.setFechaActualizado(LocalDateTime.now());
                nuevoItem.setEstadoItem("ACTIVO");
                nuevoItem.setNotasItem(itemOriginal.getNotasItem());
                
                itemCarritoRepository.save(nuevoItem);
            }
        }
        
        return carritoGuardado;
    }

    /**
     * Guarda carrito para después
     */
    @Transactional
    @Override
    public Carrito guardarCarritoParaDespues(Long idCarrito) {
        Carrito carrito = traerCarritoPorId(idCarrito);
        carrito.setEstadoCarrito("GUARDADO");
        carrito.setFechaActualizacion(LocalDateTime.now());
        return carritoRepository.save(carrito);
    }

    /**
     * Recupera carrito guardado
     */
    @Transactional
    @Override
    public Carrito recuperarCarritoGuardado(Long idCarrito) {
        Carrito carrito = traerCarritoPorId(idCarrito);
        if (!"GUARDADO".equals(carrito.getEstadoCarrito())) {
            throw new CarritoException("El carrito no está en estado GUARDADO");
        }
        carrito.setEstadoCarrito("ACTIVO");
        carrito.setFechaExpiracion(LocalDateTime.now().plusDays(7));
        carrito.setFechaActualizacion(LocalDateTime.now());
        return carritoRepository.save(carrito);
    }
}
