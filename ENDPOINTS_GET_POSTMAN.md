# Endpoints GET para Postman - Todos los Microservicios

Esta documentación contiene todos los endpoints GET de todos los microservicios para probar los datos iniciales en Postman.

## ⚠️ IMPORTANTE: Configuración de API Key

**TODOS los endpoints requieren el header `X-API-Key` para funcionar correctamente.**

Si no incluyes el header, recibirás un error 401:
```json
{
    "error": "Unauthorized",
    "message": "Invalid or missing API key. Please include X-API-Key header.",
    "status": 401
}
```

### Valor del API Key
```
X-API-Key: levelup-2024-secret-api-key-change-in-production
```

## 📸 IMPORTANTE: Formato de Imágenes

**TODAS las imágenes se almacenan en Base64 en la base de datos H2.**

En las respuestas JSON de Postman, las imágenes aparecen como URLs Base64:
```
data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAA...
```

**Formato:**
- `imagen`: URL Base64 completa (data:image/png;base64,...)
- `imagenes`: JSON array con URLs Base64 (["data:image/png;base64,...", ...])
- `imagenUrl`: Alias de `imagen` (mismo valor)

**El frontend y la app de Kotlin pueden usar directamente las URLs Base64 sin necesidad de decodificar manualmente:**
- **React/TypeScript**: Usar directamente `<img src={producto.imagen} />` - el navegador lo maneja automáticamente
- **Kotlin/Android**: Usar librerías como Coil o Glide que manejan automáticamente URLs `data:image`

### Cómo Configurar en Postman

#### Opción 1: Configurar a nivel de Colección (Recomendado)

1. Crea una nueva colección en Postman
2. Ve a la pestaña "Variables" de la colección
3. Agrega una variable:
   - **Nombre**: `api_key`
   - **Valor inicial**: `levelup-2024-secret-api-key-change-in-production`
   - **Valor actual**: `levelup-2024-secret-api-key-change-in-production`
4. Ve a la pestaña "Authorization" de la colección
5. Selecciona "API Key" como tipo
6. Configura:
   - **Key**: `X-API-Key`
   - **Value**: `{{api_key}}`
   - **Add to**: `Header`

#### Opción 2: Configurar a nivel de Request

1. En cada request, ve a la pestaña "Headers"
2. Agrega un nuevo header:
   - **Key**: `X-API-Key`
   - **Value**: `levelup-2024-secret-api-key-change-in-production`

## Configuración Base

- **API Gateway**: `http://localhost:8094`
- **Header requerido**: `X-API-Key: levelup-2024-secret-api-key-change-in-production`
- **Header opcional para usuarios**: `X-User-Id: 1` (para endpoints que requieren usuario autenticado)

## Microservicios y Puertos Directos

| Microservicio | Puerto Directo | Context Path |
|--------------|----------------|--------------|
| msvc-productos | 8003 | /api/v1 |
| msvc-usuario | 8095 | /api/v1 |
| msvc-carrito | 8008 | /carrito |
| msvc-inventario | 8084 | /inventario |
| msvc-referidos | 8089 | /referidos |
| msvc-gateway | 8094 | / |

---

## 1. MSVC-PRODUCTOS (Puerto 8003)

### Endpoints GET Productos

**Base URL directo**: `http://localhost:8003/api/v1/productos`  
**Base URL Gateway**: `http://localhost:8094/productos`

#### Obtener todos los productos
```
GET http://localhost:8094/productos
Headers:
  X-API-Key: levelup-2024-secret-api-key-change-in-production
```

**O desde el microservicio directo:**
```
GET http://localhost:8003/api/v1/productos
Headers:
  X-API-Key: levelup-2024-secret-api-key-change-in-production
```

