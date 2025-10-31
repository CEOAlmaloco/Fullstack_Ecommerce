package com.ampuero.msvc.producto.repositories;

import com.ampuero.msvc.producto.models.Subcategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubcategoriaRepository extends JpaRepository<Subcategoria, String> {
    List<Subcategoria> findByCategoriaId(String categoriaId);
}

