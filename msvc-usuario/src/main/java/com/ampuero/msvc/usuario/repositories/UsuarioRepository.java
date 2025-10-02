package com.ampuero.msvc.usuario.repositories;

import com.ampuero.msvc.usuario.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Usuario
 * Proporciona métodos de acceso a datos para usuarios
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca un usuario por su correo electrónico
     * @param correo Correo electrónico del usuario
     * @return Usuario encontrado
     */
    Optional<Usuario> findByCorreo(String correo);

    /**
     * Verifica si existe un usuario con el correo dado
     * @param correo Correo electrónico a verificar
     * @return true si existe, false si no
     */
    boolean existsByCorreo(String correo);

    /**
     * Busca usuarios por tipo
     * @param tipoUsuario Tipo de usuario
     * @return Lista de usuarios del tipo especificado
     */
    List<Usuario> findByTipoUsuario(Usuario.TipoUsuario tipoUsuario);

    /**
     * Busca usuarios por estado
     * @param estado Estado del usuario
     * @return Lista de usuarios con el estado especificado
     */
    List<Usuario> findByEstado(Usuario.EstadoUsuario estado);

    /**
     * Busca usuarios por nivel
     * @param nivelUsuario Nivel del usuario
     * @return Lista de usuarios con el nivel especificado
     */
    List<Usuario> findByNivelUsuario(Usuario.NivelUsuario nivelUsuario);

    /**
     * Busca usuarios por código de referido
     * @param codigoReferido Código de referido
     * @return Usuario encontrado
     */
    Optional<Usuario> findByCodigoReferido(String codigoReferido);

    /**
     * Busca usuarios que fueron referidos por otro usuario
     * @param referidoPor Código del usuario que refirió
     * @return Lista de usuarios referidos
     */
    List<Usuario> findByReferidoPor(String referidoPor);

    /**
     * Busca usuarios activos
     * @return Lista de usuarios activos
     */
    @Query("SELECT u FROM Usuario u WHERE u.estado = 'ACTIVO'")
    List<Usuario> findUsuariosActivos();

    /**
     * Busca usuarios por rango de fechas de registro
     * @param fechaInicio Fecha de inicio
     * @param fechaFin Fecha de fin
     * @return Lista de usuarios registrados en el rango
     */
    @Query("SELECT u FROM Usuario u WHERE u.fechaRegistro BETWEEN :fechaInicio AND :fechaFin")
    List<Usuario> findByFechaRegistroBetween(@Param("fechaInicio") LocalDateTime fechaInicio, 
                                           @Param("fechaFin") LocalDateTime fechaFin);

    /**
     * Cuenta usuarios por tipo
     * @param tipoUsuario Tipo de usuario
     * @return Número de usuarios del tipo especificado
     */
    long countByTipoUsuario(Usuario.TipoUsuario tipoUsuario);

    /**
     * Cuenta usuarios activos
     * @return Número de usuarios activos
     */
    long countByEstado(Usuario.EstadoUsuario estado);

    /**
     * Busca usuarios con puntos LevelUp mayores o iguales a un valor
     * @param puntosMinimos Puntos mínimos
     * @return Lista de usuarios con los puntos especificados o más
     */
    List<Usuario> findByPuntosLevelUpGreaterThanEqual(Integer puntosMinimos);

    /**
     * Busca usuarios por ciudad
     * @param ciudad Ciudad del usuario
     * @return Lista de usuarios de la ciudad especificada
     */
    List<Usuario> findByCiudad(String ciudad);

    /**
     * Busca usuarios por país
     * @param pais País del usuario
     * @return Lista de usuarios del país especificado
     */
    List<Usuario> findByPais(String pais);

    /**
     * Busca usuarios que aceptan marketing
     * @param aceptaMarketing Si acepta marketing
     * @return Lista de usuarios que aceptan o no marketing
     */
    List<Usuario> findByAceptaMarketing(Boolean aceptaMarketing);

    /**
     * Busca usuarios con email verificado
     * @param emailVerificado Si el email está verificado
     * @return Lista de usuarios con email verificado o no
     */
    List<Usuario> findByEmailVerificado(Boolean emailVerificado);
}
