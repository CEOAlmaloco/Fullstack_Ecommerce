package com.ampuero.msvc.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Rutas para msvc-auth
                .route("auth-service", r -> r
                        .path("/api/v1/auth/**")
                        .uri("http://localhost:8001"))

                // Rutas para msvc-usuario
                .route("usuario-service", r -> r
                        .path("/api/v1/usuarios/**")
                        .uri("http://localhost:8002"))

                // Rutas para msvc-productos
                .route("productos-service", r -> r
                        .path("/api/v1/productos/**")
                        .uri("http://localhost:8003"))

                // Rutas para msvc-inventario
                .route("inventario-service", r -> r
                        .path("/api/v1/inventario/**")
                        .uri("http://localhost:8004"))

                // Rutas para msvc-referidos
                .route("referidos-service", r -> r
                        .path("/api/v1/referidos/**")
                        .uri("http://localhost:8005"))

                // Rutas para msvc-notificaciones
                .route("notificaciones-service", r -> r
                        .path("/api/v1/notificaciones/**")
                        .uri("http://localhost:8006"))

                // Rutas para msvc-promociones
                .route("promociones-service", r -> r
                        .path("/api/v1/promociones/**")
                        .uri("http://localhost:8007"))

                // Rutas para msvc-carrito
                .route("carrito-service", r -> r
                        .path("/api/v1/carrito/**")
                        .uri("http://localhost:8008"))

                // Rutas para msvc-pedido
                .route("pedido-service", r -> r
                        .path("/api/v1/pedidos/**")
                        .uri("http://localhost:8009"))

                // Rutas para msvc-pagos
                .route("pagos-service", r -> r
                        .path("/api/v1/pagos/**")
                        .uri("http://localhost:8010"))

                .build();
    }
}
