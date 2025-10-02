package com.ampuero.msvc.inventario.repositories;

import com.ampuero.msvc.inventario.models.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    Optional<Inventario> findByProductoId(Long productoId);

    List<Inventario> findByCantidadDisponibleLessThanEqualAndActivoTrue(Integer stockCritico);

    List<Inventario> findByCantidadDisponibleAndActivoTrue(Integer cantidadDisponible);

    List<Inventario> findByProductoIdIn(List<Long> productosIds);
}
