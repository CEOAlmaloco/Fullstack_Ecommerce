package com.ampuero.msvc.inventario.config;

import com.ampuero.msvc.inventario.models.Inventario;
import com.ampuero.msvc.inventario.repositories.InventarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.Optional;

/**
 * Inicializador de datos por defecto para el microservicio de inventario
 * Se ejecuta al iniciar la aplicación si default.data.enabled=true
 * 
 * Ubicación: src/main/java/com/ampuero/msvc/inventario/config/InventarioDataInitializer.java
 * 
 * Nota: Este inicializador asume que los productos ya existen en el microservicio de productos.
 * Si los productos no existen, los registros de inventario no se crearán.
 */
@Configuration
@ConditionalOnProperty(name = "default.data.enabled", havingValue = "true", matchIfMissing = true)
public class InventarioDataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(InventarioDataInitializer.class);

    /**
     * Inicializa los datos por defecto en la base de datos
     * 
     * Nota: Este inicializador crea registros de inventario para productos con IDs del 1 al 20.
     * Ajusta los IDs según los productos que tengas en tu base de datos.
     */
    @Bean
    @Order(1)
    CommandLineRunner initDatabase(InventarioRepository inventarioRepository) {
        return args -> {
            logger.info("Iniciando carga de datos por defecto para inventario");

            // Array de productos con sus datos de inventario
            // Formato: {productoId, cantidadDisponible, stockCritico, ubicacionAlmacen}
            Object[][] productosInventario = {
                {1L, 50, 10, "A-001"},   // PlayStation 5
                {2L, 30, 5, "A-002"},   // PlayStation 4 Slim
                {3L, 100, 20, "A-003"}, // DualShock 4
                {4L, 80, 15, "A-004"},  // Dualsense Azul
                {5L, 0, 5, "A-005"},    // Auriculares PS4 (agotado)
                {6L, 60, 10, "B-001"},  // Auriculares Logitech
                {7L, 40, 8, "B-002"},   // Teclado Redragon RGB
                {8L, 70, 15, "B-003"},   // Mouse Cougar
                {9L, 25, 5, "B-004"},   // Monitor ASUS
                {10L, 45, 10, "B-005"}, // Webcam Logitech
                {11L, 35, 7, "B-006"},  // Microfono Logitech
                {12L, 200, 50, "C-001"}, // Poleron StarCraft
                {13L, 200, 50, "C-002"}, // Poleron Super Papá Gamer
                {14L, 200, 50, "C-003"}, // Poleron Hollow Knight
                {15L, 200, 50, "C-004"}, // Poleron PlayStation Retro Negro
                {16L, 200, 50, "C-005"}, // Poleron Stumble Guys
                {17L, 200, 50, "C-006"}, // Poleron S.T.A.R.S
                {18L, 80, 20, "D-001"},  // Catan
                {19L, 70, 15, "D-002"}   // Carcassonne
            };

            int creados = 0;
            int existentes = 0;

            for (Object[] producto : productosInventario) {
                Long productoId = (Long) producto[0];
                Integer cantidadDisponible = (Integer) producto[1];
                Integer stockCritico = (Integer) producto[2];
                String ubicacionAlmacen = (String) producto[3];

                Optional<Inventario> existing = inventarioRepository.findByProductoId(productoId);
                
                if (existing.isEmpty()) {
                    Inventario inventario = new Inventario();
                    inventario.setProductoId(productoId);
                    inventario.setCantidadDisponible(cantidadDisponible);
                    inventario.setCantidadReservada(0);
                    inventario.setStockCritico(stockCritico);
                    inventario.setUbicacionAlmacen(ubicacionAlmacen);
                    inventario.setActivo(true);
                    
                    inventarioRepository.save(inventario);
                    creados++;
                    logger.debug("Inventario creado para producto ID: {} - Stock: {} - Ubicación: {}", 
                            productoId, cantidadDisponible, ubicacionAlmacen);
                } else {
                    existentes++;
                    logger.debug("Inventario ya existe para producto ID: {}", productoId);
                }
            }

            logger.info("Carga de datos por defecto de inventario completada. Creados: {}, Existentes: {}", 
                    creados, existentes);
            
            if (creados == 0 && existentes == 0) {
                logger.warn("No se creó ningún registro de inventario. Verifica que los productos existan en el microservicio de productos.");
            }
        };
    }
}

