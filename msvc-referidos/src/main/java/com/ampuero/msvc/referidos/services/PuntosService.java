package com.ampuero.msvc.referidos.services;

import com.ampuero.msvc.referidos.dtos.*;
import com.ampuero.msvc.referidos.entities.*;
import com.ampuero.msvc.referidos.exceptions.ResourceNotFoundException;
import com.ampuero.msvc.referidos.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PuntosService {

    @Autowired
    private PuntosUsuarioRepository puntosUsuarioRepository;

    @Autowired
    private EventoPuntosRepository eventoPuntosRepository;

    @Autowired
    private TransaccionPuntosRepository transaccionPuntosRepository;

    public PuntosUsuario obtenerPuntosUsuario(Long idUsuario) {
        return puntosUsuarioRepository.findByIdUsuarioAndActivoTrue(idUsuario)
                .orElseGet(() -> crearPuntosUsuario(idUsuario));
    }

    @Transactional
    public PuntosUsuario crearPuntosUsuario(Long idUsuario) {
        PuntosUsuario puntosUsuario = new PuntosUsuario();
        puntosUsuario.setIdUsuario(idUsuario);
        puntosUsuario.setPuntosTotales(0);
        puntosUsuario.setPuntosDisponibles(0);
        puntosUsuario.setPuntosUsados(0);
        puntosUsuario.setNivelUsuario(PuntosUsuario.NivelUsuario.BRONZE);
        puntosUsuario.setCodigoReferido(generarCodigoReferido(idUsuario));
        puntosUsuario.setActivo(true);
        return puntosUsuarioRepository.save(puntosUsuario);
    }

    private String generarCodigoReferido(Long idUsuario) {
        return "REF-" + idUsuario + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    @Transactional
    public TransaccionPuntos otorgarPuntosPorEvento(Long idUsuario, EventoPuntosCreationDTO eventoDTO) {
        PuntosUsuario puntosUsuario = obtenerPuntosUsuario(idUsuario);
        
        // Crear evento
        EventoPuntos evento = new EventoPuntos();
        evento.setTipoEvento(EventoPuntos.TipoEvento.valueOf(eventoDTO.getTipoEvento()));
        evento.setNombreEvento(eventoDTO.getNombreEvento());
        evento.setDescripcionEvento(eventoDTO.getDescripcionEvento());
        evento.setPuntosOtorgados(eventoDTO.getPuntosOtorgados());
        evento.setCodigoEvento(eventoDTO.getCodigoEvento());
        evento.setFechaEvento(eventoDTO.getFechaEvento());
        evento.setLugarEvento(eventoDTO.getLugarEvento());
        evento.setDireccionEvento(eventoDTO.getDireccionEvento());
        evento.setIdUsuario(idUsuario);
        evento.setIdProducto(eventoDTO.getIdProducto());
        evento.setIdPedido(eventoDTO.getIdPedido());
        evento.setActivo(true);
        evento = eventoPuntosRepository.save(evento);

        // Actualizar puntos del usuario
        Integer puntosAnteriores = puntosUsuario.getPuntosDisponibles();
        puntosUsuario.setPuntosTotales(puntosUsuario.getPuntosTotales() + evento.getPuntosOtorgados());
        puntosUsuario.setPuntosDisponibles(puntosUsuario.getPuntosDisponibles() + evento.getPuntosOtorgados());
        puntosUsuarioRepository.save(puntosUsuario);

        // Crear transacción
        TransaccionPuntos transaccion = new TransaccionPuntos();
        transaccion.setIdUsuario(idUsuario);
        transaccion.setIdEvento(evento.getId());
        transaccion.setTipoTransaccion(TransaccionPuntos.TipoTransaccion.CREDITO);
        transaccion.setPuntos(evento.getPuntosOtorgados());
        transaccion.setPuntosAnteriores(puntosAnteriores);
        transaccion.setPuntosNuevos(puntosUsuario.getPuntosDisponibles());
        transaccion.setDescripcion("Puntos otorgados por: " + evento.getNombreEvento());
        transaccion.setCodigoReferencia(evento.getCodigoEvento());
        transaccion.setActivo(true);
        
        return transaccionPuntosRepository.save(transaccion);
    }

    @Transactional
    public TransaccionPuntos otorgarPuntosPorTipo(Long idUsuario, EventoPuntos.TipoEvento tipoEvento, Integer puntos, String descripcion) {
        EventoPuntosCreationDTO eventoDTO = new EventoPuntosCreationDTO();
        eventoDTO.setTipoEvento(tipoEvento.name());
        eventoDTO.setNombreEvento(obtenerNombreEventoPorTipo(tipoEvento));
        eventoDTO.setDescripcionEvento(descripcion);
        eventoDTO.setPuntosOtorgados(puntos);
        return otorgarPuntosPorEvento(idUsuario, eventoDTO);
    }

    private String obtenerNombreEventoPorTipo(EventoPuntos.TipoEvento tipo) {
        return switch (tipo) {
            case INICIO_SESION -> "Inicio de sesión";
            case REGISTRO -> "Registro de usuario";
            case REFERIDO -> "Usuario referido";
            case COMPRA -> "Compra realizada";
            case ASISTENCIA_EVENTO -> "Asistencia a evento";
            case CANJE_PUNTOS -> "Canje de puntos";
            case REVIEW_PRODUCTO -> "Reseña de producto";
            case LOGRO_DESBLOQUEADO -> "Logro desbloqueado";
        };
    }

    @Transactional
    public TransaccionPuntos canjearPuntos(Long idUsuario, CanjePuntosDTO canjeDTO) {
        PuntosUsuario puntosUsuario = obtenerPuntosUsuario(idUsuario);
        
        if (puntosUsuario.getPuntosDisponibles() < canjeDTO.getPuntosACanjear()) {
            throw new IllegalArgumentException("No tienes suficientes puntos para este canje");
        }

        // Crear evento de canje
        EventoPuntos evento = new EventoPuntos();
        evento.setTipoEvento(EventoPuntos.TipoEvento.CANJE_PUNTOS);
        evento.setNombreEvento("Canje de puntos");
        evento.setDescripcionEvento(canjeDTO.getDescripcion());
        evento.setPuntosOtorgados(-canjeDTO.getPuntosACanjear());
        evento.setIdUsuario(idUsuario);
        if (canjeDTO.getCodigoReferencia() != null) {
            evento.setCodigoEvento(canjeDTO.getCodigoReferencia());
        }
        evento.setActivo(true);
        evento = eventoPuntosRepository.save(evento);

        // Actualizar puntos del usuario
        Integer puntosAnteriores = puntosUsuario.getPuntosDisponibles();
        puntosUsuario.setPuntosDisponibles(puntosUsuario.getPuntosDisponibles() - canjeDTO.getPuntosACanjear());
        puntosUsuario.setPuntosUsados(puntosUsuario.getPuntosUsados() + canjeDTO.getPuntosACanjear());
        puntosUsuarioRepository.save(puntosUsuario);

        // Crear transacción
        TransaccionPuntos transaccion = new TransaccionPuntos();
        transaccion.setIdUsuario(idUsuario);
        transaccion.setIdEvento(evento.getId());
        transaccion.setTipoTransaccion(TransaccionPuntos.TipoTransaccion.DEBITO);
        transaccion.setPuntos(-canjeDTO.getPuntosACanjear());
        transaccion.setPuntosAnteriores(puntosAnteriores);
        transaccion.setPuntosNuevos(puntosUsuario.getPuntosDisponibles());
        transaccion.setDescripcion(canjeDTO.getDescripcion() != null ? canjeDTO.getDescripcion() : "Canje de puntos");
        transaccion.setCodigoReferencia(canjeDTO.getCodigoReferencia());
        transaccion.setActivo(true);
        
        return transaccionPuntosRepository.save(transaccion);
    }

    @Transactional
    public TransaccionPuntos canjearCodigoEvento(Long idUsuario, CanjeCodigoEventoDTO canjeDTO) {
        EventoPuntos evento = eventoPuntosRepository.findByCodigoEvento(canjeDTO.getCodigoEvento())
                .orElseThrow(() -> new ResourceNotFoundException("Código de evento no encontrado: " + canjeDTO.getCodigoEvento()));

        if (!evento.getActivo()) {
            throw new IllegalArgumentException("El código de evento no está activo");
        }

        // Verificar si el usuario ya canjeó este código
        List<TransaccionPuntos> transaccionesExistentes = transaccionPuntosRepository.findByIdUsuario(idUsuario);
        boolean yaCanjeo = transaccionesExistentes.stream()
                .anyMatch(t -> t.getIdEvento().equals(evento.getId()));
        
        if (yaCanjeo) {
            throw new IllegalArgumentException("Ya has canjeado este código de evento");
        }

        // Otorgar puntos
        EventoPuntosCreationDTO eventoDTO = new EventoPuntosCreationDTO();
        eventoDTO.setTipoEvento(evento.getTipoEvento().name());
        eventoDTO.setNombreEvento(evento.getNombreEvento());
        eventoDTO.setDescripcionEvento(evento.getDescripcionEvento());
        eventoDTO.setPuntosOtorgados(evento.getPuntosOtorgados());
        eventoDTO.setCodigoEvento(evento.getCodigoEvento());
        eventoDTO.setFechaEvento(evento.getFechaEvento());
        eventoDTO.setLugarEvento(evento.getLugarEvento());
        eventoDTO.setDireccionEvento(evento.getDireccionEvento());
        
        return otorgarPuntosPorEvento(idUsuario, eventoDTO);
    }

    public List<TransaccionPuntos> obtenerHistorialPuntos(Long idUsuario) {
        return transaccionPuntosRepository.findByIdUsuarioOrderByFechaTransaccionDesc(idUsuario);
    }

    public String obtenerCodigoReferido(Long idUsuario) {
        PuntosUsuario puntosUsuario = obtenerPuntosUsuario(idUsuario);
        return puntosUsuario.getCodigoReferido();
    }
}

