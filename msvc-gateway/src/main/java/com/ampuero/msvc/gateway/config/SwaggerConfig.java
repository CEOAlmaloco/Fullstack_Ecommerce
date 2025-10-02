package com.ampuero.msvc.gateway.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Level-Up Gamer - API Gateway")
                        .description("Gateway principal para todos los microservicios de Level-Up Gamer. " +
                                "Punto de entrada único con enrutamiento automático, CORS y health checks.")
                        .version("1.0.0")
                );
    }
}
