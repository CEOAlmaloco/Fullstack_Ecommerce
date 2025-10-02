# Microservicio Gateway - Level-Up Gamer

## Descripción

API Gateway que actúa como punto único de entrada para todos los microservicios de Level-Up Gamer. Centraliza la
autenticación, autorización, enrutamiento, balanceo de carga y políticas de seguridad para toda la arquitectura de
microservicios.

## Funcionalidad Principal

- Punto único de entrada para el frontend
- Enrutamiento inteligente a microservicios
- Validación centralizada de JWT tokens
- Manejo de CORS y políticas de seguridad
- Rate limiting y throttling
- Load balancing entre instancias
- Circuit breaker para tolerancia a fallos
- Logging y monitoreo centralizado

## Endpoints API

### Enrutamiento de Microservicios

- **Todas las rutas** `/api/**` - Proxy a microservicios correspondientes
- **Auth** `/api/auth/**` → msvc-auth (Puerto 8081)
- **Usuarios** `/api/usuarios/**` → msvc-usuario (Puerto 8082)
- **Productos** `/api/productos/**` → msvc-productos (Puerto 8083)
- **Carrito** `/api/carrito/**` → msvc-carrito (Puerto 8084)
- **Pedidos** `/api/pedidos/**` → msvc-pedido (Puerto 8085)
- **Inventario** `/api/inventario/**` → msvc-inventario (Puerto 8086)
- **Referidos** `/api/referidos/**` → msvc-referidos (Porto 8087)
- **Reseñas** `/api/resenias/**` → msvc-resenia (Puerto 8088)
- **Pagos** `/api/pagos/**` → msvc-pagos (Puerto 8089)
- **Notificaciones** `/api/notificaciones/**` → msvc-notificaciones (Puerto 8090)
- **Eventos** `/api/eventos/**` → msvc-eventos (Puerto 8091)
- **Contenido** `/api/contenido/**` → msvc-contenido (Puerto 8092)

### Gestión del Gateway

- **GET** `/gateway/health` - Estado de salud del gateway
- **GET** `/gateway/routes` - Rutas configuradas
- **GET** `/gateway/services` - Servicios registrados y su estado
- **POST** `/gateway/refresh` - Recargar configuración
- **GET** `/gateway/metrics` - Métricas de tráfico

### Autenticación Centralizada

- **POST** `/api/auth/login` - Login (proxy a msvc-auth)
- **POST** `/api/auth/refresh` - Refresh token (proxy a msvc-auth)
- **GET** `/api/auth/validate` - Validar token actual
- **POST** `/api/auth/logout` - Logout y blacklist token

## 🔄 Flujo de Comunicación Frontend ↔ Gateway ↔ Microservicios

### 1️⃣ Request del Frontend

```javascript
// Frontend hace petición con JWT
fetch('http://localhost:8080/usuarios/123', {
  method: 'GET',
  headers: {
    'Authorization': 'Bearer eyJ...',
    'Content-Type': 'application/json'
  }
})

1. Gateway recibe request en puerto 8080
2. Extrae JWT del header Authorization
3. Llama a msvc-auth/validate para verificar token
4. Si válido: enruta a msvc-usuario:8082/usuarios/123
5. Si inválido: retorna 401 Unauthorized
```

### 2️⃣ Validación de Token (Gateway → Auth)

```javascript
// Gateway valida token internamente
POST http://localhost:8081/auth/validate
{
  "token": "eyJ...",
  "requestPath": "/usuarios/123",
  "method": "GET"
}

// Respuesta de msvc-auth
{
  "valid": true,
  "userId": 123,
  "tipoUsuario": "CLIENTE",
  "permissions": ["READ_PROFILE"]
}
```

### 3️⃣ Enrutamiento a Microservicio

```javascript
// Gateway enruta a microservicio destino
GET http://localhost:8082/usuarios/123
Headers:
  X-User-Id: 123
  X-User-Type: CLIENTE
  X-Request-Id: uuid-12345
  Authorization: Bearer eyJ...
```

### 4️⃣ Respuesta al Frontend

```javascript
// Gateway retorna respuesta del microservicio
{
  "idUsuario": 123,
  "nombreUsuario": "Juan",
  "correoUsuario": "juan@duoc.cl",
  "tipoUsuario": "CLIENTE"
}
```

## Comunicación con otros Microservicios

### 📤 Envía peticiones a:

