package com.ampuero.msvc.producto.config;

import com.ampuero.msvc.producto.models.Categoria;
import com.ampuero.msvc.producto.models.Producto;
import com.ampuero.msvc.producto.models.Subcategoria;
import com.ampuero.msvc.producto.repositories.CategoriaRepository;
import com.ampuero.msvc.producto.repositories.ProductoRepository;
import com.ampuero.msvc.producto.repositories.SubcategoriaRepository;
import com.ampuero.msvc.producto.services.ImageBase64Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.List;
import java.util.Optional;

/**
 * Inicializador de datos por defecto para el microservicio de productos
 * Se ejecuta al iniciar la aplicación si default.data.enabled=true
 * 
 * Carga datos iniciales desde la configuración DefaultDataConfig
 * Las imágenes se convierten a Base64 y se almacenan en la base de datos H2
 * 
 * Ubicación: src/main/java/com/ampuero/msvc/producto/config/ProductoDataInitializer.java
 * 
 * Para usar en otros microservicios:
 * 1. Copiar esta estructura
 * 2. Adaptar las entidades y repositorios según el microservicio
 * 3. Configurar los datos por defecto en application.properties o application-dev.properties
 */
@Configuration
@ConditionalOnProperty(name = "default.data.enabled", havingValue = "true", matchIfMissing = true)
public class ProductoDataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(ProductoDataInitializer.class);

    /**
     * Inicializa los datos por defecto en la base de datos
     * Se ejecuta después de que Spring Boot haya inicializado el contexto
     */
    @Bean
    @Order(1)
    CommandLineRunner initDatabase(
            CategoriaRepository categoriaRepository,
            SubcategoriaRepository subcategoriaRepository,
            ProductoRepository productoRepository,
            ImageBase64Service imageBase64Service,
            DefaultDataConfig defaultDataConfig) {
        
        return args -> {
            if (!defaultDataConfig.isEnabled()) {
                logger.info("Inicialización de datos por defecto deshabilitada");
                return;
            }

            logger.info("Iniciando carga de datos por defecto para usuario: {}", defaultDataConfig.getUsuario());
            
            // Inicializar categorías
            if (defaultDataConfig.getCategorias() != null && !defaultDataConfig.getCategorias().isEmpty()) {
                initializeCategorias(categoriaRepository, defaultDataConfig.getCategorias());
            }

            // Inicializar subcategorías
            if (defaultDataConfig.getSubcategorias() != null && !defaultDataConfig.getSubcategorias().isEmpty()) {
                initializeSubcategorias(subcategoriaRepository, categoriaRepository, defaultDataConfig.getSubcategorias());
            }

            // Inicializar productos
            if (defaultDataConfig.getProductos() != null && !defaultDataConfig.getProductos().isEmpty()) {
                initializeProductos(productoRepository, categoriaRepository, subcategoriaRepository, 
                        imageBase64Service, defaultDataConfig.getProductos());
            }

            logger.info("Carga de datos por defecto completada");
        };
    }

    /**
     * Inicializa las categorías desde la configuración
     */
    private void initializeCategorias(CategoriaRepository categoriaRepository, 
                                      List<DefaultDataConfig.CategoriaDefault> categoriasDefault) {
        logger.info("Inicializando {} categorías", categoriasDefault.size());
        
        for (DefaultDataConfig.CategoriaDefault catDefault : categoriasDefault) {
            Optional<Categoria> existing = categoriaRepository.findById(catDefault.getId());
            if (existing.isEmpty()) {
                Categoria categoria = new Categoria();
                categoria.setId(catDefault.getId());
                categoria.setNombre(catDefault.getNombre());
                categoriaRepository.save(categoria);
                logger.debug("Categoría creada: {} - {}", catDefault.getId(), catDefault.getNombre());
            } else {
                logger.debug("Categoría ya existe: {} - {}", catDefault.getId(), catDefault.getNombre());
            }
        }
    }

    /**
     * Inicializa las subcategorías desde la configuración
     */
    private void initializeSubcategorias(SubcategoriaRepository subcategoriaRepository,
                                        CategoriaRepository categoriaRepository,
                                        List<DefaultDataConfig.SubcategoriaDefault> subcategoriasDefault) {
        logger.info("Inicializando {} subcategorías", subcategoriasDefault.size());
        
        for (DefaultDataConfig.SubcategoriaDefault subcatDefault : subcategoriasDefault) {
            Optional<Subcategoria> existing = subcategoriaRepository.findById(subcatDefault.getId());
            if (existing.isEmpty()) {
                Optional<Categoria> categoria = categoriaRepository.findById(subcatDefault.getCategoriaId());
                if (categoria.isPresent()) {
                    Subcategoria subcategoria = new Subcategoria();
                    subcategoria.setId(subcatDefault.getId());
                    subcategoria.setNombre(subcatDefault.getNombre());
                    subcategoria.setCategoria(categoria.get());
                    subcategoriaRepository.save(subcategoria);
                    logger.debug("Subcategoría creada: {} - {}", subcatDefault.getId(), subcatDefault.getNombre());
                } else {
                    logger.warn("Categoría no encontrada para subcategoría {}: {}", 
                            subcatDefault.getId(), subcatDefault.getCategoriaId());
                }
            } else {
                logger.debug("Subcategoría ya existe: {} - {}", subcatDefault.getId(), subcatDefault.getNombre());
            }
        }
    }

    /**
     * Inicializa los productos desde la configuración
     * Convierte las imágenes a Base64 antes de guardarlas
     */
    private void initializeProductos(ProductoRepository productoRepository,
                                    CategoriaRepository categoriaRepository,
                                    SubcategoriaRepository subcategoriaRepository,
                                    ImageBase64Service imageBase64Service,
                                    List<DefaultDataConfig.ProductoDefault> productosDefault) {
        logger.info("Inicializando {} productos", productosDefault.size());
        
        for (DefaultDataConfig.ProductoDefault prodDefault : productosDefault) {
            // Verificar si el producto ya existe por código
            boolean exists = productoRepository.findAll().stream()
                    .anyMatch(p -> prodDefault.getCodigoProducto() != null && 
                            prodDefault.getCodigoProducto().equals(p.getCodigoProducto()));
            
            if (!exists) {
                Optional<Categoria> categoria = categoriaRepository.findById(prodDefault.getCategoriaId());
                Optional<Subcategoria> subcategoria = subcategoriaRepository.findById(prodDefault.getSubcategoriaId());
                
                if (categoria.isPresent() && subcategoria.isPresent()) {
                    Producto producto = new Producto();
                    producto.setTitulo(prodDefault.getTitulo());
                    producto.setCategoria(categoria.get());
                    producto.setSubcategoria(subcategoria.get());
                    producto.setPrecio(prodDefault.getPrecio());
                    producto.setDisponible(prodDefault.getDisponible() != null ? prodDefault.getDisponible() : true);
                    producto.setRating(prodDefault.getRating() != null ? prodDefault.getRating() : 0.0);
                    producto.setDescripcion(prodDefault.getDescripcion());
                    producto.setStock(prodDefault.getStock() != null ? prodDefault.getStock() : 0);
                    producto.setCodigoProducto(prodDefault.getCodigoProducto());

                    // Convertir imagen principal a Base64
                    if (prodDefault.getImagen() != null && !prodDefault.getImagen().isEmpty()) {
                        String base64Image = imageBase64Service.convertImageToBase64(prodDefault.getImagen());
                        if (base64Image != null) {
                            producto.setImagen(base64Image);
                            logger.debug("Imagen principal convertida a Base64: {}", prodDefault.getImagen());
                        } else {
                            logger.warn("No se pudo convertir imagen principal: {}", prodDefault.getImagen());
                            producto.setImagen(prodDefault.getImagen()); // Guardar ruta original como fallback
                        }
                    }

                    // Convertir array de imágenes a JSON Base64
                    if (prodDefault.getImagenes() != null && prodDefault.getImagenes().length > 0) {
                        String imagenesJson = imageBase64Service.convertImagesArrayToBase64Json(prodDefault.getImagenes());
                        producto.setImagenes(imagenesJson);
                        logger.debug("Imágenes adicionales convertidas a Base64 JSON: {} imágenes", 
                                prodDefault.getImagenes().length);
                    }

                    productoRepository.save(producto);
                    logger.debug("Producto creado: {} - {}", prodDefault.getCodigoProducto(), prodDefault.getTitulo());
                } else {
                    logger.warn("Categoría o subcategoría no encontrada para producto {}: cat={}, subcat={}", 
                            prodDefault.getCodigoProducto(), prodDefault.getCategoriaId(), prodDefault.getSubcategoriaId());
                }
            } else {
                logger.debug("Producto ya existe: {} - {}", prodDefault.getCodigoProducto(), prodDefault.getTitulo());
            }
        }
    }
}

