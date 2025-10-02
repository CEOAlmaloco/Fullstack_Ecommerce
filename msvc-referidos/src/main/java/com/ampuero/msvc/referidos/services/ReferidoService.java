package com.ampuero.msvc.referidos.services;

import com.ampuero.msvc.referidos.dtos.ReferidoCreationDTO;
import com.ampuero.msvc.referidos.dtos.ReferidoEstadoDTO;
import com.ampuero.msvc.referidos.entities.Referido;

import java.util.List;
import java.util.Map;

public interface ReferidoService {

    Referido crearReferido(ReferidoCreationDTO referidoCreationDTO);

    List<Referido> traerTodos();

    Referido traerPorId(Long id);

    Referido actualizarReferido(Long id, Referido referido);

    Referido actualizarEstadoReferido(Long id, ReferidoEstadoDTO referidoEstadoDTO);

    void eliminarReferido(Long id);

    Referido buscarPorCodigoReferido(String codigoReferido);

    Referido buscarPorEmail(String email);

    Referido buscarPorRun(String run);

    List<Referido> buscarReferidosPorReferidor(Long idReferidor);

    String generarCodigoReferido();

    void sumarPuntosPorReferido(Long idReferidor, Integer puntos);

    String calcularNivel(Integer puntos);

    boolean validarCodigoReferido(String codigoReferido);

    // Métodos para canje de productos
    Map<String, Object> canjearProductoPorPuntos(Long idReferido, Long idProducto, Integer puntosRequeridos);

    List<Map<String, Object>> obtenerProductosCanjeables(Integer puntos);

    Map<String, Object> obtenerDescuentosDisponibles(Long idReferido);
}
