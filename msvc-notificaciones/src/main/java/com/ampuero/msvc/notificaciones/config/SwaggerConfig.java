package com.ampuero.msvc.notificaciones.config;

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
                        .title("API Notificaciones - Level-Up Gamer")
                        .version("1.0")
                        .description("API para gestión de notificaciones multicanal (email, WhatsApp, SMS, push) y plantillas de mensajes")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
