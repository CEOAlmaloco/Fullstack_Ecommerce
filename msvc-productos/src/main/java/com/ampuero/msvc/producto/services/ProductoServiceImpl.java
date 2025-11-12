package com.ampuero.msvc.producto.services;

import com.ampuero.msvc.producto.clients.PromocionClient;
import com.ampuero.msvc.producto.clients.ReseniaClient;
import com.ampuero.msvc.producto.dtos.PromocionResumenDTO;
import com.ampuero.msvc.producto.dtos.ReseniaResumenDTO;
import com.ampuero.msvc.producto.exceptions.ProductoException;
import com.ampuero.msvc.producto.models.Producto;
import com.ampuero.msvc.producto.repositories.ProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de gestión de productos.
 * <p>
 * Esta clase proporciona la lógica de negocio para las operaciones CRUD de productos,
 * utilizando el patrón Service Layer con inyección de dependencias de Spring.
 *
 * @author Ampuero Development Team
 * @version 1.0
 * @since 1.0
 */
@Service
public class ProductoServiceImpl implements ProductoService {

    private static final Logger log = LoggerFactory.getLogger(ProductoServiceImpl.class);

    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private com.ampuero.msvc.producto.repositories.CategoriaRepository categoriaRepository;
    
    @Autowired
    private com.ampuero.msvc.producto.repositories.SubcategoriaRepository subcategoriaRepository;
    
    @Autowired(required = false)
    private com.ampuero.msvc.producto.clients.InventarioClient inventarioClient;
    
    @Autowired
    private ProductoMapper productoMapper;

    @Autowired(required = false)
    private ReseniaClient reseniaClient;

    @Autowired(required = false)
    private PromocionClient promocionClient;

    @Override
    public Producto crearProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public List<Producto> traerTodo() {
        try {
            log.info("Iniciando carga de productos desde BD...");
            List<Producto> productos = this.productoRepository.findAllWithRelations();
            log.info("Productos obtenidos de BD: {}", productos.size());
            
            if (productos.isEmpty()) {
                log.warn("No se encontraron productos en la base de datos");
                // Intentar sin relaciones por si el problema es el JOIN FETCH
                productos = this.productoRepository.findAll();
                log.info("Productos obtenidos sin relaciones: {}", productos.size());
            }
            
            // Asegurar que las relaciones se inicialicen correctamente
            for (Producto prod : productos) {
                if (prod.getCategoria() != null && prod.getCategoria().getId() != null) {
                    // Forzar la inicialización de la relación si es necesario
                    prod.getCategoria().getId(); // Acceder para inicializar si está lazy
                    log.debug("Producto {} tiene categoría: {}", prod.getId(), prod.getCategoria().getId());
                } else {
                    log.warn("Producto {} no tiene categoría asignada", prod.getId());
                }
                if (prod.getSubcategoria() != null && prod.getSubcategoria().getId() != null) {
                    prod.getSubcategoria().getId(); // Acceder para inicializar si está lazy
                    log.debug("Producto {} tiene subcategoría: {}", prod.getId(), prod.getSubcategoria().getId());
                } else {
                    log.warn("Producto {} no tiene subcategoría asignada", prod.getId());
                }
            }
            
            // Sincronizar stock desde inventario para todos los productos si el cliente está disponible
            if (inventarioClient != null && !productos.isEmpty()) {
                for (Producto prod : productos) {
                    try {
                        Map<String, Object> inventario = inventarioClient.obtenerInventarioPorProducto(prod.getId());
                        if (inventario != null && inventario.containsKey("cantidadDisponible")) {
                            Integer stockInventario = ((Number) inventario.get("cantidadDisponible")).intValue();
                            prod.setStock(stockInventario);
                            prod.setDisponible(stockInventario > 0);
                        }
                    } catch (Exception e) {
                        // Si no se puede obtener el inventario, usar el stock local
                        log.debug("No se pudo obtener inventario para producto {}: {}", prod.getId(), e.getMessage());
                    }
                }
            }
            
            log.info("Total productos a retornar: {}", productos.size());
            enrichProductos(productos);
            return productos;
        } catch (Exception ex) {
            log.error("Error al cargar productos: {}", ex.getMessage(), ex);
            ex.printStackTrace();
            return java.util.Collections.emptyList();
        }
    }