**Respuesta JSON:**
```json
[
    {
        "id": 1,
        "titulo": "PlayStation 5",
        "imagen": "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAA...",
        "imagenes": "[\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAA...\", \"data:image/webp;base64,iVBORw0KGgoAAAANSUhEUgAA...\"]",
        "precio": 549990.0,
        "disponible": true,
        "rating": 1.7,
        "descripcion": "La consola de última generación de Sony, que ofrece gráficos impresionantes y tiempos de carga ultrarrápidos para una experiencia de juego inmersiva.",
        "stock": 50,
        "codigoProducto": "CO001",
        "createdAt": "2025-10-30T22:41:21.32389",
        "updatedAt": "2025-10-30T22:41:21.32389",
        "categoriaId": "CO",
        "subcategoriaId": "HA",
        "nombre": "PlayStation 5",
        "imagenUrl": "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAA..."
    },
    {
        "id": 2,
        "titulo": "PlayStation 4 Slim",
        "imagen": "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAA...",
        "imagenes": "[\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAA...\"]",
        "precio": 179990.0,
        "disponible": true,
        "rating": 5.0,
        "descripcion": "Una consola versátil y compacta que sigue siendo ideal para disfrutar de un extenso catálogo de juegos con gran rendimiento y entretenimiento garantizado.",
        "stock": 30,
        "codigoProducto": "CO002",
        "createdAt": "2025-10-30T22:41:21.32389",
        "updatedAt": "2025-10-30T22:41:21.32389",
        "categoriaId": "CO",
        "subcategoriaId": "HA",
        "nombre": "PlayStation 4 Slim",
        "imagenUrl": "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAA..."
    },
    {
        "id": 15,
        "titulo": "Poleron PlayStation Retro Negro",
        "imagen": "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAA...",
        "imagenes": "[\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAA...\"]",
        "precio": 40000.0,
        "disponible": true,
        "rating": 4.5,
        "descripcion": "Polerón retro en color negro con el clásico logo de PlayStation, perfecto para los nostálgicos del gaming.",
        "stock": 200,
        "codigoProducto": "RO004",
        "createdAt": "2025-10-30T22:41:21.32389",
        "updatedAt": "2025-10-30T22:41:21.32389",
        "categoriaId": "RO",
        "subcategoriaId": "PG",
        "nombre": "Poleron PlayStation Retro Negro",
        "imagenUrl": "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAA..."
    }
]
```

#### Obtener producto por ID
```
GET http://localhost:8094/productos/{id}
Headers:
  X-API-Key: levelup-2024-secret-api-key-change-in-production
Ejemplo: GET http://localhost:8094/productos/1
```

#### Obtener categorías y subcategorías
```
GET http://localhost:8094/productos/categorias
Headers:
  X-API-Key: levelup-2024-secret-api-key-change-in-production
```

**Respuesta JSON:**
```json
{
    "subcategorias": [
        {
            "categoriaId": "CO",
            "id": "MA",
            "nombre": "Mandos"
        },
        {
            "categoriaId": "CO",
            "id": "AC",
            "nombre": "Accesorios"
        },
        {
            "categoriaId": "CO",
            "id": "HA",
            "nombre": "Hardware"
        },
        {
            "categoriaId": "PE",
            "id": "TE",
            "nombre": "Teclados"
        },
        {
            "categoriaId": "PE",
            "id": "MO",
            "nombre": "Mouses"
        },
        {
            "categoriaId": "PE",
            "id": "AU",
            "nombre": "Auriculares"
        },
        {
            "categoriaId": "PE",
            "id": "MT",
            "nombre": "Monitores"
        },
        {
            "categoriaId": "PE",
            "id": "MI",
            "nombre": "Microfonos"
        },
        {
            "categoriaId": "PE",
            "id": "CW",
            "nombre": "Camaras web"
        },
        {
            "categoriaId": "PE",
            "id": "MP",
            "nombre": "Mousepad"
        },
        {
            "categoriaId": "PE",
            "id": "SI",
            "nombre": "Sillas Gamers"
        },
        {
            "categoriaId": "EN",
            "id": "JM",
            "nombre": "Juegos de Mesa"
        },
        {
            "categoriaId": "RO",
            "id": "PG",
            "nombre": "Polerones Gamers Personalizados"
        },
        {
            "categoriaId": "RO",
            "id": "PR",
            "nombre": "Poleras Personalizadas"
        }
    ],
    "categorias": [
        {
            "id": "CO",
            "nombre": "Consola"
        },
        {
            "id": "PE",
            "nombre": "Perifericos"
        },
        {
            "id": "RO",
            "nombre": "Ropa"
        },
        {
            "id": "EN",
            "nombre": "Entretenimiento"
        }
    ]
}
```

