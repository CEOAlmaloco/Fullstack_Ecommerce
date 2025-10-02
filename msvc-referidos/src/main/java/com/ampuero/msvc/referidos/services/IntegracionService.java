package com.ampuero.msvc.referidos.services;

import java.util.Map;

public interface IntegracionService {

    // Integración con msvc-auth
    Boolean validarUsuarioExistente(String email, String run);

    Map<String, Object> sincronizarUsuarioConAuth(Long idReferido);

    // Integración con msvc-usuario
    Map<String, Object> actualizarPerfilUsuario(Long idReferido, String nivel);

    // Integración con msvc-productos
    Map<String, Object> procesarCanjeProducto(Long idReferido, Long idProducto, Integer puntosRequeridos);

    // Integración con msvc-inventario
    Boolean verificarDisponibilidadProducto(Long idProducto);

    Boolean reservarProductoParaCanje(Long idProducto);

    // Integración con msvc-promociones
    Map<String, Object> obtenerDescuentosDisponibles(String nivel, Integer puntos);

    // Integración con msvc-notificaciones
    Boolean notificarReferidoExitoso(Long idReferidor, String nombreReferido);

    Boolean notificarPuntosOtorgados(Long idUsuario, Integer puntos);

    Boolean notificarNivelAscendido(Long idUsuario, String nuevoNivel);

    Boolean notificarCanjeRealizado(Long idUsuario, String producto, Integer puntosGastados);
}
