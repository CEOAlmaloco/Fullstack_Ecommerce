package com.ampuero.msvc.producto.config;

import com.ampuero.msvc.producto.models.Producto;
import com.ampuero.msvc.producto.repositories.ProductoRepository;
import com.ampuero.msvc.producto.services.ImageBase64Service;
import com.ampuero.msvc.producto.services.ImagePathMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * Inicializador de productos que convierte imágenes a Base64
 * Lee los productos y convierte las rutas de imágenes a Base64 antes de guardarlos
 * 
 * Ubicación: src/main/java/com/ampuero/msvc/producto/config/ProductoDataInitializerFromSQL.java
 */
@Configuration
@ConditionalOnProperty(name = "default.data.enabled", havingValue = "true", matchIfMissing = true)
public class ProductoDataInitializerFromSQL {

    private static final Logger logger = LoggerFactory.getLogger(ProductoDataInitializerFromSQL.class);

    @Value("${productos.autoconvert-base64:false}")
    private boolean autoConvertBase64;

    /**
     * Inicializa productos convirtiendo imágenes a Base64
     * Se ejecuta después de que data.sql haya cargado las categorías y subcategorías
     */
    @Bean
    @Order(2)
    CommandLineRunner initProductosWithBase64(
            ProductoRepository productoRepository,
            ImageBase64Service imageBase64Service,
            ImagePathMapper imagePathMapper) {
        
        return args -> {
            if (!autoConvertBase64) {
                logger.info("Conversión de imágenes a Base64 deshabilitada. Se conservan las rutas originales.");
                return;
            }

            logger.info("Iniciando conversión de imágenes a Base64 para productos existentes");

            // Obtener todos los productos
            var productos = productoRepository.findAll();
            
            int convertidos = 0;
            int yaConvertidos = 0;
            
            for (Producto producto : productos) {
                boolean necesitaConversion = false;
                
                // Verificar si la imagen principal necesita conversión (no es Base64)
                if (producto.getImagen() != null && !producto.getImagen().isEmpty()) {
                    String imagenActual = producto.getImagen();
                    if (!imagenActual.startsWith("data:image")) {
                        // Es una ruta, mapear a la ruta correcta
                        String rutaMapeada = imagePathMapper.mapImagePath(imagenActual);
                        
                        // Convertir a Base64
                        String base64Image = imageBase64Service.convertImageToBase64(rutaMapeada);
                        if (base64Image != null) {
                            producto.setImagen(base64Image);
                            necesitaConversion = true;
                            logger.info("Imagen principal convertida a Base64: {} -> Base64", imagenActual);
                        } else {
                            logger.warn("No se pudo convertir imagen principal: {}", imagenActual);
                        }
                    } else {
                        logger.debug("Imagen principal ya está en Base64: {}", producto.getTitulo());
                    }
                }

                // Verificar si las imágenes adicionales necesitan conversión
                if (producto.getImagenes() != null && !producto.getImagenes().isEmpty()) {
                    String imagenesJson = producto.getImagenes();
                    // Verificar si es un JSON array con rutas (no Base64)
                    // Buscar si contiene rutas como "img/" o "./img/" o no comienza con "data:image"
                    boolean tieneRutas = imagenesJson.contains("\"img/") || 
                                       imagenesJson.contains("\"./img/") ||
                                       (!imagenesJson.contains("\"data:image"));
                    
                    if (tieneRutas) {
                        // Parsear el JSON array y convertir cada ruta
                        try {
                            // Remover corchetes externos
                            String imagenesSinCorchetes = imagenesJson.trim();
                            if (imagenesSinCorchetes.startsWith("[")) {
                                imagenesSinCorchetes = imagenesSinCorchetes.substring(1);
                            }
                            if (imagenesSinCorchetes.endsWith("]")) {
                                imagenesSinCorchetes = imagenesSinCorchetes.substring(0, imagenesSinCorchetes.length() - 1);
                            }
                            
                            // Dividir por comas que están fuera de comillas
                            // Usar regex para dividir correctamente
                            String[] rutas = imagenesSinCorchetes.split(",\\s*");
                            
                            // Convertir cada ruta a Base64
                            StringBuilder jsonArray = new StringBuilder("[");
                            boolean primero = true;
                            int convertidas = 0;
                            
                            for (String ruta : rutas) {
                                // Limpiar la ruta (remover comillas y espacios)
                                ruta = ruta.trim().replace("\"", "").replace("'", "");
                                
                                if (!ruta.isEmpty() && !ruta.startsWith("data:image")) {
                                    // Mapear a la ruta correcta
                                    String rutaMapeada = imagePathMapper.mapImagePath(ruta);
                                    
                                    // Convertir a Base64
                                    String base64 = imageBase64Service.convertImageToBase64(rutaMapeada);
                                    if (base64 != null) {
                                        if (!primero) {
                                            jsonArray.append(",");
                                        }
                                        jsonArray.append("\"").append(base64).append("\"");
                                        primero = false;
                                        convertidas++;
                                        logger.debug("Ruta convertida a Base64: {} -> Base64", ruta);
                                    } else {
                                        logger.warn("No se pudo convertir ruta: {}", ruta);
                                        // Mantener la ruta original si falla
                                        if (!primero) {
                                            jsonArray.append(",");
                                        }
                                        jsonArray.append("\"").append(ruta).append("\"");
                                        primero = false;
                                    }
                                } else if (ruta.startsWith("data:image")) {
                                    // Ya es Base64, mantenerla
                                    if (!primero) {
                                        jsonArray.append(",");
                                    }
                                    jsonArray.append("\"").append(ruta).append("\"");
                                    primero = false;
                                }
                            }
                            jsonArray.append("]");
                            
                            producto.setImagenes(jsonArray.toString());
                            necesitaConversion = true;
                            logger.info("Imágenes adicionales convertidas a Base64: {} de {} imágenes", convertidas, rutas.length);
                        } catch (Exception e) {
                            logger.error("Error al convertir imágenes adicionales para producto {}: {}", 
                                    producto.getTitulo(), e.getMessage(), e);
                        }
                    } else {
                        logger.debug("Imágenes adicionales ya están en Base64: {}", producto.getTitulo());
                    }
                }

                if (necesitaConversion) {
                    productoRepository.save(producto);
                    convertidos++;
                    logger.info("Producto actualizado con imágenes Base64: {} - {}", 
                            producto.getCodigoProducto(), producto.getTitulo());
                } else {
                    yaConvertidos++;
                }
            }

            logger.info("Conversión de imágenes completada. Convertidos: {}, Ya convertidos: {}", 
                    convertidos, yaConvertidos);
        };
    }
}

