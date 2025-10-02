package com.ampuero.msvc.eventos.services;

import com.ampuero.msvc.eventos.dtos.EventoCreationDTO;
import com.ampuero.msvc.eventos.dtos.EventoEstadoDTO;
import com.ampuero.msvc.eventos.models.Evento;

import java.util.List;

public interface EventoService {
    List<Evento> traerTodos();
    
    List<Evento> traerEventosFuturos();
    
    List<Evento> traerEventosEnCurso();
    
    List<Evento> traerEventosPorTipo(String tipo);
    
    List<Evento> traerEventosConCupos();
    
    List<Evento> traerEventosConCoordenadas();
    
    Evento traerPorId(Long id);
    
    Evento traerPorNombre(String nombre);
    
    Evento crearEvento(EventoCreationDTO eventoDetails);
    
    void eliminarEvento(Long id);
    
    Evento actualizarEvento(Long id, Evento evento);
    
    Evento actualizarEstadoEvento(Long id, EventoEstadoDTO eventoEstadoDetails);
    
    Evento registrarParticipacion(Long eventoId, Long usuarioId);
    
    Evento cancelarParticipacion(Long eventoId, Long usuarioId);
    
    boolean validarDisponibilidad(Long eventoId);
}
