package com.ampuero.msvc.notificaciones.services;

import com.ampuero.msvc.notificaciones.dtos.NotificacionCreationDTO;
import com.ampuero.msvc.notificaciones.dtos.NotificacionEstadoDTO;
import com.ampuero.msvc.notificaciones.dtos.PlantillaCreationDTO;
import com.ampuero.msvc.notificaciones.exceptions.NotificacionException;
import com.ampuero.msvc.notificaciones.models.Notificacion;
import com.ampuero.msvc.notificaciones.models.PlantillaNotificacion;
import com.ampuero.msvc.notificaciones.repositories.NotificacionRepository;
import com.ampuero.msvc.notificaciones.repositories.PlantillaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio de gestión de notificaciones.
 * <p>
 * Esta clase maneja todas las operaciones CRUD relacionados con notificaciones y plantillas.
 *
 * @author Level-Up Gamer Team
 * @version 1.0
 */
@Service
public class NotificacionServiceImpl implements NotificacionService {
    private static final Logger log = LoggerFactory.getLogger(NotificacionServiceImpl.class);
    
    @Autowired
    private NotificacionRepository notificacionRepository;
    
    @Autowired
    private PlantillaRepository plantillaRepository;

    // ========== GESTIÓN DE NOTIFICACIONES ==========

    /**
     * Crea una nueva notificación en el sistema
     */
    @Transactional
    @Override
    public Notificacion crearNotificacion(NotificacionCreationDTO notificacionDetails) {
        Notificacion notificacionEntity = new Notificacion();
        notificacionEntity.setIdUsuario(notificacionDetails.getIdUsuario());
        notificacionEntity.setTipoNotificacion(notificacionDetails.getTipoNotificacion());
        notificacionEntity.setCanalNotificacion(notificacionDetails.getCanalNotificacion());
        notificacionEntity.setAsuntoNotificacion(notificacionDetails.getAsuntoNotificacion());
        notificacionEntity.setContenidoNotificacion(notificacionDetails.getContenidoNotificacion());
        notificacionEntity.setPlantillaNotificacion(notificacionDetails.getPlantillaNotificacion());
        notificacionEntity.setDestinatarioNotificacion(notificacionDetails.getDestinatarioNotificacion());
        notificacionEntity.setFechaCreacion(LocalDateTime.now());
        notificacionEntity.setFechaProgramada(notificacionDetails.getFechaProgramada() != null ? 
            notificacionDetails.getFechaProgramada() : LocalDateTime.now());
        notificacionEntity.setEstadoNotificacion("CREADA");
        notificacionEntity.setPrioridadNotificacion(notificacionDetails.getPrioridadNotificacion());
        notificacionEntity.setMetadataNotificacion(notificacionDetails.getMetadataNotificacion());

        return notificacionRepository.save(notificacionEntity);
    }

    /**
     * Obtiene todas las notificaciones
     */
    @Transactional(readOnly = true)
    @Override
    public List<Notificacion> traerTodasNotificaciones() {
        List<Notificacion> notificaciones = notificacionRepository.findAll();
        if (notificaciones.isEmpty()) {
            throw new NotificacionException("No hay notificaciones registradas");
        }
        return notificaciones;
    }

    /**
     * Obtiene notificaciones por usuario
     */
    @Transactional(readOnly = true)
    @Override
    public List<Notificacion> traerNotificacionesPorUsuario(Long idUsuario) {
        return notificacionRepository.findNotificacionesPorUsuario(idUsuario);
    }

    /**
     * Obtiene notificaciones por estado
     */
    @Transactional(readOnly = true)
    @Override
    public List<Notificacion> traerNotificacionesPorEstado(String estado) {
        return notificacionRepository.findNotificacionesPorEstado(estado);
    }

    /**
     * Obtiene notificaciones por tipo
     */
    @Transactional(readOnly = true)
    @Override
    public List<Notificacion> traerNotificacionesPorTipo(String tipo) {
        return notificacionRepository.findNotificacionesPorTipo(tipo);
    }

    /**
     * Obtiene notificaciones por canal
     */
    @Transactional(readOnly = true)
    @Override
    public List<Notificacion> traerNotificacionesPorCanal(String canal) {
        return notificacionRepository.findNotificacionesPorCanal(canal);
    }

    /**
     * Obtiene notificaciones para enviar
     */
    @Transactional(readOnly = true)
    @Override
    public List<Notificacion> traerNotificacionesParaEnviar() {
        return notificacionRepository.findNotificacionesParaEnviar(LocalDateTime.now());
    }

