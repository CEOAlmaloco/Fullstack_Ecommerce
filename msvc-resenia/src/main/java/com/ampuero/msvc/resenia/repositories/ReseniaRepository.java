package com.ampuero.msvc.resenia.repositories;

import com.ampuero.msvc.resenia.models.Resenia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReseniaRepository extends JpaRepository<Resenia, Long> {
    List<Resenia> findByIdProducto(Long idProducto);
    List<Resenia> findByIdUsuario(Long idUsuario);
    List<Resenia> findByIdProductoAndActivoTrue(Long idProducto);
    List<Resenia> findByIdUsuarioAndActivoTrue(Long idUsuario);
    Optional<Resenia> findByIdAndActivoTrue(Long id);
    List<Resenia> findByRating(Integer rating);
    List<Resenia> findByIdProductoOrderByFechaCreacionDesc(Long idProducto);
}

