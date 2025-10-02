package com.ampuero.msvc.referidos.repositories;

import com.ampuero.msvc.referidos.entities.Referido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReferidoRepository extends JpaRepository<Referido, Long> {

    // Buscar por email
    Optional<Referido> findByEmailReferido(String emailReferido);

    // Buscar por RUN
    Optional<Referido> findByRunReferido(String runReferido);

    // Buscar por código de referido
    Optional<Referido> findByCodigoReferido(String codigoReferido);

    // Buscar referidos por referidor
    @Query("SELECT r FROM Referido r WHERE r.idReferidor = :idReferidor")
    List<Referido> findReferidosByReferidor(@Param("idReferidor") Long idReferidor);

    // Buscar por estado activo
    List<Referido> findByActivoTrue();

    // Verificar si existe por email
    boolean existsByEmailReferido(String emailReferido);

    // Verificar si existe por RUN
    boolean existsByRunReferido(String runReferido);

    // Verificar si existe por código de referido
    boolean existsByCodigoReferido(String codigoReferido);
}