- **msvc-auth (8081)**: Validación de tokens JWT en cada request
- **Todos los microservicios (8082-8093)**: Proxy de requests del frontend
- **Sistema de Logs**: Registro centralizado de requests
- **Métricas**: Datos de performance y uso

### 📥 Recibe peticiones de:

- **Frontend React/Angular**: Todas las requests HTTP/HTTPS
- **Aplicaciones Móviles**: APIs REST
- **Sistemas Externos**: Webhooks y integraciones
- **Microservicios**: Health checks y responses

### 🌐 Comunicación con Frontend

#### Desde Frontend (Todas las rutas van por Gateway):

```javascript
// Login (Gateway → Auth)
POST http://localhost:8080/auth/login
{
  "correoUsuario": "user@duoc.cl",
  "password": "password123"
}

// Registro (Gateway → Usuario)
POST http://localhost:8080/usuarios/register
{
  "runUsuario": "12345678",
  "nombreUsuario": "Juan",
  "correoUsuario": "juan@duoc.cl",
  "password": "password123"
}

// Productos (Gateway → Productos)
GET http://localhost:8080/productos
GET http://localhost:8080/productos/123

// Carrito (Gateway → Carrito) - Requiere Auth
POST http://localhost:8080/carrito/items
Authorization: Bearer eyJ...
{
  "productoId": 123,
  "cantidad": 2
}

// Pedidos (Gateway → Pedido) - Requiere Auth
POST http://localhost:8080/pedidos
Authorization: Bearer eyJ...
{
  "carritoId": 456,
  "direccionEnvio": "Av. Libertador 123"
}
```

#### Hacia Frontend (Respuestas procesadas):

```javascript
// Login exitoso
{
  "accessToken": "eyJ...",
  "refreshToken": "eyJ...",
  "tokenType": "Bearer",
  "expiresIn": 1800,
  "usuario": {
    "id": 123,
    "nombre": "Juan",
    "tipoUsuario": "CLIENTE"
  }
}

// Error de autenticación
{
  "error": "UNAUTHORIZED",
  "message": "Token JWT inválido o expirado",
  "timestamp": "2024-01-01T10:30:00Z",
  "path": "/usuarios/123"
}

// Error de servicio no disponible
{
  "error": "SERVICE_UNAVAILABLE", 
  "message": "El servicio de productos está temporalmente no disponible",
  "timestamp": "2024-01-01T10:30:00Z",
  "requestId": "uuid-12345"
}
```

## Políticas de Enrutamiento

### Rutas Públicas (Sin autenticación)

- `/api/auth/login` - Login de usuarios
- `/api/auth/register` - Registro de usuarios
- `/api/productos` - Catálogo público de productos
- `/api/productos/{id}` - Detalle de productos
- `/api/contenido/blogs` - Blogs públicos
- `/api/eventos/mapa` - Mapa público de eventos

### Rutas Protegidas (Requieren JWT)

- `/api/usuarios/**` - Gestión de perfil
- `/api/carrito/**` - Carrito de compras
- `/api/pedidos/**` - Gestión de pedidos
- `/api/pagos/**` - Procesamiento de pagos
- `/api/referidos/**` - Sistema de referidos
- `/api/resenias/**` - Crear/editar reseñas

### Rutas Administrativas (Requieren rol ADMIN)

- `/api/productos` (POST, PUT, DELETE) - Gestión de productos
- `/api/inventario/**` - Control de stock
- `/api/usuarios` (GET all) - Listar usuarios
- `/api/notificaciones/masivo` - Envíos masivos
- `/api/contenido/articulos` (POST, PUT, DELETE) - Gestión de contenido

## Funcionalidades de Seguridad

### Validación JWT

- **Verificación de Firma**: Validar tokens con clave secreta
- **Expiración**: Rechazar tokens expirados
- **Blacklist**: Tokens invalidados por logout
- **Refresh Automático**: Renovación transparente de tokens

### Rate Limiting

- **Por Usuario**: 100 requests/minuto por usuario autenticado
- **Por IP**: 200 requests/minuto por IP pública
- **Por Endpoint**: Límites específicos para endpoints críticos
- **Burst Capacity**: Picos temporales permitidos

### CORS Policy

- **Origins Permitidos**:
    - `http://localhost:3000` (React dev)
    - `http://localhost:5173` (Vite dev)
    - `https://levelup-gamer.cl` (Producción)
