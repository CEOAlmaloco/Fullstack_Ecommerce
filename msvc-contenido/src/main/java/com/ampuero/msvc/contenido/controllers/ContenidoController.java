package com.ampuero.msvc.contenido.controllers;

import com.ampuero.msvc.contenido.dtos.ArticuloCreationDTO;
import com.ampuero.msvc.contenido.dtos.ArticuloEstadoDTO;
import com.ampuero.msvc.contenido.dtos.ArticuloResponseDTO;
import com.ampuero.msvc.contenido.dtos.ComentarioCreationDTO;
import com.ampuero.msvc.contenido.dtos.ComentarioResponseDTO;
import com.ampuero.msvc.contenido.models.Articulo;
import com.ampuero.msvc.contenido.models.Comentario;
import com.ampuero.msvc.contenido.services.ContenidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/contenido")
public class ContenidoController {

    @Autowired
    private ContenidoService contenidoService;

    // ========== ENDPOINTS DE ARTÍCULOS ==========

    /**
     * Crear nuevo artículo
     * POST: /contenido/articulos
     */
    @PostMapping("/articulos")
    public ResponseEntity<ArticuloResponseDTO> crearArticulo(@Valid @RequestBody ArticuloCreationDTO articuloDetails) {
        Articulo articulo = contenidoService.crearArticulo(articuloDetails);
        ArticuloResponseDTO response = convertirArticuloAResponseDTO(articulo);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Obtener todos los artículos
     * GET: /contenido/articulos
     */
    @GetMapping("/articulos")
    public ResponseEntity<List<ArticuloResponseDTO>> traerTodosArticulos() {
        List<Articulo> articulos = contenidoService.traerTodosArticulos();
        List<ArticuloResponseDTO> response = articulos.stream()
                .map(this::convertirArticuloAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener artículos publicados
     * GET: /contenido/articulos/publicados
     */
    @GetMapping("/articulos/publicados")
    public ResponseEntity<List<ArticuloResponseDTO>> traerArticulosPublicados() {
        List<Articulo> articulos = contenidoService.traerArticulosPublicados();
        List<ArticuloResponseDTO> response = articulos.stream()
                .map(this::convertirArticuloAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener artículos destacados
     * GET: /contenido/articulos/destacados
     */
    @GetMapping("/articulos/destacados")
    public ResponseEntity<List<ArticuloResponseDTO>> traerArticulosDestacados() {
        List<Articulo> articulos = contenidoService.traerArticulosDestacados();
        List<ArticuloResponseDTO> response = articulos.stream()
                .map(this::convertirArticuloAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener artículos populares
     * GET: /contenido/articulos/populares
     */
    @GetMapping("/articulos/populares")
    public ResponseEntity<List<ArticuloResponseDTO>> traerArticulosPopulares() {
        List<Articulo> articulos = contenidoService.traerArticulosPopulares();
        List<ArticuloResponseDTO> response = articulos.stream()
                .map(this::convertirArticuloAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener artículos recientes
     * GET: /contenido/articulos/recientes
     */
    @GetMapping("/articulos/recientes")
    public ResponseEntity<List<ArticuloResponseDTO>> traerArticulosRecientes() {
        List<Articulo> articulos = contenidoService.traerArticulosRecientes();
        List<ArticuloResponseDTO> response = articulos.stream()
                .map(this::convertirArticuloAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener artículos por categoría
     * GET: /contenido/articulos/categoria/{categoria}
     */
    @GetMapping("/articulos/categoria/{categoria}")
    public ResponseEntity<List<ArticuloResponseDTO>> traerArticulosPorCategoria(@PathVariable String categoria) {
        List<Articulo> articulos = contenidoService.traerArticulosPorCategoria(categoria);
        List<ArticuloResponseDTO> response = articulos.stream()
                .map(this::convertirArticuloAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Buscar artículos
     * GET: /contenido/articulos/buscar?q={busqueda}
     */
    @GetMapping("/articulos/buscar")
    public ResponseEntity<List<ArticuloResponseDTO>> buscarArticulos(@RequestParam String q) {
        List<Articulo> articulos = contenidoService.buscarArticulos(q);
        List<ArticuloResponseDTO> response = articulos.stream()
                .map(this::convertirArticuloAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener artículos gratuitos
     * GET: /contenido/articulos/gratuitos
     */
    @GetMapping("/articulos/gratuitos")
    public ResponseEntity<List<ArticuloResponseDTO>> traerArticulosGratuitos() {
        List<Articulo> articulos = contenidoService.traerArticulosGratuitos();
        List<ArticuloResponseDTO> response = articulos.stream()
                .map(this::convertirArticuloAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener artículos premium
     * GET: /contenido/articulos/premium
     */
    @GetMapping("/articulos/premium")
    public ResponseEntity<List<ArticuloResponseDTO>> traerArticulosPremium() {
        List<Articulo> articulos = contenidoService.traerArticulosPremium();
        List<ArticuloResponseDTO> response = articulos.stream()
                .map(this::convertirArticuloAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener artículos por autor
     * GET: /contenido/articulos/autor/{autor}
     */
    @GetMapping("/articulos/autor/{autor}")
    public ResponseEntity<List<ArticuloResponseDTO>> traerArticulosPorAutor(@PathVariable String autor) {
        List<Articulo> articulos = contenidoService.traerArticulosPorAutor(autor);
        List<ArticuloResponseDTO> response = articulos.stream()
                .map(this::convertirArticuloAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener artículo por ID
     * GET: /contenido/articulos/{id}
     */
    @GetMapping("/articulos/{id}")
    public ResponseEntity<ArticuloResponseDTO> traerArticuloPorId(@PathVariable Long id) {
        Articulo articulo = contenidoService.traerArticuloPorId(id);
        ArticuloResponseDTO response = convertirArticuloAResponseDTO(articulo);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener artículo por título
     * GET: /contenido/articulos/titulo/{titulo}
     */
    @GetMapping("/articulos/titulo/{titulo}")
    public ResponseEntity<ArticuloResponseDTO> traerArticuloPorTitulo(@PathVariable String titulo) {
        Articulo articulo = contenidoService.traerArticuloPorTitulo(titulo);
        ArticuloResponseDTO response = convertirArticuloAResponseDTO(articulo);
        return ResponseEntity.ok(response);
    }

    /**
     * Incrementar vistas de artículo
     * POST: /contenido/articulos/{id}/vista
     */
    @PostMapping("/articulos/{id}/vista")
    public ResponseEntity<ArticuloResponseDTO> incrementarVistas(@PathVariable Long id) {
        Articulo articulo = contenidoService.incrementarVistas(id);
        ArticuloResponseDTO response = convertirArticuloAResponseDTO(articulo);
        return ResponseEntity.ok(response);
    }

    /**
     * Dar like a artículo
     * POST: /contenido/articulos/{id}/like
     */
    @PostMapping("/articulos/{id}/like")
    public ResponseEntity<ArticuloResponseDTO> darLikeArticulo(@PathVariable Long id) {
        Articulo articulo = contenidoService.darLikeArticulo(id);
        ArticuloResponseDTO response = convertirArticuloAResponseDTO(articulo);
        return ResponseEntity.ok(response);
    }

    /**
     * Compartir artículo
     * POST: /contenido/articulos/{id}/compartir
     */
    @PostMapping("/articulos/{id}/compartir")
    public ResponseEntity<ArticuloResponseDTO> compartirArticulo(@PathVariable Long id) {
        Articulo articulo = contenidoService.compartirArticulo(id);
        ArticuloResponseDTO response = convertirArticuloAResponseDTO(articulo);
        return ResponseEntity.ok(response);
    }

    /**
     * Actualizar artículo
     * PUT: /contenido/articulos/{id}
     */
    @PutMapping("/articulos/{id}")
    public ResponseEntity<ArticuloResponseDTO> actualizarArticulo(@PathVariable Long id, 
                                                                @Valid @RequestBody Articulo articuloDetails) {
        Articulo articulo = contenidoService.actualizarArticulo(id, articuloDetails);
        ArticuloResponseDTO response = convertirArticuloAResponseDTO(articulo);
        return ResponseEntity.ok(response);
    }

    /**
     * Actualizar estado de artículo
     * PUT: /contenido/articulos/{id}/estado
     */
    @PutMapping("/articulos/{id}/estado")
    public ResponseEntity<ArticuloResponseDTO> actualizarEstadoArticulo(@PathVariable Long id, 
                                                                       @Valid @RequestBody ArticuloEstadoDTO estadoDetails) {
        Articulo articulo = contenidoService.actualizarEstadoArticulo(id, estadoDetails);
        ArticuloResponseDTO response = convertirArticuloAResponseDTO(articulo);
        return ResponseEntity.ok(response);
    }

    /**
     * Eliminar artículo
     * DELETE: /contenido/articulos/{id}
     */
    @DeleteMapping("/articulos/{id}")
    public ResponseEntity<Void> eliminarArticulo(@PathVariable Long id) {
        contenidoService.eliminarArticulo(id);
        return ResponseEntity.noContent().build();
    }

    // ========== ENDPOINTS DE COMENTARIOS ==========

    /**
     * Crear nuevo comentario
     * POST: /contenido/comentarios
     */
    @PostMapping("/comentarios")
    public ResponseEntity<ComentarioResponseDTO> crearComentario(@Valid @RequestBody ComentarioCreationDTO comentarioDetails) {
        Comentario comentario = contenidoService.crearComentario(comentarioDetails);
        ComentarioResponseDTO response = convertirComentarioAResponseDTO(comentario);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Obtener comentarios por artículo
     * GET: /contenido/comentarios/articulo/{idArticulo}
     */
    @GetMapping("/comentarios/articulo/{idArticulo}")
    public ResponseEntity<List<ComentarioResponseDTO>> traerComentariosPorArticulo(@PathVariable Long idArticulo) {
        List<Comentario> comentarios = contenidoService.traerComentariosPorArticulo(idArticulo);
        List<ComentarioResponseDTO> response = comentarios.stream()
                .map(this::convertirComentarioAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener respuestas por comentario
     * GET: /contenido/comentarios/{idComentario}/respuestas
     */
    @GetMapping("/comentarios/{idComentario}/respuestas")
    public ResponseEntity<List<ComentarioResponseDTO>> traerRespuestasPorComentario(@PathVariable Long idComentario) {
        List<Comentario> comentarios = contenidoService.traerRespuestasPorComentario(idComentario);
        List<ComentarioResponseDTO> response = comentarios.stream()
                .map(this::convertirComentarioAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener comentarios pendientes
     * GET: /contenido/comentarios/pendientes
     */
    @GetMapping("/comentarios/pendientes")
    public ResponseEntity<List<ComentarioResponseDTO>> traerComentariosPendientes() {
        List<Comentario> comentarios = contenidoService.traerComentariosPendientes();
        List<ComentarioResponseDTO> response = comentarios.stream()
                .map(this::convertirComentarioAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener comentarios por usuario
     * GET: /contenido/comentarios/usuario/{idUsuario}
     */
    @GetMapping("/comentarios/usuario/{idUsuario}")
    public ResponseEntity<List<ComentarioResponseDTO>> traerComentariosPorUsuario(@PathVariable Long idUsuario) {
        List<Comentario> comentarios = contenidoService.traerComentariosPorUsuario(idUsuario);
        List<ComentarioResponseDTO> response = comentarios.stream()
                .map(this::convertirComentarioAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener comentario por ID
     * GET: /contenido/comentarios/{id}
     */
    @GetMapping("/comentarios/{id}")
    public ResponseEntity<ComentarioResponseDTO> traerComentarioPorId(@PathVariable Long id) {
        Comentario comentario = contenidoService.traerComentarioPorId(id);
        ComentarioResponseDTO response = convertirComentarioAResponseDTO(comentario);
        return ResponseEntity.ok(response);
    }

    /**
     * Aprobar comentario
     * PUT: /contenido/comentarios/{id}/aprobar
     */
    @PutMapping("/comentarios/{id}/aprobar")
    public ResponseEntity<ComentarioResponseDTO> aprobarComentario(@PathVariable Long id) {
        Comentario comentario = contenidoService.aprobarComentario(id);
        ComentarioResponseDTO response = convertirComentarioAResponseDTO(comentario);
        return ResponseEntity.ok(response);
    }

    /**
     * Rechazar comentario
     * PUT: /contenido/comentarios/{id}/rechazar
     */
    @PutMapping("/comentarios/{id}/rechazar")
    public ResponseEntity<ComentarioResponseDTO> rechazarComentario(@PathVariable Long id) {
        Comentario comentario = contenidoService.rechazarComentario(id);
        ComentarioResponseDTO response = convertirComentarioAResponseDTO(comentario);
        return ResponseEntity.ok(response);
    }

    /**
     * Dar like a comentario
     * POST: /contenido/comentarios/{id}/like
     */
    @PostMapping("/comentarios/{id}/like")
    public ResponseEntity<ComentarioResponseDTO> darLikeComentario(@PathVariable Long id) {
        Comentario comentario = contenidoService.darLikeComentario(id);
        ComentarioResponseDTO response = convertirComentarioAResponseDTO(comentario);
        return ResponseEntity.ok(response);
    }

    /**
     * Actualizar comentario
     * PUT: /contenido/comentarios/{id}
     */
    @PutMapping("/comentarios/{id}")
    public ResponseEntity<ComentarioResponseDTO> actualizarComentario(@PathVariable Long id, 
                                                                     @Valid @RequestBody Comentario comentarioDetails) {
        Comentario comentario = contenidoService.actualizarComentario(id, comentarioDetails);
        ComentarioResponseDTO response = convertirComentarioAResponseDTO(comentario);
        return ResponseEntity.ok(response);
    }

    /**
     * Eliminar comentario
     * DELETE: /contenido/comentarios/{id}
     */
    @DeleteMapping("/comentarios/{id}")
    public ResponseEntity<Void> eliminarComentario(@PathVariable Long id) {
        contenidoService.eliminarComentario(id);
        return ResponseEntity.noContent().build();
    }

    // ========== MÉTODOS DE CONVERSIÓN ==========

    /**
     * Convierte una entidad Articulo a ArticuloResponseDTO
     */
    private ArticuloResponseDTO convertirArticuloAResponseDTO(Articulo articulo) {
        ArticuloResponseDTO response = new ArticuloResponseDTO();
        response.setIdArticulo(articulo.getIdArticulo());
        response.setTituloArticulo(articulo.getTituloArticulo());
        response.setContenidoArticulo(articulo.getContenidoArticulo());
        response.setResumenArticulo(articulo.getResumenArticulo());
        response.setImagenArticulo(articulo.getImagenArticulo());
        response.setCategoriaArticulo(articulo.getCategoriaArticulo());
        response.setEtiquetasArticulo(articulo.getEtiquetasArticulo());
        response.setAutorArticulo(articulo.getAutorArticulo());
        response.setFechaPublicacion(articulo.getFechaPublicacion());
        response.setFechaActualizacion(articulo.getFechaActualizacion());
        response.setEstadoArticulo(articulo.getEstadoArticulo());
        response.setVistasArticulo(articulo.getVistasArticulo());
        response.setLikesArticulo(articulo.getLikesArticulo());
        response.setCompartidosArentario(articulo.getCompartidosArticulo());
        response.setTiempoLectura(articulo.getTiempoLectura());
        response.setEsDestacado(articulo.getEsDestacado());
        response.setEsPremium(articulo.getEsPremium());
        response.setActivo(articulo.getActivo());
        return response;
    }

    /**
     * Convierte una entidad Comentario a ComentarioResponseDTO
     */
    private ComentarioResponseDTO convertirComentarioAResponseDTO(Comentario comentario) {
        ComentarioResponseDTO response = new ComentarioResponseDTO();
        response.setIdComentario(comentario.getIdComentario());
        response.setIdArticulo(comentario.getIdArticulo());
        response.setIdUsuario(comentario.getIdUsuario());
        response.setNombreUsuario(comentario.getNombreUsuario());
        response.setContenidoComentario(comentario.getContenidoComentario());
        response.setFechaComentario(comentario.getFechaComentario());
        response.setEstadoComentario(comentario.getEstadoComentario());
        response.setLikesComentario(comentario.getLikesComentario());
        response.setIdComentarioPadre(comentario.getIdComentarioPadre());
        response.setActivo(comentario.getActivo());
        return response;
    }
}
