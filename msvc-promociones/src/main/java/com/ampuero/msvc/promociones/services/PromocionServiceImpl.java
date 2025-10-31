package com.ampuero.msvc.promociones.services;

import com.ampuero.msvc.promociones.dtos.PromocionCreationDTO;
import com.ampuero.msvc.promociones.dtos.PromocionEstadoDTO;
import com.ampuero.msvc.promociones.exceptions.PromocionException;
import com.ampuero.msvc.promociones.models.Promocion;
import com.ampuero.msvc.promociones.repositories.PromocionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio de gestión de promociones.
 * <p>
 * Esta clase maneja todas las operaciones CRUD relacionados con promociones.
 *
 * @author Level-Up Gamer Team
 * @version 1.0
 */
@Service
public class PromocionServiceImpl implements PromocionService {
    private static final Logger log = LoggerFactory.getLogger(PromocionServiceImpl.class);
    
    @Autowired
    private PromocionRepository promocionRepository;

    /**
     * Crea una nueva promoción en el sistema
     *
     * @param promocionDetails datos de promoción a crear
     * @throws PromocionException si el código de promoción ya existe
     */
    @Transactional
    @Override
    public Promocion crearPromocion(PromocionCreationDTO promocionDetails) {
        boolean existe = promocionRepository.existsByCodigoPromocion(promocionDetails.getCodigoPromocion());

        if (existe) {
            throw new PromocionException("Ya existe una promoción con ese código: " + promocionDetails.getCodigoPromocion());
        }

        Promocion promocionEntity = new Promocion();
        promocionEntity.setCodigoPromocion(promocionDetails.getCodigoPromocion());
        promocionEntity.setNombrePromocion(promocionDetails.getNombrePromocion());
        promocionEntity.setDescripcionPromocion(promocionDetails.getDescripcionPromocion());
        promocionEntity.setTipoDescuento(promocionDetails.getTipoDescuento());
        promocionEntity.setValorDescuento(promocionDetails.getValorDescuento());
        promocionEntity.setMontoMinimo(promocionDetails.getMontoMinimo());
        promocionEntity.setMontoMaximoDescuento(promocionDetails.getMontoMaximoDescuento());
        promocionEntity.setFechaInicio(promocionDetails.getFechaInicio());
        promocionEntity.setFechaFin(promocionDetails.getFechaFin());
        promocionEntity.setUsosMaximos(promocionDetails.getUsosMaximos());
        promocionEntity.setUsosPorUsuario(promocionDetails.getUsosPorUsuario());
        promocionEntity.setAplicableDuoc(promocionDetails.getAplicableDuoc());
        promocionEntity.setCategoriaAplicable(promocionDetails.getCategoriaAplicable());
        promocionEntity.setPuntosRequeridos(promocionDetails.getPuntosRequeridos() != null ? 
                promocionDetails.getPuntosRequeridos() : 0);
        promocionEntity.setTipoPromocion(promocionDetails.getTipoPromocion() != null ? 
                promocionDetails.getTipoPromocion() : "DESCUENTO");

        return promocionRepository.save(promocionEntity);
    }

    /**
     * Obtiene una lista con todas las promociones registradas
     *
     * @return Lista de promociones
     * @throws PromocionException si no hay promociones registradas
     */
    @Transactional(readOnly = true)
    @Override
    public List<Promocion> traerTodos() {
        return promocionRepository.findAll();
    }

    /**
     * Obtiene promociones activas
     *
     * @return Lista de promociones activas
     */
    @Transactional(readOnly = true)
    @Override
    public List<Promocion> traerPromocionesActivas() {
        return promocionRepository.findPromocionesActivas(LocalDateTime.now());
    }

    /**
     * Obtiene promociones aplicables para usuarios Duoc
     *
     * @return Lista de promociones Duoc activas
     */
    @Transactional(readOnly = true)
    @Override
    public List<Promocion> traerPromocionesDuocActivas() {
        return promocionRepository.findPromocionesDuocActivas(LocalDateTime.now());
    }

    /**
     * Obtiene promociones por categoría
     *
     * @param categoria categoría de productos
     * @return Lista de promociones para la categoría
     */
    @Transactional(readOnly = true)
    @Override
    public List<Promocion> traerPromocionesPorCategoria(String categoria) {
        return promocionRepository.findPromocionesPorCategoria(categoria, LocalDateTime.now());
    }

    /**
     * Obtiene una promoción por ID
     *
     * @param id ID de la promoción
     * @return promoción con el ID proporcionado
     * @throws PromocionException si el ID no existe
     */
    @Transactional(readOnly = true)
    @Override
    public Promocion traerPorId(Long id) {
        return promocionRepository.findById(id).orElseThrow(
                () -> new PromocionException("Promoción con id " + id + " no encontrada")
        );
    }

    /**
     * Obtiene una promoción por código
     *
     * @param codigo código de la promoción
     * @return promoción con el código proporcionado
     * @throws PromocionException si el código no existe
     */
    @Transactional(readOnly = true)
    @Override
    public Promocion traerPorCodigo(String codigo) {
        return promocionRepository.findByCodigoPromocion(codigo).orElseThrow(
                () -> new PromocionException("Promoción con código " + codigo + " no encontrada")
        );
    }

