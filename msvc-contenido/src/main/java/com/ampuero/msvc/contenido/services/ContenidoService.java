package com.ampuero.msvc.contenido.services;

import com.ampuero.msvc.contenido.dtos.ArticuloCreationDTO;
import com.ampuero.msvc.contenido.dtos.ArticuloEstadoDTO;
import com.ampuero.msvc.contenido.dtos.ComentarioCreationDTO;
import com.ampuero.msvc.contenido.models.Articulo;
import com.ampuero.msvc.contenido.models.Comentario;

import java.util.List;

public interface ContenidoService {
    // Gestión de Artículos
    List<Articulo> traerTodosArticulos();
    List<Articulo> traerArticulosPublicados();
    List<Articulo> traerArticulosDestacados();
    List<Articulo> traerArticulosPopulares();
    List<Articulo> traerArticulosRecientes();
    List<Articulo> traerArticulosPorCategoria(String categoria);
    List<Articulo> buscarArticulos(String busqueda);
    List<Articulo> traerArticulosGratuitos();
    List<Articulo> traerArticulosPremium();
    List<Articulo> traerArticulosPorAutor(String autor);
    Articulo traerArticuloPorId(Long id);
    Articulo traerArticuloPorTitulo(String titulo);
    Articulo crearArticulo(ArticuloCreationDTO articuloDetails);
    void eliminarArticulo(Long id);
    Articulo actualizarArticulo(Long id, Articulo articulo);
    Articulo actualizarEstadoArticulo(Long id, ArticuloEstadoDTO estadoDetails);
    Articulo incrementarVistas(Long id);
    Articulo darLikeArticulo(Long id);
    Articulo compartirArticulo(Long id);
    
    // Gestión de Comentarios
    List<Comentario> traerComentariosPorArticulo(Long idArticulo);
    List<Comentario> traerRespuestasPorComentario(Long idComentario);
    List<Comentario> traerComentariosPendientes();
    List<Comentario> traerComentariosPorUsuario(Long idUsuario);
    Comentario traerComentarioPorId(Long id);
    Comentario crearComentario(ComentarioCreationDTO comentarioDetails);
    void eliminarComentario(Long id);
    Comentario actualizarComentario(Long id, Comentario comentario);
    Comentario aprobarComentario(Long id);
    Comentario rechazarComentario(Long id);
    Comentario darLikeComentario(Long id);
}
