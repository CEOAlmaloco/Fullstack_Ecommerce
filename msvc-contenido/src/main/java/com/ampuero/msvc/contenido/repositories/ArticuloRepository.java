package com.ampuero.msvc.contenido.repositories;

import com.ampuero.msvc.contenido.models.Articulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ArticuloRepository extends JpaRepository<Articulo, Long> {
    
    @Query("SELECT a FROM Articulo a WHERE a.activo = true AND a.estadoArticulo = 'PUBLICADO' ORDER BY a.fechaPublicacion DESC")
    List<Articulo> findArticulosPublicados();
    
    @Query("SELECT a FROM Articulo a WHERE a.activo = true AND a.estadoArticulo = 'PUBLICADO' AND a.esDestacado = true ORDER BY a.fechaPublicacion DESC")
    List<Articulo> findArticulosDestacados();
    
    @Query("SELECT a FROM Articulo a WHERE a.activo = true AND a.estadoArticulo = 'PUBLICADO' ORDER BY a.vistasArticulo DESC")
    List<Articulo> findArticulosPopulares();
    
    @Query("SELECT a FROM Articulo a WHERE a.activo = true AND a.estadoArticulo = 'PUBLICADO' ORDER BY a.fechaPublicacion DESC")
    List<Articulo> findArticulosRecientes();
    
    @Query("SELECT a FROM Articulo a WHERE a.activo = true AND a.estadoArticulo = 'PUBLICADO' AND a.categoriaArticulo = :categoria ORDER BY a.fechaPublicacion DESC")
    List<Articulo> findArticulosPorCategoria(@Param("categoria") String categoria);
    
    @Query("SELECT a FROM Articulo a WHERE a.activo = true AND a.estadoArticulo = 'PUBLICADO' AND (a.tituloArticulo LIKE %:busqueda% OR a.contenidoArticulo LIKE %:busqueda% OR a.etiquetasArticulo LIKE %:busqueda%)")
    List<Articulo> buscarArticulos(@Param("busqueda") String busqueda);
    
    @Query("SELECT a FROM Articulo a WHERE a.activo = true AND a.estadoArticulo = 'PUBLICADO' AND a.esPremium = false")
    List<Articulo> findArticulosGratuitos();
    
    @Query("SELECT a FROM Articulo a WHERE a.activo = true AND a.estadoArticulo = 'PUBLICADO' AND a.esPremium = true")
    List<Articulo> findArticulosPremium();
    
    @Query("SELECT a FROM Articulo a WHERE a.activo = true AND a.autorArticulo = :autor ORDER BY a.fechaPublicacion DESC")
    List<Articulo> findArticulosPorAutor(@Param("autor") String autor);
    
    List<Articulo> findByActivoTrue();
    
    Optional<Articulo> findByTituloArticulo(String tituloArticulo);
}
