package com.ampuero.msvc.contenido.services;

import com.ampuero.msvc.contenido.dtos.ArticuloCreationDTO;
import com.ampuero.msvc.contenido.dtos.ArticuloEstadoDTO;
import com.ampuero.msvc.contenido.dtos.ComentarioCreationDTO;
import com.ampuero.msvc.contenido.exceptions.ContenidoException;
import com.ampuero.msvc.contenido.models.Articulo;
import com.ampuero.msvc.contenido.models.Comentario;
import com.ampuero.msvc.contenido.repositories.ArticuloRepository;
import com.ampuero.msvc.contenido.repositories.ComentarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio de gestión de contenido.
 * <p>
 * Esta clase maneja todas las operaciones CRUD relacionados con artículos y comentarios.
 *
 * @author Level-Up Gamer Team
 * @version 1.0
 */
@Service
public class ContenidoServiceImpl implements ContenidoService {
    private static final Logger log = LoggerFactory.getLogger(ContenidoServiceImpl.class);
    
    @Autowired
    private ArticuloRepository articuloRepository;
    
    @Autowired
    private ComentarioRepository comentarioRepository;

    // ========== GESTIÓN DE ARTÍCULOS ==========

    /**
     * Crea un nuevo artículo en el sistema
     */
    @Transactional
    @Override
    public Articulo crearArticulo(ArticuloCreationDTO articuloDetails) {
        Optional<Articulo> articuloExistente = articuloRepository.findByTituloArticulo(articuloDetails.getTituloArticulo());

        if (articuloExistente.isPresent()) {
            throw new ContenidoException("Ya existe un artículo con ese título: " + articuloDetails.getTituloArticulo());
        }

        Articulo articuloEntity = new Articulo();
        articuloEntity.setTituloArticulo(articuloDetails.getTituloArticulo());
        articuloEntity.setContenidoArticulo(articuloDetails.getContenidoArticulo());
        articuloEntity.setResumenArticulo(articuloDetails.getResumenArticulo());
        articuloEntity.setImagenArticulo(articuloDetails.getImagenArticulo());
        articuloEntity.setCategoriaArticulo(articuloDetails.getCategoriaArticulo());
        articuloEntity.setEtiquetasArticulo(articuloDetails.getEtiquetasArticulo());
        articuloEntity.setAutorArticulo(articuloDetails.getAutorArticulo());
        articuloEntity.setFechaPublicacion(articuloDetails.getFechaPublicacion() != null ? 
            articuloDetails.getFechaPublicacion() : LocalDateTime.now());
        articuloEntity.setEstadoArticulo(articuloDetails.getEstadoArticulo() != null ? 
            articuloDetails.getEstadoArticulo() : "BORRADOR");
        articuloEntity.setTiempoLectura(articuloDetails.getTiempoLectura());
        articuloEntity.setEsDestacado(articuloDetails.getEsDestacado());
        articuloEntity.setEsPremium(articuloDetails.getEsPremium());

        return articuloRepository.save(articuloEntity);
    }

    /**
     * Obtiene todos los artículos
     */
    @Transactional(readOnly = true)
    @Override
    public List<Articulo> traerTodosArticulos() {
        List<Articulo> articulos = articuloRepository.findAll();
        if (articulos.isEmpty()) {
            throw new ContenidoException("No hay artículos registrados");
        }
        return articulos;
    }

    /**
     * Obtiene artículos publicados
     */
    @Transactional(readOnly = true)
    @Override
    public List<Articulo> traerArticulosPublicados() {
        return articuloRepository.findArticulosPublicados();
    }

    /**
     * Obtiene artículos destacados
     */
    @Transactional(readOnly = true)
    @Override
    public List<Articulo> traerArticulosDestacados() {
        return articuloRepository.findArticulosDestacados();
    }

    /**
     * Obtiene artículos populares
     */
    @Transactional(readOnly = true)
    @Override
    public List<Articulo> traerArticulosPopulares() {
        return articuloRepository.findArticulosPopulares();
    }

    /**
     * Obtiene artículos recientes
     */
    @Transactional(readOnly = true)
    @Override
    public List<Articulo> traerArticulosRecientes() {
        return articuloRepository.findArticulosRecientes();
    }

    /**
     * Obtiene artículos por categoría
     */
    @Transactional(readOnly = true)
    @Override
    public List<Articulo> traerArticulosPorCategoria(String categoria) {
        return articuloRepository.findArticulosPorCategoria(categoria);
    }

