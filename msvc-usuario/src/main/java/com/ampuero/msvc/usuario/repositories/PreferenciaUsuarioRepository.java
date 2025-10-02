package com.ampuero.msvc.usuario.repositories;

import com.ampuero.msvc.usuario.entities.PreferenciaUsuario;
import com.ampuero.msvc.usuario.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad PreferenciaUsuario
 * Proporciona métodos de acceso a datos para preferencias de usuarios
 */
@Repository
public interface PreferenciaUsuarioRepository extends JpaRepository<PreferenciaUsuario, Long> {

    /**
     * Busca todas las preferencias de un usuario
     * @param usuario Usuario propietario de las preferencias
     * @return Lista de preferencias del usuario
     */
    List<PreferenciaUsuario> findByUsuario(Usuario usuario);

    /**
     * Busca preferencias activas de un usuario
     * @param usuario Usuario propietario
     * @param activa Si la preferencia está activa
     * @return Lista de preferencias activas del usuario
     */
    List<PreferenciaUsuario> findByUsuarioAndActiva(Usuario usuario, Boolean activa);

    /**
     * Busca una preferencia específica de un usuario por clave
     * @param usuario Usuario propietario
     * @param clave Clave de la preferencia
     * @return Preferencia encontrada
     */
    Optional<PreferenciaUsuario> findByUsuarioAndClave(Usuario usuario, String clave);

    /**
     * Busca preferencias por tipo
     * @param usuario Usuario propietario
     * @param tipoPreferencia Tipo de preferencia
     * @return Lista de preferencias del tipo especificado
     */
    List<PreferenciaUsuario> findByUsuarioAndTipoPreferencia(Usuario usuario, 
                                                           PreferenciaUsuario.TipoPreferencia tipoPreferencia);

    /**
     * Busca preferencias por categoría
     * @param usuario Usuario propietario
     * @param categoria Categoría de las preferencias
     * @return Lista de preferencias de la categoría especificada
     */
    List<PreferenciaUsuario> findByUsuarioAndCategoria(Usuario usuario, String categoria);

    /**
     * Busca preferencias por tipo y categoría
     * @param usuario Usuario propietario
     * @param tipoPreferencia Tipo de preferencia
     * @param categoria Categoría de las preferencias
     * @return Lista de preferencias del tipo y categoría especificados
     */
    List<PreferenciaUsuario> findByUsuarioAndTipoPreferenciaAndCategoria(Usuario usuario,
                                                                       PreferenciaUsuario.TipoPreferencia tipoPreferencia,
                                                                       String categoria);

    /**
     * Verifica si existe una preferencia con la clave dada para un usuario
     * @param usuario Usuario propietario
     * @param clave Clave de la preferencia
     * @return true si existe, false si no
     */
    boolean existsByUsuarioAndClave(Usuario usuario, String clave);

    /**
     * Cuenta preferencias de un usuario
     * @param usuario Usuario propietario
     * @return Número de preferencias del usuario
     */
    long countByUsuario(Usuario usuario);

    /**
     * Cuenta preferencias activas de un usuario
     * @param usuario Usuario propietario
     * @param activa Si la preferencia está activa
     * @return Número de preferencias activas del usuario
     */
    long countByUsuarioAndActiva(Usuario usuario, Boolean activa);

    /**
     * Busca todas las preferencias activas
     * @return Lista de todas las preferencias activas
     */
    @Query("SELECT p FROM PreferenciaUsuario p WHERE p.activa = true")
    List<PreferenciaUsuario> findAllPreferenciasActivas();

    /**
     * Busca preferencias por tipo (todos los usuarios)
     * @param tipoPreferencia Tipo de preferencia
     * @return Lista de preferencias del tipo especificado
     */
    List<PreferenciaUsuario> findByTipoPreferencia(PreferenciaUsuario.TipoPreferencia tipoPreferencia);

    /**
     * Busca preferencias por categoría (todos los usuarios)
     * @param categoria Categoría de las preferencias
     * @return Lista de preferencias de la categoría especificada
     */
    List<PreferenciaUsuario> findByCategoria(String categoria);

    /**
     * Elimina preferencias de un usuario por tipo
     * @param usuario Usuario propietario
     * @param tipoPreferencia Tipo de preferencia a eliminar
     */
    void deleteByUsuarioAndTipoPreferencia(Usuario usuario, PreferenciaUsuario.TipoPreferencia tipoPreferencia);
}
