package com.ampuero.msvc.referidos.config;

import com.ampuero.msvc.referidos.entities.Referido;
import com.ampuero.msvc.referidos.repositories.ReferidoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;


/**
 * Inicializador de datos por defecto para el microservicio de referidos
 * Se ejecuta al iniciar la aplicación si default.data.enabled=true
 * 
 * Ubicación: src/main/java/com/ampuero/msvc/referidos/config/ReferidoDataInitializer.java
 * 
 * Nota: Este inicializador crea usuarios referidos de prueba.
 * Los códigos de referido deben ser únicos.
 */
@Configuration
@ConditionalOnProperty(name = "default.data.enabled", havingValue = "true", matchIfMissing = true)
public class ReferidoDataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(ReferidoDataInitializer.class);

    /**
     * Inicializa los datos por defecto en la base de datos
     */
    @Bean
    @Order(1)
    CommandLineRunner initDatabase(ReferidoRepository referidoRepository) {
        return args -> {
            logger.info("Iniciando carga de datos por defecto para referidos");

            // Referido para usuario "aa" (ID 5) - Este referido representa al usuario "aa" en el sistema de referidos
            // El usuario "aa" tiene código de referido "AA001" en msvc-usuario
            // Aquí creamos su registro en el sistema de referidos
            if (!referidoRepository.existsByCodigoReferido("AA001")) {
                Referido referidoAA = new Referido();
                referidoAA.setNombreReferido("Usuario");
                referidoAA.setApellidosReferido("Prueba Completo");
                referidoAA.setEmailReferido("aa@test.com");
                referidoAA.setRunReferido("11111111-1");
                referidoAA.setCodigoReferido("AA001");
                referidoAA.setPuntosLevelup(750);
                referidoAA.setNivelUsuario("ORO");
                referidoAA.setIdReferidor(null); // Usuario raíz
                referidoAA.setActivo(true);
                referidoRepository.save(referidoAA);
                logger.info("Referido creado: AA001 - Usuario Prueba Completo (usuario aa)");
            }

            // Referidos por el usuario "aa" (código AA001)
            // Obtener el ID del referido AA001 para usar como referidor
            var referidoAA = referidoRepository.findByCodigoReferido("AA001").orElse(null);
            Long idReferidorAA = referidoAA != null ? referidoAA.getIdReferido() : null;

            // Referido 1 - Referido por AA001 (usuario "aa")
            if (!referidoRepository.existsByCodigoReferido("REFAA001")) {
                Referido referido1 = new Referido();
                referido1.setNombreReferido("Referido");
                referido1.setApellidosReferido("Uno");
                referido1.setEmailReferido("referido1@levelup.com");
                referido1.setRunReferido("22222222-2");
                referido1.setCodigoReferido("REFAA001");
                referido1.setPuntosLevelup(150);
                referido1.setNivelUsuario("BRONZE");
                referido1.setIdReferidor(idReferidorAA); // Referido por usuario "aa"
                referido1.setActivo(true);
                referidoRepository.save(referido1);
                logger.info("Referido creado: REFAA001 - Referido Uno (referido por AA001)");
            }

            // Referido 2 - Referido por AA001 (usuario "aa")
            if (!referidoRepository.existsByCodigoReferido("REFAA002")) {
                Referido referido2 = new Referido();
                referido2.setNombreReferido("Referida");
                referido2.setApellidosReferido("Dos");
                referido2.setEmailReferido("referido2@levelup.com");
                referido2.setRunReferido("33333333-3");
                referido2.setCodigoReferido("REFAA002");
                referido2.setPuntosLevelup(100);
                referido2.setNivelUsuario("BRONZE");
                referido2.setIdReferidor(idReferidorAA); // Referido por usuario "aa"
                referido2.setActivo(true);
                referidoRepository.save(referido2);
                logger.info("Referido creado: REFAA002 - Referida Dos (referido por AA001)");
            }

            // Referido 1 - Usuario raíz (sin referidor) - Solo si no existe
            if (!referidoRepository.existsByCodigoReferido("REF001")) {
                Referido referido1 = new Referido();
                referido1.setNombreReferido("Juan");
                referido1.setApellidosReferido("Pérez");
                referido1.setEmailReferido("juan.perez@levelup.com");
                referido1.setRunReferido("12345678-9");
                referido1.setCodigoReferido("REF001");
                referido1.setPuntosLevelup(500);
                referido1.setNivelUsuario("ORO");
                referido1.setIdReferidor(null); // Usuario raíz
                referido1.setActivo(true);
                referidoRepository.save(referido1);
                logger.info("Referido creado: REF001 - Juan Pérez");
            }

            // Referido 3 - Referido por REF001
            if (!referidoRepository.existsByCodigoReferido("REF003")) {
                Referido referido3 = new Referido();
                referido3.setNombreReferido("Carlos");
                referido3.setApellidosReferido("Rodríguez");
                referido3.setEmailReferido("carlos.rodriguez@levelup.com");
                referido3.setRunReferido("34567890-1");
                referido3.setCodigoReferido("REF003");
                referido3.setPuntosLevelup(100);
                referido3.setNivelUsuario("BRONZE");
                referido3.setIdReferidor(1L);
                referido3.setActivo(true);
                referidoRepository.save(referido3);
                logger.info("Referido creado: REF003 - Carlos Rodríguez (referido por REF001)");
            }

            // Referido 4 - Referido por REF002
            if (!referidoRepository.existsByCodigoReferido("REF004")) {
                Referido referido4 = new Referido();
                referido4.setNombreReferido("Ana");
                referido4.setApellidosReferido("Martínez");
                referido4.setEmailReferido("ana.martinez@levelup.com");
                referido4.setRunReferido("45678901-2");
                referido4.setCodigoReferido("REF004");
                referido4.setPuntosLevelup(50);
                referido4.setNivelUsuario("BRONZE");
                referido4.setIdReferidor(2L);
                referido4.setActivo(true);
                referidoRepository.save(referido4);
                logger.info("Referido creado: REF004 - Ana Martínez (referido por REF002)");
            }

            // Referido 5 - Usuario raíz (sin referidor)
            if (!referidoRepository.existsByCodigoReferido("REF005")) {
                Referido referido5 = new Referido();
                referido5.setNombreReferido("Luis");
                referido5.setApellidosReferido("Sánchez");
                referido5.setEmailReferido("luis.sanchez@levelup.com");
                referido5.setRunReferido("56789012-3");
                referido5.setCodigoReferido("REF005");
                referido5.setPuntosLevelup(1000);
                referido5.setNivelUsuario("DIAMANTE");
                referido5.setIdReferidor(null); // Usuario raíz
                referido5.setActivo(true);
                referidoRepository.save(referido5);
                logger.info("Referido creado: REF005 - Luis Sánchez");
            }

            logger.info("Carga de datos por defecto de referidos completada");
        };
    }
}

