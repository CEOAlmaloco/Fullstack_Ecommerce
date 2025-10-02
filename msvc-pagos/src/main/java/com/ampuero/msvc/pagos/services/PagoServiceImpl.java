package com.ampuero.msvc.pagos.services;

import com.ampuero.msvc.pagos.dtos.PagoCreationDTO;
import com.ampuero.msvc.pagos.dtos.PagoEstadoDTO;
import com.ampuero.msvc.pagos.dtos.TransaccionCreationDTO;
import com.ampuero.msvc.pagos.exceptions.PagoException;
import com.ampuero.msvc.pagos.models.Pago;
import com.ampuero.msvc.pagos.models.Transaccion;
import com.ampuero.msvc.pagos.repositories.PagoRepository;
import com.ampuero.msvc.pagos.repositories.TransaccionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementación del servicio de gestión de pagos.
 * <p>
 * Esta clase maneja todas las operaciones CRUD relacionados con pagos y transacciones.
 *
 * @author Level-Up Gamer Team
 * @version 1.0
 */
@Service
public class PagoServiceImpl implements PagoService {
    private static final Logger log = LoggerFactory.getLogger(PagoServiceImpl.class);
    
    @Autowired
    private PagoRepository pagoRepository;
    
    @Autowired
    private TransaccionRepository transaccionRepository;

    // ========== GESTIÓN DE PAGOS ==========

    /**
     * Crea un nuevo pago en el sistema
     */
    @Transactional
    @Override
    public Pago crearPago(PagoCreationDTO pagoDetails) {
        Pago pagoEntity = new Pago();
        pagoEntity.setIdPedido(pagoDetails.getIdPedido());
        pagoEntity.setIdUsuario(pagoDetails.getIdUsuario());
        pagoEntity.setMontoPago(pagoDetails.getMontoPago());
        pagoEntity.setMonedaPago(pagoDetails.getMonedaPago());
        pagoEntity.setMetodoPago(pagoDetails.getMetodoPago());
        pagoEntity.setNumeroTarjetaEnmascarado(pagoDetails.getNumeroTarjetaEnmascarado());
        pagoEntity.setTipoTarjeta(pagoDetails.getTipoTarjeta());
        pagoEntity.setBancoEmisor(pagoDetails.getBancoEmisor());
        pagoEntity.setFechaPago(LocalDateTime.now());
        pagoEntity.setEstadoPago("PENDIENTE");
        pagoEntity.setDatosAdicionales(pagoDetails.getDatosAdicionales());
        pagoEntity.setFechaVencimiento(pagoDetails.getFechaVencimiento() != null ? 
            pagoDetails.getFechaVencimiento() : LocalDateTime.now().plusHours(24));
        
        // Generar número de transacción único
        pagoEntity.setNumeroTransaccion("TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());

        return pagoRepository.save(pagoEntity);
    }

    /**
     * Obtiene todos los pagos
     */
    @Transactional(readOnly = true)
    @Override
    public List<Pago> traerTodosPagos() {
        List<Pago> pagos = pagoRepository.findAll();
        if (pagos.isEmpty()) {
            throw new PagoException("No hay pagos registrados");
        }
        return pagos;
    }

    /**
     * Obtiene pagos por pedido
     */
    @Transactional(readOnly = true)
    @Override
    public List<Pago> traerPagosPorPedido(Long idPedido) {
        return pagoRepository.findPagosPorPedido(idPedido);
    }

    /**
     * Obtiene pagos por usuario
     */
    @Transactional(readOnly = true)
    @Override
    public List<Pago> traerPagosPorUsuario(Long idUsuario) {
        return pagoRepository.findPagosPorUsuario(idUsuario);
    }

    /**
     * Obtiene pagos por estado
     */
    @Transactional(readOnly = true)
    @Override
    public List<Pago> traerPagosPorEstado(String estado) {
        return pagoRepository.findPagosPorEstado(estado);
    }

    /**
     * Obtiene pagos por método
     */
    @Transactional(readOnly = true)
    @Override
    public List<Pago> traerPagosPorMetodo(String metodo) {
        return pagoRepository.findPagosPorMetodo(metodo);
    }

    /**
     * Obtiene pagos vencidos
     */
    @Transactional(readOnly = true)
    @Override
    public List<Pago> traerPagosVencidos() {
        return pagoRepository.findPagosVencidos(LocalDateTime.now());
    }

    /**
     * Obtiene pagos para reintentar
     */
    @Transactional(readOnly = true)
    @Override
    public List<Pago> traerPagosParaReintentar() {
        return pagoRepository.findPagosParaReintentar();
    }

    /**
     * Obtiene pagos por rango de fechas
     */
    @Transactional(readOnly = true)
    @Override
    public List<Pago> traerPagosPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return pagoRepository.findPagosPorRangoFechas(fechaInicio, fechaFin);
    }

