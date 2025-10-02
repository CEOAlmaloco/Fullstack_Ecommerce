package com.ampuero.msvc.notificaciones.repositories;

import com.ampuero.msvc.notificaciones.models.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    
    @Query("SELECT n FROM Notificacion n WHERE n.activo = true AND n.idUsuario = :idUsuario ORDER BY n.fechaCreacion DESC")
    List<Notificacion> findNotificacionesPorUsuario(@Param("idUsuario") Long idUsuario);
    
    @Query("SELECT n FROM Notificacion n WHERE n.activo = true AND n.estadoNotificacion = :estado ORDER BY n.fechaCreacion ASC")
    List<Notificacion> findNotificacionesPorEstado(@Param("estado") String estado);
    
    @Query("SELECT n FROM Notificacion n WHERE n.activo = true AND n.tipoNotificacion = :tipo ORDER BY n.fechaCreacion DESC")
    List<Notificacion> findNotificacionesPorTipo(@Param("tipo") String tipo);
    
    @Query("SELECT n FROM Notificacion n WHERE n.activo = true AND n.canalNotificacion = :canal ORDER BY n.fechaCreacion DESC")
    List<Notificacion> findNotificacionesPorCanal(@Param("canal") String canal);
    
    @Query("SELECT n FROM Notificacion n WHERE n.activo = true AND n.estadoNotificacion = 'EN_COLA' AND n.fechaProgramada <= :fecha ORDER BY n.prioridadNotificacion DESC, n.fechaCreacion ASC")
    List<Notificacion> findNotificacionesParaEnviar(@Param("fecha") LocalDateTime fecha);
    
    @Query("SELECT n FROM Notificacion n WHERE n.activo = true AND n.estadoNotificacion = 'FALLIDA' AND n.intentosEnvio < n.maxIntentos ORDER BY n.fechaCreacion ASC")
    List<Notificacion> findNotificacionesParaReintentar();
    
    @Query("SELECT n FROM Notificacion n WHERE n.activo = true AND n.fechaCreacion >= :fechaInicio AND n.fechaCreacion <= :fechaFin ORDER BY n.fechaCreacion DESC")
    List<Notificacion> findNotificacionesPorRangoFechas(@Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);
    
    @Query("SELECT n FROM Notificacion n WHERE n.activo = true AND n.destinatarioNotificacion = :destinatario ORDER BY n.fechaCreacion DESC")
    List<Notificacion> findNotificacionesPorDestinatario(@Param("destinatario") String destinatario);
    
    List<Notificacion> findByActivoTrue();
}
