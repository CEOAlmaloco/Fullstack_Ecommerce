package com.ampuero.msvc.eventos.repositories;

import com.ampuero.msvc.eventos.models.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    
    @Query("SELECT e FROM Evento e WHERE e.activo = true AND e.fechaInicio >= :fecha")
    List<Evento> findEventosFuturos(@Param("fecha") LocalDateTime fecha);
    
    @Query("SELECT e FROM Evento e WHERE e.activo = true AND e.fechaInicio <= :fecha AND e.fechaFin >= :fecha")
    List<Evento> findEventosEnCurso(@Param("fecha") LocalDateTime fecha);
    
    @Query("SELECT e FROM Evento e WHERE e.activo = true AND e.tipoEvento = :tipo AND e.fechaInicio >= :fecha")
    List<Evento> findEventosPorTipo(@Param("tipo") String tipo, @Param("fecha") LocalDateTime fecha);
    
    @Query("SELECT e FROM Evento e WHERE e.activo = true AND e.cuposDisponibles > 0 AND e.fechaInicio >= :fecha")
    List<Evento> findEventosConCupos(@Param("fecha") LocalDateTime fecha);
    
    @Query("SELECT e FROM Evento e WHERE e.activo = true AND e.coordenadasLatitud IS NOT NULL AND e.coordenadasLongitud IS NOT NULL")
    List<Evento> findEventosConCoordenadas();
    
    List<Evento> findByActivoTrue();
    
    Optional<Evento> findByNombreEvento(String nombreEvento);
}