    /**
     * Actualiza los datos de una promoción por su ID
     *
     * @param idPromocion ID de la promoción
     * @param promocionDetails detalles de promoción para actualizar
     * @throws PromocionException si el ID no existe
     */
    @Transactional
    @Override
    public Promocion actualizarPromocion(Long idPromocion, Promocion promocionDetails) {
        return promocionRepository.findById(idPromocion).map(promocion -> {
            promocion.setCodigoPromocion(promocionDetails.getCodigoPromocion());
            promocion.setNombrePromocion(promocionDetails.getNombrePromocion());
            promocion.setDescripcionPromocion(promocionDetails.getDescripcionPromocion());
            promocion.setTipoDescuento(promocionDetails.getTipoDescuento());
            promocion.setValorDescuento(promocionDetails.getValorDescuento());
            promocion.setMontoMinimo(promocionDetails.getMontoMinimo());
            promocion.setMontoMaximoDescuento(promocionDetails.getMontoMaximoDescuento());
            promocion.setFechaInicio(promocionDetails.getFechaInicio());
            promocion.setFechaFin(promocionDetails.getFechaFin());
            promocion.setUsosMaximos(promocionDetails.getUsosMaximos());
            promocion.setUsosPorUsuario(promocionDetails.getUsosPorUsuario());
            promocion.setAplicableDuoc(promocionDetails.getAplicableDuoc());
            promocion.setCategoriaAplicable(promocionDetails.getCategoriaAplicable());
            promocion.setPuntosRequeridos(promocionDetails.getPuntosRequeridos());
            promocion.setTipoPromocion(promocionDetails.getTipoPromocion());
            return promocionRepository.save(promocion);
        }).orElseThrow(() -> new PromocionException("Promoción con id " + idPromocion + " no encontrada"));
    }

    /**
     * Elimina una promoción por su ID
     *
     * @param id ID de la promoción
     * @throws PromocionException si el ID no existe
     */
    @Transactional
    @Override
    public void eliminarPromocion(Long id) {
        Optional<Promocion> promocionOptional = promocionRepository.findById(id);

        if (promocionOptional.isEmpty()) {
            throw new PromocionException("No se pudo eliminar: Promoción con id " + id + " no encontrada");
        }

        promocionRepository.deleteById(id);
    }

    /**
     * Actualiza el estado de una promoción
     *
     * @param id ID de la promoción
     * @param promocionEstadoDetails estado a actualizar
     * @throws PromocionException si el ID no existe
     */
    @Transactional
    @Override
    public Promocion actualizarEstadoPromocion(Long id, PromocionEstadoDTO promocionEstadoDetails) {
        return promocionRepository.findById(id).map(promocion -> {
            promocion.setActivo(promocionEstadoDetails.getActivo());
            log.info("Estado actualizado: {} {}", promocionEstadoDetails.getActivo(), promocion);
            return promocionRepository.save(promocion);
        }).orElseThrow(() -> new PromocionException("Promoción con id " + id + " no encontrada"));
    }

    /**
     * Aplica una promoción a un pedido
     *
     * @param codigo código de la promoción
     * @param montoTotal monto total del pedido
     * @param correoUsuario correo del usuario
     * @return promoción aplicada
     * @throws PromocionException si la promoción no es válida
     */
    @Transactional
    @Override
    public Promocion aplicarPromocion(String codigo, Double montoTotal, String correoUsuario) {
        Promocion promocion = traerPorCodigo(codigo);
        
        if (!validarPromocion(codigo, montoTotal, correoUsuario)) {
            throw new PromocionException("La promoción no es válida para este pedido");
        }
        
        // Incrementar contador de usos
        promocion.setUsosActuales(promocion.getUsosActuales() + 1);
        return promocionRepository.save(promocion);
    }

    /**
     * Valida si una promoción es aplicable
     *
     * @param codigo código de la promoción
     * @param montoTotal monto total del pedido
     * @param correoUsuario correo del usuario
     * @return true si es válida, false en caso contrario
     */
    @Transactional(readOnly = true)
    @Override
    public boolean validarPromocion(String codigo, Double montoTotal, String correoUsuario) {
        try {
            Promocion promocion = traerPorCodigo(codigo);
            
            // Validar que esté activa
            if (!promocion.getActivo()) {
                return false;
            }
            
            // Validar fechas
            LocalDateTime ahora = LocalDateTime.now();
            if (ahora.isBefore(promocion.getFechaInicio()) || ahora.isAfter(promocion.getFechaFin())) {
                return false;
            }
            
            // Validar monto mínimo
            if (promocion.getMontoMinimo() != null && montoTotal < promocion.getMontoMinimo()) {
                return false;
            }
            
            // Validar usos máximos
            if (promocion.getUsosMaximos() != null && promocion.getUsosActuales() >= promocion.getUsosMaximos()) {
                return false;
            }
            
            // Validar si es aplicable para usuarios Duoc
            if (promocion.getAplicableDuoc() != null && promocion.getAplicableDuoc() && 
                !correoUsuario.endsWith("@duoc.cl") && !correoUsuario.endsWith("@profesor.duoc.cl")) {
                return false;
            }
            
            // Validar puntos requeridos para promociones canjeables con puntos
            if (promocion.getTipoPromocion() != null && 
                promocion.getTipoPromocion().equals("CANJE_PUNTOS") && 
                promocion.getPuntosRequeridos() != null && 
                promocion.getPuntosRequeridos() > 0) {
                // Esta validación se debe hacer con el microservicio de puntos
                // Por ahora retornamos true, pero se debe integrar
            }
            
            return true;
        } catch (PromocionException e) {
            return false;
        }
    }
}
