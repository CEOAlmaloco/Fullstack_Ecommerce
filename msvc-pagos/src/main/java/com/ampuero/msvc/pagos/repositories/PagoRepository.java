package com.ampuero.msvc.pagos.repositories;

import com.ampuero.msvc.pagos.models.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
    
    @Query("SELECT p FROM Pago p WHERE p.activo = true AND p.idPedido = :idPedido ORDER BY p.fechaPago DESC")
    List<Pago> findPagosPorPedido(@Param("idPedido") Long idPedido);
    
    @Query("SELECT p FROM Pago p WHERE p.activo = true AND p.idUsuario = :idUsuario ORDER BY p.fechaPago DESC")
    List<Pago> findPagosPorUsuario(@Param("idUsuario") Long idUsuario);
    
    @Query("SELECT p FROM Pago p WHERE p.activo = true AND p.estadoPago = :estado ORDER BY p.fechaPago DESC")
    List<Pago> findPagosPorEstado(@Param("estado") String estado);
    
    @Query("SELECT p FROM Pago p WHERE p.activo = true AND p.metodoPago = :metodo ORDER BY p.fechaPago DESC")
    List<Pago> findPagosPorMetodo(@Param("metodo") String metodo);
    
    @Query("SELECT p FROM Pago p WHERE p.activo = true AND p.estadoPago = 'PENDIENTE' AND p.fechaVencimiento <= :fecha ORDER BY p.fechaPago ASC")
    List<Pago> findPagosVencidos(@Param("fecha") LocalDateTime fecha);
    
    @Query("SELECT p FROM Pago p WHERE p.activo = true AND p.estadoPago = 'PENDIENTE' AND p.intentosPago < p.maxIntentos ORDER BY p.fechaPago ASC")
    List<Pago> findPagosParaReintentar();
    
    @Query("SELECT p FROM Pago p WHERE p.activo = true AND p.fechaPago >= :fechaInicio AND p.fechaPago <= :fechaFin ORDER BY p.fechaPago DESC")
    List<Pago> findPagosPorRangoFechas(@Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);
    
    @Query("SELECT p FROM Pago p WHERE p.activo = true AND p.numeroTransaccion = :numeroTransaccion")
    Optional<Pago> findByNumeroTransaccion(@Param("numeroTransaccion") String numeroTransaccion);
    
    @Query("SELECT p FROM Pago p WHERE p.activo = true AND p.codigoAutorizacion = :codigoAutorizacion")
    Optional<Pago> findByCodigoAutorizacion(@Param("codigoAutorizacion") String codigoAutorizacion);
    
    List<Pago> findByActivoTrue();
}