    /**
     * Busca artículos por texto
     */
    @Transactional(readOnly = true)
    @Override
    public List<Articulo> buscarArticulos(String busqueda) {
        return articuloRepository.buscarArticulos(busqueda);
    }

    /**
     * Obtiene artículos gratuitos
     */
    @Transactional(readOnly = true)
    @Override
    public List<Articulo> traerArticulosGratuitos() {
        return articuloRepository.findArticulosGratuitos();
    }

    /**
     * Obtiene artículos premium
     */
    @Transactional(readOnly = true)
    @Override
    public List<Articulo> traerArticulosPremium() {
        return articuloRepository.findArticulosPremium();
    }

    /**
     * Obtiene artículos por autor
     */
    @Transactional(readOnly = true)
    @Override
    public List<Articulo> traerArticulosPorAutor(String autor) {
        return articuloRepository.findArticulosPorAutor(autor);
    }

    /**
     * Obtiene un artículo por ID
     */
    @Transactional(readOnly = true)
    @Override
    public Articulo traerArticuloPorId(Long id) {
        return articuloRepository.findById(id).orElseThrow(
                () -> new ContenidoException("Artículo con id " + id + " no encontrado")
        );
    }

    /**
     * Obtiene un artículo por título
     */
    @Transactional(readOnly = true)
    @Override
    public Articulo traerArticuloPorTitulo(String titulo) {
        return articuloRepository.findByTituloArticulo(titulo).orElseThrow(
                () -> new ContenidoException("Artículo con título " + titulo + " no encontrado")
        );
    }

    /**
     * Actualiza un artículo
     */
    @Transactional
    @Override
    public Articulo actualizarArticulo(Long id, Articulo articuloDetails) {
        return articuloRepository.findById(id).map(articulo -> {
            articulo.setTituloArticulo(articuloDetails.getTituloArticulo());
            articulo.setContenidoArticulo(articuloDetails.getContenidoArticulo());
            articulo.setResumenArticulo(articuloDetails.getResumenArticulo());
            articulo.setImagenArticulo(articuloDetails.getImagenArticulo());
            articulo.setCategoriaArticulo(articuloDetails.getCategoriaArticulo());
            articulo.setEtiquetasArticulo(articuloDetails.getEtiquetasArticulo());
            articulo.setAutorArticulo(articuloDetails.getAutorArticulo());
            articulo.setFechaActualizacion(LocalDateTime.now());
            articulo.setTiempoLectura(articuloDetails.getTiempoLectura());
            articulo.setEsDestacado(articuloDetails.getEsDestacado());
            articulo.setEsPremium(articuloDetails.getEsPremium());
            return articuloRepository.save(articulo);
        }).orElseThrow(() -> new ContenidoException("Artículo con id " + id + " no encontrado"));
    }

    /**
     * Actualiza el estado de un artículo
     */
    @Transactional
    @Override
    public Articulo actualizarEstadoArticulo(Long id, ArticuloEstadoDTO estadoDetails) {
        return articuloRepository.findById(id).map(articulo -> {
            articulo.setEstadoArticulo(estadoDetails.getEstadoArticulo());
            if ("PUBLICADO".equals(estadoDetails.getEstadoArticulo()) && articulo.getFechaPublicacion() == null) {
                articulo.setFechaPublicacion(LocalDateTime.now());
            }
            log.info("Estado actualizado: {} {}", estadoDetails.getEstadoArticulo(), articulo);
            return articuloRepository.save(articulo);
        }).orElseThrow(() -> new ContenidoException("Artículo con id " + id + " no encontrado"));
    }

    /**
     * Incrementa las vistas de un artículo
     */
    @Transactional
    @Override
    public Articulo incrementarVistas(Long id) {
        Articulo articulo = traerArticuloPorId(id);
        articulo.setVistasArticulo(articulo.getVistasArticulo() + 1);
        return articuloRepository.save(articulo);
    }

    /**
     * Da like a un artículo
     */
    @Transactional
    @Override
    public Articulo darLikeArticulo(Long id) {
        Articulo articulo = traerArticuloPorId(id);
        articulo.setLikesArticulo(articulo.getLikesArticulo() + 1);
        return articuloRepository.save(articulo);
    }

    /**
     * Comparte un artículo
     */
    @Transactional
    @Override
    public Articulo compartirArticulo(Long id) {
        Articulo articulo = traerArticuloPorId(id);
        articulo.setCompartidosArticulo(articulo.getCompartidosArticulo() + 1);
        return articuloRepository.save(articulo);
    }

