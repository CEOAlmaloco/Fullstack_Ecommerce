package com.ampuero.msvc.referidos.services;

import com.ampuero.msvc.referidos.dtos.ReferidoCreationDTO;
import com.ampuero.msvc.referidos.dtos.ReferidoEstadoDTO;
import com.ampuero.msvc.referidos.entities.Referido;
import com.ampuero.msvc.referidos.exceptions.ReferidoException;
import com.ampuero.msvc.referidos.exceptions.ResourceNotFoundException;
import com.ampuero.msvc.referidos.repositories.ReferidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ReferidoServiceImpl implements ReferidoService {

    @Autowired
    private ReferidoRepository referidoRepository;

    @Autowired
    private IntegracionService integracionService;

    @Override
    @Transactional
    public Referido crearReferido(ReferidoCreationDTO referidoCreationDTO) {

        // Validar que no exista el email en nuestro sistema
        if (referidoRepository.existsByEmailReferido(referidoCreationDTO.getEmailReferido())) {
            throw new ReferidoException("Ya existe un usuario registrado con este email");
        }

        // Validar que no exista el RUN en nuestro sistema
        if (referidoRepository.existsByRunReferido(referidoCreationDTO.getRunReferido())) {
            throw new ReferidoException("Ya existe un usuario registrado con este RUN");
        }

        // Validar con otros microservicios si el usuario ya existe
        if (integracionService.validarUsuarioExistente(referidoCreationDTO.getEmailReferido(), referidoCreationDTO.getRunReferido())) {
            throw new ReferidoException("Ya existe un usuario registrado en el sistema con este email o RUN");
        }

        // Validar código de referido si se proporciona
        Referido referidor = null;
        if (referidoCreationDTO.getCodigoReferido() != null && !referidoCreationDTO.getCodigoReferido().isEmpty()) {
            referidor = referidoRepository.findByCodigoReferido(referidoCreationDTO.getCodigoReferido())
                    .orElseThrow(() -> new ReferidoException("Código de referido no válido"));
        }

        // Crear el nuevo referido
        Referido nuevoReferido = new Referido();
        nuevoReferido.setNombreReferido(referidoCreationDTO.getNombreReferido());
        nuevoReferido.setApellidosReferido(referidoCreationDTO.getApellidosReferido());
        nuevoReferido.setEmailReferido(referidoCreationDTO.getEmailReferido());
        nuevoReferido.setRunReferido(referidoCreationDTO.getRunReferido());
        nuevoReferido.setCodigoReferido(generarCodigoReferido());
        nuevoReferido.setPuntosLevelup(0);
        nuevoReferido.setNivelUsuario("BRONZE");
        nuevoReferido.setFechaRegistro(LocalDateTime.now());
        nuevoReferido.setActivo(true);

        // Si hay referidor, asignarlo y otorgar puntos
        if (referidor != null) {
            nuevoReferido.setIdReferidor(referidor.getIdReferido());
            // Otorgar puntos al referidor
            sumarPuntosPorReferido(referidor.getIdReferido(), 10);

            // Notificar al referidor sobre el referido exitoso
            integracionService.notificarReferidoExitoso(referidor.getIdReferido(), nuevoReferido.getNombreReferido());
        }

        Referido referidoGuardado = referidoRepository.save(nuevoReferido);

        // Sincronizar con msvc-auth
        integracionService.sincronizarUsuarioConAuth(referidoGuardado.getIdReferido());

        return referidoGuardado;
    }

    @Override
    public List<Referido> traerTodos() {
        return referidoRepository.findAll();
    }

    @Override
    public Referido traerPorId(Long id) {
        return referidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Referido no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public Referido actualizarReferido(Long id, Referido referido) {
        Referido referidoExistente = traerPorId(id);

        referidoExistente.setNombreReferido(referido.getNombreReferido());
        referidoExistente.setApellidosReferido(referido.getApellidosReferido());
        referidoExistente.setEmailReferido(referido.getEmailReferido());
        referidoExistente.setRunReferido(referido.getRunReferido());

        return referidoRepository.save(referidoExistente);
    }

    @Override
    @Transactional
    public Referido actualizarEstadoReferido(Long id, ReferidoEstadoDTO referidoEstadoDTO) {
        Referido referido = traerPorId(id);
        referido.setActivo(referidoEstadoDTO.getActivo());
        return referidoRepository.save(referido);
    }

    @Override
    @Transactional
    public void eliminarReferido(Long id) {
        Referido referido = traerPorId(id);
        referidoRepository.delete(referido);
    }

    @Override
    public Referido buscarPorCodigoReferido(String codigoReferido) {
        return referidoRepository.findByCodigoReferido(codigoReferido)
                .orElseThrow(() -> new ResourceNotFoundException("Referido no encontrado con código: " + codigoReferido));
    }

    @Override
    public Referido buscarPorEmail(String email) {
        return referidoRepository.findByEmailReferido(email)
                .orElseThrow(() -> new ResourceNotFoundException("Referido no encontrado con email: " + email));
    }

    @Override
    public Referido buscarPorRun(String run) {
        return referidoRepository.findByRunReferido(run)
                .orElseThrow(() -> new ResourceNotFoundException("Referido no encontrado con RUN: " + run));
    }

    @Override
    public List<Referido> buscarReferidosPorReferidor(Long idReferidor) {
        return referidoRepository.findReferidosByReferidor(idReferidor);
    }

    @Override
    public String generarCodigoReferido() {
        String codigo;
        do {
            codigo = "REF" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } while (referidoRepository.existsByCodigoReferido(codigo));
        return codigo;
    }

    @Override
    @Transactional
    public void sumarPuntosPorReferido(Long idReferidor, Integer puntos) {
        Referido referidor = traerPorId(idReferidor);
        String nivelAnterior = referidor.getNivelUsuario();

        referidor.setPuntosLevelup(referidor.getPuntosLevelup() + puntos);
        String nuevoNivel = calcularNivel(referidor.getPuntosLevelup());
        referidor.setNivelUsuario(nuevoNivel);

        referidoRepository.save(referidor);

        // Notificar puntos otorgados
        integracionService.notificarPuntosOtorgados(idReferidor, puntos);

        // Notificar si ascendió de nivel
        if (!nivelAnterior.equals(nuevoNivel)) {
            integracionService.notificarNivelAscendido(idReferidor, nuevoNivel);
            integracionService.actualizarPerfilUsuario(idReferidor, nuevoNivel);
        }
    }

    @Override
    public String calcularNivel(Integer puntos) {
        if (puntos >= 600) {
            return "PLATINUM";
        } else if (puntos >= 300) {
            return "GOLD";
        } else if (puntos >= 100) {
            return "SILVER";
        } else {
            return "BRONZE";
        }
    }

    @Override
    public boolean validarCodigoReferido(String codigoReferido) {
        return referidoRepository.existsByCodigoReferido(codigoReferido);
    }

    @Override
    @Transactional
    public Map<String, Object> canjearProductoPorPuntos(Long idReferido, Long idProducto, Integer puntosRequeridos) {
        Referido referido = traerPorId(idReferido);

        // Verificar que tenga suficientes puntos
        if (referido.getPuntosLevelup() < puntosRequeridos) {
            throw new ReferidoException("Puntos insuficientes para realizar el canje");
        }

        // Procesar canje con otros microservicios
        Map<String, Object> resultado = integracionService.procesarCanjeProducto(idReferido, idProducto, puntosRequeridos);

        if (resultado.containsKey("error")) {
            throw new ReferidoException("Error al procesar canje: " + resultado.get("error"));
        }

        // Descontar puntos del usuario
        referido.setPuntosLevelup(referido.getPuntosLevelup() - puntosRequeridos);
        referidoRepository.save(referido);

        // Notificar canje realizado
        String nombreProducto = resultado.get("producto") != null ? resultado.get("producto").toString() : "Producto";
        integracionService.notificarCanjeRealizado(idReferido, nombreProducto, puntosRequeridos);

        return resultado;
    }

    @Override
    public List<Map<String, Object>> obtenerProductosCanjeables(Integer puntos) {
        try {
            return integracionService.obtenerDescuentosDisponibles("", puntos).get("promocionesPuntos") != null ?
                    (List<Map<String, Object>>) integracionService.obtenerDescuentosDisponibles("", puntos).get("promocionesPuntos") :
                    List.of();
        } catch (Exception e) {
            return List.of();
        }
    }

    @Override
    public Map<String, Object> obtenerDescuentosDisponibles(Long idReferido) {
        Referido referido = traerPorId(idReferido);
        return integracionService.obtenerDescuentosDisponibles(referido.getNivelUsuario(), referido.getPuntosLevelup());
    }
}
