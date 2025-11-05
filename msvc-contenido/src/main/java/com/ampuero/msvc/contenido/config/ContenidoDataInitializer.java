package com.ampuero.msvc.contenido.config;

import com.ampuero.msvc.contenido.models.Articulo;
import com.ampuero.msvc.contenido.repositories.ArticuloRepository;
import com.ampuero.msvc.contenido.services.ImageBase64Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.time.LocalDateTime;

/**
 * Inicializador de datos por defecto para el microservicio de contenido (blogs)
 * Se ejecuta al iniciar la aplicación si default.data.enabled=true
 */
@Configuration
@ConditionalOnProperty(name = "default.data.enabled", havingValue = "true", matchIfMissing = true)
public class ContenidoDataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(ContenidoDataInitializer.class);

    @Bean
    @Order(1)
    CommandLineRunner initDatabase(ArticuloRepository articuloRepository, ImageBase64Service imageBase64Service) {
        return args -> {
            logger.info("Iniciando carga de datos por defecto para blogs/contenido");

            // Blog 1: PlayStation 5 Pro
            if (!articuloRepository.findByTituloArticulo("PlayStation 5 Pro: Todo lo que necesitas saber").isPresent()) {
                Articulo blog1 = new Articulo();
                blog1.setTituloArticulo("PlayStation 5 Pro: Todo lo que necesitas saber");
                blog1.setResumenArticulo("Sony anuncia oficialmente la PlayStation 5 Pro con mejoras significativas en rendimiento y gráficos. Descubre todas las características y fecha de lanzamiento.");
                blog1.setContenidoArticulo("Sony ha anunciado oficialmente la PlayStation 5 Pro, una versión mejorada de su consola de última generación. Con mejoras significativas en rendimiento y gráficos, esta nueva consola promete llevar la experiencia gaming a otro nivel.");
                blog1.setCategoriaArticulo("NOTICIAS");
                blog1.setAutorArticulo("Miguel Torres");
                blog1.setFechaPublicacion(LocalDateTime.of(2024, 12, 15, 10, 0));
                blog1.setFechaActualizacion(LocalDateTime.of(2024, 12, 15, 10, 0));
                blog1.setEstadoArticulo("PUBLICADO");
                blog1.setTiempoLectura(5);
                blog1.setVistasArticulo(0);
                blog1.setLikesArticulo(0);
                blog1.setCompartidosArticulo(0);
                blog1.setEsDestacado(true);
                blog1.setEsPremium(false);
                blog1.setActivo(true);
                
                String imagen1 = imageBase64Service.convertImageToBase64("img/play5white.png");
                if (imagen1 == null || imagen1.isEmpty()) {
                    logger.warn("No se pudo cargar imagen para blog 1, creando blog sin imagen");
                    imagen1 = "";
                } else {
                    logger.debug("Imagen cargada para blog 1: {} caracteres", imagen1.length());
                }
                blog1.setImagenArticulo(imagen1);
                
                articuloRepository.save(blog1);
                logger.info("Blog creado: PlayStation 5 Pro - ID: {}", blog1.getIdArticulo());
            }

            // Blog 2: Gaming Setup 2025
            if (!articuloRepository.findByTituloArticulo("Guía completa para armar tu setup gaming perfecto en 2025").isPresent()) {
                Articulo blog2 = new Articulo();
                blog2.setTituloArticulo("Guía completa para armar tu setup gaming perfecto en 2025");
                blog2.setResumenArticulo("¿Quieres crear el setup gaming definitivo? Te mostramos los componentes esenciales, periféricos recomendados y tips de configuración para que tengas la mejor experiencia de juego posible.");
                blog2.setContenidoArticulo("Desde monitores hasta sillas gaming, aquí encontrarás todo lo que necesitas para crear el setup gaming perfecto.");
                blog2.setCategoriaArticulo("GUIAS");
                blog2.setAutorArticulo("Ana López");
                blog2.setFechaPublicacion(LocalDateTime.of(2024, 12, 22, 14, 0));
                blog2.setFechaActualizacion(LocalDateTime.of(2024, 12, 22, 14, 0));
                blog2.setEstadoArticulo("PUBLICADO");
                blog2.setTiempoLectura(10);
                blog2.setVistasArticulo(0);
                blog2.setLikesArticulo(0);
                blog2.setCompartidosArticulo(0);
                blog2.setEsDestacado(true);
                blog2.setEsPremium(false);
                blog2.setActivo(true);
                
                String imagen2 = imageBase64Service.convertImageToBase64("img/monitorasus.png");
                if (imagen2 == null || imagen2.isEmpty()) {
                    logger.warn("No se pudo cargar imagen para blog 2, creando blog sin imagen");
                    imagen2 = "";
                } else {
                    logger.debug("Imagen cargada para blog 2: {} caracteres", imagen2.length());
                }
                blog2.setImagenArticulo(imagen2);
                
                articuloRepository.save(blog2);
                logger.info("Blog creado: Gaming Setup 2025 - ID: {}", blog2.getIdArticulo());
            }

            // Blog 3: eSports Chile
            if (!articuloRepository.findByTituloArticulo("El crecimiento de los esports en Chile: Una industria en expansión").isPresent()) {
                Articulo blog3 = new Articulo();
                blog3.setTituloArticulo("El crecimiento de los esports en Chile: Una industria en expansión");
                blog3.setResumenArticulo("Los deportes electrónicos han experimentado un crecimiento exponencial en Chile durante los últimos años. Desde torneos locales hasta competencias internacionales, el país se posiciona como un referente en la región.");
                blog3.setContenidoArticulo("Conoce los equipos más destacados y las oportunidades que ofrece esta industria.");
                blog3.setCategoriaArticulo("ESPORTS");
                blog3.setAutorArticulo("Carlos Mendoza");
                blog3.setFechaPublicacion(LocalDateTime.of(2024, 12, 28, 16, 0));
                blog3.setFechaActualizacion(LocalDateTime.of(2024, 12, 28, 16, 0));
                blog3.setEstadoArticulo("PUBLICADO");
                blog3.setTiempoLectura(9);
                blog3.setVistasArticulo(0);
                blog3.setLikesArticulo(0);
                blog3.setCompartidosArticulo(0);
                blog3.setEsDestacado(true);
                blog3.setEsPremium(false);
                blog3.setActivo(true);
                
                String imagen3 = imageBase64Service.convertImageToBase64("img/audilogitech.png");
                if (imagen3 == null || imagen3.isEmpty()) {
                    logger.warn("No se pudo cargar imagen para blog 3, creando blog sin imagen");
                    imagen3 = "";
                } else {
                    logger.debug("Imagen cargada para blog 3: {} caracteres", imagen3.length());
                }
                blog3.setImagenArticulo(imagen3);
                
                articuloRepository.save(blog3);
                logger.info("Blog creado: eSports Chile - ID: {}", blog3.getIdArticulo());
            }

            // Blog 4: Gaming Movement
            if (!articuloRepository.findByTituloArticulo("Gaming Movement: La revolución de los videojuegos independientes").isPresent()) {
                Articulo blog4 = new Articulo();
                blog4.setTituloArticulo("Gaming Movement: La revolución de los videojuegos independientes");
                blog4.setResumenArticulo("El movimiento de desarrolladores independientes está transformando la industria gaming. Pequeños estudios crean experiencias únicas que compiten con grandes producciones.");
                blog4.setContenidoArticulo("Descubre los juegos indie más prometedores del año.");
                blog4.setCategoriaArticulo("INDUSTRIA");
                blog4.setAutorArticulo("Sofia Ramirez");
                blog4.setFechaPublicacion(LocalDateTime.of(2025, 1, 2, 12, 0));
                blog4.setFechaActualizacion(LocalDateTime.of(2025, 1, 2, 12, 0));
                blog4.setEstadoArticulo("PUBLICADO");
                blog4.setTiempoLectura(7);
                blog4.setVistasArticulo(0);
                blog4.setLikesArticulo(0);
                blog4.setCompartidosArticulo(0);
                blog4.setEsDestacado(true);
                blog4.setEsPremium(false);
                blog4.setActivo(true);
                
                String imagen4 = imageBase64Service.convertImageToBase64("img/teclado_rd_rgb.png");
                if (imagen4 == null || imagen4.isEmpty()) {
                    logger.warn("No se pudo cargar imagen para blog 4, creando blog sin imagen");
                    imagen4 = "";
                } else {
                    logger.debug("Imagen cargada para blog 4: {} caracteres", imagen4.length());
                }
                blog4.setImagenArticulo(imagen4);
                
                articuloRepository.save(blog4);
                logger.info("Blog creado: Gaming Movement - ID: {}", blog4.getIdArticulo());
            }

            // Blog 5: Realidad Virtual
            if (!articuloRepository.findByTituloArticulo("Realidad Virtual en 2025: Guía para principiantes").isPresent()) {
                Articulo blog5 = new Articulo();
                blog5.setTituloArticulo("Realidad Virtual en 2025: Guía para principiantes");
                blog5.setResumenArticulo("La realidad virtual ha llegado para quedarse. Con nuevos dispositivos más accesibles y una biblioteca de juegos en constante crecimiento, nunca ha sido mejor momento para adentrarse en el mundo VR.");
                blog5.setContenidoArticulo("Explora las últimas tecnologías VR y cómo están revolucionando la forma en que experimentamos los videojuegos.");
                blog5.setCategoriaArticulo("TECNOLOGIA");
                blog5.setAutorArticulo("Diego Silva");
                blog5.setFechaPublicacion(LocalDateTime.of(2025, 1, 8, 10, 0));
                blog5.setFechaActualizacion(LocalDateTime.of(2025, 1, 8, 10, 0));
                blog5.setEstadoArticulo("PUBLICADO");
                blog5.setTiempoLectura(6);
                blog5.setVistasArticulo(0);
                blog5.setLikesArticulo(0);
                blog5.setCompartidosArticulo(0);
                blog5.setEsDestacado(true);
                blog5.setEsPremium(false);
                blog5.setActivo(true);
                
                String imagen5 = imageBase64Service.convertImageToBase64("img/webcamlogitech.png");
                if (imagen5 == null || imagen5.isEmpty()) {
                    logger.warn("No se pudo cargar imagen para blog 5, creando blog sin imagen");
                    imagen5 = "";
                } else {
                    logger.debug("Imagen cargada para blog 5: {} caracteres", imagen5.length());
                }
                blog5.setImagenArticulo(imagen5);
                
                articuloRepository.save(blog5);
                logger.info("Blog creado: Realidad Virtual - ID: {}", blog5.getIdArticulo());
            }

            // Blog 6: Juegos 2025
            if (!articuloRepository.findByTituloArticulo("Los juegos más esperados del resto del 2025").isPresent()) {
                Articulo blog6 = new Articulo();
                blog6.setTituloArticulo("Los juegos más esperados del resto del 2025");
                blog6.setResumenArticulo("El año gaming está lleno de sorpresas. Desde secuelas muy esperadas hasta nuevas IPs revolucionarias, te mostramos los títulos que marcarán el resto del año.");
                blog6.setContenidoArticulo("Descubre los juegos que marcarán el resto del 2025.");
                blog6.setCategoriaArticulo("LANZAMIENTOS");
                blog6.setAutorArticulo("Maria Gonzalez");
                blog6.setFechaPublicacion(LocalDateTime.of(2025, 1, 15, 15, 0));
                blog6.setFechaActualizacion(LocalDateTime.of(2025, 1, 15, 15, 0));
                blog6.setEstadoArticulo("PUBLICADO");
                blog6.setTiempoLectura(8);
                blog6.setVistasArticulo(0);
                blog6.setLikesArticulo(0);
                blog6.setCompartidosArticulo(0);
                blog6.setEsDestacado(true);
                blog6.setEsPremium(false);
                blog6.setActivo(true);
                
                String imagen6 = imageBase64Service.convertImageToBase64("img/micrologitech.png");
                if (imagen6 == null || imagen6.isEmpty()) {
                    logger.warn("No se pudo cargar imagen para blog 6, creando blog sin imagen");
                    imagen6 = "";
                } else {
                    logger.debug("Imagen cargada para blog 6: {} caracteres", imagen6.length());
                }
                blog6.setImagenArticulo(imagen6);
                
                articuloRepository.save(blog6);
                logger.info("Blog creado: Juegos 2025 - ID: {}", blog6.getIdArticulo());
            }

            logger.info("Carga de datos por defecto de blogs/contenido completada");
            logger.info("Total de blogs en BD: {}", articuloRepository.count());
        };
    }
}

