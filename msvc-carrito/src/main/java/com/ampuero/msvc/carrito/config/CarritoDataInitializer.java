package com.ampuero.msvc.carrito.config;

import com.ampuero.msvc.carrito.models.Carrito;
import com.ampuero.msvc.carrito.models.ItemCarrito;
import com.ampuero.msvc.carrito.repositories.CarritoRepository;
import com.ampuero.msvc.carrito.repositories.ItemCarritoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Inicializador de datos por defecto para el microservicio de carrito
 * Se ejecuta al iniciar la aplicación si default.data.enabled=true
 * 
 * Ubicación: src/main/java/com/ampuero/msvc/carrito/config/CarritoDataInitializer.java
 * 
 * Crea carritos de ejemplo con items para usuarios de prueba
 */
@Configuration
@ConditionalOnProperty(name = "default.data.enabled", havingValue = "true", matchIfMissing = true)
public class CarritoDataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(CarritoDataInitializer.class);

    /**
     * Inicializa los datos por defecto en la base de datos
     * 
     * Nota: El usuario "aa" tiene ID 5 (después de admin, cliente1, cliente2, cliente3)
     * Si cambias el orden de creación de usuarios, ajusta el ID aquí
     */
    @Bean
    @Order(2)
    CommandLineRunner initDatabase(CarritoRepository carritoRepository, ItemCarritoRepository itemCarritoRepository) {
        return args -> {
            logger.info("Iniciando carga de datos por defecto para carritos");

            // ID del usuario "aa" (normalmente es 5, pero puede variar)
            // Si el usuario "aa" no existe, este inicializador no creará nada
            Long usuarioId = 5L; // ID del usuario "aa"

            // Verificar si ya existe un carrito activo para este usuario
            List<Carrito> carritosExistentes = carritoRepository.findAll();
            boolean existeCarrito = carritosExistentes.stream()
                    .anyMatch(c -> c.getIdUsuario() != null && c.getIdUsuario().equals(usuarioId) && c.getActivo());

            if (!existeCarrito) {
                // Crear carrito activo para el usuario "aa"
                Carrito carrito = new Carrito();
                carrito.setIdUsuario(usuarioId);
                carrito.setFechaCreacion(LocalDateTime.now());
                carrito.setFechaActualizacion(LocalDateTime.now());
                carrito.setFechaExpiracion(LocalDateTime.now().plusDays(7));
                carrito.setEstadoCarrito("ACTIVO");
                carrito.setActivo(true);
                carrito.setMoneda("CLP");
                carrito.setTotalCarrito(0.0);
                carrito.setTotalDescuentos(0.0);
                carrito.setTotalImpuestos(0.0);
                carrito.setTotalFinal(0.0);
                
                carrito = carritoRepository.save(carrito);
                logger.info("Carrito creado para usuario ID: {} - Carrito ID: {}", usuarioId, carrito.getIdCarrito());

                // Agregar items al carrito
                // Item 1: PlayStation 5 (ID producto 1)
                ItemCarrito item1 = new ItemCarrito();
                item1.setCarrito(carrito);
                item1.setIdProducto(1L);
                item1.setNombreProducto("PlayStation 5");
                item1.setDescripcionProducto("La consola de última generación de Sony");
                item1.setPrecioUnitario(549990.0);
                item1.setCantidad(1);
                item1.setSubtotal(549990.0);
                item1.setDescuentoAplicado(0.0);
                item1.setImpuestoAplicado(0.0);
                item1.setTotalItem(549990.0);
                item1.setFechaAgregado(LocalDateTime.now());
                item1.setFechaActualizado(LocalDateTime.now());
                item1.setEstadoItem("ACTIVO");
                item1.setActivo(true);
                itemCarritoRepository.save(item1);
                logger.info("Item 1 agregado al carrito: PlayStation 5");

                // Item 2: Teclado Redragon RGB (ID producto 7)
                ItemCarrito item2 = new ItemCarrito();
                item2.setCarrito(carrito);
                item2.setIdProducto(7L);
                item2.setNombreProducto("Teclado Redragon RGB");
                item2.setDescripcionProducto("Teclado mecánico con iluminación RGB");
                item2.setPrecioUnitario(145990.0);
                item2.setCantidad(1);
                item2.setSubtotal(145990.0);
                item2.setDescuentoAplicado(0.0);
                item2.setImpuestoAplicado(0.0);
                item2.setTotalItem(145990.0);
                item2.setFechaAgregado(LocalDateTime.now());
                item2.setFechaActualizado(LocalDateTime.now());
                item2.setEstadoItem("ACTIVO");
                item2.setActivo(true);
                itemCarritoRepository.save(item2);
                logger.info("Item 2 agregado al carrito: Teclado Redragon RGB");

                // Item 3: Mouse Cougar (ID producto 8)
                ItemCarrito item3 = new ItemCarrito();
                item3.setCarrito(carrito);
                item3.setIdProducto(8L);
                item3.setNombreProducto("Mouse Cougar");
                item3.setDescripcionProducto("Mouse gamer ergonómico");
                item3.setPrecioUnitario(85990.0);
                item3.setCantidad(2); // Cantidad 2
                item3.setSubtotal(171980.0);
                item3.setDescuentoAplicado(0.0);
                item3.setImpuestoAplicado(0.0);
                item3.setTotalItem(171980.0);
                item3.setFechaAgregado(LocalDateTime.now());
                item3.setFechaActualizado(LocalDateTime.now());
                item3.setEstadoItem("ACTIVO");
                item3.setActivo(true);
                itemCarritoRepository.save(item3);
                logger.info("Item 3 agregado al carrito: Mouse Cougar (cantidad 2)");

                // Actualizar totales del carrito
                double totalCarrito = item1.getTotalItem() + item2.getTotalItem() + item3.getTotalItem();
                carrito.setTotalCarrito(totalCarrito);
                carrito.setTotalFinal(totalCarrito);
                carrito.setFechaActualizacion(LocalDateTime.now());
                carritoRepository.save(carrito);
                logger.info("Totales del carrito actualizados: Total: {}", totalCarrito);
            } else {
                logger.info("Carrito activo ya existe para usuario ID: {}", usuarioId);
            }

            logger.info("Carga de datos por defecto de carritos completada");
        };
    }
}

