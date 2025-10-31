package com.ampuero.msvc.resenia.services;

import com.ampuero.msvc.resenia.dtos.ReseniaCreationDTO;
import com.ampuero.msvc.resenia.dtos.ReseniaUpdateDTO;
import com.ampuero.msvc.resenia.exceptions.ResourceNotFoundException;
import com.ampuero.msvc.resenia.models.Resenia;
import com.ampuero.msvc.resenia.repositories.ReseniaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReseniaServiceImpl implements ReseniaService {

    @Autowired
    private ReseniaRepository reseniaRepository;

    @Override
    @Transactional
    public Resenia crearResenia(ReseniaCreationDTO reseniaDTO) {
        Resenia resenia = new Resenia();
        resenia.setIdProducto(reseniaDTO.getIdProducto());
        resenia.setIdUsuario(reseniaDTO.getIdUsuario());
        resenia.setUsuarioNombre(reseniaDTO.getUsuarioNombre());
        resenia.setRating(reseniaDTO.getRating());
        resenia.setComentario(reseniaDTO.getComentario());
        resenia.setActivo(true);
        return reseniaRepository.save(resenia);
    }

    @Override
    public Resenia traerReseniaPorId(Long id) {
        return reseniaRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reseña no encontrada con ID: " + id));
    }

    @Override
    public List<Resenia> traerReseniasPorProducto(Long idProducto) {
        return reseniaRepository.findByIdProductoOrderByFechaCreacionDesc(idProducto)
                .stream()
                .filter(Resenia::getActivo)
                .toList();
    }

    @Override
    public List<Resenia> traerReseniasPorUsuario(Long idUsuario) {
        return reseniaRepository.findByIdUsuarioAndActivoTrue(idUsuario);
    }

    @Override
    public List<Resenia> traerTodasResenias() {
        return reseniaRepository.findAll().stream()
                .filter(Resenia::getActivo)
                .toList();
    }

    @Override
    @Transactional
    public Resenia actualizarResenia(Long id, ReseniaUpdateDTO reseniaDTO) {
        Resenia resenia = traerReseniaPorId(id);
        
        if (reseniaDTO.getRating() != null) {
            resenia.setRating(reseniaDTO.getRating());
        }
        
        if (reseniaDTO.getComentario() != null && !reseniaDTO.getComentario().trim().isEmpty()) {
            resenia.setComentario(reseniaDTO.getComentario());
        }
        
        return reseniaRepository.save(resenia);
    }

    @Override
    @Transactional
    public void eliminarResenia(Long id) {
        Resenia resenia = traerReseniaPorId(id);
        resenia.setActivo(false);
        reseniaRepository.save(resenia);
    }

    @Override
    public Double calcularRatingPromedio(Long idProducto) {
        List<Resenia> resenias = reseniaRepository.findByIdProductoAndActivoTrue(idProducto);
        
        if (resenias.isEmpty()) {
            return 0.0;
        }
        
        double suma = resenias.stream()
                .mapToInt(Resenia::getRating)
                .sum();
        
        return suma / resenias.size();
    }
}