#### Buscar productos por nombre
```
GET http://localhost:8094/productos/buscar?nombre=PlayStation
Headers:
  X-API-Key: levelup-2024-secret-api-key-change-in-production
```

#### Productos por categoría
```
GET http://localhost:8094/productos/categoria/{categoriaId}
Headers:
  X-API-Key: levelup-2024-secret-api-key-change-in-production
Ejemplo: GET http://localhost:8094/productos/categoria/CO
```

#### Productos disponibles
```
GET http://localhost:8094/productos/disponibles
Headers:
  X-API-Key: levelup-2024-secret-api-key-change-in-production
```

#### Filtrar productos (GET)
```
GET http://localhost:8094/productos/filtrar?categoria=CO&precioMin=0&precioMax=100000&disponible=true&pagina=0&tamano=10
Headers:
  X-API-Key: levelup-2024-secret-api-key-change-in-production
```

#### Health Check
```
GET http://localhost:8003/api/v1/health
```

---

## 2. MSVC-USUARIO (Puerto 8095)

### Endpoints GET Usuarios

**Base URL directo**: `http://localhost:8095/api/v1/usuarios`  
**Base URL Gateway**: `http://localhost:8094/usuarios` (si está configurado)

#### Obtener todos los usuarios (paginado)
```
GET http://localhost:8095/api/v1/usuarios?page=0&size=20
```

#### Obtener usuario por ID
```
GET http://localhost:8095/api/v1/usuarios/{id}
Ejemplo: GET http://localhost:8095/api/v1/usuarios/1
```

#### Obtener usuario por correo
```
GET http://localhost:8095/api/v1/usuarios/correo/{correo}
Ejemplo: GET http://localhost:8095/api/v1/usuarios/correo/usuario@example.com
```

#### Obtener usuario por código de referido
```
GET http://localhost:8095/api/v1/usuarios/referido/{codigoReferido}
Ejemplo: GET http://localhost:8095/api/v1/usuarios/referido/REF001
```

#### Usuarios por tipo
```
GET http://localhost:8095/api/v1/usuarios/tipo/{tipoUsuario}?page=0&size=20
Tipos: CLIENTE, ADMINISTRADOR, MODERADOR, VENDEDOR
Ejemplo: GET http://localhost:8095/api/v1/usuarios/tipo/CLIENTE
```

#### Usuarios por estado
```
GET http://localhost:8095/api/v1/usuarios/estado/{estado}?page=0&size=20
Estados: ACTIVO, INACTIVO, SUSPENDIDO, BANEADO, PENDIENTE_VERIFICACION
Ejemplo: GET http://localhost:8095/api/v1/usuarios/estado/ACTIVO
```

#### Usuarios por nivel
```
GET http://localhost:8095/api/v1/usuarios/nivel/{nivelUsuario}?page=0&size=20
Niveles: NOVATO, BRONCE, PLATA, ORO, PLATINO, DIAMANTE, MAESTRO, GRAN_MAESTRO
Ejemplo: GET http://localhost:8095/api/v1/usuarios/nivel/ORO
```

#### Usuarios referidos por código
```
GET http://localhost:8095/api/v1/usuarios/referidos/{codigoReferido}?page=0&size=20
Headers:
  X-API-Key: levelup-2024-secret-api-key-change-in-production
```

**Respuesta JSON:**
```json
{
    "content": [],
    "pageable": {
        "pageNumber": 0,
        "pageSize": 20,
        "sort": [],
        "offset": 0,
        "unpaged": false,
        "paged": true
    },
    "totalPages": 0,
    "totalElements": 0,
    "last": true,
    "size": 20,
    "number": 0,
    "sort": [],
    "numberOfElements": 0,
    "first": true,
    "empty": true
}
```

