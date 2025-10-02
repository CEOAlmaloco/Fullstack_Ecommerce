package com.ampuero.msvc.producto.services;

import com.ampuero.msvc.producto.exceptions.ProductoException;
import com.ampuero.msvc.producto.models.Producto;
import com.ampuero.msvc.producto.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementación del servicio de gestión de productos.
 * <p>
 * Esta clase proporciona la lógica de negocio para las operaciones CRUD de productos,
 * utilizando el patrón Service Layer con inyección de dependencias de Spring.
 *
 * @author Ampuero Development Team
 * @version 1.0
 * @since 1.0
 */
@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public Producto crearProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public List<Producto> traerTodo() {
        return this.productoRepository.findAll();
    }

    @Override
    public Producto traerPorId(Long id) {
        return this.productoRepository.findById(id)
                .orElseThrow(
                        () -> new ProductoException("El producto con el id " + id + " no existe")
                );
    }

    @Override
    public Producto actualizarProducto(Long id, Producto producto) {
        return productoRepository.findById(id).map(p -> {
            if (producto.getTitulo() != null) p.setTitulo(producto.getTitulo());
            if (producto.getCategoriaId() != null) p.setCategoriaId(producto.getCategoriaId());
            if (producto.getSubcategoriaId() != null) p.setSubcategoriaId(producto.getSubcategoriaId());
            if (producto.getImagen() != null) p.setImagen(producto.getImagen());
            if (producto.getImagenes() != null) p.setImagenes(producto.getImagenes());
            if (producto.getPrecio() != null) p.setPrecio(producto.getPrecio());
            if (producto.getDisponible() != null) p.setDisponible(producto.getDisponible());
            if (producto.getRating() != null) p.setRating(producto.getRating());
            if (producto.getDescripcion() != null) p.setDescripcion(producto.getDescripcion());
            if (producto.getStock() != null) p.setStock(producto.getStock());
            return productoRepository.save(p);
        }).orElseThrow(() -> new ProductoException("El producto con el id " + id + " no existe"));
    }

    @Override
    public void eliminarProducto(Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
        } else {
            throw new ProductoException("El producto con el id " + id + " no existe");
        }
    }

    @Override
    public Map<String, Object> obtenerCategorias() {
        List<Map<String, String>> categorias = Arrays.asList(
                Map.of("id", "CO", "nombre", "Consola"),
                Map.of("id", "PE", "nombre", "Perifericos"),
                Map.of("id", "RO", "nombre", "Ropa"),
                Map.of("id", "EN", "nombre", "Entretenimiento")
        );

        List<Map<String, String>> subcategorias = Arrays.asList(
                Map.of("id", "MA", "nombre", "Mandos", "categoriaId", "CO"),
                Map.of("id", "AC", "nombre", "Accesorios", "categoriaId", "CO"),
                Map.of("id", "HA", "nombre", "Hardware", "categoriaId", "CO"),
                Map.of("id", "TE", "nombre", "Teclados", "categoriaId", "PE"),
                Map.of("id", "MO", "nombre", "Mouses", "categoriaId", "PE"),
                Map.of("id", "AU", "nombre", "Auriculares", "categoriaId", "PE"),
                Map.of("id", "MT", "nombre", "Monitores", "categoriaId", "PE"),
                Map.of("id", "MI", "nombre", "Microfonos", "categoriaId", "PE"),
                Map.of("id", "CW", "nombre", "Camaras web", "categoriaId", "PE"),
                Map.of("id", "MP", "nombre", "Mousepad", "categoriaId", "PE"),
                Map.of("id", "SI", "nombre", "Sillas Gamers", "categoriaId", "PE"),
                Map.of("id", "JM", "nombre", "Juegos de Mesa", "categoriaId", "EN"),
                Map.of("id", "PG", "nombre", "Polerones Gamers Personalizados", "categoriaId", "RO"),
                Map.of("id", "PR", "nombre", "Poleras Personalizadas", "categoriaId", "RO")
        );

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("categorias", categorias);
        resultado.put("subcategorias", subcategorias);

        return resultado;
    }

    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByTituloContainingIgnoreCase(nombre);
    }

    @Override
    public List<Producto> obtenerPorCategoria(String categoria) {
        return productoRepository.findByCategoriaId(categoria);
    }

    @Override
    public List<Producto> obtenerDisponibles() {
        return productoRepository.findByDisponibleTrueAndStockGreaterThan(0);
    }
}
