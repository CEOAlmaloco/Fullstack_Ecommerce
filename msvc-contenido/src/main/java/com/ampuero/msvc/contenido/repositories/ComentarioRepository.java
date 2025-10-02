package com.ampuero.msvc.contenido.repositories;

import com.ampuero.msvc.contenido.models.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    
    @Query("SELECT c FROM Comentario c WHERE c.activo = true AND c.idArticulo = :idArticulo AND c.estadoComentario = 'APROBADO' ORDER BY c.fechaComentario ASC")
    List<Comentario> findComentariosPorArticulo(@Param("idArticulo") Long idArticulo);
    
    @Query("SELECT c FROM Comentario c WHERE c.activo = true AND c.idComentarioPadre = :idComentarioPadre AND c.estadoComentario = 'APROBADO' ORDER BY c.fechaComentario ASC")
    List<Comentario> findRespuestasPorComentario(@Param("idComentarioPadre") Long idComentarioPadre);
    
    @Query("SELECT c FROM Comentario c WHERE c.activo = true AND c.estadoComentario = 'PENDIENTE' ORDER BY c.fechaComentario ASC")
    List<Comentario> findComentariosPendientes();
    
    @Query("SELECT c FROM Comentario c WHERE c.activo = true AND c.idUsuario = :idUsuario ORDER BY c.fechaComentario DESC")
    List<Comentario> findComentariosPorUsuario(@Param("idUsuario") Long idUsuario);
    
    List<Comentario> findByActivoTrue();
}
