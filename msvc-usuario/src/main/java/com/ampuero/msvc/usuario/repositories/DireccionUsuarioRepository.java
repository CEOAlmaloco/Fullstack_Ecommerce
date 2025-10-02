package com.ampuero.msvc.usuario.repositories;

import com.ampuero.msvc.usuario.entities.DireccionUsuario;
import com.ampuero.msvc.usuario.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad DireccionUsuario
 * Proporciona métodos de acceso a datos para direcciones de usuarios
 */
@Repository
public interface DireccionUsuarioRepository extends JpaRepository<DireccionUsuario, Long> {

    /**
     * Busca todas las direcciones de un usuario
     * @param usuario Usuario propietario de las direcciones
     * @return Lista de direcciones del usuario
     */
    List<DireccionUsuario> findByUsuario(Usuario usuario);

    /**
     * Busca direcciones activas de un usuario
     * @param usuario Usuario propietario
     * @param activa Si la dirección está activa
     * @return Lista de direcciones activas del usuario
     */
    List<DireccionUsuario> findByUsuarioAndActiva(Usuario usuario, Boolean activa);

    /**
     * Busca la dirección principal de un usuario
     * @param usuario Usuario propietario
     * @param esPrincipal Si es la dirección principal
     * @return Dirección principal del usuario
     */
    Optional<DireccionUsuario> findByUsuarioAndEsPrincipal(Usuario usuario, Boolean esPrincipal);

    /**
     * Busca direcciones por tipo
     * @param usuario Usuario propietario
     * @param tipoDireccion Tipo de dirección
     * @return Lista de direcciones del tipo especificado
     */
    List<DireccionUsuario> findByUsuarioAndTipoDireccion(Usuario usuario, 
                                                       DireccionUsuario.TipoDireccion tipoDireccion);

    /**
     * Busca direcciones por ciudad
     * @param ciudad Ciudad de las direcciones
     * @return Lista de direcciones en la ciudad especificada
     */
    List<DireccionUsuario> findByCiudad(String ciudad);

    /**
     * Busca direcciones por país
     * @param pais País de las direcciones
     * @return Lista de direcciones en el país especificado
     */
    List<DireccionUsuario> findByPais(String pais);

    /**
     * Cuenta direcciones de un usuario
     * @param usuario Usuario propietario
     * @return Número de direcciones del usuario
     */
    long countByUsuario(Usuario usuario);

    /**
     * Cuenta direcciones activas de un usuario
     * @param usuario Usuario propietario
     * @param activa Si la dirección está activa
     * @return Número de direcciones activas del usuario
     */
    long countByUsuarioAndActiva(Usuario usuario, Boolean activa);

    /**
     * Verifica si un usuario tiene una dirección principal
     * @param usuario Usuario propietario
     * @param esPrincipal Si es la dirección principal
     * @return true si tiene dirección principal, false si no
     */
    boolean existsByUsuarioAndEsPrincipal(Usuario usuario, Boolean esPrincipal);

    /**
     * Busca todas las direcciones activas
     * @return Lista de todas las direcciones activas
     */
    @Query("SELECT d FROM DireccionUsuario d WHERE d.activa = true")
    List<DireccionUsuario> findAllDireccionesActivas();

    /**
     * Busca direcciones por código postal
     * @param codigoPostal Código postal
     * @return Lista de direcciones con el código postal especificado
     */
    List<DireccionUsuario> findByCodigoPostal(String codigoPostal);
}
