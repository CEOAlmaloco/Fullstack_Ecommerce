package com.ampuero.msvc.producto.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuración para datos por defecto según usuario
 * Permite definir arrays de datos iniciales que se cargarán en la base de datos
 * Puede ser configurado por perfil (dev, test, prod) o por usuario específico
 */
@Configuration
@ConfigurationProperties(prefix = "default.data")
@Data
public class DefaultDataConfig {

    /**
     * Indica si se deben cargar datos por defecto al iniciar
     */
    private boolean enabled = true;

    /**
     * Usuario para el cual se cargarán los datos (puede ser "admin", "dev", etc.)
     */
    private String usuario = "default";

    /**
     * Lista de productos por defecto
     */
    private List<ProductoDefault> productos;

    /**
     * Lista de categorías por defecto
     */
    private List<CategoriaDefault> categorias;

    /**
     * Lista de subcategorías por defecto
     */
    private List<SubcategoriaDefault> subcategorias;

    @Data
    public static class ProductoDefault {
        private String titulo;
        private String categoriaId;
        private String subcategoriaId;
        private String imagen; // Ruta relativa desde static/img
        private String[] imagenes; // Array de rutas de imágenes
        private Double precio;
        private Boolean disponible = true;
        private Double rating = 0.0;
        private String descripcion;
        private Integer stock = 0;
        private String codigoProducto;
    }

    @Data
    public static class CategoriaDefault {
        private String id;
        private String nombre;
    }

    @Data
    public static class SubcategoriaDefault {
        private String id;
        private String nombre;
        private String categoriaId;
    }
}

