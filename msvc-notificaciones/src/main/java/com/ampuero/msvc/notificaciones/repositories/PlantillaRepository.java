package com.ampuero.msvc.notificaciones.repositories;

import com.ampuero.msvc.notificaciones.models.PlantillaNotificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlantillaRepository extends JpaRepository<PlantillaNotificacion, Long> {
    
    @Query("SELECT p FROM PlantillaNotificacion p WHERE p.activo = true AND p.tipoPlantilla = :tipo ORDER BY p.nombrePlantilla ASC")
    List<PlantillaNotificacion> findPlantillasPorTipo(@Param("tipo") String tipo);
    
    @Query("SELECT p FROM PlantillaNotificacion p WHERE p.activo = true ORDER BY p.nombrePlantilla ASC")
    List<PlantillaNotificacion> findPlantillasActivas();
    
    Optional<PlantillaNotificacion> findByCodigoPlantilla(String codigoPlantilla);
    
    boolean existsByCodigoPlantilla(String codigoPlantilla);
    
    List<PlantillaNotificacion> findByActivoTrue();
}