    @Override
    public Producto traerPorId(Long id) {
        Producto producto = this.productoRepository.findById(id)
                .orElseThrow(
                        () -> new ProductoException("El producto con el id " + id + " no existe")
                );
        
        // Sincronizar stock desde inventario si el cliente está disponible
        if (inventarioClient != null) {
            try {
                Object inventarioObj = inventarioClient.obtenerInventarioPorProducto(id);
                if (inventarioObj != null && inventarioObj instanceof Map) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> inventario = (Map<String, Object>) inventarioObj;
                    if (inventario.containsKey("cantidadDisponible")) {
                        Integer stockInventario = ((Number) inventario.get("cantidadDisponible")).intValue();
                        producto.setStock(stockInventario);
                        // Actualizar disponibilidad basada en stock
                        producto.setDisponible(stockInventario > 0);
                    }
                }
            } catch (Exception e) {
                // Si no se puede obtener el inventario, usar el stock local
                // Log del error si es necesario
            }
        }
        enrichProductos(List.of(producto));
        return producto;
    }

    @Override
    public Producto actualizarProducto(Long id, Producto producto) {
        return productoRepository.findById(id).map(p -> {
            if (producto.getTitulo() != null) p.setTitulo(producto.getTitulo());
            
            // Actualizar relación de categoría si se proporciona
            if (producto.getCategoria() != null && producto.getCategoria().getId() != null) {
                categoriaRepository.findById(producto.getCategoria().getId())
                    .ifPresent(p::setCategoria);
            } else if (producto.getCategoriaId() != null) {
                categoriaRepository.findById(producto.getCategoriaId())
                    .ifPresent(p::setCategoria);
            }
            
            // Actualizar relación de subcategoría si se proporciona
            if (producto.getSubcategoria() != null && producto.getSubcategoria().getId() != null) {
                subcategoriaRepository.findById(producto.getSubcategoria().getId())
                    .ifPresent(p::setSubcategoria);
            } else if (producto.getSubcategoriaId() != null) {
                subcategoriaRepository.findById(producto.getSubcategoriaId())
                    .ifPresent(p::setSubcategoria);
            }
            
            if (producto.getImagen() != null) p.setImagen(producto.getImagen());
            if (producto.getImagenes() != null) p.setImagenes(producto.getImagenes());
            if (producto.getPrecio() != null) p.setPrecio(producto.getPrecio());
            if (producto.getDisponible() != null) p.setDisponible(producto.getDisponible());
            if (producto.getRating() != null) p.setRating(producto.getRating());
            if (producto.getDescripcion() != null) p.setDescripcion(producto.getDescripcion());
            if (producto.getStock() != null) p.setStock(producto.getStock());
            return productoRepository.save(p);
        }).orElseThrow(() -> new ProductoException("El producto con el id " + id + " no existe"));
    }

    @Override
    public void eliminarProducto(Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
        } else {
            throw new ProductoException("El producto con el id " + id + " no existe");
        }
    }

    @Override
    public Map<String, Object> obtenerCategorias() {
        List<Map<String, String>> categorias = Arrays.asList(
                Map.of("id", "CO", "nombre", "Consola"),
                Map.of("id", "PE", "nombre", "Perifericos"),
                Map.of("id", "RO", "nombre", "Ropa"),
                Map.of("id", "EN", "nombre", "Entretenimiento")
        );

        List<Map<String, String>> subcategorias = Arrays.asList(
                Map.of("id", "MA", "nombre", "Mandos", "categoriaId", "CO"),
                Map.of("id", "AC", "nombre", "Accesorios", "categoriaId", "CO"),
                Map.of("id", "HA", "nombre", "Hardware", "categoriaId", "CO"),
                Map.of("id", "TE", "nombre", "Teclados", "categoriaId", "PE"),
                Map.of("id", "MO", "nombre", "Mouses", "categoriaId", "PE"),
                Map.of("id", "AU", "nombre", "Auriculares", "categoriaId", "PE"),
                Map.of("id", "MT", "nombre", "Monitores", "categoriaId", "PE"),
                Map.of("id", "MI", "nombre", "Microfonos", "categoriaId", "PE"),
                Map.of("id", "CW", "nombre", "Camaras web", "categoriaId", "PE"),
                Map.of("id", "MP", "nombre", "Mousepad", "categoriaId", "PE"),
                Map.of("id", "SI", "nombre", "Sillas Gamers", "categoriaId", "PE"),
                Map.of("id", "JM", "nombre", "Juegos de Mesa", "categoriaId", "EN"),
                Map.of("id", "PG", "nombre", "Polerones Gamers Personalizados", "categoriaId", "RO"),
                Map.of("id", "PR", "nombre", "Poleras Personalizadas", "categoriaId", "RO")
        );

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("categorias", categorias);
        resultado.put("subcategorias", subcategorias);

        return resultado;
    }

    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return traerTodo();
        }
        List<Producto> productos = productoRepository.findByTituloContainingIgnoreCase(nombre.trim());
        
        // Sincronizar stock desde inventario si el cliente está disponible
        if (inventarioClient != null && !productos.isEmpty()) {
            for (Producto prod : productos) {
                try {
                    Map<String, Object> inventario = inventarioClient.obtenerInventarioPorProducto(prod.getId());
                    if (inventario != null && inventario.containsKey("cantidadDisponible")) {
                        Integer stockInventario = ((Number) inventario.get("cantidadDisponible")).intValue();
                        prod.setStock(stockInventario);
                        prod.setDisponible(stockInventario > 0);
                    }
                } catch (Exception e) {
                    // Si no se puede obtener el inventario, usar el stock local
                }
            }
        }
        enrichProductos(productos);
        return productos;
    }

    @Override
    public List<Producto> obtenerPorCategoria(String categoria) {
        List<Producto> productos = productoRepository.findByCategoriaId(categoria);
        enrichProductos(productos);
        return productos;
    }

    @Override
    public List<Producto> obtenerDisponibles() {
        List<Producto> productos = productoRepository.findByDisponibleTrueAndStockGreaterThan(0);
        enrichProductos(productos);
        return productos;
    }

    @Override
    public com.ampuero.msvc.producto.dtos.ProductoPaginadoResponseDTO filtrarProductos(com.ampuero.msvc.producto.dtos.ProductoFiltroDTO filtros) {
        log.info("==== INICIO FILTRADO ====");
        log.info("Filtros recibidos en SERVICE: categoria={}, subcategorias={}, texto={}, precioMin={}, precioMax={}, disponible={}, rating={}, orden={}, pagina={}, tamano={}", 
                filtros.getCategoria(), filtros.getSubcategorias(), filtros.getTexto(), filtros.getPrecioMin(), 
                filtros.getPrecioMax(), filtros.getDisponible(), filtros.getRating(), filtros.getOrden(), 
                filtros.getPagina(), filtros.getTamano());
        
        // Validar que los filtros no sean null
        if (filtros.getSubcategorias() != null && !filtros.getSubcategorias().isEmpty()) {
            log.info("Subcategorias recibidas (tamaño={}): {}", filtros.getSubcategorias().size(), filtros.getSubcategorias());
            for (String subcat : filtros.getSubcategorias()) {
                log.info("  - Subcategoria: '{}'", subcat);
            }
        } else {
            log.info("NO hay subcategorias en el filtro");
        }
        
        if (filtros.getTexto() != null && !filtros.getTexto().trim().isEmpty()) {
            log.info("Texto de búsqueda recibido: '{}'", filtros.getTexto());
        } else {
            log.info("NO hay texto de búsqueda en el filtro");
        }
        
        // PASO 1: Obtener TODOS los productos de la base de datos con relaciones (sin filtrar por stock/disponibilidad)
        List<Producto> productos = productoRepository.findAllWithRelations();
        log.info("PASO 1 - Productos obtenidos de BD: {}", productos.size());
        
        // Asegurar que las relaciones se inicialicen correctamente antes de filtrar
        log.info("PASO 1.1 - Inicializando relaciones de productos...");
        for (Producto prod : productos) {
            try {
                // Acceder a las relaciones para forzar su inicialización (si están lazy)
                // Esto asegura que los getters personalizados funcionen correctamente
                if (prod.getCategoria() != null) {
                    String catId = prod.getCategoria().getId(); // Forzar carga si está lazy
                    log.debug("Producto {} - Categoria inicializada: {}", prod.getId(), catId);
                } else {
                    log.warn("Producto {} - NO tiene categoria asignada", prod.getId());
                }
                if (prod.getSubcategoria() != null) {
                    String subcatId = prod.getSubcategoria().getId(); // Forzar carga si está lazy
                    log.debug("Producto {} - Subcategoria inicializada: {}", prod.getId(), subcatId);
                } else {
                    log.warn("Producto {} - NO tiene subcategoria asignada", prod.getId());
                }
                
                // Verificar que los getters personalizados funcionen
                String catIdFromGetter = prod.getCategoriaId();
                String subcatIdFromGetter = prod.getSubcategoriaId();
                log.debug("Producto {} - IDs obtenidos desde getters: categoriaId={}, subcategoriaId={}", 
                        prod.getId(), catIdFromGetter, subcatIdFromGetter);
            } catch (Exception e) {
                log.error("Error al inicializar relaciones para producto {}: {}", prod.getId(), e.getMessage());
            }
        }
        
        // PASO 2: Sincronizar stock desde inventario (actualizar disponibilidad, pero NO filtrar todavía)
        if (inventarioClient != null && !productos.isEmpty()) {
            int sincronizados = 0;
            for (Producto prod : productos) {
                try {
                    Map<String, Object> inventario = inventarioClient.obtenerInventarioPorProducto(prod.getId());
                    if (inventario != null && inventario.containsKey("cantidadDisponible")) {
                        Integer stockInventario = ((Number) inventario.get("cantidadDisponible")).intValue();
                        prod.setStock(stockInventario);
                        prod.setDisponible(stockInventario > 0);
                        sincronizados++;
                    }
                } catch (Exception e) {
                    // Si no se puede obtener el inventario, usar el stock local
                    log.debug("No se pudo obtener inventario para producto {}: {}", prod.getId(), e.getMessage());
                }
            }
            log.info("PASO 2 - Productos sincronizados con inventario: {}/{}", sincronizados, productos.size());
        }
        
        log.info("PASO 3 - Total productos antes de aplicar filtros: {}", productos.size());
        if (productos.size() > 0 && productos.size() <= 10) {
            // Si hay pocos productos, mostrar detalles de todos
            log.info("Detalles de productos: {}", productos.stream()
                    .map(p -> String.format("[id=%d, titulo=%s, categoriaId=%s, subcategoriaId=%s, stock=%d, disponible=%s]", 
                            p.getId(), p.getTitulo(), p.getCategoriaId(), p.getSubcategoriaId(), p.getStock(), p.getDisponible()))
                    .collect(java.util.stream.Collectors.joining(", ")));
        }
        
        // PASO 3: Aplicar TODOS los filtros del usuario
        log.info("PASO 4 - Aplicando filtros...");
        List<Producto> filtrados = productos.stream()
                .filter(p -> {
                    // Filtro por categoría (acepta códigos y nombres)
                    if (filtros.getCategoria() != null && !filtros.getCategoria().isEmpty() && 
                        !filtros.getCategoria().equals("todos")) {
                        String categoriaFiltro = filtros.getCategoria().toUpperCase();
                        // Mapeo de nombres a códigos en el backend también por si acaso
                        java.util.Map<String, String> categoriaMap = new java.util.HashMap<>();
                        categoriaMap.put("CONSOLAS", "CO");
                        categoriaMap.put("PERIFERICOS", "PE");
                        categoriaMap.put("PERIFÉRICOS", "PE");
                        categoriaMap.put("ROPA", "RO");
                        categoriaMap.put("ENTRETENIMIENTO", "EN");
                        String categoriaCodigo = categoriaMap.getOrDefault(categoriaFiltro, categoriaFiltro);
                        
                        // Obtener categoriaId desde la relación o el getter
                        String productoCatId = null;
                        if (p.getCategoria() != null && p.getCategoria().getId() != null) {
                            productoCatId = p.getCategoria().getId();
                        } else {
                            productoCatId = p.getCategoriaId();
                        }
                        
                        boolean matchesCategoria = productoCatId != null && 
                                (productoCatId.equalsIgnoreCase(categoriaCodigo) || 
                                 productoCatId.equalsIgnoreCase(categoriaFiltro));
                        
                        if (!matchesCategoria) {
                            log.debug("Producto {} EXCLUIDO por categoria: filtro={} (codigo={}), actual={}", 
                                    p.getId(), categoriaFiltro, categoriaCodigo, productoCatId);
                            return false;
                        } else {
                            log.debug("Producto {} INCLUIDO por categoria: filtro={}, actual={}", 
                                    p.getId(), categoriaCodigo, productoCatId);
                        }
                    }
                    
                    // Filtro por subcategorías (acepta códigos y comparación case-insensitive)
                    // ESTE FILTRO DEBE APLICARSE SIEMPRE SI EXISTE - ES CRÍTICO QUE FUNCIONE
                    if (filtros.getSubcategorias() != null && !filtros.getSubcategorias().isEmpty()) {
                        boolean matchSubcat = false;
                        String subcatLog = String.join(", ", filtros.getSubcategorias());
                        
                        log.info("Producto {} - VERIFICANDO subcategorias: filtro={}, actual={}", 
                                p.getId(), subcatLog, p.getSubcategoriaId());
                        
                        for (String subcat : filtros.getSubcategorias()) {
                            if (subcat != null && !subcat.isEmpty()) {
                                String subcatTrimmed = subcat.trim().toUpperCase();
                                
                                if (subcatTrimmed.startsWith("ALL-")) {
                                    String cat = subcatTrimmed.replace("ALL-", "");
                                    // Mapear categoría a código si es necesario
                                    String catCodigo = cat.toUpperCase();
                                    java.util.Map<String, String> catMap = new java.util.HashMap<>();
                                    catMap.put("CONSOLAS", "CO");
                                    catMap.put("PERIFERICOS", "PE");
                                    catMap.put("PERIFÉRICOS", "PE");
                                    catMap.put("ROPA", "RO");
                                    catMap.put("ENTRETENIMIENTO", "EN");
                                    catCodigo = catMap.getOrDefault(catCodigo, catCodigo);
                                    
                                    String productoCatId = p.getCategoriaId() != null ? p.getCategoriaId().trim().toUpperCase() : null;
                                    
                                    if (productoCatId != null && 
                                        (productoCatId.equals(catCodigo) || productoCatId.equals(cat))) {
                                        matchSubcat = true;
                                        log.info("Producto {} INCLUIDO por ALL-{} (categoria): filtro={}, actual={}", 
                                                p.getId(), cat, catCodigo, productoCatId);
                                        break;
                                    } else {
                                        log.info("Producto {} NO coincide con ALL-{}: filtro={}, actual={}", 
                                                p.getId(), cat, catCodigo, productoCatId);
                                    }
                                } else {
                                    // Comparación case-insensitive para subcategorías - NORMALIZAR AMBOS
                                    // Obtener subcategoriaId desde la relación o el getter
                                    String productoSubcatId = null;
                                    if (p.getSubcategoria() != null && p.getSubcategoria().getId() != null) {
                                        productoSubcatId = p.getSubcategoria().getId().trim().toUpperCase();
                                    } else {
                                        productoSubcatId = p.getSubcategoriaId() != null ? 
                                                p.getSubcategoriaId().trim().toUpperCase() : null;
                                    }
                                    
                                    log.info("Producto {} - Comparando subcategoria: filtro='{}', actual='{}'", 
                                            p.getId(), subcatTrimmed, productoSubcatId);
                                    
                                    if (productoSubcatId != null && productoSubcatId.equals(subcatTrimmed)) {
                                        matchSubcat = true;
                                        log.info("Producto {} ✓✓✓ INCLUIDO por subcategoria: filtro='{}', actual='{}'", 
                                                p.getId(), subcatTrimmed, productoSubcatId);
                                        break;
                                    } else {
                                        log.info("Producto {} ✗✗✗ NO coincide con subcategoria: filtro='{}', actual='{}'", 
                                                p.getId(), subcatTrimmed, productoSubcatId);
                                    }
                                }
                            }
                        }
                        
                        if (!matchSubcat) {
                            log.warn("Producto {} EXCLUIDO por subcategoria: esperado={}, actual={}", 
                                    p.getId(), filtros.getSubcategorias(), p.getSubcategoriaId());
                            return false;
                        } else {
                            log.info("Producto {} PASA filtro de subcategorias", p.getId());
                        }
                    }
                    
                    // Filtro por texto (búsqueda en título, descripción y nombre del producto)
                    if (filtros.getTexto() != null && !filtros.getTexto().trim().isEmpty()) {
                        String textoLower = filtros.getTexto().trim().toLowerCase();
                        boolean matchTexto = false;
                        
                        log.debug("Producto {} - Aplicando filtro de texto: buscado='{}', titulo='{}'", 
                                p.getId(), textoLower, p.getTitulo());
                        
                        // Buscar en título (nombre del producto) - PRINCIPAL
                        if (p.getTitulo() != null) {
                            String tituloLower = p.getTitulo().toLowerCase();
                            matchTexto = tituloLower.contains(textoLower);
                            if (matchTexto) {
                                log.info("✓ Producto {} INCLUIDO por texto en TITULO: buscado='{}', titulo='{}'", 
                                        p.getId(), textoLower, p.getTitulo());
                            } else {
                                log.debug("✗ Producto {} NO coincide en TITULO: buscado='{}', titulo='{}'", 
                                        p.getId(), textoLower, p.getTitulo());
                            }
                        }
                        
                        // Buscar en descripción si no coincide en título
                        if (!matchTexto && p.getDescripcion() != null) {
                            String descLower = p.getDescripcion().toLowerCase();
                            matchTexto = descLower.contains(textoLower);
                            if (matchTexto) {
                                log.info("✓ Producto {} INCLUIDO por texto en DESCRIPCION: buscado='{}'", 
                                        p.getId(), textoLower);
                            }
                        }
                        
                        // También buscar en código de producto si existe
                        if (!matchTexto && p.getCodigoProducto() != null && !p.getCodigoProducto().isEmpty()) {
                            String codigoLower = p.getCodigoProducto().toLowerCase();
                            matchTexto = codigoLower.contains(textoLower);
                            if (matchTexto) {
                                log.info("✓ Producto {} INCLUIDO por texto en CODIGO: buscado='{}'", 
                                        p.getId(), textoLower);
                            }
                        }
                        
                        if (!matchTexto) {
                            log.info("✗✗✗ Producto {} EXCLUIDO por texto: buscado='{}', titulo='{}'", 
                                    p.getId(), textoLower, p.getTitulo());
                            return false;
                        }
                    }
                    
                    // Filtro por precio mínimo
                    if (filtros.getPrecioMin() != null && p.getPrecio() < filtros.getPrecioMin()) {
                        return false;
                    }
                    
                    // Filtro por precio máximo
                    if (filtros.getPrecioMax() != null && p.getPrecio() > filtros.getPrecioMax()) {
                        return false;
                    }
                    
                    // Filtro por disponibilidad
                    if (filtros.getDisponible() != null && filtros.getDisponible() && 
                        (p.getDisponible() == null || !p.getDisponible())) {
                        return false;
                    }
                    
                    // Filtro por rating
                    if (filtros.getRating() != null && filtros.getRating() > 0) {
                        if (p.getRating() == null || p.getRating() < filtros.getRating()) {
                            return false;
                        }
                    }
                    
                    return true;
                })
                .sorted((a, b) -> {
                    // Ordenamiento
                    if (filtros.getOrden() == null || filtros.getOrden().isEmpty() || filtros.getOrden().equals("relevancia")) {
                        return 0;
                    }
                    switch (filtros.getOrden()) {
                        case "precio-asc":
                            return Double.compare(a.getPrecio() != null ? a.getPrecio() : 0.0, 
                                                 b.getPrecio() != null ? b.getPrecio() : 0.0);
                        case "precio-desc":
                            return Double.compare(b.getPrecio() != null ? b.getPrecio() : 0.0, 
                                                 a.getPrecio() != null ? a.getPrecio() : 0.0);
                        case "rating-desc":
                            return Double.compare(b.getRating() != null ? b.getRating() : 0.0, 
                                                 a.getRating() != null ? a.getRating() : 0.0);
                        default:
                            return 0;
                    }
                })
                .collect(java.util.stream.Collectors.toList());
        
        log.info("PASO 5 - Total productos después de aplicar filtros: {}", filtrados.size());
        if (filtrados.size() > 0) {
            if (filtrados.size() <= 20) {
                // Mostrar todos si hay pocos
                log.info("Productos que pasaron los filtros: {}", filtrados.stream()
                        .map(p -> String.format("[id=%d, titulo=%s, categoriaId=%s, subcategoriaId=%s, stock=%d, disponible=%s]", 
                                p.getId(), p.getTitulo(), p.getCategoriaId(), p.getSubcategoriaId(), p.getStock(), p.getDisponible()))
                        .collect(java.util.stream.Collectors.joining(", ")));
            } else {
                // Mostrar solo los primeros 10 si hay muchos
                log.info("Primeros 10 productos que pasaron los filtros: {}", filtrados.stream().limit(10)
                        .map(p -> String.format("[id=%d, titulo=%s, categoriaId=%s, subcategoriaId=%s]", 
                                p.getId(), p.getTitulo(), p.getCategoriaId(), p.getSubcategoriaId()))
                        .collect(java.util.stream.Collectors.joining(", ")));
            }
        } else {
            log.warn("¡NO se encontraron productos que cumplan con los filtros!");
        }
        
        // PASO 6: Paginación - Aplicar después de filtrar
        Integer pagina = filtros.getPagina(); // Ya tiene default en el getter
        Integer tamano = filtros.getTamano(); // Ya tiene default en el getter
        if (pagina < 0) pagina = 0;
        if (tamano <= 0) tamano = 10;
        
        log.info("PASO 6 - Valores de paginación (después de getters): pagina={}, tamano={}", pagina, tamano);
        
        log.info("PASO 6 - Aplicando paginación: pagina={}, tamano={}, totalFiltrados={}", 
                pagina, tamano, filtrados.size());
        
        int inicio = pagina * tamano;
        int fin = Math.min(inicio + tamano, filtrados.size());
        
        // Validar que inicio no sea mayor que el tamaño de la lista
        if (inicio >= filtrados.size()) {
            inicio = 0;
            fin = Math.min(tamano, filtrados.size());
        }
        
        List<Producto> productosPaginados = filtrados.isEmpty() ? 
                new java.util.ArrayList<>() : 
                (inicio < filtrados.size() ? filtrados.subList(inicio, fin) : new java.util.ArrayList<>());

        enrichProductos(productosPaginados);
 
        log.info("PASO 7 - Paginación aplicada: inicio={}, fin={}, productosEnPagina={}", 
                inicio, fin, productosPaginados.size());
        
        // Convertir productos paginados a DTOs con URLs de S3
        List<com.ampuero.msvc.producto.dtos.ProductoResponseDTO> productosDTO = productoMapper.toDTOList(productosPaginados);
        
        // Crear respuesta paginada
        com.ampuero.msvc.producto.dtos.ProductoPaginadoResponseDTO respuesta = 
                new com.ampuero.msvc.producto.dtos.ProductoPaginadoResponseDTO();
        respuesta.setProductos(productosDTO);
        respuesta.setPagina(pagina);
        respuesta.setTamano(tamano);
        respuesta.setTotalElementos((long) filtrados.size());
        int totalPaginasCalc = filtrados.isEmpty() ? 0 : (int) Math.ceil((double) filtrados.size() / tamano);
        respuesta.setTotalPaginas(totalPaginasCalc);
        respuesta.setPrimeraPagina(pagina == 0);
        respuesta.setUltimaPagina(pagina >= totalPaginasCalc - 1);
        
        log.info("==== FIN FILTRADO ====");
        log.info("RESUMEN: totalElementos={}, totalPaginas={}, paginaActual={}, productosEnRespuesta={}", 
                respuesta.getTotalElementos(), respuesta.getTotalPaginas(), pagina, productosPaginados.size());
        log.info("========================================");
        
        return respuesta;
    }

    private void enrichProductos(List<Producto> productos) {
        if (productos == null || productos.isEmpty()) {
            return;
        }
        enrichWithResenias(productos);
        enrichWithPromociones(productos);
    }

    private void enrichWithResenias(List<Producto> productos) {
        if (reseniaClient == null) {
            log.debug("ReseniaClient no disponible, se omite enriquecimiento de reseñas");
            return;
        }
        try {
            List<ReseniaResumenDTO> resenias = reseniaClient.obtenerResenias();
            if (resenias == null || resenias.isEmpty()) {
                productos.forEach(p -> {
                    p.setReviews(Collections.emptyList());
                    p.setRatingPromedioCalculado(p.getRating());
                });
                return;
            }

            Map<Long, List<ReseniaResumenDTO>> reseniasPorProducto = resenias.stream()
                    .filter(resenia -> resenia != null && resenia.getIdProducto() != null)
                    .collect(Collectors.groupingBy(ReseniaResumenDTO::getIdProducto));

            for (Producto producto : productos) {
                if (producto == null || producto.getId() == null) {
                    continue;
                }
                List<ReseniaResumenDTO> lista = reseniasPorProducto.getOrDefault(producto.getId(), Collections.emptyList());
                producto.setReviews(lista);
                double promedio = lista.stream()
                        .map(ReseniaResumenDTO::getRating)
                        .filter(Objects::nonNull)
                        .mapToInt(Integer::intValue)
                        .average()
                        .orElse(0.0);
                producto.setRatingPromedioCalculado(promedio);
            }
        } catch (Exception ex) {
            log.warn("No se pudieron cargar reseñas desde msvc-resenia: {}", ex.getMessage());
        }
    }

    private void enrichWithPromociones(List<Producto> productos) {
        if (promocionClient == null) {
            log.debug("PromocionClient no disponible, se omite enriquecimiento de promociones");
            return;
        }

        try {
            List<PromocionResumenDTO> promociones = promocionClient.obtenerPromocionesActivas();
            if (promociones == null || promociones.isEmpty()) {
                productos.forEach(p -> {
                    p.setOfertaActiva(Boolean.FALSE);
                    p.setDescuentoCalculado(null);
                    p.setPrecioConDescuentoCalculado(null);
                });
                return;
            }

            LocalDateTime ahora = LocalDateTime.now();

            for (Producto producto : productos) {
                if (producto == null) {
                    continue;
                }

                Double precio = producto.getPrecio();
                if (precio == null || precio <= 0) {
                    producto.setOfertaActiva(Boolean.FALSE);
                    producto.setDescuentoCalculado(null);
                    producto.setPrecioConDescuentoCalculado(null);
                    continue;
                }

                String categoriaProducto = producto.getCategoriaId();
                double mejorPorcentaje = 0.0;

                for (PromocionResumenDTO promocion : promociones) {
                    if (promocion == null || Boolean.FALSE.equals(promocion.getActivo())) {
                        continue;
                    }
                    if (Boolean.TRUE.equals(promocion.getAplicableDuoc())) {
                        // Evitar aplicar promociones exclusivas para usuarios Duoc al catálogo general
                        continue;
                    }
                    if (promocion.getFechaInicio() != null && ahora.isBefore(promocion.getFechaInicio())) {
                        continue;
                    }
                    if (promocion.getFechaFin() != null && ahora.isAfter(promocion.getFechaFin())) {
                        continue;
                    }
                    if (promocion.getMontoMinimo() != null && precio < promocion.getMontoMinimo()) {
                        continue;
                    }
                    String categoriaAplicable = promocion.getCategoriaAplicable();
                    if (categoriaAplicable != null && !categoriaAplicable.isBlank()) {
                        if (categoriaProducto == null || !categoriaAplicable.equalsIgnoreCase(categoriaProducto)) {
                            continue;
                        }
                    }

                    double porcentaje = 0.0;
                    if (promocion.getValorDescuento() != null) {
                        if ("PORCENTAJE".equalsIgnoreCase(promocion.getTipoDescuento())) {
                            porcentaje = promocion.getValorDescuento();
                        } else if ("MONTO".equalsIgnoreCase(promocion.getTipoDescuento()) && precio > 0) {
                            porcentaje = (promocion.getValorDescuento() / precio) * 100;
                        }
                    }

                    if (porcentaje > mejorPorcentaje) {
                        mejorPorcentaje = porcentaje;
                    }
                }

                if (mejorPorcentaje > 0) {
                    double porcentajeRedondeado = Math.round(mejorPorcentaje * 100.0) / 100.0;
                    double precioConDescuento = Math.max(0.0, precio * (1 - (porcentajeRedondeado / 100)));
                    producto.setOfertaActiva(Boolean.TRUE);
                    producto.setDescuentoCalculado(porcentajeRedondeado);
                    producto.setPrecioConDescuentoCalculado(precioConDescuento);
                } else {
                    producto.setOfertaActiva(Boolean.FALSE);
                    producto.setDescuentoCalculado(null);
                    producto.setPrecioConDescuentoCalculado(null);
                }
            }
        } catch (Exception ex) {
            log.warn("No se pudieron cargar promociones desde msvc-promociones: {}", ex.getMessage());
        }
    }
}
