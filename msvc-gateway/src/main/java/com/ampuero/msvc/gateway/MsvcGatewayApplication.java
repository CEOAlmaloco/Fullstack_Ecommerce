package com.ampuero.msvc.gateway;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@SpringBootApplication
public class MsvcGatewayApplication {

    private final Environment environment;

    public MsvcGatewayApplication(Environment environment) {
        this.environment = environment;
    }

    public static void main(String[] args) {
        SpringApplication.run(MsvcGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        String authServiceUrl = environment.getProperty("clients.auth.base-url", "http://localhost:8001");
        String usuarioServiceUrl = environment.getProperty("clients.usuario.base-url", "http://localhost:8095");
        String productosServiceUrl = environment.getProperty("clients.productos.base-url", "http://localhost:8003");
        String carritoServiceUrl = environment.getProperty("clients.carrito.base-url", "http://localhost:8008");
        String pedidoServiceUrl = environment.getProperty("clients.pedido.base-url", "http://localhost:8085");
        String inventarioServiceUrl = environment.getProperty("clients.inventario.base-url", "http://localhost:8004");
        String referidosServiceUrl = environment.getProperty("clients.referidos.base-url", "http://localhost:8005");
        String reseniaServiceUrl = environment.getProperty("clients.resenia.base-url", "http://localhost:8010");
        String pagosServiceUrl = environment.getProperty("clients.pagos.base-url", "http://localhost:8011");
        String notificacionesServiceUrl = environment.getProperty("clients.notificaciones.base-url", "http://localhost:8006");
        String eventosServiceUrl = environment.getProperty("clients.eventos.base-url", "http://localhost:8092");
        String contenidoServiceUrl = environment.getProperty("clients.contenido.base-url", "http://localhost:8093");
        String promocionesServiceUrl = environment.getProperty("clients.promociones.base-url", "http://localhost:8091");

        return builder.routes()
                // Auth Service - Rutea /auth/** a /api/v1/auth/** en el microservicio
                .route("msvc-auth", r -> r.path("/auth/**")
                        .filters(f -> f.rewritePath("/auth/(?<path>.*)", "/api/v1/auth/${path}"))
                        .uri(authServiceUrl))

                // User Service - Rutea /usuarios/** a /api/v1/usuarios/** en el microservicio
                .route("msvc-usuario", r -> r.path("/usuarios/**")
                        .filters(f -> f.rewritePath("/usuarios/(?<path>.*)", "/api/v1/usuarios/${path}"))
                        .uri(usuarioServiceUrl))

                // Product Service - Reescribe /productos a /api/v1/productos y /productos/** a /api/v1/productos/**
                .route("msvc-productos", r -> r.path("/productos", "/productos/**")
                        .filters(f -> f.rewritePath("/productos(?<path>.*)", "/api/v1/productos${path}"))
                        .uri(productosServiceUrl))

                // Cart Service - Sin prefijo, va directo a /carrito/**
                .route("msvc-carrito", r -> r.path("/carrito/**")
                        .uri(carritoServiceUrl))

                // Order Service - Reescribe /pedidos a /api/v1/pedidos
                .route("msvc-pedido", r -> r.path("/pedidos", "/pedidos/**")
                        .filters(f -> f.rewritePath("/pedidos(?<path>.*)", "/api/v1/pedidos${path}"))
                        .uri(pedidoServiceUrl))

                // Inventory Service
                .route("msvc-inventario", r -> r.path("/inventario/**")
                        .filters(f -> f.rewritePath("/inventario(?<path>.*)", "/api/v1/inventario${path}"))
                        .uri(inventarioServiceUrl))

                // Referral Service
                .route("msvc-referidos", r -> r.path("/referidos/**")
                        .filters(f -> f.rewritePath("/referidos(?<path>.*)", "/api/v1/referidos${path}"))
                        .uri(referidosServiceUrl))

                // Referral Points Service (puntos)
                .route("msvc-referidos-puntos", r -> r.path("/puntos/**")
                        .filters(f -> f.stripPrefix(1).prefixPath("/api/v1/puntos"))
                        .uri(referidosServiceUrl))

                // Review Service - Rutea /resenias/** a /api/v1/resenias/** en el microservicio
                .route("msvc-resenia", r -> r.path("/resenias/**")
                        .filters(f -> f.rewritePath("/resenias/(?<path>.*)", "/api/v1/resenias/${path}"))
                        .uri(reseniaServiceUrl))

                // Payment Service - Reescribe /pagos a /api/v1/pagos
                .route("msvc-pagos", r -> r.path("/pagos", "/pagos/**")
                        .filters(f -> f.rewritePath("/pagos(?<path>.*)", "/api/v1/pagos${path}"))
                        .uri(pagosServiceUrl))

                // Notification Service
                .route("msvc-notificaciones", r -> r.path("/notificaciones/**")
                        .uri(notificacionesServiceUrl))

                // Event Service
                .route("msvc-eventos", r -> r.path("/eventos/**")
                        .uri(eventosServiceUrl))

                // Content Service
                .route("msvc-contenido", r -> r.path("/contenido/**")
                        .uri(contenidoServiceUrl))

                // Promotion Service - Reescribe /promociones a /api/v1/promociones
                .route("msvc-promociones", r -> r.path("/promociones", "/promociones/**")
                        .filters(f -> f.rewritePath("/promociones(?<path>.*)", "/api/v1/promociones${path}"))
                        .uri(promocionesServiceUrl))

                .build();
    }

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        // Leer primero de variable de entorno CORS_ORIGINS, luego de cors.allowed-origins
        String allowedOrigins = System.getenv("CORS_ORIGINS");
        if (allowedOrigins == null || allowedOrigins.isEmpty()) {
            allowedOrigins = environment.getProperty("cors.allowed-origins", "http://localhost:5173,http://localhost:3000");
        }
        
        // Log para debugging
        System.out.println("CORS Configuration - allowedOrigins: [" + allowedOrigins + "]");
        
        // Si es "*", permitir todos los orígenes usando addAllowedOriginPattern
        String trimmed = allowedOrigins != null ? allowedOrigins.trim() : "";
        if ("*".equals(trimmed)) {
            corsConfig.addAllowedOriginPattern("*");
            corsConfig.setAllowCredentials(true);
            System.out.println("CORS: Using wildcard pattern (*)");
        } else {
            // Si son orígenes específicos, agregarlos normalmente
            Arrays.stream(allowedOrigins.split(","))
                    .map(String::trim)
                    .filter(origin -> !origin.isEmpty())
                    .distinct()
                    .forEach(corsConfig::addAllowedOrigin);
            corsConfig.setAllowCredentials(true);
            System.out.println("CORS: Using specific origins: " + corsConfig.getAllowedOrigins());
        }
        
        corsConfig.addAllowedMethod("*");
        corsConfig.addAllowedHeader("*");
        corsConfig.setMaxAge(3600L); // Cache preflight for 1 hour

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }
}