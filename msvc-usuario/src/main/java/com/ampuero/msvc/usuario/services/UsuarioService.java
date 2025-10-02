package com.ampuero.msvc.usuario.services;

import com.ampuero.msvc.usuario.dtos.UsuarioCreationDTO;
import com.ampuero.msvc.usuario.dtos.UsuarioResponseDTO;
import com.ampuero.msvc.usuario.dtos.UsuarioUpdateDTO;
import com.ampuero.msvc.usuario.entities.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz del servicio de usuarios
 * Define los métodos de negocio para la gestión de usuarios
 */
public interface UsuarioService {

    /**
     * Crea un nuevo usuario
     * @param usuarioCreationDTO Datos del usuario a crear
     * @return Usuario creado
     */
    UsuarioResponseDTO crearUsuario(UsuarioCreationDTO usuarioCreationDTO);

    /**
     * Busca un usuario por ID
     * @param id ID del usuario
     * @return Usuario encontrado
     */
    UsuarioResponseDTO obtenerUsuarioPorId(Long id);

    /**
     * Busca un usuario por correo electrónico
     * @param correo Correo del usuario
     * @return Usuario encontrado
     */
    UsuarioResponseDTO obtenerUsuarioPorCorreo(String correo);

    /**
     * Busca un usuario por código de referido
     * @param codigoReferido Código de referido
     * @return Usuario encontrado
     */
    UsuarioResponseDTO obtenerUsuarioPorCodigoReferido(String codigoReferido);

    /**
     * Obtiene todos los usuarios con paginación
     * @param pageable Configuración de paginación
     * @return Página de usuarios
     */
    Page<UsuarioResponseDTO> obtenerTodosLosUsuarios(Pageable pageable);

    /**
     * Obtiene usuarios por tipo
     * @param tipoUsuario Tipo de usuario
     * @param pageable Configuración de paginación
     * @return Página de usuarios del tipo especificado
     */
    Page<UsuarioResponseDTO> obtenerUsuariosPorTipo(Usuario.TipoUsuario tipoUsuario, Pageable pageable);

    /**
     * Obtiene usuarios por estado
     * @param estado Estado del usuario
     * @param pageable Configuración de paginación
     * @return Página de usuarios con el estado especificado
     */
    Page<UsuarioResponseDTO> obtenerUsuariosPorEstado(Usuario.EstadoUsuario estado, Pageable pageable);

    /**
     * Obtiene usuarios por nivel
     * @param nivelUsuario Nivel del usuario
     * @param pageable Configuración de paginación
     * @return Página de usuarios con el nivel especificado
     */
    Page<UsuarioResponseDTO> obtenerUsuariosPorNivel(Usuario.NivelUsuario nivelUsuario, Pageable pageable);

    /**
     * Obtiene usuarios referidos por otro usuario
     * @param codigoReferido Código del usuario que refirió
     * @param pageable Configuración de paginación
     * @return Página de usuarios referidos
     */
    Page<UsuarioResponseDTO> obtenerUsuariosReferidos(String codigoReferido, Pageable pageable);

    /**
     * Actualiza un usuario
     * @param id ID del usuario a actualizar
     * @param usuarioUpdateDTO Datos actualizados del usuario
     * @return Usuario actualizado
     */
    UsuarioResponseDTO actualizarUsuario(Long id, UsuarioUpdateDTO usuarioUpdateDTO);

    /**
     * Actualiza el estado de un usuario
     * @param id ID del usuario
     * @param nuevoEstado Nuevo estado
     * @return Usuario actualizado
     */
    UsuarioResponseDTO actualizarEstadoUsuario(Long id, Usuario.EstadoUsuario nuevoEstado);

    /**
     * Actualiza el nivel de un usuario
     * @param id ID del usuario
     * @param nuevoNivel Nuevo nivel
     * @return Usuario actualizado
     */
    UsuarioResponseDTO actualizarNivelUsuario(Long id, Usuario.NivelUsuario nuevoNivel);

    /**
     * Agrega puntos LevelUp a un usuario
     * @param id ID del usuario
     * @param puntos Puntos a agregar
     * @return Usuario actualizado
     */
    UsuarioResponseDTO agregarPuntosLevelUp(Long id, Integer puntos);

    /**
     * Verifica si un correo ya está en uso
     * @param correo Correo a verificar
     * @return true si está en uso, false si no
     */
    boolean existeUsuarioConCorreo(String correo);

    /**
     * Verifica si un código de referido ya está en uso
     * @param codigoReferido Código a verificar
     * @return true si está en uso, false si no
     */
    boolean existeUsuarioConCodigoReferido(String codigoReferido);

    /**
     * Genera un código de referido único
     * @return Código de referido único
     */
    String generarCodigoReferido();

    /**
     * Calcula el nivel de usuario basado en los puntos LevelUp
     * @param puntosLevelUp Puntos del usuario
     * @return Nivel calculado
     */
    Usuario.NivelUsuario calcularNivelUsuario(Integer puntosLevelUp);

    /**
     * Elimina un usuario (soft delete)
     * @param id ID del usuario a eliminar
     */
    void eliminarUsuario(Long id);

    /**
     * Reactiva un usuario eliminado
     * @param id ID del usuario a reactivar
     * @return Usuario reactivado
     */
    UsuarioResponseDTO reactivarUsuario(Long id);

    /**
     * Busca usuarios por criterios de búsqueda
     * @param nombre Nombre a buscar (opcional)
     * @param correo Correo a buscar (opcional)
     * @param ciudad Ciudad a buscar (opcional)
     * @param pageable Configuración de paginación
     * @return Página de usuarios que coinciden con los criterios
     */
    Page<UsuarioResponseDTO> buscarUsuarios(String nombre, String correo, String ciudad, Pageable pageable);

    /**
     * Obtiene estadísticas de usuarios
     * @return Mapa con estadísticas
     */
    java.util.Map<String, Object> obtenerEstadisticasUsuarios();
}
