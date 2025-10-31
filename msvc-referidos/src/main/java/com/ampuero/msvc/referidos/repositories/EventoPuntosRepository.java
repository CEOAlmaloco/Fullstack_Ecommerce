package com.ampuero.msvc.referidos.repositories;

import com.ampuero.msvc.referidos.entities.EventoPuntos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventoPuntosRepository extends JpaRepository<EventoPuntos, Long> {
    List<EventoPuntos> findByIdUsuario(Long idUsuario);
    List<EventoPuntos> findByTipoEvento(EventoPuntos.TipoEvento tipoEvento);
    List<EventoPuntos> findByTipoEventoAndIdUsuario(EventoPuntos.TipoEvento tipoEvento, Long idUsuario);
    Optional<EventoPuntos> findByCodigoEvento(String codigoEvento);
    List<EventoPuntos> findByActivoTrue();
}

