package com.ampuero.msvc.promociones.services;

import com.ampuero.msvc.promociones.dtos.PromocionCreationDTO;
import com.ampuero.msvc.promociones.dtos.PromocionEstadoDTO;
import com.ampuero.msvc.promociones.models.Promocion;

import java.util.List;

public interface PromocionService {
    List<Promocion> traerTodos();
    
    List<Promocion> traerPromocionesActivas();
    
    List<Promocion> traerPromocionesDuocActivas();
    
    List<Promocion> traerPromocionesPorCategoria(String categoria);
    
    Promocion traerPorId(Long id);
    
    Promocion traerPorCodigo(String codigo);
    
    Promocion crearPromocion(PromocionCreationDTO promocionDetails);
    
    void eliminarPromocion(Long id);
    
    Promocion actualizarPromocion(Long id, Promocion promocion);
    
    Promocion actualizarEstadoPromocion(Long id, PromocionEstadoDTO promocionEstadoDetails);
    
    Promocion aplicarPromocion(String codigo, Double montoTotal, String correoUsuario);
    
    boolean validarPromocion(String codigo, Double montoTotal, String correoUsuario);
}