#### Buscar usuarios
```
GET http://localhost:8095/api/v1/usuarios/buscar?nombre=Juan&correo=test@example.com&ciudad=Santiago&page=0&size=20
```

#### Estadísticas de usuarios
```
GET http://localhost:8095/api/v1/usuarios/estadisticas
```

#### Verificar disponibilidad de correo
```
GET http://localhost:8095/api/v1/usuarios/verificar-correo/{correo}
Ejemplo: GET http://localhost:8095/api/v1/usuarios/verificar-correo/nuevo@example.com
```

#### Verificar disponibilidad de código de referido
```
GET http://localhost:8095/api/v1/usuarios/verificar-codigo-referido/{codigoReferido}
```

#### Perfil del usuario autenticado
```
GET http://localhost:8095/api/v1/usuarios/perfil
Headers:
  X-User-Id: 1
```

#### Direcciones del usuario
```
GET http://localhost:8095/api/v1/usuarios/direcciones
Headers:
  X-User-Id: 1
```

---

## 3. MSVC-CARRITO (Puerto 8008)

### Endpoints GET Carrito

**Base URL directo**: `http://localhost:8008/carrito`  
**Base URL Gateway**: `http://localhost:8094/carrito` (si está configurado)

#### Obtener todos los carritos
```
GET http://localhost:8008/carrito
```

#### Obtener carrito por ID
```
GET http://localhost:8008/carrito/{id}
Ejemplo: GET http://localhost:8008/carrito/1
```

#### Carritos por usuario
```
GET http://localhost:8008/carrito/usuario/{idUsuario}
Ejemplo: GET http://localhost:8008/carrito/usuario/1
```

#### Carrito activo por usuario
```
GET http://localhost:8008/carrito/usuario/{idUsuario}/activo
```

#### Carrito activo del usuario autenticado
```
GET http://localhost:8008/carrito/activo
Headers:
  X-User-Id: 1
```

#### Carritos por estado
```
GET http://localhost:8008/carrito/estado/{estado}
Estados: ACTIVO, COMPLETADO, CANCELADO, EXPIRADO, GUARDADO
Ejemplo: GET http://localhost:8008/carrito/estado/ACTIVO
```

#### Carritos expirados
```
GET http://localhost:8008/carrito/expirados
```

#### Carritos por rango de fechas
```
GET http://localhost:8008/carrito/fechas?fechaInicio=2024-01-01T00:00:00&fechaFin=2024-12-31T23:59:59
```

#### Carritos por código promocional
```
GET http://localhost:8008/carrito/promocion/{codigoPromocional}
```

#### Contar items en carrito
```
GET http://localhost:8008/carrito/{id}/contar-items
```

#### Sumar cantidad de items en carrito
```
GET http://localhost:8008/carrito/{id}/sumar-cantidad
```

### Endpoints GET Items

#### Obtener todos los items
```
GET http://localhost:8008/carrito/items
```

#### Obtener item por ID
```
GET http://localhost:8008/carrito/items/{id}
```

#### Items por carrito
```
GET http://localhost:8008/carrito/items/carrito/{idCarrito}
```

#### Items por estado
```
GET http://localhost:8008/carrito/items/estado/{estado}
```

#### Items por producto
```
GET http://localhost:8008/carrito/items/producto/{idProducto}
```

---

## 4. MSVC-INVENTARIO (Puerto 8084)

### Endpoints GET Inventario

**Base URL directo**: `http://localhost:8084/inventario`  
**Base URL Gateway**: `http://localhost:8094/inventario` (si está configurado)

#### Obtener todo el inventario
```
GET http://localhost:8084/inventario
```

#### Obtener inventario por ID
```
GET http://localhost:8084/inventario/{id}
Ejemplo: GET http://localhost:8084/inventario/1
```

#### Inventario por producto
```
GET http://localhost:8084/inventario/producto/{productoId}
Ejemplo: GET http://localhost:8084/inventario/producto/1
```

#### Productos con stock crítico
```
GET http://localhost:8084/inventario/stock-critico
```

#### Productos agotados
```
GET http://localhost:8084/inventario/agotados
```

---

## 5. MSVC-REFERIDOS (Puerto 8089)

