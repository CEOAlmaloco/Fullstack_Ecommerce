package com.ampuero.msvc.producto.repositories;

import com.ampuero.msvc.producto.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByTituloContainingIgnoreCase(String titulo);

    List<Producto> findByDisponibleTrueAndStockGreaterThan(Integer stock);
    
    // Cargar productos con sus relaciones de categoría y subcategoría
    @Query("SELECT p FROM Producto p LEFT JOIN FETCH p.categoria LEFT JOIN FETCH p.subcategoria")
    List<Producto> findAllWithRelations();
    
    // Buscar productos por ID de categoría usando la relación JPA
    @Query("SELECT p FROM Producto p LEFT JOIN FETCH p.categoria LEFT JOIN FETCH p.subcategoria WHERE p.categoria.id = :categoriaId")
    List<Producto> findByCategoriaId(String categoriaId);
}