- **Métodos**: GET, POST, PUT, DELETE, OPTIONS
- **Headers**: Authorization, Content-Type, X-Requested-With

## Circuit Breaker

### Configuración por Servicio

- **Timeout**: 30 segundos por request
- **Failure Threshold**: 50% de fallos para abrir circuito
- **Recovery Time**: 60 segundos antes de retry
- **Fallback**: Respuestas por defecto para servicios caídos

### Estados del Circuit Breaker

1. **CLOSED** - Funcionamiento normal
2. **OPEN** - Servicio caído, devolver fallback
3. **HALF_OPEN** - Probando si servicio se recuperó

## Load Balancing

### Estrategias de Balanceo

- **Round Robin**: Distribución equitativa por defecto
- **Least Connections**: Para servicios con carga variable
- **Health-based**: Evitar instancias con problemas
- **Sticky Sessions**: Para servicios que requieren estado

### Health Checks

- **Endpoint**: `/actuator/health` en cada microservicio
- **Frecuencia**: Cada 30 segundos
- **Timeout**: 5 segundos por health check
- **Retry**: 3 intentos antes de marcar como down

## Logging y Monitoreo

### Request Logging

- **Request ID**: UUID único por request
- **Timestamp**: Hora exacta de la request
- **User ID**: Usuario autenticado (si aplica)
- **Endpoint**: Ruta solicitada
- **Response Time**: Tiempo de procesamiento
- **Status Code**: Código de respuesta HTTP

### Métricas Recolectadas

- **Throughput**: Requests por segundo
- **Latency**: Tiempo de respuesta promedio
- **Error Rate**: Porcentaje de errores
- **Service Availability**: Uptime de microservicios
- **Resource Usage**: CPU, memoria, conexiones

## Configuración de Rutas

### Predicados de Enrutamiento

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: auth-service
          uri: http://localhost:8081
          predicates:
            - Path=/api/auth/**
          filters:
            - StripPrefix=2
            
        - id: productos-service
          uri: http://localhost:8083
          predicates:
            - Path=/api/productos/**
          filters:
            - StripPrefix=2
            - name: CircuitBreaker
              args:
                name: productos-cb
```

## Filtros Personalizados

### Pre-filters (Antes de enviar a microservicio)

- **AuthenticationFilter**: Validar JWT token
- **RateLimitFilter**: Aplicar límites de rate
- **RequestLoggingFilter**: Log de request entrante
- **CorsFilter**: Manejar políticas CORS

### Post-filters (Después de recibir respuesta)

- **ResponseLoggingFilter**: Log de response
- **ErrorHandlingFilter**: Manejo de errores
- **MetricsFilter**: Recolección de métricas
- **CacheHeadersFilter**: Headers de cache

## Manejo de Errores

### Códigos de Error del Gateway

- **401 Unauthorized**: Token inválido o expirado
- **403 Forbidden**: Sin permisos para el recurso
- **429 Too Many Requests**: Rate limit excedido
- **502 Bad Gateway**: Microservicio no disponible
- **503 Service Unavailable**: Circuit breaker abierto
- **504 Gateway Timeout**: Timeout en microservicio

### Respuestas de Fallback

```json
{
  "error": "SERVICE_UNAVAILABLE",
  "message": "El servicio está temporalmente no disponible",
  "timestamp": "2024-01-15T10:30:00Z",
  "requestId": "uuid-12345"
}
```

## Tecnologías

- Spring Boot 3.4.5
- Spring Cloud Gateway
- Spring Security (JWT validation)
- Redis (Rate limiting, blacklist)
- Resilience4j (Circuit breaker)
- Micrometer (Métricas)
- Logback (Logging estructurado)
- Swagger/OpenAPI (Documentación)
- Spring Cloud LoadBalancer
- Spring Boot Actuator

## Configuración de Desarrollo vs Producción

### Desarrollo

- **CORS**: Permisivo para localhost
- **Rate Limiting**: Límites altos
- **Logging**: Nivel DEBUG
- **Circuit Breaker**: Thresholds bajos para testing

### Producción

- **CORS**: Restrictivo solo dominios autorizados
- **Rate Limiting**: Límites estrictos
- **Logging**: Nivel INFO/WARN
- **Circuit Breaker**: Thresholds optimizados
- **HTTPS**: Obligatorio con certificados SSL

## Puerto

- Desarrollo: 8080 (Puerto principal del gateway)
- Producción: 443 (HTTPS) / 80 (HTTP redirect)