### Endpoints GET Referidos

**Base URL directo**: `http://localhost:8089/referidos`  
**Base URL Gateway**: `http://localhost:8094/referidos` (si está configurado)

#### Obtener todos los referidos
```
GET http://localhost:8089/referidos
```

#### Obtener referido por ID
```
GET http://localhost:8089/referidos/{id}
Ejemplo: GET http://localhost:8089/referidos/1
```

#### Obtener código de referido
```
GET http://localhost:8089/referidos/codigo?idUsuario=1
```

#### Validar código de referido
```
GET http://localhost:8089/referidos/codigo/{codigo}
Ejemplo: GET http://localhost:8089/referidos/codigo/REF001
```

#### Lista de referidos del usuario
```
GET http://localhost:8089/referidos/lista?idUsuario=1
```

#### Referidos por usuario
```
GET http://localhost:8089/referidos/usuario/{usuarioId}/referidos
Ejemplo: GET http://localhost:8089/referidos/usuario/1/referidos
```

#### Puntos del usuario
```
GET http://localhost:8089/referidos/{id}/puntos
```

#### Productos canjeables
```
GET http://localhost:8089/referidos/productos/canjeables?puntos=100
```

#### Recompensas
```
GET http://localhost:8089/referidos/recompensas?idUsuario=1
```

#### Descuentos disponibles
```
GET http://localhost:8089/referidos/{id}/descuentos
```

### Endpoints GET Puntos

**Base URL**: `http://localhost:8089/puntos`

#### Puntos del usuario
```
GET http://localhost:8089/puntos/usuario/{idUsuario}
```

#### Código de referido del usuario
```
GET http://localhost:8089/puntos/usuario/{idUsuario}/codigo
```

#### Historial de puntos
```
GET http://localhost:8089/puntos/usuario/{idUsuario}/historial
```

---

## 6. MSVC-GATEWAY (Puerto 8094)

### Endpoints GET Gateway

#### Health Check
```
GET http://localhost:8094/health
```

#### Información de rutas
```
GET http://localhost:8094/routes
```

#### Información del gateway
```
GET http://localhost:8094/info
```

---

## 7. Otros Microservicios

### MSVC-PROMOCIONES (Puerto 8091)

#### Obtener todas las promociones
```
GET http://localhost:8091/promociones
```

#### Promociones activas
```
GET http://localhost:8091/promociones/activas
```

#### Promociones DUOC
```
GET http://localhost:8091/promociones/duoc
```

#### Promoción por ID
```
GET http://localhost:8091/promociones/{id}
```

#### Promoción por código
```
GET http://localhost:8091/promociones/codigo/{codigo}
```

#### Validar código promocional
```
GET http://localhost:8091/promociones/validar/{codigo}
```

#### Promociones por categoría
```
GET http://localhost:8091/promociones/categoria/{categoria}
```

#### Promociones del usuario
```
GET http://localhost:8091/promociones/usuario
Headers:
  X-User-Id: 1
```

### MSVC-RESENIA (Puerto 8090)

#### Obtener todas las reseñas
```
GET http://localhost:8090/resenias
```

#### Reseña por ID
```
GET http://localhost:8090/resenias/{id}
```

#### Reseñas por producto
```
GET http://localhost:8090/resenias/producto/{idProducto}
```

#### Reseñas por usuario
```
GET http://localhost:8090/resenias/usuario/{idUsuario}
```

#### Rating promedio del producto
```
GET http://localhost:8090/resenias/producto/{idProducto}/rating-promedio
```

### MSVC-PAGOS (Puerto 8088)

#### Obtener todos los pagos
```
GET http://localhost:8088/pagos
```

#### Pago por ID
```
GET http://localhost:8088/pagos/{id}
```

#### Pagos por pedido
```
GET http://localhost:8088/pagos/pedido/{idPedido}
```

#### Pagos por usuario
```
GET http://localhost:8088/pagos/usuario/{idUsuario}
```

#### Pagos por estado
```
GET http://localhost:8088/pagos/estado/{estado}
```

