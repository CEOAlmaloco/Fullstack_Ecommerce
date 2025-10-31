package com.ampuero.msvc.referidos.repositories;

import com.ampuero.msvc.referidos.entities.TransaccionPuntos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransaccionPuntosRepository extends JpaRepository<TransaccionPuntos, Long> {
    List<TransaccionPuntos> findByIdUsuario(Long idUsuario);
    List<TransaccionPuntos> findByIdUsuarioOrderByFechaTransaccionDesc(Long idUsuario);
    List<TransaccionPuntos> findByIdUsuarioAndTipoTransaccion(Long idUsuario, TransaccionPuntos.TipoTransaccion tipo);
}