    /**
     * Obtiene un pago por ID
     */
    @Transactional(readOnly = true)
    @Override
    public Pago traerPagoPorId(Long id) {
        return pagoRepository.findById(id).orElseThrow(
                () -> new PagoException("Pago con id " + id + " no encontrado")
        );
    }

    /**
     * Obtiene un pago por número de transacción
     */
    @Transactional(readOnly = true)
    @Override
    public Pago traerPagoPorNumeroTransaccion(String numeroTransaccion) {
        return pagoRepository.findByNumeroTransaccion(numeroTransaccion).orElseThrow(
                () -> new PagoException("Pago con número de transacción " + numeroTransaccion + " no encontrado")
        );
    }

    /**
     * Obtiene un pago por código de autorización
     */
    @Transactional(readOnly = true)
    @Override
    public Pago traerPagoPorCodigoAutorizacion(String codigoAutorizacion) {
        return pagoRepository.findByCodigoAutorizacion(codigoAutorizacion).orElseThrow(
                () -> new PagoException("Pago con código de autorización " + codigoAutorizacion + " no encontrado")
        );
    }

    /**
     * Actualiza un pago
     */
    @Transactional
    @Override
    public Pago actualizarPago(Long id, Pago pagoDetails) {
        return pagoRepository.findById(id).map(pago -> {
            pago.setMetodoPago(pagoDetails.getMetodoPago());
            pago.setNumeroTarjetaEnmascarado(pagoDetails.getNumeroTarjetaEnmascarado());
            pago.setTipoTarjeta(pagoDetails.getTipoTarjeta());
            pago.setBancoEmisor(pagoDetails.getBancoEmisor());
            pago.setDatosAdicionales(pagoDetails.getDatosAdicionales());
            return pagoRepository.save(pago);
        }).orElseThrow(() -> new PagoException("Pago con id " + id + " no encontrado"));
    }

    /**
     * Actualiza el estado de un pago
     */
    @Transactional
    @Override
    public Pago actualizarEstadoPago(Long id, PagoEstadoDTO estadoDetails) {
        return pagoRepository.findById(id).map(pago -> {
            pago.setEstadoPago(estadoDetails.getEstadoPago());
            log.info("Estado actualizado: {} {}", estadoDetails.getEstadoPago(), pago);
            return pagoRepository.save(pago);
        }).orElseThrow(() -> new PagoException("Pago con id " + id + " no encontrado"));
    }

    /**
     * Procesa un pago
     */
    @Transactional
    @Override
    public Pago procesarPago(Long id) {
        Pago pago = traerPagoPorId(id);
        
        if (!"PENDIENTE".equals(pago.getEstadoPago())) {
            throw new PagoException("El pago no está en estado PENDIENTE");
        }
        
        try {
            pago.setEstadoPago("PROCESANDO");
            pago.setFechaProcesamiento(LocalDateTime.now());
            pago.setIntentosPago(pago.getIntentosPago() + 1);
            
            // Simular procesamiento según método de pago
            switch (pago.getMetodoPago()) {
                case "TARJETA_CREDITO":
                case "TARJETA_DEBITO":
                    procesarPagoTarjeta(pago);
                    break;
                case "TRANSFERENCIA":
                    procesarPagoTransferencia(pago);
                    break;
                case "PAYPAL":
                    procesarPagoPaypal(pago);
                    break;
                case "WEBPAY":
                    procesarPagoWebpay(pago);
                    break;
                default:
                    throw new PagoException("Método de pago no soportado: " + pago.getMetodoPago());
            }
            
        } catch (Exception e) {
            pago.setEstadoPago("RECHAZADO");
            pago.setMensajeRespuesta(e.getMessage());
            log.error("Error procesando pago: {}", e.getMessage());
        }
        
        return pagoRepository.save(pago);
    }