    /**
     * Elimina un artículo
     */
    @Transactional
    @Override
    public void eliminarArticulo(Long id) {
        Optional<Articulo> articuloOptional = articuloRepository.findById(id);
        if (articuloOptional.isEmpty()) {
            throw new ContenidoException("No se pudo eliminar: Artículo con id " + id + " no encontrado");
        }
        articuloRepository.deleteById(id);
    }

    // ========== GESTIÓN DE COMENTARIOS ==========

    /**
     * Crea un nuevo comentario
     */
    @Transactional
    @Override
    public Comentario crearComentario(ComentarioCreationDTO comentarioDetails) {
        Comentario comentarioEntity = new Comentario();
        comentarioEntity.setIdArticulo(comentarioDetails.getIdArticulo());
        comentarioEntity.setIdUsuario(comentarioDetails.getIdUsuario());
        comentarioEntity.setNombreUsuario(comentarioDetails.getNombreUsuario());
        comentarioEntity.setContenidoComentario(comentarioDetails.getContenidoComentario());
        comentarioEntity.setFechaComentario(LocalDateTime.now());
        comentarioEntity.setEstadoComentario("PENDIENTE");
        comentarioEntity.setIdComentarioPadre(comentarioDetails.getIdComentarioPadre());

        return comentarioRepository.save(comentarioEntity);
    }

    /**
     * Obtiene comentarios por artículo
     */
    @Transactional(readOnly = true)
    @Override
    public List<Comentario> traerComentariosPorArticulo(Long idArticulo) {
        return comentarioRepository.findComentariosPorArticulo(idArticulo);
    }

    /**
     * Obtiene respuestas por comentario
     */
    @Transactional(readOnly = true)
    @Override
    public List<Comentario> traerRespuestasPorComentario(Long idComentario) {
        return comentarioRepository.findRespuestasPorComentario(idComentario);
    }

    /**
     * Obtiene comentarios pendientes
     */
    @Transactional(readOnly = true)
    @Override
    public List<Comentario> traerComentariosPendientes() {
        return comentarioRepository.findComentariosPendientes();
    }

    /**
     * Obtiene comentarios por usuario
     */
    @Transactional(readOnly = true)
    @Override
    public List<Comentario> traerComentariosPorUsuario(Long idUsuario) {
        return comentarioRepository.findComentariosPorUsuario(idUsuario);
    }

    /**
     * Obtiene un comentario por ID
     */
    @Transactional(readOnly = true)
    @Override
    public Comentario traerComentarioPorId(Long id) {
        return comentarioRepository.findById(id).orElseThrow(
                () -> new ContenidoException("Comentario con id " + id + " no encontrado")
        );
    }

    /**
     * Actualiza un comentario
     */
    @Transactional
    @Override
    public Comentario actualizarComentario(Long id, Comentario comentarioDetails) {
        return comentarioRepository.findById(id).map(comentario -> {
            comentario.setContenidoComentario(comentarioDetails.getContenidoComentario());
            return comentarioRepository.save(comentario);
        }).orElseThrow(() -> new ContenidoException("Comentario con id " + id + " no encontrado"));
    }

    /**
     * Aprueba un comentario
     */
    @Transactional
    @Override
    public Comentario aprobarComentario(Long id) {
        Comentario comentario = traerComentarioPorId(id);
        comentario.setEstadoComentario("APROBADO");
        return comentarioRepository.save(comentario);
    }

    /**
     * Rechaza un comentario
     */
    @Transactional
    @Override
    public Comentario rechazarComentario(Long id) {
        Comentario comentario = traerComentarioPorId(id);
        comentario.setEstadoComentario("RECHAZADO");
        return comentarioRepository.save(comentario);
    }

    /**
     * Da like a un comentario
     */
    @Transactional
    @Override
    public Comentario darLikeComentario(Long id) {
        Comentario comentario = traerComentarioPorId(id);
        comentario.setLikesComentario(comentario.getLikesComentario() + 1);
        return comentarioRepository.save(comentario);
    }

    /**
     * Elimina un comentario
     */
    @Transactional
    @Override
    public void eliminarComentario(Long id) {
        Optional<Comentario> comentarioOptional = comentarioRepository.findById(id);
        if (comentarioOptional.isEmpty()) {
            throw new ContenidoException("No se pudo eliminar: Comentario con id " + id + " no encontrado");
        }
        comentarioRepository.deleteById(id);
    }
}
