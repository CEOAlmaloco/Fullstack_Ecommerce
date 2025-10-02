# Microservicio de Productos - Level-Up Gamer

## Descripción

Microservicio encargado de la gestión completa del catálogo de productos para gamers. Maneja todas las operaciones CRUD
de productos, categorías y la información detallada de cada artículo disponible en la tienda.

## Funcionalidad Principal

- Gestión completa del catálogo de productos
- Organización por categorías gaming
- Control de información detallada de productos
- Gestión de precios y descripciones
- Filtros avanzados de búsqueda
- Integración con sistema de inventario

## Endpoints API

### Gestión de Productos

- **POST** `/productos` - Crear nuevo producto
- **GET** `/productos` - Listar todos los productos con filtros
- **GET** `/productos/{id}` - Obtener detalle específico del producto
- **PUT** `/productos/{id}` - Actualizar información del producto
- **DELETE** `/productos/{id}` - Eliminar producto del catálogo

### Categorías

- **GET** `/productos/categorias` - Listar todas las categorías disponibles
- **GET** `/productos/categoria/{categoria}` - Productos por categoría específica

### Búsqueda y Filtros

- **GET** `/productos/buscar?nombre={nombre}` - Buscar por nombre
- **GET** `/productos/precio?min={min}&max={max}` - Filtrar por rango de precio
- **GET** `/productos/disponibles` - Solo productos con stock

## Comunicación con otros Microservicios

### Envía datos a:

- **msvc-inventario**: Información de productos para control de stock
- **msvc-carrito**: Validación de productos agregados al carrito
- **msvc-resenia**: Datos de productos para asociar reseñas
- **Frontend**: Catálogo completo y detalles de productos

### Recibe datos de:

- **msvc-inventario**: Estado de stock para mostrar disponibilidad
- **msvc-carrito**: Consultas de productos en carrito
- **msvc-auth**: Validación de permisos para operaciones admin

## Categorías de Productos Level-Up Gamer

1. **Juegos de Mesa** - Catan, Carcassonne, etc.
2. **Accesorios** - Controladores, auriculares gaming
3. **Consolas** - PlayStation 5, Xbox Series X
4. **Computadores Gamers** - PCs de alto rendimiento
5. **Sillas Gamers** - Ergonómicas para largas sesiones
6. **Mouse** - Gaming de alta precisión
7. **Mousepad** - Con iluminación RGB
8. **Poleras Personalizadas** - Con diseños gamer
9. **Polerones Gamers** - Personalizados

## Validaciones de Productos

- **Código**: Requerido, mínimo 3 caracteres
- **Nombre**: Requerido, máximo 100 caracteres
- **Descripción**: Opcional, máximo 500 caracteres
- **Precio**: Requerido, mínimo 0 (productos gratuitos), decimales permitidos
- **Categoría**: Requerida, debe existir en catálogo
- **Imagen**: Opcional, URL válida

## Reglas de Negocio

- Productos pueden ser gratuitos (precio = 0)
- Cada producto debe tener categoría válida
- Descripciones detalladas para mejor experiencia
- Integración obligatoria con inventario
- Precios en CLP (pesos chilenos)

## Tecnologías

- Spring Boot 3.4.5
- Spring Data JPA
- H2 Database
- Bean Validation
- OpenFeign
- Swagger/OpenAPI
- CORS habilitado para frontend

## Puerto

- Desarrollo: 8083
- Producción: Configurable via environment
