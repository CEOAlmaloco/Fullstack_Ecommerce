# Guía de Microservicios para Probar la App Kotlin

## Microservicios Necesarios

Para probar la aplicación Kotlin con productos, necesitas iniciar **mínimo estos 2 microservicios**:

### 1. **msvc-productos** (Puerto 8003)
- **Puerto**: 8003
- **Ruta base**: `/api/v1/productos`
- **Descripción**: Proporciona todos los endpoints de productos
- **Iniciar**: Desde `Backend_Java_Spring/Fullstack_Ecommerce/msvc-productos`

### 2. **msvc-gateway** (Puerto 8094)
- **Puerto**: 8094
- **Ruta base**: `/` (root)
- **Descripción**: API Gateway que redirige las peticiones a los microservicios
- **Iniciar**: Desde `Backend_Java_Spring/Fullstack_Ecommerce/msvc-gateway`

## Orden de Inicio

1. **Primero**: Inicia `msvc-productos` (puerto 8003)
2. **Segundo**: Inicia `msvc-gateway` (puerto 8094)

## Problema Detectado

El gateway está configurado para redirigir `/productos/**` directamente a `http://localhost:8003`, pero el microservicio de productos tiene `context-path=/api/v1`, lo que significa que espera rutas en `/api/v1/productos/**`.

### Solución: Actualizar el Gateway

El gateway necesita reescribir la ruta para agregar el prefijo `/api/v1`:

```java
// Product Service - Reescribe /productos/** a /api/v1/productos/**
.route("msvc-productos", r -> r.path("/productos/**")
        .filters(f -> f.rewritePath("/productos/(?<path>.*)", "/api/v1/productos/${path}"))
        .uri("http://localhost:8003"))
```

## URLs de Prueba

### Desde Kotlin (Emulador Android)
- **Base URL**: `http://10.0.2.2:8094/`
- **Endpoint de productos**: `http://10.0.2.2:8094/productos`

### Desde Postman/Thunder Client
- **Base URL**: `http://localhost:8094/`
- **Endpoint de productos**: `http://localhost:8094/productos`
- **Headers requeridos**:
  - `X-API-Key: levelup-2024-secret-api-key-change-in-production`

## Microservicios Adicionales (Opcionales)

Si quieres probar funcionalidades completas:

### Carrito
- **msvc-carrito** (Puerto 8008)
- **Ruta**: `/carrito/**`

### Autenticación
- **msvc-auth** (Puerto 8001)
- **Ruta**: `/auth/**`

### Usuarios
- **msvc-usuario** (Puerto 8095)
- **Ruta**: `/usuarios/**`

### Eventos
- **msvc-eventos** (Puerto 8092)
- **Ruta**: `/eventos/**`

## Comandos para Iniciar

### En Windows (PowerShell)
```powershell
# Terminal 1: Productos
cd Backend_Java_Spring\Fullstack_Ecommerce\msvc-productos
mvn spring-boot:run

# Terminal 2: Gateway
cd Backend_Java_Spring\Fullstack_Ecommerce\msvc-gateway
mvn spring-boot:run
```

### En Linux/Mac
```bash
# Terminal 1: Productos
cd Backend_Java_Spring/Fullstack_Ecommerce/msvc-productos
mvn spring-boot:run

# Terminal 2: Gateway
cd Backend_Java_Spring/Fullstack_Ecommerce/msvc-gateway
mvn spring-boot:run
```

## Verificación

Una vez iniciados, verifica que funcionen:

1. **Productos directo** (sin gateway):
   ```
   GET http://localhost:8003/api/v1/productos
   Header: X-API-Key: levelup-2024-secret-api-key-change-in-production
   ```

2. **Productos a través del gateway**:
   ```
   GET http://localhost:8094/productos
   Header: X-API-Key: levelup-2024-secret-api-key-change-in-production
   ```

3. **Desde Kotlin**:
   - Configurado en `ApiConfig.kt` con `BASE_URL = "http://10.0.2.2:8094/"`
   - Endpoint: `productosService.getProductos()`

## Troubleshooting

### No se obtienen productos
1. Verifica que ambos microservicios estén corriendo
2. Verifica que el gateway esté redirigiendo correctamente (ver solución arriba)
3. Verifica que el API Key esté configurado en Kotlin (`BuildConfig.API_KEY`)
4. Verifica los logs del gateway y productos para ver errores
5. Verifica que la base de datos H2 tenga datos (consulta `http://localhost:8003/h2-console`)

### Error de CORS
- El gateway ya está configurado para permitir `http://10.0.2.2:8094` (emulador Android)
- Verifica que el `ProductoController` tenga `@CrossOrigin` con el origen correcto

### Error de conexión
- Verifica que el emulador esté usando la IP correcta (`10.0.2.2` para emulador, `192.168.x.x` para dispositivo físico)
- Verifica que no haya firewall bloqueando las conexiones

