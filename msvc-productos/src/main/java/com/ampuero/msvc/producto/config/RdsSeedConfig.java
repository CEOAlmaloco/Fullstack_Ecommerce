package com.ampuero.msvc.producto.config;

import javax.sql.DataSource;

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
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
@Profile({"docker", "prod"})
public class RdsSeedConfig {

    private static final Logger logger = LoggerFactory.getLogger(RdsSeedConfig.class);

    private final DataSource dataSource;
    private final boolean seedEnabled;
    private final Resource seedScript;

    public RdsSeedConfig(
            DataSource dataSource,
            @Value("${seed.productos.enabled:true}") boolean seedEnabled,
            @Value("classpath:static/seed_productos.sql") Resource seedScript) {
        this.dataSource = dataSource;
        this.seedEnabled = seedEnabled;
        this.seedScript = seedScript;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public CommandLineRunner seedProductos() {
        return args -> {
            if (!seedEnabled) {
                logger.info("Seed de productos deshabilitado (seed.productos.enabled=false)");
                return;
            }

            logger.info("Ejecutando seed_productos.sql sobre la base de datos actual");
            try {
                ResourceDatabasePopulator populator = new ResourceDatabasePopulator(seedScript);
                populator.setContinueOnError(true);
                DatabasePopulatorUtils.execute(populator, dataSource);
                logger.info("Seed de productos ejecutado correctamente");
            } catch (Exception ex) {
                logger.error("Error al ejecutar seed_productos.sql", ex);
            }
        };
    }
}
