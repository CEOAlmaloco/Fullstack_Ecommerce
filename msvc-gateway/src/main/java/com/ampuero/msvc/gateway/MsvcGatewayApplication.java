package com.ampuero.msvc.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@SpringBootApplication
public class MsvcGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsvcGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Auth Service
                .route("msvc-auth", r -> r.path("/auth/**")
                        .uri("http://localhost:8081"))

                // User Service
                .route("msvc-usuario", r -> r.path("/usuarios/**")
                        .uri("http://localhost:8082"))

                // Product Service
                .route("msvc-productos", r -> r.path("/productos/**")
                        .uri("http://localhost:8083"))

                // Cart Service
                .route("msvc-carrito", r -> r.path("/carrito/**")
                        .uri("http://localhost:8084"))

                // Order Service
                .route("msvc-pedido", r -> r.path("/pedidos/**")
                        .uri("http://localhost:8085"))

                // Inventory Service
                .route("msvc-inventario", r -> r.path("/inventario/**")
                        .uri("http://localhost:8086"))

                // Referral Service
                .route("msvc-referidos", r -> r.path("/referidos/**")
                        .uri("http://localhost:8087"))

                // Review Service
                .route("msvc-resenia", r -> r.path("/resenias/**")
                        .uri("http://localhost:8088"))

                // Payment Service
                .route("msvc-pagos", r -> r.path("/pagos/**")
                        .uri("http://localhost:8089"))

                // Notification Service
                .route("msvc-notificaciones", r -> r.path("/notificaciones/**")
                        .uri("http://localhost:8090"))

                // Event Service
                .route("msvc-eventos", r -> r.path("/eventos/**")
                        .uri("http://localhost:8091"))

                // Content Service
                .route("msvc-contenido", r -> r.path("/contenido/**")
                        .uri("http://localhost:8092"))

                // Promotion Service
                .route("msvc-promociones", r -> r.path("/promociones/**")
                        .uri("http://localhost:8093"))

                .build();
    }

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.addAllowedOrigin("http://localhost:3000"); // Frontend React
        corsConfig.addAllowedOrigin("http://localhost:4200"); // Frontend Angular
        corsConfig.addAllowedMethod("*");
        corsConfig.addAllowedHeader("*");
        corsConfig.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }
}