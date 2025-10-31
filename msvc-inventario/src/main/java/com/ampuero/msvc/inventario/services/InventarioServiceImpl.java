package com.ampuero.msvc.inventario.services;

import com.ampuero.msvc.inventario.dtos.InventarioCreationDTO;
import com.ampuero.msvc.inventario.exceptions.InventarioException;
import com.ampuero.msvc.inventario.exceptions.ResourceNotFoundException;
import com.ampuero.msvc.inventario.models.Inventario;
import com.ampuero.msvc.inventario.repositories.InventarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InventarioServiceImpl implements InventarioService {

    private static final Logger log = LoggerFactory.getLogger(InventarioServiceImpl.class);
    
    @Autowired
    private InventarioRepository inventarioRepository;
    
    @Autowired(required = false)
    private com.ampuero.msvc.inventario.clients.ProductoClientRest productoClientRest;

    @Override
    public List<Inventario> traerTodos() {
        return inventarioRepository.findAll();
    }

    @Override
    public Inventario traerPorId(Long id) {
        return inventarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventario no encontrado con id: " + id));
    }

    @Override
    public Inventario traerPorProductoId(Long productoId) {
        return inventarioRepository.findByProductoId(productoId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventario no encontrado para producto: " + productoId));
    }

    @Transactional
    @Override
    public Inventario crearInventario(InventarioCreationDTO inventarioDetails) {
        Optional<Inventario> existente = inventarioRepository.findByProductoId(inventarioDetails.getProductoId());

        if (existente.isPresent()) {
            throw new InventarioException("Ya existe inventario para el producto: " + inventarioDetails.getProductoId());
        }

        // Verificar que el producto existe en el microservicio de productos
        if (productoClientRest != null) {
            try {
                Object producto = productoClientRest.obtenerProductoPorId(inventarioDetails.getProductoId());
                if (producto == null) {
                    throw new InventarioException("El producto con id " + inventarioDetails.getProductoId() + " no existe");
                }
            } catch (Exception e) {
                log.warn("No se pudo verificar el producto en msvc-productos: " + e.getMessage());
                // Continuar aunque no se pueda verificar (por si el servicio no está disponible)
            }
        }

        Inventario inventario = new Inventario();
        inventario.setProductoId(inventarioDetails.getProductoId());
        inventario.setCantidadDisponible(inventarioDetails.getCantidadDisponible());
        inventario.setCantidadReservada(0);
        inventario.setStockCritico(inventarioDetails.getStockCritico());
        inventario.setUbicacionAlmacen(inventarioDetails.getUbicacionAlmacen());
        inventario.setActivo(true);

        return inventarioRepository.save(inventario);
    }

    @Transactional
    @Override
    public Inventario actualizarStock(Long id, Integer cantidad) {
        Inventario inventario = traerPorId(id);
        inventario.setCantidadDisponible(cantidad);
        return inventarioRepository.save(inventario);
    }

    @Transactional
    @Override
    public Inventario reservarStock(Long productoId, Integer cantidad) {
        Inventario inventario = traerPorProductoId(productoId);

        if (inventario.getCantidadDisponible() < cantidad) {
            throw new InventarioException("Stock insuficiente. Disponible: " + inventario.getCantidadDisponible());
        }

        inventario.setCantidadDisponible(inventario.getCantidadDisponible() - cantidad);
        inventario.setCantidadReservada(inventario.getCantidadReservada() + cantidad);

        return inventarioRepository.save(inventario);
    }

    @Transactional
    @Override
    public Inventario liberarReserva(Long productoId, Integer cantidad) {
        Inventario inventario = traerPorProductoId(productoId);

        if (inventario.getCantidadReservada() < cantidad) {
            throw new InventarioException("No hay suficiente stock reservado para liberar");
        }

        inventario.setCantidadDisponible(inventario.getCantidadDisponible() + cantidad);
        inventario.setCantidadReservada(inventario.getCantidadReservada() - cantidad);

        return inventarioRepository.save(inventario);
    }

    @Override
    public List<Inventario> obtenerStockCritico() {
        return inventarioRepository.findAll().stream()
                .filter(inv -> inv.getCantidadDisponible() <= inv.getStockCritico() && inv.getActivo())
                .toList();
    }

    @Override
    public List<Inventario> obtenerProductosAgotados() {
        return inventarioRepository.findByCantidadDisponible(0).stream()
                .filter(Inventario::getActivo)
                .toList();
    }

    @Transactional
    @Override
    public void eliminarInventario(Long id) {
        Inventario inventario = traerPorId(id);
        inventario.setActivo(false);
        inventarioRepository.save(inventario);
    }
}