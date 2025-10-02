package com.ampuero.msvc.notificaciones.services;

import com.ampuero.msvc.notificaciones.dtos.NotificacionCreationDTO;
import com.ampuero.msvc.notificaciones.dtos.NotificacionEstadoDTO;
import com.ampuero.msvc.notificaciones.dtos.PlantillaCreationDTO;
import com.ampuero.msvc.notificaciones.models.Notificacion;
import com.ampuero.msvc.notificaciones.models.PlantillaNotificacion;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificacionService {
    // Gestión de Notificaciones
    List<Notificacion> traerTodasNotificaciones();
    List<Notificacion> traerNotificacionesPorUsuario(Long idUsuario);
    List<Notificacion> traerNotificacionesPorEstado(String estado);
    List<Notificacion> traerNotificacionesPorTipo(String tipo);
    List<Notificacion> traerNotificacionesPorCanal(String canal);
    List<Notificacion> traerNotificacionesParaEnviar();
    List<Notificacion> traerNotificacionesParaReintentar();
    List<Notificacion> traerNotificacionesPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    List<Notificacion> traerNotificacionesPorDestinatario(String destinatario);
    Notificacion traerNotificacionPorId(Long id);
    Notificacion crearNotificacion(NotificacionCreationDTO notificacionDetails);
    void eliminarNotificacion(Long id);
    Notificacion actualizarNotificacion(Long id, Notificacion notificacion);
    Notificacion actualizarEstadoNotificacion(Long id, NotificacionEstadoDTO estadoDetails);
    Notificacion enviarNotificacion(Long id);
    Notificacion marcarComoEntregada(Long id);
    Notificacion marcarComoAbierta(Long id);
    Notificacion reintentarEnvio(Long id);
    void procesarColaNotificaciones();
    
    // Gestión de Plantillas
    List<PlantillaNotificacion> traerTodasPlantillas();
    List<PlantillaNotificacion> traerPlantillasPorTipo(String tipo);
    List<PlantillaNotificacion> traerPlantillasActivas();
    PlantillaNotificacion traerPlantillaPorId(Long id);
    PlantillaNotificacion traerPlantillaPorCodigo(String codigo);
    PlantillaNotificacion crearPlantilla(PlantillaCreationDTO plantillaDetails);
    void eliminarPlantilla(Long id);
    PlantillaNotificacion actualizarPlantilla(Long id, PlantillaNotificacion plantilla);
    
    // Envío masivo
    List<Notificacion> enviarNotificacionMasiva(List<Long> idsUsuarios, String tipoNotificacion, String canalNotificacion, String asunto, String contenido);
    List<Notificacion> enviarNotificacionPorPlantilla(List<Long> idsUsuarios, String codigoPlantilla, String metadata);
}