    /**
     * Obtiene notificaciones para reintentar
     */
    @Transactional(readOnly = true)
    @Override
    public List<Notificacion> traerNotificacionesParaReintentar() {
        return notificacionRepository.findNotificacionesParaReintentar();
    }

    /**
     * Obtiene notificaciones por rango de fechas
     */
    @Transactional(readOnly = true)
    @Override
    public List<Notificacion> traerNotificacionesPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return notificacionRepository.findNotificacionesPorRangoFechas(fechaInicio, fechaFin);
    }

    /**
     * Obtiene notificaciones por destinatario
     */
    @Transactional(readOnly = true)
    @Override
    public List<Notificacion> traerNotificacionesPorDestinatario(String destinatario) {
        return notificacionRepository.findNotificacionesPorDestinatario(destinatario);
    }

    /**
     * Obtiene una notificación por ID
     */
    @Transactional(readOnly = true)
    @Override
    public Notificacion traerNotificacionPorId(Long id) {
        return notificacionRepository.findById(id).orElseThrow(
                () -> new NotificacionException("Notificación con id " + id + " no encontrada")
        );
    }

    /**
     * Actualiza una notificación
     */
    @Transactional
    @Override
    public Notificacion actualizarNotificacion(Long id, Notificacion notificacionDetails) {
        return notificacionRepository.findById(id).map(notificacion -> {
            notificacion.setTipoNotificacion(notificacionDetails.getTipoNotificacion());
            notificacion.setCanalNotificacion(notificacionDetails.getCanalNotificacion());
            notificacion.setAsuntoNotificacion(notificacionDetails.getAsuntoNotificacion());
            notificacion.setContenidoNotificacion(notificacionDetails.getContenidoNotificacion());
            notificacion.setPlantillaNotificacion(notificacionDetails.getPlantillaNotificacion());
            notificacion.setDestinatarioNotificacion(notificacionDetails.getDestinatarioNotificacion());
            notificacion.setPrioridadNotificacion(notificacionDetails.getPrioridadNotificacion());
            notificacion.setMetadataNotificacion(notificacionDetails.getMetadataNotificacion());
            return notificacionRepository.save(notificacion);
        }).orElseThrow(() -> new NotificacionException("Notificación con id " + id + " no encontrada"));
    }

    /**
     * Actualiza el estado de una notificación
     */
    @Transactional
    @Override
    public Notificacion actualizarEstadoNotificacion(Long id, NotificacionEstadoDTO estadoDetails) {
        return notificacionRepository.findById(id).map(notificacion -> {
            notificacion.setEstadoNotificacion(estadoDetails.getEstadoNotificacion());
            log.info("Estado actualizado: {} {}", estadoDetails.getEstadoNotificacion(), notificacion);
            return notificacionRepository.save(notificacion);
        }).orElseThrow(() -> new NotificacionException("Notificación con id " + id + " no encontrada"));
    }

    /**
     * Envía una notificación
     */
    @Transactional
    @Override
    public Notificacion enviarNotificacion(Long id) {
        Notificacion notificacion = traerNotificacionPorId(id);
        
        try {
            // Simular envío de notificación
            notificacion.setEstadoNotificacion("ENVIANDO");
            notificacion.setFechaEnvio(LocalDateTime.now());
            notificacion.setIntentosEnvio(notificacion.getIntentosEnvio() + 1);
            
            // Aquí iría la lógica real de envío según el tipo
            notificacion.setEstadoNotificacion("ENVIADA");
            notificacion.setFechaEntrega(LocalDateTime.now());
            
            log.info("Notificación enviada exitosamente: {}", notificacion.getIdNotificacion());
            
        } catch (Exception e) {
            notificacion.setEstadoNotificacion("FALLIDA");
            notificacion.setErrorMensaje(e.getMessage());
            log.error("Error enviando notificación: {}", e.getMessage());
        }
        
        return notificacionRepository.save(notificacion);
    }

    /**
     * Marca una notificación como entregada
     */
    @Transactional
    @Override
    public Notificacion marcarComoEntregada(Long id) {
        Notificacion notificacion = traerNotificacionPorId(id);
        notificacion.setEstadoNotificacion("ENTREGADA");
        notificacion.setFechaEntrega(LocalDateTime.now());
        return notificacionRepository.save(notificacion);
    }

    /**
     * Marca una notificación como abierta
     */
    @Transactional
    @Override
    public Notificacion marcarComoAbierta(Long id) {
        Notificacion notificacion = traerNotificacionPorId(id);
        notificacion.setEstadoNotificacion("ABIERTA");
        notificacion.setFechaApertura(LocalDateTime.now());
        return notificacionRepository.save(notificacion);
    }

    /**
     * Reintenta el envío de una notificación
     */
    @Transactional
    @Override
    public Notificacion reintentarEnvio(Long id) {
        Notificacion notificacion = traerNotificacionPorId(id);
        
        if (notificacion.getIntentosEnvio() >= notificacion.getMaxIntentos()) {
            throw new NotificacionException("Máximo número de intentos alcanzado");
        }
        
        return enviarNotificacion(id);
    }

    /**
     * Procesa la cola de notificaciones pendientes
     */
    @Transactional
    @Scheduled(fixedRate = 60000) // Cada minuto
    @Override
    public void procesarColaNotificaciones() {
        List<Notificacion> notificacionesParaEnviar = traerNotificacionesParaEnviar();
        
        for (Notificacion notificacion : notificacionesParaEnviar) {
            try {
                enviarNotificacion(notificacion.getIdNotificacion());
            } catch (Exception e) {
                log.error("Error procesando notificación {}: {}", notificacion.getIdNotificacion(), e.getMessage());
            }
        }
        
        // Procesar notificaciones fallidas para reintento
        List<Notificacion> notificacionesParaReintentar = traerNotificacionesParaReintentar();
        for (Notificacion notificacion : notificacionesParaReintentar) {
            try {
                reintentarEnvio(notificacion.getIdNotificacion());
            } catch (Exception e) {
                log.error("Error reintentando notificación {}: {}", notificacion.getIdNotificacion(), e.getMessage());
            }
        }
    }

    /**
     * Elimina una notificación
     */
    @Transactional
    @Override
    public void eliminarNotificacion(Long id) {
        Optional<Notificacion> notificacionOptional = notificacionRepository.findById(id);
        if (notificacionOptional.isEmpty()) {
            throw new NotificacionException("No se pudo eliminar: Notificación con id " + id + " no encontrada");
        }
        notificacionRepository.deleteById(id);
    }

    // ========== GESTIÓN DE PLANTILLAS ==========

    /**
     * Crea una nueva plantilla
     */
    @Transactional
    @Override
    public PlantillaNotificacion crearPlantilla(PlantillaCreationDTO plantillaDetails) {
        boolean existe = plantillaRepository.existsByCodigoPlantilla(plantillaDetails.getCodigoPlantilla());
        if (existe) {
            throw new NotificacionException("Ya existe una plantilla con ese código: " + plantillaDetails.getCodigoPlantilla());
        }

        PlantillaNotificacion plantillaEntity = new PlantillaNotificacion();
        plantillaEntity.setCodigoPlantilla(plantillaDetails.getCodigoPlantilla());
        plantillaEntity.setNombrePlantilla(plantillaDetails.getNombrePlantilla());
        plantillaEntity.setDescripcionPlantilla(plantillaDetails.getDescripcionPlantilla());
        plantillaEntity.setTipoPlantilla(plantillaDetails.getTipoPlantilla());
        plantillaEntity.setAsuntoPlantilla(plantillaDetails.getAsuntoPlantilla());
        plantillaEntity.setContenidoPlantilla(plantillaDetails.getContenidoPlantilla());
        plantillaEntity.setVariablesPlantilla(plantillaDetails.getVariablesPlantilla());
        plantillaEntity.setFechaCreacion(LocalDateTime.now());
        plantillaEntity.setVersionPlantilla(plantillaDetails.getVersionPlantilla() != null ? 
            plantillaDetails.getVersionPlantilla() : "1.0");

        return plantillaRepository.save(plantillaEntity);
    }

    /**
     * Obtiene todas las plantillas
     */
    @Transactional(readOnly = true)
    @Override
    public List<PlantillaNotificacion> traerTodasPlantillas() {
        return plantillaRepository.findAll();
    }

    /**
     * Obtiene plantillas por tipo
     */
    @Transactional(readOnly = true)
    @Override
    public List<PlantillaNotificacion> traerPlantillasPorTipo(String tipo) {
        return plantillaRepository.findPlantillasPorTipo(tipo);
    }

    /**
     * Obtiene plantillas activas
     */
    @Transactional(readOnly = true)
    @Override
    public List<PlantillaNotificacion> traerPlantillasActivas() {
        return plantillaRepository.findPlantillasActivas();
    }

    /**
     * Obtiene una plantilla por ID
     */
    @Transactional(readOnly = true)
    @Override
    public PlantillaNotificacion traerPlantillaPorId(Long id) {
        return plantillaRepository.findById(id).orElseThrow(
                () -> new NotificacionException("Plantilla con id " + id + " no encontrada")
        );
    }

    /**
     * Obtiene una plantilla por código
     */
    @Transactional(readOnly = true)
    @Override
    public PlantillaNotificacion traerPlantillaPorCodigo(String codigo) {
        return plantillaRepository.findByCodigoPlantilla(codigo).orElseThrow(
                () -> new NotificacionException("Plantilla con código " + codigo + " no encontrada")
        );
    }

    /**
     * Actualiza una plantilla
     */
    @Transactional
    @Override
    public PlantillaNotificacion actualizarPlantilla(Long id, PlantillaNotificacion plantillaDetails) {
        return plantillaRepository.findById(id).map(plantilla -> {
            plantilla.setNombrePlantilla(plantillaDetails.getNombrePlantilla());
            plantilla.setDescripcionPlantilla(plantillaDetails.getDescripcionPlantilla());
            plantilla.setTipoPlantilla(plantillaDetails.getTipoPlantilla());
            plantilla.setAsuntoPlantilla(plantillaDetails.getAsuntoPlantilla());
            plantilla.setContenidoPlantilla(plantillaDetails.getContenidoPlantilla());
            plantilla.setVariablesPlantilla(plantillaDetails.getVariablesPlantilla());
            plantilla.setFechaActualizacion(LocalDateTime.now());
            return plantillaRepository.save(plantilla);
        }).orElseThrow(() -> new NotificacionException("Plantilla con id " + id + " no encontrada"));
    }

    /**
     * Elimina una plantilla
     */
    @Transactional
    @Override
    public void eliminarPlantilla(Long id) {
        Optional<PlantillaNotificacion> plantillaOptional = plantillaRepository.findById(id);
        if (plantillaOptional.isEmpty()) {
            throw new NotificacionException("No se pudo eliminar: Plantilla con id " + id + " no encontrada");
        }
        plantillaRepository.deleteById(id);
    }

    // ========== ENVÍO MASIVO ==========

    /**
     * Envía notificación masiva
     */
    @Transactional
    @Override
    public List<Notificacion> enviarNotificacionMasiva(List<Long> idsUsuarios, String tipoNotificacion, 
                                                      String canalNotificacion, String asunto, String contenido) {
        List<Notificacion> notificaciones = new ArrayList<>();
        
        for (Long idUsuario : idsUsuarios) {
            NotificacionCreationDTO dto = new NotificacionCreationDTO();
            dto.setIdUsuario(idUsuario);
            dto.setTipoNotificacion(tipoNotificacion);
            dto.setCanalNotificacion(canalNotificacion);
            dto.setAsuntoNotificacion(asunto);
            dto.setContenidoNotificacion(contenido);
            dto.setPrioridadNotificacion("BAJA");
            
            Notificacion notificacion = crearNotificacion(dto);
            notificaciones.add(notificacion);
        }
        
        return notificaciones;
    }

    /**
     * Envía notificación masiva por plantilla
     */
    @Transactional
    @Override
    public List<Notificacion> enviarNotificacionPorPlantilla(List<Long> idsUsuarios, String codigoPlantilla, String metadata) {
        PlantillaNotificacion plantilla = traerPlantillaPorCodigo(codigoPlantilla);
        List<Notificacion> notificaciones = new ArrayList<>();
        
        for (Long idUsuario : idsUsuarios) {
            NotificacionCreationDTO dto = new NotificacionCreationDTO();
            dto.setIdUsuario(idUsuario);
            dto.setTipoNotificacion(plantilla.getTipoPlantilla());
            dto.setCanalNotificacion("PROMOCIONAL");
            dto.setAsuntoNotificacion(plantilla.getAsuntoPlantilla());
            dto.setContenidoNotificacion(plantilla.getContenidoPlantilla());
            dto.setPlantillaNotificacion(codigoPlantilla);
            dto.setMetadataNotificacion(metadata);
            dto.setPrioridadNotificacion("MEDIA");
            
            Notificacion notificacion = crearNotificacion(dto);
            notificaciones.add(notificacion);
        }
        
        return notificaciones;
    }
}
