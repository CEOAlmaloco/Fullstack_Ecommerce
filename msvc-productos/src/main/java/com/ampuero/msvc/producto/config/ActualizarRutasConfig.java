package com.ampuero.msvc.producto.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Configuración para ejecutar automáticamente el script verificar_y_actualizar_rutas.sql
 * al iniciar el microservicio.
 * 
 * Solo ejecuta los comandos UPDATE, ignorando los SELECTs.
 */
@Configuration
@Profile({"docker", "prod"})
public class ActualizarRutasConfig {

    private static final Logger logger = LoggerFactory.getLogger(ActualizarRutasConfig.class);

    private final DataSource dataSource;
    private final ResourceLoader resourceLoader;
    private final boolean actualizarRutasEnabled;

    public ActualizarRutasConfig(
            DataSource dataSource,
            ResourceLoader resourceLoader,
            @Value("${actualizar.rutas.enabled:true}") boolean actualizarRutasEnabled) {
        this.dataSource = dataSource;
        this.resourceLoader = resourceLoader;
        this.actualizarRutasEnabled = actualizarRutasEnabled;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE + 1) // Ejecutar después del seed si existe
    public CommandLineRunner actualizarRutasImagenes() {
        return args -> {
            if (!actualizarRutasEnabled) {
                logger.info("Actualización automática de rutas deshabilitada (actualizar.rutas.enabled=false)");
                return;
            }

            logger.info("Ejecutando verificar_y_actualizar_rutas.sql automáticamente...");
            try {
                Resource scriptResource = resourceLoader.getResource("classpath:verificar_y_actualizar_rutas.sql");
                
                if (!scriptResource.exists()) {
                    logger.warn("Script verificar_y_actualizar_rutas.sql no encontrado en classpath");
                    return;
                }

                // Leer el script y extraer solo los UPDATEs
                List<String> updateStatements = extractUpdateStatements(scriptResource);
                
                if (updateStatements.isEmpty()) {
                    logger.info("No se encontraron comandos UPDATE en el script");
                    return;
                }

                // Ejecutar los UPDATEs
                JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
                int totalUpdates = 0;
                
                for (String updateStatement : updateStatements) {
                    try {
                        int rowsAffected = jdbcTemplate.update(updateStatement);
                        totalUpdates += rowsAffected;
                        logger.debug("UPDATE ejecutado: {} filas afectadas", rowsAffected);
                    } catch (Exception e) {
                        logger.warn("Error al ejecutar UPDATE (continuando): {}", e.getMessage());
                        // Continuar con el siguiente UPDATE
                    }
                }

                logger.info("Actualización de rutas completada: {} filas actualizadas en total", totalUpdates);
            } catch (Exception ex) {
                logger.error("Error al ejecutar verificar_y_actualizar_rutas.sql", ex);
            }
        };
    }

    /**
     * Extrae solo los comandos UPDATE del script SQL, ignorando SELECTs y comentarios
     */
    private List<String> extractUpdateStatements(Resource scriptResource) throws Exception {
        List<String> updateStatements = new ArrayList<>();
        StringBuilder currentStatement = new StringBuilder();
        
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(scriptResource.getInputStream(), StandardCharsets.UTF_8))) {
            
            String line;
            boolean inUpdateStatement = false;
            
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                
                // Ignorar líneas vacías y comentarios
                if (line.isEmpty() || line.startsWith("--") || line.startsWith("/*")) {
                    continue;
                }
                
                // Detectar inicio de UPDATE
                if (line.toUpperCase().startsWith("UPDATE ")) {
                    inUpdateStatement = true;
                    currentStatement = new StringBuilder(line);
                } 
                // Continuar acumulando líneas del UPDATE hasta encontrar punto y coma
                else if (inUpdateStatement) {
                    currentStatement.append(" ").append(line);
                    
                    // Si termina con punto y coma, guardar el statement completo
                    if (line.endsWith(";")) {
                        String statement = currentStatement.toString().trim();
                        if (!statement.isEmpty()) {
                            updateStatements.add(statement);
                        }
                        currentStatement = new StringBuilder();
                        inUpdateStatement = false;
                    }
                }
            }
            
            // Si quedó un statement sin terminar, agregarlo también
            if (inUpdateStatement && currentStatement.length() > 0) {
                String statement = currentStatement.toString().trim();
                if (!statement.isEmpty() && !statement.endsWith(";")) {
                    statement += ";";
                }
                if (!statement.isEmpty()) {
                    updateStatements.add(statement);
                }
            }
        }
        
        return updateStatements;
    }
}

