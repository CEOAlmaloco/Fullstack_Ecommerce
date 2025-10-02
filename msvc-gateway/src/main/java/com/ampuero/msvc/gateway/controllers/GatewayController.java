package com.ampuero.msvc.gateway.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/gateway")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
@Tag(name = "Gateway", description = "API Gateway Level-Up Gamer")
public class GatewayController {

    @GetMapping("/health")
    @Operation(summary = "Health Check", description = "Estado del Gateway")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "Level-Up Gamer API Gateway");
        response.put("version", "1.0.0");
        response.put("timestamp", LocalDateTime.now());
        response.put("port", 8080);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/routes")
    @Operation(summary = "Rutas disponibles", description = "Lista todos los microservicios")
    public ResponseEntity<Map<String, Object>> getRoutes() {
        Map<String, Object> routes = new HashMap<>();

        // Core Services
        routes.put("auth", Map.of(
                "url", "http://localhost:8081/auth/**",
                "description", "Autenticación JWT",
                "status", "active"
        ));

        routes.put("usuarios", Map.of(
                "url", "http://localhost:8082/usuarios/**",
                "description", "Gestión de usuarios",
                "status", "active"
        ));

        // Business Services
        routes.put("productos", Map.of(
                "url", "http://localhost:8083/productos/**",
                "description", "Catálogo de productos",
                "status", "active"
        ));

        routes.put("carrito", Map.of(
                "url", "http://localhost:8084/carrito/**",
                "description", "Carrito de compras",
                "status", "pending"
        ));

        routes.put("pedidos", Map.of(
                "url", "http://localhost:8085/pedidos/**",
                "description", "Gestión de pedidos",
                "status", "pending"
        ));

        routes.put("inventario", Map.of(
                "url", "http://localhost:8086/inventario/**",
                "description", "Control de stock",
                "status", "active"
        ));

        // Feature Services
        routes.put("referidos", Map.of(
                "url", "http://localhost:8087/referidos/**",
                "description", "Sistema de referidos",
                "status", "pending"
        ));

        routes.put("resenias", Map.of(
                "url", "http://localhost:8088/resenias/**",
                "description", "Reseñas de productos",
                "status", "pending"
        ));

        routes.put("pagos", Map.of(
                "url", "http://localhost:8089/pagos/**",
                "description", "Procesamiento de pagos",
                "status", "pending"
        ));

        routes.put("notificaciones", Map.of(
                "url", "http://localhost:8090/notificaciones/**",
                "description", "Notificaciones multi-canal",
                "status", "pending"
        ));

        routes.put("eventos", Map.of(
                "url", "http://localhost:8091/eventos/**",
                "description", "Eventos gaming",
                "status", "pending"
        ));

        routes.put("contenido", Map.of(
                "url", "http://localhost:8092/contenido/**",
                "description", "Blogs y contenido",
                "status", "pending"
        ));

        routes.put("promociones", Map.of(
                "url", "http://localhost:8093/promociones/**",
                "description", "Promociones y descuentos",
                "status", "pending"
        ));

        return ResponseEntity.ok(routes);
    }

    @GetMapping("/info")
    @Operation(summary = "Información del Gateway", description = "Detalles técnicos")
    public ResponseEntity<Map<String, Object>> getInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("name", "Level-Up Gamer Gateway");
        info.put("description", "API Gateway para microservicios de gaming");
        info.put("version", "1.0.0");
        info.put("port", 8080);
        info.put("cors", new String[]{"http://localhost:3000", "http://localhost:5173"});
        info.put("totalRoutes", 13);
        info.put("activeServices", 3);
        info.put("pendingServices", 10);
        return ResponseEntity.ok(info);
    }
}