package com.ampuero.msvc.eventos.services;

import com.ampuero.msvc.eventos.dtos.EventoCreationDTO;
import com.ampuero.msvc.eventos.dtos.EventoEstadoDTO;
import com.ampuero.msvc.eventos.exceptions.EventoException;
import com.ampuero.msvc.eventos.models.Evento;
import com.ampuero.msvc.eventos.repositories.EventoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio de gestión de eventos.
 * <p>
 * Esta clase maneja todas las operaciones CRUD relacionados con eventos gaming.
 *
 * @author Level-Up Gamer Team
 * @version 1.0
 */
@Service
public class EventoServiceImpl implements EventoService {
    private static final Logger log = LoggerFactory.getLogger(EventoServiceImpl.class);
    
    @Autowired
    private EventoRepository eventoRepository;

    /**
     * Crea un nuevo evento en el sistema
     *
     * @param eventoDetails datos de evento a crear
     * @throws EventoException si el nombre del evento ya existe
     */
    @Transactional
    @Override
    public Evento crearEvento(EventoCreationDTO eventoDetails) {
        Optional<Evento> eventoExistente = eventoRepository.findByNombreEvento(eventoDetails.getNombreEvento());

        if (eventoExistente.isPresent()) {
            throw new EventoException("Ya existe un evento con ese nombre: " + eventoDetails.getNombreEvento());
        }

        Evento eventoEntity = new Evento();
        eventoEntity.setNombreEvento(eventoDetails.getNombreEvento());
        eventoEntity.setDescripcionEvento(eventoDetails.getDescripcionEvento());
        eventoEntity.setFechaInicio(eventoDetails.getFechaInicio());
        eventoEntity.setFechaFin(eventoDetails.getFechaFin());
        eventoEntity.setUbicacionEvento(eventoDetails.getUbicacionEvento());
        eventoEntity.setCiudad(eventoDetails.getCiudad());
        eventoEntity.setCoordenadasLatitud(eventoDetails.getCoordenadasLatitud());
        eventoEntity.setCoordenadasLongitud(eventoDetails.getCoordenadasLongitud());
        eventoEntity.setTipoEvento(eventoDetails.getTipoEvento());
        eventoEntity.setCuposMaximos(eventoDetails.getCuposMaximos());
        eventoEntity.setCuposDisponibles(eventoDetails.getCuposMaximos());
        eventoEntity.setCostoEntrada(eventoDetails.getCostoEntrada());
        eventoEntity.setPuntosLevelUp(eventoDetails.getPuntosLevelUp());
        eventoEntity.setRequisitosEdad(eventoDetails.getRequisitosEdad());
        eventoEntity.setEquiposRequeridos(eventoDetails.getEquiposRequeridos());

        return eventoRepository.save(eventoEntity);
    }

    /**
     * Obtiene una lista con todos los eventos registrados
     *
     * @return Lista de eventos
     * @throws EventoException si no hay eventos registrados
     */
    @Transactional(readOnly = true)
    @Override
    public List<Evento> traerTodos() {
        List<Evento> eventos = eventoRepository.findAll();

        if (eventos.isEmpty()) {
            throw new EventoException("No hay eventos registrados");
        }

        return eventos;
    }

    /**
     * Obtiene eventos futuros
     *
     * @return Lista de eventos futuros
     */
    @Transactional(readOnly = true)
    @Override
    public List<Evento> traerEventosFuturos() {
        return eventoRepository.findEventosFuturos(LocalDateTime.now());
    }

    /**
     * Obtiene eventos en curso
     *
     * @return Lista de eventos en curso
     */
    @Transactional(readOnly = true)
    @Override
    public List<Evento> traerEventosEnCurso() {
        return eventoRepository.findEventosEnCurso(LocalDateTime.now());
    }

    /**
     * Obtiene eventos por tipo
     *
     * @param tipo tipo de evento
     * @return Lista de eventos del tipo especificado
     */
    @Transactional(readOnly = true)
    @Override
    public List<Evento> traerEventosPorTipo(String tipo) {
        return eventoRepository.findEventosPorTipo(tipo, LocalDateTime.now());
    }

    /**
     * Obtiene eventos con cupos disponibles
     *
     * @return Lista de eventos con cupos
     */
    @Transactional(readOnly = true)
    @Override
    public List<Evento> traerEventosConCupos() {
        return eventoRepository.findEventosConCupos(LocalDateTime.now());
    }

    /**
     * Obtiene eventos con coordenadas para mapa
     *
     * @return Lista de eventos con coordenadas
     */
    @Transactional(readOnly = true)
    @Override
    public List<Evento> traerEventosConCoordenadas() {
        return eventoRepository.findEventosConCoordenadas();
    }

    /**
     * Obtiene un evento por ID
     *
     * @param id ID del evento
     * @return evento con el ID proporcionado
     * @throws EventoException si el ID no existe
     */
    @Transactional(readOnly = true)
    @Override
    public Evento traerPorId(Long id) {
        return eventoRepository.findById(id).orElseThrow(
                () -> new EventoException("Evento con id " + id + " no encontrado")
        );
    }

