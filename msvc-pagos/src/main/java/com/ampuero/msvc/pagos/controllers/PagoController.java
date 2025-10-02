package com.ampuero.msvc.pagos.controllers;

import com.ampuero.msvc.pagos.dtos.PagoCreationDTO;
import com.ampuero.msvc.pagos.dtos.PagoEstadoDTO;
import com.ampuero.msvc.pagos.dtos.PagoResponseDTO;
import com.ampuero.msvc.pagos.dtos.TransaccionCreationDTO;
import com.ampuero.msvc.pagos.dtos.TransaccionResponseDTO;
import com.ampuero.msvc.pagos.models.Pago;
import com.ampuero.msvc.pagos.models.Transaccion;
import com.ampuero.msvc.pagos.services.PagoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pagos")
@CrossOrigin(origins = "*")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    // ========== ENDPOINTS DE PAGOS ==========

    /**
     * Crear nuevo pago
     * POST: /pagos
     */
    @PostMapping
    public ResponseEntity<PagoResponseDTO> crearPago(@Valid @RequestBody PagoCreationDTO pagoDetails) {
        Pago pago = pagoService.crearPago(pagoDetails);
        PagoResponseDTO response = convertirPagoAResponseDTO(pago);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Obtener todos los pagos
     * GET: /pagos
     */
    @GetMapping
    public ResponseEntity<List<PagoResponseDTO>> traerTodosPagos() {
        List<Pago> pagos = pagoService.traerTodosPagos();
        List<PagoResponseDTO> response = pagos.stream()
                .map(this::convertirPagoAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener pagos por pedido
     * GET: /pagos/pedido/{idPedido}
     */
    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<List<PagoResponseDTO>> traerPagosPorPedido(@PathVariable Long idPedido) {
        List<Pago> pagos = pagoService.traerPagosPorPedido(idPedido);
        List<PagoResponseDTO> response = pagos.stream()
                .map(this::convertirPagoAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener pagos por usuario
     * GET: /pagos/usuario/{idUsuario}
     */
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<PagoResponseDTO>> traerPagosPorUsuario(@PathVariable Long idUsuario) {
        List<Pago> pagos = pagoService.traerPagosPorUsuario(idUsuario);
        List<PagoResponseDTO> response = pagos.stream()
                .map(this::convertirPagoAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener pagos por estado
     * GET: /pagos/estado/{estado}
     */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PagoResponseDTO>> traerPagosPorEstado(@PathVariable String estado) {
        List<Pago> pagos = pagoService.traerPagosPorEstado(estado);
        List<PagoResponseDTO> response = pagos.stream()
                .map(this::convertirPagoAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener pagos por método
     * GET: /pagos/metodo/{metodo}
     */
    @GetMapping("/metodo/{metodo}")
    public ResponseEntity<List<PagoResponseDTO>> traerPagosPorMetodo(@PathVariable String metodo) {
        List<Pago> pagos = pagoService.traerPagosPorMetodo(metodo);
        List<PagoResponseDTO> response = pagos.stream()
                .map(this::convertirPagoAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener pagos vencidos
     * GET: /pagos/vencidos
     */
    @GetMapping("/vencidos")
    public ResponseEntity<List<PagoResponseDTO>> traerPagosVencidos() {
        List<Pago> pagos = pagoService.traerPagosVencidos();
        List<PagoResponseDTO> response = pagos.stream()
                .map(this::convertirPagoAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener pagos para reintentar
     * GET: /pagos/reintentar
     */
    @GetMapping("/reintentar")
    public ResponseEntity<List<PagoResponseDTO>> traerPagosParaReintentar() {
        List<Pago> pagos = pagoService.traerPagosParaReintentar();
        List<PagoResponseDTO> response = pagos.stream()
                .map(this::convertirPagoAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener pagos por rango de fechas
     * GET: /pagos/fechas?fechaInicio=...&fechaFin=...
     */
    @GetMapping("/fechas")
    public ResponseEntity<List<PagoResponseDTO>> traerPagosPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        List<Pago> pagos = pagoService.traerPagosPorRangoFechas(fechaInicio, fechaFin);
        List<PagoResponseDTO> response = pagos.stream()
                .map(this::convertirPagoAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener pago por ID
     * GET: /pagos/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<PagoResponseDTO> traerPagoPorId(@PathVariable Long id) {
        Pago pago = pagoService.traerPagoPorId(id);
        PagoResponseDTO response = convertirPagoAResponseDTO(pago);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener pago por número de transacción
     * GET: /pagos/transaccion/{numeroTransaccion}
     */
    @GetMapping("/transaccion/{numeroTransaccion}")
    public ResponseEntity<PagoResponseDTO> traerPagoPorNumeroTransaccion(@PathVariable String numeroTransaccion) {
        Pago pago = pagoService.traerPagoPorNumeroTransaccion(numeroTransaccion);
        PagoResponseDTO response = convertirPagoAResponseDTO(pago);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener pago por código de autorización
     * GET: /pagos/autorizacion/{codigoAutorizacion}
     */
    @GetMapping("/autorizacion/{codigoAutorizacion}")
    public ResponseEntity<PagoResponseDTO> traerPagoPorCodigoAutorizacion(@PathVariable String codigoAutorizacion) {
        Pago pago = pagoService.traerPagoPorCodigoAutorizacion(codigoAutorizacion);
        PagoResponseDTO response = convertirPagoAResponseDTO(pago);
        return ResponseEntity.ok(response);
    }

    /**
     * Procesar pago
     * POST: /pagos/{id}/procesar
     */
    @PostMapping("/{id}/procesar")
    public ResponseEntity<PagoResponseDTO> procesarPago(@PathVariable Long id) {
        Pago pago = pagoService.procesarPago(id);
        PagoResponseDTO response = convertirPagoAResponseDTO(pago);
        return ResponseEntity.ok(response);
    }

    /**
     * Aprobar pago
     * POST: /pagos/{id}/aprobar
     */
    @PostMapping("/{id}/aprobar")
    public ResponseEntity<PagoResponseDTO> aprobarPago(@PathVariable Long id, @RequestParam String codigoAutorizacion) {
        Pago pago = pagoService.aprobarPago(id, codigoAutorizacion);
        PagoResponseDTO response = convertirPagoAResponseDTO(pago);
        return ResponseEntity.ok(response);
    }

    /**
     * Rechazar pago
     * POST: /pagos/{id}/rechazar
     */
    @PostMapping("/{id}/rechazar")
    public ResponseEntity<PagoResponseDTO> rechazarPago(@PathVariable Long id, @RequestParam String motivo) {
        Pago pago = pagoService.rechazarPago(id, motivo);
        PagoResponseDTO response = convertirPagoAResponseDTO(pago);
        return ResponseEntity.ok(response);
    }

    /**
     * Cancelar pago
     * POST: /pagos/{id}/cancelar
     */
    @PostMapping("/{id}/cancelar")
    public ResponseEntity<PagoResponseDTO> cancelarPago(@PathVariable Long id) {
        Pago pago = pagoService.cancelarPago(id);
        PagoResponseDTO response = convertirPagoAResponseDTO(pago);
        return ResponseEntity.ok(response);
    }

    /**
     * Reembolsar pago
     * POST: /pagos/{id}/reembolsar
     */
    @PostMapping("/{id}/reembolsar")
    public ResponseEntity<PagoResponseDTO> reembolsarPago(@PathVariable Long id, @RequestParam Double montoReembolso) {
        Pago pago = pagoService.reembolsarPago(id, montoReembolso);
        PagoResponseDTO response = convertirPagoAResponseDTO(pago);
        return ResponseEntity.ok(response);
    }

    /**
     * Reintentar pago
     * POST: /pagos/{id}/reintentar
     */
    @PostMapping("/{id}/reintentar")
    public ResponseEntity<PagoResponseDTO> reintentarPago(@PathVariable Long id) {
        Pago pago = pagoService.reintentarPago(id);
        PagoResponseDTO response = convertirPagoAResponseDTO(pago);
        return ResponseEntity.ok(response);
    }

    /**
     * Actualizar pago
     * PUT: /pagos/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<PagoResponseDTO> actualizarPago(@PathVariable Long id, 
                                                         @Valid @RequestBody Pago pagoDetails) {
        Pago pago = pagoService.actualizarPago(id, pagoDetails);
        PagoResponseDTO response = convertirPagoAResponseDTO(pago);
        return ResponseEntity.ok(response);
    }

    /**
     * Actualizar estado de pago
     * PUT: /pagos/{id}/estado
     */
    @PutMapping("/{id}/estado")
    public ResponseEntity<PagoResponseDTO> actualizarEstadoPago(@PathVariable Long id, 
                                                               @Valid @RequestBody PagoEstadoDTO estadoDetails) {
        Pago pago = pagoService.actualizarEstadoPago(id, estadoDetails);
        PagoResponseDTO response = convertirPagoAResponseDTO(pago);
        return ResponseEntity.ok(response);
    }

    /**
     * Eliminar pago
     * DELETE: /pagos/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPago(@PathVariable Long id) {
        pagoService.eliminarPago(id);
        return ResponseEntity.noContent().build();
    }

    // ========== ENDPOINTS DE TRANSACCIONES ==========

    /**
     * Crear nueva transacción
     * POST: /pagos/transacciones
     */
    @PostMapping("/transacciones")
    public ResponseEntity<TransaccionResponseDTO> crearTransaccion(@Valid @RequestBody TransaccionCreationDTO transaccionDetails) {
        Transaccion transaccion = pagoService.crearTransaccion(transaccionDetails);
        TransaccionResponseDTO response = convertirTransaccionAResponseDTO(transaccion);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Obtener todas las transacciones
     * GET: /pagos/transacciones
     */
    @GetMapping("/transacciones")
    public ResponseEntity<List<TransaccionResponseDTO>> traerTodasTransacciones() {
        List<Transaccion> transacciones = pagoService.traerTodasTransacciones();
        List<TransaccionResponseDTO> response = transacciones.stream()
                .map(this::convertirTransaccionAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener transacciones por pago
     * GET: /pagos/transacciones/pago/{idPago}
     */
    @GetMapping("/transacciones/pago/{idPago}")
    public ResponseEntity<List<TransaccionResponseDTO>> traerTransaccionesPorPago(@PathVariable Long idPago) {
        List<Transaccion> transacciones = pagoService.traerTransaccionesPorPago(idPago);
        List<TransaccionResponseDTO> response = transacciones.stream()
                .map(this::convertirTransaccionAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener transacciones por tipo
     * GET: /pagos/transacciones/tipo/{tipo}
     */
    @GetMapping("/transacciones/tipo/{tipo}")
    public ResponseEntity<List<TransaccionResponseDTO>> traerTransaccionesPorTipo(@PathVariable String tipo) {
        List<Transaccion> transacciones = pagoService.traerTransaccionesPorTipo(tipo);
        List<TransaccionResponseDTO> response = transacciones.stream()
                .map(this::convertirTransaccionAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener transacciones por estado
     * GET: /pagos/transacciones/estado/{estado}
     */
    @GetMapping("/transacciones/estado/{estado}")
    public ResponseEntity<List<TransaccionResponseDTO>> traerTransaccionesPorEstado(@PathVariable String estado) {
        List<Transaccion> transacciones = pagoService.traerTransaccionesPorEstado(estado);
        List<TransaccionResponseDTO> response = transacciones.stream()
                .map(this::convertirTransaccionAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener transacciones por proveedor
     * GET: /pagos/transacciones/proveedor/{proveedor}
     */
    @GetMapping("/transacciones/proveedor/{proveedor}")
    public ResponseEntity<List<TransaccionResponseDTO>> traerTransaccionesPorProveedor(@PathVariable String proveedor) {
        List<Transaccion> transacciones = pagoService.traerTransaccionesPorProveedor(proveedor);
        List<TransaccionResponseDTO> response = transacciones.stream()
                .map(this::convertirTransaccionAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener transacciones por rango de fechas
     * GET: /pagos/transacciones/fechas?fechaInicio=...&fechaFin=...
     */
    @GetMapping("/transacciones/fechas")
    public ResponseEntity<List<TransaccionResponseDTO>> traerTransaccionesPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        List<Transaccion> transacciones = pagoService.traerTransaccionesPorRangoFechas(fechaInicio, fechaFin);
        List<TransaccionResponseDTO> response = transacciones.stream()
                .map(this::convertirTransaccionAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener transacción por ID
     * GET: /pagos/transacciones/{id}
     */
    @GetMapping("/transacciones/{id}")
    public ResponseEntity<TransaccionResponseDTO> traerTransaccionPorId(@PathVariable Long id) {
        Transaccion transaccion = pagoService.traerTransaccionPorId(id);
        TransaccionResponseDTO response = convertirTransaccionAResponseDTO(transaccion);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener transacción por número externo
     * GET: /pagos/transacciones/externa/{numeroTransaccionExterna}
     */
    @GetMapping("/transacciones/externa/{numeroTransaccionExterna}")
    public ResponseEntity<TransaccionResponseDTO> traerTransaccionPorNumeroExterno(@PathVariable String numeroTransaccionExterna) {
        Transaccion transaccion = pagoService.traerTransaccionPorNumeroExterno(numeroTransaccionExterna);
        TransaccionResponseDTO response = convertirTransaccionAResponseDTO(transaccion);
        return ResponseEntity.ok(response);
    }

    /**
     * Procesar transacción
     * POST: /pagos/transacciones/{id}/procesar
     */
    @PostMapping("/transacciones/{id}/procesar")
    public ResponseEntity<TransaccionResponseDTO> procesarTransaccion(@PathVariable Long id) {
        Transaccion transaccion = pagoService.procesarTransaccion(id);
        TransaccionResponseDTO response = convertirTransaccionAResponseDTO(transaccion);
        return ResponseEntity.ok(response);
    }

    /**
     * Completar transacción
     * POST: /pagos/transacciones/{id}/completar
     */
    @PostMapping("/transacciones/{id}/completar")
    public ResponseEntity<TransaccionResponseDTO> completarTransaccion(@PathVariable Long id, 
                                                                       @RequestParam String codigoRespuesta,
                                                                       @RequestParam String mensajeRespuesta) {
        Transaccion transaccion = pagoService.completarTransaccion(id, codigoRespuesta, mensajeRespuesta);
        TransaccionResponseDTO response = convertirTransaccionAResponseDTO(transaccion);
        return ResponseEntity.ok(response);
    }

    /**
     * Fallar transacción
     * POST: /pagos/transacciones/{id}/fallar
     */
    @PostMapping("/transacciones/{id}/fallar")
    public ResponseEntity<TransaccionResponseDTO> fallarTransaccion(@PathVariable Long id, 
                                                                   @RequestParam String codigoError,
                                                                   @RequestParam String mensajeError) {
        Transaccion transaccion = pagoService.fallarTransaccion(id, codigoError, mensajeError);
        TransaccionResponseDTO response = convertirTransaccionAResponseDTO(transaccion);
        return ResponseEntity.ok(response);
    }

    /**
     * Actualizar transacción
     * PUT: /pagos/transacciones/{id}
     */
    @PutMapping("/transacciones/{id}")
    public ResponseEntity<TransaccionResponseDTO> actualizarTransaccion(@PathVariable Long id, 
                                                                        @Valid @RequestBody Transaccion transaccionDetails) {
        Transaccion transaccion = pagoService.actualizarTransaccion(id, transaccionDetails);
        TransaccionResponseDTO response = convertirTransaccionAResponseDTO(transaccion);
        return ResponseEntity.ok(response);
    }

    /**
     * Eliminar transacción
     * DELETE: /pagos/transacciones/{id}
     */
    @DeleteMapping("/transacciones/{id}")
    public ResponseEntity<Void> eliminarTransaccion(@PathVariable Long id) {
        pagoService.eliminarTransaccion(id);
        return ResponseEntity.noContent().build();
    }

    // ========== ENDPOINTS DE INTEGRACIÓN CON PROVEEDORES ==========

    /**
     * Procesar pago con Webpay
     * POST: /pagos/webpay/{idPago}
     */
    @PostMapping("/webpay/{idPago}")
    public ResponseEntity<PagoResponseDTO> procesarPagoWebpay(@PathVariable Long idPago, @RequestParam String token) {
        Pago pago = pagoService.procesarPagoWebpay(idPago, token);
        PagoResponseDTO response = convertirPagoAResponseDTO(pago);
        return ResponseEntity.ok(response);
    }

    /**
     * Procesar pago con PayPal
     * POST: /pagos/paypal/{idPago}
     */
    @PostMapping("/paypal/{idPago}")
    public ResponseEntity<PagoResponseDTO> procesarPagoPaypal(@PathVariable Long idPago, @RequestParam String paymentId) {
        Pago pago = pagoService.procesarPagoPaypal(idPago, paymentId);
        PagoResponseDTO response = convertirPagoAResponseDTO(pago);
        return ResponseEntity.ok(response);
    }

    /**
     * Procesar pago por transferencia
     * POST: /pagos/transferencia/{idPago}
     */
    @PostMapping("/transferencia/{idPago}")
    public ResponseEntity<PagoResponseDTO> procesarPagoTransferencia(@PathVariable Long idPago, @RequestParam String numeroTransferencia) {
        Pago pago = pagoService.procesarPagoTransferencia(idPago, numeroTransferencia);
        PagoResponseDTO response = convertirPagoAResponseDTO(pago);
        return ResponseEntity.ok(response);
    }

    // ========== ENDPOINTS DE REPORTES ==========

    /**
     * Obtener total de pagos por período
     * GET: /pagos/reportes/total?fechaInicio=...&fechaFin=...
     */
    @GetMapping("/reportes/total")
    public ResponseEntity<Double> obtenerTotalPagosPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        Double total = pagoService.obtenerTotalPagosPorPeriodo(fechaInicio, fechaFin);
        return ResponseEntity.ok(total);
    }

    /**
     * Obtener cantidad de pagos por estado
     * GET: /pagos/reportes/cantidad/{estado}
     */
    @GetMapping("/reportes/cantidad/{estado}")
    public ResponseEntity<Long> obtenerCantidadPagosPorEstado(@PathVariable String estado) {
        Long cantidad = pagoService.obtenerCantidadPagosPorEstado(estado);
        return ResponseEntity.ok(cantidad);
    }

    /**
     * Obtener comisiones por período
     * GET: /pagos/reportes/comisiones?fechaInicio=...&fechaFin=...
     */
    @GetMapping("/reportes/comisiones")
    public ResponseEntity<Double> obtenerComisionesPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        Double comisiones = pagoService.obtenerComisionesPorPeriodo(fechaInicio, fechaFin);
        return ResponseEntity.ok(comisiones);
    }

    // ========== MÉTODOS DE CONVERSIÓN ==========

    /**
     * Convierte una entidad Pago a PagoResponseDTO
     */
    private PagoResponseDTO convertirPagoAResponseDTO(Pago pago) {
        PagoResponseDTO response = new PagoResponseDTO();
        response.setIdPago(pago.getIdPago());
        response.setIdPedido(pago.getIdPedido());
        response.setIdUsuario(pago.getIdUsuario());
        response.setMontoPago(pago.getMontoPago());
        response.setMonedaPago(pago.getMonedaPago());
        response.setMetodoPago(pago.getMetodoPago());
        response.setNumeroTarjetaEnmascarado(pago.getNumeroTarjetaEnmascarado());
        response.setTipoTarjeta(pago.getTipoTarjeta());
        response.setBancoEmisor(pago.getBancoEmisor());
        response.setNumeroTransaccion(pago.getNumeroTransaccion());
        response.setCodigoAutorizacion(pago.getCodigoAutorizacion());
        response.setFechaPago(pago.getFechaPago());
        response.setFechaProcesamiento(pago.getFechaProcesamiento());
        response.setEstadoPago(pago.getEstadoPago());
        response.setCodigoRespuesta(pago.getCodigoRespuesta());
        response.setMensajeRespuesta(pago.getMensajeRespuesta());
        response.setComisionPago(pago.getComisionPago());
        response.setMontoNeto(pago.getMontoNeto());
        response.setDatosAdicionales(pago.getDatosAdicionales());
        response.setIntentosPago(pago.getIntentosPago());
        response.setMaxIntentos(pago.getMaxIntentos());
        response.setFechaVencimiento(pago.getFechaVencimiento());
        response.setActivo(pago.getActivo());
        return response;
    }

    /**
     * Convierte una entidad Transaccion a TransaccionResponseDTO
     */
    private TransaccionResponseDTO convertirTransaccionAResponseDTO(Transaccion transaccion) {
        TransaccionResponseDTO response = new TransaccionResponseDTO();
        response.setIdTransaccion(transaccion.getIdTransaccion());
        response.setIdPago(transaccion.getIdPago());
        response.setTipoTransaccion(transaccion.getTipoTransaccion());
        response.setMontoTransaccion(transaccion.getMontoTransaccion());
        response.setMonedaTransaccion(transaccion.getMonedaTransaccion());
        response.setNumeroTransaccionExterna(transaccion.getNumeroTransaccionExterna());
        response.setFechaTransaccion(transaccion.getFechaTransaccion());
        response.setEstadoTransaccion(transaccion.getEstadoTransaccion());
        response.setCodigoRespuestaExterna(transaccion.getCodigoRespuestaExterna());
        response.setMensajeRespuestaExterna(transaccion.getMensajeRespuestaExterna());
        response.setDatosRespuesta(transaccion.getDatosRespuesta());
        response.setTiempoProcesamiento(transaccion.getTiempoProcesamiento());
        response.setProveedorPago(transaccion.getProveedorPago());
        response.setIpCliente(transaccion.getIpCliente());
        response.setUserAgent(transaccion.getUserAgent());
        response.setActivo(transaccion.getActivo());
        return response;
    }
}
