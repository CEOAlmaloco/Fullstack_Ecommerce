package com.ampuero.msvc.producto.repositories;

import com.ampuero.msvc.producto.models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, String> {
}

