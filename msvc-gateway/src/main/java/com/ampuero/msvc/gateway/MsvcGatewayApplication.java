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
                // Auth Service - Rutea /auth/** a /api/v1/auth/** en el microservicio
                .route("msvc-auth", r -> r.path("/auth/**")
                        .filters(f -> f.rewritePath("/auth/(?<path>.*)", "/api/v1/auth/${path}"))
                        .uri("http://localhost:8001"))

                // User Service - Rutea /usuarios/** a /api/v1/usuarios/** en el microservicio
                .route("msvc-usuario", r -> r.path("/usuarios/**")
                        .filters(f -> f.rewritePath("/usuarios/(?<path>.*)", "/api/v1/usuarios/${path}"))
                        .uri("http://localhost:8095"))

                // Product Service - Reescribe /productos a /api/v1/productos y /productos/** a /api/v1/productos/**
                .route("msvc-productos", r -> r.path("/productos", "/productos/**")
                        .filters(f -> f.rewritePath("/productos(?<path>.*)", "/api/v1/productos${path}"))
                        .uri("http://localhost:8003"))

                // Cart Service - Sin prefijo, va directo a /carrito/**
                .route("msvc-carrito", r -> r.path("/carrito/**")
                        .uri("http://localhost:8008"))

                // Order Service
                .route("msvc-pedido", r -> r.path("/pedidos/**")
                        .uri("http://localhost:8085"))

                // Inventory Service
                .route("msvc-inventario", r -> r.path("/inventario/**")
                        .uri("http://localhost:8004"))

                // Referral Service
                .route("msvc-referidos", r -> r.path("/referidos/**")
                        .uri("http://localhost:8005"))

                // Review Service
                .route("msvc-resenia", r -> r.path("/resenias/**")
                        .uri("http://localhost:8010"))

                // Payment Service
                .route("msvc-pagos", r -> r.path("/pagos/**")
                        .uri("http://localhost:8011"))

                // Notification Service
                .route("msvc-notificaciones", r -> r.path("/notificaciones/**")
                        .uri("http://localhost:8006"))

                // Event Service
                .route("msvc-eventos", r -> r.path("/eventos/**")
                        .uri("http://localhost:8092"))

                // Content Service
                .route("msvc-contenido", r -> r.path("/contenido/**")
                        .uri("http://localhost:8093"))

                // Promotion Service
                .route("msvc-promociones", r -> r.path("/promociones/**")
                        .uri("http://localhost:8091"))

                .build();
    }

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.addAllowedOrigin("http://localhost:3000"); // Frontend React
        corsConfig.addAllowedOrigin("http://localhost:5173"); // Frontend React Vite
        corsConfig.addAllowedOrigin("http://localhost:4200"); // Frontend Angular
        corsConfig.addAllowedOrigin("http://10.0.2.2:8094"); // Android Emulator
        corsConfig.addAllowedMethod("*");
        corsConfig.addAllowedHeader("*");
        corsConfig.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }
}