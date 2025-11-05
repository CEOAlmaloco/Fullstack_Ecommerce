package com.ampuero.msvc.eventos.config;

import com.ampuero.msvc.eventos.models.Evento;
import com.ampuero.msvc.eventos.repositories.EventoRepository;
import com.ampuero.msvc.eventos.services.ImageBase64Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.time.LocalDateTime;

/**
 * Inicializador de datos por defecto para el microservicio de eventos
 * Se ejecuta al iniciar la aplicación si default.data.enabled=true
 */
@Configuration
@ConditionalOnProperty(name = "default.data.enabled", havingValue = "true", matchIfMissing = true)
public class EventoDataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(EventoDataInitializer.class);

    @Bean
    @Order(1)
    CommandLineRunner initDatabase(EventoRepository eventoRepository, ImageBase64Service imageBase64Service) {
        return args -> {
            logger.info("Iniciando carga de datos por defecto para eventos");

            // Evento 1: Santiago Gaming Fest
            if (!eventoRepository.existsByNombreEvento("Santiago Gaming Fest")) {
                Evento evento1 = new Evento();
                evento1.setNombreEvento("Santiago Gaming Fest");
                evento1.setDescripcionEvento("El evento gaming más grande de Santiago. Compite, juega y gana premios increíbles.");
                evento1.setFechaInicio(LocalDateTime.of(2025, 7, 12, 11, 0));
                evento1.setFechaFin(LocalDateTime.of(2025, 7, 12, 20, 0));
                evento1.setUbicacionEvento("Centro Cultural Estación Mapocho");
                evento1.setCiudad("Santiago");
                evento1.setCoordenadasLatitud(-33.4275);
                evento1.setCoordenadasLongitud(-70.6785);
                evento1.setTipoEvento("TORNEO");
                evento1.setCuposMaximos(500);
                evento1.setCuposDisponibles(450);
                evento1.setCostoEntrada(5000.0);
                evento1.setPuntosLevelUp(100);
                evento1.setActivo(true);
                evento1.setRequisitosEdad(13);
                evento1.setEquiposRequeridos("Consola propia opcional");
                
                // Imagen en Base64 (intentar desde productos, si no existe usar string vacío)
                String imagen1 = imageBase64Service.convertImageToBase64("img/play5white.png");
                if (imagen1 == null || imagen1.isEmpty()) {
                    logger.warn("No se pudo cargar imagen para evento 1, creando evento sin imagen");
                    imagen1 = ""; // Si no hay imagen, usar string vacío
                }
                evento1.setImagen(imagen1);
                evento1.setImagenes("[]"); // Array vacío por ahora
                
                eventoRepository.save(evento1);
                logger.info("Evento creado: Santiago Gaming Fest - ID: {}", evento1.getIdEvento());
            }

            // Evento 2: Viña eSports Meetup
            if (!eventoRepository.existsByNombreEvento("Viña eSports Meetup")) {
                Evento evento2 = new Evento();
                evento2.setNombreEvento("Viña eSports Meetup");
                evento2.setDescripcionEvento("Encuentro de esports en Viña del Mar. Competencias, networking y diversión.");
                evento2.setFechaInicio(LocalDateTime.of(2025, 7, 26, 16, 0));
                evento2.setFechaFin(LocalDateTime.of(2025, 7, 26, 22, 0));
                evento2.setUbicacionEvento("Quinta Vergara");
                evento2.setCiudad("Viña del Mar");
                evento2.setCoordenadasLatitud(-33.0246);
                evento2.setCoordenadasLongitud(-71.5518);
                evento2.setTipoEvento("MEETUP");
                evento2.setCuposMaximos(300);
                evento2.setCuposDisponibles(280);
                evento2.setCostoEntrada(3000.0);
                evento2.setPuntosLevelUp(80);
                evento2.setActivo(true);
                evento2.setRequisitosEdad(13);
                evento2.setEquiposRequeridos("Ninguno");
                
                String imagen2 = imageBase64Service.convertImageToBase64("img/play4.png");
                if (imagen2 == null) {
                    imagen2 = "";
                }
                evento2.setImagen(imagen2);
                evento2.setImagenes("[]");
                
                eventoRepository.save(evento2);
                logger.info("Evento creado: Viña eSports Meetup");
            }

            // Evento 3: Concepción Retro Game Day
            if (!eventoRepository.existsByNombreEvento("Concepción Retro Game Day")) {
                Evento evento3 = new Evento();
                evento3.setNombreEvento("Concepción Retro Game Day");
                evento3.setDescripcionEvento("Día dedicado a los juegos retro. Disfruta de clásicos y nostalgia gaming.");
                evento3.setFechaInicio(LocalDateTime.of(2025, 8, 9, 12, 0));
                evento3.setFechaFin(LocalDateTime.of(2025, 8, 9, 20, 0));
                evento3.setUbicacionEvento("Plaza de la Independencia");
                evento3.setCiudad("Concepción");
                evento3.setCoordenadasLatitud(-36.8201);
                evento3.setCoordenadasLongitud(-73.0444);
                evento3.setTipoEvento("MEETUP");
                evento3.setCuposMaximos(200);
                evento3.setCuposDisponibles(180);
                evento3.setCostoEntrada(2000.0);
                evento3.setPuntosLevelUp(70);
                evento3.setActivo(true);
                evento3.setRequisitosEdad(10);
                evento3.setEquiposRequeridos("Ninguno");
                
                String imagen3 = imageBase64Service.convertImageToBase64("img/mandoplay.png");
                if (imagen3 == null) {
                    imagen3 = "";
                }
                evento3.setImagen(imagen3);
                evento3.setImagenes("[]");
                
                eventoRepository.save(evento3);
                logger.info("Evento creado: Concepción Retro Game Day");
            }

            // Evento 4: La Serena LAN Party
            if (!eventoRepository.existsByNombreEvento("La Serena LAN Party")) {
                Evento evento4 = new Evento();
                evento4.setNombreEvento("La Serena LAN Party");
                evento4.setDescripcionEvento("LAN Party en La Serena. Trae tu PC y juega con otros gamers.");
                evento4.setFechaInicio(LocalDateTime.of(2025, 8, 23, 14, 0));
                evento4.setFechaFin(LocalDateTime.of(2025, 8, 23, 22, 0));
                evento4.setUbicacionEvento("Mall Plaza La Serena");
                evento4.setCiudad("La Serena");
                evento4.setCoordenadasLatitud(-29.9027);
                evento4.setCoordenadasLongitud(-71.2519);
                evento4.setTipoEvento("LANZAMIENTO");
                evento4.setCuposMaximos(150);
                evento4.setCuposDisponibles(140);
                evento4.setCostoEntrada(4000.0);
                evento4.setPuntosLevelUp(60);
                evento4.setActivo(true);
                evento4.setRequisitosEdad(16);
                evento4.setEquiposRequeridos("PC propia");
                
                String imagen4 = imageBase64Service.convertImageToBase64("img/mandoplayazul.png");
                if (imagen4 == null) {
                    imagen4 = "";
                }
                evento4.setImagen(imagen4);
                evento4.setImagenes("[]");
                
                eventoRepository.save(evento4);
                logger.info("Evento creado: La Serena LAN Party");
            }

            // Evento 5: Antofagasta Arena Gaming
            if (!eventoRepository.existsByNombreEvento("Antofagasta Arena Gaming")) {
                Evento evento5 = new Evento();
                evento5.setNombreEvento("Antofagasta Arena Gaming");
                evento5.setDescripcionEvento("Arena gaming en Antofagasta. Competencias profesionales y amateur.");
                evento5.setFechaInicio(LocalDateTime.of(2025, 9, 6, 15, 0));
                evento5.setFechaFin(LocalDateTime.of(2025, 9, 6, 23, 0));
                evento5.setUbicacionEvento("Plaza Colón");
                evento5.setCiudad("Antofagasta");
                evento5.setCoordenadasLatitud(-23.6509);
                evento5.setCoordenadasLongitud(-70.3975);
                evento5.setTipoEvento("TORNEO");
                evento5.setCuposMaximos(250);
                evento5.setCuposDisponibles(230);
                evento5.setCostoEntrada(3500.0);
                evento5.setPuntosLevelUp(80);
                evento5.setActivo(true);
                evento5.setRequisitosEdad(13);
                evento5.setEquiposRequeridos("Consola propia opcional");
                
                String imagen5 = imageBase64Service.convertImageToBase64("img/audifonoazul.png");
                if (imagen5 == null) {
                    imagen5 = "";
                }
                evento5.setImagen(imagen5);
                evento5.setImagenes("[]");
                
                eventoRepository.save(evento5);
                logger.info("Evento creado: Antofagasta Arena Gaming");
            }

            // Evento 6: Temuco Indie Dev Showcase
            if (!eventoRepository.existsByNombreEvento("Temuco Indie Dev Showcase")) {
                Evento evento6 = new Evento();
                evento6.setNombreEvento("Temuco Indie Dev Showcase");
                evento6.setDescripcionEvento("Muestra de juegos independientes desarrollados en Chile.");
                evento6.setFechaInicio(LocalDateTime.of(2025, 9, 20, 10, 0));
                evento6.setFechaFin(LocalDateTime.of(2025, 9, 20, 18, 0));
                evento6.setUbicacionEvento("Plaza Aníbal Pinto");
                evento6.setCiudad("Temuco");
                evento6.setCoordenadasLatitud(-38.7359);
                evento6.setCoordenadasLongitud(-72.5904);
                evento6.setTipoEvento("WORKSHOP");
                evento6.setCuposMaximos(100);
                evento6.setCuposDisponibles(90);
                evento6.setCostoEntrada(2500.0);
                evento6.setPuntosLevelUp(60);
                evento6.setActivo(true);
                evento6.setRequisitosEdad(13);
                evento6.setEquiposRequeridos("Ninguno");
                
                String imagen6 = imageBase64Service.convertImageToBase64("img/audilogitech.png");
                if (imagen6 == null) {
                    imagen6 = "";
                }
                evento6.setImagen(imagen6);
                evento6.setImagenes("[]");
                
                eventoRepository.save(evento6);
                logger.info("Evento creado: Temuco Indie Dev Showcase");
            }

            // Evento 7: Puerto Montt Game Night
            if (!eventoRepository.existsByNombreEvento("Puerto Montt Game Night")) {
                Evento evento7 = new Evento();
                evento7.setNombreEvento("Puerto Montt Game Night");
                evento7.setDescripcionEvento("Noche gaming en Puerto Montt. Juegos, música y diversión.");
                evento7.setFechaInicio(LocalDateTime.of(2025, 10, 4, 18, 0));
                evento7.setFechaFin(LocalDateTime.of(2025, 10, 4, 23, 0));
                evento7.setUbicacionEvento("Arena Puerto Montt");
                evento7.setCiudad("Puerto Montt");
                evento7.setCoordenadasLatitud(-41.4718);
                evento7.setCoordenadasLongitud(-72.9366);
                evento7.setTipoEvento("MEETUP");
                evento7.setCuposMaximos(180);
                evento7.setCuposDisponibles(170);
                evento7.setCostoEntrada(3000.0);
                evento7.setPuntosLevelUp(90);
                evento7.setActivo(true);
                evento7.setRequisitosEdad(13);
                evento7.setEquiposRequeridos("Ninguno");
                
                String imagen7 = imageBase64Service.convertImageToBase64("img/teclado_rd_rgb.png");
                if (imagen7 == null) {
                    imagen7 = "";
                }
                evento7.setImagen(imagen7);
                evento7.setImagenes("[]");
                
                eventoRepository.save(evento7);
                logger.info("Evento creado: Puerto Montt Game Night");
            }

            logger.info("Carga de datos por defecto de eventos completada");
            logger.info("Total de eventos en BD: {}", eventoRepository.count());
        };
    }
}

