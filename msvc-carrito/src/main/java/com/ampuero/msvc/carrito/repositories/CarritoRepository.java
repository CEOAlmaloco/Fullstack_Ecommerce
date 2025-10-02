package com.ampuero.msvc.carrito.repositories;

import com.ampuero.msvc.carrito.models.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    
    @Query("SELECT c FROM Carrito c WHERE c.activo = true AND c.idUsuario = :idUsuario AND c.estadoCarrito = 'ACTIVO' ORDER BY c.fechaCreacion DESC")
    Optional<Carrito> findCarritoActivoPorUsuario(@Param("idUsuario") Long idUsuario);
    
    @Query("SELECT c FROM Carrito c WHERE c.activo = true AND c.idUsuario = :idUsuario ORDER BY c.fechaCreacion DESC")
    List<Carrito> findCarritosPorUsuario(@Param("idUsuario") Long idUsuario);
    
    @Query("SELECT c FROM Carrito c WHERE c.activo = true AND c.estadoCarrito = :estado ORDER BY c.fechaCreacion DESC")
    List<Carrito> findCarritosPorEstado(@Param("estado") String estado);
    
    @Query("SELECT c FROM Carrito c WHERE c.activo = true AND c.fechaExpiracion <= :fecha ORDER BY c.fechaCreacion ASC")
    List<Carrito> findCarritosExpirados(@Param("fecha") LocalDateTime fecha);
    
    @Query("SELECT c FROM Carrito c WHERE c.activo = true AND c.fechaCreacion >= :fechaInicio AND c.fechaCreacion <= :fechaFin ORDER BY c.fechaCreacion DESC")
    List<Carrito> findCarritosPorRangoFechas(@Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);
    
    @Query("SELECT c FROM Carrito c WHERE c.activo = true AND c.codigoPromocional = :codigoPromocional ORDER BY c.fechaCreacion DESC")
    List<Carrito> findCarritosPorCodigoPromocional(@Param("codigoPromocional") String codigoPromocional);
    
    @Query("SELECT c FROM Carrito c WHERE c.activo = true AND c.idPromocionAplicada = :idPromocion ORDER BY c.fechaCreacion DESC")
    List<Carrito> findCarritosPorPromocion(@Param("idPromocion") Long idPromocion);
    
    List<Carrito> findByActivoTrue();
}
