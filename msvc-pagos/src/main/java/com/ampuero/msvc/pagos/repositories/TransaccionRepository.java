package com.ampuero.msvc.pagos.repositories;

import com.ampuero.msvc.pagos.models.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
    
    @Query("SELECT t FROM Transaccion t WHERE t.activo = true AND t.idPago = :idPago ORDER BY t.fechaTransaccion DESC")
    List<Transaccion> findTransaccionesPorPago(@Param("idPago") Long idPago);
    
    @Query("SELECT t FROM Transaccion t WHERE t.activo = true AND t.tipoTransaccion = :tipo ORDER BY t.fechaTransaccion DESC")
    List<Transaccion> findTransaccionesPorTipo(@Param("tipo") String tipo);
    
    @Query("SELECT t FROM Transaccion t WHERE t.activo = true AND t.estadoTransaccion = :estado ORDER BY t.fechaTransaccion DESC")
    List<Transaccion> findTransaccionesPorEstado(@Param("estado") String estado);
    
    @Query("SELECT t FROM Transaccion t WHERE t.activo = true AND t.proveedorPago = :proveedor ORDER BY t.fechaTransaccion DESC")
    List<Transaccion> findTransaccionesPorProveedor(@Param("proveedor") String proveedor);
    
    @Query("SELECT t FROM Transaccion t WHERE t.activo = true AND t.fechaTransaccion >= :fechaInicio AND t.fechaTransaccion <= :fechaFin ORDER BY t.fechaTransaccion DESC")
    List<Transaccion> findTransaccionesPorRangoFechas(@Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);
    
    @Query("SELECT t FROM Transaccion t WHERE t.activo = true AND t.numeroTransaccionExterna = :numeroTransaccionExterna")
    Optional<Transaccion> findByNumeroTransaccionExterna(@Param("numeroTransaccionExterna") String numeroTransaccionExterna);
    
    List<Transaccion> findByActivoTrue();
}