#### Pagos vencidos
```
GET http://localhost:8088/pagos/vencidos
```

#### Reportes
```
GET http://localhost:8088/pagos/reportes/total
GET http://localhost:8088/pagos/reportes/cantidad/{estado}
GET http://localhost:8088/pagos/reportes/comisiones
```

### MSVC-PEDIDO (Puerto 8085)

#### Obtener todos los pedidos
```
GET http://localhost:8085/pedidos
```

#### Pedido por ID
```
GET http://localhost:8085/pedidos/{id}
```

#### Pedido por código
```
GET http://localhost:8085/pedidos/codigo/{codigo}
```

#### Pedidos por usuario
```
GET http://localhost:8085/pedidos/usuario/{idUsuario}
```

### MSVC-EVENTOS (Puerto 8092)

#### Obtener todos los eventos
```
GET http://localhost:8092/eventos
```

#### Eventos futuros
```
GET http://localhost:8092/eventos/futuros
```

#### Eventos en curso
```
GET http://localhost:8092/eventos/en-curso
```

#### Eventos por tipo
```
GET http://localhost:8092/eventos/tipo/{tipo}
```

#### Eventos con cupos disponibles
```
GET http://localhost:8092/eventos/con-cupos
```

#### Evento por ID
```
GET http://localhost:8092/eventos/{id}
```

#### Disponibilidad del evento
```
GET http://localhost:8092/eventos/{id}/disponibilidad
```

### MSVC-CONTENIDO (Puerto 8093)

#### Obtener todos los artículos
```
GET http://localhost:8093/contenido/articulos
```

#### Artículo por ID
```
GET http://localhost:8093/contenido/articulos/{id}
```

#### Artículos publicados
```
GET http://localhost:8093/contenido/articulos/publicados
```

#### Artículos destacados
```
GET http://localhost:8093/contenido/articulos/destacados
```

#### Artículos populares
```
GET http://localhost:8093/contenido/articulos/populares
```

#### Artículos recientes
```
GET http://localhost:8093/contenido/articulos/recientes
```

#### Artículos por categoría
```
GET http://localhost:8093/contenido/articulos/categoria/{categoria}
```

#### Buscar artículos
```
GET http://localhost:8093/contenido/articulos/buscar?q=busqueda
```

---

## Notas Importantes

1. **API Key**: Todos los endpoints requieren el header `X-API-Key` con el valor configurado.

2. **Usuario Autenticado**: Algunos endpoints requieren el header `X-User-Id` para identificar al usuario autenticado.

3. **Base64 en Imágenes**: Las imágenes se almacenan en Base64 en la base de datos. El frontend y la app de Kotlin deben decodificar el Base64 para mostrar las imágenes.

4. **Paginación**: Los endpoints que devuelven listas pueden usar paginación con parámetros `page` y `size`.

5. **Filtros**: Muchos endpoints aceptan parámetros de query string para filtrar resultados.

---

## Ejemplo de Colección Postman

Para crear una colección en Postman con estos endpoints:

1. Crear una nueva colección llamada "Level Up Gamer - GET Endpoints"
2. Agregar una variable de colección:
   - `base_url_gateway`: `http://localhost:8094`
   - `api_key`: `levelup-2024-secret-api-key-change-in-production`
3. Crear carpetas para cada microservicio
4. Importar cada endpoint usando las URLs proporcionadas
5. Configurar el header `X-API-Key` como variable de colección

---

## Verificación de Datos Iniciales

Para verificar que los datos iniciales se cargaron correctamente, ejecuta estos endpoints en orden:

1. **Productos**: `GET http://localhost:8094/productos`
2. **Categorías**: `GET http://localhost:8094/productos/categorias`
3. **Usuarios**: `GET http://localhost:8095/api/v1/usuarios`
4. **Inventario**: `GET http://localhost:8084/inventario`
5. **Referidos**: `GET http://localhost:8089/referidos`
6. **Carrito**: `GET http://localhost:8008/carrito`

Si alguno de estos endpoints devuelve datos vacíos, verifica que el inicializador de datos correspondiente se haya ejecutado correctamente al iniciar el microservicio.

