package com.ampuero.msvc.promociones.repositories;

import com.ampuero.msvc.promociones.models.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PromocionRepository extends JpaRepository<Promocion, Long> {
    
    Optional<Promocion> findByCodigoPromocion(String codigoPromocion);
    
    boolean existsByCodigoPromocion(String codigoPromocion);
    
    @Query("SELECT p FROM Promocion p WHERE p.activo = true AND p.fechaInicio <= :fecha AND p.fechaFin >= :fecha")
    List<Promocion> findPromocionesActivas(@Param("fecha") LocalDateTime fecha);
    
    @Query("SELECT p FROM Promocion p WHERE p.activo = true AND p.aplicableDuoc = true AND p.fechaInicio <= :fecha AND p.fechaFin >= :fecha")
    List<Promocion> findPromocionesDuocActivas(@Param("fecha") LocalDateTime fecha);
    
    @Query("SELECT p FROM Promocion p WHERE p.activo = true AND p.categoriaAplicable = :categoria AND p.fechaInicio <= :fecha AND p.fechaFin >= :fecha")
    List<Promocion> findPromocionesPorCategoria(@Param("categoria") String categoria, @Param("fecha") LocalDateTime fecha);
    
    List<Promocion> findByActivoTrue();
}
