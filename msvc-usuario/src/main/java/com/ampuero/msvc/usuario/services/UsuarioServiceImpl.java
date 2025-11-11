package com.ampuero.msvc.usuario.services;

import com.ampuero.msvc.usuario.dtos.UsuarioCreationDTO;
import com.ampuero.msvc.usuario.dtos.UsuarioResponseDTO;
import com.ampuero.msvc.usuario.dtos.UsuarioUpdateDTO;
import com.ampuero.msvc.usuario.entities.Usuario;
import com.ampuero.msvc.usuario.exceptions.DuplicateResourceException;
import com.ampuero.msvc.usuario.exceptions.ResourceNotFoundException;
import com.ampuero.msvc.usuario.exceptions.UsuarioException;
import com.ampuero.msvc.usuario.repositories.UsuarioRepository;
import com.ampuero.msvc.usuario.repositories.DireccionUsuarioRepository;
import com.ampuero.msvc.usuario.entities.DireccionUsuario;
import com.ampuero.msvc.usuario.dtos.DireccionUsuarioResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Implementación del servicio de usuarios
 * Contiene la lógica de negocio para la gestión de usuarios
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final DireccionUsuarioRepository direccionUsuarioRepository;
    private final Random random = new Random();

    @Override
    public UsuarioResponseDTO crearUsuario(UsuarioCreationDTO usuarioCreationDTO) {
        log.info("Creando nuevo usuario con correo: {}", usuarioCreationDTO.getCorreo());

        // Verificar si el correo ya existe
        if (existeUsuarioConCorreo(usuarioCreationDTO.getCorreo())) {
            throw new DuplicateResourceException("Ya existe un usuario con el correo: " + usuarioCreationDTO.getCorreo());
        }

        // Verificar si el RUN ya existe (si se proporciona)
        if (usuarioCreationDTO.getRunUsuario() != null && !usuarioCreationDTO.getRunUsuario().isEmpty()) {
            if (existeUsuarioConRun(usuarioCreationDTO.getRunUsuario())) {
                throw new DuplicateResourceException("Ya existe un usuario con el RUN: " + usuarioCreationDTO.getRunUsuario());
            }
        }

        // Crear el usuario
        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(usuarioCreationDTO, usuario);

        // Encriptar contraseña
        usuario.setPassword(passwordEncoder.encode(usuarioCreationDTO.getPassword()));

        // Generar código de referido único
        usuario.setCodigoReferido(generarCodigoReferido());

        // Validar código de referido si se proporciona
        if (usuarioCreationDTO.getReferidoPor() != null && !usuarioCreationDTO.getReferidoPor().isEmpty()) {
            Usuario usuarioReferidor = usuarioRepository.findByCodigoReferido(usuarioCreationDTO.getReferidoPor())
                    .orElseThrow(() -> new ResourceNotFoundException("Código de referido inválido: " + usuarioCreationDTO.getReferidoPor()));
            usuario.setReferidoPor(usuarioCreationDTO.getReferidoPor());
            
            // Agregar puntos al referidor
            agregarPuntosLevelUp(usuarioReferidor.getIdUsuario(), 50);
        }

        // Calcular nivel inicial
        usuario.setNivelUsuario(calcularNivelUsuario(usuario.getPuntosLevelUp()));

        // Guardar usuario
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        log.info("Usuario creado exitosamente con ID: {}", usuarioGuardado.getIdUsuario());

        return new UsuarioResponseDTO(usuarioGuardado);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO obtenerUsuarioPorId(Long id) {
        log.info("Obteniendo usuario por ID: {}", id);
        
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
        
        return new UsuarioResponseDTO(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO obtenerUsuarioPorCorreo(String correo) {
        log.info("Obteniendo usuario por correo: {}", correo);
        
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con correo: " + correo));
        
        return new UsuarioResponseDTO(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO obtenerUsuarioPorCodigoReferido(String codigoReferido) {
        log.info("Obteniendo usuario por código de referido: {}", codigoReferido);
        
        Usuario usuario = usuarioRepository.findByCodigoReferido(codigoReferido)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con código de referido: " + codigoReferido));
        
        return new UsuarioResponseDTO(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioResponseDTO> obtenerTodosLosUsuarios(Pageable pageable) {
        log.info("Obteniendo todos los usuarios con paginación");
        
        Page<Usuario> usuarios = usuarioRepository.findAll(pageable);
        return usuarios.map(UsuarioResponseDTO::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioResponseDTO> obtenerUsuariosPorTipo(Usuario.TipoUsuario tipoUsuario, Pageable pageable) {
        log.info("Obteniendo usuarios por tipo: {}", tipoUsuario);
        
        Page<Usuario> usuarios = usuarioRepository.findByTipoUsuario(tipoUsuario, pageable);
        return usuarios.map(UsuarioResponseDTO::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioResponseDTO> obtenerUsuariosPorEstado(Usuario.EstadoUsuario estado, Pageable pageable) {
        log.info("Obteniendo usuarios por estado: {}", estado);
        
        Page<Usuario> usuarios = usuarioRepository.findByEstado(estado, pageable);
        return usuarios.map(UsuarioResponseDTO::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioResponseDTO> obtenerUsuariosPorNivel(Usuario.NivelUsuario nivelUsuario, Pageable pageable) {
        log.info("Obteniendo usuarios por nivel: {}", nivelUsuario);
        
        Page<Usuario> usuarios = usuarioRepository.findByNivelUsuario(nivelUsuario, pageable);
        return usuarios.map(UsuarioResponseDTO::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioResponseDTO> obtenerUsuariosReferidos(String codigoReferido, Pageable pageable) {
        log.info("Obteniendo usuarios referidos por: {}", codigoReferido);
        
        Page<Usuario> usuarios = usuarioRepository.findByReferidoPor(codigoReferido, pageable);
        return usuarios.map(UsuarioResponseDTO::new);
    }

    @Override
    public UsuarioResponseDTO actualizarUsuario(Long id, UsuarioUpdateDTO usuarioUpdateDTO) {
        log.info("Actualizando usuario con ID: {}", id);
        
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));

        // Actualizar campos si se proporcionan
        if (usuarioUpdateDTO.getNombre() != null) {
            usuario.setNombre(usuarioUpdateDTO.getNombre());
        }
        if (usuarioUpdateDTO.getApellido() != null) {
            usuario.setApellido(usuarioUpdateDTO.getApellido());
        }
        if (usuarioUpdateDTO.getCorreo() != null && !usuarioUpdateDTO.getCorreo().equals(usuario.getCorreo())) {
            if (existeUsuarioConCorreo(usuarioUpdateDTO.getCorreo())) {
                throw new DuplicateResourceException("Ya existe un usuario con el correo: " + usuarioUpdateDTO.getCorreo());
            }
            usuario.setCorreo(usuarioUpdateDTO.getCorreo());
        }
        if (usuarioUpdateDTO.getPassword() != null) {
            usuario.setPassword(passwordEncoder.encode(usuarioUpdateDTO.getPassword()));
        }
        if (usuarioUpdateDTO.getTelefono() != null) {
            usuario.setTelefono(usuarioUpdateDTO.getTelefono());
        }
        if (usuarioUpdateDTO.getFechaNacimiento() != null) {
            usuario.setFechaNacimiento(usuarioUpdateDTO.getFechaNacimiento());
        }
        if (usuarioUpdateDTO.getGenero() != null) {
            usuario.setGenero(usuarioUpdateDTO.getGenero());
        }
        if (usuarioUpdateDTO.getDireccion() != null) {
            usuario.setDireccion(usuarioUpdateDTO.getDireccion());
        }
        if (usuarioUpdateDTO.getCiudad() != null) {
            usuario.setCiudad(usuarioUpdateDTO.getCiudad());
        }
        if (usuarioUpdateDTO.getPais() != null) {
            usuario.setPais(usuarioUpdateDTO.getPais());
        }
        if (usuarioUpdateDTO.getCodigoPostal() != null) {
            usuario.setCodigoPostal(usuarioUpdateDTO.getCodigoPostal());
        }
        if (usuarioUpdateDTO.getAvatarUrl() != null) {
            usuario.setAvatarUrl(usuarioUpdateDTO.getAvatarUrl());
        }
        // También aceptar "avatar" (compatibilidad con Kotlin)
        if (usuarioUpdateDTO.getAvatar() != null && usuarioUpdateDTO.getAvatarUrl() == null) {
            usuario.setAvatarUrl(usuarioUpdateDTO.getAvatar());
        }
        if (usuarioUpdateDTO.getTipoUsuario() != null) {
            usuario.setTipoUsuario(usuarioUpdateDTO.getTipoUsuario());
        }
        if (usuarioUpdateDTO.getEstado() != null) {
            usuario.setEstado(usuarioUpdateDTO.getEstado());
        }
        if (usuarioUpdateDTO.getEmailVerificado() != null) {
            usuario.setEmailVerificado(usuarioUpdateDTO.getEmailVerificado());
        }
        if (usuarioUpdateDTO.getTelefonoVerificado() != null) {
            usuario.setTelefonoVerificado(usuarioUpdateDTO.getTelefonoVerificado());
        }
        if (usuarioUpdateDTO.getAceptaMarketing() != null) {
            usuario.setAceptaMarketing(usuarioUpdateDTO.getAceptaMarketing());
        }
        if (usuarioUpdateDTO.getPuntosLevelUp() != null) {
            usuario.setPuntosLevelUp(usuarioUpdateDTO.getPuntosLevelUp());
            usuario.setNivelUsuario(calcularNivelUsuario(usuario.getPuntosLevelUp()));
        }
        if (usuarioUpdateDTO.getNivelUsuario() != null) {
            usuario.setNivelUsuario(usuarioUpdateDTO.getNivelUsuario());
        }

        // Actualizar último acceso
        usuario.setUltimoAcceso(LocalDateTime.now());

        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        log.info("Usuario actualizado exitosamente con ID: {}", usuarioActualizado.getIdUsuario());

        return new UsuarioResponseDTO(usuarioActualizado);
    }

    @Override
    public UsuarioResponseDTO actualizarEstadoUsuario(Long id, Usuario.EstadoUsuario nuevoEstado) {
        log.info("Actualizando estado del usuario ID: {} a {}", id, nuevoEstado);
        
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));

        usuario.setEstado(nuevoEstado);
        Usuario usuarioActualizado = usuarioRepository.save(usuario);

        return new UsuarioResponseDTO(usuarioActualizado);
    }

    @Override
    public UsuarioResponseDTO actualizarNivelUsuario(Long id, Usuario.NivelUsuario nuevoNivel) {
        log.info("Actualizando nivel del usuario ID: {} a {}", id, nuevoNivel);
        
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));

        usuario.setNivelUsuario(nuevoNivel);
        Usuario usuarioActualizado = usuarioRepository.save(usuario);

        return new UsuarioResponseDTO(usuarioActualizado);
    }

    @Override
    public UsuarioResponseDTO agregarPuntosLevelUp(Long id, Integer puntos) {
        log.info("Agregando {} puntos LevelUp al usuario ID: {}", puntos, id);
        
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));

        usuario.setPuntosLevelUp(usuario.getPuntosLevelUp() + puntos);
        usuario.setNivelUsuario(calcularNivelUsuario(usuario.getPuntosLevelUp()));
        
        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        log.info("Puntos agregados exitosamente. Nuevo total: {}", usuarioActualizado.getPuntosLevelUp());

        return new UsuarioResponseDTO(usuarioActualizado);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeUsuarioConCorreo(String correo) {
        return usuarioRepository.existsByCorreo(correo);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeUsuarioConRun(String runUsuario) {
        return usuarioRepository.existsByRunUsuario(runUsuario);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeUsuarioConCodigoReferido(String codigoReferido) {
        return usuarioRepository.findByCodigoReferido(codigoReferido).isPresent();
    }

    @Override
    public String generarCodigoReferido() {
        String codigo;
        do {
            codigo = "REF" + (100000 + random.nextInt(900000));
        } while (existeUsuarioConCodigoReferido(codigo));
        
        return codigo;
    }

    @Override
    public Usuario.NivelUsuario calcularNivelUsuario(Integer puntosLevelUp) {
        if (puntosLevelUp == null || puntosLevelUp < 0) {
            return Usuario.NivelUsuario.NOVATO;
        }

        return switch (puntosLevelUp / 100) {
            case 0 -> Usuario.NivelUsuario.NOVATO;
            case 1, 2 -> Usuario.NivelUsuario.BRONCE;
            case 3, 4, 5 -> Usuario.NivelUsuario.PLATA;
            case 6, 7, 8, 9 -> Usuario.NivelUsuario.ORO;
            case 10, 11, 12, 13, 14 -> Usuario.NivelUsuario.PLATINO;
            case 15, 16, 17, 18, 19, 20, 21, 22, 23, 24 -> Usuario.NivelUsuario.DIAMANTE;
            case 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39 -> Usuario.NivelUsuario.MAESTRO;
            default -> Usuario.NivelUsuario.GRAN_MAESTRO;
        };
    }

    @Override
    public void eliminarUsuario(Long id) {
        log.info("Eliminando usuario con ID: {}", id);
        
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));

        usuario.setEstado(Usuario.EstadoUsuario.INACTIVO);
        usuarioRepository.save(usuario);
        log.info("Usuario eliminado exitosamente con ID: {}", id);
    }

    @Override
    public UsuarioResponseDTO reactivarUsuario(Long id) {
        log.info("Reactivando usuario con ID: {}", id);
        
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));

        usuario.setEstado(Usuario.EstadoUsuario.ACTIVO);
        Usuario usuarioReactivado = usuarioRepository.save(usuario);

        return new UsuarioResponseDTO(usuarioReactivado);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioResponseDTO> buscarUsuarios(String nombre, String correo, String ciudad, Pageable pageable) {
        log.info("Buscando usuarios con criterios - nombre: {}, correo: {}, ciudad: {}", nombre, correo, ciudad);
        
        // Implementación simplificada - en un caso real usarías Criteria API o QueryDSL
        if (nombre != null && !nombre.isEmpty()) {
            Page<Usuario> usuarios = usuarioRepository.findAll(pageable);
            return usuarios.map(UsuarioResponseDTO::new)
                    .map(usuario -> {
                        if (usuario.getNombre().toLowerCase().contains(nombre.toLowerCase()) ||
                            usuario.getApellido().toLowerCase().contains(nombre.toLowerCase())) {
                            return usuario;
                        }
                        return null;
                    });
        }
        
        if (correo != null && !correo.isEmpty()) {
            return Page.empty();
        }
        
        if (ciudad != null && !ciudad.isEmpty()) {
            Page<Usuario> usuarios = usuarioRepository.findByCiudad(ciudad, pageable);
            return usuarios.map(UsuarioResponseDTO::new);
        }
        
        return obtenerTodosLosUsuarios(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> obtenerEstadisticasUsuarios() {
        log.info("Obteniendo estadísticas de usuarios");
        
        Map<String, Object> estadisticas = new HashMap<>();
        
        // Total de usuarios
        estadisticas.put("totalUsuarios", usuarioRepository.count());
        
        // Usuarios por tipo
        for (Usuario.TipoUsuario tipo : Usuario.TipoUsuario.values()) {
            estadisticas.put("usuariosTipo" + tipo.name(), usuarioRepository.countByTipoUsuario(tipo));
        }
        
        // Usuarios por estado
        for (Usuario.EstadoUsuario estado : Usuario.EstadoUsuario.values()) {
            estadisticas.put("usuariosEstado" + estado.name(), usuarioRepository.countByEstado(estado));
        }
        
        // Usuarios por nivel
        for (Usuario.NivelUsuario nivel : Usuario.NivelUsuario.values()) {
            estadisticas.put("usuariosNivel" + nivel.name(), usuarioRepository.findByNivelUsuario(nivel).size());
        }
        
        return estadisticas;
    }

    // ===== Perfil y Direcciones (consumo frontend) =====
    @Override
    public UsuarioResponseDTO getCurrentUserProfile(Long userId) {
        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        return new UsuarioResponseDTO(usuario);
    }

    @Override
    public UsuarioResponseDTO updateProfile(Long userId, UsuarioUpdateDTO request) {
        return actualizarUsuario(userId, request);
    }

    @Override
    public java.util.List<DireccionUsuarioResponseDTO> getUserAddresses(Long userId) {
        java.util.List<DireccionUsuario> list = direccionUsuarioRepository.findByUsuario_IdUsuario(userId);
        return list.stream().map(DireccionUsuarioResponseDTO::new).toList();
    }

    @Override
    public DireccionUsuarioResponseDTO addAddress(Long userId, DireccionUsuario direccion) {
        Usuario u = usuarioRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        direccion.setUsuario(u);
        DireccionUsuario saved = direccionUsuarioRepository.save(direccion);
        return new DireccionUsuarioResponseDTO(saved);
    }

    @Override
    public void deleteAddress(Long userId, Long direccionId) {
        DireccionUsuario dir = direccionUsuarioRepository.findById(direccionId)
                .orElseThrow(() -> new ResourceNotFoundException("Dirección no encontrada"));
        if (!dir.getUsuario().getIdUsuario().equals(userId)) {
            throw new UsuarioException("No autorizado a eliminar esta dirección");
        }
        direccionUsuarioRepository.deleteById(direccionId);
    }

    @Override
    @Transactional(readOnly = true)
    public com.ampuero.msvc.usuario.dtos.CredentialsValidationResponseDTO validarCredenciales(String correoUsuario, String password) {
        String identificador = correoUsuario != null ? correoUsuario.trim() : "";
        log.info("Validando credenciales para identificador: {}", identificador);
        
        try {
            if (identificador.isEmpty()) {
                log.warn("Identificador vacío recibido en validación de credenciales");
                return new com.ampuero.msvc.usuario.dtos.CredentialsValidationResponseDTO(
                        false, null, null, null, null, null, null, null, null,
                        "Credenciales inválidas"
                );
            }

            Usuario usuario = null;

            // Determinar si es correo (contiene '@') o nombre
            if (identificador.contains("@")) {
                usuario = usuarioRepository.findByCorreo(identificador.toLowerCase())
                        .orElse(null);
            } else {
                usuario = usuarioRepository.findFirstByNombreIgnoreCase(identificador)
                        .orElse(null);

                // Si no se encuentra por nombre, intentar por correo igualmente
                if (usuario == null) {
                    usuario = usuarioRepository.findByCorreo(identificador)
                            .orElse(null);
                }
            }
            
            if (usuario == null) {
                log.warn("Usuario no encontrado con identificador: {}", identificador);
                return new com.ampuero.msvc.usuario.dtos.CredentialsValidationResponseDTO(
                        false, null, null, null, null, null, null, null, null,
                        "Credenciales inválidas"
                );
            }
            
            // Verificar estado del usuario
            if (usuario.getEstado() != Usuario.EstadoUsuario.ACTIVO) {
                log.warn("Usuario inactivo con identificador: {}", identificador);
                // Calcular descuentoDuoc basado en el correo (si es @duoc.cl o @profesor.duoc.cl)
                Boolean descuentoDuoc = usuario.getCorreo() != null && 
                        (usuario.getCorreo().endsWith("@duoc.cl") || usuario.getCorreo().endsWith("@profesor.duoc.cl"));
                return new com.ampuero.msvc.usuario.dtos.CredentialsValidationResponseDTO(
                        false, usuario.getIdUsuario(), usuario.getNombre(), usuario.getApellido(),
                        usuario.getCorreo(), usuario.getTipoUsuario().name(), 
                        descuentoDuoc, usuario.getRegion(), usuario.getComuna(),
                        "Usuario inactivo"
                );
            }
            
            // Verificar contraseña
            if (!passwordEncoder.matches(password, usuario.getPassword())) {
                log.warn("Contraseña incorrecta para identificador: {}", identificador);
                // Calcular descuentoDuoc basado en el correo (si es @duoc.cl o @profesor.duoc.cl)
                Boolean descuentoDuoc = usuario.getCorreo() != null && 
                        (usuario.getCorreo().endsWith("@duoc.cl") || usuario.getCorreo().endsWith("@profesor.duoc.cl"));
                return new com.ampuero.msvc.usuario.dtos.CredentialsValidationResponseDTO(
                        false, usuario.getIdUsuario(), usuario.getNombre(), usuario.getApellido(),
                        usuario.getCorreo(), usuario.getTipoUsuario().name(),
                        descuentoDuoc, usuario.getRegion(), usuario.getComuna(),
                        "Credenciales inválidas"
                );
            }
            
            // Credenciales válidas
            log.info("Credenciales válidas para usuario ID: {}", usuario.getIdUsuario());
            // Calcular descuentoDuoc basado en el correo (si es @duoc.cl o @profesor.duoc.cl)
            Boolean descuentoDuoc = usuario.getCorreo() != null && 
                    (usuario.getCorreo().endsWith("@duoc.cl") || usuario.getCorreo().endsWith("@profesor.duoc.cl"));
            return new com.ampuero.msvc.usuario.dtos.CredentialsValidationResponseDTO(
                    true, usuario.getIdUsuario(), usuario.getNombre(), usuario.getApellido(),
                    usuario.getCorreo(), usuario.getTipoUsuario().name(),
                    descuentoDuoc, usuario.getRegion(), usuario.getComuna(),
                    "Credenciales válidas"
            );
            
        } catch (Exception e) {
            log.error("Error al validar credenciales: ", e);
            return new com.ampuero.msvc.usuario.dtos.CredentialsValidationResponseDTO(
                    false, null, null, null, null, null, null, null, null,
                    "Error interno al validar credenciales"
            );
        }
    }
}