    /**
     * Obtiene un evento por nombre
     *
     * @param nombre nombre del evento
     * @return evento con el nombre proporcionado
     * @throws EventoException si el nombre no existe
     */
    @Transactional(readOnly = true)
    @Override
    public Evento traerPorNombre(String nombre) {
        return eventoRepository.findByNombreEvento(nombre).orElseThrow(
                () -> new EventoException("Evento con nombre " + nombre + " no encontrado")
        );
    }

    /**
     * Actualiza los datos de un evento por su ID
     *
     * @param idEvento ID del evento
     * @param eventoDetails detalles de evento para actualizar
     * @throws EventoException si el ID no existe
     */
    @Transactional
    @Override
    public Evento actualizarEvento(Long idEvento, Evento eventoDetails) {
        return eventoRepository.findById(idEvento).map(evento -> {
            evento.setNombreEvento(eventoDetails.getNombreEvento());
            evento.setDescripcionEvento(eventoDetails.getDescripcionEvento());
            evento.setFechaInicio(eventoDetails.getFechaInicio());
            evento.setFechaFin(eventoDetails.getFechaFin());
            evento.setUbicacionEvento(eventoDetails.getUbicacionEvento());
            evento.setCoordenadasLatitud(eventoDetails.getCoordenadasLatitud());
            evento.setCoordenadasLongitud(eventoDetails.getCoordenadasLongitud());
            evento.setTipoEvento(eventoDetails.getTipoEvento());
            evento.setCuposMaximos(eventoDetails.getCuposMaximos());
            evento.setCuposDisponibles(eventoDetails.getCuposDisponibles());
            evento.setCostoEntrada(eventoDetails.getCostoEntrada());
            evento.setPuntosLevelUp(eventoDetails.getPuntosLevelUp());
            evento.setRequisitosEdad(eventoDetails.getRequisitosEdad());
            evento.setEquiposRequeridos(eventoDetails.getEquiposRequeridos());
            return eventoRepository.save(evento);
        }).orElseThrow(() -> new EventoException("Evento con id " + idEvento + " no encontrado"));
    }

    /**
     * Elimina un evento por su ID
     *
     * @param id ID del evento
     * @throws EventoException si el ID no existe
     */
    @Transactional
    @Override
    public void eliminarEvento(Long id) {
        Optional<Evento> eventoOptional = eventoRepository.findById(id);

        if (eventoOptional.isEmpty()) {
            throw new EventoException("No se pudo eliminar: Evento con id " + id + " no encontrado");
        }

        eventoRepository.deleteById(id);
    }

    /**
     * Actualiza el estado de un evento
     *
     * @param id ID del evento
     * @param eventoEstadoDetails estado a actualizar
     * @throws EventoException si el ID no existe
     */
    @Transactional
    @Override
    public Evento actualizarEstadoEvento(Long id, EventoEstadoDTO eventoEstadoDetails) {
        return eventoRepository.findById(id).map(evento -> {
            evento.setActivo(eventoEstadoDetails.getActivo());
            log.info("Estado actualizado: {} {}", eventoEstadoDetails.getActivo(), evento);
            return eventoRepository.save(evento);
        }).orElseThrow(() -> new EventoException("Evento con id " + id + " no encontrado"));
    }

    /**
     * Registra participación de usuario en evento
     *
     * @param eventoId ID del evento
     * @param usuarioId ID del usuario
     * @return evento actualizado
     * @throws EventoException si no hay cupos disponibles
     */
    @Transactional
    @Override
    public Evento registrarParticipacion(Long eventoId, Long usuarioId) {
        Evento evento = traerPorId(eventoId);
        
        if (evento.getCuposDisponibles() <= 0) {
            throw new EventoException("No hay cupos disponibles para este evento");
        }
        
        evento.setCuposDisponibles(evento.getCuposDisponibles() - 1);
        return eventoRepository.save(evento);
    }

    /**
     * Cancela participación de usuario en evento
     *
     * @param eventoId ID del evento
     * @param usuarioId ID del usuario
     * @return evento actualizado
     */
    @Transactional
    @Override
    public Evento cancelarParticipacion(Long eventoId, Long usuarioId) {
        Evento evento = traerPorId(eventoId);
        
        if (evento.getCuposDisponibles() < evento.getCuposMaximos()) {
            evento.setCuposDisponibles(evento.getCuposDisponibles() + 1);
        }
        
        return eventoRepository.save(evento);
    }

    /**
     * Valida disponibilidad de cupos en evento
     *
     * @param eventoId ID del evento
     * @return true si hay cupos disponibles
     */
    @Transactional(readOnly = true)
    @Override
    public boolean validarDisponibilidad(Long eventoId) {
        try {
            Evento evento = traerPorId(eventoId);
            return evento.getCuposDisponibles() > 0 && evento.getActivo();
        } catch (EventoException e) {
            return false;
        }
    }
}
