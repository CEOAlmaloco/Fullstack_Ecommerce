package com.ampuero.msvc.referidos.repositories;

import com.ampuero.msvc.referidos.entities.PuntosUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PuntosUsuarioRepository extends JpaRepository<PuntosUsuario, Long> {
    Optional<PuntosUsuario> findByIdUsuario(Long idUsuario);
    Optional<PuntosUsuario> findByCodigoReferido(String codigoReferido);
    Optional<PuntosUsuario> findByIdUsuarioAndActivoTrue(Long idUsuario);
}

