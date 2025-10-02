package com.ampuero.msvc.carrito.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Carrito - Level-Up Gamer")
                        .version("1.0")
                        .description("API para gestión de carritos de compras, items y operaciones de e-commerce")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