    /**
     * Aprueba un pago
     */
    @Transactional
    @Override
    public Pago aprobarPago(Long id, String codigoAutorizacion) {
        Pago pago = traerPagoPorId(id);
        pago.setEstadoPago("APROBADO");
        pago.setCodigoAutorizacion(codigoAutorizacion);
        pago.setFechaProcesamiento(LocalDateTime.now());
        pago.setCodigoRespuesta("00");
        pago.setMensajeRespuesta("Pago aprobado exitosamente");
        
        // Calcular comisión (ejemplo: 3% para tarjetas)
        if (pago.getMetodoPago().contains("TARJETA")) {
            pago.setComisionPago(pago.getMontoPago() * 0.03);
            pago.setMontoNeto(pago.getMontoPago() - pago.getComisionPago());
        } else {
            pago.setComisionPago(0.0);
            pago.setMontoNeto(pago.getMontoPago());
        }
        
        return pagoRepository.save(pago);
    }

    /**
     * Rechaza un pago
     */
    @Transactional
    @Override
    public Pago rechazarPago(Long id, String motivo) {
        Pago pago = traerPagoPorId(id);
        pago.setEstadoPago("RECHAZADO");
        pago.setFechaProcesamiento(LocalDateTime.now());
        pago.setCodigoRespuesta("99");
        pago.setMensajeRespuesta(motivo);
        return pagoRepository.save(pago);
    }

    /**
     * Cancela un pago
     */
    @Transactional
    @Override
    public Pago cancelarPago(Long id) {
        Pago pago = traerPagoPorId(id);
        if ("APROBADO".equals(pago.getEstadoPago())) {
            throw new PagoException("No se puede cancelar un pago ya aprobado");
        }
        pago.setEstadoPago("CANCELADO");
        return pagoRepository.save(pago);
    }

    /**
     * Reembolsa un pago
     */
    @Transactional
    @Override
    public Pago reembolsarPago(Long id, Double montoReembolso) {
        Pago pago = traerPagoPorId(id);
        if (!"APROBADO".equals(pago.getEstadoPago())) {
            throw new PagoException("Solo se pueden reembolsar pagos aprobados");
        }
        if (montoReembolso > pago.getMontoPago()) {
            throw new PagoException("El monto de reembolso no puede ser mayor al monto del pago");
        }
        
        pago.setEstadoPago("REEMBOLSADO");
        pago.setMontoPago(pago.getMontoPago() - montoReembolso);
        return pagoRepository.save(pago);
    }

    /**
     * Reintenta un pago
     */
    @Transactional
    @Override
    public Pago reintentarPago(Long id) {
        Pago pago = traerPagoPorId(id);
        
        if (pago.getIntentosPago() >= pago.getMaxIntentos()) {
            throw new PagoException("Máximo número de intentos alcanzado");
        }
        
        return procesarPago(id);
    }

    /**
     * Procesa pagos vencidos
     */
    @Transactional
    @Scheduled(fixedRate = 300000) // Cada 5 minutos
    @Override
    public void procesarPagosVencidos() {
        List<Pago> pagosVencidos = traerPagosVencidos();
        
        for (Pago pago : pagosVencidos) {
            try {
                pago.setEstadoPago("CANCELADO");
                pago.setMensajeRespuesta("Pago cancelado por vencimiento");
                pagoRepository.save(pago);
                log.info("Pago vencido cancelado: {}", pago.getIdPago());
            } catch (Exception e) {
                log.error("Error procesando pago vencido {}: {}", pago.getIdPago(), e.getMessage());
            }
        }
    }

    /**
     * Elimina un pago
     */
    @Transactional
    @Override
    public void eliminarPago(Long id) {
        Optional<Pago> pagoOptional = pagoRepository.findById(id);
        if (pagoOptional.isEmpty()) {
            throw new PagoException("No se pudo eliminar: Pago con id " + id + " no encontrado");
        }
        pagoRepository.deleteById(id);
    }

    // ========== GESTIÓN DE TRANSACCIONES ==========

    /**
     * Crea una nueva transacción
     */
    @Transactional
    @Override
    public Transaccion crearTransaccion(TransaccionCreationDTO transaccionDetails) {
        Transaccion transaccionEntity = new Transaccion();
        transaccionEntity.setIdPago(transaccionDetails.getIdPago());
        transaccionEntity.setTipoTransaccion(transaccionDetails.getTipoTransaccion());
        transaccionEntity.setMontoTransaccion(transaccionDetails.getMontoTransaccion());
        transaccionEntity.setMonedaTransaccion(transaccionDetails.getMonedaTransaccion());
        transaccionEntity.setNumeroTransaccionExterna(transaccionDetails.getNumeroTransaccionExterna());
        transaccionEntity.setFechaTransaccion(LocalDateTime.now());
        transaccionEntity.setEstadoTransaccion("INICIADA");
        transaccionEntity.setProveedorPago(transaccionDetails.getProveedorPago());
        transaccionEntity.setIpCliente(transaccionDetails.getIpCliente());
        transaccionEntity.setUserAgent(transaccionDetails.getUserAgent());

        return transaccionRepository.save(transaccionEntity);
    }

