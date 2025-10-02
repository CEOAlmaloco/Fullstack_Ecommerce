package com.ampuero.msvc.carrito.repositories;

import com.ampuero.msvc.carrito.models.ItemCarrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemCarritoRepository extends JpaRepository<ItemCarrito, Long> {
    
    @Query("SELECT i FROM ItemCarrito i WHERE i.activo = true AND i.carrito.idCarrito = :idCarrito ORDER BY i.fechaAgregado ASC")
    List<ItemCarrito> findItemsPorCarrito(@Param("idCarrito") Long idCarrito);
    
    @Query("SELECT i FROM ItemCarrito i WHERE i.activo = true AND i.carrito.idCarrito = :idCarrito AND i.idProducto = :idProducto")
    Optional<ItemCarrito> findItemPorCarritoYProducto(@Param("idCarrito") Long idCarrito, @Param("idProducto") Long idProducto);
    
    @Query("SELECT i FROM ItemCarrito i WHERE i.activo = true AND i.estadoItem = :estado ORDER BY i.fechaAgregado DESC")
    List<ItemCarrito> findItemsPorEstado(@Param("estado") String estado);
    
    @Query("SELECT i FROM ItemCarrito i WHERE i.activo = true AND i.idProducto = :idProducto ORDER BY i.fechaAgregado DESC")
    List<ItemCarrito> findItemsPorProducto(@Param("idProducto") Long idProducto);
    
    @Query("SELECT COUNT(i) FROM ItemCarrito i WHERE i.activo = true AND i.carrito.idCarrito = :idCarrito")
    Long contarItemsEnCarrito(@Param("idCarrito") Long idCarrito);
    
    @Query("SELECT SUM(i.cantidad) FROM ItemCarrito i WHERE i.activo = true AND i.carrito.idCarrito = :idCarrito")
    Long sumarCantidadItemsEnCarrito(@Param("idCarrito") Long idCarrito);
    
    List<ItemCarrito> findByActivoTrue();
}
