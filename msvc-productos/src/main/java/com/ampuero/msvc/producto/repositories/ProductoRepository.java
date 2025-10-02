package com.ampuero.msvc.producto.repositories;

import com.ampuero.msvc.producto.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByTituloContainingIgnoreCase(String titulo);

    List<Producto> findByCategoriaId(String categoriaId);

    List<Producto> findByDisponibleTrueAndStockGreaterThan(Integer stock);
}
