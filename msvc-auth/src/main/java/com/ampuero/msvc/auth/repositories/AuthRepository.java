package com.ampuero.msvc.auth.repositories;

import com.ampuero.msvc.auth.models.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Long> {

    // Búsqueda de tokens
    Optional<Auth> findByTokenJwtAndActivoTrue(String tokenJwt);

    Optional<Auth> findByTokenJwt(String tokenJwt);

    // Tokens por usuario
    List<Auth> findByUsuarioIdAndActivoTrue(Long usuarioId);

    List<Auth> findByUsuarioIdAndTipoTokenAndActivoTrue(Long usuarioId, String tipoToken);

    // Tokens expirados
    @Query("SELECT a FROM Auth a WHERE a.fechaExpiracion < :fechaActual AND a.activo = true")
    List<Auth> findTokensExpirados(@Param("fechaActual") LocalDateTime fechaActual);

    // Revocar tokens
    @Modifying
    @Query("UPDATE Auth a SET a.activo = false WHERE a.usuarioId = :usuarioId")
    void revocarTodosLosTokensDelUsuario(@Param("usuarioId") Long usuarioId);

    @Modifying
    @Query("UPDATE Auth a SET a.activo = false WHERE a.tokenJwt = :tokenJwt")
    void revocarToken(@Param("tokenJwt") String tokenJwt);

    @Modifying
    @Query("UPDATE Auth a SET a.activo = false WHERE a.usuarioId = :usuarioId AND a.tipoToken = :tipoToken")
    void revocarTokensPorTipo(@Param("usuarioId") Long usuarioId, @Param("tipoToken") String tipoToken);

    // Limpieza de tokens expirados
    @Modifying
    @Query("DELETE FROM Auth a WHERE a.fechaExpiracion < :fechaLimite")
    void eliminarTokensExpirados(@Param("fechaLimite") LocalDateTime fechaLimite);

    // Estadísticas
    Long countByUsuarioIdAndActivoTrue(Long usuarioId);

    Long countByTipoTokenAndActivoTrue(String tipoToken);
}
