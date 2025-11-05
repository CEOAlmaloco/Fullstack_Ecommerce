package com.ampuero.msvc.resenia.config;

import com.ampuero.msvc.resenia.models.Resenia;
import com.ampuero.msvc.resenia.repositories.ReseniaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.time.LocalDateTime;

/**
 * Inicializador de datos por defecto para el microservicio de reseñas
 * Se ejecuta al iniciar la aplicación si default.data.enabled=true
 */
@Configuration
@ConditionalOnProperty(name = "default.data.enabled", havingValue = "true", matchIfMissing = true)
public class ReseniaDataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(ReseniaDataInitializer.class);

    @Bean
    @Order(1)
    CommandLineRunner initDatabase(ReseniaRepository reseniaRepository) {
        return args -> {
            logger.info("Iniciando carga de datos por defecto para reseñas");

            // Reseña 1: PlayStation 5 (Producto ID 1, Usuario ID 5 - usuario "aa")
            if (!reseniaRepository.existsByIdProductoAndIdUsuario(1L, 5L)) {
                Resenia resenia1 = new Resenia();
                resenia1.setIdProducto(1L);
                resenia1.setIdUsuario(5L);
                resenia1.setUsuarioNombre("aa aa");
                resenia1.setRating(5);
                resenia1.setComentario("Excelente consola, la PlayStation 5 superó todas mis expectativas. Los gráficos son impresionantes y los tiempos de carga son muy rápidos. Muy recomendada.");
                resenia1.setFechaCreacion(LocalDateTime.of(2024, 11, 1, 10, 0));
                resenia1.setFechaActualizacion(LocalDateTime.of(2024, 11, 1, 10, 0));
                resenia1.setActivo(true);
                
                reseniaRepository.save(resenia1);
                logger.info("Reseña creada: PlayStation 5 - ID: {}", resenia1.getId());
            }

            // Reseña 2: PlayStation 5 (Producto ID 1, Usuario ID 2)
            if (!reseniaRepository.existsByIdProductoAndIdUsuario(1L, 2L)) {
                Resenia resenia2 = new Resenia();
                resenia2.setIdProducto(1L);
                resenia2.setIdUsuario(2L);
                resenia2.setUsuarioNombre("Cliente Test");
                resenia2.setRating(4);
                resenia2.setComentario("Muy buena consola, pero el precio es un poco alto. La calidad es excelente y los juegos corren perfectamente.");
                resenia2.setFechaCreacion(LocalDateTime.of(2024, 10, 28, 15, 30));
                resenia2.setFechaActualizacion(LocalDateTime.of(2024, 10, 28, 15, 30));
                resenia2.setActivo(true);
                
                reseniaRepository.save(resenia2);
                logger.info("Reseña creada: PlayStation 5 (Usuario 2) - ID: {}", resenia2.getId());
            }

            // Reseña 3: PlayStation 4 Slim (Producto ID 2, Usuario ID 5)
            if (!reseniaRepository.existsByIdProductoAndIdUsuario(2L, 5L)) {
                Resenia resenia3 = new Resenia();
                resenia3.setIdProducto(2L);
                resenia3.setIdUsuario(5L);
                resenia3.setUsuarioNombre("aa aa");
                resenia3.setRating(4);
                resenia3.setComentario("Buena consola para el precio. Aunque ya no es de última generación, sigue siendo una excelente opción para jugar muchos títulos.");
                resenia3.setFechaCreacion(LocalDateTime.of(2024, 10, 25, 14, 0));
                resenia3.setFechaActualizacion(LocalDateTime.of(2024, 10, 25, 14, 0));
                resenia3.setActivo(true);
                
                reseniaRepository.save(resenia3);
                logger.info("Reseña creada: PlayStation 4 Slim - ID: {}", resenia3.getId());
            }

            // Reseña 4: DualShock 4 (Producto ID 3, Usuario ID 5)
            if (!reseniaRepository.existsByIdProductoAndIdUsuario(3L, 5L)) {
                Resenia resenia4 = new Resenia();
                resenia4.setIdProducto(3L);
                resenia4.setIdUsuario(5L);
                resenia4.setUsuarioNombre("aa aa");
                resenia4.setRating(5);
                resenia4.setComentario("Control cómodo y preciso. La batería dura bastante y es muy ergonómico. Perfecto para sesiones largas de juego.");
                resenia4.setFechaCreacion(LocalDateTime.of(2024, 10, 20, 11, 0));
                resenia4.setFechaActualizacion(LocalDateTime.of(2024, 10, 20, 11, 0));
                resenia4.setActivo(true);
                
                reseniaRepository.save(resenia4);
                logger.info("Reseña creada: DualShock 4 - ID: {}", resenia4.getId());
            }

            // Reseña 5: Auriculares Logitech (Producto ID 6, Usuario ID 5)
            if (!reseniaRepository.existsByIdProductoAndIdUsuario(6L, 5L)) {
                Resenia resenia5 = new Resenia();
                resenia5.setIdProducto(6L);
                resenia5.setIdUsuario(5L);
                resenia5.setUsuarioNombre("aa aa");
                resenia5.setRating(5);
                resenia5.setComentario("Excelente calidad de sonido. El micrófono es claro y la cancelación de ruido funciona muy bien. Muy recomendados para gaming.");
                resenia5.setFechaCreacion(LocalDateTime.of(2024, 10, 15, 16, 0));
                resenia5.setFechaActualizacion(LocalDateTime.of(2024, 10, 15, 16, 0));
                resenia5.setActivo(true);
                
                reseniaRepository.save(resenia5);
                logger.info("Reseña creada: Auriculares Logitech - ID: {}", resenia5.getId());
            }

            // Reseña 6: Teclado Redragon RGB (Producto ID 7, Usuario ID 5)
            if (!reseniaRepository.existsByIdProductoAndIdUsuario(7L, 5L)) {
                Resenia resenia6 = new Resenia();
                resenia6.setIdProducto(7L);
                resenia6.setIdUsuario(5L);
                resenia6.setUsuarioNombre("aa aa");
                resenia6.setRating(4);
                resenia6.setComentario("Buen teclado mecánico con luces RGB llamativas. Los switches son sensibles y el diseño es atractivo. El único detalle es que puede ser un poco ruidoso.");
                resenia6.setFechaCreacion(LocalDateTime.of(2024, 10, 10, 9, 0));
                resenia6.setFechaActualizacion(LocalDateTime.of(2024, 10, 10, 9, 0));
                resenia6.setActivo(true);
                
                reseniaRepository.save(resenia6);
                logger.info("Reseña creada: Teclado Redragon RGB - ID: {}", resenia6.getId());
            }

            // Reseña 7: Poleron StarCraft (Producto ID 12, Usuario ID 5)
            if (!reseniaRepository.existsByIdProductoAndIdUsuario(12L, 5L)) {
                Resenia resenia7 = new Resenia();
                resenia7.setIdProducto(12L);
                resenia7.setIdUsuario(5L);
                resenia7.setUsuarioNombre("aa aa");
                resenia7.setRating(5);
                resenia7.setComentario("Polerón muy cómodo y de buena calidad. El diseño de StarCraft es genial y la tela es suave. Perfecto para los fanáticos del juego.");
                resenia7.setFechaCreacion(LocalDateTime.of(2024, 9, 30, 12, 0));
                resenia7.setFechaActualizacion(LocalDateTime.of(2024, 9, 30, 12, 0));
                resenia7.setActivo(true);
                
                reseniaRepository.save(resenia7);
                logger.info("Reseña creada: Poleron StarCraft - ID: {}", resenia7.getId());
            }

            // Reseña 8: Catan (Producto ID 18, Usuario ID 5)
            if (!reseniaRepository.existsByIdProductoAndIdUsuario(18L, 5L)) {
                Resenia resenia8 = new Resenia();
                resenia8.setIdProducto(18L);
                resenia8.setIdUsuario(5L);
                resenia8.setUsuarioNombre("aa aa");
                resenia8.setRating(5);
                resenia8.setComentario("Juego de mesa clásico y divertido. Perfecto para jugar en familia o con amigos. Las reglas son fáciles de aprender y cada partida es diferente.");
                resenia8.setFechaCreacion(LocalDateTime.of(2024, 9, 25, 18, 0));
                resenia8.setFechaActualizacion(LocalDateTime.of(2024, 9, 25, 18, 0));
                resenia8.setActivo(true);
                
                reseniaRepository.save(resenia8);
                logger.info("Reseña creada: Catan - ID: {}", resenia8.getId());
            }

            logger.info("Carga de datos por defecto de reseñas completada");
            logger.info("Total de reseñas en BD: {}", reseniaRepository.count());
        };
    }
}

