package com.ampuero.msvc.resenia.services;

import com.ampuero.msvc.resenia.dtos.ReseniaCreationDTO;
import com.ampuero.msvc.resenia.dtos.ReseniaUpdateDTO;
import com.ampuero.msvc.resenia.models.Resenia;

import java.util.List;

public interface ReseniaService {
    Resenia crearResenia(ReseniaCreationDTO reseniaDTO);
    Resenia traerReseniaPorId(Long id);
    List<Resenia> traerReseniasPorProducto(Long idProducto);
    List<Resenia> traerReseniasPorUsuario(Long idUsuario);
    List<Resenia> traerTodasResenias();
    Resenia actualizarResenia(Long id, ReseniaUpdateDTO reseniaDTO);
    void eliminarResenia(Long id);
    Double calcularRatingPromedio(Long idProducto);
}

