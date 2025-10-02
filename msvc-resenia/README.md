# Microservicio de Reseñas - Level-Up Gamer

## Descripción

Microservicio encargado de la gestión completa del sistema de reseñas y calificaciones de productos. Permite a los
clientes compartir sus experiencias, calificar productos comprados y ayudar a otros gamers en sus decisiones de compra.

## Funcionalidad Principal

- Sistema de reseñas y calificaciones (1-5 estrellas)
- Validación de compras para reseñar
- Moderación de contenido inapropiado
- Cálculo de promedios de calificación
- Reseñas destacadas y útiles
- Respuestas a reseñas por parte de administradores
- Integración con sistema de puntos LevelUp

## Endpoints API

### Gestión de Reseñas

- **POST** `/resenias` - Crear nueva reseña de producto
- **GET** `/resenias/producto/{productoId}` - Obtener reseñas de un producto
- **GET** `/resenias/{id}` - Obtener reseña específica
- **PUT** `/resenias/{id}` - Editar reseña propia
- **DELETE** `/resenias/{id}` - Eliminar reseña propia

### Calificaciones y Estadísticas

- **GET** `/resenias/producto/{productoId}/promedio` - Promedio de calificaciones
- **GET** `/resenias/producto/{productoId}/estadisticas` - Distribución de calificaciones
- **GET** `/resenias/usuario/{userId}` - Reseñas realizadas por usuario
- **GET** `/resenias/recientes` - Reseñas más recientes

### Moderación y Administración

- **PUT** `/resenias/{id}/aprobar` - Aprobar reseña (admin)
- **PUT** `/resenias/{id}/rechazar` - Rechazar reseña (admin)
- **POST** `/resenias/{id}/responder` - Responder a reseña (admin)
- **GET** `/resenias/pendientes` - Reseñas pendientes de moderación
- **PUT** `/resenias/{id}/destacar` - Marcar reseña como destacada

### Utilidad y Interacción

- **POST** `/resenias/{id}/util` - Marcar reseña como útil
- **POST** `/resenias/{id}/reportar` - Reportar reseña inapropiada
- **GET** `/resenias/mas-utiles` - Reseñas más valoradas como útiles
- **GET** `/resenias/verificadas` - Solo reseñas de compras verificadas

## Comunicación con otros Microservicios

### Envía datos a:

- **msvc-productos**: Promedio de calificaciones para mostrar en catálogo
- **msvc-referidos**: Puntos LevelUp por reseñas aprobadas
- **msvc-usuario**: Estadísticas de reseñas del usuario
- **Sistema de Notificaciones**: Alertas de nuevas reseñas a administradores

### Recibe datos de:

- **msvc-pedido**: Validación de compra para permitir reseñar
- **msvc-productos**: Información de productos para asociar reseñas
- **msvc-usuario**: Datos del usuario que realiza la reseña
- **msvc-auth**: Validación de permisos para moderar

## Validaciones de Reseñas

### Requisitos para Reseñar

- Usuario debe haber comprado el producto
- Solo una reseña por producto por usuario
- Compra debe estar entregada
- Máximo 90 días después de la entrega

### Validaciones de Contenido

- **Título**: Requerido, 10-100 caracteres
- **Comentario**: Requerido, 50-1000 caracteres
- **Calificación**: Requerida, 1-5 estrellas
- **Contenido**: Sin palabras ofensivas o spam
- **Imágenes**: Opcional, máximo 5 imágenes

## Estados de Reseña

1. **PENDIENTE** - Esperando moderación
2. **APROBADA** - Reseña visible públicamente
3. **RECHAZADA** - No cumple políticas
4. **DESTACADA** - Marcada como ejemplar
5. **REPORTADA** - Bajo revisión por reportes
6. **ELIMINADA** - Removida del sistema

## Sistema de Calificación

### Criterios de Evaluación

- **Calidad del Producto** (1-5 estrellas)
- **Relación Precio-Calidad** (1-5 estrellas)
- **Tiempo de Entrega** (1-5 estrellas)
- **Empaque y Presentación** (1-5 estrellas)
- **Recomendación General** (Sí/No)

### Cálculo de Promedio

- Promedio ponderado por utilidad de reseña
- Mayor peso a reseñas verificadas
- Consideración de fecha de reseña
- Exclusión de reseñas reportadas

## Funcionalidades Especiales

- **Reseñas Verificadas**: Distintivo para compras confirmadas
- **Reseñas con Imágenes**: Soporte para fotos del producto
- **Filtros Avanzados**: Por calificación, fecha, utilidad
- **Respuestas Oficiales**: Administradores pueden responder
- **Puntos LevelUp**: 50 puntos por reseña aprobada

## Reglas de Negocio

- Una reseña por producto por usuario
- Solo productos comprados pueden ser reseñados
- Reseñas pueden editarse dentro de 7 días
- Moderación automática por palabras clave
- Puntos solo por primera reseña aprobada por producto
- Reseñas de administradores no generan puntos

## Políticas de Contenido

- Prohibido contenido ofensivo o discriminatorio
- No se permiten datos personales de terceros
- Prohibidas reseñas falsas o incentivadas
- No se permite spam o contenido promocional
- Reseñas deben ser sobre el producto específico

## Tecnologías

- Spring Boot 3.4.5
- Spring Data JPA
- H2 Database
- OpenFeign
- Swagger/OpenAPI
- Content moderation API
- Image upload service
- Scheduled Tasks (limpieza automática)

## Puerto

- Desarrollo: 8088
- Producción: Configurable via environment