    /**
     * Obtiene todas las transacciones
     */
    @Transactional(readOnly = true)
    @Override
    public List<Transaccion> traerTodasTransacciones() {
        return transaccionRepository.findAll();
    }

    /**
     * Obtiene transacciones por pago
     */
    @Transactional(readOnly = true)
    @Override
    public List<Transaccion> traerTransaccionesPorPago(Long idPago) {
        return transaccionRepository.findTransaccionesPorPago(idPago);
    }

    /**
     * Obtiene transacciones por tipo
     */
    @Transactional(readOnly = true)
    @Override
    public List<Transaccion> traerTransaccionesPorTipo(String tipo) {
        return transaccionRepository.findTransaccionesPorTipo(tipo);
    }

    /**
     * Obtiene transacciones por estado
     */
    @Transactional(readOnly = true)
    @Override
    public List<Transaccion> traerTransaccionesPorEstado(String estado) {
        return transaccionRepository.findTransaccionesPorEstado(estado);
    }

    /**
     * Obtiene transacciones por proveedor
     */
    @Transactional(readOnly = true)
    @Override
    public List<Transaccion> traerTransaccionesPorProveedor(String proveedor) {
        return transaccionRepository.findTransaccionesPorProveedor(proveedor);
    }

    /**
     * Obtiene transacciones por rango de fechas
     */
    @Transactional(readOnly = true)
    @Override
    public List<Transaccion> traerTransaccionesPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return transaccionRepository.findTransaccionesPorRangoFechas(fechaInicio, fechaFin);
    }

    /**
     * Obtiene una transacción por ID
     */
    @Transactional(readOnly = true)
    @Override
    public Transaccion traerTransaccionPorId(Long id) {
        return transaccionRepository.findById(id).orElseThrow(
                () -> new PagoException("Transacción con id " + id + " no encontrada")
        );
    }

    /**
     * Obtiene una transacción por número externo
     */
    @Transactional(readOnly = true)
    @Override
    public Transaccion traerTransaccionPorNumeroExterno(String numeroTransaccionExterna) {
        return transaccionRepository.findByNumeroTransaccionExterna(numeroTransaccionExterna).orElseThrow(
                () -> new PagoException("Transacción con número externo " + numeroTransaccionExterna + " no encontrada")
        );
    }

    /**
     * Actualiza una transacción
     */
    @Transactional
    @Override
    public Transaccion actualizarTransaccion(Long id, Transaccion transaccionDetails) {
        return transaccionRepository.findById(id).map(transaccion -> {
            transaccion.setTipoTransaccion(transaccionDetails.getTipoTransaccion());
            transaccion.setMontoTransaccion(transaccionDetails.getMontoTransaccion());
            transaccion.setProveedorPago(transaccionDetails.getProveedorPago());
            return transaccionRepository.save(transaccion);
        }).orElseThrow(() -> new PagoException("Transacción con id " + id + " no encontrada"));
    }

    /**
     * Procesa una transacción
     */
    @Transactional
    @Override
    public Transaccion procesarTransaccion(Long id) {
        Transaccion transaccion = traerTransaccionPorId(id);
        transaccion.setEstadoTransaccion("PROCESANDO");
        return transaccionRepository.save(transaccion);
    }

    /**
     * Completa una transacción
     */
    @Transactional
    @Override
    public Transaccion completarTransaccion(Long id, String codigoRespuesta, String mensajeRespuesta) {
        Transaccion transaccion = traerTransaccionPorId(id);
        transaccion.setEstadoTransaccion("COMPLETADA");
        transaccion.setCodigoRespuestaExterna(codigoRespuesta);
        transaccion.setMensajeRespuestaExterna(mensajeRespuesta);
        transaccion.setTiempoProcesamiento(System.currentTimeMillis() - transaccion.getFechaTransaccion().atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli());
        return transaccionRepository.save(transaccion);
    }

    /**
     * Falla una transacción
     */
    @Transactional
    @Override
    public Transaccion fallarTransaccion(Long id, String codigoError, String mensajeError) {
        Transaccion transaccion = traerTransaccionPorId(id);
        transaccion.setEstadoTransaccion("FALLIDA");
        transaccion.setCodigoRespuestaExterna(codigoError);
        transaccion.setMensajeRespuestaExterna(mensajeError);
        return transaccionRepository.save(transaccion);
    }

    /**
     * Elimina una transacción
     */
    @Transactional
    @Override
    public void eliminarTransaccion(Long id) {
        Optional<Transaccion> transaccionOptional = transaccionRepository.findById(id);
        if (transaccionOptional.isEmpty()) {
            throw new PagoException("No se pudo eliminar: Transacción con id " + id + " no encontrada");
        }
        transaccionRepository.deleteById(id);
    }

    // ========== INTEGRACIÓN CON PROVEEDORES DE PAGO ==========

    /**
     * Procesa pago con Webpay
     */
    @Transactional
    @Override
    public Pago procesarPagoWebpay(Long idPago, String token) {
        Pago pago = traerPagoPorId(idPago);
        // Aquí iría la integración real con Webpay
        log.info("Procesando pago Webpay para pago {} con token {}", idPago, token);
        return aprobarPago(idPago, "WEBPAY-" + token.substring(0, 8));
    }

    /**
     * Procesa pago con PayPal
     */
    @Transactional
    @Override
    public Pago procesarPagoPaypal(Long idPago, String paymentId) {
        Pago pago = traerPagoPorId(idPago);
        // Aquí iría la integración real con PayPal
        log.info("Procesando pago PayPal para pago {} con paymentId {}", idPago, paymentId);
        return aprobarPago(idPago, "PAYPAL-" + paymentId.substring(0, 8));
    }

    /**
     * Procesa pago por transferencia
     */
    @Transactional
    @Override
    public Pago procesarPagoTransferencia(Long idPago, String numeroTransferencia) {
        Pago pago = traerPagoPorId(idPago);
        // Aquí iría la validación de la transferencia bancaria
        log.info("Procesando pago transferencia para pago {} con número {}", idPago, numeroTransferencia);
        return aprobarPago(idPago, "TRANS-" + numeroTransferencia.substring(0, 8));
    }

    // ========== MÉTODOS PRIVADOS ==========

    private void procesarPagoTarjeta(Pago pago) {
        // Simular validación de tarjeta
        if (pago.getNumeroTarjetaEnmascarado() == null || pago.getNumeroTarjetaEnmascarado().length() < 4) {
            throw new PagoException("Número de tarjeta inválido");
        }
        aprobarPago(pago.getIdPago(), "AUTH-" + UUID.randomUUID().toString().substring(0, 8));
    }

    private void procesarPagoTransferencia(Pago pago) {
        // Simular validación de transferencia
        aprobarPago(pago.getIdPago(), "TRANS-" + UUID.randomUUID().toString().substring(0, 8));
    }

    private void procesarPagoPaypal(Pago pago) {
        // Simular validación de PayPal
        aprobarPago(pago.getIdPago(), "PAYPAL-" + UUID.randomUUID().toString().substring(0, 8));
    }

    private void procesarPagoWebpay(Pago pago) {
        // Simular validación de Webpay
        aprobarPago(pago.getIdPago(), "WEBPAY-" + UUID.randomUUID().toString().substring(0, 8));
    }

    // ========== REPORTES Y ESTADÍSTICAS ==========

    /**
     * Obtiene el total de pagos por período
     */
    @Transactional(readOnly = true)
    @Override
    public Double obtenerTotalPagosPorPeriodo(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        List<Pago> pagos = traerPagosPorRangoFechas(fechaInicio, fechaFin);
        return pagos.stream()
                .filter(pago -> "APROBADO".equals(pago.getEstadoPago()))
                .mapToDouble(Pago::getMontoPago)
                .sum();
    }

    /**
     * Obtiene la cantidad de pagos por estado
     */
    @Transactional(readOnly = true)
    @Override
    public Long obtenerCantidadPagosPorEstado(String estado) {
        return (long) traerPagosPorEstado(estado).size();
    }

    /**
     * Obtiene las comisiones por período
     */
    @Transactional(readOnly = true)
    @Override
    public Double obtenerComisionesPorPeriodo(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        List<Pago> pagos = traerPagosPorRangoFechas(fechaInicio, fechaFin);
        return pagos.stream()
                .filter(pago -> "APROBADO".equals(pago.getEstadoPago()))
                .mapToDouble(pago -> pago.getComisionPago() != null ? pago.getComisionPago() : 0.0)
                .sum();
    }
}
