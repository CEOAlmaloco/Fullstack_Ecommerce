package com.ampuero.msvc.pagos.services;

import com.ampuero.msvc.pagos.dtos.PagoCreationDTO;
import com.ampuero.msvc.pagos.dtos.PagoEstadoDTO;
import com.ampuero.msvc.pagos.dtos.TransaccionCreationDTO;
import com.ampuero.msvc.pagos.models.Pago;
import com.ampuero.msvc.pagos.models.Transaccion;

import java.time.LocalDateTime;
import java.util.List;

public interface PagoService {
    // Gestión de Pagos
    List<Pago> traerTodosPagos();
    List<Pago> traerPagosPorPedido(Long idPedido);
    List<Pago> traerPagosPorUsuario(Long idUsuario);
    List<Pago> traerPagosPorEstado(String estado);
    List<Pago> traerPagosPorMetodo(String metodo);
    List<Pago> traerPagosVencidos();
    List<Pago> traerPagosParaReintentar();
    List<Pago> traerPagosPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    Pago traerPagoPorId(Long id);
    Pago traerPagoPorNumeroTransaccion(String numeroTransaccion);
    Pago traerPagoPorCodigoAutorizacion(String codigoAutorizacion);
    Pago crearPago(PagoCreationDTO pagoDetails);
    void eliminarPago(Long id);
    Pago actualizarPago(Long id, Pago pago);
    Pago actualizarEstadoPago(Long id, PagoEstadoDTO estadoDetails);
    Pago procesarPago(Long id);
    Pago aprobarPago(Long id, String codigoAutorizacion);
    Pago rechazarPago(Long id, String motivo);
    Pago cancelarPago(Long id);
    Pago reembolsarPago(Long id, Double montoReembolso);
    Pago reintentarPago(Long id);
    void procesarPagosVencidos();
    
    // Gestión de Transacciones
    List<Transaccion> traerTodasTransacciones();
    List<Transaccion> traerTransaccionesPorPago(Long idPago);
    List<Transaccion> traerTransaccionesPorTipo(String tipo);
    List<Transaccion> traerTransaccionesPorEstado(String estado);
    List<Transaccion> traerTransaccionesPorProveedor(String proveedor);
    List<Transaccion> traerTransaccionesPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    Transaccion traerTransaccionPorId(Long id);
    Transaccion traerTransaccionPorNumeroExterno(String numeroTransaccionExterna);
    Transaccion crearTransaccion(TransaccionCreationDTO transaccionDetails);
    void eliminarTransaccion(Long id);
    Transaccion actualizarTransaccion(Long id, Transaccion transaccion);
    Transaccion procesarTransaccion(Long id);
    Transaccion completarTransaccion(Long id, String codigoRespuesta, String mensajeRespuesta);
    Transaccion fallarTransaccion(Long id, String codigoError, String mensajeError);
    
    // Integración con proveedores de pago
    Pago procesarPagoWebpay(Long idPago, String token);
    Pago procesarPagoPaypal(Long idPago, String paymentId);
    Pago procesarPagoTransferencia(Long idPago, String numeroTransferencia);
    
    // Reportes y estadísticas
    Double obtenerTotalPagosPorPeriodo(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    Long obtenerCantidadPagosPorEstado(String estado);
    Double obtenerComisionesPorPeriodo(LocalDateTime fechaInicio, LocalDateTime fechaFin);
}
